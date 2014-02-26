package com.iwami.iwami.app.biz.impl;

import java.util.List;

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
		}
		
		presentService.updateExchangeStatus(exchangeid, status);
		// jpush TODO
		return true;
	}

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
