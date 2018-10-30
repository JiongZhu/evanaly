package com.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Pattern;

public class OtherUtil {
	static Set<String> socSet = new HashSet<String>();
	static Set<String> busSet = new HashSet<String>();
	static{
		init();
	}
	

	private static void init() {
		String tmp ="";
		BufferedReader br = null;
		try {
			
			File f = new File(OtherUtil.class.getResource("/").getPath()
					+ "cate.txt");      
	        if(f.isFile()&&f.exists())  
	        {       
	            InputStreamReader read = new InputStreamReader(new FileInputStream(f),"utf-8");       
	            br=new BufferedReader(read);       
	            tmp = br.readLine();
				String info[] = tmp.split("：")[1].split(" ");
				for (String string : info) {
					socSet.add(string);
				}
				tmp = br.readLine();
				info = tmp.split("：")[1].split(" ");
				for (String string : info) {
					busSet.add(string);
				}
				br.close();
	        }     
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public static Set<String> getSocietySet() {
		return socSet;
	}
	public static Set<String> getBusinessSet() {
		
		return busSet;
	}

	/**
	 * heat:x1 frequency:x2 historicalLevel:x3 
	 * 方程 y = a1*x1+a2*x2+a3*x3+a4*x1^2+a5*x2^2+a6*x3^2+a7*x1*x2+a8*x1*x3+a9*x2*x3 
	 * a1 ~ a9: 0.8138 -1.2640 2.9694 0.3481 0.3814 -0.1853 0.5005 -0.2495 -0.2995
	 */
	public static int caclNum(int heat, int frequency, int historicalLevel) {
		return (int) (1000 * (0.8138 * heat - 1.2640 * frequency + 2.9694
				* historicalLevel + 0.3481 * heat * heat + 0.3814 * frequency
				* frequency - 0.1853 * historicalLevel * historicalLevel
				+ 0.5005 * heat * frequency - 0.2495 * heat * historicalLevel -0.2995
				* frequency * historicalLevel));
	}
	
	
	//验证手机格式
	public static boolean validatePhone(String phone){
		Pattern p =  Pattern.compile("1[3,4,5,8]\\d{9}");
		return p.matcher(phone).matches();
	}
	//验证工号格式
	public static boolean validateNo(String no){
		Pattern p =  Pattern.compile("\\d{11}");
		return p.matcher(no).matches();
	}
	//验证邮箱格式
	public static boolean validateEmail(String email){

		Pattern p =  Pattern.compile("^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$");
		return p.matcher(email).matches();

	}

}
