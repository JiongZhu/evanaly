package com.spider.pageProcessor;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.EventDAO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
/**
 * https://www.damai.cn/
 * 音乐会爬虫
 */

@Component
public class ConcertProcessorA implements PageProcessor {
	@Autowired
	private EventDAO eventDAO;
	private Map<String, Integer> cityMap;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	private String startDate;
	private String endDate;
	private int mcid ;
	public static final String URL_Target = "https://piao\\.damai\\.cn/.*\\.html";
	
	
	
	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if(page.getUrl().regex(URL_Target).match()){
			processInfo(page);
		}else{
			JSONObject jobj=JSON.parseObject(page.getHtml().xpath("//*body/text()").get()); 
			String[] ids = jobj.get("ids").toString().split(",");
			System.out.println();
			int size = ids.length;
			for (int i = 0; i < size; i++) {
				page.addTargetRequest("https://piao.damai.cn/"+ids[i].trim()+".html");
			}
		}
		System.out.println(page.getUrl());
	}
	/**
	 *  处理信息过程 
	 */
	private void processInfo(Page page) {
		Event e = new Event();
		//事件名称
		String name = page.getHtml().xpath("//*div[@class='m-goods']/h2/span/text()").get().trim();
		//时间
		String tmp = page.getHtml().xpath("//*div[@id='projectInfo']//div[@class='m-showtime']/div/span/text()").get();
		String sp[] = tmp.split("-");
		
		if (sp[0].equals("常年"))
			e.setFrequency(3);
		else
			e.setFrequency(1);
		// 获取最近一场演出
		try {
			// 这里要解析ajax生成的数据
			e.setStartDate(processDate(StringUtil.findNumber(page
					.getUrl().toString())[0]));
			e.setEndDate(e.getStartDate());
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
//		if(sp.length==1){
//			if(sp[0].indexOf('.')<0){
//				if(sp[0].equals("常年"))
//					e.setFrequency(3);
//				else
//					e.setFrequency(1);
//					
//				//获取最近一场演出
//				try {
//					//这里要解析ajax生成的数据
//					e.setStartDate(processDate(StringUtil.findNumber(page.getUrl().toString())[0]));
//					e.setEndDate(e.getStartDate());
//				} catch (Exception e1) {
//					e1.printStackTrace();
//					return ;
//				}
//				
//			}else{
//				e.setStartDate(DateUtil.parse(sp[0]));
//				e.setEndDate(DateUtil.parse(sp[0]));
//			}
//		}else if(sp.length==2){
//			e.setStartDate(DateUtil.parse(sp[0]));
//			
//			e.setEndDate(DateUtil.parse(sp[1]));
//		}
		//类型
		e.setType(2);
		//地点
		tmp = page.getHtml().xpath("//*[@id='projectInfo']//div[@class='m-venue']/div/p/a/text()").get();
		sp = tmp.split(" - ");
		if(sp[1].endsWith("市"))
			e.setCity(new City(CityUtil.getId(sp[1].substring(0,sp[1].length()-1))));
		else
			e.setCity(new City(CityUtil.getId(sp[1].substring(0,sp[1].length()))));
		if(e.getCity().getId()==null)
			return;
			
		//提取关键字
	    List<String> keyWords = MyTextRankKeyword.getKeywordList(name, 2);
	    if(eventDAO.searchByKW(e, keyWords).size()>0){//已存在，不需要添加
	    	return ;
	    }
	    e.setName(name);
	    e.setKeyWords(StringUtil.toString(keyWords));
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
		
		//数据源
		e.setSrc(Event.DAMAI);
		page.putField("event", e);
	}

	private Date processDate(int projectId) {
		Html page = HtmlUtil.download("https://piao.damai.cn/ajax/getInfo.html?projectId="+projectId);
		JSONObject jobj=JSON.parseObject(page.xpath("//*body/text()").get());  
 		jobj = JSON.parseObject(jobj.getString("Data"));
 		JSONArray ja = JSON.parseArray(jobj.getString("performs"));
 		jobj = JSON.parseObject(ja.get(0).toString());
 		return DateUtil.parse(jobj.get("ShowDate").toString());
	}
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

	public int getMcid() {
		return mcid;
	}

	public void setMcid(int mcid) {
		this.mcid = mcid;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public Map<String, Integer> getCityMap() {
		return cityMap;
	}

	public void setCityMap(Map<String, Integer> cityMap) {
		this.cityMap = cityMap;
	}


}
