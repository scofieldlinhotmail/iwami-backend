package com.iwami.iwami.app.dao;

import java.util.List;

import com.iwami.iwami.app.model.LuckyConfig;
import com.iwami.iwami.app.model.LuckyHistory;
import com.iwami.iwami.app.model.LuckyRule;

public interface LuckyDao {
	
	public List<LuckyRule> getLuckyRules();
	
	public boolean delLuckyRules(long id);
	
	public boolean addLuckyRule(LuckyRule rule);
	
	public LuckyConfig getLuckyConfig();
	
	public boolean delLuckyConfig(long id);
	
	public boolean addLuckyConfig(LuckyConfig config);

	public boolean addLuckyHistory(LuckyHistory history);
	
	public List<LuckyHistory> getAllLuckyHistory();
	
	public int getLuckyCountByUserid(long userid);
}
