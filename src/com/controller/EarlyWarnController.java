package com.controller;

import com.alibaba.fastjson.JSON;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.service.CityService;
import com.service.EarlyWarnService;
import com.service.RouteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 预警控制类
 *
 */
@Controller
public class EarlyWarnController {
	@Autowired
	private EarlyWarnService earlyWarnService;
	@Autowired
	private  RouteService routeService;
	@Autowired
	private CityService cityService;
	public void test(){
		earlyWarnService.weeklyCityPF();//城市客流量
		earlyWarnService.weeklyPF();//路线客流量
		routeService.getAllRoute();
		cityService.getAllCity();
	}

	@RequestMapping("/citywarn")
	@ResponseBody
	public void citywarn(HttpServletResponse response) {
		ResponseUtil.write(response, JSON.toJSONString(earlyWarnService.weeklyCityPF()));
	}

	@RequestMapping("/routewarn")
	@ResponseBody
	public void routewarn(HttpServletResponse response) {
		ResponseUtil.write(response, JSON.toJSONString(earlyWarnService.weeklyPF()));
	}

	@RequestMapping("/getAllRoute")
	@ResponseBody
	public void getAllRoute(HttpServletResponse response) {
		ResponseUtil.write(response, JSON.toJSONString(routeService.getAllRoute()));
	}
}
