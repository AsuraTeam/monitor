/**
 * @FileName: BaseEntity.java
 * @Package com.asura.framework.entity
 * 
 * @author zhangshaobin
 * @created 2014年10月17日 下午2:02:44
 * 
 * Copyright 2011-2015 Asura
 */
package com.asura.framework.base.entity;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import com.asura.framework.base.util.JsonEntityTransform;

/**
 * <p>实体基类</p>
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
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 3096849013001446703L;

	/**
	 * 
	 * toString方法，返回属性名称及值
	 *
	 * @author zhangshaobin
	 * @created 2012-12-19 上午10:16:37
	 *
	 * @return	属性名称及值，格式：[name]张三 [sex]男
	 */
	@Override
	public String toString() {
		StringBuffer result = new StringBuffer();
		try {
			Class<? extends Object> clazz = this.getClass();
			Field[] fields = clazz.getDeclaredFields();
			for (Field field : fields) {
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
						+ fieldName.substring(1, fieldName.length());
				Method method = null;
				Object resultObj = null;
				method = clazz.getMethod(methodName);
				resultObj = method.invoke(this);
				if (resultObj != null && !"".equals(resultObj)) {
					result.append("[").append(fieldName).append("]").append(resultObj).append(" ");
				}
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return result.toString();
	}

	/**
	 * 
	 * 根据字段转换json串
	 *
	 * @author zhangshaobin
	 * @created 2014年11月28日 下午3:33:23
	 *
	 * @param paramName
	 * @return
	 */
	public String toJsonStr(String... paramName) {
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		Map<String, Object> map = this.toMap();
		for (String pn : paramName) {
			if (map.get(pn) != null) {
				jsonMap.put(pn, map.get(pn));
			}
		}
		return JsonEntityTransform.Object2Json(jsonMap);
	}

	/**
	 * 
	 * 根据字段转换json串
	 *
	 * @author zhangshaobin
	 * @created 2014年11月28日 下午3:33:23
	 *
	 * @param paramName
	 * @return
	 */
	public String toJsonStr() {
		return JsonEntityTransform.Object2Json(this);
	}

	/**
	 * 
	 * 对象转换成map
	 *
	 * @author zhangshaobin
	 * @created 2014年11月28日 下午3:35:05
	 *
	 * @return
	 */
	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		Class<? extends Object> clazz = this.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			try {
				String fieldName = field.getName();
				if ("serialVersionUID".equals(fieldName)) {
					continue;
				}
				String methodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				map.put(fieldName, clazz.getMethod(methodName).invoke(this));

			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return map;
	}

}
