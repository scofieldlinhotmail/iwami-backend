package com.iwami.iwami.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;

import com.iwami.iwami.app.sal.JPushSAL;
import com.iwami.iwami.app.service.PushService;

public class PushServiceImpl implements PushService {
	
	private JPushSAL pushSAL;
	// real jpush
	@Override
	public boolean pushUserMsg(String alias, String msg) {
		int status = pushSAL.sendCustomMessageWithAlias(genSendNo(), alias, msg, msg);
		
		return checkStatus(status);
	}
	
	private boolean checkStatus(int status) {
		return status == 0;
	}

	private int genSendNo(){
		return NumberUtils.toInt(new SimpleDateFormat("HHmmssS").format(new Date()));
	}

	public JPushSAL getPushSAL() {
		return pushSAL;
	}

	public void setPushSAL(JPushSAL pushSAL) {
		this.pushSAL = pushSAL;
	}

}
