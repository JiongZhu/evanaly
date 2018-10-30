package com.model;

public class Flight {
	private Integer id;
	private City fromCity;
	private City toCity;
	private String flightNo;
	private Route route;
	
	public Flight() {
		super();
	}
	
	
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
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


	public Route getRoute() {
		return route;
	}


	public void setRoute(Route route) {
		this.route = route;
	}

}
