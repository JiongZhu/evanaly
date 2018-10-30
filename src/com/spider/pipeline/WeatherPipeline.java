package com.spider.pipeline;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.DAO.WeatherDAO;
import com.model.Weather;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
@Component
public class WeatherPipeline implements Pipeline{
	@Autowired
	private WeatherDAO weatherDAO;
	public void process(ResultItems resultItems, Task task) {
		Weather w = resultItems.get("weather");
		if(w!=null)
			weatherDAO.insert(w);
			
	}

}
