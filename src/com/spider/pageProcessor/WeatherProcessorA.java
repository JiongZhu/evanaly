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
 * http://www.weather.com.cn 天气爬虫
 *
 */
@Component
public class WeatherProcessorA implements PageProcessor{
	@Autowired
	private WeatherService weatherService;
	private Map<String, Integer> cityMap;
	
	private Site site = Site.me().setRetryTimes(5).setSleepTime(1000).setCharset("utf-8")
						.setUserAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:52.0) Gecko/20100101 Firefox/52.0");
	
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		List<String> lis = page.getHtml().xpath("//*div[@id='7d']/ul[@class='t']/li").all();
		List<String> name = page.getHtml().xpath("//*div[@class='ctop']/div[@class='crumbs']/a").all();
		Date date;
		if(name==null||name.size()==0)
			return;
		String city;
		if(name.size()==1)
			city = Html.create(name.get(0)).xpath("//*a/text()").get();
		else
			city = Html.create(name.get(1)).xpath("//*a/text()").get();
			
//		String c = page.getHtml().xpath("//*div[@class='ctop']/div[@class='crumbs']/span[2]/text()").get();
		int size = lis.size();
		Html h ;
		Weather w;
		
		//未来七天
		for (int i = 1; i<size ; i++) {
			w = new Weather();
			w.setCity(new City(cityMap.get(city)));
			h = Html.create(lis.get(i));
			String desc = h.xpath("//*p[@class='wea']/text()").get();
			String wind = h.xpath("//*p[@class='win']/i/text()").get();
			String tmp = h.xpath("//*p[@class='tem']/i/text()").get();
			int minTem = 0;
			int maxTem = 0;
			if(tmp.endsWith("℃")){
				
				minTem = Integer.parseInt(tmp.substring(0,tmp.length()-1));
			}else{
				minTem = Integer.parseInt(tmp);
				
			}
			tmp = h.xpath("//*p[@class='tem']/span/text()").get();
			if(tmp.endsWith("℃")){
				
				maxTem= Integer.parseInt(tmp.substring(0,tmp.length()-1));
			}else{
				maxTem= Integer.parseInt(tmp);
			}
			w.setMinTem(minTem);
			w.setMaxTem(maxTem);
			date = DateUtil.getOffsetToday(i);
			w.setDate(date);
			w.setWind(wind);
			w.setDescription(desc);
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
