package com.iwami.iwami.app.biz.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.iwami.iwami.app.biz.UserBiz;
import com.iwami.iwami.app.exception.VerifyCodeMismatchException;
import com.iwami.iwami.app.model.Code;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.service.SMSService;
import com.iwami.iwami.app.service.UserService;
import com.iwami.iwami.app.util.IWamiUtils;

public class UserBizImpl implements UserBiz {
	
	private int verifyCodeLength = 4;
	
	private UserService userService;
	
	private SMSService smsService;
	
	public User getUserById(long userid){
		return userService.getUserById(userid);
	}

	@Override
	public User getUserByCellPhone(long cellPhone) {
		return userService.getUserByCellPhone(cellPhone);
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public boolean sendVerifyCode(long cellPhone) {
		boolean result = false;
		
		if(cellPhone > 0){
			String code = IWamiUtils.getRandInt(verifyCodeLength);
			Code _code = new Code();
			_code.setCellPhone(cellPhone);
			_code.setCode(code);
			if(userService.addCode(_code)){
				if(smsService.sendVerifyCodeSMS("" + cellPhone, code))
					result = true;
				else
					throw new RuntimeException("Error in sending code to cell phone.");
			} else
				throw new RuntimeException("Error in saving code into db.");
		}
		return result;
	}

	@Override
	public boolean sendSMS(long cellPhone, User user, int count) {
		boolean result = false;
		
		if(cellPhone > 0){
			if(smsService.sendInvitationSMS("" + cellPhone, user.getName(), user.getCellPhone(), count))
				result = true;
			else
				throw new RuntimeException("Error in sending code to cell phone.");
		}
		return result;
	}

	@Override
	public User register(String uuid, String name, long cellPhone, String alias, String code) throws VerifyCodeMismatchException {
		// reset expire ms
		Code _code = userService.getCode(cellPhone, code, DateUtils.addMinutes(new Date(), -5));
		if(_code != null && _code.getCellPhone() == cellPhone && StringUtils.equals(_code.getCode(), code)){
			User user = userService.getUserByCellPhone(cellPhone);
			
			if(user == null){
				user = new User();
				user.setCellPhone(cellPhone);
				user.setName(name);
				user.setUuid(uuid);
				user.setAlias(alias);
				userService.addUser4Register(user);
			} else{
				user.setCellPhone(cellPhone);
				user.setName(name);
				user.setUuid(uuid);
				user.setAlias(alias);
				userService.updateUser4Register(user);
			}
			
			return user;
		} else
			throw new VerifyCodeMismatchException();
	}

	@Override
	public boolean modifyUserInfo4Register(User user) {
		return userService.modifyUserInfo4Register(user);
	}

	public int getVerifyCodeLength() {
		return verifyCodeLength;
	}

	public void setVerifyCodeLength(int verifyCodeLength) {
		this.verifyCodeLength = verifyCodeLength;
	}

	public SMSService getSmsService() {
		return smsService;
	}

	public void setSmsService(SMSService smsService) {
		this.smsService = smsService;
	}
}
