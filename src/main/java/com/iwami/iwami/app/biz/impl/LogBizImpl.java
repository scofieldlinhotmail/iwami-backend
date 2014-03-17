package com.iwami.iwami.app.biz.impl;

import com.iwami.iwami.app.biz.LogBiz;
import com.iwami.iwami.app.model.Log;
import com.iwami.iwami.app.service.LogService;

public class LogBizImpl implements LogBiz {
	
	private LogService logService;

	@Override
	public void log(Log log) {
		logService.log(log);
	}

	public LogService getLogService() {
		return logService;
	}

	public void setLogService(LogService logService) {
		this.logService = logService;
	}

}
