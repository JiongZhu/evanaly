package com.spider.start;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

import us.codecraft.webmagic.Spider;

import com.DAO.WeatherURLDAO;
import com.model.WeatherURL;
import com.spider.pageProcessor.WeatherProcessorA;
import com.spider.pageProcessor.WeatherProcessorB;
import com.util.CityUtil;
@Component
public class WeatherSpiderStart {
	@Autowired
	private WeatherProcessorA weatherProcessorA;
	@Autowired
	private WeatherProcessorB weatherProcessorB;
	@Autowired
	private WeatherURLDAO weatherURLDAO;
	
	private List<WeatherURL> weatherURLs;
	
	public  void startA(){
		weatherURLs = weatherURLDAO.selectAll();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Spider s = Spider.create(weatherProcessorA).thread(3);
		WeatherURL wurl;
		for (int i = 0; i < weatherURLs.size(); i++) {
			wurl = weatherURLs.get(i);
			s.addUrl(wurl.getUrlA());
			map.put(wurl.getCity().getName(), wurl.getCity().getId());
		}
		weatherProcessorA.setCityMap(map);
		s.start();
	}
	
	public void startB(){
		weatherURLs = weatherURLDAO.selectAll();
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Spider s = Spider.create(weatherProcessorB).thread(3);
		WeatherURL wurl;
		for (int i = 0; i < weatherURLs.size(); i++) {
			wurl = weatherURLs.get(i);
			s.addUrl(weatherURLs.get(i).getUrlB());
			map.put(wurl.getCity().getName(), wurl.getCity().getId());
		}
		weatherProcessorB.setCityMap(map);
		s.start();
		
	}
	


	
	
}
