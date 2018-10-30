package com.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

public class Weather implements Serializable{
	private int id;
	/**
	 * 城市
	 */
	private City city;
	/**
	 * 日期
	 */
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private  Date date;
	/**
	 * 最低温度
	 */
	private Integer minTem;
	/**
	 * 最高温度
	 */
	private Integer maxTem;
	/**
	 * 风强度情况
	 */
	
	private String wind;
	/**
	 * 天气情况
	 */
	private String description;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getMinTem() {
		return minTem;
	}
	public void setMinTem(Integer minTem) {
		this.minTem = minTem;
	}
	public Integer getMaxTem() {
		return maxTem;
	}
	public void setMaxTem(Integer maxTem) {
		this.maxTem = maxTem;
	}
	public String getWind() {
		return wind;
	}
	public void setWind(String wind) {
		this.wind = wind;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Weather [city=" + city + ", date=" + date + ", description="
				+ description + ", maxTem=" + maxTem + ", minTem=" + minTem
				+ ", wind=" + wind + "]";
	}

	
	
}
