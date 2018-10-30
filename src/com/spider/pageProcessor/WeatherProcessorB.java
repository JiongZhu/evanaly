package com.spider.pageProcessor;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.WeatherDAO;
import com.model.City;
import com.model.Weather;
import com.service.WeatherService;
import com.util.DateUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * http://www.tianqi.com/  天气爬虫
 *
 */
@Component
public class WeatherProcessorB implements PageProcessor{
	@Autowired
	private WeatherService weatherService;
	private Map<String, Integer> cityMap;
	
	private Site site = Site.me().setRetryTimes(5).setSleepTime(1000)
						.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
	
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		List<String> lis = page.getHtml().xpath("//[@id='detail']/div[@class='tqshow1']").all();
		String city= page.getHtml().xpath("//[@id='city']/option[@selected]/text()").get().split(" ")[1];
		Date date;
		int size = lis.size();
		Html h ;
		Weather w;
		if(city.equals("黄南"))
			System.out.println(page.getUrl());
		//未来七天
		for (int i = 1; i<size ; i++) {
			w = new Weather();
			w.setCity(new City(cityMap.get(city.trim())));
			h = Html.create(lis.get(i));
			//温度
			int minTem = 0;
			int maxTem = 0;
			String tmp = h.xpath("//*ul/li[2]/font[1]/text()").get();
			if(tmp.endsWith("℃")){
				
				maxTem = Integer.parseInt(tmp.substring(0,tmp.length()-1));
			}else{
				maxTem = Integer.parseInt(tmp);
				
			}
			tmp = h.xpath("//*ul/li[2]/font[2]/text()").get();
			if(tmp.endsWith("℃")){
				minTem = Integer.parseInt(tmp.substring(0,tmp.length()-1));
			}else{
				minTem = Integer.parseInt(tmp);
				
			}
			w.setMinTem(minTem);
			w.setMaxTem(maxTem);
			//天气
			String desc = h.xpath("//*ul/li[3]/text()").get();
			//风
			String wind = h.xpath("//*ul/li[4]/text(").get();
			w.setWind(wind);
			w.setDescription(desc);
			date = DateUtil.getOffsetToday(i);
			w.setDate(date);
//			System.out.println(w);
			if(weatherService.searchByDateAddr(w.getDate(), w.getCity())==null)
				weatherService.insert(w);
			else
				weatherService.update(w);	
		}
		
	}

	public Map<String, Integer> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<String, Integer> cityMap) {
		this.cityMap = cityMap;
	}

}
