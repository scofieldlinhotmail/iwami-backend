package com.iwami.iwami.app.dao;

import java.util.List;
import java.util.Map;

import com.iwami.iwami.app.model.Wami;

public interface WamiDao {
	
	public void newWami(Wami wami);

	public Wami getLatestWami(long userid, long taskid);

	public Map<Long, Wami> getLatestWami(long userid, List<Long> taskids);

	public Map<Long, Wami> getDoneTaskIds(long userid);

	public Map<Long, Wami> getOngoingWami(long userid);

	public Wami getWamiByType(long userid, long taskid, int type);

	public List<Wami> getWamiHistory(long userid, int status);
	
}
