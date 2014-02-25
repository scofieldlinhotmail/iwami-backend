package com.iwami.iwami.app.biz.impl;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.springframework.transaction.annotation.Transactional;

import com.iwami.iwami.app.biz.LuckyBiz;
import com.iwami.iwami.app.comparator.LuckyRuleComparator;
import com.iwami.iwami.app.exception.LuckyExceedLimitException;
import com.iwami.iwami.app.exception.NotEnoughPrizeException;
import com.iwami.iwami.app.model.LuckyConfig;
import com.iwami.iwami.app.model.LuckyHistory;
import com.iwami.iwami.app.model.LuckyRule;
import com.iwami.iwami.app.model.User;
import com.iwami.iwami.app.service.LuckyService;
import com.iwami.iwami.app.service.UserService;

public class LuckyBizImpl implements LuckyBiz {
	
	private LuckyService luckyService;
	
	private UserService userService;

	@Override
	public List<LuckyRule> getLuckyRules() {
		List<LuckyRule> rules = luckyService.getLuckyRules();
		if(rules != null && rules.size() > 0)
			Collections.sort(rules, new LuckyRuleComparator());
		return rules;
	}

	@Override
	public LuckyConfig getLuckyConfig() {
		return luckyService.getLuckyConfig();
	}

	@Override
	@Transactional(rollbackFor=Exception.class, value="txManager")
	public LuckyRule draw(User user, LuckyConfig config) throws LuckyExceedLimitException, NotEnoughPrizeException {
		if(config.getCount() >= 0){
			int count = luckyService.getLuckyCountByUserid(user.getId());
			if(count >= config.getCount())
				throw new LuckyExceedLimitException();
		}
		
		List<LuckyRule> rules = luckyService.getLuckyRules();
		if(rules == null || rules.size() <= 0)
			throw new RuntimeException("no lucky rules at all...");
		Collections.sort(rules, new LuckyRuleComparator());
		
		LuckyRule frule = null;
		int prob = new Random(System.currentTimeMillis()).nextInt(100);
		for(LuckyRule rule : rules){
			prob = prob - rule.getProb();
			if(prob < 0)
				frule = rule;
		}
		
		// substract from user.current_price
		if(userService.subUserCurrentPrize4Draw(user.getId(), config.getPrize())){
			LuckyHistory history = new LuckyHistory();
			history.setUserid(user.getId());
			history.setUsername(user.getName());
			history.setCellPhone(user.getCellPhone());
			history.setDrawPrize(config.getPrize());
			if(frule != null){
				history.setDrawid(frule.getId());
				history.setGift(frule.getGift());
			} else{
				history.setDrawid(-1);
				history.setGift("未中奖");
			}
			
			if(luckyService.addLuckyHistory(history))
				return frule;
			else
				throw new RuntimeException("insert luck history failed...");
		} else
			throw new NotEnoughPrizeException();
	}

	public LuckyService getLuckyService() {
		return luckyService;
	}

	public void setLuckyService(LuckyService luckyService) {
		this.luckyService = luckyService;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

}
