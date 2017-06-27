package com.plan.my.mytoolslibrary.toolutils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String toMd5(byte[] bytes) {
		try {
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			algorithm.reset();
			algorithm.update(bytes);
			return toHexString(algorithm.digest(), "");
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	public static String toHexString(byte[] bytes, String separator) {
		StringBuilder hexString = new StringBuilder();
		for (byte b : bytes) {
			int n = b;
			if (n < 0) {
				n = b & 0x7F + 128;
			}
			hexString.append(
					Integer.toHexString(n / 16) + Integer.toHexString(n % 16))
					.append(separator);
		}
		return hexString.toString().toUpperCase();
	}

}
