
package com.u8.server.sdk.kuaiyong.kuaiyong;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;



/**
 * RSA签名验签类
 */
public class RSASignature{
	
	private static Logger logger = Logger.getLogger(RSASignature.class);

	public static final String  SIGN_ALGORITHMS = "SHA1WithRSA";
	/**
	* RSA签名
	* @param content 待签名数据
	* @param privateKey 商户私钥
	* @param encode 字符集编码
	* @return 签名值
	*/
	public static String sign(String content, String privateKey, String encode)
	{
		String charset = "utf-8";
		if(!StringUtils.isEmpty(encode)){
		    charset=encode;
		}
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	
        	KeyFactory keyf 				= KeyFactory.getInstance("RSA");
        	PrivateKey priKey 				= keyf.generatePrivate(priPKCS8);

            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(priKey);
            signature.update( content.getBytes(charset));

            byte[] signed = signature.sign();
            
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	logger.error(e,e);
        }
        
        return null;
    }
	
	public static String sign(String content, String privateKey)
	{
        try 
        {
        	PKCS8EncodedKeySpec priPKCS8 	= new PKCS8EncodedKeySpec( Base64.decode(privateKey) ); 
        	KeyFactory keyf = KeyFactory.getInstance("RSA");
        	PrivateKey priKey = keyf.generatePrivate(priPKCS8);
            java.security.Signature signature = java.security.Signature.getInstance(SIGN_ALGORITHMS);
            signature.initSign(priKey);
            signature.update( content.getBytes());
            byte[] signed = signature.sign();
            return Base64.encode(signed);
        }
        catch (Exception e) 
        {
        	logger.error(e,e);
        }
        return null;
    }
	
	/**
	* RSA验签名检查
	* @param content 待签名数据
	* @param sign 签名值
	* @param publicKey 分配给开发商公钥
	* @param encode 字符集编码
	* @return 布尔值
	*/
	public static boolean doCheck(String content, String sign, String publicKey,String encode)
	{
	    String charset = "utf-8";
        if(!StringUtils.isEmpty(encode)){
            charset=encode;
        }
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes(charset) );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			logger.error(e,e);
		}
		
		return false;
	}
	
	public static boolean doCheck(String content, String sign, String publicKey)
	{
		try 
		{
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        byte[] encodedKey = Base64.decode(publicKey);
	        PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));

		
			java.security.Signature signature = java.security.Signature
			.getInstance(SIGN_ALGORITHMS);
		
			signature.initVerify(pubKey);
			signature.update( content.getBytes() );
		
			boolean bverify = signature.verify( Base64.decode(sign) );
			return bverify;
			
		} 
		catch (Exception e) 
		{
			logger.error(e,e);
		}
		
		return false;
	}
	
}
