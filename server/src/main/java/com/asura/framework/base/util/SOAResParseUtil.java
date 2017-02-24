/**
 * @FileName: SOAResParseUtil.java
 * @Package: com.asura.cleaning.util
 * @author sence
 * @created 9/14/2015 4:18 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.framework.base.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.asura.framework.base.exception.SOAParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>SOA接口返回数据解析工具类</p>
 * <p/>
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author sence
 * @version 1.0
 * @since 1.0
 */
public class SOAResParseUtil {

    public static final Logger LOGGER = LoggerFactory.getLogger(SOAResParseUtil.class);

    /**
     * 私有化构造，工具类
     */
    private SOAResParseUtil() {

    }

    /**
     * 获取返回的JSON对象
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午5:59:58
     */
    public static JSONObject getJsonObj(final String result) {
        try {
            return JSON.parseObject(result);
        } catch (final JSONException e) {
            LOGGER.error("解析SOA返回JSON结果错误！", e);
            return null;
        }
    }

    /**
     * 获取返回对象中的data对象，需要SOA响应状态码是0
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月9日 上午11:22:17
     */
    public static JSONObject getDataObj(final String result) {
        final JSONObject obj = getJsonObj(result);
        JSONObject data = null;
        if (null != obj && 0 == obj.getIntValue("code") && obj.containsKey("data")) {
            data = obj.getJSONObject("data");
        }
        return data;
    }

    /**
     * 根据key获取返回对象data中的字符串值(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static String getStrFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getString(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的整数(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Integer getIntFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getInteger(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的长整数(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Long getLongFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getLong(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的float(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Float getFloatFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getFloat(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的double(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Double getDoubleFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getDouble(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的Boolean值(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static Boolean getBooleanFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getBoolean(key);
        } else {
            return Boolean.FALSE;
        }
    }

    /**
     * 根据key获取返回对象data中的JSON对象(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static JSONObject getJsonObjFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getJSONObject(key);
        } else {
            return null;
        }
    }

    /**
     * 根据key获取返回对象data中的Array对象(只是第一层key),需要soa响应状态码是0
     *
     * @param result
     * @param key
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:00:25
     */
    public static JSONArray getArrayFromDataByKey(final String result, final String key) {
        final JSONObject data = getDataObj(result);
        if (null != data) {
            return data.getJSONArray(key);
        } else {
            return null;
        }
    }

    /**
     * 获取返回JSON对象的响应状态码
     *
     * @param result
     * @return
     * @author xuxiao
     * @created 2014年5月7日 下午6:01:09
     */
    public static int getReturnCode(final String result) {
        LOGGER.debug("return code,result:{}", result);
        final JSONObject json = getJsonObj(result);
        if (null != json && json.containsKey("code")) {
            return json.getIntValue("code");
        } else {
            return -1;
        }
    }

    /**
     * 从SOA内直接取得对应的class
     *
     * @param result
     * @param key
     * @param clazz
     * @return
     * @throws SOAParseException
     * @author liushengqi
     * @created 2014年5月14日 上午9:58:18
     */
    public static <T> T getValueFromDataByKey(final String result, final String key, Class<T> clazz)
            throws SOAParseException {
        String keyValue = getStrFromDataByKey(result, key);
        if (keyValue == null) {
            LOGGER.info("key was null,result:{},key:{},class:{}", result, key, clazz.getName());
            return null;
        } else {
            try {
                return JSON.parseObject(keyValue, clazz);
            } catch (Exception e) {
                LOGGER.error("PARSE SOA RETURN ERROR,result:{},key:{},class:{},exception:{}", result, key, clazz.getName(),e);
                throw new SOAParseException("SOA return data:[" + keyValue + "] can not cast to list of " + clazz.getName()+" error:"+e);
            }
        }
    }

    /**
     * 从SOA内直接取得对应的class list
     *
     * @param result
     * @param key
     * @param clazz
     * @return
     * @throws SOAParseException
     * @author liushengqi
     * @created 2014年5月14日 上午10:19:52
     */
    public static <T> List<T> getListValueFromDataByKey(final String result, final String key, Class<T> clazz)
            throws SOAParseException {
        String keyValue = getStrFromDataByKey(result, key);
        if (keyValue == null) {
            LOGGER.info("key was null,result:{},key:{},class:{}", result, key, clazz.getName());
            return new ArrayList<>();
        } else {
            try {
                return JSON.parseArray(keyValue, clazz);
            } catch (Exception e) {
                LOGGER.error("pasre soa json error,result:{},key:{},class:{},exception:{}", result, key, clazz.getName(),e);
                throw new SOAParseException("SOA return data:[" + keyValue + "] can not cast to class " + clazz.getName()+" error:"+e);
            }
        }
    }

    /**
     * 判断SOA返回结果是否符合期望结果
     *
     * @param result
     * @param expectCode
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午9:48:45
     */
    public static boolean checkSOAReturnExpect(String result, Integer expectCode) {
        int returnCode = getReturnCode(result);
        return returnCode == expectCode;
    }

    /**
     * 判断SOA返回结果是否符合期望结果,使用默认期望code 0
     *
     * @param result
     * @return
     * @author liushengqi
     * @created 2014年5月14日 上午9:50:06
     */
    public static boolean checkSOAReturnSuccess(String result) {
        return checkSOAReturnExpect(result, 0);
    }

    /**
     * 获取返回JSON对象的响应信息
     *
     * @param result
     * @created 2014年5月7日 下午6:01:09
     */
    public static String getReturnMsg(String result) {
        LOGGER.debug("return msg,result:{}", result);
        final JSONObject json = getJsonObj(result);
        if (null != json && json.containsKey("msg")) {
            return json.getString("msg");
        } else {
            return null;
        }
    }
}
