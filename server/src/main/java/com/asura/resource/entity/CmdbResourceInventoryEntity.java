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
 * @date 2017-01-13 08:06:42
 * @since 1.0
 */
public class CmdbResourceInventoryEntity extends BaseEntity{

    private int inventoryUsed;

    public int getInventoryUsed() {
        return inventoryUsed;
    }

    public void setInventoryUsed(int inventoryUsed) {
        this.inventoryUsed = inventoryUsed;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_id
     * Comment: id,主键
     * @param inventoryId the value for cmdb_resource_inventory.inventory_id
     */

    private java.lang.Integer inventoryId;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.title
     * Comment: 中心标志
     * @param title the value for cmdb_resource_inventory.title
     */

    private java.lang.String title;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.groups_id
     * Comment: 中心拥有的组id
     * @param groupsId the value for cmdb_resource_inventory.groups_id
     */

    private java.lang.String groupsId;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.last_modify_time
     * Comment: 最近想修改时间
     * @param lastModifyTime the value for cmdb_resource_inventory.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_number
     * Comment: 库存数量
     * @param inventoryNumber the value for cmdb_resource_inventory.inventory_number
     */

    private java.lang.Integer inventoryNumber;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_totle
     * Comment: 库存总量
     * @param inventoryTotle the value for cmdb_resource_inventory.inventory_totle
     */

    private java.lang.Integer inventoryTotle;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for cmdb_resource_inventory.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.update_time
     * Comment: 数据更新时间
     * @param updateTime the value for cmdb_resource_inventory.update_time
     */

    private java.lang.String updateTime;


    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_id
     * Comment: id,主键
     * @param inventoryId the value for cmdb_resource_inventory.inventory_id
     */
    public void setInventoryId(java.lang.Integer inventoryId){
       this.inventoryId = inventoryId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.title
     * Comment: 中心标志
     * @param title the value for cmdb_resource_inventory.title
     */
    public void setTitle(java.lang.String title){
       this.title = title;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.groups_id
     * Comment: 中心拥有的组id
     * @param groupsId the value for cmdb_resource_inventory.groups_id
     */
    public void setGroupsId(java.lang.String groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.last_modify_time
     * Comment: 最近想修改时间
     * @param lastModifyTime the value for cmdb_resource_inventory.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_number
     * Comment: 库存数量
     * @param inventoryNumber the value for cmdb_resource_inventory.inventory_number
     */
    public void setInventoryNumber(java.lang.Integer inventoryNumber){
       this.inventoryNumber = inventoryNumber;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_totle
     * Comment: 库存总量
     * @param inventoryTotle the value for cmdb_resource_inventory.inventory_totle
     */
    public void setInventoryTotle(java.lang.Integer inventoryTotle){
       this.inventoryTotle = inventoryTotle;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for cmdb_resource_inventory.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.update_time
     * Comment: 数据更新时间
     * @param updateTime the value for cmdb_resource_inventory.update_time
     */
    public void setUpdateTime(java.lang.String updateTime){
       this.updateTime = updateTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_id
     * Comment: id,主键
     * @return the value of cmdb_resource_inventory.inventory_id
     */
     public java.lang.Integer getInventoryId() {
        return inventoryId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.title
     * Comment: 中心标志
     * @return the value of cmdb_resource_inventory.title
     */
     public java.lang.String getTitle() {
        return title;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.groups_id
     * Comment: 中心拥有的组id
     * @return the value of cmdb_resource_inventory.groups_id
     */
     public java.lang.String getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.last_modify_time
     * Comment: 最近想修改时间
     * @return the value of cmdb_resource_inventory.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_number
     * Comment: 库存数量
     * @return the value of cmdb_resource_inventory.inventory_number
     */
     public java.lang.Integer getInventoryNumber() {
        return inventoryNumber;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.inventory_totle
     * Comment: 库存总量
     * @return the value of cmdb_resource_inventory.inventory_totle
     */
     public java.lang.Integer getInventoryTotle() {
        return inventoryTotle;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.last_modify_user
     * Comment: 最近修改用户
     * @return the value of cmdb_resource_inventory.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_inventory.update_time
     * Comment: 数据更新时间
     * @return the value of cmdb_resource_inventory.update_time
     */
     public java.lang.String getUpdateTime() {
        return updateTime;
    }
}
