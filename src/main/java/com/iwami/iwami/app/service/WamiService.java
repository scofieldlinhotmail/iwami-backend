package com.iwami.iwami.app.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.iwami.iwami.app.model.Wami;

public interface WamiService{

	public Wami getLatestWami(long userid, long taskid);

	public void newWami(Wami wami);

	public Map<Long, Wami> getLatestWamis(long userid, List<Long> taskids);

	public List<Long> getOngoingWami(long userid);

	public List<Long> getDoneTaskIds(long userid, Date start);

}
