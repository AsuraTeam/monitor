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
 * @date 2016-07-28 11:30:55
 * @since 1.0
 */
public class CmdbResourceEntnameEntity extends BaseEntity {

    private java.lang.String lastModifyTime;

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.ent_id
     * Comment: 环境id
     * @param entId the value for cmdb_resource_entname.ent_id
     */

    private java.lang.Integer entId;


    /**
     * This field corresponds to the database column cmdb_resource_entname.ent_name
     * Comment: 环境名称
     * @param entName the value for cmdb_resource_entname.ent_name
     */

    private java.lang.String entName;


    /**
     * This field corresponds to the database column cmdb_resource_entname.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_entname.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_entname.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_entname.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_entname.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_entname.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_entname.ent_id
     * Comment: 环境id
     * @param entId the value for cmdb_resource_entname.ent_id
     */
    public void setEntId(java.lang.Integer entId){
       this.entId = entId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.ent_name
     * Comment: 环境名称
     * @param entName the value for cmdb_resource_entname.ent_name
     */
    public void setEntName(java.lang.String entName){
       this.entName = entName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_entname.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_entname.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_entname.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.ent_id
     * Comment: 环境id
     * @return the value of cmdb_resource_entname.ent_id
     */
     public java.lang.Integer getEntId() {
        return entId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.ent_name
     * Comment: 环境名称
     * @return the value of cmdb_resource_entname.ent_name
     */
     public java.lang.String getEntName() {
        return entName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_entname.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_entname.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_entname.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_entname.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
