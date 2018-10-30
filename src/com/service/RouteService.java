package com.service;

import java.util.List;
import java.util.Map;

import com.model.City;
import com.model.Route;

public interface RouteService {
	boolean update(Route route);
	boolean insert(Route route);
	boolean delete(List<Integer> ids);
	List<City> pagingQuery(Map<String, Object> map);
	List<Route> getAllRoute();
}
