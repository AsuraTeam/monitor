package com.asura.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq14
 * @since 1.0
 * @version 1.0
 */
public class Base64Util {

        // 加密
        public static String encode(String str) {
            byte[] b = null;
            String s = null;
            try {
                b = str.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            if (b != null) {
                s = new BASE64Encoder().encode(b);
            }
            return s;
        }

        // 解密
        public static String decode(String s) {
            byte[] b = null;
            String result = null;
            if (s != null) {
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    b = decoder.decodeBuffer(s);
                    result = new String(b, "utf-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return result;
        }
}
