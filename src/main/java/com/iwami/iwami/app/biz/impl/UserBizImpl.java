package com.iwami.iwami.app.biz.impl;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

import com.iwami.iwami.app.biz.UserBiz;
import com.iwami.iwami.app.exception.VerifyCodeMismatchException;
import com.iwami.iwami.app.model.Code;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.service.UserService;
import com.iwami.iwami.app.util.IWamiUtils;
import com.iwami.iwami.app.util.SMSUtils;

public class UserBizImpl implements UserBiz {
	
	private UserService userService;
	
	private int verifyCodeLength = 4;
	
	public User getUserById(long userid){
		return userService.getUserById(userid);
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
			Code codeO = new Code();
			codeO.setCellPhone(cellPhone);
			codeO.setCode(code);
			if(userService.addCode(codeO)){
				//TODO modify sms content
				if(SMSUtils.sendLuosiMao("验证码为" + code + "，5分钟内有效，祝您选餐开心【iwami】", "" + cellPhone))
					result = true;
				else
					throw new RuntimeException("Error in sending code to cell phone.");
			} else
				throw new RuntimeException("Error in saving code into db.");
		}
		return result;
	}

	@Override
	public User register(String uuid, String name, long cellPhone, String code) throws VerifyCodeMismatchException {
		// TODO reset expire ms
		Code codeO = userService.getCode(cellPhone, code, DateUtils.addMinutes(new Date(), -5));
		if(codeO != null && codeO.getCellPhone() == cellPhone && StringUtils.equals(codeO.getCode(), code)){
			User user = userService.getUserByCellPhone(cellPhone);
			
			if(user == null){
				user = new User();
				user.setCellPhone(cellPhone);
				user.setName(name);
				user.setUuid(uuid);
				userService.addUser4Register(user);
			} else{
				user.setCellPhone(cellPhone);
				user.setName(name);
				user.setUuid(uuid);
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
}
