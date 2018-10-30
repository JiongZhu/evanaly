package com.test;

import com.DAO.EventDAO;
import com.DAO.PassengerFlowDAO;
import com.DAO.RouteDAO;
import com.github.pagehelper.Page;
import com.hankcs.hanlp.HanLP;
import com.model.City;
import com.model.EventSf;
import com.model.GroupBy;
import com.model.Route;
import com.service.CityService;
import com.service.EarlyWarnService;
import com.service.QRCodeService;
import com.service.RouteService;
import com.service.WeatherService;
import com.serviceImp.QRCodeServiceImp;
import com.util.DateUtil;
import com.util.NLPUtil;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Test6 {
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");
	public static void main(String[] args) {
		EventDAO eventDAO = (EventDAO) ctx.getBean("eventDAO");
		PassengerFlowDAO passengerFlowDAO = (PassengerFlowDAO) ctx.getBean("passengerFlowDAO");
		RouteService routeService = (RouteService) ctx.getBean("routeServiceImp");
//		System.out.println(eventDAO.eventsSum(null));
//		Date date1 = DateUtil.getOffsetDate(new java.util.Date(),1);
//		Date date2 = DateUtil.getOffsetDate(new java.util.Date(),7);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("startDate", date1);
//		map.put("endDate", date2);
//		System.out.println(eventDAO.groupByTypeDate(map));
//		System.out.println(eventDAO.eventsAmountByDate(map));
//		System.out.println(eventDAO.cityTopFive(map));
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("", new Date(System.currentTimeMillis()));
//		List<GroupBy> list = eventDAO.eventsAmountByDate(map);
////		System.out.println(list.get(0));
//		List<EventSf> eList = eventDAO.searchEventSf(new Date(System.currentTimeMillis()));
//		System.out.println(eList.get(0));
//		new HanLP.Config();
//		EarlyWarnService earlyWarnService = (EarlyWarnService) ctx.getBean("earlyWarnServiceImp");
//		System.out.println(earlyWarnService.weeklyCityPF());
//		System.out.println(earlyWarnService.weeklyPF());
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("startDate", DateUtil.getOffsetToday(1));
//		map.put("endDate", DateUtil.getOffsetToday(7));
//		System.out.println(routeService.getAllRoute().size());
//		QRCodeService codeService = (QRCodeService) ctx.getBean(QRCodeServiceImp.class);
//		System.out.println(codeService.scanQRCode("14082532017", "1504511949492"));
		WeatherService weatherService = ctx.getBean(WeatherService.class);
		System.out.println(weatherService.weeklyWeatherWran());
	}
	

}
