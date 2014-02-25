package com.iwami.iwami.app.dao;


public interface WamiDao {
	
	boolean uploadStatus(int userid,long taskid,int type,long time);
	
}
