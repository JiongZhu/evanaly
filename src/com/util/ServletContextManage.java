package com.util;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ServletContextManage{
	private static ThreadLocal<HttpServletRequest> request = new ThreadLocal<HttpServletRequest>();
	
	private static ThreadLocal<HttpServletResponse> response = new ThreadLocal<HttpServletResponse>();
	private static ThreadLocal<HttpSession> session = new ThreadLocal<HttpSession>();
	public static HttpServletRequest getRequest(){
		return request.get();
	}
	public static void setRequest(HttpServletRequest req){
		request.set(req);
	}
	
	
	public static void setResponse(HttpServletResponse resp){
		
		response.set(resp);
		
	}
	public static HttpServletResponse getResponse(){
		
		return response.get();
	}
	public static void setSession(HttpSession session){
		ServletContextManage.session.set(session);
	} 
	public static Object sessionGet(String key ){
		HttpSession session = ServletContextManage.session.get();
		return session.getAttribute(key);
	}
	
	public static void sessionRemove(String key){
		HttpSession session = ServletContextManage.session.get();
		session.removeAttribute(key);
	}
	public static void sessionPut(String key,Object o){
		HttpSession session = ServletContextManage.session.get();
		session.setAttribute(key, o);
	}
	
	public static void put(String name,Object o){
		HttpServletRequest request = ServletContextManage.request.get();
		request.setAttribute(name, o);
		
	}
	public static String getServletPath(){
		HttpServletRequest request = ServletContextManage.request.get();
		return request.getServletPath();
	}
	
	


	
}
