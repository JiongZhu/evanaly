package com.filter;

import com.model.Employee;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description:登陆过滤器
 * @Author:wx6_2
 * @Date:2017/5/20
 **/
public class LoginFilter implements Filter {
    private FilterConfig filterConfig;

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }
    public static boolean isContains(String url, String[] pages) {
        for (int i = 0; i < pages.length; i++) {
            if (url.indexOf(pages[i]) != -1) {
                return true;
            }
        }
        return false;
    }
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String includeStrings = filterConfig.getInitParameter("includePage");    // 过滤资源后缀参数
        String redirectPath = request.getContextPath() + filterConfig.getInitParameter("redirectPath");// 没有登陆转向页面

        String[] includeList = includeStrings.split(";");

        if (!this.isContains(request.getRequestURI(), includeList)) {// 只对指定过滤参数后缀进行过滤
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        Employee employee = (Employee) request.getSession().getAttribute("employee");//判断用户是否登录
        if (employee == null) {
            response.sendRedirect(redirectPath);
            return;
        } else {
            filterChain.doFilter(request, response);
            return;
        }
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
