package com.serviceImp;

import com.DAO.CityDAO;
import com.github.pagehelper.PageHelper;
import com.model.City;
import com.model.Event;
import com.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Description:城市服务层
 * @Author:wx6_2
 * @Date:2017/6/18
 **/
@Service
public class CityServiceImp implements CityService {
    @Autowired
    private CityDAO cityDAO;

    public List<City> getAllCity() {
        return cityDAO.getAllCity();
    }

    public boolean hasCity(String name) {
        if(cityDAO.search(name) != null)
            return true;
        return false;
    }

	public boolean delete(List<Integer> ids) {
		return cityDAO.delete(ids)>0;
	}

	public boolean insert(City city) {
		
		return cityDAO.insert(city)>0;
	}

	public List<City> pagingQuery(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		PageHelper.startPage(page, 8);
		return cityDAO.pagingQuery(map);
	}

	public boolean update(City city) {
		
		return cityDAO.update(city)>0;
	}
}
