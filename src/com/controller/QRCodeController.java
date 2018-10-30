package com.controller;

/**
 * 二位扫描控制器接口
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.service.QRCodeService;
@Controller
public class QRCodeController{
	
	@Autowired
	private QRCodeService qrCodeService;
	//生成二维码
	@ResponseBody
	@RequestMapping("generateQRCode")
	public String generateQRCode(){
		String r= qrCodeService.getQRCode();
		return r;
	}
	//扫描二维码
	@ResponseBody
	@RequestMapping("scanQRCode")
	public String scanQRCode(String username,String qrCode){
		System.out.println("scanQRCode");
		String r = qrCodeService.scanQRCode(username,qrCode);
		return r;
	}
	
	//检查二位码是否被扫过
	@ResponseBody
	@RequestMapping("checkQRCode")
	public String checkQRCode(String qrCode){
//		System.out.println(ServletContextManage.getRequest().getContextPath());
		String r = qrCodeService.checkQRCode(qrCode);
		return r;
	}
	

}
