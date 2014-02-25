package com.iwami.iwami.app.biz;

import com.iwami.iwami.app.exception.VerifyCodeMismatchException;
import com.iwami.iwami.app.model.User;

public interface UserBiz {
	
	public User getUserById(long userid);

	public boolean sendVerifyCode(long cellPhone);

	public User register(String uuid, String name, long cellPhone, String code) throws VerifyCodeMismatchException;

	public boolean modifyUserInfo4Register(User user);

}
