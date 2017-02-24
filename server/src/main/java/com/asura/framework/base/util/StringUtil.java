/**
 * @FileName: StringUtil.java
 * @Package com.asura.framework.util
 * 
 * @author zhangshaobin
 * @created 2012-11-5 下午5:52:52
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.util;

/**
 * <p>字符串工具类</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * <PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class StringUtil {

	/**
	 * 
	 * 截取目标字符串（即：target）最后一个标识符（即：separator）前的子串
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午5:56:29
	 *
	 * @param target	目标字符串
	 * @param separator	标识符
	 * @return
	 */
	public static String substringBeforeLastIgnoreCase(final String target, final String separator) {
		if ("".equals(target))
			return target;
		if ("".equals(separator))
			return target;
		final String tempStr = target.toUpperCase();
		final String tempSeparator = separator.toUpperCase();

		final int index = tempStr.lastIndexOf(tempSeparator);
		if (index == -1)
			return target;
		return target.substring(0, index);
	}

	/**
	 * 
	 * 单引号（“'”）逃逸，用于构建SQL语句时使用
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午5:55:44
	 *
	 * @param target	目标字符串
	 * @return
	 */
	public static String singleQuotoAndEscape(final String target) {
		if (!Check.NuNStr(target)) {
			final StringBuffer t = new StringBuffer(target.length() + 3);
			t.append("'");
			t.append(target.replaceAll("'", "''"));
			t.append("'");
			return t.toString();
		}
		return "''";
	}

	/**
	 * 
	 * 字符串左补字符到固定长度（如：高位补0到10位）
	 *
	 * @author zhangshaobin
	 * @created 2012-11-21 上午11:20:41
	 *
	 * @param input 原字符串
	 * @param padCode 补充字符
	 * @param toLength 到固定长度
	 * @return 补充完字符后字符串
	 */
	public static final String lpad(final String input, final String padCode, final int toLength) {
		if (input != null && input.length() < toLength) {
			final StringBuffer sb = new StringBuffer(input);
			while (sb.length() < toLength) {
				sb.insert(0, padCode);
			}
			return sb.toString();
		} else {
			return input;
		}
	}

	/**
	 * 
	 * 字符串右补字符到固定长度（如：地位补0到10位）
	 *
	 * @author zhangshaobin
	 * @created 2012-11-21 上午11:20:41
	 *
	 * @param input 原字符串
	 * @param padCode 补充字符
	 * @param toLength 到固定长度
	 * @return 补充完字符后字符串
	 */
	public static final String rpad(final String input, final String padCode, final int toLength) {
		if (input != null && input.length() < toLength) {
			final StringBuffer sb = new StringBuffer(input);
			while (sb.length() < toLength) {
				sb.append(padCode);
			}
			return sb.toString();
		} else {
			return input;
		}
	}

	/**
	 * 
	 * 判断字符串长度范围. 默认从0开始.
	 *
	 * @author zhangshaobin
	 * @created 2014年11月3日 上午10:11:10
	 *
	 * @param str		待判断字符串
	 * @param length	最大长度
	 * @return
	 */
	public static boolean isMeetInExpectLength(final String str, final int length) {
		return isMeetInExpectLength(str, 0, length);
	}

	/**
	 * 判断字符串长度范围 (min,max]
	 *
	 * @author zhangshaobin
	 * @created 2014年11月3日 上午10:11:06
	 *
	 * @param str		待判断字符串
	 * @param minLength	最小长度
	 * @param maxLength	最大长度
	 * @return
	 */
	public static boolean isMeetInExpectLength(final String str, final int minLength, final int maxLength) {
		if (str != null && str.length() > minLength) {
			return str.length() < maxLength + 1;
		}
		return false;
	}

	/**
	 *
	 * 删除空格
	 * @param str
	 * @return
	 *
	 */
	public static String delSpace (String str) {
		return str = str.replaceAll("\\s*", "");
	}

	/**
	 *
	 * 查找
	 * @param str
	 * @param length
	 * @return
	 *
	 */
	public static String cutStr (String str, int length) {
		if (Check.NuNStr(str) || str.length() < length) {
			return str;
		}
		return str.substring(0, length);
	}

}
