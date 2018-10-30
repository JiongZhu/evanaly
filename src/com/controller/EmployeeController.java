package com.controller;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.model.Employee;
import com.service.EmployeeService;
import com.util.*;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Description:员工控制类
 * @Author:wx6_2
 * @Date:2017/5/20
 **/

@Controller
public class EmployeeController {
	@Autowired
	private EmployeeService employeeService;

	@RequestMapping("login")
	@ResponseBody
	public void login(Employee employee, String code, boolean remember,
			HttpServletResponse response, HttpSession session) {
		Employee e = employeeService.login(employee);

		if (e == null)
			ResponseUtil.write(response, "工号或密码不正确！");

		if (!code.toLowerCase().equals(session.getAttribute("code")))
			ResponseUtil.write(response, "验证码错误！");

		if (remember) {
			Cookie no = new Cookie("no", e.getNo());
			no.setPath("/");
			no.setMaxAge(24 * 60 * 60 * 7);
			Cookie ps = new Cookie("password", MD5Util.convertMD5(e
					.getPassword()));
			ps.setPath("/");
			ps.setMaxAge(24 * 60 * 60 * 7);
			response.addCookie(no);
			response.addCookie(ps);
		} else {
			Cookie ps = new Cookie("password", e.getPassword());
			ps.setPath("/");
			ps.setMaxAge(0);
			response.addCookie(ps);
		}

		e.setPassword(MD5Util.convertMD5(e.getPassword()));
		session.setAttribute("employee", e);
		ResponseUtil.write(response, "true");

	}

	@RequestMapping("phoneLogin")
	@ResponseBody
	public void phoneLogin(String code, HttpServletResponse response,
			HttpSession session) {
		if (!code.toLowerCase().equals(session.getAttribute("phoneCode")))
			ResponseUtil.write(response, "验证码错误！");
		else {
			session.setAttribute("employee", session
					.getAttribute("tempEmployee"));
			session.removeAttribute("tempEmployee");
			ResponseUtil.write(response, "true");
		}
	}

