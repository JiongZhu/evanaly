package com.DAO;

import com.model.Flight;

import java.util.List;


public interface FlightDAO {
	int insert(Flight f);
	List<Flight> select(int routeID);
}
