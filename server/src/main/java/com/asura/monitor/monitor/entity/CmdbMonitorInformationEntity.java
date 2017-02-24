package com.asura.monitor.monitor.entity;
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
 * @date 2016-07-23 08:34:41
 * @since 1.0
 */
public class CmdbMonitorInformationEntity extends BaseEntity{

    private String monitorName;

    public String getMonitorName() {
        return monitorName;
    }

    public void setMonitorName(String monitorName) {
        this.monitorName = monitorName;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.id
     * Comment: 主键
     * @param id the value for cmdb_monitor_information.id
     */

    private java.lang.Integer id;


    /**
     * This field corresponds to the database column cmdb_monitor_information.ip
     * Comment: 被监控的IP地址
     * @param ip the value for cmdb_monitor_information.ip
     */

    private java.lang.String ip;



    /**
     *
     * Comment: 非正常状态的值
     * @param noStatus
     */
    private String noStatus;


    /**
     * This field corresponds to the database column cmdb_monitor_information.name
     * Comment: 
     * @param name the value for cmdb_monitor_information.name
     */

    private java.lang.String name;


    /**
     * This field corresponds to the database column cmdb_monitor_information.check_command
     * Comment: 监控脚本名称
     * @param checkCommand the value for cmdb_monitor_information.check_command
     */

    private java.lang.String checkCommand;


    /**
     * This field corresponds to the database column cmdb_monitor_information.last_time
     * Comment: 最近检查时间
     * @param lastTime the value for cmdb_monitor_information.last_time
     */

    private java.lang.String lastTime;


    /**
     * This field corresponds to the database column cmdb_monitor_information.next_time
     * Comment: 下次检查时间
     * @param nextTime the value for cmdb_monitor_information.next_time
     */

    private java.lang.String nextTime;


    /**
     * This field corresponds to the database column cmdb_monitor_information.status
     * Comment: 
     * @param status the value for cmdb_monitor_information.status
     */

    private java.lang.String status;


    /**
     * This field corresponds to the database column cmdb_monitor_information.info
     * Comment: 监控输出信息
     * @param info the value for cmdb_monitor_information.info
     */

    private java.lang.String info;


    /**
     * This field corresponds to the database column cmdb_monitor_information.notice_number
     * Comment: 监控报警次数
     * @param noticeNumber the value for cmdb_monitor_information.notice_number
     */

    private java.lang.Integer noticeNumber;


    /**
     * This field corresponds to the database column cmdb_monitor_information.groups
     * Comment: 业务组
     * @param groups the value for cmdb_monitor_information.groups
     */

    private java.lang.String groups;


    /**
     * This field corresponds to the database column cmdb_monitor_information.site
     * Comment: 监控站点
     * @param site the value for cmdb_monitor_information.site
     */

    private java.lang.String site;


    /**
     * This field corresponds to the database column cmdb_monitor_information.id
     * Comment: 主键
     * @param id the value for cmdb_monitor_information.id
     */
    public void setId(java.lang.Integer id){
       this.id = id;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.ip
     * Comment: 被监控的IP地址
     * @param ip the value for cmdb_monitor_information.ip
     */
    public void setIp(java.lang.String ip){
       this.ip = ip;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.name
     * Comment: 
     * @param name the value for cmdb_monitor_information.name
     */
    public void setName(java.lang.String name){
       this.name = name;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.check_command
     * Comment: 监控脚本名称
     * @param checkCommand the value for cmdb_monitor_information.check_command
     */
    public void setCheckCommand(java.lang.String checkCommand){
       this.checkCommand = checkCommand;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.last_time
     * Comment: 最近检查时间
     * @param lastTime the value for cmdb_monitor_information.last_time
     */
    public void setLastTime(java.lang.String lastTime){
       this.lastTime = lastTime;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.next_time
     * Comment: 下次检查时间
     * @param nextTime the value for cmdb_monitor_information.next_time
     */
    public void setNextTime(java.lang.String nextTime){
       this.nextTime = nextTime;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.status
     * Comment: 
     * @param status the value for cmdb_monitor_information.status
     */
    public void setStatus(java.lang.String status){
       this.status = status;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.info
     * Comment: 监控输出信息
     * @param info the value for cmdb_monitor_information.info
     */
    public void setInfo(java.lang.String info){
       this.info = info;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.notice_number
     * Comment: 监控报警次数
     * @param noticeNumber the value for cmdb_monitor_information.notice_number
     */
    public void setNoticeNumber(java.lang.Integer noticeNumber){
       this.noticeNumber = noticeNumber;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.groups
     * Comment: 业务组
     * @param groups the value for cmdb_monitor_information.groups
     */
    public void setGroups(java.lang.String groups){
       this.groups = groups;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.site
     * Comment: 监控站点
     * @param site the value for cmdb_monitor_information.site
     */
    public void setSite(java.lang.String site){
       this.site = site;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.id
     * Comment: 主键
     * @return the value of cmdb_monitor_information.id
     */
     public java.lang.Integer getId() {
        return id;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.ip
     * Comment: 被监控的IP地址
     * @return the value of cmdb_monitor_information.ip
     */
     public java.lang.String getIp() {
        return ip;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.name
     * Comment: 
     * @return the value of cmdb_monitor_information.name
     */
     public java.lang.String getName() {
        return name;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.check_command
     * Comment: 监控脚本名称
     * @return the value of cmdb_monitor_information.check_command
     */
     public java.lang.String getCheckCommand() {
        return checkCommand;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.last_time
     * Comment: 最近检查时间
     * @return the value of cmdb_monitor_information.last_time
     */
     public java.lang.String getLastTime() {
        return lastTime;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.next_time
     * Comment: 下次检查时间
     * @return the value of cmdb_monitor_information.next_time
     */
     public java.lang.String getNextTime() {
        return nextTime;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.status
     * Comment: 
     * @return the value of cmdb_monitor_information.status
     */
     public java.lang.String getStatus() {
        return status;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.info
     * Comment: 监控输出信息
     * @return the value of cmdb_monitor_information.info
     */
     public java.lang.String getInfo() {
        return info;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.notice_number
     * Comment: 监控报警次数
     * @return the value of cmdb_monitor_information.notice_number
     */
     public java.lang.Integer getNoticeNumber() {
        return noticeNumber;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.groups
     * Comment: 业务组
     * @return the value of cmdb_monitor_information.groups
     */
     public java.lang.String getGroups() {
        return groups;
    }

    /**
     * This field corresponds to the database column cmdb_monitor_information.site
     * Comment: 监控站点
     * @return the value of cmdb_monitor_information.site
     */
     public java.lang.String getSite() {
        return site;
    }


    public String getNoStatus() {
        return noStatus;
    }

    public void setNoStatus(String noStatus) {
        this.noStatus = noStatus;
    }
}
