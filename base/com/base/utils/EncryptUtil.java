package com.base.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptUtil {
	public static int UPPER_CASE = 1;
	public static int LOWER_CASE = 0;
	public static int LENGTH_32 = 32;
	public static int LENGTH_16 = 16;
	

    public static String MD5(String inputText, int forNum) {
        String result = inputText;
        for (int i = 0; i < forNum; i++) {
            result = MD5(result, false, false);
        }
        return result;
    }
	
	public static String MD5(String inputText) {
		return MD5(inputText, false, false);
	}
	/**
	 * 返回32位MD5
	 * @param inputText
	 * @param cases
	 * @return
	 */
	public static String MD5(String inputText, boolean cases) {
		return MD5(inputText, cases, false);
	}
	
	/**
	 * MD5加密
	 * @param inputText 加密字符串
	 * @param cases 大写true 小写false
	 * @param length 32位true 16位false
	 * @return
	 */
	public static String MD5(String inputText, boolean cases, boolean length) {
		String value = encrypt(inputText, "md5");
		if(cases){
			value = value.toUpperCase();	//转换大写
		}
		if(length){
			value = value.substring(8, 24);	//取16位
		}
		return value;
	}

	/**
	 * SHA-1加密
	 * @param inputText
	 * @return
	 */
	public static String SHA(String inputText) {
		return encrypt(inputText, "sha-1");
	}

	/**
	 * md5或者sha-1加密
	 * 
	 * @param inputText
	 *            要加密的内容
	 * @param algorithmName
	 *            加密算法名称：md5或者sha-1，不区分大小写
	 * @return
	 */
	private static String encrypt(String inputText, String algorithmName) {
//		if (inputText == null || "".equals(inputText.trim())) {
//			throw new IllegalArgumentException("请输入要加密的内容");
//		}
		if (algorithmName == null || "".equals(algorithmName.trim())) {
			algorithmName = "md5";
		}
		String encryptText = null;
		try {
			MessageDigest m = MessageDigest.getInstance(algorithmName);
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			// m.digest(inputText.getBytes("UTF8"));
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encryptText;
	}

	// 返回十六进制字符串
	private static String hex(byte[] arr) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			sb.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1, 3));
		}
		return sb.toString();
	}
}