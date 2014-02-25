package com.iwami.iwami.app.service.impl;

import com.iwami.iwami.app.dao.WamiDao;
import com.iwami.iwami.app.service.WamiService;

public class WamiServiceImpl implements WamiService{
	
	private WamiDao wamiDao;

	public WamiDao getWamiDao() {
		return wamiDao;
	}

	public void setWamiDao(WamiDao wamiDao) {
		this.wamiDao = wamiDao;
	}
}
