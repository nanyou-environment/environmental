package com.nanyou.framework.util;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/*******************************************************************************
 * AES加解密算法
 * 
 * 加密用的Key 可以用26个字母和数字组成，最好不要用保留字符，虽然不会错，至于怎么裁决，个人看情况而定
 * 此处使用AES-128-CBC加密模式，key需要为16位。 iv也是使用"0102030405060708"
 */

public class AESUtils {
	private static final String DEFAULT_KEY="$a1lQmLo^n]eKy7?";
	
	public static void main(String[] args) throws Exception {
		System.err.println("root\t"+Encrypt("root"));
		System.err.println("123456\t"+Encrypt("123456"));
	}
	
	/**
	 * 加密
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String sSrc){
		try {
			return Encrypt(sSrc,DEFAULT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加密
	 * @param sSrc 被加密的明文字符�?
	 * @param sKey 密钥，必须为16�?
	 * @return 密文
	 * @throws Exception
	 */
	private static String Encrypt(String sSrc, String sKey) throws Exception {
		if (sKey == null) {
			System.out.print("Key为空");
			return null;
		}
		// 判断Key是否�?6�?
		if (sKey.length() != 16) {
			System.out.print("Key长度不是16位");
			return null;
		}
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强�?
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes());

		return Base64.encode(encrypted);// 此处使用BAES64做转码功能，同时能起�?次加密的作用�?
	}
	
	/**
	 * 解密
	 * @param sSrc
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String sSrc){
		try {
			return Decrypt(sSrc, DEFAULT_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param sSrc 已加密的密文字符�?
	 * @param sKey 密钥，必须为16�?
	 * @return 解密后的明文字符�?
	 * @throws Exception
	 */
	private static String Decrypt(String sSrc, String sKey) throws Exception {
		try {
			// 判断Key是否正确
			if (sKey == null) {
				System.out.print("Key为空");
				return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
				System.out.print("Key长度不是16位");
				return null;
			}
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] encrypted1 = Base64.decode(sSrc);// 先用bAES64解密
			try {
				byte[] original = cipher.doFinal(encrypted1);
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				System.out.println(e.toString());
				return null;
			}
		} catch (Exception ex) {
			System.out.println(ex.toString());
			return null;
		}
	}

}