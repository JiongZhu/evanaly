package com.test;

import com.DAO.WeatherURLDAO;
import com.model.City;
import com.model.WeatherURL;
import com.util.ContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Test4{
	static ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-*.xml");
	public static void main(String[] args) {
//		ModelMapper dao = (ModelMapper) ContextUtil.getBean("modelMapper");
		WeatherURLDAO dao = (WeatherURLDAO) ContextUtil.getBean("weatherURLDAO");
//		EventDAO eventDAO = (EventDAO) ContextUtil.getBean("eventDAO");
//		List<String> list = new ArrayList<String>();
//		list.add("test");
//		list.add("mode");
//		List<Event> search = eventDAO.searchByKW(new Event(), list);
		Map<String, WeatherURL> m = new HashMap<String, WeatherURL>();
//		List<WeatherURL> urls = dao.selectAll();
//		for (WeatherURL w : urls) {
//			m.put(w.getCity(), w);
//		}
		try {
			Map<String, String> map = new HashMap<String, String>();
			BufferedReader br = new BufferedReader(new FileReader("F:\\RES\\软件杯\\w2.txt"));
//			BufferedWriter bw = new BufferedWriter(new FileWriter("F:\\RES\\软件杯\\w2.txt"));
			Set<String> set = new HashSet<String>();
			String s =null;
			String sp[];
			while((s=br.readLine())!=null){
				if(s.trim().equals(""))
					continue;
//				set.add(s);
				sp = s.split("=");
				WeatherURL w = new WeatherURL();
				City city = new City();
				city.setName(sp[0].trim());
				w.setCity(city);
//				w.setUrlA("http://www.weather.com.cn/weather/"+sp[1].trim()+".shtml");
//				dao.insert(w);
				w.setUrlB(sp[1].trim());
				dao.update(w);
//				map.put(sp[0].trim(), sp[1]);
			}
//			System.out.println(map.size());
//			for (String string : set) {
//				bw.write(string);
//				bw.newLine();
//				bw.flush();
//			}
//			br = new BufferedReader(new FileReader("F:\\RES\\软件杯\\w1.txt"));
//			while((s=br.readLine())!=null){
//				if(s.trim().equals(""))
//					continue;
//				sp = s.split("=");
//				if(map.get(sp[0])==null){
//					System.out.println(s.trim());
//				}
//				else{
//					bw.write(sp[0]+"="+map.get(sp[0]));
//					bw.newLine();
//					bw.flush();
//				}
//					
//			}
//			
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
