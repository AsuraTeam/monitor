package com.asura.monitor.report.entity;
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
 * @date 2017-01-26 15:39:45
 * @since 1.0
 */
public class MonitorReportSendEntity extends BaseEntity{

    private String groupsName;

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    /**
     * This field corresponds to the database column monitor_report_send.send_id
     * Comment: 主键
     * @param sendId the value for monitor_report_send.send_id
     */

    private java.lang.Integer sendId;


    /**
     * This field corresponds to the database column monitor_report_send.groups_id
     * Comment: 参数cmdb资源的组id
     * @param groupsId the value for monitor_report_send.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column monitor_report_send.emails
     * Comment: 自动发送报表信息的邮件地址多个用逗号分隔
     * @param emails the value for monitor_report_send.emails
     */

    private java.lang.String emails;


    /**
     * This field corresponds to the database column monitor_report_send.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_report_send.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_report_send.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_report_send.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_report_send.send_id
     * Comment: 主键
     * @param sendId the value for monitor_report_send.send_id
     */
    public void setSendId(java.lang.Integer sendId){
       this.sendId = sendId;
    }

    /**
     * This field corresponds to the database column monitor_report_send.groups_id
     * Comment: 参数cmdb资源的组id
     * @param groupsId the value for monitor_report_send.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_report_send.emails
     * Comment: 自动发送报表信息的邮件地址多个用逗号分隔
     * @param emails the value for monitor_report_send.emails
     */
    public void setEmails(java.lang.String emails){
       this.emails = emails;
    }

    /**
     * This field corresponds to the database column monitor_report_send.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_report_send.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_report_send.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_report_send.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_report_send.send_id
     * Comment: 主键
     * @return the value of monitor_report_send.send_id
     */
     public java.lang.Integer getSendId() {
        return sendId;
    }

    /**
     * This field corresponds to the database column monitor_report_send.groups_id
     * Comment: 参数cmdb资源的组id
     * @return the value of monitor_report_send.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column monitor_report_send.emails
     * Comment: 自动发送报表信息的邮件地址多个用逗号分隔
     * @return the value of monitor_report_send.emails
     */
     public java.lang.String getEmails() {
        return emails;
    }

    /**
     * This field corresponds to the database column monitor_report_send.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_report_send.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_report_send.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_report_send.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }
}
