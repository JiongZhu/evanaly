package com.DAO;

import com.model.WeatherURL;

import java.util.List;

public interface WeatherURLDAO {
	List<WeatherURL> selectAll();
	int insert(WeatherURL o);
	int update(WeatherURL o);
	List<WeatherURL> query(String name);
	int delete(List<Integer> ids);

}
