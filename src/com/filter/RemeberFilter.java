package com.filter;

import com.model.Employee;
import com.service.EmployeeService;
import com.util.ContextUtil;
import com.util.MD5Util;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Description:记住密码过滤器
 * @Author:wx6_2
 * @Date:2017/6/1
 **/
public class RemeberFilter implements Filter {
    private FilterConfig filterConfig;
    private static EmployeeService employeeService;
    static{

        employeeService = (EmployeeService) ContextUtil.getBean("employeeServiceImp");
    }

    public FilterConfig getFilterConfig() {
        return filterConfig;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        Cookie[] cookies = request.getCookies();
        String no = null;
        String password = null;

        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        if(employee != null) {
            filterChain.doFilter(request, response);
            return;
        }

        if(cookies != null)
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals("no"))
                    no = cookie.getValue();
                if(cookie.getName().equals("password"))
                    password = cookie.getValue();
            }

        if(no != null && !no.trim().equals("") && password != null && !password.trim().equals("")) {
            employee = new Employee();
            employee.setNo(no);
            employee.setPassword(MD5Util.convertMD5(password));
            employee = employeeService.login(employee);

            if(employee != null) {
                employee.setPassword(password);
                session.setAttribute("employee", employee);
            }
        }

        filterChain.doFilter(request, response);
        return;
    }

    public void destroy() {
        this.filterConfig = null;
    }
}
