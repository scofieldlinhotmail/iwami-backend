package com.iwami.iwami.app.service;

public interface SMSService {
	
	public boolean sendVerifyCodeSMS(String cellPhone, String msg);

	public boolean sendInvitationSMS(String cellPhone, String name, long phone, int count);
}
