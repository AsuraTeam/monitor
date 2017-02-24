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
 * @date 2016-07-28 11:29:56
 * @since 1.0
 */
public class CmdbResourceCabinetEntity extends BaseEntity {

    private String lastModifyTime;
    private String floorAddress;
    private String floorName;
    private String cabinetScope;

    public String getCabinetScope() {
        return cabinetScope;
    }

    public void setCabinetScope(String cabinetScope) {
        this.cabinetScope = cabinetScope;
    }

    // 最多存放设备
    private int number;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getFloorAddress() {
        return floorAddress;
    }

    public void setFloorAddress(String floorAddress) {
        this.floorAddress = floorAddress;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.cabinet_id
     * Comment: 机柜编号
     * @param cabinetId the value for cmdb_resource_cabinet.cabinet_id
     */

    private java.lang.Integer cabinetId;


    /**
     * This field corresponds to the database column cmdb_resource_cabinet.cabinet_name
     * Comment: 机柜名称
     * @param cabinetName the value for cmdb_resource_cabinet.cabinet_name
     */

    private java.lang.String cabinetName;


    /**
     * This field corresponds to the database column cmdb_resource_cabinet.floor_id
     * Comment: 
     * @param floorId the value for cmdb_resource_cabinet.floor_id
     */

    private java.lang.Integer floorId;


    /**
     * This field corresponds to the database column cmdb_resource_cabinet.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_cabinet.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_cabinet.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_cabinet.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_cabinet.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for cmdb_resource_cabinet.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_cabinet.cabinet_id
     * Comment: 机柜编号
     * @param cabinetId the value for cmdb_resource_cabinet.cabinet_id
     */
    public void setCabinetId(java.lang.Integer cabinetId){
       this.cabinetId = cabinetId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.cabinet_name
     * Comment: 机柜名称
     * @param cabinetName the value for cmdb_resource_cabinet.cabinet_name
     */
    public void setCabinetName(java.lang.String cabinetName){
       this.cabinetName = cabinetName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.floor_id
     * Comment: 
     * @param floorId the value for cmdb_resource_cabinet.floor_id
     */
    public void setFloorId(java.lang.Integer floorId){
       this.floorId = floorId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_cabinet.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_cabinet.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for cmdb_resource_cabinet.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.cabinet_id
     * Comment: 机柜编号
     * @return the value of cmdb_resource_cabinet.cabinet_id
     */
     public java.lang.Integer getCabinetId() {
        return cabinetId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.cabinet_name
     * Comment: 机柜名称
     * @return the value of cmdb_resource_cabinet.cabinet_name
     */
     public java.lang.String getCabinetName() {
        return cabinetName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.floor_id
     * Comment: 
     * @return the value of cmdb_resource_cabinet.floor_id
     */
     public java.lang.Integer getFloorId() {
        return floorId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_cabinet.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_cabinet.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_cabinet.last_modify_user
     * Comment: 最近修改用户
     * @return the value of cmdb_resource_cabinet.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
