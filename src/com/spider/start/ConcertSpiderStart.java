package com.spider.start;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import com.spider.pageProcessor.ConcertProcessorA;
import com.spider.pageProcessor.ConcertProcessorB;
import com.spider.pipeline.ConcertPipeple;
import com.util.DateUtil;
/**
 * 演唱会事件爬虫启动类
 */
@Component
public class ConcertSpiderStart {
	@Autowired
	private ConcertProcessorA concertProcessorA;
	@Autowired
	private ConcertProcessorB concertProcessorB;
	@Autowired
	private ConcertPipeple concertPipeple;
	
	//https://www.damai.cn 爬虫启动入口
	public void startA(){
		String startDate = DateUtil.getTodayString(1);
		String endDate = DateUtil.getOffsetTodayString(30,1);
		concertProcessorA.setStartDate(startDate);
		concertProcessorA.setEndDate(endDate);
		concertProcessorA.setMcid(1);
//		Spider s = Spider.create(concertProcessorA).addUrl("https://www.damai.cn/projectlist.do?mcid="+1+"&d=5&startDate="+startDate+"&endDate="+endDate+"&pageIndex="+(1));
		Spider s = Spider.create(concertProcessorA).addUrl("https://search.damai.cn/searchajax.html?st="+startDate+"&et="+endDate+"&tsg=5&ctl=演唱会");
		s.addPipeline(concertPipeple);
		s.thread(3);
		s.start();
	}
	//http://piao.jd.com/yanchanghui/ 爬虫启动入口
	public void startB(){
		String startDate = DateUtil.getTodayString(2);
		String endDate = DateUtil.getOffsetTodayString(30,2);
		concertProcessorB.setStartDate(startDate);
		concertProcessorB.setEndDate(endDate);
		Spider s = Spider.create(concertProcessorB).addUrl("http://piao.jd.com/yanchanghui/-1--"+startDate+"%2000:00:00-"+endDate+"%2023:59:59-time_select----0--1-10.html");
		s.addPipeline(concertPipeple);
		s.thread(3);
		s.start();
	}

}
