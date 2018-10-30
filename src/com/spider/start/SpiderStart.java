package com.spider.start;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.spider.pageProcessor.SportProcessorA;
import com.spider.pageProcessor.ShowProcessorA;
import com.spider.pageProcessor.WeatherProcessorA;
import com.spider.pipeline.ShowPipeline;
import com.spider.pipeline.Mypipeline;
import com.util.CityUtil;
import com.util.DateUtil;

import us.codecraft.webmagic.Spider;
@Component
public class SpiderStart {
	private SportProcessorA sportProcessor;
	@Autowired
	private ShowProcessorA eShowProcessor;
	@Autowired
	private  ShowPipeline eShowPipeline;
	private static String URL = "http://www.weather.com.cn/weather/";
	public static void main(String[] args) {
//		start2();

	}
	
	public static void start3(){
		
	}
	
	
}
