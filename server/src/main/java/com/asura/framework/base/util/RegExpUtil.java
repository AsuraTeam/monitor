package com.asura.framework.base.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

/**
 * 
 * <p>正则表达式工具</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author liushengqi
 * @since 1.0
 * @version 1.0
 */
public class RegExpUtil {

	public static final String REG_MOBILE = "^1[3|5|7|8|][0-9]{9}$";
	//邮箱正则表达式
	public static final String REG_EMAIL = "^[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?$";

	public static final String IDENTIFY_CARD_NUM = "^(\\d{18}|\\d{15}|\\d{17}[xX])$";

	public static final String CHINESE = "^[\\u4e00-\\u9fa5]*$";

	public static final String PASSPORT = "^[A-Za-z0-9]{1,20}$";

	public static final String JG = "^[\\u4e00-\\u9fa5a-zA-Z0-9]{1,20}$";

	private static String[] valCodeArr = { "1", "0", "x", "9", "8", "7", "6", "5", "4","3", "2" };

	private static String[] wi = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7","9", "10", "5", "8", "4", "2" };
	
	public static final String REG_TIME = "^(?:[01]\\d|2[0-3])(?::[0-5]\\d)$";
	
	public static boolean isMobilePhoneNum(final String mobilePhoneNum) {
		if (mobilePhoneNum == null || "".equals(mobilePhoneNum))
			return false;
		return mobilePhoneNum.matches(REG_MOBILE);
	}

	public static boolean isEmailAddress(final String emailAddress) {
		if (emailAddress == null || "".equals(emailAddress))
			return false;
		return emailAddress.matches(REG_EMAIL);
	}

	/**身份证校验*/
	public static boolean idIdentifyCardNum(final String idNum) {
		if (idNum == null || "".equals(idNum))
			return false;
		if(!idNum.matches(IDENTIFY_CARD_NUM)){
			return false;
		}
		String ai="";
		if (idNum.length() == 18) {
			ai = idNum.substring(0, 17);
		} else if (idNum.length() == 15) {
			ai = idNum.substring(0, 6) + "19" + idNum.substring(6, 15);
		}
		String strYear = idNum.substring(6, 10);// 年份
		String strMonth = idNum.substring(10, 12);// 月份
		String strDay = idNum.substring(12, 14);// 日
		if (!DateUtil.isDate(strYear + "-" + strMonth + "-" + strDay)) {
			return false;
		}
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
		try {
			if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
					|| (gc.getTime().getTime() - s.parse(
					strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
				return false;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return false;
		} catch (java.text.ParseException e) {
			e.printStackTrace();
			return false;
		}
		if(getAreaCode().get(ai.substring(0, 2)) == null){
			return false;
		}
		int totalmulAiWi = 0;
		for (int i = 0; i < 17; i++) {
			totalmulAiWi = totalmulAiWi
					+ Integer.parseInt(String.valueOf(ai.charAt(i)))
					* Integer.parseInt(wi[i]);
		}
		int modValue = totalmulAiWi % 11;
		String strVerifyCode = valCodeArr[modValue];
		ai = ai + strVerifyCode;
		if (idNum.length() == 18) {
			if (ai.equalsIgnoreCase(idNum) == false) {
				return false;
			}
		}
		return true;

	}

	public static boolean isChineseCharacter(String character){
		if(Check.NuNStr(character)) return false;
		return character.matches(CHINESE);
	}

	/**
	 * 是否护照
	 * @param passport
	 * @return
	 */
	public static boolean isPassportNum(String passport){
		if(Check.NuNStr(passport)) return false;
		return passport.matches(PASSPORT);
	}

	/**
	 * 是否港澳台胞证
	 * @param passport
	 * @return
	 */
	public static boolean isHMT(String passport){
		if(Check.NuNStr(passport)) return false;
		return passport.matches(PASSPORT);
	}

	/**
	 * 是否军官证
	 * @param jg
	 * @return
	 */
	public static boolean isJG(String jg){
		if(Check.NuNStr(jg)) return false;
		return jg.matches(JG);
	}
	/**
	 * 是否是HH:MM格式的时间字符串
	 * @param jg
	 * @return
	 */
	public static boolean isHHMMTime(String time){
		if(Check.NuNStr(time)) return false;
		return time.matches(REG_TIME);
	}
	private static Hashtable<String,String> getAreaCode() {
		Hashtable<String,String> hashtable = new Hashtable<String,String>();
		hashtable.put("11", "北京");
		hashtable.put("12", "天津");
		hashtable.put("13", "河北");
		hashtable.put("14", "山西");
		hashtable.put("15", "内蒙古");
		hashtable.put("21", "辽宁");
		hashtable.put("22", "吉林");
		hashtable.put("23", "黑龙江");
		hashtable.put("31", "上海");
		hashtable.put("32", "江苏");
		hashtable.put("33", "浙江");
		hashtable.put("34", "安徽");
		hashtable.put("35", "福建");
		hashtable.put("36", "江西");
		hashtable.put("37", "山东");
		hashtable.put("41", "河南");
		hashtable.put("42", "湖北");
		hashtable.put("43", "湖南");
		hashtable.put("44", "广东");
		hashtable.put("45", "广西");
		hashtable.put("46", "海南");
		hashtable.put("50", "重庆");
		hashtable.put("51", "四川");
		hashtable.put("52", "贵州");
		hashtable.put("53", "云南");
		hashtable.put("54", "西藏");
		hashtable.put("61", "陕西");
		hashtable.put("62", "甘肃");
		hashtable.put("63", "青海");
		hashtable.put("64", "宁夏");
		hashtable.put("65", "新疆");
		hashtable.put("71", "台湾");
		hashtable.put("81", "香港");
		hashtable.put("82", "澳门");
		hashtable.put("91", "国外");
		return hashtable;
	}
}
