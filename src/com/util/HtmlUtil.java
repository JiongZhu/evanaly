package com.util;

import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.selector.Html;

public class HtmlUtil {
	private static  HttpClientDownloader downloader = new HttpClientDownloader();
	public static Html download(String url){
		return downloader.download(url);
	}

}
