package com.iwami.iwami.app.service.impl;

import com.iwami.iwami.app.sal.SMSSAL;
import com.iwami.iwami.app.service.SMSService;

public class SMSServiceImpl implements SMSService {
	
	private SMSSAL smsSAL;

	@Override
	public boolean sendVerifyCodeSMS(String cellPhone, String msg) {
		return smsSAL.sendVerifyCodeSMS(cellPhone, msg);
	}

	@Override
	public boolean sendInvitationSMS(String cellPhone, String name, long phone, int count) {
		return smsSAL.sendInvitationSMS(cellPhone, name, phone, count);
	}

	public SMSSAL getSmsSAL() {
		return smsSAL;
	}

	public void setSmsSAL(SMSSAL smsSAL) {
		this.smsSAL = smsSAL;
	}

}
