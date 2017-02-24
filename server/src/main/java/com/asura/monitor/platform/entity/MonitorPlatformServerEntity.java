package com.asura.monitor.platform.entity;
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
 * @date 2016-11-07 11:35:05
 * @since 1.0
 */
public class MonitorPlatformServerEntity extends BaseEntity{

    private String data1;
    private String data2;
    private String data3;
    private String data4;
    private String data5;

    private String cpu;
    private String loadavg;
    private String iowait;
    // 平台状态
    private String status;
    private String groupsName;

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getLoadavg() {
        return loadavg;
    }

    public void setLoadavg(String loadavg) {
        this.loadavg = loadavg;
    }

    public String getIowait() {
        return iowait;
    }

    public void setIowait(String iowait) {
        this.iowait = iowait;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.platform_id
     * Comment: 主键
     * @param platformId the value for monitor_platform_server.platform_id
     */

    private java.lang.Integer platformId;


    /**
     * This field corresponds to the database column monitor_platform_server.ip
     * Comment: ip地址
     * @param ip the value for monitor_platform_server.ip
     */

    private java.lang.String ip;


    /**
     * This field corresponds to the database column monitor_platform_server.hostname
     * Comment: 主机名
     * @param hostname the value for monitor_platform_server.hostname
     */

    private java.lang.String hostname;


    /**
     * This field corresponds to the database column monitor_platform_server.show_key
     * Comment: 动态显示的数据key
     * @param showKey the value for monitor_platform_server.show_key
     */

    private java.lang.String showKey;


    /**
     * This field corresponds to the database column monitor_platform_server.last_modify_time
     * Comment: 最近更新时间
     * @param lastModifyTime the value for monitor_platform_server.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_platform_server.platform_id
     * Comment: 主键
     * @param platformId the value for monitor_platform_server.platform_id
     */
    public void setPlatformId(java.lang.Integer platformId){
       this.platformId = platformId;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.ip
     * Comment: ip地址
     * @param ip the value for monitor_platform_server.ip
     */
    public void setIp(java.lang.String ip){
       this.ip = ip;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.hostname
     * Comment: 主机名
     * @param hostname the value for monitor_platform_server.hostname
     */
    public void setHostname(java.lang.String hostname){
       this.hostname = hostname;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.show_key
     * Comment: 动态显示的数据key
     * @param showKey the value for monitor_platform_server.show_key
     */
    public void setShowKey(java.lang.String showKey){
       this.showKey = showKey;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.last_modify_time
     * Comment: 最近更新时间
     * @param lastModifyTime the value for monitor_platform_server.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.platform_id
     * Comment: 主键
     * @return the value of monitor_platform_server.platform_id
     */
     public java.lang.Integer getPlatformId() {
        return platformId;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.ip
     * Comment: ip地址
     * @return the value of monitor_platform_server.ip
     */
     public java.lang.String getIp() {
        return ip;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.hostname
     * Comment: 主机名
     * @return the value of monitor_platform_server.hostname
     */
     public java.lang.String getHostname() {
        return hostname;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.show_key
     * Comment: 动态显示的数据key
     * @return the value of monitor_platform_server.show_key
     */
     public java.lang.String getShowKey() {
        return showKey;
    }

    /**
     * This field corresponds to the database column monitor_platform_server.last_modify_time
     * Comment: 最近更新时间
     * @return the value of monitor_platform_server.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }
}
