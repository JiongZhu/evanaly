package com.service;

import com.model.PassengerFlow;

import java.util.List;
import java.util.Map;

public interface PassengerFlowService {
	void caclPassengerFlow();
	List<PassengerFlow> searchPF(Map<String, Object> map);
	PassengerFlow initPFChart(Map<String, Object> map);
}
