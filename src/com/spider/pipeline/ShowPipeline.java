package com.spider.pipeline;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.EventDAO;
import com.model.Event;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component("eShowPipeline")
public class ShowPipeline implements Pipeline{
	@Autowired
	 private EventDAO eventDAO;

	public void process(ResultItems resultItems, Task task) {
		Event e = resultItems.get("event");
		if(e!=null){
			eventDAO.insert(e);
			
		}
	}
}
