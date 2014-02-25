package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.ApkAjax;

import junit.framework.TestCase;

public class ApkAjaxTest extends TestCase {

	private boolean flag = false;
	
	@Test
	public void testDownload(){
		if(flag){
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			ApkAjax ajax = ac.getBean("apkAjax", ApkAjax.class);
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.download(params));
		}
	}
	
	@Test
	public void testCheckUpdate(){
		if(flag){
			ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
			ApkAjax ajax = ac.getBean("apkAjax", ApkAjax.class);
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.checkUpdate(params));
			
			params.clear();
			params.put("version", "0.2");
			System.out.println(ajax.checkUpdate(params));
			params.clear();
			params.put("version", "0.2.1");
			System.out.println(ajax.checkUpdate(params));

			params.clear();
			params.put("version", "1.2");
			System.out.println(ajax.checkUpdate(params));
			params.clear();
			params.put("version", "1.2.3");
			System.out.println(ajax.checkUpdate(params));
		}
	}
}
