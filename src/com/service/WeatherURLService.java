package com.service;

import com.model.WeatherURL;

import java.util.List;

/**
 * Created by wx6_2 on 2017/6/17.
 */
public interface WeatherURLService {
    List<WeatherURL> pagingQuery(int page, String name);
    boolean delete(List<Integer> ids);
    boolean update(WeatherURL weatherURL);
    boolean insert(WeatherURL weatherURL, String cityName);
}
