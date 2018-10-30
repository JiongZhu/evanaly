package com.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.model.Event;
import com.model.GroupBy;
import com.service.EventService;
import com.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * @Description:事件控制层
 * @Author:wx6_2
 * @Date:2017/5/20
 **/
@Controller
public class EventController {
    @Autowired
    private EventService eventService;

    @RequestMapping(value = "/manage/updateEvent", method = RequestMethod.GET)
    @ResponseBody
    public void updateEvent(Event event, HttpServletResponse response) {
        if (eventService.update(event))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/insertEvent", method = RequestMethod.GET)
    @ResponseBody
    public void insertEvent(Event event, HttpServletResponse response) {
//    	System.out.println(event);
        if (eventService.insert(event))
            ResponseUtil.write(response, "true");
    }
    @RequestMapping(value = "/manage/deleteEvent", method = RequestMethod.POST)
    @ResponseBody
    public void deleteEvent(@RequestBody List<Integer> ids, HttpServletResponse response) {
        if (eventService.delete(ids))
            ResponseUtil.write(response, "true");
    }

    @RequestMapping(value = "/manage/eventPagingQuery", method = RequestMethod.POST)
    @ResponseBody
    public void eventPagingQuery(@RequestBody Map<String, Object> map, HttpServletResponse response) {
        List<Event> events = eventService.pagingQuery(map);
        String resp = "{\"data\":" + JSON.toJSONString(events) + ", \"count\":" + ((Page) events).getPages() + "}";
        ResponseUtil.write(response, resp);
        ResponseUtil.write(response, JSON.toJSONString(events));

    }

    @RequestMapping(value = "/sprider", method = RequestMethod.POST)
    @ResponseBody
    public void sprider(@RequestBody Map<String, Object> map, HttpServletResponse response, HttpSession session) {
        List<Event> events = eventService.sprider(map);
        String resp = "{\"data\":" + JSON.toJSONString(events) + ", \"count\":" + ((Page) events).getPages() + "}";
        session.setAttribute("condition", map);
        ResponseUtil.write(response, resp);

    }

    @RequestMapping(value = "/analysis", method = RequestMethod.POST)
    @ResponseBody
    public void analysis(@RequestBody Map<String, Object> map, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, List<GroupBy>> result = eventService.groupby(map);
        ResponseUtil.write(response, JSON.toJSONString(result));
    }

    @RequestMapping(value = "/weeklyAnalysis", method = RequestMethod.GET)
    @ResponseBody
    public void weeklyAnalysis(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, List<GroupBy>> result = eventService.weekly();
        ResponseUtil.write(response, JSON.toJSONString(result));
    }

    @RequestMapping(value = "/monthlyAnalysis", method = RequestMethod.GET)
    @ResponseBody
    public void monthlyAnalysis(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        Map<String, List<GroupBy>> result = eventService.monthly();
        ResponseUtil.write(response, JSON.toJSONString(result));
    }
}
