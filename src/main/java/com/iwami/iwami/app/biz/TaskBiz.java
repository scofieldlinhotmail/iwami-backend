package com.iwami.iwami.app.biz;

import java.util.List;

import com.iwami.iwami.app.model.Task;
import com.iwami.iwami.app.model.TreasureConfig;

public interface TaskBiz {
	
	public List<Task> getTopTasks();

	public List<List<Task>> getWamiTasks(long userid);

	public TreasureConfig getTreasureConfig();

}
