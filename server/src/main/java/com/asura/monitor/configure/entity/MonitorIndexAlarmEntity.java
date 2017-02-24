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
 * @date 2016-11-18 08:59:10
 * @since 1.0
 */
public class MonitorIndexAlarmEntity extends BaseEntity{

    private String indexName;
    private String ipAddress;
    private boolean sendAlarm;
    private String sendMessages;
    private boolean isConfigure;
    private String  allGroups;
    private String alarmCount;
    private String alarmInterval;

    public String getAlarmInterval() {
        return alarmInterval;
    }

    public void setAlarmInterval(String alarmInterval) {
        this.alarmInterval = alarmInterval;
    }

    public String getAlarmCount() {
        return alarmCount;
    }

    public void setAlarmCount(String alarmCount) {
        this.alarmCount = alarmCount;
    }

    public String getAllGroups() {
        return allGroups;
    }

    public void setAllGroups(String allGroups) {
        this.allGroups = allGroups;
    }

    public boolean getIsConfigure() {
        return isConfigure;
    }

    public void setIsConfigure(boolean noConfigure) {
        isConfigure = noConfigure;
    }

    public String getSendMessages() {
        return sendMessages;
    }

    public void setSendMessages(String sendMessages) {
        this.sendMessages = sendMessages;
    }

    public boolean getSendAlarm() {
        return sendAlarm;
    }

