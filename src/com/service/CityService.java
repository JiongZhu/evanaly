package com.service;

import com.model.City;
import com.model.Event;

import java.util.List;
import java.util.Map;

/**
 * Created by wx6_2 on 2017/6/18.
 */
public interface CityService {
    List<City> getAllCity();
    boolean hasCity(String name);
	boolean update(City city);
	boolean insert(City city);
	boolean delete(List<Integer> ids);
	List<City> pagingQuery(Map<String, Object> map);

}
