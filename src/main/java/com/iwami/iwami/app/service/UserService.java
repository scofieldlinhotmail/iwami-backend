package com.iwami.iwami.app.service;

import java.util.Date;

import com.iwami.iwami.app.model.Code;
import com.iwami.iwami.app.model.User;

public interface UserService {

	public User getUserById(long id);
	
	public boolean subUserCurrentPrize(long userid, int prize);

	public boolean addCode(Code code);

	public Code getCode(long cellPhone, String code, Date start);

	public User getUserByCellPhone(long cellPhone);

	public boolean addUser4Register(User user);

	public boolean updateUser4Register(User user);

	public boolean modifyUserInfo4Register(User user);

	public void addUserCurrentPrize(long userid, int prize);

	public boolean updateUser4ExpressExchange(long userid, int allPrize, long cellPhone, String address, String name);
}
