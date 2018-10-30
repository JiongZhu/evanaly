package com.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.model.WeatherURL;
import com.service.WeatherURLService;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description:天气源控制类
 * @Author:wx6_2
 * @Date:2017/6/17
 **/
@Controller
public class WeatherURLController {
    @Autowired
    private WeatherURLService weatherURLService;

    @RequestMapping(value = "/manage/weatherURLPagingQuery", method = RequestMethod.GET)
    @ResponseBody
    public void weatherURLPagingQuery(@RequestParam String name, int page, HttpServletResponse response) {
        List<WeatherURL> weatherURLList = weatherURLService.pagingQuery(page, name);
        String resp = "{\"data\":" + JSON.toJSONString(weatherURLList) + ", \"count\":" + ((Page) weatherURLList).getPages() + "}";
        ResponseUtil.write(response, resp);
    }

    @RequestMapping(value = "/manage/deleteWeatherURL", method = RequestMethod.POST)
    @ResponseBody
    public void deleteWeatherURL(@RequestBody List<Integer> ids, HttpServletResponse response) {
        if (weatherURLService.delete(ids))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/updateWeatherURL", method = RequestMethod.GET)
    @ResponseBody
    public void updateWeatherURL(WeatherURL weatherURL, HttpServletResponse response) {
        if (weatherURLService.update(weatherURL))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/insertWeatherURL", method = RequestMethod.GET)
    @ResponseBody
    public void insertWeatherURL(WeatherURL weatherURL, String cityName, HttpServletResponse response) {
        if (weatherURLService.insert(weatherURL, cityName))
            ResponseUtil.write(response, "true");
    }
}
