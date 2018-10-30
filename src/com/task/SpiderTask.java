package com.task;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.PassengerFlowService;
import com.spider.start.ConcertSpiderStart;
import com.spider.start.ShowSpiderStart;
import com.spider.start.SportSpiderStart;
import com.spider.start.WeatherSpiderStart;
/**
 *任务定时器 
 *
 */
@Component
public class SpiderTask {
	@Autowired
	private ConcertSpiderStart concertSpiderStart;
	@Autowired
	private ShowSpiderStart showSpiderStart;
	@Autowired
	private SportSpiderStart sportSpiderStart;
	@Autowired
	private WeatherSpiderStart weatherSpiderStart;
	@Autowired
	private PassengerFlowService passengerFlowService;
	/**
	 * 事件爬虫任务 每天0点和12触发
	 */
	@Scheduled(cron = "0 0 0,12  * * ? ")
	public void eventTask() {
		System.out.println(new Timestamp(System.currentTimeMillis())+"------eventTask开始------");
		concertSpiderStart.startA();
		concertSpiderStart.startB();
		showSpiderStart.startA();
		showSpiderStart.startB();
		sportSpiderStart.startA();
		sportSpiderStart.startB();
		weatherSpiderStart.startA();
		weatherSpiderStart.startB();
	}
	/**
	 * 计算影响客流量 在事件爬虫任务30分钟后启动
	 */
	@Scheduled(cron = "0 30 0,12  * * ? ")
	public void caclPassengerFlowTask() {
		System.out.println(new Timestamp(System.currentTimeMillis())+"----caclPassengerFlowTask开始----");
		passengerFlowService.caclPassengerFlow();
	}
	
	

}
