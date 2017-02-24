package com.asura.monitor.monitor.entity;
import com.asura.framework.base.entity.BaseEntity;

import java.sql.Timestamp;


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
 * @date 2016-07-24 09:04:53
 * @since 1.0
 */
public class CmdbMonitorMessagesEntity extends BaseEntity{

    // 开始时间
    private Timestamp start;

    // 结束时间
    private Timestamp end;

    // 每小时统计数
    private int cnt;

    // 时间的每小时
    private  String hours;

    // 监控脚本名称
    private String  monitorName;

    // 组名
    private String groups;

    // 状态
    private  String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    public  String getHours() {
        return hours;
    }
    public void   setHours(String hours) {
        this.hours = hours;
    }
    public int    getCnt() {
        return cnt;
    }
    public void   setCnt(int cnt) {
        this.cnt = cnt;
    }

    public Timestamp getStart() {
        return start;
    }

    public void setStart(Timestamp start) {
        this.start = start;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }



    /**
     * This field corresponds to the database column cmdb_monitor_messages.id
     * Comment: 主键
     * @param id the value for cmdb_monitor_messages.id
     */

    private java.lang.Integer id;


    /**
     * This field corresponds to the database column cmdb_monitor_messages.mobile
     * Comment: 接收报警手机
     * @param mobile the value for cmdb_monitor_messages.mobile
     */

    private java.lang.String mobile;


    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages
     * Comment: 发送报警信息
     * @param messages the value for cmdb_monitor_messages.messages
     */

    private java.lang.String messages;


    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages_id
     * Comment: 发送报警类型
     * @param messagesId the value for cmdb_monitor_messages.messages_id
     */

    private java.lang.String messagesId;


    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages_time
     * Comment: 插入数据时间
     * @param messagesTime the value for cmdb_monitor_messages.messages_time
     */

    private java.sql.Timestamp messagesTime;


    /**
     * This field corresponds to the database column cmdb_monitor_messages.id
     * Comment: 主键
     * @param id the value for cmdb_monitor_messages.id
     */
    public void setId(java.lang.Integer id){
       this.id = id;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.mobile
     * Comment: 接收报警手机
     * @param mobile the value for cmdb_monitor_messages.mobile
     */
    public void setMobile(java.lang.String mobile){
       this.mobile = mobile;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages
     * Comment: 发送报警信息
     * @param messages the value for cmdb_monitor_messages.messages
     */
    public void setMessages(java.lang.String messages){
       this.messages = messages;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages_id
     * Comment: 发送报警类型
     * @param messagesId the value for cmdb_monitor_messages.messages_id
     */
    public void setMessagesId(java.lang.String messagesId){
       this.messagesId = messagesId;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages_time
     * Comment: 插入数据时间
     * @param messagesTime the value for cmdb_monitor_messages.messages_time
     */
    public void setMessagesTime(java.sql.Timestamp messagesTime){
       this.messagesTime = messagesTime;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.id
     * Comment: 主键
     * @return the value of cmdb_monitor_messages.id
     */
     public java.lang.Integer getId() {
        return id;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.mobile
     * Comment: 接收报警手机
     * @return the value of cmdb_monitor_messages.mobile
     */
     public java.lang.String getMobile() {
        return mobile;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages
     * Comment: 发送报警信息
     * @return the value of cmdb_monitor_messages.messages
     */
     public java.lang.String getMessages() {
        return messages;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages_id
     * Comment: 发送报警类型
     * @return the value of cmdb_monitor_messages.messages_id
     */
     public java.lang.String getMessagesId() {
        return messagesId;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_messages.messages_time
     * Comment: 插入数据时间
     * @return the value of cmdb_monitor_messages.messages_time
     */
     public java.sql.Timestamp getMessagesTime() {
        return messagesTime;
    }
}
