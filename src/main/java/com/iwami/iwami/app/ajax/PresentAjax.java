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
import com.iwami.iwami.app.exception.NotEnoughPrizeException;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.util.IWamiUtils;

@AjaxClass
public class PresentAjax {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private PresentBiz presentBiz;
	
	private UserBiz userBiz;

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
