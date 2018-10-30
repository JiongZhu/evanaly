package com.service;

import java.util.Date;
import java.util.List;

import com.model.City;
import com.model.Weather;

public interface WeatherService {
	int insert(Weather w);
	int update(Weather w);
	List<Weather> search(Weather w);
	Weather searchByDateAddr(Date d, City c);
	List<Weather> searchFiveDay(Weather weather);
	List<Weather> weeklyWeatherWran() ;
}
