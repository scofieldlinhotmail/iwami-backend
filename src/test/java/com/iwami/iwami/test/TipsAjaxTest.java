package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.TipsAjax;

public class TipsAjaxTest extends TestCase {
	
	private boolean flag = false;

	public void testGetTips(){
		if(flag){
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			TipsAjax ajax = ac.getBean("tipsAjax", TipsAjax.class);
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getTips(params));
		
			params.clear();
			params.put("type", "-22");
			System.out.println(ajax.getTips(params));
		
			params.clear();
			params.put("type", "0");
			System.out.println(ajax.getTips(params));
		
			params.clear();
			params.put("type", "1");
			System.out.println(ajax.getTips(params));
		
			params.clear();
			params.put("type", "2");
			System.out.println(ajax.getTips(params));
		}
	}
}
