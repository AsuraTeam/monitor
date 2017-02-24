package com.asura.common.entity;
import com.asura.framework.base.entity.BaseEntity;


/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *
 * @author zhaozq14
 * @version 1.0
 * @date 2017-01-12 10:20:08
 * @since 1.0
 */
public class AuthorityLogEntity extends BaseEntity{
    /**
     * This field corresponds to the database column authority_log.log_id
     * Comment: 
     * @param logId the value for authority_log.log_id
     */

    private java.lang.Integer logId;


    /**
     * This field corresponds to the database column authority_log.ip
     * Comment: 
     * @param ip the value for authority_log.ip
     */

    private java.lang.String ip;


    /**
     * This field corresponds to the database column authority_log.user
     * Comment: 
     * @param user the value for authority_log.user
     */

    private java.lang.String user;


    /**
     * This field corresponds to the database column authority_log.time
     * Comment: 
     * @param time the value for authority_log.time
     */

    private java.lang.String time;


    /**
     * This field corresponds to the database column authority_log.info
     * Comment: 
     * @param info the value for authority_log.info
     */

    private java.lang.String info;


    /**
     * This field corresponds to the database column authority_log.log_id
     * Comment: 
     * @param logId the value for authority_log.log_id
     */
    public void setLogId(java.lang.Integer logId){
       this.logId = logId;
    }

    /**
     * This field corresponds to the database column authority_log.ip
     * Comment: 
     * @param ip the value for authority_log.ip
     */
    public void setIp(java.lang.String ip){
       this.ip = ip;
    }

    /**
     * This field corresponds to the database column authority_log.user
     * Comment: 
     * @param user the value for authority_log.user
     */
    public void setUser(java.lang.String user){
       this.user = user;
    }

    /**
     * This field corresponds to the database column authority_log.time
     * Comment: 
     * @param time the value for authority_log.time
     */
    public void setTime(java.lang.String time){
       this.time = time;
    }

    /**
     * This field corresponds to the database column authority_log.info
     * Comment: 
     * @param info the value for authority_log.info
     */
    public void setInfo(java.lang.String info){
       this.info = info;
    }

    /**
     * This field corresponds to the database column authority_log.log_id
     * Comment: 
     * @return the value of authority_log.log_id
     */
     public java.lang.Integer getLogId() {
        return logId;
    }

    /**
     * This field corresponds to the database column authority_log.ip
     * Comment: 
     * @return the value of authority_log.ip
     */
     public java.lang.String getIp() {
        return ip;
    }

    /**
     * This field corresponds to the database column authority_log.user
     * Comment: 
     * @return the value of authority_log.user
     */
     public java.lang.String getUser() {
        return user;
    }

    /**
     * This field corresponds to the database column authority_log.time
     * Comment: 
     * @return the value of authority_log.time
     */
     public java.lang.String getTime() {
        return time;
    }

    /**
     * This field corresponds to the database column authority_log.info
     * Comment: 
     * @return the value of authority_log.info
     */
     public java.lang.String getInfo() {
        return info;
    }
}
