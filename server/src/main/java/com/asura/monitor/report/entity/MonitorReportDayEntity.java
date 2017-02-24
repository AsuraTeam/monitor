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
 * @date 2016-11-03 10:33:03
 * @since 1.0
 */
public class MonitorReportDayEntity extends BaseEntity{

    private String indexName;
    private String ipAddress;
    private String groupsName;
    private String cnt;
    private String month;
    // 处理进度
    private String changeStatus;
    private String startTime;


    // 画饼图使用
    private String name;
    private int y;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getChangeStatus() {
        return changeStatus;
    }

    public void setChangeStatus(String changeStatus) {
        this.changeStatus = changeStatus;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
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

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    /**
     * This field corresponds to the database column monitor_report_day.report_id
     * Comment: 主键
     * @param reportId the value for monitor_report_day.report_id
     */

    private java.lang.Integer reportId;


    /**
     * This field corresponds to the database column monitor_report_day.server_id
     * Comment: 服务器ID，参考CMDB数据
     * @param serverId the value for monitor_report_day.server_id
     */

    private Long serverId;


    /**
     * This field corresponds to the database column monitor_report_day.operator
     * Comment: 故障处理人
     * @param operator the value for monitor_report_day.operator
     */

    private java.lang.String operator;


    /**
     * This field corresponds to the database column monitor_report_day.alarm_time
     * Comment: 报警时间
     * @param alarmTime the value for monitor_report_day.alarm_time
     */

    private java.lang.String alarmTime;


    /**
     * This field corresponds to the database column monitor_report_day.scripts_id
     * Comment: 脚本ID，参考脚本ID
     * @param scriptsId the value for monitor_report_day.scripts_id
     */

    private java.lang.Integer scriptsId;


    /**
     * This field corresponds to the database column monitor_report_day.severity_id
     * Comment: 报警级别,参考severity_id
     * @param severityId the value for monitor_report_day.severity_id
     */

    private java.lang.Integer severityId;


    /**
     * This field corresponds to the database column monitor_report_day.operator_time
     * Comment: 故障处理时间
     * @param operatorTime the value for monitor_report_day.operator_time
     */

    private java.lang.String operatorTime;


    /**
     * This field corresponds to the database column monitor_report_day.groups_id
     * Comment: 组ID
     * @param groupsId the value for monitor_report_day.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column monitor_report_day.report_id
     * Comment: 主键
     * @param reportId the value for monitor_report_day.report_id
     */
    public void setReportId(java.lang.Integer reportId){
       this.reportId = reportId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.server_id
     * Comment: 服务器ID，参考CMDB数据
     * @param serverId the value for monitor_report_day.server_id
     */
    public void setServerId(Long serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.operator
     * Comment: 故障处理人
     * @param operator the value for monitor_report_day.operator
     */
    public void setOperator(java.lang.String operator){
       this.operator = operator;
    }

    /**
     * This field corresponds to the database column monitor_report_day.alarm_time
     * Comment: 报警时间
     * @param alarmTime the value for monitor_report_day.alarm_time
     */
    public void setAlarmTime(java.lang.String alarmTime){
       this.alarmTime = alarmTime;
    }

    /**
     * This field corresponds to the database column monitor_report_day.scripts_id
     * Comment: 脚本ID，参考脚本ID
     * @param scriptsId the value for monitor_report_day.scripts_id
     */
    public void setScriptsId(java.lang.Integer scriptsId){
       this.scriptsId = scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.severity_id
     * Comment: 报警级别,参考severity_id
     * @param severityId the value for monitor_report_day.severity_id
     */
    public void setSeverityId(java.lang.Integer severityId){
       this.severityId = severityId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.operator_time
     * Comment: 故障处理时间
     * @param operatorTime the value for monitor_report_day.operator_time
     */
    public void setOperatorTime(java.lang.String operatorTime){
       this.operatorTime = operatorTime;
    }

    /**
     * This field corresponds to the database column monitor_report_day.groups_id
     * Comment: 组ID
     * @param groupsId the value for monitor_report_day.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.report_id
     * Comment: 主键
     * @return the value of monitor_report_day.report_id
     */
     public java.lang.Integer getReportId() {
        return reportId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.server_id
     * Comment: 服务器ID，参考CMDB数据
     * @return the value of monitor_report_day.server_id
     */
     public Long getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.operator
     * Comment: 故障处理人
     * @return the value of monitor_report_day.operator
     */
     public java.lang.String getOperator() {
        return operator;
    }

    /**
     * This field corresponds to the database column monitor_report_day.alarm_time
     * Comment: 报警时间
     * @return the value of monitor_report_day.alarm_time
     */
     public java.lang.String getAlarmTime() {
        return alarmTime;
    }

    /**
     * This field corresponds to the database column monitor_report_day.scripts_id
     * Comment: 脚本ID，参考脚本ID
     * @return the value of monitor_report_day.scripts_id
     */
     public java.lang.Integer getScriptsId() {
        return scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.severity_id
     * Comment: 报警级别,参考severity_id
     * @return the value of monitor_report_day.severity_id
     */
     public java.lang.Integer getSeverityId() {
        return severityId;
    }

    /**
     * This field corresponds to the database column monitor_report_day.operator_time
     * Comment: 故障处理时间
     * @return the value of monitor_report_day.operator_time
     */
     public java.lang.String getOperatorTime() {
        return operatorTime;
    }

    /**
     * This field corresponds to the database column monitor_report_day.groups_id
     * Comment: 组ID
     * @return the value of monitor_report_day.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }
}
