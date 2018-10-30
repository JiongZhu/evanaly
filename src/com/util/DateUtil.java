package com.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
	
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
	private static SimpleDateFormat sdf3 = new SimpleDateFormat("yyyyMMdd");
	private static SimpleDateFormat sdf4 = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat sdf5 = new SimpleDateFormat("HH:mm:ss");
	private static Pattern pattern = Pattern.compile("\\d+-\\d+-\\d+");
	//参数m为formate方式
	//1：yyyy/MM/dd   2：yyyyMMdd
	public static String getTodayString(int m){
		if(m==1)
			return sdf.format(System.currentTimeMillis());
		else if(m==2)
			return sdf3.format(System.currentTimeMillis());
		else 
			return sdf4.format(System.currentTimeMillis());
	}
	
	
	public static Date getOffsetToday(int offset){
		long today = System.currentTimeMillis();
		Date d = new Date(today+24L*3600*1000*(offset));
		return d;
	}
	public static java.sql.Date getOffsetDate(Date d ,int offset){
		return new java.sql.Date(d.getTime()+24L*3600*1000*(offset));
	}
	
	//参数m为formate方式
	public static String getOffsetTodayString(int offset,int m){
		long today = System.currentTimeMillis();
		Date d = new Date(today+24L*3600*1000*(offset));
		if(m==1)
			return sdf.format(d);
		else if(m==2)
			return sdf3.format(d);
		else
			return sdf4.format(d);
		
	}
	public static Date parse(String s){
		try {
			if(s.indexOf('.')<0){
				
				return sdf.parse(s);
			}
			else{
				//时间可能不符合要求 比如只有月份和日  没有年份
				if(s.length()<8)
					s = Calendar.getInstance().get(1)+"."+s;
				return sdf2.parse(s);
			}
				
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getNowTime(){
		return sdf5.format(System.currentTimeMillis());
	}

	public static Date[] getDate(String t) {
		List<Date> l  = new ArrayList<Date>();
		Matcher m = pattern.matcher(t);
		while(m.find()){
			try {
				l.add(sdf4.parse(m.group()));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return  l.toArray(new Date[]{});
	}
	
	
}
