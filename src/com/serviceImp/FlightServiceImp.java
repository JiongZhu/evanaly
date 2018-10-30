package com.serviceImp;

import com.DAO.FlightDAO;
import com.model.Flight;
import com.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:航班服务层
 * @Author:wx6_2
 * @Date:2017/6/24
 **/
@Service
public class FlightServiceImp implements FlightService {
    @Autowired
    private FlightDAO flightDAO;

    public List<Flight> select(int routeID) {
        return flightDAO.select(routeID);
    }
}
