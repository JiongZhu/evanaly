package com.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class PhoneUtil {

	public static void send(String verifyCode, String phone) {
		HttpClient client = new HttpClient();
		PostMethod post = new PostMethod("http://utf8.sms.webchinese.cn/");
//		PostMethod post = new PostMethod("http://utf8.api.smschinese.cn");
		post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=utf8");
		NameValuePair[] data = { new NameValuePair("Uid", "wx33"), new NameValuePair("Key", "4fae55f1cab41461c036"),
				new NameValuePair("smsMob", phone), new NameValuePair("smsText", "验证码:" + verifyCode) };
		post.setRequestBody(data);

		try {
			client.executeMethod(post);
			post.releaseConnection();
		} catch (HttpException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
