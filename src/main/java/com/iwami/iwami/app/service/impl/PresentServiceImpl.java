package com.iwami.iwami.app.service.impl;

import java.util.Collections;
import java.util.List;

import com.iwami.iwami.app.comparator.PresentRankComparator;
import com.iwami.iwami.app.constants.IWamiConstants;
import com.iwami.iwami.app.dao.PresentDao;
import com.iwami.iwami.app.model.Exchange;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.Share;
import com.iwami.iwami.app.service.PresentService;
import com.iwami.iwami.app.util.LocalCaches;

public class PresentServiceImpl implements PresentService {
	
	private PresentDao presentDao;
	
	private long expireTime;

	@Override
	@SuppressWarnings("unchecked")
	public List<Present> getAllAvailablePresents() {
		List<Present> presents = (List<Present>)LocalCaches.get(IWamiConstants.CACHE_PRESENT_KEY, System.currentTimeMillis(), expireTime);
		
		if(presents == null){
			presents = presentDao.getAllPresents();
			
			if(presents != null && presents.size() > 0){
				Collections.sort(presents, new PresentRankComparator());
				
				LocalCaches.set(IWamiConstants.CACHE_PRESENT_KEY, presents, System.currentTimeMillis());
			}
		}
		
		return presents;
	}

	@Override
	public long addExchange(Exchange exchange) {
		return presentDao.addExchange(exchange);
	}

	@Override
	public void updateExchangeStatus(long id, int status) {
		 presentDao.updateExchangeStatus(id, status);
	}

	@Override
	public boolean addShareExchange(Share share) {
		return presentDao.addShareExchange(share);
	}

	public long getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(long expireTime) {
		this.expireTime = expireTime;
	}

	public PresentDao getPresentDao() {
		return presentDao;
	}

	public void setPresentDao(PresentDao presentDao) {
		this.presentDao = presentDao;
	}

}
