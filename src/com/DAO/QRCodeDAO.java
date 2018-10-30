package com.DAO;

import java.util.Map;

import com.model.QRCode;

public interface QRCodeDAO {
	QRCode searchByUid(Map<String, Object> map);
	int insert(QRCode code);
	int update(QRCode code);
}
