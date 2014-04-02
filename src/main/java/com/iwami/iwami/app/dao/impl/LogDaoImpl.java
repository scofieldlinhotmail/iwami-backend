package com.iwami.iwami.app.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.iwami.iwami.app.constants.SqlConstants;
import com.iwami.iwami.app.dao.LogDao;
import com.iwami.iwami.app.model.Log;

public class LogDaoImpl extends JdbcDaoSupport implements LogDao {

	@Override
	public void log(Log log) {
		getJdbcTemplate().update("insert into " + SqlConstants.TABLE_REQUEST_LOG + "(userid, type, msg, add_time) values(?, ?, ?, now())", new Object[]{log.getUserid(), log.getType(), log.getMsg()});
	}

}
