/**
 * @FileName:
 * @Package: com.asura.framework.base.util
 *
 * @author sence
 * @created 11/13/2014 11:29 AM
 *
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.util;

import java.util.Random;

/**
 *
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @since 1.0
 * @version 1.0
 */
public class RandomUtil {

	/**
	 *
	 * 生成指定位数的数字串
	 *
	 * @author zhangshaobin
	 * @created 2013-6-6 下午7:07:24
	 *
	 * @param pwd_len
	 * @return
	 */
	public static String genRandomNum(int pwd_len) {
		int maxNum = 1000;
		int i; // 生成的随机数
		int count = 0; // 生成的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < pwd_len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			i = i % 10;
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

}
