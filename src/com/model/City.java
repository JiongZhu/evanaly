package com.model;

import java.io.Serializable;

public class City implements Serializable{
	private  Integer id;
	/*
	 * 城市名称
	 */
	private String name;
	/**
	 * 最低人流量
	 */
	private int low;
	/**
	 * 中等人流量
	 */
	private int mid;
	/**
	 * 最高人流量
	 */
	
	private int high;
	
	
	
	
	public City(Integer id) {
		super();
		this.id = id;
	}
	public City() {
		super();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLow() {
		return low;
	}
	public void setLow(int low) {
		this.low = low;
	}
	public int getMid() {
		return mid;
	}
	public void setMid(int mid) {
		this.mid = mid;
	}
	public int getHigh() {
		return high;
	}
	public void setHigh(int high) {
		this.high = high;
	}
	@Override
	public String toString() {
		return "City [high=" + high + ", id=" + id + ", low=" + low + ", mid="
				+ mid + ", name=" + name + "]";
	}
	
}
