package com.iwami.iwami.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.iwami.iwami.app.constants.SqlConstants;
import com.iwami.iwami.app.dao.TipsDao;
import com.iwami.iwami.app.model.Tips;

public class TipsDaoImpl extends JdbcDaoSupport implements TipsDao{

	@Override
	public List<Tips> getAllTips() {
		String sql = "select id, type, content, lastmod_time, lastmod_userid from "+ SqlConstants.TABLE_TIPS +" where isdel = 0";
		return getJdbcTemplate().query(sql, new RowMapper<Tips>(){
			@Override
			public Tips mapRow(ResultSet rs, int index) throws SQLException {
				Tips tips  =  new Tips();
				tips.setId(rs.getLong("id"));
				tips.setType(rs.getInt("type"));
				tips.setContent(rs.getString("content"));
				Timestamp ts = rs.getTimestamp("lastmod_time");
				if(ts != null)
					tips.setLastmodTime(new Date(ts.getTime()));
				tips.setLastmodUserid(rs.getLong("lastmod_userid"));
				return tips;
			}
		});
	}
}
