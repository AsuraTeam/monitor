/**
 * @FileName: ResponseVo.java
 * @Package: com.asura.monitor.common.response
 * @author liusq23
 * @created 7/5/2016 6:46 PM
 * <p/>
 * Copyright 2015 asura
 */
package com.asura.common.response;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author liusq23
 * @since 1.0
 * @version 1.0
 */
public class ResponseVo {

    private static final String RESPONSE_OK = "ok";

    private static final String RESPONSE_ERROR = "error";

    private String status;
    private String message;
    private Object data;

    public ResponseVo() {
    }

    public ResponseVo(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static ResponseVo responseOk(Object data){
        return new ResponseVo(RESPONSE_OK,null,data);
    }

    public static ResponseVo responseError(Object data){
        return new ResponseVo(RESPONSE_ERROR,null,data);
    }
}
