package com.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	static Pattern p = Pattern.compile("\\d+");
	static Map<String, Integer> numMap = new HashMap<String, Integer>();
	static {
		numMap.put("一", 1);
		numMap.put("二", 2);
		numMap.put("三", 3);
		numMap.put("四", 4);
		numMap.put("五", 5);
		numMap.put("六", 6);
		numMap.put("七", 7);
		numMap.put("八", 8);
	}

	/**
	 * 将中文数字 转换为阿拉伯数字
	 */
	public static int convertNumber(String s) {
		Integer i = numMap.get(s);
		if (i == null)
			return 10;
		return i;
	}

	// 获取字符串中的数字
	public static Integer[] findNumber(String s) {
		Matcher m = p.matcher(s);
		List<Integer> l = new ArrayList<Integer>();
		while (m.find()) {
			l.add(Integer.parseInt(m.group()));
		}
		return l.toArray(new Integer[] {});
	}

	public static boolean isEmpty(String s) {
		if (s == null || "".equals(s.trim()))
			return true;
		return false;
	}

	public static String toString(List<String> keyWords) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < keyWords.size(); i++) {
			sb.append(keyWords.get(i));
			sb.append(" ");
		}
		return sb.toString();
	}

}
