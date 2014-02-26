package com.iwami.iwami.app.biz;

import java.util.List;

import com.iwami.iwami.app.exception.NotEnoughPrizeException;
import com.iwami.iwami.app.model.Present;
import com.iwami.iwami.app.model.User;

public interface PresentBiz {

	public List<Present> getAllPresents(long userid);

	public boolean gift(User user, User user2, int prize) throws NotEnoughPrizeException;
	
	// only for transaction control, not open for other callers.
	public void doGift(User user, User user2, int prize) throws NotEnoughPrizeException;

	public boolean addShareExchange(long userid, int type, int target, String msg);
}
