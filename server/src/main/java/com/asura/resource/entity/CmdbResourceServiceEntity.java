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
 * @date 2016-07-28 11:40:35
 * @since 1.0
 */
public class CmdbResourceServiceEntity extends BaseEntity{
    private java.lang.String lastModifyTime;

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.service_id
     * Comment: 服务类型id
     * @param serviceId the value for cmdb_resource_service.service_id
     */

    private java.lang.Integer serviceId;


    /**
     * This field corresponds to the database column cmdb_resource_service.service_name
     * Comment: 服务类型名称
     * @param serviceName the value for cmdb_resource_service.service_name
     */

    private java.lang.String serviceName;


    /**
     * This field corresponds to the database column cmdb_resource_service.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_service.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_service.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_service.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_service.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_service.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_service.service_id
     * Comment: 服务类型id
     * @param serviceId the value for cmdb_resource_service.service_id
     */
    public void setServiceId(java.lang.Integer serviceId){
       this.serviceId = serviceId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.service_name
     * Comment: 服务类型名称
     * @param serviceName the value for cmdb_resource_service.service_name
     */
    public void setServiceName(java.lang.String serviceName){
       this.serviceName = serviceName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_service.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_service.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_service.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.service_id
     * Comment: 服务类型id
     * @return the value of cmdb_resource_service.service_id
     */
     public java.lang.Integer getServiceId() {
        return serviceId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.service_name
     * Comment: 服务类型名称
     * @return the value of cmdb_resource_service.service_name
     */
     public java.lang.String getServiceName() {
        return serviceName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_service.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_service.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_service.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_service.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
