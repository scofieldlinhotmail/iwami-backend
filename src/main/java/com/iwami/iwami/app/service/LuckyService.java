package com.iwami.iwami.app.service;

import java.util.Date;
import java.util.List;

import com.iwami.iwami.app.model.LuckyConfig;
import com.iwami.iwami.app.model.LuckyHistory;
import com.iwami.iwami.app.model.LuckyRule;

public interface LuckyService {

	public List<LuckyRule> getLuckyRules();

	public LuckyConfig getLuckyConfig();
	
	public int getLuckyCountByUserid(long userid);

	public boolean addLuckyHistory(LuckyHistory history);

	public int getLuckyDrawCount(long drawid, Date start, Date end);
}
