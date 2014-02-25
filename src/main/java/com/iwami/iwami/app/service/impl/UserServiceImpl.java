package com.iwami.iwami.app.service.impl;

import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import com.iwami.iwami.app.dao.UserDao;
import com.iwami.iwami.app.model.Code;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.service.UserService;

public class UserServiceImpl implements UserService {
	
	private UserDao userDao;

	@Override
	public User getUserById(long id) {
		return userDao.getUserById(id);
	}

	@Override
	public boolean subUserCurrentPrize4Draw(long userid, int prize) {
		return userDao.subUserCurrentPrize4Draw(userid, prize);
	}

	@Override
	public boolean addCode(Code code) {
		return userDao.addCode(code);
	}

	@Override
	public Code getCode(long cellPhone, String code, Date start) {
		return userDao.getCode(cellPhone, code, start);
	}

	@Override
	public User getUserByCellPhone(long cellPhone) {
		return userDao.getUserByCellPhone(cellPhone);
	}

	@Override
	@Transactional(rollbackFor=Exception.class, value="txManager")
	public boolean addUser4Register(User user) {
		if(userDao.newUser4Register(user) && userDao.addUserInfo4Register(user))
			return true;
		else
			throw new RuntimeException("Error in saving user into db.");
	}

	@Override
	@Transactional(rollbackFor=Exception.class, value="txManager")
	public boolean updateUser4Register(User user) {
		if(userDao.updateUser4Register(user))
			return true;
		else
			throw new RuntimeException("Error in updating user into db.");
	}

	@Override
	public boolean modifyUserInfo4Register(User user) {
		return userDao.modifyUserInfo4Register(user);
	}

	public UserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

}
