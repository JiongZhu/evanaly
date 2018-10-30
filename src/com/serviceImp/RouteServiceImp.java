package com.serviceImp;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.RouteDAO;
import com.github.pagehelper.PageHelper;
import com.model.City;
import com.model.Route;
import com.service.RouteService;

@Service
public class RouteServiceImp implements RouteService{
	@Autowired
	private RouteDAO routeDAO;
	
	public boolean delete(List<Integer> ids) {
		return routeDAO.delete(ids)>0;
	}

	public boolean insert(Route route) {
		
		return (routeDAO.search(route).size()==0)&&(routeDAO.insert(route)>0);
	}

	public List<City> pagingQuery(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		PageHelper.startPage(page, 8);
		return routeDAO.pagingQuery(map);
	}

	public boolean update(Route route) {
		
		return routeDAO.update(route)>0;
	}

	public List<Route> getAllRoute() {
		return routeDAO.search(null);
	}

}
