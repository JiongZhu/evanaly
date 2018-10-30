package com.spider.pageProcessor;

import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.EventDAO;
import com.model.City;
import com.model.Event;
import com.util.CityUtil;
import com.util.DateUtil;
import com.util.HtmlUtil;
import com.util.MyTextRankKeyword;
import com.util.OtherUtil;
import com.util.StringUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
/**
 * http://piao.jd.com/yanchanghui/
 * 演唱会爬虫
 */

@Component
public class ConcertProcessorB implements PageProcessor {
	@Autowired
	private EventDAO eventDAO;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	private String startDate;
	private String endDate;
	
	
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
			List<Selectable> nodes = page.getHtml().xpath("//*div[@class='w']/div[@class='c-list']/div[@class='l-content']/div[@class='left']/div[@class='block']").nodes();
			int size = nodes.size();
			for (int i = 0; i <size-1; i++) {
				processInfo(page,nodes.get(i));
			}
			List<String> lis = page.getHtml().xpath("//*div[@class='w']/div[@class='c-list']/div[@class='l-content']//div[@class='cj-mRight20']/a/@href").all();
			size = lis.size();
			for (int i = 0; i < size; i++) {
				page.addTargetRequest(lis.get(i));
			}
			String nextInfo = page.getHtml().xpath("//*div[@class='cj-bBottom0']//page[@class='pagin']//span[@class='next-disabled']").get();
			String nowPageInfo = page.getHtml().xpath("//*div[@class='cj-bBottom0']//div[@class='pagin']//a[@class='current']/text()").get();
			if(nextInfo==null&&nowPageInfo!=null){
				try {
					int nowPage = Integer.parseInt(nowPageInfo);
					page.addTargetRequest("http://piao.jd.com/yanchanghui/-47--"+startDate+"%2000:00:00-"+endDate+"%2023:59:59-time_select----0--"+(nowPage+1)+"-10.html");
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
		System.out.println(page.getUrl());
	}
	
	/**
	 *  处理信息过程 
	 */
	private void processInfo(Page page,Selectable node) {
		Event e = new Event();
		//事件名称
		String name = node.xpath("//*div[@class='name']//a/text()").get().trim();
		//时间
		String tmp = node.xpath("//*div[@class='show-time']//span/text()").get();
		Date []date = DateUtil.getDate(tmp);
		e.setStartDate(date[0]);
		if(date.length==1)
			e.setEndDate(date[0]);
		else
			e.setEndDate(date[1]);
			
		String sp[] ;
		//类型
		e.setType(2);
		tmp = getCity(node);
//		//地点
		if(tmp.endsWith("市"))
			e.setCity(new City(CityUtil.getId(tmp.substring(0,tmp.length()-1))));
		else
			e.setCity(new City(CityUtil.getId(tmp.substring(0,tmp.length()))));
		if(e.getCity().getId()==null)
			return;
		//提取关键字
	    List<String> keyWords = MyTextRankKeyword.getKeywordList(name, 2);
	    if(eventDAO.searchByKW(e, keyWords).size()>0){//已存在，不需要添加
	    	return ;
	    }
	    e.setName(name);
	    e.setKeyWords(StringUtil.toString(keyWords));
		System.out.println(name);
		//频率
		if(e.getName().contains("巡回"))
			e.setFrequency(3);
		else
			e.setFrequency(1);
			
		//热度
		e.setHeat(e.getFrequency());
		//历史悠久
		if(e.getHeat()==1)
			e.setHistoricalLevel(2);
		else
			e.setHistoricalLevel(3);
		
		//影响最大范围 -市
		e.setAffectRange(2);
		//影响年龄  -成年
		e.setAffectAge(Pattern.matches(".*\\w+.*", e.getName())?2:3);
		//影响群体 -社会大众
		e.setAffectCommunity(2);
		//是否固定人参加
		e.setFixedParticipation(0);
		
		
		//主办方类型
		e.setSponsorType(0);
		//主办方级别
		e.setSponsorLevel(0);
		//影响人数
		e.setPnums(OtherUtil.caclNum(e.getHeat(),e.getFrequency(), e.getHistoricalLevel()));
		e.setSrc(Event.JDPIAO);
		page.putField("event", e);
	}
	//获取城市
	private String getCity(Selectable node) {
		
		String tmp = node.xpath("//*div[@class='show-address']//a[@class='cj-shark']/@href").get();
		System.out.println(tmp);
		Html html = HtmlUtil.download(tmp);
		tmp = html.xpath("/html/body/div[3]/div/span[1]/a/@href").get();
		html = HtmlUtil.download(tmp);
		tmp = html.xpath("//*[@id='stype']/a/text()").get();
		return tmp;
	}
	//计算人数
	private  int caclNum(int h,int his){
		if(h==3&&his==3)
			return 2;
		else 
			return 1;
	}
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}


}
