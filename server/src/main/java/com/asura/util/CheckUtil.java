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

public class CheckUtil {

    /**
     * 检查字符串是否为空
     * @param str
     * @return
     */
    public static boolean checkString(String str){
        if (str != null && str.length() > 0){
            return true;
        }
        return false;
    }

}
