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
 * @date 2016-08-20 09:07:21
 * @since 1.0
 */
public class MonitorContactGroupEntity extends BaseEntity{

    /**
     * 是否是管理员
     */
    private int isAdmin;

    public int getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(int isAdmin) {
        this.isAdmin = isAdmin;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_id
     * Comment: 主键
     * @param groupId the value for monitor_contact_group.group_id
     */

    private java.lang.Integer groupId;


    /**
     * This field corresponds to the database column monitor_contact_group.group_name
     * Comment: 组名字
     * @param groupName the value for monitor_contact_group.group_name
     */

    private java.lang.String groupName;


    /**
     * This field corresponds to the database column monitor_contact_group.description
     * Comment: 描述信息
     * @param description the value for monitor_contact_group.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_contact_group.member
     * Comment: 组成员
     * @param member the value for monitor_contact_group.member
     */

    private java.lang.String member;


    /**
     * This field corresponds to the database column monitor_contact_group.ismail
     * Comment: 是否发送邮件
     * @param ismail the value for monitor_contact_group.ismail
     */

    private java.lang.Integer ismail;


    /**
     * This field corresponds to the database column monitor_contact_group.ismobile
     * Comment: 是否发送邮件
     * @param ismobile the value for monitor_contact_group.ismobile
     */

    private java.lang.Integer ismobile;


    /**
     * This field corresponds to the database column monitor_contact_group.status
     * Comment: 状态
     * @param status the value for monitor_contact_group.status
     */

    private java.lang.Integer status;


    /**
     * This field corresponds to the database column monitor_contact_group.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_contact_group.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_contact_group.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_contact_group.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_contact_group.group_id
     * Comment: 主键
     * @param groupId the value for monitor_contact_group.group_id
     */
    public void setGroupId(java.lang.Integer groupId){
       this.groupId = groupId;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_name
     * Comment: 组名字
     * @param groupName the value for monitor_contact_group.group_name
     */
    public void setGroupName(java.lang.String groupName){
       this.groupName = groupName;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.description
     * Comment: 描述信息
     * @param description the value for monitor_contact_group.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.member
     * Comment: 组成员
     * @param member the value for monitor_contact_group.member
     */
    public void setMember(java.lang.String member){
       this.member = member;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismail
     * Comment: 是否发送邮件
     * @param ismail the value for monitor_contact_group.ismail
     */
    public void setIsmail(java.lang.Integer ismail){
       this.ismail = ismail;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismobile
     * Comment: 是否发送邮件
     * @param ismobile the value for monitor_contact_group.ismobile
     */
    public void setIsmobile(java.lang.Integer ismobile){
       this.ismobile = ismobile;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.status
     * Comment: 状态
     * @param status the value for monitor_contact_group.status
     */
    public void setStatus(java.lang.Integer status){
       this.status = status;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_contact_group.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_contact_group.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_id
     * Comment: 主键
     * @return the value of monitor_contact_group.group_id
     */
     public java.lang.Integer getGroupId() {
        return groupId;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_name
     * Comment: 组名字
     * @return the value of monitor_contact_group.group_name
     */
     public java.lang.String getGroupName() {
        return groupName;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.description
     * Comment: 描述信息
     * @return the value of monitor_contact_group.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.member
     * Comment: 组成员
     * @return the value of monitor_contact_group.member
     */
     public java.lang.String getMember() {
        return member;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismail
     * Comment: 是否发送邮件
     * @return the value of monitor_contact_group.ismail
     */
     public java.lang.Integer getIsmail() {
        return ismail;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismobile
     * Comment: 是否发送邮件
     * @return the value of monitor_contact_group.ismobile
     */
     public java.lang.Integer getIsmobile() {
        return ismobile;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.status
     * Comment: 状态
     * @return the value of monitor_contact_group.status
     */
     public java.lang.Integer getStatus() {
        return status;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_contact_group.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_contact_group.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
