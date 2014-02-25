package com.iwami.iwami.app.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.iwami.iwami.app.biz.TaskBiz;
import com.iwami.iwami.app.comparator.TaskDoneComparator;
import com.iwami.iwami.app.comparator.TaskNewComparator;
import com.iwami.iwami.app.comparator.TaskOngoingComparator;
import com.iwami.iwami.app.comparator.TaskRankComparator;
import com.iwami.iwami.app.model.Task;
import com.iwami.iwami.app.model.TreasureConfig;
import com.iwami.iwami.app.service.TaskService;

public class TaskBizImpl implements TaskBiz {
	
	private TaskService taskService;

	@Override
	public List<Task> getTopTasks() {
		List<Task> tasks = getDoneTasks();
		
		List<Task> tmp = taskService.getAllAvailableTasks();
		if(tmp != null && tmp.size() > 0){
			List<Task> ttt = getDoneTasks();
			for(Task task : tmp)
				if((task.getType() & 8) == 8){
					ttt.add(task);
				}
					
			Collections.sort(ttt, new TaskRankComparator());
			
			for(int i = 0; i < ttt.size() && i < 3; i ++){
				ttt.get(i).setRank(i);
				tasks.add(ttt.get(i));
			}
		}
		
		return tasks;
	}

	@Override
	public List<List<Task>> getWamiTasks(long userid) {
		List<List<Task>> tasks = new ArrayList<List<Task>>();
		
		// new
		List<Task> newtasks = new ArrayList<Task>();
		List<Task> ongoingtasks = new ArrayList<Task>();
		List<Task> donetasks = new ArrayList<Task>();
		
		if(userid > 0){
			// new
			List<Task> tmpnew = getNewTasks();
			
			// ongoing
			List<Task> tmpongoing = getOngoingTasks();
			
			// done
			donetasks = getDoneTasks();

			// filter ongoing tasks
			Set<Integer> filteredIds = new HashSet<Integer>();
			
			if(donetasks != null && donetasks.size() > 0)
				for(Task task : donetasks)
					filteredIds.add(task.getId());
			
			if(tmpongoing != null && tmpongoing.size() > 0)
				for(Task task : tmpongoing)
					if(!filteredIds.contains(task.getId())){
						ongoingtasks.add(task);
						filteredIds.add(task.getId());
					}
			
			// filter new tasks
			if(tmpnew != null && tmpnew.size() > 0)
				for(Task task : tmpnew)
					if(!filteredIds.contains(task.getId()))
						newtasks.add(task);
		}
		
		// sort new
		Collections.sort(newtasks, new TaskNewComparator());
		// set rank
		for(int i = 0; i < newtasks.size(); i ++){
			newtasks.get(i).setRank(i);
		}
		// add new tasks
		tasks.add(newtasks);
		
		// sort ongoing
		Collections.sort(ongoingtasks, new TaskOngoingComparator());
		// set rank
		for(int i = 0; i < ongoingtasks.size(); i ++){
			ongoingtasks.get(i).setRank(i);
		}
		// add ongoing tasks
		tasks.add(ongoingtasks);
		
		// sort done
		Collections.sort(donetasks, new TaskDoneComparator());
		// set rank
		for(int i = 0; i < donetasks.size(); i ++){
			donetasks.get(i).setRank(i);
		}
		// add done tasks
		tasks.add(donetasks);
		
		return tasks;
	}

	private List<Task> getDoneTasks() {
		//TODO
		List<Task> donetasks = new ArrayList<Task>();
		return donetasks;
	}

	private List<Task> getOngoingTasks() {
		//TODO
		List<Task> ongoingtasks = new ArrayList<Task>();
		return ongoingtasks;
	}

	private List<Task> getNewTasks() {
		List<Task> newtasks = new ArrayList<Task>();
		List<Task> tmp = taskService.getAllAvailableTasks();
		if(tmp != null && tmp.size() > 0){
			for(Task task : tmp)
				if((task.getType() & 1) == 1){
					Task tmpTask = new Task();
					BeanUtils.copyProperties(task, tmpTask);
					newtasks.add(tmpTask);
				}
		}
		return newtasks;
	}

	@Override
	public TreasureConfig getTreasureConfig() {
		return taskService.getTreasureConfig();
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

}
