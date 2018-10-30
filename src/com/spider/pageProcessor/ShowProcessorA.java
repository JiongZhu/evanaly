package com.spider.pageProcessor;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.lf5.util.DateFormatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.EventDAO;
import com.model.City;
import com.model.Event;
import com.util.CityUtil;
import com.util.DateUtil;
import com.util.MyTextRankKeyword;
import com.util.OtherUtil;
import com.util.StringUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

/**
 * 
 * http://www.eshow365.com/ 展会信息抓取处理器
 * 
 * 
 */
@Component
public class ShowProcessorA implements PageProcessor {

	@Autowired
	private EventDAO eventDAO;
	private Map<String, Integer> levelMap = new HashMap<String, Integer>();
	private Set<String> businessSet = OtherUtil.getBusinessSet();
	private Site site = Site.me().setRetryTimes(3).setSleepTime(200);
	private String startTime;
	private String endTime;
	public static final String URL_Target = "http://www\\.eshow365\\.com/zhanhui/html/.*\\.html";
	public static String URL = "http://www.eshow365.com/ZhanHui/Ajax/AjaxSearcherV3.aspx?";

	public void process(Page page) {
		if (page.getUrl().regex(URL_Target).match()) {// 目标页
			processInfo(page);// 处理数据
		} else {// 列表页
			// 添加目标页到队列
			List<String> all = page.getHtml().xpath("//*div[@class='sslist']")
					.all();// 获取页面列表的链接
			String link;
			String l;
			for (int i = 1; i < all.size(); i++) {
				link = Html.create(all.get(i)).xpath(
						"//p[@class='zhtitle']/a/@href").get();
				l = Html.create(all.get(i)).xpath("//p[@class='xj']/img/@src")
						.get();
				// System.out.println(l.charAt(l.lastIndexOf('/')+1));
				if (link != null && l != null) {
					page.addTargetRequest(link);
					levelMap.put(link, l.charAt(l.lastIndexOf('/') + 1) - '0');// 添加热度属性
				}
			}
			String lastPage = page.getHtml().xpath(
					"//*[@id='pagestr']/li[4]/a[2]/@rel").get();
			if (lastPage != null) {
				String nowPage = page.getHtml().xpath(
						"//*[@id='pagestr']/li[3]/span/text()").get();
				page.addTargetRequest(URL + "starttime=" + startTime
						+ "&endtime=" + endTime + "&tag=0&page="
						+ (Integer.parseInt(nowPage) + 1));
				// System.out.println(nowPage);
			}
		}
		System.out.println(page.getUrl());
	}

	private void processInfo(Page page) {
		Map<String, String> map = new HashMap<String, String>();
		// 展会名称
		String name = page.getHtml().xpath(
				"//div[@class='zhmaincontent']/h1/text()").get().trim();
		// 属性信息先全部获取，然后筛选所需的
		List<String> content = page.getHtml().xpath(
				"//div[@class='zhxxcontent']/p/text()").all();
		String[] info;
		Integer t = null;
		for (int i = 0; i < content.size(); i++) {
			info = content.get(i).split("：");
			if (info.length > 1) {
				map.put(info[0].trim(), info[1].trim());
			}
		}
		// 时间
		String tmp = map.get("举办时间"); // 2017/4/27---2017/4/29 格式需要处理
		String sp[] = tmp.split("---");
		// 这里事件判重
		Date startDate = DateUtil.parse(sp[0]);
		Date endDate = DateUtil.parse(sp[1]);
		Event e = new Event();
		e.setStartDate(startDate);
		e.setEndDate(endDate);
		// 类型
		e.setType(1);
		// 城市
		tmp = map.get("展会城市");
		sp = tmp.split("\\|");
		
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
		// 举办周期
		tmp = map.get("举办周期");
		
		// 频率
		if ("常年".equals(tmp)) {
			e.setFrequency(3);
		} else if (tmp != null) {
			t = StringUtil.convertNumber(tmp.charAt(2) + "");
			if (t < 2)
				e.setFrequency(1);
			else if (t < 3)
				e.setFrequency(2);
			else
				e.setFrequency(3);
		} else {
			e.setFrequency(1);

		}

		// 历史悠久程度
		tmp = map.get("举办届数");
		if (tmp == null) {
			e.setHistoricalLevel(2);
		} else {
			t = Integer.parseInt(tmp.substring(0, tmp.length() - 1));
			if (t < 5)
				e.setHistoricalLevel(1);
			if (t < 10)
				e.setHistoricalLevel(2);
			else
				e.setHistoricalLevel(3);
		}
		// 事件热度
		t = levelMap.remove(page.getUrl().toString());
		if (t < 2)
			e.setHeat(1);
		else if (t <= 3)
			e.setHeat(2);
		else
			e.setHeat(3);

		// 影响群体
		// 根据行业判断
		tmp = map.get("所属行业");
		e.setAffectCommunity(processAffectCommunity(tmp));
		// 固定参加群体
		e.setFixedParticipation(0);

		// 影响年龄阶段
		e.setAffectAge(3);

		// 主办方类型
		// 主办方级别

		if (!StringUtil.isEmpty(tmp)) {
			if ("文化艺术".equals(tmp))
				e.setSponsorType(1);
			else
				e.setSponsorType(2);
			tmp = map.get("主办单位");
			sp = tmp.split(" |、");
			for (String string : sp) {
				t = Math.max(processSponsorLevel(string), t);
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
		e.setSrc(Event.ESHOW365);
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

}
