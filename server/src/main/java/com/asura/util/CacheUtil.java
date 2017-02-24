package com.asura.util;

import com.google.gson.Gson;

import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * 
 * @author zy
 * @Date 2016-06-26 实现动态代理方法 key 将数据库的sql语句md5, 方法名 + 参数 MD5 value 将结果变成字符串形式
 */
public class CacheUtil {

	private static RedisUtil redisUtil =  new RedisUtil();
	/**
	 * 
	 * @param <T>
	 * @param sourceStr
	 * @param args
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	private static String MD5(String sourceStr) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(sourceStr.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();
			// System.out.println("MD5(" + sourceStr + ",32) = " + result);
			// System.out.println("MD5(" + sourceStr + ",16) = " +
			// buf.toString().substring(8, 24));
		} catch (NoSuchAlgorithmException e) {
			// System.out.println(e);
		}
		return result;
	}


	/**
	 * 
	 * @param key
	 * @return
	 * 获取缓存数据
	 */
	public  String getCache(String key, Object content) {
		String value = new Gson().toJson(content);
		key = MD5(key + value);
		String result = redisUtil.get(key);
		return result;
	}

	/**
	 * @param key
	 * @param content
	 * 设置缓存数据
	 */
	public   void setCache(String key, Object content) {
		String value = new Gson().toJson(content);
		key = MD5(key + value);
		redisUtil.set(key, value);
	}


}
