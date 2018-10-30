package com.spider.pageProcessor;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.EventDAO;
import com.hankcs.hanlp.HanLP;
import com.model.City;
import com.model.Event;
import com.util.CityUtil;
import com.util.DateUtil;
import com.util.MyTextRankKeyword;
import com.util.NLPUtil;
import com.util.OtherUtil;
import com.util.StringUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 
 * http://www.ex-easy.com 展会信息爬虫
 * 
 * 
 */
@Component
public class ShowProcessorB implements PageProcessor {

	@Autowired
	private EventDAO eventDAO;

	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	private Set<String> businessSet = OtherUtil.getBusinessSet();
	private String startTime;
	private String endTime;
	private String time;
	public static final String URL_Target = "http://www\\.ex-easy\\.com/.*\\.html";

	public void process(Page page) {
		if (page.getUrl().regex(URL_Target).match()) {// 目标页
			processInfo(page);// 处理数据
		} else {// 列表页
			// 添加目标页到队列
			List<String> all = page.getHtml().xpath(
					"//*div[@class='info']//a/@href").all();// 获取页面列表的链接
			for (int i = 0; i < all.size(); i++) {
				page.addTargetRequest(all.get(i));
			}
			String lastPage = page.getHtml()
					.xpath("//*div[@class=page]/text()").get().trim();
			Integer[] t = StringUtil.findNumber(lastPage);

			int nowPage = Integer.parseInt(page.getHtml().xpath(
					"//*[@id='jumpSel']/option[@selected='selected']/text()")
					.get());
			page.addTargetRequest("http://www.ex-easy.com/pages/web/search.htm?searchList2&searchType=1&beginDt="
							+ startTime
							+ "T"
							+ time
							+ "Z&endDt="
							+ endTime
							+ "T" + time + "Z&from=1&limit=10&pageNo=1");
			if (nowPage < t[0])
				page
						.addTargetRequest("http://www.ex-easy.com/pages/web/search.htm?searchList2&beginDt="
								+ startTime
								+ "T"
								+ time
								+ "Z&endDt="
								+ endTime
								+ "T"
								+ time
								+ "Z&from=1&limit=10&pageNo="
								+ (nowPage + 1));
		}
	}

