package com.service;

import com.model.Event;
import com.model.GroupBy;

import java.util.List;
import java.util.Map;

/**
 * Created by wx6_2 on 2017/5/20.
 */
public interface EventService {
	boolean insert(Event event);
    boolean update(Event event);
    boolean delete(List<Integer> ids);
    List<Event> pagingQuery(Map<String, Object> map);
    List<Event> sprider(Map<String, Object> map);
    Map<String, List<GroupBy>> groupby(Map<String, Object> map);
    Map<String, List<GroupBy>> weekly();
    Map<String, List<GroupBy>> monthly();
}
