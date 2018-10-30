package com.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

import org.jsoup.Jsoup;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.util.DateUtil;
import com.util.NLPUtil;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

/**
 * http://www.tianqi.com/chinacity.html
 *
 */
public class Test3 implements PageProcessor {
	private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

	public Site getSite() {
		return site;
	}

	public void process(Page page) {
//		File file = new File("E:\\workspace\\JEE\\airline\\src\\city2.txt");
//		PrintWriter pw =null;
//		try {
//			pw = new PrintWriter(new FileWriter(file));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		List<String> list = page.getHtml().xpath("//*[@id='cityall']//ul/li/a").all();
//		Html h ;
//		String link;
//		String name;
//		for (int i = 0; i < list.size(); i++) {
//			h = Html.create(list.get(i));
//			link = h.xpath("//a/@href").get();
//			name = h.xpath("//a/text()").get();
//			pw.write(name+"="+link);
//			pw.println();
//		}
//		pw.close();
		Selectable tmp = page.getHtml().xpath(
		"//*div[@class='sh-pro-info']//a[2]/span/text()");
		System.out.println(tmp);
	}
	public static void main(String[] args) {
		
		Spider.create(new Test3()).addUrl("http://www.ex-easy.com/112101910.html").run();
		
	}

}
