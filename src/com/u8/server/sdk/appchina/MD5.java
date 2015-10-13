package com.u8.server.sdk.appchina;

/**
 * MD5工具
 *
 */

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class MD5 {

	/**
	 * MD5值计算<p>
	 * MD5的算法在RFC1321 中定义:
	 * 在RFC 1321中，给出了Test suite用来检验你的实现是否正确：
	 * MD5 ("") = d41d8cd98f00b204e9800998ecf8427e
	 * MD5 ("a") = 0cc175b9c0f1b6a831c399e269772661
	 * MD5 ("abc") = 900150983cd24fb0d6963f7d28e17f72
	 * MD5 ("message digest") = f96b697d7cb7938d525a2f31aaf161d0
	 * MD5 ("abcdefghijklmnopqrstuvwxyz") = c3fcd3d76192e4007dfb496cca67e13b
	 *
	 * @param res 源字符串
	 * @return md5值
	 */
	public final static String md5Digest(String res) {
		if(res ==null||"".equals(res)){
			return null;
		}
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		byte[] strTemp;
		try {
			strTemp = res.getBytes("UTF-8");
		} catch (UnsupportedEncodingException e1) {
			return null;
		}
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			String dd = new String(str);
			return dd;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * MD5值计算+Base64<p>
	 * MD5的算法在RFC1321 中定义:
	 * 在RFC 1321中
	 * 
	 * @param res 源字符串
	 * @return md5值
	 */
	public final static byte[] md5SrcDigest(String res) {
		if(res == null || "".equals(res)){
			return null;
		}
		byte[] strTemp = res.getBytes();
		try {
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			return md;
		} catch (Exception e) {
			return null;
		}
	}

	
}
