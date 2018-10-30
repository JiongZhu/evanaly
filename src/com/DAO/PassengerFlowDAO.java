package com.DAO;

import com.model.PassengerFlow;
import com.model.Route;

import java.util.List;
import java.util.Map;

public interface PassengerFlowDAO {
	List<PassengerFlow> searchAll();
	int insert(PassengerFlow pf);
	List<Route> searchByRouteDate(PassengerFlow pf);
	int updateByRouteDate(PassengerFlow pfTmp);
	List<PassengerFlow> search(Map<String, Object> map);
	PassengerFlow groupByType(Map<String, Object> map);
	List<PassengerFlow> searchPFByDate(Map<String, Object> map);
}
