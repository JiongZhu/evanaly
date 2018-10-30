package com.serviceImp;

import com.DAO.CityDAO;
import com.DAO.WeatherURLDAO;
import com.github.pagehelper.PageHelper;
import com.model.City;
import com.model.WeatherURL;
import com.service.WeatherURLService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description:天气源服务层
 * @Author:wx6_2
 * @Date:2017/6/17
 **/
@Service
public class WeatherURLServiceImp implements WeatherURLService {
    @Autowired
    private WeatherURLDAO weatherURLDAO;
    @Autowired
    private CityDAO cityDAO;

    public List<WeatherURL> pagingQuery(int page, String name) {
        PageHelper.startPage(page, 8);
        List<WeatherURL> weatherURLS = weatherURLDAO.query(name);
        return weatherURLS;
    }

    public boolean delete(List<Integer> ids) {
        if(weatherURLDAO.delete(ids) > 0)
            return true;
        return false;
    }

    public boolean update(WeatherURL weatherURL) {
        if(weatherURLDAO.update(weatherURL) > 0)
            return true;
        return false;
    }

    public boolean insert(WeatherURL weatherURL, String cityName) {
        City city = new City();
        city.setName(cityName);
        cityDAO.insert(city);
        weatherURL.setCity(city);
        if(weatherURLDAO.insert(weatherURL) > 0)
            return true;
        return false;
    }
}
