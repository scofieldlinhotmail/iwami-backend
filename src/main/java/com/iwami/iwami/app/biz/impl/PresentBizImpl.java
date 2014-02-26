package com.iwami.iwami.app.biz.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.annotation.Transactional;

import com.iwami.iwami.app.biz.PresentBiz;
import com.iwami.iwami.app.exception.NotEnoughPrizeException;
import com.iwami.iwami.app.model.Exchange;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.Share;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.service.PresentService;
import com.iwami.iwami.app.service.UserService;

public class PresentBizImpl implements PresentBiz {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private PresentService presentService;
	
	private UserService userService;

	@Override
	public List<Present> getAllPresents(long userid) {
		return presentService.getAllAvailablePresents();
	}

	@Override
	public boolean gift(User user, User user2, int prize) throws NotEnoughPrizeException {
		Exchange exchange = new Exchange();
		exchange.setUserid(user.getId());
		exchange.setPresentId(user2.getId());
		exchange.setPresentName("赠" + user2.getName());
		exchange.setPresentPrize(-1);
		exchange.setPresentType(Present.TYPE_GIFT);
		exchange.setCount(-1);
		exchange.setPrize(prize);
		exchange.setStatus(Exchange.STATUS_NEW);
		exchange.setLastModUserid(user.getId());
		
		long exchangeid = presentService.addExchange(exchange);
		int status = Exchange.STATUS_FAILED;
		
		try{
			doGift(user, user2, prize);
			status = Exchange.STATUS_FINISH;
		} catch(NotEnoughPrizeException e){
			if(logger.isErrorEnabled())
				logger.error("exception in gift from " + user.getId() + " to " + user2.getId() + " <" + prize + ">", e);
			throw e;
		} finally{
			presentService.updateExchangeStatus(exchangeid, status);
		}
		
		// jpush TODO
		return true;
	}

	@Override
	@Transactional(rollbackFor=Exception.class, value="txManager")
	public void doGift(User user, User user2, int prize) throws NotEnoughPrizeException {
		if(userService.subUserCurrentPrize(user.getId(), prize))
			userService.addUserCurrentPrize(user2.getId(), prize);
		else
			throw new NotEnoughPrizeException();
	}

	@Override
	public boolean addShareExchange(long userid, int type, int target, String msg) {
		Share share = new Share();
		share.setUserid(userid);
		share.setType(type);
		share.setTarget(target);
		share.setMsg(msg);
		return presentService.addShareExchange(share);
	}

	@Override
	public Map<Long, Present> getPresentsByIds(List<Long> ids) {
		return presentService.getPresentsByIds(ids);
	}

	@Override
	public boolean exchangeExpress(User user, Map<Present, Integer> presentCnts, long cellPhone, String address, String name) throws NotEnoughPrizeException {

		int allPrize = 0;
		List<Exchange> exchanges = new ArrayList<Exchange>();
		for(Present present : presentCnts.keySet()){
			Exchange exchange = new Exchange();
			exchange.setUserid(user.getId());
			exchange.setPresentId(present.getId());
			exchange.setPresentName(present.getName());
			exchange.setPresentPrize(present.getPrize());
			exchange.setPresentType(present.getType());
			int count = presentCnts.get(present);
			exchange.setCount(count);
			int prize = present.getPrize() * count;
			exchange.setPrize(prize);
			allPrize += prize;
			exchange.setStatus(Exchange.STATUS_NEW);
			exchange.setCellPhone(cellPhone);
			exchange.setAddress(address);
			exchange.setName(name);
			exchange.setLastModUserid(user.getId());
			exchanges.add(exchange);
		}
		
		List<Long> ids = new ArrayList<Long>();
		for(Exchange exchange : exchanges)
			ids.add(presentService.addExchange(exchange));
		
		int status = Exchange.STATUS_FAILED;
		if(userService.updateUser4ExpressExchange(user.getId(), allPrize, cellPhone, address, name))
			status = Exchange.STATUS_FINISH;
		
		presentService.updateExchangesStatus(ids, status);
		
		return status == Exchange.STATUS_FINISH;
	}

	public PresentService getPresentService() {
		return presentService;
	}

	public void setPresentService(PresentService presentService) {
		this.presentService = presentService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}