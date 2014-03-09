package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.LuckyAjax;

public class LuckyAjaxTest extends TestCase {

	private boolean flag = false;
	
	public void testGetLuckyRules(){
		if(flag){
			LuckyAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getRules(params));
		}
	}
	
	public void testDraw(){
		if(flag){
			LuckyAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.draw(params));
			
			params.put("userid", "1212");
			System.out.println(ajax.draw(params));
			
			params.put("userid", "10");
			System.out.println(ajax.draw(params));
		}
	}

	private LuckyAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("luckyAjax", LuckyAjax.class);
	}
	
}
