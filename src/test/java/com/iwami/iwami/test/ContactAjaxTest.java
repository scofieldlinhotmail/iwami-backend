package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.ContactAjax;

public class ContactAjaxTest extends TestCase {
	
	private boolean flag = false;
	
	@Test
	public void testGetContact(){
		if(flag){
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			ContactAjax ajax = ac.getBean("contactAjax", ContactAjax.class);
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getContact(params));
		}
	}

}
