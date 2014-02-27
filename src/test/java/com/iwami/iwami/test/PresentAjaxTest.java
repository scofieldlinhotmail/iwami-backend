package com.iwami.iwami.test;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.iwami.iwami.app.ajax.PresentAjax;

public class PresentAjaxTest extends TestCase {
	
	private boolean flag = false;
	
	public void testSendGift(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.sendGift(params));

			params.put("userid", "12121");
			System.out.println(ajax.sendGift(params));

			params.put("cellPhone", "1860116583293");
			System.out.println(ajax.sendGift(params));

			params.put("prize", "220");
			System.out.println(ajax.sendGift(params));

			params.put("userid", "10");
			System.out.println(ajax.sendGift(params));

			params.put("cellPhone", "18611007601");
			System.out.println(ajax.sendGift(params));

			params.put("cellPhone", "18601165893");
			System.out.println(ajax.sendGift(params));

			params.put("prize", "-1");
			System.out.println(ajax.sendGift(params));

			params.put("prize", "0");
			System.out.println(ajax.sendGift(params));

			params.put("prize", "10");
			System.out.println(ajax.sendGift(params));
		}
	}
	
	public void testSendSMS(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.sendSMS(params));

			params.put("userid", "12121");
			System.out.println(ajax.sendSMS(params));

			params.put("cellPhone", "1860116583293");
			System.out.println(ajax.sendSMS(params));

			params.put("userid", "10");
			System.out.println(ajax.sendSMS(params));

			params.put("cellPhone", "18611007601");
			System.out.println(ajax.sendSMS(params));

			params.put("cellPhone", "18601165893");
			System.out.println(ajax.sendSMS(params));

			params.put("cellPhone", "18601165895");
			System.out.println(ajax.sendSMS(params));
		}
	}
	
	public void testShareExchange(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.shareExchange(params));
			
			params.put("userid", "10");
			System.out.println(ajax.shareExchange(params));
			
			params.put("type", "2");
			System.out.println(ajax.shareExchange(params));
			
			params.put("target", "2");
			System.out.println(ajax.shareExchange(params));
			
			params.put("msg", "msg");
			System.out.println(ajax.shareExchange(params));
		}
	}
	
	public void testGetPresents(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getPresents(params));
		}
	}
	
	public void testExchangeAlipayPresent(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("userid", "1212");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("id", "232123");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("aliAccount", "scofieldlin@Hotmail.com");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("prize", "12");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("userid", "10");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("id", "6");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("prize", "51");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("prize", "1001");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("prize", "200000");
			System.out.println(ajax.exchangeAlipayPresent(params));
			
			params.put("prize", "1000");
			System.out.println(ajax.exchangeAlipayPresent(params));
		}
	}
	
	public void testExchangeBankPresent(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("userid", "1212");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("id", "232123");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("bankAccount", "name");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("bankName", "name");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("bankNo", "121212312");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("prize", "12");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("userid", "10");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("id", "8");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("prize", "51");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("prize", "1001");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("prize", "200000");
			System.out.println(ajax.exchangeBankPresent(params));
			
			params.put("prize", "1000");
			System.out.println(ajax.exchangeBankPresent(params));
		}
	}
	
	public void testExchangeMobilePresent(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("userid", "1212");
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("id", "232123");
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("cellPhone", "121212312");
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("cellPhone", "18611007601");
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("userid", "10");
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("id", "8");
			System.out.println(ajax.exchangeMobilePresent(params));
			
			params.put("id", "4");
			System.out.println(ajax.exchangeMobilePresent(params));
			
		}
	}
	
	public void testExchangeExpressPresents(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("userid", "1212");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("ids", "1212,23,2,32");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("counts", "1,2,3");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("address", "fdfd");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("name", "fdfd");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("cellPhone", "12121212");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("counts", "1,2,3,3");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("ids", "2");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("counts", "4");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("cellPhone", "18611007601");
			System.out.println(ajax.exchangeExpressPresents(params));
			
			params.put("userid", "10");
			System.out.println(ajax.exchangeExpressPresents(params));
			
		}
	}
	
	public void testExchangeOfflinePresents(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("userid", "1212");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("ids", "1212,23,2,32");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("counts", "1,2,3");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("channel", "12121");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("counts", "1,2,3,3");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("ids", "2");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("counts", "4");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("userid", "10");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("ids", "10,12");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
			params.put("counts", "4,2");
			System.out.println(ajax.exchangeOfflinePresents(params));
			
		}
	}
	
	public void testGetExchangeHistory(){
		if(flag){
			PresentAjax ajax = getAjax();
			Map<String, String> params = new HashMap<String, String>();
			System.out.println(ajax.getExchangeHistory(params));

			params.put("userid", "101");
			System.out.println(ajax.getExchangeHistory(params));

			params.put("userid", "11");
			System.out.println(ajax.getExchangeHistory(params));

			params.put("userid", "10");
			System.out.println(ajax.getExchangeHistory(params));
		}
	}

	private PresentAjax getAjax() {
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		return ac.getBean("presentAjax", PresentAjax.class);
	}

}
