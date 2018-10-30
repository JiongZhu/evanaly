package com.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.model.Flight;
import com.model.PassengerFlow;
import com.service.FlightService;
import com.service.PassengerFlowService;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Description:客流量控制层
 * @Author:wx6_2
 * @Date:2017/6/23
 **/
@Controller
public class PassengerFlowController {
    @Autowired
    private PassengerFlowService passengerFlowService;
    @Autowired
    private FlightService flightService;

    @RequestMapping(value = "/searchPF", method = RequestMethod.POST)
    @ResponseBody
    public void searchPF(@RequestBody Map map, HttpServletResponse response, HttpSession session) {
        List<PassengerFlow> list = passengerFlowService.searchPF(map);
        String resp = "{\"data\":" + JSON.toJSONString(list) + ", \"count\":" + ((Page) list).getPages() + "}";
        ResponseUtil.write(response, resp);
    }

    @RequestMapping(value = "/initPFChart", method = RequestMethod.POST)
    @ResponseBody
    public void initPFChart(@RequestBody Map map, HttpServletResponse response, HttpSession session) {
        PassengerFlow passengerFlow = passengerFlowService.initPFChart(map);
        ResponseUtil.write(response, JSON.toJSONString(passengerFlow));
    }

    @RequestMapping(value = "/getFlights", method = RequestMethod.GET)
    @ResponseBody
    public void getFlights(Integer routeID, HttpServletResponse response, HttpSession session) {
        List<Flight> flights = flightService.select(routeID);
        ResponseUtil.write(response, JSON.toJSONString(flights));
    }
}
