package com.iwami.iwami.app.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iwami.iwami.app.biz.PresentBiz;
import com.iwami.iwami.app.biz.UserBiz;
import com.iwami.iwami.app.common.dispatch.AjaxClass;
import com.iwami.iwami.app.common.dispatch.AjaxMethod;
import com.iwami.iwami.app.constants.ErrorCodeConstants;
import com.iwami.iwami.app.constants.IWamiConstants;
import com.iwami.iwami.app.exception.NotEnoughPrizeException;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.util.IWamiUtils;

@AjaxClass
public class PresentAjax {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private PresentBiz presentBiz;
	
	private UserBiz userBiz;

	@AjaxMethod(path = "gift/express.ajax")
	public Map<Object, Object> exchangeExpressPresents(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			if(params.containsKey("userid") && params.containsKey("ids") && params.containsKey("counts") && params.containsKey("cellPhone")
					 && params.containsKey("address") && params.containsKey("name")){
				long userid = NumberUtils.toLong(params.get("userid"), -1);
				if(userid > 0){
					// 1. split ids
					String[] tmpids = StringUtils.split(params.get("ids"), IWamiConstants.SEPARATOR_PRESENT);
					// 2. check ids
					if(tmpids != null && tmpids.length > 0){
						List<Long> ids = new ArrayList<Long>();
						for(String tmpid : tmpids){
							long id = NumberUtils.toLong(tmpid, -1);
							if(id > 0)
								ids.add(id);
						}
						
						if(ids.size() == tmpids.length){
							String[] tmpcounts = StringUtils.split(params.get("counts"), IWamiConstants.SEPARATOR_PRESENT);
							if(tmpcounts != null && tmpcounts.length > 0){
								List<Integer> counts = new ArrayList<Integer>();
								for(String tmpcount : tmpcounts){
									int count = NumberUtils.toInt(tmpcount, -1);
									if(count > 0)
										counts.add(count);
								}
								
								if(counts.size() == ids.size()){
									long cellPhone = NumberUtils.toLong(params.get("cellPhone"), -1);
									if(cellPhone > 0 && IWamiUtils.validatePhone("" + cellPhone)){
										String address = StringUtils.trimToEmpty(params.get("address"));
										if(StringUtils.isNotBlank(address)){
											String name = StringUtils.trimToEmpty(params.get("name"));
											if(StringUtils.isNotBlank(name)){
												User user = userBiz.getUserById(userid);
												if(user != null){
													// 3. get present by ids
													Map<Long, Present> presents = presentBiz.getPresentsByIds(ids);
													// 4. present count = ids.length?
													if(presents != null && presents.size() == ids.size()){
														// 5. group by type
														List<Present> onlineExpress = new ArrayList<Present>();
						
														for(Present present : presents.values())
															if(present.getType() == Present.TYPE_ONLINE_EMS)
																onlineExpress.add(present);
														
														if(onlineExpress.size() == ids.size()){
															Map<Present, Integer> presentCnts = new HashMap<Present, Integer>();
															int allPrize = 0;
															for(int i = 0; i < ids.size(); i ++){
																long tmpid = ids.get(i);
																int tmpCnt = counts.get(i);
																
																Present present = presents.get(tmpid);
																
																allPrize += (present.getPrize() * tmpCnt);
																presentCnts.put(present, tmpCnt);
															}
															
															if(allPrize <= user.getCurrentPrize()){
																if(presentBiz.exchangeExpress(user, presentCnts, cellPhone, address, name))
																	result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
																else {
																	result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_NOTENOUGHT_PRIZE);
																	result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_NOTENOUGHT_PRIZE));
																}
															} else{
																result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_NOTENOUGHT_PRIZE);
																result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_NOTENOUGHT_PRIZE));
															}
														} else{
															result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ID);
															result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ID));
														}
													}
												} else{
													result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_USERID);
													result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_USERID));
												}
											} else{
												result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_NAME);
												result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_NAME));
											}
										} else{
											result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ADDRESS);
											result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ADDRESS));
										}
									} else{
										result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_CELLPHONE);
										result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_CELLPHONE));
									}
								} else{
									result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_COUNT);
									result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_COUNT));
								}
							} else{
								result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_COUNT);
								result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_COUNT));
							}
						} else{
							result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ID);
							result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ID));
						}
					} else{
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ID);
						result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_PRESENT_ID));
					}
				} else{
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_EXCHANGE_USERID);
					result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_EXCHANGE_USERID));
				}
			} else
				result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in exchangePresents", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		return result;
	}

	@AjaxMethod(path = "sendsms.ajax")
	public Map<Object, Object> sendSMS(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			if(params.containsKey("userid") && params.containsKey("cellPhone")){
				long userid = NumberUtils.toLong(params.get("userid"), -1);
				if(userid > 0){
					long cellPhone = NumberUtils.toLong(params.get("cellPhone"), -1);
					if(cellPhone > 0 && IWamiUtils.validatePhone("" + cellPhone)){
						User user = userBiz.getUserById(userid);
						if(user != null){
							if(cellPhone != user.getCellPhone()){
								if(userBiz.sendSMS(cellPhone, user))
									result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
								else
									result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
							} else{
								result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_SEND_SMS_SELF);
								result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_SEND_SMS_SELF));
							}
						} else{
							result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_SEND_SMS_USERID);
							result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_SEND_SMS_USERID));
						}
					} else{
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_SEND_SMS_CELLPHONE_INVALID);
						result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_SEND_SMS_CELLPHONE_INVALID));
					}
				} else{
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_SEND_SMS_USERID);
					result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_SEND_SMS_USERID));
				}
			} else
				result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in getPresents", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		return result;
	}

	@AjaxMethod(path = "present/gift.ajax")
	public Map<Object, Object> sendGift(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			if(params.containsKey("userid") && params.containsKey("cellPhone") && params.containsKey("prize")){
				long userid = NumberUtils.toLong(params.get("userid"), -1);
				if(userid > 0){
					User user = userBiz.getUserById(userid);
					if(user != null){
						long cellPhone = NumberUtils.toLong(params.get("cellPhone"), -1);
						if(cellPhone > 0){
							if(cellPhone != user.getCellPhone()){
								User user2 = userBiz.getUserByCellPhone(cellPhone);
								if(user2 != null){
									int prize = NumberUtils.toInt(params.get("prize"), -1);
									if(prize > 0){
										if(prize >= user.getCurrentPrize()){
											if(presentBiz.gift(user, user2, prize)){
												user = userBiz.getUserById(userid);
												Map<String, Object> data = new HashMap<String, Object>();
												data.put("prize", user.getCurrentPrize());
												result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
											} else
												result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
										} else{
											result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_NOTENOUGHT_PRIZE);
											result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_NOTENOUGHT_PRIZE));
										}
									} else {
										result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_PRIZE_INVALID);
										result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_PRIZE_INVALID));
									}
								} else{
									result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_CELLPHONE_NOTEXISTS);
									result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_CELLPHONE_NOTEXISTS));
								}
							} else{
								result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_SELF);
								result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_SELF));
							}
						} else{
							result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_CELLPHONE_NOTEXISTS);
							result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_CELLPHONE_NOTEXISTS));
						}
					} else{
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_USERID);
						result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_USERID));
					}
				} else{
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_USERID);
					result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_USERID));
				}
			} else
				result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch(NotEnoughPrizeException e){
			if(logger.isErrorEnabled())
				logger.error("Exception in getPresents", e);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR_GIFT_NOTENOUGHT_PRIZE);
			result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_ERROR_GIFT_NOTENOUGHT_PRIZE));
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in getPresents", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		return result;
	}

	@AjaxMethod(path = "share.ajax")
	public Map<Object, Object> shareExchange(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			if(params.containsKey("userid") && params.containsKey("type") && params.containsKey("target") && params.containsKey("msg")){
				if(presentBiz.addShareExchange(NumberUtils.toLong(params.get("userid")), NumberUtils.toInt(params.get("type"), -1), NumberUtils.toInt(params.get("target"), -1), StringUtils.trimToEmpty(params.get("msg"))))
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
				else
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
			} else
				result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in shareExchange", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}

	@AjaxMethod(path = "present/list.ajax")
	public Map<Object, Object> getPresents(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			List<Present> presents = presentBiz.getAllPresents(NumberUtils.toLong(params.get("userid"), -1));
			result.put("data", parsePresents(presents));
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in getPresents", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}

	private List<Map<String, Object>> parsePresents(List<Present> presents) {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		
		if(presents != null && presents.size() > 0)
			for(Present present : presents){
				Map<String, Object> tmp = new HashMap<String, Object>();
				
				tmp.put("id", present.getId());
				tmp.put("name", present.getName());
				tmp.put("prize", present.getPrize());
				tmp.put("count", present.getCount());
				tmp.put("rank", present.getRank());
				tmp.put("type", present.getType());
				tmp.put("iconSmall", present.getIconSmall());
				tmp.put("iconBig", present.getIconBig());
				
				data.add(tmp);
			}
		
		return data;
	}

	public PresentBiz getPresentBiz() {
		return presentBiz;
	}

	public void setPresentBiz(PresentBiz presentBiz) {
		this.presentBiz = presentBiz;
	}

	public UserBiz getUserBiz() {
		return userBiz;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

}
