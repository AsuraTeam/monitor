package com.asura.agent.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 
 * @author zy
 * @Date 2016-06-21
 * 获取时间格式
 */
public class DateUtil {

	public static String DATE_FORMAT = "yyyy-MM-dd";

	public static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	// format "yyyy-MM-dd HH:mm:ss"可以随意组合
	public static String getDate(String format){
		String d = new SimpleDateFormat(format).format(Calendar.getInstance().getTime());
		return d;
	}

	// 获取timestamp时间
	public static  Timestamp getTimeStamp(){
		String d = new SimpleDateFormat(TIME_FORMAT).format(Calendar.getInstance().getTime());
		return Timestamp.valueOf(d);
	}

	public static String getDay(){
		String d = new SimpleDateFormat(TIME_FORMAT).format(Calendar.getInstance().getTime());
		System.out.println(d);
		return d;
	}

	/**
	 * 当前时间
	 * @return
     */
	public static Long getCurrTime(){
		return System.currentTimeMillis() / 1000;
	}

}