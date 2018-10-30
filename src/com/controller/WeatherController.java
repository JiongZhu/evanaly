package com.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.model.Weather;
import com.service.WeatherService;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description:温度控制力
 * @Author:wx6_2
 * @Date:2017/5/21
 **/
@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @RequestMapping("/weather/searchFiveDay")
    @ResponseBody
    public void searchFiveDay(Weather weather, HttpServletResponse response) {
        List<Weather> weathers = weatherService.searchFiveDay(weather);

        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        ResponseUtil.write(response, JSON.toJSONString(weathers, SerializerFeature.WriteDateUseDateFormat));
    }
    
    @RequestMapping("/warnWeather")
    @ResponseBody
    public void warnWeather(HttpServletResponse response){
    	ResponseUtil.write(response, JSON.toJSONString(weatherService.weeklyWeatherWran(), SerializerFeature.WriteDateUseDateFormat));
    	
    }

}
