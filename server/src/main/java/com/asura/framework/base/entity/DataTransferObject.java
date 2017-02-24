/**
 * @FileName: DataTransferObject.java
 * @Package com.asura.framework.entity
 * 
 * @author zhangshaobin
 * @created 2014年10月17日 下午2:05:03
 * 
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.entity;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.asura.framework.base.exception.BusinessException;
import com.asura.framework.base.util.Check;
import com.asura.framework.base.util.JsonEntityTransform;

/**
 * <p>数据传输对象，包装数据传输内容</p>
 * 
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 
 * @author zhangshaobin
 * @since 1.0
 * @version 1.0
 */
public class DataTransferObject implements Serializable {

   private static final long serialVersionUID = -2443929344379556217L;
   
   public static final Logger LOGGER = LoggerFactory.getLogger(DataTransferObject.class);

	/** 成功 */
	public static final int SUCCESS = 0;

	/** 失败 */
	public static final int ERROR = 1;

	/**
	 * 编码
	 */
	private int code = 0;

	/**
	 * 消息（错误消息）
	 */
	private String msg = "";

	/**
	 * 成功消息数据
	 */
	private Map<String, Object> data = new HashMap<String, Object>();

	public DataTransferObject() {

	}

	public DataTransferObject(final int code, final String msg, final Map<String, Object> data) {
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	public void setErrCode(final int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(final String msg) {
		this.msg = msg;
	}

	/**
	 * @return the data
	 */
	public Map<String, Object> getData() {
		return data;
	}

	/**
	 * 
	 * 向data中写入Entity
	 *
	 * @author zhangshaobin
	 * @created 2012-11-14 下午2:09:33
	 *
	 * @param key
	 * @param value
	 */
	public void putValue(final String key, final Object value) {
		data.put(key, value);
	}

	/**
	 * 
	 * 转换成json字符串
	 *
	 * @author zhangshaobin
	 * @created 2014年10月22日 下午2:39:45
	 *
	 * @return
	 */
	public String toJsonString() {
		return JsonEntityTransform.entity2Json(this);
	}
	
	/**
     * dto内部元素转换成Object
     * @author jiangnian
     * @param dto
     * @param key
     * @param type
     * @return
     */
    public  <T> T  parseData(String key,TypeReference<T> type){
        ObjectMapper mapper = new ObjectMapper();
        try {
            if(Check.NuNObj(this)){
                return null;
            }
            Map<?, ?> map=this.getData();
            if(Check.NuNMap(map)){
                return null;
            }
            Object obj=map.get(key);
            String json=JsonEntityTransform.Object2Json(obj);   
            return mapper.readValue(json, type);  
        } catch (JsonParseException e) {
            LOGGER.error("parseData error: JsonParseException", e);
            throw new BusinessException("dto内部元素转换成Object异常。", e);
        } catch (JsonMappingException e) {
            LOGGER.error("parseData error: JsonMappingException", e);
            throw new BusinessException("dto内部元素转换成Object异常。", e);
        } catch (IOException e) {
            LOGGER.error("parseData error: IOException", e);
            throw new BusinessException("dto内部元素转换成Object异常。", e);
        }
    }
}
