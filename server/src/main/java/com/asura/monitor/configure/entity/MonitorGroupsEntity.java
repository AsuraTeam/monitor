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
 * @date 2016-08-20 10:18:32
 * @since 1.0
 */
public class MonitorGroupsEntity extends BaseEntity{

    // 业务组名称
    private String gname;

    // 组附加名称
    private String otherName;

    //  ip地址
    private String ipList;

    public String getIpList() {
        return ipList;
    }

    public void setIpList(String ipList) {
        this.ipList = ipList;
    }

    public String getOtherName() {
        return otherName;
    }

    public void setOtherName(String otherName) {
        this.otherName = otherName;
    }

    public String getGname() {
        return gname;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    /**
     * This field corresponds to the database column monitor_groups.groups_id
     * Comment: 主键
     * @param groupsId the value for monitor_groups.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column monitor_groups.groups_name
     * Comment: 组名称
     * @param groupsName the value for monitor_groups.groups_name
     */

    private java.lang.String groupsName;


    /**
     * This field corresponds to the database column monitor_groups.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_groups.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_groups.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_groups.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_groups.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_groups.is_valid
     */

    private java.lang.Integer isValid;


    /**
     * This field corresponds to the database column monitor_groups.hosts
     * Comment: 拥有的主机
     * @param hosts the value for monitor_groups.hosts
     */

    private java.lang.String hosts;


    /**
     * This field corresponds to the database column monitor_groups.groups_id
     * Comment: 主键
     * @param groupsId the value for monitor_groups.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_groups.groups_name
     * Comment: 组名称
     * @param groupsName the value for monitor_groups.groups_name
     */
    public void setGroupsName(java.lang.String groupsName){
       this.groupsName = groupsName;
    }

    /**
     * This field corresponds to the database column monitor_groups.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_groups.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_groups.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_groups.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_groups.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_groups.is_valid
     */
    public void setIsValid(java.lang.Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_groups.hosts
     * Comment: 拥有的主机
     * @param hosts the value for monitor_groups.hosts
     */
    public void setHosts(java.lang.String hosts){
       this.hosts = hosts;
    }

    /**
     * This field corresponds to the database column monitor_groups.groups_id
     * Comment: 主键
     * @return the value of monitor_groups.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column monitor_groups.groups_name
     * Comment: 组名称
     * @return the value of monitor_groups.groups_name
     */
     public java.lang.String getGroupsName() {
        return groupsName;
    }

    /**
     * This field corresponds to the database column monitor_groups.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_groups.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_groups.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_groups.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_groups.is_valid
     * Comment: 是否有效
     * @return the value of monitor_groups.is_valid
     */
     public java.lang.Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_groups.hosts
     * Comment: 拥有的主机
     * @return the value of monitor_groups.hosts
     */
     public java.lang.String getHosts() {
        return hosts;
    }
}
