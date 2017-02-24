package com.asura.resource.entity;

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
 * @date 2016-07-28 11:31:36
 * @since 1.0
 */
public class CmdbResourceGroupsEntity extends BaseEntity {

    private java.lang.String lastModifyTime;

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.groups_id
     * Comment: 业务组ID
     * @param groupsId the value for cmdb_resource_groups.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column cmdb_resource_groups.groups_name
     * Comment: 业务组名称
     * @param groupsName the value for cmdb_resource_groups.groups_name
     */

    private java.lang.String groupsName;


    /**
     * This field corresponds to the database column cmdb_resource_groups.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_groups.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_groups.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_groups.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_groups.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_groups.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_groups.groups_id
     * Comment: 业务组ID
     * @param groupsId the value for cmdb_resource_groups.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.groups_name
     * Comment: 业务组名称
     * @param groupsName the value for cmdb_resource_groups.groups_name
     */
    public void setGroupsName(java.lang.String groupsName){
       this.groupsName = groupsName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_groups.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_groups.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_groups.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.groups_id
     * Comment: 业务组ID
     * @return the value of cmdb_resource_groups.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.groups_name
     * Comment: 业务组名称
     * @return the value of cmdb_resource_groups.groups_name
     */
     public java.lang.String getGroupsName() {
        return groupsName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_groups.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_groups.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_groups.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_groups.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
