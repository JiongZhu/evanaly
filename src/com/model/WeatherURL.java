package com.model;

import java.io.Serializable;

public class WeatherURL implements Serializable{
	private int id;
	/**
	 * 城市
	 */
	private City city;
	/**
	 * 数据源A url
	 */
	private String urlA;
	/**
	 * 数据源B url
	 */
	private String urlB;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrlA() {
		return urlA;
	}
	public void setUrlA(String urlA) {
		this.urlA = urlA;
	}
	public String getUrlB() {
		return urlB;
	}
	public void setUrlB(String urlB) {
		this.urlB = urlB;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	
	
	

}
