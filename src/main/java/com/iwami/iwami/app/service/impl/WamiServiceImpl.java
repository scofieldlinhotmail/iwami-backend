package com.iwami.iwami.app.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.iwami.iwami.app.dao.WamiDao;
import com.iwami.iwami.app.model.Wami;
import com.iwami.iwami.app.service.WamiService;

public class WamiServiceImpl implements WamiService{
	
	private WamiDao wamiDao;

	@Override
	public Wami getLatestWami(long userid, long taskid) {
		return wamiDao.getLatestWami(userid, taskid);
	}

	@Override
	public Wami getWamiByType(long userid, long taskid, int type) {
		return wamiDao.getWamiByType(userid, taskid, type);
	}

	@Override
	public Map<Long, Wami> getLatestWamis(long userid, List<Long> taskids) {
		return wamiDao.getLatestWami(userid, taskids);
	}

	@Override
	public void newWami(Wami wami) {
		wamiDao.newWami(wami);
	}

	@Override
	public Map<Long, Wami> getDoneTaskIds(long userid/*, Date start*/) {
		return wamiDao.getDoneTaskIds(userid/*, start*/);
	}

	@Override
	public Map<Long, Wami> getOngoingWami(long userid) {
		return wamiDao.getOngoingWami(userid);
	}

	@Override
	public List<Wami> getWamiHistory(long userid, int status) {
		return wamiDao.getWamiHistory(userid, status);
	}

	public WamiDao getWamiDao() {
		return wamiDao;
	}

	public void setWamiDao(WamiDao wamiDao) {
		this.wamiDao = wamiDao;
	}
}
