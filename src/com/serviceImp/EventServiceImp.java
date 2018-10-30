package com.serviceImp;

import com.DAO.EventDAO;
import com.github.pagehelper.PageHelper;
import com.model.Event;
import com.model.GroupBy;
import com.service.EventService;
import com.util.DateUtil;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description:事件服务层实现类
 * @Author:wx6_2
 * @Date:2017/5/20
 **/
@Service
public class EventServiceImp implements EventService {
	@Autowired
	private EventDAO eventDAO;

	public boolean update(Event event) {
		if (eventDAO.update(event) > 0)
			return true;
		return false;
	}

	public boolean delete(List<Integer> ids) {
		if (eventDAO.delete(ids) > 0)
			return true;
		return false;
	}

	public boolean insert(Event event) {
		event.setSrc(Event.XINHUA);
		if (eventDAO.insert(event) > 0)
			return true;
		return false;
	}

	public List<Event> pagingQuery(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		PageHelper.startPage(page, 8);
		List<Event> events = eventDAO.query(map);

		return events;
	}

	public List<Event> sprider(Map<String, Object> map) {
		Integer page = (Integer) map.get("page");
		PageHelper.startPage(page, 8);
		List<Event> events = eventDAO.sprider(map);

		return events;
	}

	public Map<String, List<GroupBy>> groupby(Map<String, Object> map) {
		Map<String, List<GroupBy>> result = new HashedMap();
		result.put("sponsorLevel", eventDAO.groupbySponsorLevel(map));
		result.put("sponsorType", eventDAO.groupbySponsorType(map));
		result.put("affectAge", eventDAO.groupbyAffectAge(map));
		result.put("affectRange", eventDAO.groupbyAffectRange(map));
		result.put("city", eventDAO.groupbyCity(map));
		result.put("date", eventDAO.groupbyDate(map));
		result.put("heat", eventDAO.groupbyHeat(map));
		result.put("src", eventDAO.groupbySrc(map));
		result.put("type", eventDAO.groupbyType(map));

		return result;
	}

	public Map<String, List<GroupBy>> weekly() {
		Map<String, Object> map = new HashMap<String, Object>();
		Date startDate = DateUtil.getOffsetDate(new java.util.Date(), 1);
		Date endDate = DateUtil.getOffsetDate(new java.util.Date(), 7);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Map<String, List<GroupBy>> result = new HashedMap();
		result.put("type", eventDAO.groupByTypeDate(map));

		int allCity = eventDAO.eventsSum(map);
		List<GroupBy> list = eventDAO.cityTopFive(map);
		for (GroupBy groupBy : list) {
			allCity -= groupBy.getCount();
		}
		GroupBy other = new GroupBy();
		other.setType("其他");
		other.setCount(allCity);
		list.add(other);
		result.put("city", list);

		list = eventDAO.eventsAmountByDate(map);
		List<GroupBy> newList = new ArrayList<GroupBy>();
		for (int i = 0, j = 0; i < 7; i++) {
			if (j < list.size()
					&& list.get(j).getDate().toString().equals(
							DateUtil.getOffsetDate(startDate, i).toString())) {
				newList.add(list.get(j));
				j++;
			} else {
				newList
						.add(new GroupBy(0, DateUtil
								.getOffsetDate(startDate, i)));
			}
		}
		result.put("date", newList);

		return result;
	}

	public Map<String, List<GroupBy>> monthly() {
		Map<String, Object> map = new HashMap<String, Object>();
		Date startDate = DateUtil.getOffsetDate(new java.util.Date(), 1);
		Date endDate = DateUtil.getOffsetDate(new java.util.Date(), 30);
		map.put("startDate", startDate);
		map.put("endDate", endDate);
		Map<String, List<GroupBy>> result = new HashedMap();
		result.put("monthType", eventDAO.groupByTypeDate(map));

		int allCity = eventDAO.eventsSum(map);
		List<GroupBy> list = eventDAO.cityTopFive(map);
		for (GroupBy groupBy : list) {
			allCity -= groupBy.getCount();
		}
		GroupBy other = new GroupBy();
		other.setType("其他");
		other.setCount(allCity);
		list.add(other);
		result.put("monthCity", list);

		list = eventDAO.eventsAmountByDate(map);
		List<GroupBy> newList = new ArrayList<GroupBy>();
		for (int i = 0, j = 0; i < 30; i++) {
			if (j < list.size()
					&& list.get(j).getDate().toString().equals(
							DateUtil.getOffsetDate(startDate, i).toString())) {
				newList.add(list.get(j));
				j++;
			} else {
				newList
						.add(new GroupBy(0, DateUtil
								.getOffsetDate(startDate, i)));
			}
		}
		result.put("monthDate", newList);

		return result;
	}

}
