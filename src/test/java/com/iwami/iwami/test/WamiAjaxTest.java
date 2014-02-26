package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.WamiAjax;

public class WamiAjaxTest extends TestCase {
	
	private boolean flag = false;
	
	@Test
	public void testShareUpload(){
		if(flag){
			WamiAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.shareUpload(params));
			
			params.put("taskid", "232323232");
			System.out.println(ajax.shareUpload(params));
			
			params.put("userid", "23232");
			System.out.println(ajax.shareUpload(params));
			
			params.put("time", "121212121212");
			System.out.println(ajax.shareUpload(params));
			
			params.put("userid", "10");
			System.out.println(ajax.shareUpload(params));
			
			params.put("taskid", "5");
			System.out.println(ajax.shareUpload(params));
			
			System.out.println(ajax.shareUpload(params));
		}
	}

	@Test
	public void testStatusUpload(){
		if(true){
			WamiAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.statusUpload(params));
			
			params.put("userid", "23232");
			System.out.println(ajax.statusUpload(params));
			
			params.put("taskid", "232323232");
			System.out.println(ajax.statusUpload(params));
			
			params.put("type", "0");
			System.out.println(ajax.statusUpload(params));
			
			params.put("time", "121212121212");
			System.out.println(ajax.statusUpload(params));
			
			params.put("userid", "10");
			System.out.println(ajax.statusUpload(params));
			
			params.put("taskid", "20");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "1");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "0");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "2");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "3");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "4");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "5");
			System.out.println(ajax.statusUpload(params));

			params.put("type", "2");
			System.out.println(ajax.statusUpload(params));
		}
	}

	private WamiAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("wamiAjax", WamiAjax.class);
	}

}
