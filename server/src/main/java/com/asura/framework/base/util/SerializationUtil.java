/**
 * @FileName: SerializationUtil.java
 * @Package com.asura.framework.util
 * 
 * @author zhangshaobin
 * @created 2012-12-14 上午11:13:27
 * 
 * Copyright 2011-2015 asura
 */
package com.asura.framework.base.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * <p>序列化及反序列化工具类</p>
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
public class SerializationUtil {
	
	/**
	 * 私有构造器
	 */
    private SerializationUtil() {}
    
    /**
     * 
     * 深度克隆对象
     *
     * @author zhangshaobin
     * @created 2012-12-14 上午11:14:38
     *
     * @param object	可序列化的被克隆对象
     * @return	克隆后的对象
     */
    public static Object clone(Serializable object) {
        return deserialize(serialize(object));
    }
    
    /**
     * 
     * 将对象序列化为字节数组
     *
     * @author zhangshaobin
     * @created 2012-12-14 上午11:17:29
     *
     * @param obj	要序列化的对象
     * @return	序列化后的字节数组
     */
    public static byte[] serialize(Serializable obj) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(baos);
            out.writeObject(obj);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return baos.toByteArray();
    }
    
    /**
     * 
     * 将字节数组反序列化为对象
     *
     * @author zhangshaobin
     * @created 2012-12-14 上午11:18:33
     *
     * @param objectData	要反序列化的字节数组
     * @return	反序列化后的对象
     */
    public static Object deserialize(byte[] objectData) {
        if (objectData == null) {
            throw new IllegalArgumentException("The byte[] must not be null");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(objectData);
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(bais);
            return in.readObject();
            
        } catch (ClassNotFoundException ex) {
        	throw new RuntimeException(ex);
        } catch (IOException ex) {
        	throw new RuntimeException(ex);
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
    }

}
