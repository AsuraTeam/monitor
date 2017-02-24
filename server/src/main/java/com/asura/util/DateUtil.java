package com.asura.util;

import com.asura.framework.base.util.Check;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * .
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

	/**
	 *
	 * @return
     */
	public static long getDayInt(){
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
			String day = new SimpleDateFormat(DATE_FORMAT).format(Calendar.getInstance().getTime());
			String t = dateFormat.parse(day).getTime() + "";
			return Long.valueOf(t.substring(0,10))-86400;
		}catch (Exception e){

		}
		return 0;
	}

	// 获取timestamp时间
	public static  Timestamp getTimeStamp(){
		String d = new SimpleDateFormat(TIME_FORMAT).format(Calendar.getInstance().getTime());
		return Timestamp.valueOf(d);
	}

	// 获取时间戳
	public static long getDateStamp(){
		return (long)System.currentTimeMillis()/1000;
	}

	// 获取时间戳
	public static Integer getDateStampInteter(){
		return Integer.valueOf(System.currentTimeMillis()/1000+"");
	}


	// 获取今天的时间
	public static String getDay(){
		String d = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		return d;
	}

	/**
	 * 强nginx的时间改成时间错
	 * @param t
	 * @return
     */
	public static String  ngxToBig(String t){
		try {
			String timeLocal = t.replace("[", "").replace("]", "");
			SimpleDateFormat formatter = new SimpleDateFormat("dd/MMM/yyyy:hh:mm:ss", Locale.ENGLISH);
			Date date = formatter.parse(timeLocal);
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return format.format(date);
		}catch (Exception e){

		}
		return null;
	}

	/**
	 * 获取昨天的时间
	 * @return
	 */
	public static String getYestDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}


	/**
	 * 获取明天的时间
	 * @return
	 */
	public static String getToDay(){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,1);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}

	// 获取Timestamp
	public static Timestamp[] getDayStartEnd(){
        Timestamp[] t = new Timestamp[2];
		String day = new SimpleDateFormat(DATE_FORMAT)
				.format(Calendar.getInstance().getTime());
		t[0] = Timestamp.valueOf(day + " 00:00:00");
		t[1] = Timestamp.valueOf(day + " 23:59:59");
		return t;
	}

	/**
	 * 获取每天的开始时间戳
	 * @return
     */
	public static  long getDayStartTimestamp(){
		Timestamp[] timestamps = getDayStartEnd();
		return  Long.valueOf(dateToStamp(timestamps[0]+""));
	}


	/**
	 * 获取timestamp的时间
	 * @return
     */
	public  static Timestamp getTime(){
		String day = new SimpleDateFormat(TIME_FORMAT).format(Calendar.getInstance().getTime());
		return Timestamp.valueOf(day);
	}

	/**
	 * 将日期格式的字符串转换为长整型
	 *
	 * @param date
	 * @param format
	 * @return
	 */
	public static long convert2Long(String date, String format) throws ParseException{
		if (!Check.NuNStr(date)) {
			SimpleDateFormat sf = new SimpleDateFormat(format);
			return sf.parse(date).getTime();
		}
		return 0l;
	}

	/**
	 * 将时间变成时间戳的字符串
	 * @return
     */
	public static String dateToStamp(String date){
		try {
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(TIME_FORMAT);
			Date date1 = simpleDateFormat.parse(date);
			long ts = date1.getTime()/1000;
			return String.valueOf(ts);
		}catch (Exception e){

		}
		return String.valueOf(getDateStamp()/1000);
	}

	/**
	 * 获取当前月的第一天和最后一天
	 * @return
     */
	public static  String[] getMonthStartEnd(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		//获取前月的第一天
		Calendar   cal_1=Calendar.getInstance();//获取当前日期
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
		String firstDay = format.format(cal_1.getTime());
		//获取前月的最后一天
		Calendar cale = Calendar.getInstance();
		cale.set(Calendar.DAY_OF_MONTH,cale.getActualMaximum(Calendar.DAY_OF_MONTH));//设置为1号,当前日期既为本月第一天
		String lastDay = format.format(cale.getTime());
		String[] result = new String[2];
		result[0] = firstDay;
		result[1] = lastDay;
		return result;
	}

	public static String getWeekStart(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);

		currentDate.set(Calendar.HOUR_OF_DAY, 0);
		currentDate.set(Calendar.MINUTE, 0);
		currentDate.set(Calendar.SECOND, 0);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)currentDate.getTime().clone());
		return date;
	}

	public static String getWeekEnd(){
		Calendar currentDate = new GregorianCalendar();
		currentDate.setFirstDayOfWeek(Calendar.MONDAY);
		currentDate.set(Calendar.HOUR_OF_DAY, 23);
		currentDate.set(Calendar.MINUTE, 59);
		currentDate.set(Calendar.SECOND, 59);
		currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
		String date = new SimpleDateFormat("yyyy-MM-dd").format((Date)currentDate.getTime().clone());
		return date;
	}

	/**
	 * 时间戳转换成日期格式字符串
	 * @param seconds 精确到秒的字符串
	 * @return
	 */
	public static String timeStamp2Date(String seconds) {
		if(seconds == null || seconds.isEmpty() || seconds.equals("null")){
			return "";
		}
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date(Long.valueOf(seconds)));
	}

	/**
	 * 获取最近N天的时间
	 * @return
	 */
	public static String getLastNDay(int number){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE,-number);
		return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
	}
}
