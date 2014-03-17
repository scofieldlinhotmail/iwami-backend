package com.iwami.iwami.app.ajax;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iwami.iwami.app.biz.LogBiz;
import com.iwami.iwami.app.biz.TaskBiz;
import com.iwami.iwami.app.common.dispatch.AjaxClass;
import com.iwami.iwami.app.common.dispatch.AjaxMethod;
import com.iwami.iwami.app.constants.ErrorCodeConstants;
import com.iwami.iwami.app.model.Task;
import com.iwami.iwami.app.model.TreasureConfig;

@AjaxClass
public class TaskAjax {
	
	private Log logger = LogFactory.getLog(getClass());
	
	private TaskBiz taskBiz;
	
	private LogBiz logBiz;
	
	@AjaxMethod(path = "wami/share.ajax")
	public Map<Object, Object> getShareTasks(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			List<Task> tasks = taskBiz.getShareTasks();
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
			result.put("data", parseShareTasks(tasks));
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in top.ajax", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}

	private List<Map<String, Object>> parseShareTasks(List<Task> tasks) {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		
		if(tasks != null && tasks.size() > 0)
			for(Task task : tasks)
				if(task != null){
					Map<String, Object> tmp = new HashMap<String, Object>();
					
					tmp.put("id", task.getId());
					tmp.put("name", task.getName());
					tmp.put("rank", task.getRank());
					tmp.put("intr", task.getIntr());
					tmp.put("prize", task.getPrize());
					tmp.put("currentprize", task.getCurrentPrize());
					tmp.put("icon", task.getIconSmall());
					
					data.add(tmp);
				}
		
		return data;
	}
	
	@AjaxMethod(path = "wami/treasure.ajax")
	public Map<Object, Object> getTreasureTasks(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			long userid = NumberUtils.toLong(params.get("userid"), -1);
			List<Task> tasks = taskBiz.getTreasureTasks(userid);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
			result.put("data", parseTasks(tasks));
			try{
				com.iwami.iwami.app.model.Log log = new com.iwami.iwami.app.model.Log();
				log.setUserid(userid);
				log.setType(com.iwami.iwami.app.model.Log.TYPE_TASK_TREASURE);
				logBiz.log(log);
			} catch(Throwable t){
				t.printStackTrace();
			}
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in top.ajax", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}
	
	@AjaxMethod(path = "wami/tasks.ajax")
	public Map<Object, Object> getWamiTasks(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			long userid = NumberUtils.toLong(params.get("userid"), -1);
			List<List<Task>> tasks = taskBiz.getWamiTasks(userid);
			result.put("new", parseTasks(tasks.get(0)));
			result.put("ongoing", parseTasks(tasks.get(1)));
			result.put("done", parseTasks(tasks.get(2)));
			
			TreasureConfig config = taskBiz.getTreasureConfig();
			int days = -1;
			int count = -1;
			if(config != null){
				days = config.getDays();
				count = config.getCount();
			}
			
			result.put("days", days);
			result.put("count", count);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
			try{
				com.iwami.iwami.app.model.Log log = new com.iwami.iwami.app.model.Log();
				log.setUserid(userid);
				log.setType(com.iwami.iwami.app.model.Log.TYPE_TASK_TASK);
				logBiz.log(log);
			} catch(Throwable t){
				t.printStackTrace();
			}
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in top.ajax", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}
	
	@AjaxMethod(path = "top.ajax")
	public Map<Object, Object> getTops(Map<String, String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		try{
			long userid = NumberUtils.toLong(params.get("userid"), -1);
			List<Task> tasks = taskBiz.getTopTasks(userid);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
			result.put("data", parseTasks(tasks));
			result.put("time", getLatestTime(tasks));
			try{
				com.iwami.iwami.app.model.Log log = new com.iwami.iwami.app.model.Log();
				log.setUserid(userid);
				log.setType(com.iwami.iwami.app.model.Log.TYPE_TASK_TOP);
				logBiz.log(log);
			} catch(Throwable t){
				t.printStackTrace();
			}
		} catch(Throwable t){
			if(logger.isErrorEnabled())
				logger.error("Exception in top.ajax", t);
			result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_ERROR);
		}
		
		
		return result;
	}

	private long getLatestTime(List<Task> tasks) {
		long result = 0;
		
		if(tasks != null && tasks.size() > 0)
			for(Task task : tasks)
				if(task != null && task.getLastModTime() != null){
					long tmp = task.getLastModTime().getTime();
					if(tmp > result)
						result = task.getLastModTime().getTime();
				}
		
		return result;
	}

	private List<Map<String, Object>> parseTasks(List<Task> tasks) {
		List<Map<String, Object>> data = new ArrayList<Map<String,Object>>();
		
		if(tasks != null && tasks.size() > 0)
			for(Task task : tasks)
				if(task != null){
					Map<String, Object> tmp = new HashMap<String, Object>();
					
					tmp.put("id", task.getId());
					tmp.put("name", task.getName());
					tmp.put("rank", task.getRank());
					tmp.put("intr", task.getIntr());
					tmp.put("appintr", task.getAppIntr());
					tmp.put("prize", task.getPrize());
					tmp.put("url", task.getUrl());
					tmp.put("available", task.getAvailable());
					tmp.put("background", task.getBackground());
					tmp.put("register", task.getRegister());
					tmp.put("time", task.getTime());
					tmp.put("iconSmall", task.getIconSmall());
					tmp.put("iconBig", task.getIconBig());
					tmp.put("status", task.getStatus());
					
					data.add(tmp);
				}
		
		return data;
	}

	public TaskBiz getTaskBiz() {
		return taskBiz;
	}

	public void setTaskBiz(TaskBiz taskBiz) {
		this.taskBiz = taskBiz;
	}

	public LogBiz getLogBiz() {
		return logBiz;
	}

	public void setLogBiz(LogBiz logBiz) {
		this.logBiz = logBiz;
	}

}
