package com.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.ServletContextManage;

/**
 * 
 * 设置线程的servlet环境
 */
public class ServletFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		ServletContextManage.setRequest((HttpServletRequest) req);
		resp.setCharacterEncoding("utf-8");
		ServletContextManage.setResponse((HttpServletResponse)resp);
		ServletContextManage.setSession(((HttpServletRequest) req).getSession());
		chain.doFilter(req, resp); 
	}

	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	
}
