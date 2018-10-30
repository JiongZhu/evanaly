package com.service;

import java.util.List;

import com.model.EventSf;
import com.model.PassengerFlow;
import com.model.Route;

public interface EarlyWarnService {
	public List<PassengerFlow> weeklyPF();
	public List<EventSf> weeklyCityPF();
	public List<Route> getAllRoute();
	
}
