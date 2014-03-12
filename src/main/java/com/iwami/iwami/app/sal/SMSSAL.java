package com.iwami.iwami.app.sal;

public interface SMSSAL {

	public boolean sendVerifyCodeSMS(String cellPhone, String msg);

	public boolean sendInvitationSMS(String cellPhone, String name, long phone, int count);
}
