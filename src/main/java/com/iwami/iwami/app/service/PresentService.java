package com.iwami.iwami.app.service;

import java.util.List;

import com.iwami.iwami.app.model.Exchange;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.Share;

public interface PresentService {

	public List<Present> getAllAvailablePresents();

	public long addExchange(Exchange exchange);

	public void updateExchangeStatus(long id, int status);

	public boolean addShareExchange(Share share);
}
