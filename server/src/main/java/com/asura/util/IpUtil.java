package com.asura.util;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class IpUtil {

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 判断是否为合法IP
     * @return the ip
     */
    public static boolean isIP(String addr)
    {
        if(addr.length() < 7 || addr.length() > 15 || "".equals(addr))
        {
            return false;
        }
        String[] arrs = addr.split("\\.");
        if (arrs.length != 4){
            return false;
        }

        for (String ip:arrs){
            if (!isInteger(ip) ){
                return false;
            }
            if (Integer.valueOf(ip)>255 ){
                return false;
            }
        }
        if (Integer.valueOf(arrs[0])==0){return false;}
        return true;
    }


}
