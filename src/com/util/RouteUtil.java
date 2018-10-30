package com.util;

import com.DAO.RouteDAO;
import com.model.Route;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RouteUtil {
	public static Map<Integer, List<Route>> routeMap = new HashMap<Integer, List<Route>>();  
	public static RouteDAO routeDAO;
	static{
		routeDAO = (RouteDAO) ContextUtil.getBean("routeDAO");
		init();
		
	}
	private static void init() {
		List<Route> routeList = routeDAO.search(null);
		List<Route> list;
		for (Route route : routeList) {
			list = routeMap.get(route.getToCity().getId());
			if(list==null){
				list = new ArrayList<Route>();
				routeMap.put(route.getToCity().getId(), list);
			}
			list.add(route);
		}
	}
	
	public static List<Route> getRouteList(int id){
		return routeMap.get(id);
	}

}
