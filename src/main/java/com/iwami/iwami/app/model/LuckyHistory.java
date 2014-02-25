package com.iwami.iwami.app.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class LuckyHistory {

	private long id;
	
	private long userid;
	
	private String username;
	
	private long cellPhone;
	
	private long drawid;
	
	private int drawPrize;
	
	private String gift;
	
	private Date lastmodTime;
	
	private long lastmodUserid;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public long getCellPhone() {
		return cellPhone;
	}

	public void setCellPhone(long cellPhone) {
		this.cellPhone = cellPhone;
	}

	public long getDrawid() {
		return drawid;
	}

	public void setDrawid(long drawid) {
		this.drawid = drawid;
	}

	public int getDrawPrize() {
		return drawPrize;
	}

	public void setDrawPrize(int drawPrize) {
		this.drawPrize = drawPrize;
	}

	public String getGift() {
		return gift;
	}

	public void setGift(String gift) {
		this.gift = gift;
	}

	public Date getLastmodTime() {
		return lastmodTime;
	}

	public void setLastmodTime(Date lastmodTime) {
		this.lastmodTime = lastmodTime;
	}

	public long getLastmodUserid() {
		return lastmodUserid;
	}

	public void setLastmodUserid(long lastmodUserid) {
		this.lastmodUserid = lastmodUserid;
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
