package com.test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

import com.util.DateUtil;

public class Test2 implements PageProcessor {
	private static BufferedWriter bw;
	private static BufferedReader br;
	private static Set<String> set = new HashSet<String>();
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	public static final String URL_Target ="http://www\\.eshow365\\.com/zhanhui/html/.*\\.html";
	public static String URL = "http://www.eshow365.com/ZhanHui/Ajax/AjaxSearcherV3.aspx?tag=0";
	public Site getSite() {
		return site;
	}



	public void process(Page page) {
		if(page.getUrl().regex(URL_Target).match()){//目标页
	 		processInfo(page);//处理数据
	 	}else{//列表页
	 		//添加目标页到队列
	 		List<String> all = page.getHtml().links().regex(URL_Target).all();//获取页面列表的链接
	 		page.addTargetRequests(all);
	 		String lastPage = page.getHtml().xpath("//*[@id='pagestr']/li[4]/a[2]/@rel").get();
	 		if(lastPage!=null){
	 			String nowPage = page.getHtml().xpath("//*[@id='pagestr']/li[3]/span/text()").get();
	 			page.addTargetRequest(URL+"&page="+(Integer.parseInt(nowPage)+1));
	 			System.out.println(nowPage);
	 		}
	 	}
//	   System.out.println(page.getUrl());
	}

	
	private void processInfo(Page page) {
		Map<String, String> map = new HashMap<String, String>();
		List<String> content = page.getHtml().xpath(
				"//div[@class='zhxxcontent']/p/text()").all();
		String[] info;
		for (int i = 0; i < content.size(); i++) {
			info = content.get(i).split("：");
			if (info.length > 1) {
				map.put(info[0].trim(), info[1].trim());
			}
		}
		String s = map.get("主办单位");
		
		if(s!=null){
			info = s.split(" ");
			for (String string : info) {
				set.add(string.trim());
				try {
					bw.write(string.trim());
					bw.newLine();
					bw.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}



	public static void main(String[] args) {
		try {
			bw = new BufferedWriter(new FileWriter("ass.txt",true));
			br = new BufferedReader(new FileReader("ass.txt"));
			String s;
			while((s=br.readLine())!=null){
				set.add(s);
			}
		Spider.create(new Test2()).addUrl(URL+"&page=230").run();
//		for (String string : set) {
//			bw.write(string);
//			bw.newLine();
//			bw.flush();
//		}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
