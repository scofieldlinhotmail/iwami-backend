package com.iwami.iwami.app.service.impl;

import com.iwami.iwami.app.dao.LogDao;
import com.iwami.iwami.app.model.Log;
import com.iwami.iwami.app.service.LogService;

public class LogServiceImpl implements LogService {
	
	private LogDao logDao;

	@Override
	public void log(Log log) {
		logDao.log(log);
	}

	public LogDao getLogDao() {
		return logDao;
	}

	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

}