    public void setSendAlarm(boolean sendAlarm) {
        this.sendAlarm = sendAlarm;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.index_id
     * Comment: 参考指标表id
     * @param indexId the value for monitor_index_alarm.index_id
     */

    private java.lang.Integer indexId;


    /**
     * This field corresponds to the database column monitor_index_alarm.server_id
     * Comment: 参考cmdb的表server_id
     * @param serverId the value for monitor_index_alarm.server_id
     */

    private java.lang.Integer serverId;


    /**
     * This field corresponds to the database column monitor_index_alarm.gt_value
     * Comment: 
     * @param gtValue the value for monitor_index_alarm.gt_value
     */

    private java.lang.String gtValue;


    /**
     * This field corresponds to the database column monitor_index_alarm.lt_value
     * Comment: 
     * @param ltValue the value for monitor_index_alarm.lt_value
     */

    private java.lang.String ltValue;


    /**
     * This field corresponds to the database column monitor_index_alarm.eq_value
     * Comment: 
     * @param eqValue the value for monitor_index_alarm.eq_value
     */

    private java.lang.String eqValue;


    /**
     * This field corresponds to the database column monitor_index_alarm.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_index_alarm.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_index_alarm.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_index_alarm.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_index_alarm.status_id
     * Comment: 条件达成后报警级别
     * @param statusId the value for monitor_index_alarm.status_id
     */

    private java.lang.Integer statusId;


    /**
     * This field corresponds to the database column monitor_index_alarm.weixin_groups
     * Comment: 微信组
     * @param weixinGroups the value for monitor_index_alarm.weixin_groups
     */

    private java.lang.String weixinGroups;


    /**
     * This field corresponds to the database column monitor_index_alarm.ding_groups
     * Comment: 
     * @param dingGroups the value for monitor_index_alarm.ding_groups
     */

    private java.lang.String dingGroups;


    /**
     * This field corresponds to the database column monitor_index_alarm.mobile_groups
     * Comment: 
     * @param mobileGroups the value for monitor_index_alarm.mobile_groups
     */

    private java.lang.String mobileGroups;


    /**
     * This field corresponds to the database column monitor_index_alarm.email_groups
     * Comment: 
     * @param emailGroups the value for monitor_index_alarm.email_groups
     */

    private java.lang.String emailGroups;


    /**
     * This field corresponds to the database column monitor_index_alarm.alarm_id
     * Comment: 
     * @param alarmId the value for monitor_index_alarm.alarm_id
     */

    private java.lang.Integer alarmId;


    /**
     * This field corresponds to the database column monitor_index_alarm.is_mobile
     * Comment: 
     * @param isMobile the value for monitor_index_alarm.is_mobile
     */

    private java.lang.Integer isMobile;


    /**
     * This field corresponds to the database column monitor_index_alarm.is_email
     * Comment: 
     * @param isEmail the value for monitor_index_alarm.is_email
     */

    private java.lang.Integer isEmail;


    /**
     * This field corresponds to the database column monitor_index_alarm.is_ding
     * Comment: 
     * @param isDing the value for monitor_index_alarm.is_ding
     */

    private java.lang.Integer isDing;


    /**
     * This field corresponds to the database column monitor_index_alarm.is_weixin
     * Comment: 
     * @param isWeixin the value for monitor_index_alarm.is_weixin
     */

    private java.lang.Integer isWeixin;


    /**
     * This field corresponds to the database column monitor_index_alarm.is_alarm
     * Comment: 
     * @param isAlarm the value for monitor_index_alarm.is_alarm
     */

    private java.lang.Integer isAlarm;


    /**
     * This field corresponds to the database column monitor_index_alarm.not_eq_value
     * Comment: 
     * @param notEqValue the value for monitor_index_alarm.not_eq_value
     */

    private java.lang.String notEqValue;


    /**
     * This field corresponds to the database column monitor_index_alarm.index_id
     * Comment: 参考指标表id
     * @param indexId the value for monitor_index_alarm.index_id
     */
    public void setIndexId(java.lang.Integer indexId){
       this.indexId = indexId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.server_id
     * Comment: 参考cmdb的表server_id
     * @param serverId the value for monitor_index_alarm.server_id
     */
    public void setServerId(java.lang.Integer serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.gt_value
     * Comment: 
     * @param gtValue the value for monitor_index_alarm.gt_value
     */
    public void setGtValue(java.lang.String gtValue){
       this.gtValue = gtValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.lt_value
     * Comment: 
     * @param ltValue the value for monitor_index_alarm.lt_value
     */
    public void setLtValue(java.lang.String ltValue){
       this.ltValue = ltValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.eq_value
     * Comment: 
     * @param eqValue the value for monitor_index_alarm.eq_value
     */
    public void setEqValue(java.lang.String eqValue){
       this.eqValue = eqValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_index_alarm.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_index_alarm.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.status_id
     * Comment: 条件达成后报警级别
     * @param statusId the value for monitor_index_alarm.status_id
     */
    public void setStatusId(java.lang.Integer statusId){
       this.statusId = statusId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.weixin_groups
     * Comment: 微信组
     * @param weixinGroups the value for monitor_index_alarm.weixin_groups
     */
    public void setWeixinGroups(java.lang.String weixinGroups){
       this.weixinGroups = weixinGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.ding_groups
     * Comment: 
     * @param dingGroups the value for monitor_index_alarm.ding_groups
     */
    public void setDingGroups(java.lang.String dingGroups){
       this.dingGroups = dingGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.mobile_groups
     * Comment: 
     * @param mobileGroups the value for monitor_index_alarm.mobile_groups
     */
    public void setMobileGroups(java.lang.String mobileGroups){
       this.mobileGroups = mobileGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.email_groups
     * Comment: 
     * @param emailGroups the value for monitor_index_alarm.email_groups
     */
    public void setEmailGroups(java.lang.String emailGroups){
       this.emailGroups = emailGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.alarm_id
     * Comment: 
     * @param alarmId the value for monitor_index_alarm.alarm_id
     */
    public void setAlarmId(java.lang.Integer alarmId){
       this.alarmId = alarmId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_mobile
     * Comment: 
     * @param isMobile the value for monitor_index_alarm.is_mobile
     */
    public void setIsMobile(java.lang.Integer isMobile){
       this.isMobile = isMobile;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_email
     * Comment: 
     * @param isEmail the value for monitor_index_alarm.is_email
     */
    public void setIsEmail(java.lang.Integer isEmail){
       this.isEmail = isEmail;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_ding
     * Comment: 
     * @param isDing the value for monitor_index_alarm.is_ding
     */
    public void setIsDing(java.lang.Integer isDing){
       this.isDing = isDing;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_weixin
     * Comment: 
     * @param isWeixin the value for monitor_index_alarm.is_weixin
     */
    public void setIsWeixin(java.lang.Integer isWeixin){
       this.isWeixin = isWeixin;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_alarm
     * Comment: 
     * @param isAlarm the value for monitor_index_alarm.is_alarm
     */
    public void setIsAlarm(java.lang.Integer isAlarm){
       this.isAlarm = isAlarm;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.not_eq_value
     * Comment: 
     * @param notEqValue the value for monitor_index_alarm.not_eq_value
     */
    public void setNotEqValue(java.lang.String notEqValue){
       this.notEqValue = notEqValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.index_id
     * Comment: 参考指标表id
     * @return the value of monitor_index_alarm.index_id
     */
     public java.lang.Integer getIndexId() {
        return indexId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.server_id
     * Comment: 参考cmdb的表server_id
     * @return the value of monitor_index_alarm.server_id
     */
     public java.lang.Integer getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.gt_value
     * Comment: 
     * @return the value of monitor_index_alarm.gt_value
     */
     public java.lang.String getGtValue() {
        return gtValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.lt_value
     * Comment: 
     * @return the value of monitor_index_alarm.lt_value
     */
     public java.lang.String getLtValue() {
        return ltValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.eq_value
     * Comment: 
     * @return the value of monitor_index_alarm.eq_value
     */
     public java.lang.String getEqValue() {
        return eqValue;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_index_alarm.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_index_alarm.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.status_id
     * Comment: 条件达成后报警级别
     * @return the value of monitor_index_alarm.status_id
     */
     public java.lang.Integer getStatusId() {
        return statusId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.weixin_groups
     * Comment: 微信组
     * @return the value of monitor_index_alarm.weixin_groups
     */
     public java.lang.String getWeixinGroups() {
        return weixinGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.ding_groups
     * Comment: 
     * @return the value of monitor_index_alarm.ding_groups
     */
     public java.lang.String getDingGroups() {
        return dingGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.mobile_groups
     * Comment: 
     * @return the value of monitor_index_alarm.mobile_groups
     */
     public java.lang.String getMobileGroups() {
        return mobileGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.email_groups
     * Comment: 
     * @return the value of monitor_index_alarm.email_groups
     */
     public java.lang.String getEmailGroups() {
        return emailGroups;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.alarm_id
     * Comment: 
     * @return the value of monitor_index_alarm.alarm_id
     */
     public java.lang.Integer getAlarmId() {
        return alarmId;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_mobile
     * Comment: 
     * @return the value of monitor_index_alarm.is_mobile
     */
     public java.lang.Integer getIsMobile() {
        return isMobile;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_email
     * Comment: 
     * @return the value of monitor_index_alarm.is_email
     */
     public java.lang.Integer getIsEmail() {
        return isEmail;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_ding
     * Comment: 
     * @return the value of monitor_index_alarm.is_ding
     */
     public java.lang.Integer getIsDing() {
        return isDing;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_weixin
     * Comment: 
     * @return the value of monitor_index_alarm.is_weixin
     */
     public java.lang.Integer getIsWeixin() {
        return isWeixin;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.is_alarm
     * Comment: 
     * @return the value of monitor_index_alarm.is_alarm
     */
     public java.lang.Integer getIsAlarm() {
        return isAlarm;
    }

    /**
     * This field corresponds to the database column monitor_index_alarm.not_eq_value
     * Comment: 
     * @return the value of monitor_index_alarm.not_eq_value
     */
     public java.lang.String getNotEqValue() {
        return notEqValue;
    }
}
