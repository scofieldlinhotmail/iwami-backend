package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.MonitorAjax;

public class MonitorAjaxTeset extends TestCase {
	
	private boolean flag = false;

	public void testMonitor(){
		if(flag){
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			MonitorAjax ajax = ac.getBean("monitorAjax", MonitorAjax.class);
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.monitor(params));
		}
	}
}