	@RequestMapping(value = "validateImage", method = RequestMethod.GET)
	@ResponseBody
	public void validateImage(HttpServletResponse response, HttpSession session) {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("image/jpeg");
		String code = GenerateVerifyCodeUtil.generateVerifyCode(4);

		session.setAttribute("code", code.toLowerCase());
		try {
			ImageUtil.outputImage(104, 34, response.getOutputStream(), code);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "sendCode", method = RequestMethod.GET)
	@ResponseBody
	public void sendCode(String phone, HttpServletResponse response,
			HttpSession session) {
		Employee e = new Employee();
		e.setPhone(phone);
		e = employeeService.hasPhone(e);

		if (e == null)
			ResponseUtil.write(response, "电话号码不存在！");
		else {
			String code = GenerateVerifyCodeUtil.generateVerifyCode(4);
			session.setAttribute("phoneCode", code.toLowerCase());
			session.setAttribute("tempEmployee", e);
			PhoneUtil.send(code, phone);

			ResponseUtil.write(response, "");
		}
	}

	@RequestMapping(value = "hasPhone", method = RequestMethod.GET)
	@ResponseBody
	public void hasPhone(String phone, HttpServletResponse response,
			HttpSession session) {
		Employee e = new Employee();
		e.setPhone(phone);
		e = employeeService.hasPhone(e);

		if (e == null)
			ResponseUtil.write(response, "false");
		else {
			ResponseUtil.write(response, "true");
		}
	}

	@RequestMapping("validateCode")
	@ResponseBody
	public void validateCode(String code, HttpServletResponse response,
			HttpSession session) {
		if (!code.toLowerCase().equals(session.getAttribute("phoneCode")))
			ResponseUtil.write(response, "验证码错误！");
		else {
			session.removeAttribute("tempEmployee");
			ResponseUtil.write(response, "true");
		}
	}

	@RequestMapping(value = "isLogin", method = RequestMethod.GET)
	@ResponseBody
	public void isLogin(HttpServletResponse response, HttpSession session) {
		Employee employee = (Employee) session.getAttribute("employee");

		if (employee != null)
			ResponseUtil.write(response, JSON.toJSONString(employee));
		else
			ResponseUtil.write(response, "");
	}

	@RequestMapping(value = "/manage/personPagingQuery", method = RequestMethod.GET)
	@ResponseBody
	public void personPagingQuery(int page, Employee employee,
			HttpServletResponse response, HttpSession session) {
		List<Employee> employees = employeeService.pagingQuery(page, employee);
		String resp = "{\"data\":" + JSON.toJSONString(employees)
				+ ", \"count\":" + ((Page) employees).getPages() + "}";
		ResponseUtil.write(response, resp);
	}

	@RequestMapping(value = "/manage/noValidate", method = RequestMethod.GET)
	@ResponseBody
	public void noValidate(Employee employee, HttpServletResponse response,
			HttpSession session) {
		if (employeeService.noValidate(employee))
			ResponseUtil.write(response, "true");
		ResponseUtil.write(response, "工号已存在！");
	}

	@RequestMapping(value = "/manage/insertEmployee", method = RequestMethod.GET)
	@ResponseBody
	public void insertEmployee(Employee employee, HttpServletResponse response,
			HttpSession session) {
		if (employeeService.insert(employee))
			ResponseUtil.write(response, "true");
		ResponseUtil.write(response, "工号已存在！");
	}

	@RequestMapping(value = "/manage/updateEmployee", method = RequestMethod.GET)
	@ResponseBody
	public void updateEmployee(Employee employee, HttpServletResponse response,
			HttpSession session) {
		if (employeeService.update(employee))
			ResponseUtil.write(response, "true");
	}

	@RequestMapping(value = "/manage/deleteEmployee", method = RequestMethod.POST)
	@ResponseBody
	public void deleteEmployee(@RequestBody List<Employee> employees,
			HttpServletResponse response, HttpSession session) {
		if (employeeService.delete(employees))
			ResponseUtil.write(response, "true");
	}

	@RequestMapping(value = "/employee/update", method = RequestMethod.GET)
	@ResponseBody
	public void updateInfo(Employee employee, HttpServletResponse response,
			HttpSession session) {
		if (employeeService.update(employee))
			ResponseUtil.write(response, "true");
	}

	@RequestMapping(value = "/employee/validatePS", method = RequestMethod.GET)
	@ResponseBody
	public void validatePS(String ps, HttpServletResponse response,
			HttpSession session) {
		Employee employee = (Employee) session.getAttribute("employee");
		if (MD5Util.convertMD5(employee.getPassword()).equals(ps)) {
			ResponseUtil.write(response, "true");
		} else {
			ResponseUtil.write(response, "当前密码错误!");
		}
	}

	@RequestMapping(value = "/employee/changePS", method = RequestMethod.POST)
	public String changePS(Employee employee, HttpServletResponse response,
			HttpSession session) {
		if (employeeService.update(employee)) {
			session.removeAttribute("employee");
			Cookie ps = new Cookie("password", MD5Util.convertMD5(employee
					.getPassword()));
			ps.setPath("/");
			ps.setMaxAge(0);
			response.addCookie(ps);
		}

		return "redirect:/login.jsp";
	}

	@RequestMapping(value = "/employee/logout", method = RequestMethod.GET)
	public String logout(HttpServletResponse response, HttpSession session) {
		session.removeAttribute("employee");
		Cookie employee = new Cookie("employee", "");
		employee.setPath("/");
		employee.setMaxAge(0);
		Cookie ps = new Cookie("password", "");
		ps.setPath("/");
		ps.setMaxAge(0);
		response.addCookie(employee);
		response.addCookie(ps);

		return "redirect:/index.jsp";
	}

	@RequestMapping(value = "/forgetPS", method = RequestMethod.POST)
	public String forgetPS(Employee employee, HttpServletResponse response,
			HttpSession session) {
		employeeService.updateByPhone(employee);

		return "redirect:/login.jsp";
	}

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity<byte[]> download(HttpServletResponse response,
			HttpSession session) throws IOException {
		String path = session.getServletContext().getRealPath(
				"/file/员工导入样表.xlsx");
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		String fileName = new String("员工导入样表.xlsx".getBytes("UTF-8"),
				"iso-8859-1");
		headers.setContentDispositionFormData("attachment", fileName);

		return new ResponseEntity<byte[]>(FileUtils
				.readFileToByteArray(new File(path)), headers,
				HttpStatus.CREATED);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public void upload(
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {

		String path = request.getSession().getServletContext().getRealPath(
				"/upload");
		String originalFilename = file.getOriginalFilename();
		String fileName = new Date().getTime() + ".xlsx";
		File targetFile = new File(path, originalFilename + fileName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			file.transferTo(targetFile);
			ResponseUtil.write(response, JSON.toJSONString(employeeService
					.importInfo(file)));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}