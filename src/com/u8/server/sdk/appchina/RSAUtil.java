package com.u8.server.sdk.appchina;

import java.math.BigInteger;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RSAUtil {
	/**
	 * 
	 */
	public static final int NUMBIT = 64;

	/**
	 * 获取随机质数
	 * 
	 * @return
	 */
	public static BigInteger getPrimes() {
		return BigInteger.probablePrime(NUMBIT, new Random());
	}

	/**
	 * 通过P,Q计算N值
	 * 
	 * @param 一个素数p
	 * @param 一个素数q
	 * @return 返回P*Q的值n
	 */
	public static BigInteger getN(BigInteger p, BigInteger q) {
		return p.multiply(q);
	}

	/**
	 * 通过P,Q计算ran值 modkey
	 * 
	 * @param 一个素数p
	 *            ,不能为空
	 * @param 一个素数q
	 *            ,不能为空
	 * @return 返回(P-1)*(Q-1)的值ran
	 */
	public static BigInteger getRan(BigInteger p, BigInteger q) {
		return (p.subtract(BigInteger.ONE))
				.multiply(q.subtract(BigInteger.ONE));
	}

	/**
	 * 获取公钥(128位)
	 * <p>
	 * 具体代码如下: <code><font color="red"><br>
	 * BigInteger p = RSAUtil.getPrimes();<br>
	 * BigInteger q = RSAUtil.getPrimes();<br>
	 * BigInteger ran = RSAUtil.getRan(p, q);<br>
	 * BigInteger n = RSAUtil.getN(p, q);//modkey -- N值<br>
	 * BigInteger pKey = RSAUtil.getPublicKey(ran);//publicKey -- 公钥<br>
	 * </font></code>
	 * 
	 * @param ran
	 *            通过getRan静态方法计算出来的值
	 * @return
	 */
	public static BigInteger getPublicKey(BigInteger ran) {
		BigInteger temp = null;
		BigInteger e = BigInteger.ZERO;
		do {
			temp = BigInteger.probablePrime(NUMBIT, new Random());
			/*
			 * 随机生成一个素数，看他是否与ran的公约数为1，如果为1，e=temp退出循环
			 */
			if ((temp.gcd(ran)).equals(BigInteger.ONE)) {
				e = temp;
			}
		} while (!((temp.gcd(ran)).equals(BigInteger.ONE)));

		return e;
	}

	/**
	 * 获取私钥(128位)
	 * <p>
	 * 具体代码如下: <code><font color="red"><br>
	 * BigInteger priKey = RSAUtil.getPrivateKey(ran,pKey);//ran是产生公钥的ran变量,pKey是公钥<br>
	 * </font></code>
	 * 
	 * @param ran
	 *            通过getRan静态方法计算出来的值
	 * @param publicKey
	 *            公钥
	 * @return
	 */
	public static BigInteger getPrivateKey(BigInteger ran, BigInteger publicKey) {
		return publicKey.modInverse(ran);
	}

	/**
	 * 对明文进行加密，通过公式 密文=(明文（e次幂） mod m)
	 * 
	 * @param 明文em
	 *            不为空
	 * @param 公钥e
	 * @param 模数n
	 * @return 加密后的密文encodeM
	 */
	private static BigInteger[] encodeRSA(byte[][] em, BigInteger e,
			BigInteger n) {
		BigInteger[] encodeM = new BigInteger[em.length];
		for (int i = 0; i < em.length; i++) {
			encodeM[i] = new BigInteger(em[i]);
			encodeM[i] = encodeM[i].modPow(e, n);
		}
		return encodeM;
	}

	/**
	 * 对密文进行解密，通过公式 明文 = （密文（d次幂）mod m）
	 * 
	 * @param 密文encodeM
	 *            不为空
	 * @param 密钥d
	 * @param 模数n
	 * @return 解密后的明文dencodeM
	 */
	private static byte[][] dencodeRSA(BigInteger[] encodeM, BigInteger d,
			BigInteger n) {
		byte[][] dencodeM = new byte[encodeM.length][];
		int i;
		int lung = encodeM.length;
		for (i = 0; i < lung; i++) {
			dencodeM[i] = encodeM[i].modPow(d, n).toByteArray();
		}
		return dencodeM;
	}

	/**
	 * 将数组byte[]arrayByte,转化为二维数组,分段加密/解密
	 * 
	 * @param arrayByte
	 * @param numBytes
	 * @return arrayEm 不会为空
	 */
	private static byte[][] byteToEm(byte[] arrayByte, int numBytes) {
		/**
		 * 分段
		 */
		int total = arrayByte.length;
		int dab = (total - 1) / numBytes + 1, iab = 0;
		byte[][] arrayEm = new byte[dab][];
		int i, j;
		for (i = 0; i < dab; i++) {
			arrayEm[i] = new byte[numBytes];

			for (j = 0; j < numBytes && iab < total; j++, iab++) {
				arrayEm[i][j] = arrayByte[iab];
			}
			/**
			 * 补齐空格字符(ox20=32)
			 */
			for (; j < numBytes; j++) {
				arrayEm[i][j] = ' ';
			}
		}
		return arrayEm;
	}

	/**
	 * 
	 * 将二维数组转化为一维数组
	 * 
	 * @param arraySenS
	 * @return
	 */
	private static byte[] StringToByte(byte[][] arraySenS) {
		int i, dab = 0;
		for (i = 0; i < arraySenS.length; i++) {
			if (arraySenS[i] == null) {
				return null;
			}
			dab = dab + arraySenS[i].length;
		}
		List<Byte> listByte = new ArrayList<Byte>();
		int j;
		for (i = 0; i < arraySenS.length; i++) {
			for (j = 0; j < arraySenS[i].length; j++) {
				if (arraySenS[i][j] != ' ') {
					listByte.add(arraySenS[i][j]);
				}
			}
		}
		Byte[] arrayByte = listByte.toArray(new Byte[0]);
		byte[] result = new byte[arrayByte.length];
		for (int k = 0; k < arrayByte.length; k++) {
			result[k] = (arrayByte[k]).byteValue();
		}
		listByte = null;
		arrayByte = null;
		return result;
	}

	/**
	 *<font color="red"> 加密方法(如果使用了产生密钥功能,则需要同步使用此方法加密)</font>
	 * 
	 * @param source
	 *            ： 明文
	 * @param e
	 *            公钥
	 * @param n
	 *            modkey
	 * @return 密文 带","
	 * @throws Exception
	 */
	public static String encrypt(String source, BigInteger e, BigInteger n)
			throws Exception {
		return encrypt(source, e, n, NUMBIT * 2);
	}

	/**
	 ** 加密方法
	 * 
	 * @param source
	 *            ： 明文
	 * @param e
	 *            公钥
	 * @param n
	 * @return 密文 带","
	 * @throws Exception
	 */
	public static String encrypt(String source, BigInteger e, BigInteger n,
			int numBit) throws Exception {
		String text = URLEncoder.encode(source, "UTF-8");// 为了支持汉字、汉字和英文混排
		if (text == null || "".equals(text)) {
			throw new Exception("明文转换为UTF-8,导致转换异常!!!");
		}
		byte[] arraySendM = text.getBytes("UTF-8");
		if (arraySendM == null) {
			throw new Exception("明文转换为UTF-8,导致转换异常!!!");
		}
		if (numBit <= 1) {
			throw new Exception("随机数位数不能少于2!!!");
		}
		int numeroByte = (numBit - 1) / 8;
		byte[][] encodSendM = RSAUtil.byteToEm(arraySendM, numeroByte);
		BigInteger[] encodingM = RSAUtil.encodeRSA(encodSendM, e, n);
		StringBuilder encondSm = new StringBuilder();
		for (BigInteger em : encodingM) {
			encondSm.append(em.toString(16));
			encondSm.append(" ");
		}
		return encondSm.toString();
	}

	/**
	 * <font color="red"> 解密算法(如果使用了产生密钥功能,则需要同步使用此方法解密)</font>
	 * 
	 * @param cryptograph
	 *            :密文,带","
	 * @param d
	 *            私钥
	 * @param n
	 *            modkey
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph, BigInteger d, BigInteger n)
			throws Exception {
		return decrypt(cryptograph, d, n, NUMBIT * 2);
	}

	/**
	 * 解密算法
	 * 
	 * @param cryptograph
	 *            :密文,带","
	 * @param d
	 *            私钥
	 * @param n
	 * @param numBit
	 *            位数
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String cryptograph, BigInteger d,
			BigInteger n, int numBit) throws Exception {
		String[] chs = cryptograph.split(" ");
		if (chs == null || chs.length <= 0) {
			throw new Exception("密文不符合要求!!!");
		}
		int numeroToken = chs.length;
		BigInteger[] StringToByte = new BigInteger[numeroToken];
		for (int i = 0; i < numeroToken; i++) {
			StringToByte[i] = new BigInteger(chs[i], 16);
		}
		byte[][] encodeM = RSAUtil.dencodeRSA(StringToByte, d, n);
		byte[] sendMessage = RSAUtil.StringToByte(encodeM);
		String message = new String(sendMessage, "UTF-8");
		String result = URLDecoder.decode(message, "UTF-8");
		return result;
	}

}
