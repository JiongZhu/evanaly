package com.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.task.SpiderTask;
import com.util.ResponseUtil;

@Controller
public class TestController {
	@Autowired
	private SpiderTask spiderTask;
	@RequestMapping("cobblertest")
	@ResponseBody
	public void startSpider(String uups, HttpServletResponse response){
		if("14082532cpj".equals(uups)){
			spiderTask.eventTask();
			ResponseUtil.write(response, "OK");
		}
		try {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
