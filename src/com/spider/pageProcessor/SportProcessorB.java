package com.spider.pageProcessor;

import java.util.Date;
import java.util.List;

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
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;
/**
 * http://piao.jd.com/tiyusaishi/
 * 体育赛事爬虫
 */

@Component
public class SportProcessorB implements PageProcessor {
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
			for (int i = 0; i <nodes.size()-1; i++) {
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
					page.addTargetRequest("http://piao.jd.com/tiyusaishi/-47--"+startDate+"%2000:00:00-"+endDate+"%2023:59:59-time_select----0--"+(nowPage+1)+"-10.html");
				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}
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
		e.setType(3);
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
		//频度
		if(e.getFrequency()==null)
			e.setFrequency(processFrequency(e.getName()));
		
		
		//主办方类型
		e.setSponsorType(processSponsorType(e.getName()));
		//主办方级别
		e.setSponsorLevel(e.getSponsorType()==0?1:2);
		//是否固定人参加
		e.setFixedParticipation(processFixedParti(e.getName()));
		//影响最大范围 -省
		e.setAffectRange(e.getSponsorLevel()==0?3:e.getSponsorLevel());
		//历史悠久
		e.setHistoricalLevel(4-e.getFrequency());
		//热度
		e.setHeat(processHeat(e.getFrequency(),e.getAffectRange()));
		//影响年龄  -成年
		e.setAffectAge(3);
		//影响群体 -社会大众
		e.setAffectCommunity(2);
		//影响人数
		e.setPnums(OtherUtil.caclNum(e.getHeat(),e.getFrequency(), e.getHistoricalLevel()));
		//数据源
		e.setSrc(Event.JDPIAO);
		page.putField("event", e);
	}
	//计算人数
	private  int caclNum(int h,int sponsor){
		if(sponsor==1){
			if(h==3)
				return 5;
			else
				return 4;
		}else if(sponsor==2){
			if(h==3)
				return 3;
			else
				return 2;
		}
		else 
			return 1;
	}

	
	
	/**
	 * 处理热度
	 * @param frequency 频率
	 * @param affectRange 影响范围
	 * @return
	 */
	private int processHeat(int frequency, int affectRange) {
		if(affectRange==3)
			return 3;
		else if(frequency==3&&affectRange==2)
			return 3;
		else if(affectRange==1)
			return 1;
		else 
			return 2;
			
	}
	/**
	 * 处理主办方类型 
	 */
	private int processSponsorType(String s) {
		if(s.contains("甲")||s.contains("乙")||s.contains("超")||(!s.contains("锦标赛")&&!s.contains("站")))
			return 1;
		else if((s.contains("锦标赛")&&s.contains("站")))
			return 2;
		else if(s.contains("俱乐部"))
			return 0;
		else
			return 1;
			
	}
	/**
	 * 处理有固定人参加
	 */
	private int processFixedParti(String s) {
		if(s.contains("杯"))
			return 1;
		else if(s.contains("城市俱乐部"))
			return 1;
		else if(s.contains("锦标赛"))
			return Math.random()>0.5?1:0;
		return 0;
	}
	/**
	 * 处理频率 
	 * @return
	 */
	private int processFrequency(String s) {
		if(s.contains("联赛"))
			return 3;
		else if(s.contains("俱乐部"))
			return 1;
		else 
			return 2;
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
