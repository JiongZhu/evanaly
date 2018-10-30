package com.DAO;

import com.model.Weather;

import java.util.List;
import java.util.Map;

public interface WeatherDAO {
	int insert(Weather w);
	int update(Weather w);
	List<Weather> search(Weather w);
	List<Weather> searchFiveDay(Weather weather);
	List<Weather> searchWanrByDate(Map<String, Object> map);
}
