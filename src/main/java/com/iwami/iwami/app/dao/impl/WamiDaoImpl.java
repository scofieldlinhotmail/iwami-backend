package com.iwami.iwami.app.dao.impl;

import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.iwami.iwami.app.constants.SqlConstants;
import com.iwami.iwami.app.dao.WamiDao;

public class WamiDaoImpl extends JdbcDaoSupport implements WamiDao {

	@Override
	public boolean uploadStatus(int userid, long taskid, int type, long time) {
		String sql ="insert into "+ SqlConstants.TABLE_WAMI +" set where isdel = 0  and type = ? order by lastmod_time desc  limit 1";
		
		boolean result = false;
/*		List<Wami> list = getJdbcTemplate().query(sql,new RowMapper<Wami>(){
			@Override 
			public Wami mapRow(ResultSet rs, int index) throws SQLException {
				Wami wami = new Wami();

				return wami;
			}
		});*/
			return result;
	} 
}
