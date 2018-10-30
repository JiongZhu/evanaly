package com.serviceImp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DAO.EmployeeDAO;
import com.DAO.QRCodeDAO;
import com.model.Employee;
import com.model.QRCode;
import com.service.QRCodeService;
import com.util.ServletContextManage;


@Service
public class QRCodeServiceImp implements QRCodeService {
	@Autowired
	private QRCodeDAO qrCodeDAO;
	@Autowired
	private EmployeeDAO employeeDAO;
	public String getQRCode() {
		String r = System.currentTimeMillis()+"";
		QRCode qr = new  QRCode();
		qr.setUid(r);
		qrCodeDAO.insert(qr);
		return r;
	}

	public String scanQRCode(String username, String qrCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", qrCode);
		QRCode  qr =   qrCodeDAO.searchByUid(map);
		if(qr==null){
			return "二维码不存在";
		}else {
			Employee emp = new Employee();
			emp.setNo(username);
			List<Employee> emps = employeeDAO.select(emp);
			emp = emps.size()>0?emps.get(0):null;
			if(emp==null){
				return "用户不存在";
			}else{
				qr.setEmployee(emp);
				qrCodeDAO.update(qr);
				return "扫描成功";
			}
		}
	}

	public String checkQRCode(String qrCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("uid", qrCode);
		QRCode  qr =   qrCodeDAO.searchByUid(map);
		if(qr==null){
			return "N";
		}else if(qr.getEmployee()==null){
			return "N";
		}else{
			Employee emp = qr.getEmployee();
			ServletContextManage.sessionPut("employee", emp);
			return "Y";
		}
		
	}

	
	
	
}
