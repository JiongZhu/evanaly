package com.util;

import java.util.Random;

public class GenerateVerifyCodeUtil {
	private static final String VERIFY_CODES = "234567890ABCDEFGHJKLMNPQRSTUVWXYZ";

	public static String generateVerifyCode(int len) {
		Random rand = new Random(System.currentTimeMillis());
		StringBuilder verifyCode = new StringBuilder(len);
		
		for(int i = 0; i < len; i++) {
			verifyCode.append(VERIFY_CODES.charAt(rand.nextInt(VERIFY_CODES.length())));
		}
		
		return verifyCode.toString();
	}
}
