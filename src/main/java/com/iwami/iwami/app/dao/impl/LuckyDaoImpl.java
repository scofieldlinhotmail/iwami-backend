package com.iwami.iwami.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.iwami.iwami.app.constants.SqlConstants;
import com.iwami.iwami.app.dao.LuckyDao;
import com.iwami.iwami.app.model.LuckyConfig;
import com.iwami.iwami.app.model.LuckyHistory;
import com.iwami.iwami.app.model.LuckyRule;

public class LuckyDaoImpl extends JdbcDaoSupport implements LuckyDao {

	@Override
	public List<LuckyRule> getLuckyRules() {
		List<LuckyRule> result = getJdbcTemplate().query("select id, index_lev, gift, prob, lastmod_time, lastmod_userid from " + SqlConstants.TABLE_LUCKY_RULE + " where isdel = 0", new RowMapper<LuckyRule>() {

			@Override
			public LuckyRule mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LuckyRule draw = new LuckyRule();
				draw.setId(rs.getLong("id"));
				draw.setIndexLevel(rs.getInt("index_lev"));
				draw.setGift(rs.getString("gift"));
				draw.setProb(rs.getInt("prob"));
				draw.setLastmodTime(rs.getDate("lastmod_time"));
				draw.setLastmodUserid(rs.getLong("lastmod_userid"));
				return draw;
			}
		});
		return result;
	}

	@Override
	public boolean delLuckyRules(long id) {
		int count = getJdbcTemplate().update("update " + SqlConstants.TABLE_LUCKY_RULE + " set isdel = 1 where id = ?", new Object[]{id});
		if(count > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean addLuckyRule(LuckyRule rule) {
		int count = getJdbcTemplate().update("insert into " + SqlConstants.TABLE_LUCKY_RULE + "(index_lev, gift, prob, lastmod_time, lastmod_userid, isdel) values(?,?,?,now(),?,0)", 
				new Object[]{rule.getIndexLevel(), rule.getGift(), rule.getProb(), rule.getLastmodUserid()});
		if(count > 0)
			return true;
		else
			return false;
	}

	@Override
	public LuckyConfig getLuckyConfig() {
		List<LuckyConfig> configs = getJdbcTemplate().query("select id, count, prize, lastmod_time, lastmod_userid from " + SqlConstants.TABLE_LUCKY_CONFIG + " where isdel = 0 order by lastmod_time desc limit 1", new RowMapper<LuckyConfig>(){

			@Override
			public LuckyConfig mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LuckyConfig config = new LuckyConfig();
				config.setId(rs.getLong("id"));
				config.setCount(rs.getInt("count"));
				config.setPrize(rs.getInt("prize"));
				config.setLastmodTime(rs.getDate("lastmod_time"));
				config.setLastmodUserid(rs.getLong("lastmod_userid"));
				return config;
			}
			
		});
		if(configs != null && configs.size() > 0)
			return configs.get(0);
		else
			return null;
	}

	@Override
	public boolean delLuckyConfig(long id) {
		int count = getJdbcTemplate().update("update " + SqlConstants.TABLE_LUCKY_CONFIG + " set isdel = 1 where id = ?", new Object[]{id});
		if(count > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean addLuckyConfig(LuckyConfig config) {
		int count = getJdbcTemplate().update("insert into " + SqlConstants.TABLE_LUCKY_CONFIG + "(count, prize, lastmod_time, lastmod_userid, isdel) values(?,?,now(),?,0)", 
				new Object[]{config.getCount(), config.getPrize(), config.getLastmodUserid()});
		if(count > 0)
			return true;
		else
			return false;
	}

	@Override
	public boolean addLuckyHistory(LuckyHistory history) {
		System.out.println(history);
		int count = getJdbcTemplate().update("insert into " + SqlConstants.TABLE_LUCKY_HISTORY + "(userid, user_name, cell_phone, draw_id, draw_prize, gift, lastmod_time, lastmod_userid, isdel) values(?,?,?,?,?,?,now(),?,0)", 
				new Object[]{history.getUserid(), history.getUsername(), history.getCellPhone(), history.getDrawid(), history.getDrawPrize(), history.getGift(), history.getLastmodUserid()});
		if(count > 0)
			return true;
		else
			return false;
	}

	@Override
	public List<LuckyHistory> getAllLuckyHistory() {
		List<LuckyHistory> historys = getJdbcTemplate().query("select id, userid, user_name, cell_phone, draw_id, draw_prize, gift, lastmod_time, lastmod_userid from " + SqlConstants.TABLE_LUCKY_HISTORY + " where isdel = 0 order by id", new RowMapper<LuckyHistory>(){

			@Override
			public LuckyHistory mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				LuckyHistory history = new LuckyHistory();
				history.setId(rs.getLong("id"));
				history.setUserid(rs.getLong("userid"));
				history.setUsername(rs.getString("user_name"));
				history.setCellPhone(rs.getLong("cell_phone"));
				history.setDrawid(rs.getLong("draw_id"));
				history.setDrawPrize(rs.getInt("draw_prize"));
				history.setGift(rs.getString("gift"));
				history.setLastmodTime(rs.getDate("lastmod_time"));
				history.setLastmodUserid(rs.getLong("lastmod_userid"));
				return history;
			}
			
		});
		return historys;
	}

	@Override
	public int getLuckyCountByUserid(long userid) {
		return getJdbcTemplate().queryForInt("select count(1) from " + SqlConstants.TABLE_LUCKY_HISTORY + " where userid = ? and isdel = 0", new Object[]{userid});
	}

}
