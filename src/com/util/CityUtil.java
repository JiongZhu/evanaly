package com.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.DAO.CityDAO;
import com.model.City;
public class CityUtil  {
	private static CityDAO cityDAO;
	private static String provinces ;
	private static  Map<String, Integer> cityMap;
	static{
		
		provinces = "﻿重庆 江苏 安徽 黑龙江 湖北 江西 甘肃 陕西 湖南 山东  福建  台湾 贵州  山西  广东  云南  新疆 四川  浙江 辽宁 海南 河南  广西 河北 宁夏回族自治区";
		cityDAO = (CityDAO) ContextUtil.getBean("cityDAO");
		loadCity();
	}

	public static boolean isProvince(String s){
		return provinces.contains(s);
	}
	
	public static void loadCity(){
		List<City> cities = cityDAO.getAllCity();
		cityMap = new HashMap<String, Integer>();
		City c;
		for (int i = 0; i < cities.size(); i++) {
			c = cities.get(i);
			cityMap.put(c.getName(), c.getId());
		}
	}
	public static Integer getId(Object o){
		return cityMap.get(o);
	}
	
}
