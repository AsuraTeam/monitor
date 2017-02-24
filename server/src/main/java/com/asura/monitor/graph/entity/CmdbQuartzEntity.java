package com.asura.monitor.graph.entity;
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
 * @date 2016-08-10 11:50:49
 * @since 1.0
 */
public class CmdbQuartzEntity extends BaseEntity{
    /**
     * This field corresponds to the database column cmdb_quartz.quartz_id
     * Comment: 主键
     * @param quartzId the value for cmdb_quartz.quartz_id
     */

    private java.lang.Integer quartzId;


    /**
     * This field corresponds to the database column cmdb_quartz.quartz_name
     * Comment: 任务名称
     * @param quartzName the value for cmdb_quartz.quartz_name
     */

    private java.lang.String quartzName;


    /**
     * This field corresponds to the database column cmdb_quartz.quartz_url
     * Comment: 任务接口地址
     * @param quartzUrl the value for cmdb_quartz.quartz_url
     */

    private java.lang.String quartzUrl;


    /**
     * This field corresponds to the database column cmdb_quartz.quartz_time
     * Comment: 任务执行时间, 秒
     * @param quartzTime the value for cmdb_quartz.quartz_time
     */

    private java.lang.Integer quartzTime;


    /**
     * This field corresponds to the database column cmdb_quartz.description
     * Comment: 描述信息
     * @param description the value for cmdb_quartz.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column cmdb_quartz.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for cmdb_quartz.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column cmdb_quartz.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for cmdb_quartz.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_quartz.quartz_id
     * Comment: 主键
     * @param quartzId the value for cmdb_quartz.quartz_id
     */
    public void setQuartzId(java.lang.Integer quartzId){
       this.quartzId = quartzId;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_name
     * Comment: 任务名称
     * @param quartzName the value for cmdb_quartz.quartz_name
     */
    public void setQuartzName(java.lang.String quartzName){
       this.quartzName = quartzName;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_url
     * Comment: 任务接口地址
     * @param quartzUrl the value for cmdb_quartz.quartz_url
     */
    public void setQuartzUrl(java.lang.String quartzUrl){
       this.quartzUrl = quartzUrl;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_time
     * Comment: 任务执行时间, 秒
     * @param quartzTime the value for cmdb_quartz.quartz_time
     */
    public void setQuartzTime(java.lang.Integer quartzTime){
       this.quartzTime = quartzTime;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.description
     * Comment: 描述信息
     * @param description the value for cmdb_quartz.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for cmdb_quartz.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for cmdb_quartz.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_id
     * Comment: 主键
     * @return the value of cmdb_quartz.quartz_id
     */
     public java.lang.Integer getQuartzId() {
        return quartzId;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_name
     * Comment: 任务名称
     * @return the value of cmdb_quartz.quartz_name
     */
     public java.lang.String getQuartzName() {
        return quartzName;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_url
     * Comment: 任务接口地址
     * @return the value of cmdb_quartz.quartz_url
     */
     public java.lang.String getQuartzUrl() {
        return quartzUrl;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.quartz_time
     * Comment: 任务执行时间, 秒
     * @return the value of cmdb_quartz.quartz_time
     */
     public java.lang.Integer getQuartzTime() {
        return quartzTime;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.description
     * Comment: 描述信息
     * @return the value of cmdb_quartz.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.last_modify_time
     * Comment: 最近修改时间
     * @return the value of cmdb_quartz.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_quartz.last_modify_user
     * Comment: 最近修改用户
     * @return the value of cmdb_quartz.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
