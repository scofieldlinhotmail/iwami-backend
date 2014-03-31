package com.iwami.iwami.app.ajax;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.iwami.iwami.app.biz.TaskBiz;
import com.iwami.iwami.app.biz.UserBiz;
import com.iwami.iwami.app.biz.WamiBiz;
import com.iwami.iwami.app.common.dispatch.AjaxClass;
import com.iwami.iwami.app.common.dispatch.AjaxMethod;
import com.iwami.iwami.app.constants.ErrorCodeConstants;
import com.iwami.iwami.app.exception.TaskFinishedException;
import com.iwami.iwami.app.exception.TaskNotExistsException;
import com.iwami.iwami.app.exception.TaskRepeatStartException;
import com.iwami.iwami.app.exception.TaskUnavailableException;
import com.iwami.iwami.app.exception.TaskWamiedException;
import com.iwami.iwami.app.model.Task;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.model.Wami;

@AjaxClass
public class WamiAjax {

	private Log logger = LogFactory.getLog(getClass());

	private WamiBiz wamiBiz;
	
	private UserBiz userBiz;
	
	private TaskBiz taskBiz;

	@AjaxMethod(path = "sharetask.ajax")
	public Map<Object, Object> shareUpload(Map<String,String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			if(params.containsKey("userid") && params.containsKey("taskid") && params.containsKey("time")){
				long userid = NumberUtils.toLong(params.get("userid"), -1);
				if(userid > 0){
					long taskid = NumberUtils.toLong(params.get("taskid"), -1);
					if(taskid > 0){
						int type = Task.STATUS_FINISH;
						if(type >= 0){
							long time = NumberUtils.toLong(params.get("time"), -1);
							if(time > 0){
								User user = userBiz.getUserById(userid);
								if(user != null && user.getId() == userid){
									Task task = taskBiz.getTaskById(taskid);
									if(task != null && task.getId() == taskid){
										wamiBiz.share(user, task, type, time, StringUtils.trimToEmpty(params.get("channel")));
										result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
									} else {
										result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TASKID);
										result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TASKID));
									}
								} else {
									result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_USERID);
									result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_USERID));
								}
							} else{
								result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TIME);
								result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TIME));
							}
						} else{
							result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TYPE);
							result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TYPE));
						}
					} else {
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TASKID);
						result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TASKID));
					}
				} else {
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_USERID);
					result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_USERID));
				}
			} else
				result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch (Throwable t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_ERROR);
		}
		return result;
	}

	@AjaxMethod(path = "wami.ajax")
	public Map<Object, Object> statusUpload(Map<String,String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			if(params.containsKey("userid") && params.containsKey("taskid") && params.containsKey("type") && params.containsKey("time")){
				long userid = NumberUtils.toLong(params.get("userid"), -1);
				if(userid > 0){
					long taskid = NumberUtils.toLong(params.get("taskid"), -1);
					if(taskid > 0){
						int type = NumberUtils.toInt(params.get("type"), -1);
						if(type >= 0){
							long time = NumberUtils.toLong(params.get("time"), -1);
							if(time > 0){
								User user = userBiz.getUserById(userid);
								if(user != null && user.getId() == userid){
									Task task = taskBiz.getTaskById(taskid);
									if(task != null && task.getId() == taskid){
										wamiBiz.wami(user, task, type, time, StringUtils.trimToEmpty(params.get("channel")));
										result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
									} else {
										result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TASKID);
										result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TASKID));
									}
								} else {
									result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_USERID);
									result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_USERID));
								}
							} else{
								result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TIME);
								result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TIME));
							}
						} else{
							result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TYPE);
							result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TYPE));
						}
					} else {
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_TASKID);
						result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_TASKID));
					}
				} else {
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_USERID);
					result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_USERID));
				}
			} else
				result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch (TaskRepeatStartException t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_WAMI_REPEAT_START);
			result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_REPEAT_START));
		} catch (TaskNotExistsException t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_TASK_NOT_EXISTS);
			result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_TASK_NOT_EXISTS));
		} catch (TaskFinishedException t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_TASK_FINISHED);
			result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_TASK_FINISHED));
		} catch (TaskUnavailableException t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_TASK_UNAVAILABLE);
			result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_TASK_UNAVAILABLE));
		} catch (TaskWamiedException t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_TASK_WAMIED);
			result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_TASK_WAMIED));
		} catch (Throwable t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in wami", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_ERROR);
		}
		return result;
	}

	@AjaxMethod(path = "wami/history.ajax")
	public Map<Object, Object> getWamiHistory(Map<String,String> params) {
		Map<Object, Object> result = new HashMap<Object, Object>();
		try {
			if(params.containsKey("userid")){
				long userid = NumberUtils.toLong(params.get("userid"), -1);
				if(userid > 0){
					User user = userBiz.getUserById(userid);
					if(user != null && user.getId() == userid){
						List<Wami> wamis = wamiBiz.getWamiHistory(userid);
						
						result.put("data", parseWami(wamis));
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_OK);
					} else {
						result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_USERID);
						result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_USERID));
					}
				} else {
					result.put(ErrorCodeConstants.STATUS_KEY, ErrorCodeConstants.STATUS_WAMI_USERID);
					result.put(ErrorCodeConstants.MSG_KEY, ErrorCodeConstants.ERROR_MSG_MAP.get(ErrorCodeConstants.STATUS_WAMI_USERID));
				}
			} else
				result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_PARAM_ERROR);
		} catch (Throwable t) {
			if (logger.isErrorEnabled()) 
				logger.error("Exception in getWamiHistory", t);
			result.put(ErrorCodeConstants.STATUS_KEY,ErrorCodeConstants.STATUS_ERROR);
		}
		return result;
	}

	private List<Map<String, Object>> parseWami(List<Wami> wamis) {
		Map<Date, List<Map<String, Object>>> result = new HashMap<Date, List<Map<String,Object>>>();
		
		if(wamis != null && wamis.size() > 0){
			List<Long> ids = new ArrayList<Long>();
			for(Wami wami : wamis)
				ids.add(wami.getTaskId());
			
			Map<Long, Task> tasks = taskBiz.getTaskByIds(ids);
			
			for(Wami wami : wamis){
				Date key = DateUtils.truncate(wami.getLastmodTime(), Calendar.DATE);
				
				List<Map<String, Object>> tmp = result.get(key);
				if(tmp == null){
					tmp = new ArrayList<Map<String,Object>>();
					result.put(key, tmp);
				}
				
				tmp.add(parseWami(wami, tasks.get(wami.getTaskId())));
			}
		}
		
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		if(result != null && result.size() > 0)
			for(Date key : sort(result.keySet())){
				Map<String, Object> _data = new HashMap<String, Object>();
				
				_data.put("time", key.getTime());
				_data.put("data", result.get(key));
				
				data.add(_data);
			}
		
		return data;
	}

	private List<Date> sort(Set<Date> keys) {
		List<Date> data = new ArrayList<Date>(keys);
		
		Collections.sort(data, new Comparator<Date>() {

			@Override
			public int compare(Date o1, Date o2) {
				return o1.before(o2) ? -1 : 1;
			}
		});
		
		return data;
	}

	private Map<String, Object> parseWami(Wami wami, Task task) {
		Map<String, Object> result = new HashMap<String, Object>();
		
		if(wami != null){
			result.put("taskName", task.getName());
			result.put("prize", wami.getPrize());
		}
		
		return result;
	}

	public WamiBiz getWamiBiz() {
		return wamiBiz;
	}

	public void setWamiBiz(WamiBiz wamiBiz) {
		this.wamiBiz = wamiBiz;
	}

	public UserBiz getUserBiz() {
		return userBiz;
	}

	public void setUserBiz(UserBiz userBiz) {
		this.userBiz = userBiz;
	}

	public TaskBiz getTaskBiz() {
		return taskBiz;
	}

	public void setTaskBiz(TaskBiz taskBiz) {
		this.taskBiz = taskBiz;
	}
}
