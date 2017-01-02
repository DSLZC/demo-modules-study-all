package com.dslcode.core.util;

import java.security.MessageDigest;
import java.util.Base64;

/**
 * 密码工具类
 * @author DSL 
 * @date 2016-07-13
 */
public class PwdUtil {

	/**
	 * MD5加密
	 * @param str
	 * @return
	 * @throws Exception 
	 */
	public static String MD5Encode(String str){
		if(NullUtil.isNull(str)) return null;
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.getBytes("UTF-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] byteArray = messageDigest.digest();
		StringBuffer md5StrBuff = new StringBuffer();
		int byteLength = byteArray.length;
		for (int i = 0; i < byteLength; i++) {
			int hex = 0xFF & byteArray[i];
			if(hex < 16) md5StrBuff.append("0");
			md5StrBuff.append(Integer.toHexString(hex));
		}
		return md5StrBuff.toString();
	}
	
	/**
	 * Base64加密
	 * @param str
	 * @return
	 * @throws Exception 
	 */
    public static String BASE64Encode(String str) {
    	if(NullUtil.isNull(str)) return null;
        try {
			return new String(Base64.getEncoder().encode(str.getBytes("utf-8")), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
   
    /**
     * Base64解密
     * @param str
     * @return
     * @throws Exception
     */
    public static String BASE64Decod(String str) {
    	if(NullUtil.isNull(str)) return null;
    	try {
			return new String(Base64.getDecoder().decode(str.getBytes("utf-8")), "utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
    }
    
}
