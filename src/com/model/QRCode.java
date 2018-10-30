package com.model;
/**
 * 	二维码实体 
 *
 */
public class QRCode {
	private int id;
	private String uid;//唯一标识
	private Employee employee;//二维码被扫描的用户
	
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	

}
