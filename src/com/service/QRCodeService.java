package com.service;

public interface QRCodeService {
	//生成二维码
	String getQRCode();
	//扫描二维码
	String scanQRCode(String username, String qrCode);
	//检查二维码是否被扫过
	String checkQRCode(String qrCode);

	

}
