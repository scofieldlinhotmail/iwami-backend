package com.iwami.iwami.app.dao;

import java.util.List;

import com.iwami.iwami.app.model.Exchange;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.Share;

public interface PresentDao {
	
	public List<Present> getAllPresents();

	public long addExchange(Exchange exchange);

	public void updateExchangeStatus(long id, int status);

	public boolean addShareExchange(Share share);

}
