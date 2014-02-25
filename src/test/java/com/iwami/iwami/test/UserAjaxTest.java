package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.UserAjax;

public class UserAjaxTest extends TestCase {
	
	private boolean flag = false;
	
	public void testSendVerifyCode(){
		if(flag){
			UserAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.sendVerifyCode(params));
			
			params.put("cellPhone", "121212");
			System.out.println(ajax.sendVerifyCode(params));
			
			params.put("cellPhone", "18611007601");
			System.out.println(ajax.sendVerifyCode(params));
		}
	}
	
	public void testRegister(){
		if(flag){
			UserAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.register(params));
			
			params.put("cellPhone", "18611007601");
			System.out.println(ajax.register(params));
			
			params.put("name", "name1");
			System.out.println(ajax.register(params));
			
			params.put("uuid", "dfadsfdsfdsfds");
			System.out.println(ajax.register(params));
			
			params.put("alias", "aliasdfdsfsd");
			System.out.println(ajax.register(params));
			
			params.put("code", "1212");
			System.out.println(ajax.register(params));
			
			params.put("code", "0635");
			System.out.println(ajax.register(params));
	
			params.put("uuid", "222222");
			params.put("alias", "2222");
			params.put("cellPhone", "18611007601");
			params.put("name", "name2");
			System.out.println(ajax.register(params));
		}
	}
	
	public void testModifyUser(){
		if(flag){
			UserAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("userid", "9");
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("userid", "10");
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("age", "-1");
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("age", "26");
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("gender", "0");
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("job", "dfdfd");
			System.out.println(ajax.modifyUserInfo(params));
			
			params.put("address", "dfdfddf");
			System.out.println(ajax.modifyUserInfo(params));
		}
	}
	
	public void testGetUserInfo(){
		if(true){
			UserAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getUserinfo(params));
			
			params.put("userid", "232");
			System.out.println(ajax.getUserinfo(params));
			
			params.put("userid", "10");
			System.out.println(ajax.getUserinfo(params));
		}
	}

	private UserAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("userAjax", UserAjax.class);
	}

}
