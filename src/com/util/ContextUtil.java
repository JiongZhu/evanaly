package com.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
@Component
public class ContextUtil implements ApplicationContextAware{
	private static ApplicationContext ctx;
	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

	public static Object getBean(String bean){
		return ctx.getBean(bean);
	}
	
}
