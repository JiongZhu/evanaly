package com.model;

import java.io.Serializable;
import java.sql.Date;

public class PassengerFlow implements Serializable {
	private Integer id;
	private Route route;
	private Date date;
	/**
	 * 展会人流量影响
	 */
	private int showPnums;
	/**
	 * 演唱会人流量影响
	 */
	private int concertPnums;
	/**
	 * 体育赛事人流量影响
	 */
	
	private int sportPnums;
	/**
	 * 政治事件人流量影响
	 */
	
	private int polityPnums;
	/*
	 * 总计
	 */
	
	private int total;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getShowPnums() {
		return showPnums;
	}

	public void setShowPnums(int showPnums) {
		this.showPnums = showPnums;
	}

	public int getConcertPnums() {
		return concertPnums;
	}

	public void setConcertPnums(int concertPnums) {
		this.concertPnums = concertPnums;
	}

	public int getSportPnums() {
		return sportPnums;
	}

	public void setSportPnums(int sportPnums) {
		this.sportPnums = sportPnums;
	}

	public int getPolityPnums() {
		return polityPnums;
	}

	public void setPolityPnums(int polityPnums) {
		this.polityPnums = polityPnums;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	@Override
	public String toString() {
		return "PassengerFlow [concertPnums=" + concertPnums + ", date=" + date
				+ ", id=" + id + ", polityPnums=" + polityPnums + ", route="
				+ route + ", showPnums=" + showPnums + ", sportPnums="
				+ sportPnums + ", total=" + total + "]";
	}

	
	

}
