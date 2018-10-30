package com.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.model.City;
import com.model.Event;
import com.model.Route;
import com.service.CityService;
import com.service.RouteService;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * @Description:城市控制类
 * @Author:wx6_2
 * @Date:2017/6/18
 **/
@Controller
public class RouteController {
    @Autowired
    private RouteService routeService;
    @RequestMapping(value = "/manage/updateRoute", method = RequestMethod.POST)
    @ResponseBody
    public void updateCity(Route route, HttpServletResponse response) {
        if (routeService.update(route))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/insertRoute", method = RequestMethod.POST)
    @ResponseBody
    public void insertCity(Route route, HttpServletResponse response) {
        if (routeService.insert(route))
            ResponseUtil.write(response, "true");
        else
        	ResponseUtil.write(response, "exist");
    }
    @RequestMapping(value = "/manage/deleteRoute", method = RequestMethod.POST)
    @ResponseBody
    public void deleteCity(@RequestBody List<Integer> ids, HttpServletResponse response) {
        if (routeService.delete(ids))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/routePagingQuery", method = RequestMethod.POST)
    @ResponseBody
    public void cityPagingQuery(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<City> routes = routeService.pagingQuery(map);
        String resp = "{\"data\":" + JSON.toJSONString(routes) + ", \"count\":" + ((Page) routes).getPages() + "}";
        ResponseUtil.write(response, resp);
        ResponseUtil.write(response, JSON.toJSONString(routes));

    }
}
