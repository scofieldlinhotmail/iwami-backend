package com.iwami.iwami.app.service;

import java.util.List;

import com.iwami.iwami.app.model.Task;
import com.iwami.iwami.app.model.TreasureConfig;

public interface TaskService {
	
	public List<Task> getAllAvailableTasks();

	public TreasureConfig getTreasureConfig();

}
