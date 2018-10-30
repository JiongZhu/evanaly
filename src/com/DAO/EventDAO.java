package com.DAO;

import com.model.Event;
import com.model.EventSf;
import com.model.GroupBy;
import org.apache.ibatis.annotations.Param;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface EventDAO {
	int insert(Event e);
	List<Event> search(Event event);
	List<Event> query(Map<String, Object> map);
	List<Event> sprider(Map<String, Object> map);
	List<Event> searchByKW(@Param("event") Event e, @Param("keyWords") List<String> keyWords);
	int update(Event event);
	int delete(List<Integer> ids);
	List<GroupBy> groupbySponsorLevel(Map<String, Object> map);
	List<GroupBy> groupbySponsorType(Map<String, Object> map);
	List<GroupBy> groupbyAffectAge(Map<String, Object> map);
	List<GroupBy> groupbyAffectRange(Map<String, Object> map);
	List<GroupBy> groupbyType(Map<String, Object> map);
	List<GroupBy> groupbyHeat(Map<String, Object> map);
	List<GroupBy> groupbyCity(Map<String, Object> map);
	List<GroupBy> groupbyDate(Map<String, Object> map);
	List<GroupBy> groupbySrc(Map<String, Object> map);
	Integer eventsSum(Map<String, Object> map);
	List<GroupBy> groupByTypeDate(Map<String, Object> map);
	List<GroupBy> cityTopFive(Map<String, Object> map);
	List<GroupBy> eventsAmountByDate(Map<String, Object> map);
	List<EventSf> searchCityPFByDate(Map<String, Object> map);
	List<EventSf> searchEventSf(@Param("date")Date date);
}
