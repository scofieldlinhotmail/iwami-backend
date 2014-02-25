package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.OnstartAjax;

public class OnstartAjaxTest extends TestCase {
	
	private boolean flag = false;
	
	public void testOnstart(){
		if(flag){
			OnstartAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.onstart(params));
			
			params.put("userid", "-1");
			System.out.println(ajax.onstart(params));
			
			params.put("cellPhone", "-1");
			System.out.println(ajax.onstart(params));
			
			params.put("uuid", "uuuuuuuuiididididid");
			System.out.println(ajax.onstart(params));
			
			params.put("gps", "dfdsfadsfasdfasdfasdf");
			System.out.println(ajax.onstart(params));
			
			params.put("alias", "fadfadfasdfdsafasdf");
			System.out.println(ajax.onstart(params));
			
			params.put("version", "1.0.0");
			System.out.println(ajax.onstart(params));
			
			params.put("type", "0");
			System.out.println(ajax.onstart(params));
			
			params.put("time", "1212312312312");
			System.out.println(ajax.onstart(params));

		}
	}

	private OnstartAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("onstartAjax", OnstartAjax.class);
	}

}
