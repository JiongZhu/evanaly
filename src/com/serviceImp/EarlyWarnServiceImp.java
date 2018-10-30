package com.serviceImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.CityDAO;
import com.DAO.EventDAO;
import com.DAO.PassengerFlowDAO;
import com.model.City;
import com.model.EventSf;
import com.model.PassengerFlow;
import com.model.Route;
import com.service.EarlyWarnService;
import com.util.DateUtil;
@Service
public class EarlyWarnServiceImp implements EarlyWarnService{

	@Autowired
	private PassengerFlowDAO passengerFlowDAO;
	
	@Autowired
	private EventDAO eventDAO;
	public List<PassengerFlow> weeklyPF() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", DateUtil.getOffsetToday(1));
		map.put("endDate", DateUtil.getOffsetToday(7));
		return passengerFlowDAO.searchPFByDate(map);
	}


	public List<Route> getAllRoute() {
		return null;
	}

	public List<EventSf> weeklyCityPF() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("startDate", DateUtil.getOffsetToday(1));
		map.put("endDate", DateUtil.getOffsetToday(7));
		return eventDAO.searchCityPFByDate(map);
	}

}
