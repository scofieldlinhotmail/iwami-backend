package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.StrategyAjax;

public class StrategyAjaxTest extends TestCase {

	private boolean flag = false;
	
	public void testGetStrategyList(){
		if(flag){
			StrategyAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getStrategyList(params));
		}
	}
	
	public void testRateStrategy(){
		if(flag){
			StrategyAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.rateStrategy(params));
			
			params.put("id", "5");
			System.out.println(ajax.rateStrategy(params));
			
			params.put("uuid", "121");
			System.out.println(ajax.rateStrategy(params));
			
			System.out.println(ajax.rateStrategy(params));
			
			params.put("id", "6");
			System.out.println(ajax.rateStrategy(params));
		}
	}
	
	public void testGetStrategyDetail(){
		if(flag){
			StrategyAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getStrategyDetail(params));
			
			params.put("id", "5");
			System.out.println(ajax.getStrategyDetail(params));
			
			params.put("start", "0");
			System.out.println(ajax.getStrategyDetail(params));
			
			params.put("step", "5");
			System.out.println(ajax.getStrategyDetail(params));
			
			params.put("start", "5");
			System.out.println(ajax.getStrategyDetail(params));
		}
	}

	private StrategyAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("strategyAjax", StrategyAjax.class);
	}
}
