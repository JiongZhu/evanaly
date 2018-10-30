package com.model;

import java.io.Serializable;
import java.util.Date;

public class Event implements Serializable{
	private Integer id;
	/**
	 * 事件名称
	 */
	private String name;
	/**
	 * 主办方级别 1：国家政府 2：省政府 3：地方政府 4：国际性组织
	 */
	private Integer sponsorLevel;
	/**
	 * 主办方类型 1：国内民间协会 2：国内行业协会 3：国际民间协会 4：国际行业协会
	 */
	private Integer sponsorType;
	/**
	 * 影响年龄阶段 1：儿童 2：青年 3：成年 4：老年
	 */
	private Integer affectAge;
	/**
	 * 是否有固定参与人群 1：是 2：否
	 */
	private Integer fixedParticipation;
	/**
	 * 影响社会群体 1：商务人群 2：社会大众
	 */
	private Integer affectCommunity;
	/**
	 * 影响范围 1：全国 2：全省 3：全市 4：全球 5：洲际
	 */
	private Integer affectRange;
	/**
	 * 事件类型 1：展会 2：演唱会 3：体育赛事 4：政治会议 5：地方性节假日
	 */
	private Integer type;
	/**
	 * 事件热度 1-3程度递增
	 */
	private Integer heat;
	/**
	 * 一年内频率
	 */
	private Integer frequency;
	/**
	 * 历史悠久程度 1-3程度递增
	 */
	private Integer historicalLevel;

	/**
	 * 事件开始时间
	 */
	private Date startDate;
	/**
	 * 时间结束时间
	 */
	private Date endDate;
	/**
	 * 举办城市
	 */
	private City city;
	/**
	 * 影响人数
	 */
	private Integer pnums;
	/**
	 *关键词 
	 */
	private String keyWords;
	/**
	 *  事件来源 
	 *  1:易展网   2：展易网   3：大麦网  4: 京东演出票   5;新华网    6: 新浪
	 */
	private Integer src;
	
	public  static final Integer ESHOW365 = 1;
	public  static final Integer EXSHOW = 2;
	public  static final Integer DAMAI = 3;
	public  static final Integer JDPIAO = 4;
	public  static final Integer XINHUA = 5;
	public  static final Integer SINA = 6;
	
	
	
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
	public Integer getSponsorLevel() {
		return sponsorLevel;
	}
	public void setSponsorLevel(Integer sponsorLevel) {
		this.sponsorLevel = sponsorLevel;
	}
	public Integer getSponsorType() {
		return sponsorType;
	}
	public void setSponsorType(Integer sponsorType) {
		this.sponsorType = sponsorType;
	}
	public Integer getAffectAge() {
		return affectAge;
	}
	public void setAffectAge(Integer affectAge) {
		this.affectAge = affectAge;
	}
	public Integer getFixedParticipation() {
		return fixedParticipation;
	}
	public void setFixedParticipation(Integer fixedParticipation) {
		this.fixedParticipation = fixedParticipation;
	}
	public Integer getAffectCommunity() {
		return affectCommunity;
	}
	public void setAffectCommunity(Integer affectCommunity) {
		this.affectCommunity = affectCommunity;
	}
	public Integer getAffectRange() {
		return affectRange;
	}
	public void setAffectRange(Integer affectRange) {
		this.affectRange = affectRange;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getHeat() {
		return heat;
	}
	public void setHeat(Integer heat) {
		this.heat = heat;
	}
	public Integer getHistoricalLevel() {
		return historicalLevel;
	}
	public void setHistoricalLevel(Integer historicalLevel) {
		this.historicalLevel = historicalLevel;
	}
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public City getCity() {
		return city;
	}
	public void setCity(City city) {
		this.city = city;
	}
	public String getKeyWords() {
		return keyWords;
	}
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}
	public Integer getPnums() {
		return pnums;
	}
	public void setPnums(Integer pnums) {
		this.pnums = pnums;
	}
	public Integer getSrc() {
		return src;
	}
	public void setSrc(Integer src) {
		this.src = src;
	}
	@Override
	public String toString() {
		return "Event [affectAge=" + affectAge + ", affectCommunity="
				+ affectCommunity + ", affectRange=" + affectRange + ", city="
				+ city + ", endDate=" + endDate + ", fixedParticipation="
				+ fixedParticipation + ", frequency=" + frequency + ", heat="
				+ heat + ", historicalLevel=" + historicalLevel + ", id=" + id
				+ ", keyWords=" + keyWords + ", name=" + name + ", pnums="
				+ pnums + ", sponsorLevel=" + sponsorLevel + ", sponsorType="
				+ sponsorType + ", src=" + src + ", startDate=" + startDate
				+ ", type=" + type + "]";
	}
	

}
