package com.model;

import java.io.Serializable;
import java.util.List;
/**
 * 路线 
 *
 */
public class Route implements Serializable {
	private Integer id;
	private City fromCity;
	private City toCity;
	private int low;
	private int mid;
	private int high;
	/**
	 * 航班
	 */
	private List<Flight> flights;
	/**
	 * 权重
	 */
	
	
	private double weight;
	public Route(Integer id) {
		super();
		this.id = id;
	}
	public Route() {
		super();
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public City getFromCity() {
		return fromCity;
	}
	public void setFromCity(City fromCity) {
		this.fromCity = fromCity;
	}
	public City getToCity() {
		return toCity;
	}
	public void setToCity(City toCity) {
		this.toCity = toCity;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public List<Flight> getFlights() {
		return flights;
	}
	public void setFlights(List<Flight> flights) {
		this.flights = flights;
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
		return "Route [flights=" + flights + ", fromCity=" + fromCity + ", id="
				+ id + ", toCity=" + toCity + ", weight=" + weight + "]";
	}

	
	
	
}
