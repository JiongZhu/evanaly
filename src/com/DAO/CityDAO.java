package com.DAO;

import com.model.City;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * Created by wx6_2 on 2017/6/2.
 */
public interface CityDAO {
    List<City> getAllCity();
    List<City> pagingQuery(Map<String, Object> map);
    City search(@Param("name")String name);
    int insert(City city);
	int delete(List<Integer> ids);
	int update(City city);
}
