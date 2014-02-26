package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.TaskAjax;

public class TaskAjaxTest extends TestCase {
	
	private boolean flag = false;
	
	@Test
	public void testGetTops(){
		if(flag){
			TaskAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getTops(params));
			
			params.put("userid", "10");
			System.out.println(ajax.getTops(params));
		}
	}

	@Test
	public void testGetShareTasks(){
		if(flag){
			TaskAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getShareTasks(params));
		}
	}

	@Test
	public void testGetTreasureTasks(){
		if(flag){
			TaskAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getTreasureTasks(params));
			
			params.put("userid", "10");
			System.out.println(ajax.getTreasureTasks(params));
		}
	}

	@Test
	public void testGetWamiTasks(){
		if(flag){
			TaskAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getWamiTasks(params));
			
			params.put("userid", "10");
			System.out.println(ajax.getWamiTasks(params));
		}
	}

	private TaskAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("taskAjax", TaskAjax.class);
	}

}