	private void processInfo(Page page) {
		Map<String, String> map = new HashMap<String, String>();
		// 国外事件直接排除
		String tmp = page.getHtml().xpath(
				"//*div[@class='cm-crumb']/a[2]/text()").get();
		if ("国外展会".equals(tmp.trim()))
			return;
		// 展会名称
		// (行业)+名字 把名字分离出来
		String name = page.getHtml().xpath(
				"//*div[@class='sh-pro-title']/h1/text()").get().trim();
		String sp[] = processName(name);
		String industry = "";
		if (sp.length == 1) {
			name = sp[0];
			industry = "";
		} else {
			industry = sp[0];
			name = sp[1];
		}
		tmp = page.getHtml().xpath(
				"//*div[@class='sh-pro-subtitle']//div[@class='time']/text()")
				.get();
		// 这里事件判重

		// 时间
		Date[] date = DateUtil.getDate(tmp);
		Event e = new Event();
		e.setStartDate(date[0]);
		if (date.length > 1)
			e.setEndDate(date[1]);
		else
			e.setEndDate(date[0]);

		e.setType(1);
		// 城市
		try {
			tmp = page.getHtml().xpath(
					"//*div[@class='sh-pro-info']//a[1]/span/text()").get();
			if ("中国".equals(tmp)||CityUtil.isProvince(tmp)) {
				String tmpc = page.getHtml().xpath(
				"//*div[@class='sh-pro-info']//a[2]/span/text()").get();
				tmpc = NLPUtil.getAddress(tmpc);
				if (tmpc != null)
					tmp = tmpc;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			return;
		}
		if(tmp.endsWith("市"))
			e.setCity(new City(CityUtil.getId(tmp.substring(0,tmp.length()-1))));
		else
			e.setCity(new City(CityUtil.getId(tmp)));
		if(e.getCity().getId()==null)
			return;
		// 提取关键字
		List<String> keyWords = MyTextRankKeyword.getKeywordList(name, 2);
		if (eventDAO.searchByKW(e, keyWords).size() > 0) {// 已存在，不需要添加
			return;
		}
		e.setName(name);
		e.setKeyWords(StringUtil.toString(keyWords));
		// 属性信息先全部获取，然后筛选所需的
		List<String> content = page.getHtml().xpath(
				"//div[@class='sh-pro-info']/div//*text()").all();
		for (int i = 0; i < content.size(); i++) {
			sp = content.get(i).split("：");
			if (sp.length > 1) {
				map.put(sp[0].trim(), sp[1].trim());
			}
		}
		// 举办周期
		// 频率
		tmp = map.get("展会周期");
		Integer t  = 1;
		Integer nums[] = StringUtil.findNumber(tmp);
		if(nums.length==2)
			t = nums[1];
		else if(nums.length==1)
			t = nums[0];
		else
			t = 1;
			         
		if (t < 3)
			e.setFrequency(1);
		else if (t < 5)
			e.setFrequency(2);
		else
			e.setFrequency(3);

		// 举办届数
		tmp = page.getHtml().xpath("//*div[@class='sh-pro-title']/span/text()")
				.get();

		try {
			t = StringUtil.findNumber(tmp)[0];
		} catch (Exception e1) {
			t =1;
		}
		
		// 历史悠久程度
		if (tmp == null) {
			e.setHistoricalLevel(1);
		} else {
			if (t < 5)
				e.setHistoricalLevel(1);
			if (t < 10)
				e.setHistoricalLevel(2);
			else
				e.setHistoricalLevel(3);

		}
		t = StringUtil.findNumber(page.getHtml().xpath(
				"//*div[@class='sh-pro-subtitle']/script").get())[0];
		// 事件热度
		if (t < 2)
			e.setHeat(1);
		else if (t <= 3)
			e.setHeat(2);
		else
			e.setHeat(3);

		// 影响年龄阶段
		e.setAffectAge(3);

		// 影响群体
		e.setAffectCommunity(processAffectCommunity(industry));
		// 固定参加群体
		e.setFixedParticipation(0);
		// 主办方
		// 主办方级别
		tmp = map.get("主办方");
		if (tmp == null)
			tmp = map.get("协办方");
		t = 0;
		if (!StringUtil.isEmpty(industry)) {
			if ("广电文娱".equals(industry))
				e.setSponsorType(1);

			else
				e.setSponsorType(2);
			if (tmp != null) {
				sp = tmp.split(" |、");
				for (String string : sp) {
					t = Math.max(processSponsorLevel(string), t);
				}
				e.setSponsorLevel(t);
			} else {
				e.setSponsorLevel(3);
			}
		} else {
			e.setSponsorType(2);
			e.setSponsorLevel(3);
		}

		// 影响范围
		if (e.getName().contains("国际") || e.getName().contains("中国"))
			e.setAffectRange(1);
		else if (e.getName().contains("购物"))
			e.setAffectRange(3);
		else
			e.setAffectRange(2);
		//影响人数
		e.setPnums(OtherUtil.caclNum(e.getHeat(),e.getFrequency(), e.getHistoricalLevel()));
		//数据源
		e.setSrc(Event.EXSHOW);
		page.putField("event", e);
	}
	//计算影响人数
	private Integer caclNum(String str) {
		int v = 0;
		if (str == null)
			return 8000;
		else {
			Integer[] num = StringUtil.findNumber(str);
			if (num.length > 0)
				v = num[0];
			if (v == 0)
				return 8000;
		}

		return v > 20000 ? 20000 : v;
	}
	//处理主办方等级
	private int processSponsorLevel(String s) {
		if (StringUtil.isEmpty(s))
			return 0;
		else if (s.contains("会") || s.contains("政府") || s.contains("局")) {
			if (s.contains("国"))
				return 1;
			else if (s.contains("省"))
				return 2;
			else
				return 3;
		} else if (s.contains("公司")) {
			return 0;

		}
		return 3;

	}
	//处理影响群体
	private int processAffectCommunity(String s) {
		if (StringUtil.isEmpty(s))
			return 0;
		if (businessSet.contains(s))
			return 1;
		else
			return 2;

	}

	// 把标题拆分成 行业 + 展会名称
	private String[] processName(String title) {
		List<String> list = new ArrayList<String>();
		if (title.startsWith("（") || title.startsWith("(")) {
			int index = -1;
			if(title.contains("）"))
				index = title.indexOf("）");
			else if(title.contains(")"))
					index = title.indexOf(")");
				
			list.add(title.substring(1, index));
			list.add(title.substring(index+1));
		} else {
			list.add(title);
		}
		return list.toArray(new String[] {});
	}

	public Site getSite() {
		return site;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

}
