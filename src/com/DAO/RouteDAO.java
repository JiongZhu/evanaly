package com.DAO;

import com.model.City;
import com.model.Route;

import java.util.List;
import java.util.Map;

public interface RouteDAO {
	List<Route> search(Route route);
	int update(Route route);
	int insert(Route route);
	int delete(List<Integer> ids);
	List<City> pagingQuery(Map<String, Object> map);
}
