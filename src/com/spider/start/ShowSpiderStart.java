package com.spider.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;

import com.spider.pageProcessor.ShowProcessorA;
import com.spider.pageProcessor.ShowProcessorB;
import com.spider.pipeline.ShowPipeline;
import com.util.DateUtil;
/**
 * 
 * 展会爬虫启动器
 *
 */
@Component
public class ShowSpiderStart {
	@Autowired
	private ShowProcessorA showProcessorA;
	@Autowired
	private ShowProcessorB showProcessorB;
	@Autowired
	private  ShowPipeline showPipeline;
	
	//www.eshow365.com 爬虫启动入口
	public  void startA(){
		String startTime = DateUtil.getTodayString(1);
		String endTime = DateUtil.getOffsetTodayString(30,2);
		Spider s = Spider.create(showProcessorA).addUrl("http://www.eshow365.com/ZhanHui/Ajax/AjaxSearcherV3.aspx?starttime="+startTime+"&endtime="+endTime+"&tag=0&page=1");
		showProcessorA.setStartTime(startTime);
		showProcessorA. setEndTime(endTime);
		s.addPipeline(showPipeline);
		s.thread(3);
		s.start();
	}
	
	// http://www.ex-easy.com 爬虫启动入口
	public void startB(){
		String startTime = DateUtil.getTodayString(3);
		String endTime = DateUtil.getOffsetTodayString(30, 3);
		String time = DateUtil.getNowTime();
		Spider s = Spider.create(showProcessorB).addUrl("http://www.ex-easy.com/pages/web/search.htm?searchList2&searchType=1&beginDt="+startTime+"T"+time+"Z&endDt="+endTime+"T"+time+"Z&from=1&limit=10&pageNo=1");
		showProcessorB.setStartTime(startTime);
		showProcessorB.setEndTime(endTime);
		showProcessorB.setTime(time);
		s.addPipeline(showPipeline);
		s.thread(3);
		s.start();
	}
	
}
