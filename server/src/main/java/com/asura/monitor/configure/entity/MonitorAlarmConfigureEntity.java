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
 * @date 2017-07-15 08:49:22
 * @since 1.0
 */
public class MonitorAlarmConfigureEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_alarm_configure.configure_id
     * Comment: 
     * @param configureId the value for monitor_alarm_configure.configure_id
     */

    private java.lang.Integer configureId;


    /**
     * This field corresponds to the database column monitor_alarm_configure.description
     * Comment: 描述信息
     * @param description the value for monitor_alarm_configure.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_alarm_configure.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_alarm_configure.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_alarm_configure.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_alarm_configure.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_alarm_configure.ent_id
     * Comment: 对不同的环境接报警
     * @param entId the value for monitor_alarm_configure.ent_id
     */

    private java.lang.String entId;


    /**
     * This field corresponds to the database column monitor_alarm_configure.hosts
     * Comment: 对不同的主机接收任何报警
     * @param hosts the value for monitor_alarm_configure.hosts
     */

    private java.lang.String hosts;


    /**
     * This field corresponds to the database column monitor_alarm_configure.item_id
     * Comment: 对不同的项目进行报警
     * @param itemId the value for monitor_alarm_configure.item_id
     */

    private java.lang.String itemId;


    /**
     * This field corresponds to the database column monitor_alarm_configure.start_time
     * Comment: 报警开始接收时间
     * @param startTime the value for monitor_alarm_configure.start_time
     */

    private java.lang.String startTime;


    /**
     * This field corresponds to the database column monitor_alarm_configure.end_time
     * Comment: 报警完成时间
     * @param endTime the value for monitor_alarm_configure.end_time
     */

    private java.lang.String endTime;


    /**
     * This field corresponds to the database column monitor_alarm_configure.all_groups
     * Comment: 接收报警组
     * @param allGroups the value for monitor_alarm_configure.all_groups
     */

    private java.lang.String allGroups;


    /**
     * This field corresponds to the database column monitor_alarm_configure.gson_data
     * Comment: 
     * @param gsonData the value for monitor_alarm_configure.gson_data
     */

    private java.lang.String gsonData;


    /**
     * This field corresponds to the database column monitor_alarm_configure.configure_id
     * Comment: 
     * @param configureId the value for monitor_alarm_configure.configure_id
     */
    public void setConfigureId(java.lang.Integer configureId){
       this.configureId = configureId;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.description
     * Comment: 描述信息
     * @param description the value for monitor_alarm_configure.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_alarm_configure.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_alarm_configure.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.ent_id
     * Comment: 对不同的环境接报警
     * @param entId the value for monitor_alarm_configure.ent_id
     */
    public void setEntId(java.lang.String entId){
       this.entId = entId;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.hosts
     * Comment: 对不同的主机接收任何报警
     * @param hosts the value for monitor_alarm_configure.hosts
     */
    public void setHosts(java.lang.String hosts){
       this.hosts = hosts;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.item_id
     * Comment: 对不同的项目进行报警
     * @param itemId the value for monitor_alarm_configure.item_id
     */
    public void setItemId(java.lang.String itemId){
       this.itemId = itemId;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.start_time
     * Comment: 报警开始接收时间
     * @param startTime the value for monitor_alarm_configure.start_time
     */
    public void setStartTime(java.lang.String startTime){
       this.startTime = startTime;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.end_time
     * Comment: 报警完成时间
     * @param endTime the value for monitor_alarm_configure.end_time
     */
    public void setEndTime(java.lang.String endTime){
       this.endTime = endTime;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.all_groups
     * Comment: 接收报警组
     * @param allGroups the value for monitor_alarm_configure.all_groups
     */
    public void setAllGroups(java.lang.String allGroups){
       this.allGroups = allGroups;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.gson_data
     * Comment: 
     * @param gsonData the value for monitor_alarm_configure.gson_data
     */
    public void setGsonData(java.lang.String gsonData){
       this.gsonData = gsonData;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.configure_id
     * Comment: 
     * @return the value of monitor_alarm_configure.configure_id
     */
     public java.lang.Integer getConfigureId() {
        return configureId;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.description
     * Comment: 描述信息
     * @return the value of monitor_alarm_configure.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_alarm_configure.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_alarm_configure.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.ent_id
     * Comment: 对不同的环境接报警
     * @return the value of monitor_alarm_configure.ent_id
     */
     public java.lang.String getEntId() {
        return entId;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.hosts
     * Comment: 对不同的主机接收任何报警
     * @return the value of monitor_alarm_configure.hosts
     */
     public java.lang.String getHosts() {
        return hosts;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.item_id
     * Comment: 对不同的项目进行报警
     * @return the value of monitor_alarm_configure.item_id
     */
     public java.lang.String getItemId() {
        return itemId;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.start_time
     * Comment: 报警开始接收时间
     * @return the value of monitor_alarm_configure.start_time
     */
     public java.lang.String getStartTime() {
        return startTime;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.end_time
     * Comment: 报警完成时间
     * @return the value of monitor_alarm_configure.end_time
     */
     public java.lang.String getEndTime() {
        return endTime;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.all_groups
     * Comment: 接收报警组
     * @return the value of monitor_alarm_configure.all_groups
     */
     public java.lang.String getAllGroups() {
        return allGroups;
    }

    /**
     * This field corresponds to the database column monitor_alarm_configure.gson_data
     * Comment: 
     * @return the value of monitor_alarm_configure.gson_data
     */
     public java.lang.String getGsonData() {
        return gsonData;
    }
}
