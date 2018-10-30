package com.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.model.City;
import com.model.Event;
import com.service.CityService;
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
public class CityController {
    @Autowired
    private CityService cityService;

    @RequestMapping("/getAllCity")
    @ResponseBody
    public void getAllCity(HttpServletResponse response) {
        List<City> cities = cityService.getAllCity();
        ResponseUtil.write(response, JSON.toJSONString(cities));
    }

    @RequestMapping(value = "/manage/hasCity", method = RequestMethod.GET)
    @ResponseBody
    public void hasCity(String city, HttpServletResponse response) {
        if (cityService.hasCity(city))
            ResponseUtil.write(response, "true");
        else
            ResponseUtil.write(response, "false");
    }
    @RequestMapping(value = "/manage/updateCity", method = RequestMethod.POST)
    @ResponseBody
    public void updateCity(City city, HttpServletResponse response) {
        if (cityService.update(city))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/insertCity", method = RequestMethod.POST)
    @ResponseBody
    public void insertCity(City city, HttpServletResponse response) {
        if (cityService.insert(city))
            ResponseUtil.write(response, "true");
    }
    @RequestMapping(value = "/manage/deleteCity", method = RequestMethod.POST)
    @ResponseBody
    public void deleteCity(@RequestBody List<Integer> ids, HttpServletResponse response) {
        if (cityService.delete(ids))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/cityPagingQuery", method = RequestMethod.POST)
    @ResponseBody
    public void cityPagingQuery(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<City> cities = cityService.pagingQuery(map);
        String resp = "{\"data\":" + JSON.toJSONString(cities) + ", \"count\":" + ((Page) cities).getPages() + "}";
        ResponseUtil.write(response, resp);
        ResponseUtil.write(response, JSON.toJSONString(cities));

    }
}
