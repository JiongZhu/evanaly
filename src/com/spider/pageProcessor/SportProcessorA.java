package com.spider.pageProcessor;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpUtils;

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
import us.codecraft.webmagic.selector.Selectable;

/**
 * 
 * 体育事件爬虫 数据源：https://www.damai.cn/
 */
@Component
public class SportProcessorA implements PageProcessor {
	@Autowired
	private EventDAO eventDAO;
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	private String startDate;
	private String endDate;
	public static final String URL_Target = "https://piao\\.damai\\.cn/.*\\.html";

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
		if (page.getUrl().regex(URL_Target).match()) {
			processInfo(page);
		} else {
			JSONObject jobj = JSON.parseObject(page.getHtml().xpath(
					"//*body/text()").get());
			String[] ids = jobj.get("ids").toString().split(",");
			System.out.println();
			int size = ids.length;
			for (int i = 0; i < size; i++) {
				page.addTargetRequest("https://piao.damai.cn/" + ids[i].trim()
						+ ".html");
			}
		}
	}

	private void processInfo(Page page) {
		Event e = new Event();
		// 事件名称
		String name = page.getHtml().xpath(
				"//*div[@class='m-goods']/h2/span/text()").get().trim();
		// 时间
		String tmp = page
				.getHtml()
				.xpath(
						"//*div[@id='projectInfo']//div[@class='m-showtime']/div/span/text()")
				.get();
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
//		if (sp.length == 1) {
//			if (sp[0].indexOf('.') < 0) {
//
//			
//
//			} else {
//				e.setStartDate(DateUtil.parse(sp[0]));
//				e.setEndDate(DateUtil.parse(sp[0]));
//			}
//		} else if (sp.length == 2) {
//			e.setStartDate(DateUtil.parse(sp[0]));
//			if (e.getStartDate() == null)
//				e.setStartDate(new Date(System.currentTimeMillis()));
//			e.setEndDate(DateUtil.parse(sp[1]));
//		}
		// 类型
		e.setType(3);
		// 地点
		tmp = page.getHtml().xpath(
				"//*[@id='projectInfo']//div[@class='m-venue']/div/p/a/text()")
				.get();
		sp = tmp.split(" - ");
		if(sp[1].endsWith("市"))
			e.setCity(new City(CityUtil.getId(sp[1].substring(0,sp[1].length()-1))));
		else
			e.setCity(new City(CityUtil.getId(sp[1].substring(0,sp[1].length()))));
		if(e.getCity().getId()==null)
			return;
		// 提取关键字
		List<String> keyWords = MyTextRankKeyword.getKeywordList(name, 2);
		if (eventDAO.searchByKW(e, keyWords).size() > 0) {// 已存在，不需要添加
			return;
		}
		e.setName(name);
		e.setKeyWords(StringUtil.toString(keyWords));
		// 频度
		if (e.getFrequency() == null)
			e.setFrequency(processFrequency(e.getName()));

		// 主办方类型
		e.setSponsorType(processSponsorType(e.getName()));
		// 主办方级别
		e.setSponsorLevel(e.getSponsorType() == 0 ? 1 : 2);
		// 是否固定人参加
		e.setFixedParticipation(processFixedParti(e.getName()));
		// 影响最大范围 -省
		e.setAffectRange(e.getSponsorLevel() == 0 ? 3 : e.getSponsorLevel());
		// 历史悠久
		e.setHistoricalLevel(4 - e.getFrequency());
		// 热度
		e.setHeat(processHeat(e.getFrequency(), e.getAffectRange()));
		// 影响年龄 -成年
		e.setAffectAge(3);
		// 影响群体 -社会大众
		e.setAffectCommunity(2);
		//影响人数
		e.setPnums(OtherUtil.caclNum(e.getHeat(),e.getFrequency(), e.getHistoricalLevel()));
		//数据源
		e.setSrc(Event.DAMAI);
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

	private Date processDate(int projectId) {
		Html page = HtmlUtil
				.download("https://piao.damai.cn/ajax/getInfo.html?projectId="
						+ projectId);
		JSONObject jobj = JSON.parseObject(page.xpath("//*body/text()").get());
		jobj = JSON.parseObject(jobj.getString("Data"));
		JSONArray ja = JSON.parseArray(jobj.getString("performs"));
		jobj = JSON.parseObject(ja.get(0).toString());
		return DateUtil.parse(jobj.get("ShowDate").toString());
	}

	/**
	 * 处理热度
	 * 
	 * @param frequency
	 *            频率
	 * @param affectRange
	 *            影响范围
	 * @return
	 */
	private int processHeat(int frequency, int affectRange) {
		if (affectRange == 3)
			return 3;
		else if (frequency == 3 && affectRange == 2)
			return 3;
		else if (affectRange == 1)
			return 1;
		else
			return 2;

	}

	/**
	 * 处理主办方类型
	 */
	private int processSponsorType(String s) {
		if (s.contains("甲") || s.contains("乙") || s.contains("超")
				|| (!s.contains("锦标赛") && !s.contains("站")))
			return 1;
		else if ((s.contains("锦标赛") && s.contains("站")))
			return 2;
		else if (s.contains("俱乐部"))
			return 0;
		else
			return 1;

	}

	/**
	 * 处理有固定人参加
	 */
	private int processFixedParti(String s) {
		if (s.contains("杯"))
			return 1;
		else if (s.contains("城市俱乐部"))
			return 1;
		else if (s.contains("锦标赛"))
			return Math.random() > 0.5 ? 1 : 0;
		return 0;
	}

	/**
	 * 处理频率
	 * 
	 * @return
	 */
	private int processFrequency(String s) {
		if (s.contains("联赛"))
			return 3;
		else if (s.contains("俱乐部"))
			return 1;
		else
			return 2;
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
