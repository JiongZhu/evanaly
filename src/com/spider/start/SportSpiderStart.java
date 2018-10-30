package com.spider.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import us.codecraft.webmagic.Spider;

import com.spider.pageProcessor.SportProcessorA;
import com.spider.pageProcessor.SportProcessorB;
import com.spider.pipeline.SportPipeline;
import com.util.DateUtil;
/**
 * 
 *  体育赛事爬虫启动类
 */
@Component
public class SportSpiderStart {
	@Autowired
	private SportProcessorA sportProcessorA;
	@Autowired
	private SportProcessorB sportProcessorB;
	@Autowired
	private SportPipeline sportPipeline;
	
	//https://www.damai.cn 爬虫启动入口
	public void startA(){
		String startDate = DateUtil.getTodayString(2);
		String endDate = DateUtil.getOffsetTodayString(30,2);
		sportProcessorA.setStartDate(startDate);
		sportProcessorA.setEndDate(endDate);
//		Spider s = Spider.create(sportProcessorA).addUrl("https://www.damai.cn/projectlist.do?mcid="+6+"&d=5&startDate="+startDate+"&endDate="+endDate+"&pageIndex="+(1));
		Spider s = Spider.create(sportProcessorA).addUrl("https://search.damai.cn/searchajax.html?st="+startDate+"&et="+endDate+"&tsg=5&ctl=体育比赛");
		s.addPipeline(sportPipeline);
		s.thread(3);
		s.start();
	}
	//http://piao.jd.com/tiyusaishi/ 爬虫启动入口
	public void startB(){
		String startDate = DateUtil.getTodayString(2);
		String endDate = DateUtil.getOffsetTodayString(30,2);
		sportProcessorB.setStartDate(startDate);
		sportProcessorB.setEndDate(endDate);
		Spider s = Spider.create(sportProcessorB).addUrl("http://piao.jd.com/tiyusaishi/-47--"+startDate+"%2000:00:00-"+endDate+"%2023:59:59-time_select----0--1-10.html");
		s.addPipeline(sportPipeline);
		s.thread(3);
		s.start();
	}
}
