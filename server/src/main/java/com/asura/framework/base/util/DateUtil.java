/**
 * @FileName: DateUtil.java
 * @Package com.asura.framework.util
 * 
 * @author zhangshaobin
 * @created 2012-11-5 下午5:53:07
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.asura.framework.base.exception.BusinessException;

/**
 * <p>日期工具类</p>
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
public class DateUtil {

	public enum IntervalUnit {
		MILLISECOND, SECOND, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR
	}

	private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static final Map<String, ThreadLocal<SimpleDateFormat>> dateFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

	private static final Object timestampFormatLock = new Object();

	private static final Object dateFormatLock = new Object();

	private static String dateFormatPattern = "yyyy-MM-dd";

	private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";

	private static SimpleDateFormat getDateFormat() {
		ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern);
		if (null == tl) {
			synchronized (dateFormatLock) {
				tl = dateFormatPool.get(dateFormatPattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(dateFormatPattern);
						}
					};
					dateFormatPool.put(dateFormatPattern, tl);
				}
			}
		}
		return tl.get();
	}
	
	private static SimpleDateFormat getDateFormat(final String dateFormatPattern) {
		ThreadLocal<SimpleDateFormat> tl = dateFormatPool.get(dateFormatPattern);
		if (null == tl) {
			synchronized (dateFormatLock) {
				tl = dateFormatPool.get(dateFormatPattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(dateFormatPattern);
						}
					};
					dateFormatPool.put(dateFormatPattern, tl);
				}
			}
		}
		return tl.get();
	}

	private static SimpleDateFormat getTimestampFormat() {
		ThreadLocal<SimpleDateFormat> tl = timestampFormatPool.get(timestampPattern);
		if (null == tl) {
			synchronized (timestampFormatLock) {
				tl = timestampFormatPool.get(timestampPattern);
				if (null == tl) {
					tl = new ThreadLocal<SimpleDateFormat>() {
						@Override
						protected synchronized SimpleDateFormat initialValue() {
							return new SimpleDateFormat(timestampPattern);
						}
					};
					timestampFormatPool.put(timestampPattern, tl);
				}
			}
		}
		return tl.get();
	}

	/**
	 * 时间戳格式
	 */
	//	private static SimpleDateFormat timestampFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 日期格式
	 */
	//	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	/**
	 * 
	 * 根据日期格式解析成对应的date
	 *
	 * @author zhangshaobin
	 * @created 2015年7月16日 下午6:58:20
	 *
	 * @param str
	 * @param dateFormatPattern
	 * @return
	 * @throws ParseException
	 */
	 public static Date parseDate(String str,String dateFormatPattern) throws ParseException {
		 SimpleDateFormat sdf = getDateFormat(dateFormatPattern);
	     Date d = sdf.parse(str);
	     return d;
	 }
	 
	/**
	 * 
	 * 格式化成时间戳格式
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:05:59
	 *
	 * @param date	要格式化的日期
	 * @return	"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 */
	public static String timestampFormat(final Date date) {
		if (date == null) {
			return "";
		}
		//		return timestampFormat.format(date);
		return getTimestampFormat().format(date);
	}

	/**
	 * 
	 * 格式化成时间戳格式
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:14:18
	 *
	 * @param datetime	要格式化的日期
	 * @return	"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 */
	public static String timestampFormat(final long datetime) {
		//		return timestampFormat.format(new Date(datetime));
		return getTimestampFormat().format(new Date(datetime));
	}

	/**
	 * 
	 * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成Long型日期
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:23:28
	 *
	 * @param timestampStr	年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
	 * @return	Long型日期
	 */
	public static long formatTimestampToLong(final String timestampStr) {
		Date date;
		try {
			//			date = timestampFormat.parse(timestampStr);
			date = getTimestampFormat().parse(timestampStr);
		} catch (final ParseException e) {
			return 0L;
		}
		return date.getTime();
	}

	/**
	 * 
	 * 格式化成日期格式
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:10:42
	 *
	 * @param date	要格式化的日期
	 * @return	"年年年年-月月-日日"格式的日期字符串
	 */
	public static String dateFormat(final Date date) {
		if (date == null) {
			return "";
		}
		//		return dateFormat.format(date);
		return getDateFormat().format(date);
	}
	
	/**
	 * 
	 * 格式化成日期格式, 根据传过来的日志格式
	 *
	 * @author zhangshaobin
	 * @created 2015年7月10日 下午1:49:31
	 *
	 * @param date
	 * @param dateFormatPattern
	 * @return
	 */
	public static String dateFormat(final Date date, String dateFormatPattern) {
		if (date == null) {
			return "";
		}
		//		return dateFormat.format(date);
		return getDateFormat(dateFormatPattern).format(date);
	}

	/**
	 * 
	 * 格式化成日期格式
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:13:41
	 *
	 * @param datetime	要格式化的日期
	 * @return	"年年年年-月月-日日"格式的日期字符串
	 */
	public static String dateFormat(final long datetime) {
		//		return dateFormat.format(new Date(datetime));
		return getDateFormat().format(new Date(datetime));
	}

	/**
	 * 
	 * 将"年年年年-月月-日日"格式的日期字符串转换成Long型日期
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:25:21
	 *
	 * @param dateStr	"年年年年-月月-日日"格式的日期字符串
	 * @return	Long型日期
	 * @throws BusinessException	日期格式化异常
	 */
	public static long formatDateToLong(final String dateStr) throws BusinessException {
		Date date;
		try {
			//date = dateFormat.parse(dateStr);

			date = getDateFormat().parse(dateStr);
		} catch (final ParseException e) {
			throw new BusinessException("将输入内容格式化成日期类型时出错。", e);
		}
		return date.getTime();
	}

	/**
	 * 
	 * 得到本月的第一天
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:33:43
	 *
	 * @return	以"年年年年-月月-日日"格式返回当前月第一天的日期
	 */
	public static String getFirstDayOfCurrentMonth() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		//        return dateFormat.format(calendar.getTime());
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 *
	 * 得到月份第一天.以当前月份为基准
	 *
	 * @author liushengqi
	 *
	 * @param offset
	 * @return Date
	 */
	public static Date getFirstDayOfMonth(int offset) {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH,offset);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}


	/**
	 * 得到下个月的第一秒
	 * @return
	 */
	public static Long getFirstMillSecondOfNextMonth(){
		Date d = getFirstDayOfMonth(1);
		return d.getTime();
	}

	/**
	 * 得到下个月的最后一秒
	 * @return
	 */
	public static Long getLastMillSecondOfNextMonth(){
		Date d = getLastDayOfMonth(1);
		return d.getTime()+24*3600*1000L-1000L;
	}

	/**
	 *
	 * 得到月份最后一天.以当前月份为基准
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:39:38
	 *
	 * @return	以"年年年年-月月-日日"格式返回当前月最后一天的日期
	 */
	public static Date getLastDayOfMonth(int offset) {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH,offset);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(Calendar.SECOND,0);
		calendar.set(Calendar.MILLISECOND,0);
		return calendar.getTime();
	}

	/**
	 * 
	 * 得到本月的最后一天 
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:39:38
	 *
	 * @return	以"年年年年-月月-日日"格式返回当前月最后一天的日期
	 */
	public static String getLastDayOfCurrentMonth() {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		//        return dateFormat.format(calendar.getTime());
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 获取指定日期所在月的第一天
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午6:45:07
	 *
	 * @param date	日期
	 * @return	以"年年年年-月月-日日"格式返回当指定月第一天的日期
	 */
	public static String getFirstDayOfMonth(final Date date) {
		final Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		//		return dateFormat.format(ca.getTime());
		return getDateFormat().format(ca.getTime());
	}

	/**
	 * 
	 * 获取指定日期所在月的最后一天
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:04:10
	 *
	 * @param date
	 * @return	以"年年年年-月月-日日"格式返回当指定月最后一天的日期
	 */
	public static String getLastDayOfMonth(final Date date) {
		final Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.roll(Calendar.DAY_OF_MONTH, -1);
		//		return dateFormat.format(ca.getTime());
		return getDateFormat().format(ca.getTime());
	}
	/**
	 *
	 * 获取指定日期所在月的最后一天
	 *
	 * @author zhangshaobin
	 * @created 2012-11-5 下午7:04:10
	 *
	 * @param date
	 * @return	以"年年年年-月月-日日"格式返回当指定月最后一天的日期
	 */
	public static Long getLastDayTimeOfMonth(final Date date) {
		final Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_MONTH, 1);
		ca.roll(Calendar.DAY_OF_MONTH, -1);
		//		return dateFormat.format(ca.getTime());
		return ca.getTime().getTime();
	}

	/**
	 * 
	 * 获取指定日期所在周的第一天
	 *
	 * @author zhangshaobin
	 * @created 2012-12-4 下午6:45:07
	 *
	 * @param date	日期
	 * @return	以"年年年年-月月-日日"格式返回当指定周第一天的日期
	 */
	public static String getFirstDayOfWeek(final Date date) {
		final Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.set(Calendar.DAY_OF_WEEK, 2);
		//		return dateFormat.format(ca.getTime());
		return getDateFormat().format(ca.getTime());
	}

	/**
	 * 获取当前日期所在周的周末
	 * @param date
	 * @return
	 */
	private static Calendar lastDayOfWeek(final Date date){
		final Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		//日期减去1防止是周日（国外周日为一周的第一天）
		ca.add(Calendar.DATE,-1);
		//设置为本周的周六,这里不能直接设置为周日，中国本周日和国外本周日不同
		ca.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
		//日期往前推移一天
		ca.add(Calendar.DATE,1);
		return ca;
	}

	/**
	 * 
	 * 获取当前日期所在周的周末 
	 *
	 * @author zhangshaobin
	 * @created 2012-12-4 下午7:04:10
	 *
	 * @param date
	 * @return	以"年年年年-月月-日日"
	 */
	public static String getLastDayOfWeek(final Date date) {
		final Calendar ca = lastDayOfWeek(date);
		return getDateFormat().format(ca.getTime());
	}
	
	/**
	 * 获取当前日期所在的周六    第一秒
	 * @param date
	 * @return
	 */
	public static Long getSaturdayOfWeek(final Date date) {
		//周日零点零分零秒
		final Calendar ca = lastDayOfWeek(date);
		ca.set(Calendar.HOUR_OF_DAY,0);
		ca.set(Calendar.MINUTE,0);
		ca.set(Calendar.SECOND,0);
		ca.set(Calendar.MILLISECOND,0);
		//日期减去1天 变为周六零时零分零秒
		ca.add(Calendar.DATE,-1);
		return ca.getTime().getTime();
	}

	/**
	 * 获取当前日期所在的周日    最后一秒
	 * @param date
	 * @return
	 */
	public static Long getLastSecondOfWeek(final Date date) {
		final Calendar ca = lastDayOfWeek(date);
		ca.set(Calendar.HOUR_OF_DAY, 0);
		ca.set(Calendar.MINUTE,0);
		ca.set(Calendar.SECOND,0);
		ca.set(Calendar.MILLISECOND,0);
		ca.add(Calendar.DATE,1);
		return ca.getTime().getTime()-1;
	}

	/**
	 * 
	 * 获取当前日期的前一天
	 *
	 * @author zhangshaobin
	 * @created 2013-3-22 上午8:58:12
	 *
	 * @return	以"年年年年-月月-日日"格式返回当前日期的前一天的日期
	 */
	public static String getDayBeforeCurrentDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		//		return dateFormat.format(calendar.getTime());
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 获取指定日期的前一天
	 *
	 * @author zhangshaobin
	 * @created 2013-3-22 上午9:35:59
	 *
	 * @param date
	 * @return	以"年年年年-月月-日日"格式返回指定日期的前一天的日期
	 */
	public static String getDayBeforeDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, -1);
		//		return dateFormat.format(calendar.getTime());
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 获取当前日期的后一天
	 *
	 * @author zhangshaobin
	 * @created 2013-3-22 上午9:44:36
	 *
	 * @return	以"年年年年-月月-日日"格式返回当前日期的后一天的日期
	 */
	public static String getDayAfterCurrentDate() {
		final Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		//		return dateFormat.format(calendar.getTime());
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 获取当前日期的后一天
	 *
	 * @author zhangshaobin
	 * @created 2013-3-22 上午9:44:36
	 *
	 * @return	以"年年年年-月月-日日"格式返回指定日期的后一天的日期
	 */
	public static String getDayAfterDate(final Date date) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DATE, 1);
		//		return dateFormat.format(calendar.getTime());
		return getDateFormat().format(calendar.getTime());
	}

	/**
	 * 
	 * 获取当前时间，精确到秒
	 *
	 * @author zhangshaobin
	 * @created 2013-1-24 下午4:40:19
	 *
	 * @return	精确到秒的当前时间
	 */
	public static int currentTimeSecond() {
		return Long.valueOf(System.currentTimeMillis() / 1000).intValue();
	}

	/**
	 * 
	 * 替换掉日期格式中所有分隔符（含“-”、“:”及空格）
	 *
	 * @author zhangshaobin
	 * @created 2013-6-8 下午1:17:18
	 *
	 * @param target	字符型目标日期
	 * @return	替换后的结果
	 */
	public static String replaceAllSeparator(final String target) {
		return target.replace("-", "").replace(":", "").replace(" ", "");
	}

	/**
	 * 
	 * 替换掉日志格式中指定的分隔符
	 *
	 * @author zhangshaobin
	 * @created 2013-6-8 下午1:18:01
	 *
	 * @param target	字符型目标日期
	 * @param separator	要被替换掉的分割符
	 * @return	替换后的结果
	 */
	public static String replaceSeparator(final String target, final String... separator) {
		String temp = target;
		for (final String sep : separator) {
			temp = temp.replace(sep, "");
		}
		return temp;
	}

	/**
	 * 
	 * 根据步长获取时间
	 *
	 * @author zhangshaobin
	 * @created 2014年12月3日 上午10:41:45
	 *
	 * @param interval 步长 ，正数获取将来时间， 负数获取以前的时间
	 * @param unit 步长单位, 年月周日时分秒
	 * @return
	 */
	public static Date intervalDate(final int interval, final IntervalUnit unit) {
		final Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.setLenient(true);
		c.add(translate(unit), interval);
		return c.getTime();
	}

	private static int translate(final IntervalUnit unit) {
		switch (unit) {
		case DAY:
			return Calendar.DAY_OF_YEAR;
		case HOUR:
			return Calendar.HOUR_OF_DAY;
		case MINUTE:
			return Calendar.MINUTE;
		case MONTH:
			return Calendar.MONTH;
		case SECOND:
			return Calendar.SECOND;
		case MILLISECOND:
			return Calendar.MILLISECOND;
		case WEEK:
			return Calendar.WEEK_OF_YEAR;
		case YEAR:
			return Calendar.YEAR;
		default:
			throw new IllegalArgumentException("Unknown IntervalUnit");
		}
	}

	/**
	 * 获取几天前或几天后的日期
	 * 
	 * @param day
	 *            可为负数,为负数时代表获取之前的日期.为正数,代表获取之后的日期
	 * @return
	 */
	public static Date getTime(final int day) {
		return getTime(new Date(), day);
	}

	/**
	 * 获取指定日期几天前或几天后的日期
	 * 
	 * @param date
	 *            指定的日期
	 * @param day
	 *            可为负数, 为负数时代表获取之前的日志.为正数,代表获取之后的日期
	 * @return
	 */
	public static Date getTime(final Date date, final int day) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + day);
		return calendar.getTime();
	}

	/**
	 * 功能：判断字符串是否为日期格式
	 *
	 * @param strDate
	 * @return
	 */
	public static boolean isDate(String strDate) {
		Pattern pattern = Pattern.compile(
				"^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$");
		Matcher m = pattern.matcher(strDate);
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 
	 * 获取小时之前的时间串
	 *
	 * @author zhangshaobin
	 * @created 2014年12月3日 上午10:41:45
	 *
	 * @param hour 小时
	 * @return
	 */
	public static String getTimeStringOfHourBefore(int hour) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.HOUR, hour*-1);
		return getTimestampFormat().format(calendar.getTime());
	}
	
	/**
	 * 
	 * 获取两个日期之间的天数
	 * 例如：startDate=2016-04-03   endDate=2016-04-05
	 *     返回：2
	 *     
	 * @author zhangshaobin
	 * @created 2016年4月3日 下午午17:41:45
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static int getDatebetweenOfDayNum(Date startDate, Date endDate ) {
		try {
			startDate = parseDate(getDateFormat().format(startDate.getTime()), dateFormatPattern);
			endDate = parseDate(getDateFormat().format(endDate.getTime()), dateFormatPattern);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);  
        long time1 = cal.getTimeInMillis();               
        cal.setTime(endDate);  
        long time2 = cal.getTimeInMillis();       
        long between_days=(time2-time1)/(1000*3600*24);  
          
       return Integer.parseInt(String.valueOf(between_days));  
	}
	
	/**
	 * 获取两个日期间的区间日期
	 * 例如：startDate=2016-04-03   endDate=2016-04-05
	 *     返回：2016-04-03  2016-04-04  2016-04-05  集合
	 *     
	 * @author zhangshaobin
	 * @created 2016年4月3日 下午午17:46:20
	 *     
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<Date> getDatebetweenOfDays(Date startDate, Date endDate) {
		List<Date> list = new ArrayList<Date>();
        GregorianCalendar gc1=new GregorianCalendar(),gc2=new GregorianCalendar();   
        gc1.setTime(startDate);   
        gc2.setTime(endDate);   
        do{   
            GregorianCalendar gc3=(GregorianCalendar)gc1.clone();   
            list.add(gc3.getTime());
            System.out.println(gc3.getTime());
            gc1.add(Calendar.DAY_OF_MONTH, 1);                
         }while(!gc1.after(gc2));   
        return null;   
	}
	
	
	public static void main(String []args) throws ParseException {
		Date d1 = parseDate("2016-04-03 15:20:00", timestampPattern);
		Date d2 = parseDate("2016-04-05 19:20:00", timestampPattern);
		System.out.println(getDatebetweenOfDayNum(d1 , d2));
		
		getDatebetweenOfDays(d1, d2);
	}
	

}
