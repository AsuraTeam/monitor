package com.asura.util;

import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author zy
 * @Date 2016-06-30
 * 请求body的json转换
 */
public class RequestJsonUtil {

	private static Logger LOGGER = LoggerFactory.getLogger(RequestJsonUtil.class);
	private static Gson gson = new Gson();
    private static Object object = new Object();
    
    /**
     * 
     * @param request
     * @param obj
     * @return
     */
	public static <T> Object toObject(HttpServletRequest request, Class<T> obj){
		
		try {
			InputStream is = request.getInputStream();
	        String content = IOUtils.toString(is,"utf-8");
	        object = gson.fromJson(content, obj);
			 return object;
		} catch (IOException e) {
			LOGGER.error("ERROR:",e);
		}
	    return obj;
	}
}
