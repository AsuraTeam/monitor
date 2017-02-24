package com.asura.monitor.configure.entity;
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
 * @date 2016-08-20 09:37:40
 * @since 1.0
 */
public class MonitorSeverityEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_severity.severity_id
     * Comment: 主键
     * @param severityId the value for monitor_severity.severity_id
     */

    private java.lang.Integer severityId;


    /**
     * This field corresponds to the database column monitor_severity.severity_name
     * Comment: 级别名称
     * @param severityName the value for monitor_severity.severity_name
     */

    private java.lang.String severityName;


    /**
     * This field corresponds to the database column monitor_severity.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_severity.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_severity.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_severity.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_severity.severity_id
     * Comment: 主键
     * @param severityId the value for monitor_severity.severity_id
     */
    public void setSeverityId(java.lang.Integer severityId){
       this.severityId = severityId;
    }

    /**
     * This field corresponds to the database column monitor_severity.severity_name
     * Comment: 级别名称
     * @param severityName the value for monitor_severity.severity_name
     */
    public void setSeverityName(java.lang.String severityName){
       this.severityName = severityName;
    }

    /**
     * This field corresponds to the database column monitor_severity.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_severity.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_severity.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_severity.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_severity.severity_id
     * Comment: 主键
     * @return the value of monitor_severity.severity_id
     */
     public java.lang.Integer getSeverityId() {
        return severityId;
    }

    /**
     * This field corresponds to the database column monitor_severity.severity_name
     * Comment: 级别名称
     * @return the value of monitor_severity.severity_name
     */
     public java.lang.String getSeverityName() {
        return severityName;
    }

    /**
     * This field corresponds to the database column monitor_severity.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_severity.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_severity.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_severity.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
