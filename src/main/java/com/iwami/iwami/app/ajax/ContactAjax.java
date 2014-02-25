package com.iwami.iwami.app.ajax;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iwami.iwami.app.biz.ContactBiz;
import com.iwami.iwami.app.common.dispatch.AjaxClass;
import com.iwami.iwami.app.common.dispatch.AjaxMethod;
import com.iwami.iwami.app.constants.ErrorCodeConstants;
import com.iwami.iwami.app.model.Contact;

@AjaxClass
public class ContactAjax {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private ContactBiz contactBiz;

	@AjaxMethod(path = "contact.ajax")
	public Map<Object, Object> download(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			Contact contact = contactBiz.getContact();
			if(contact != null){
				result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
				result.put("phone1", contact.getPhone1());
				result.put("email1", contact.getEmail1());
				result.put("domain", contact.getDomain());
				result.put("qq", contact.getQq());
				result.put("qqgroup", contact.getQqgroup());
				result.put("phone2", contact.getPhone2());
				result.put("email2", contact.getEmail2());
			} else{
				result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
			}
				
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in contact", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}

	public ContactBiz getContactBiz() {
		return contactBiz;
	}

	public void setContactBiz(ContactBiz contactBiz) {
		this.contactBiz = contactBiz;
	}

}
