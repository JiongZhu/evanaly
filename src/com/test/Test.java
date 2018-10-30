package com.test;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.DAO.EventDAO;
import com.DAO.WeatherDAO;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.HanLP.Config;
import com.service.PassengerFlowService;
import com.spider.start.ConcertSpiderStart;
import com.spider.start.ShowSpiderStart;
import com.spider.start.SportSpiderStart;
import com.spider.start.WeatherSpiderStart;
import com.task.SpiderTask;

public class Test {
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-context*.xml");
	static EventDAO eventDAO = (EventDAO) ctx.getBean("eventDAO");
	static WeatherDAO weatherDAO = (WeatherDAO) ctx.getBean("weatherDAO");
	static ShowSpiderStart start = (ShowSpiderStart) ctx.getBean("showSpiderStart");
	static SportSpiderStart start2 = (SportSpiderStart) ctx.getBean("sportSpiderStart");
	static ConcertSpiderStart start3 = (ConcertSpiderStart) ctx.getBean("concertSpiderStart");
	static WeatherSpiderStart start4 = (WeatherSpiderStart) ctx.getBean("weatherSpiderStart");
	static PassengerFlowService passengerFlowService  = (PassengerFlowService) ctx.getBean("passengerFlowServiceImp");
 	public static void main(String[] args) {
//		Event event = new Event();
//		event.setName("2016 BIGBANG MADE [V.I.P] TOUR IN NANJING");
//		System.out.println(eventDAO.insert(event));
//		System.out.println(eventDAO.find(event));
//		start.startA();
//		start.startB();
//		start2.startA();
//		start2.startB();
//		start3.startA();
//		start3.startB();
 		start4.startA();
 		start4.startB();
// 		passengerFlowService.caclPassengerFlow();
//		System.out.println(Arrays.toString("上海|上海市".split("\\|")));
// 		Weather w = new Weather();
// 		w.setDate(new Date());
// 		w.setCity("aa");
// 		w.setMaxTem(40);
// 		w.setMinTem(20);
// 		w.setWind("c");
// 		weatherDAO.insert(w);
// 		Scanner sc = new Scanner(System.in);
// 		String s = sc.nextLine();
// 		JSONObject jobj=JSON.parseObject(s);  
// 		jobj = JSON.parseObject(jobj.getString("Data"));
// 		JSONArray ja = JSON.parseArray(jobj.getString("performs"));
// 		jobj = JSON.parseObject(ja.get(0).toString());
// 		System.out.println(jobj.get("ShowDate"));
	}

}
