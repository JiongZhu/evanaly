package com.service;

import com.model.Flight;

import java.util.List;

/**
 * Created by wx6_2 on 2017/6/24.
 */
public interface FlightService {
    List<Flight> select(int routeID);
}
