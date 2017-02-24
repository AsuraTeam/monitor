package com.asura.agent.entity;
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
public class MonitorContactGroupEntity {
    /**
     * This field corresponds to the database column monitor_contact_group.group_id
     * Comment: 主键
     * @param groupId the value for monitor_contact_group.group_id
     */

    private Integer groupId;


    /**
     * This field corresponds to the database column monitor_contact_group.group_name
     * Comment: 组名字
     * @param groupName the value for monitor_contact_group.group_name
     */

    private String groupName;


    /**
     * This field corresponds to the database column monitor_contact_group.description
     * Comment: 描述信息
     * @param description the value for monitor_contact_group.description
     */

    private String description;


    /**
     * This field corresponds to the database column monitor_contact_group.member
     * Comment: 组成员
     * @param member the value for monitor_contact_group.member
     */

    private String member;


    /**
     * This field corresponds to the database column monitor_contact_group.ismail
     * Comment: 是否发送邮件
     * @param ismail the value for monitor_contact_group.ismail
     */

    private Integer ismail;


    /**
     * This field corresponds to the database column monitor_contact_group.ismobile
     * Comment: 是否发送邮件
     * @param ismobile the value for monitor_contact_group.ismobile
     */

    private Integer ismobile;


    /**
     * This field corresponds to the database column monitor_contact_group.status
     * Comment: 状态
     * @param status the value for monitor_contact_group.status
     */

    private Integer status;


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

    private String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_contact_group.group_id
     * Comment: 主键
     * @param groupId the value for monitor_contact_group.group_id
     */
    public void setGroupId(Integer groupId){
       this.groupId = groupId;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_name
     * Comment: 组名字
     * @param groupName the value for monitor_contact_group.group_name
     */
    public void setGroupName(String groupName){
       this.groupName = groupName;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.description
     * Comment: 描述信息
     * @param description the value for monitor_contact_group.description
     */
    public void setDescription(String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.member
     * Comment: 组成员
     * @param member the value for monitor_contact_group.member
     */
    public void setMember(String member){
       this.member = member;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismail
     * Comment: 是否发送邮件
     * @param ismail the value for monitor_contact_group.ismail
     */
    public void setIsmail(Integer ismail){
       this.ismail = ismail;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismobile
     * Comment: 是否发送邮件
     * @param ismobile the value for monitor_contact_group.ismobile
     */
    public void setIsmobile(Integer ismobile){
       this.ismobile = ismobile;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.status
     * Comment: 状态
     * @param status the value for monitor_contact_group.status
     */
    public void setStatus(Integer status){
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
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_id
     * Comment: 主键
     * @return the value of monitor_contact_group.group_id
     */
     public Integer getGroupId() {
        return groupId;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.group_name
     * Comment: 组名字
     * @return the value of monitor_contact_group.group_name
     */
     public String getGroupName() {
        return groupName;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.description
     * Comment: 描述信息
     * @return the value of monitor_contact_group.description
     */
     public String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.member
     * Comment: 组成员
     * @return the value of monitor_contact_group.member
     */
     public String getMember() {
        return member;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismail
     * Comment: 是否发送邮件
     * @return the value of monitor_contact_group.ismail
     */
     public Integer getIsmail() {
        return ismail;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.ismobile
     * Comment: 是否发送邮件
     * @return the value of monitor_contact_group.ismobile
     */
     public Integer getIsmobile() {
        return ismobile;
    }

    /**
     * This field corresponds to the database column monitor_contact_group.status
     * Comment: 状态
     * @return the value of monitor_contact_group.status
     */
     public Integer getStatus() {
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
     public String getLastModifyUser() {
        return lastModifyUser;
    }
}
