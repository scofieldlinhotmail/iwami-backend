package com.iwami.iwami.app.report.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.HttpRequestHandler;

public class IndexServlet implements HttpRequestHandler {
	
	private Log logger = LogFactory.getLog(getClass());
	
    public static final int UA_TYPE_DUMBPHONE = 0;

    public static final int UA_TYPE_ANDROID = 1;

    public static final int UA_TYPE_IOS = 2;

    public static final int UA_TYPE_WINDOWS_PHONE_7 = 3;

    public static final int UA_TYPE_PC_BROWSER = 4;

    public static final int UA_TYPE_PC_IE = 5;

    public static final int UA_TYPE_SEARCH_ENGINE = 6;

    public static final int UA_TYPE_BAIDU_TRANSCODER = 7;

    public static final int UA_TYPE_IPAD = 8;

    public static final int UA_TYPE_SINA_WEIBO = 9;
	
	private Map<String, String> uaMap;
	
	private Map<String, Integer> types;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String params = request.getQueryString();
		try {
			String tmp = "empty";
			String ua = StringUtils.lowerCase(StringUtils.trimToEmpty(request.getHeader("User-Agent")));
			if(StringUtils.isNotBlank(ua)){
				if(uaMap != null && uaMap.size() > 0)
					for(String key : uaMap.keySet())
						if(ua.contains(key)){
							tmp = uaMap.get(key);
							break;
						}
			} else
				tmp = "unknown";
			
			int type = UA_TYPE_DUMBPHONE;
			if(types.containsKey(tmp))
				type = types.get(tmp);
			
			String url = "http://www.iwami.cn/wx";
	        switch (type) {
	            case UA_TYPE_ANDROID:
	            case UA_TYPE_IOS:
	            case UA_TYPE_IPAD:
	            case UA_TYPE_WINDOWS_PHONE_7:
	            case UA_TYPE_SEARCH_ENGINE:
	                break;
	            case UA_TYPE_PC_BROWSER:
	            case UA_TYPE_PC_IE:
	            	url = "http://www.iwami.cn/index.html";
	                break;
	            case UA_TYPE_SINA_WEIBO:
	            case UA_TYPE_DUMBPHONE:
	            case UA_TYPE_BAIDU_TRANSCODER:
	                break;
	            default:
	            	url = "http://www.iwami.cn/index.html";
	        }
			
        	if(StringUtils.isNotBlank(params))
        		url += ("?" + params);
	        
			logger.info("[UA]" + request.getHeader("User-Agent") + " " + url);
			
			response.sendRedirect(url);
		} catch (Throwable t) {
			logger.warn("index redirect error. Reason:", t);
			
			String url = "http://www.iwami.cn/index.html";
        	if(StringUtils.isNotBlank(params))
        		url += ("?" + params);
			response.sendRedirect(url);
		}
	}

	public void handleRequest(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public Map<String, String> getUaMap() {
		return uaMap;
	}

	public void setUaMap(Map<String, String> uaMap) {
		this.uaMap = uaMap;
	}

	public Map<String, Integer> getTypes() {
		return types;
	}

	public void setTypes(Map<String, Integer> types) {
		this.types = types;
	}
}
