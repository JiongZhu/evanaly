package com.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * 
 * 事件辅助类 
 * 
 */
public class EventSf implements Serializable {

	private Date date;
	private int type;
	private int pnums;
	private int city;
	private int count;
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPnums() {
		return pnums;
	}
	public void setPnums(int pnums) {
		this.pnums = pnums;
	}
	public int getCity() {
		return city;
	}
	public void setCity(int city) {
		this.city = city;
	}
	
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public String toString() {
		return "EventSf [city=" + city + ", count=" + count + ", date=" + date
				+ ", pnums=" + pnums + ", type=" + type + "]";
	}
	
	
}
