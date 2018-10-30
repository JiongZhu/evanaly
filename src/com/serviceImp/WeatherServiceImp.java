package com.serviceImp;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.WeatherDAO;
import com.model.City;
import com.model.EventSf;
import com.model.Weather;
import com.service.WeatherService;
import com.util.DateUtil;
@Service
public class WeatherServiceImp implements WeatherService {
	@Autowired
	private WeatherDAO weatherDAO;
	public int insert(Weather w) {
		return weatherDAO.insert(w);
	}

	public List<Weather> search(Weather w) {
		
		return weatherDAO.search(w);
	}
	/**
	 * 根据时间和地点查询
	 */
	public Weather searchByDateAddr(Date d, City c) {
		Weather w = new Weather();
		w.setDate(d);
		w.setCity(c);
		List<Weather> search = weatherDAO.search(w);
		return search.size()==0?null:search.get(0);
	}
	public List<Weather> weeklyWeatherWran() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", DateUtil.getOffsetToday(1));
		map.put("endDate", DateUtil.getOffsetToday(7));
		return weatherDAO.searchWanrByDate(map);
	}
	public int update(Weather w) {
		return weatherDAO.update(w);
	}
	public List<Weather> searchFiveDay(Weather weather) {
		return weatherDAO.searchFiveDay(weather);
	}
}
