package com.iwami.iwami.app.biz.impl;

import com.iwami.iwami.app.biz.WamiBiz;
import com.iwami.iwami.app.service.WamiService;

public class WamiBizImpl  implements WamiBiz {
	
	private WamiService wamiService;



	@Override
	public boolean getIdStatus(long taskid) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public WamiService getWamiService() {
		return wamiService;
	}

	public void setWamiService(WamiService wamiService) {
		this.wamiService = wamiService;
	}
}
