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

    // 获取测试环境购买量
    private String testBuyNumber;
    // 获取测试环境借用量
    private String testFromNumber;
    // 获取测试环境物理库存量
    private String testPhyInventoryNumber;
    // 获取测试环境已使用
    private String testVmUsedNumber;
    // 获取测试环境未使用库存
    private String testVmInventoryNumber;
    // 测试环境未使用库存
    private String testUnused;
    // 测试环境vm物理机使用
    private String testVmPhyNumber;
    // 测试环境应用物理机数量
    private int testPhyUsedNumber;

    // 获取测试环境购买量
    private String onlineBuyNumber;
    // 获取测试环境借用量
    private String onlineFromNumber;
    // 获取测试环境物理库存量
    private String onlinePhyInventoryNumber;
    // 获取测试环境未使用库存
    private String onlineVmInventoryNumber;
    // 生产环境未使用机器
    private String onlineUnused;
    // 测试环境vm物理机使用
    private String onlineVmPhyNumber;
    // 生产环境应用物理机数量
    private int onlinePhyUsedNumber;
    // 设置虚拟机库存数据量
    private int vmInventoryNumber;

    // 获取生产环境已使用
    private String onlineVmUsedNumber;

    // 获取借用库存
    private int fromInventory;

    // 获取每个虚拟机平均价格
    private int vmPrice;
    // 按单元计算虚拟机实例
    private int vmUnitsUsed;
    // 购买数量
    private int buyNumber;
    private int inventoryUsed;
    // 物理机库存，除虚拟化设备外的
    private int phyInventoryNumber;
    // 虚拟化主机使用量
    private int phyVmNumber;
    // 物理机已使用
    private int phyUsed;
    // 获取未使用库存
    private int unused;

    public int getVmInventoryNumber() {
        return vmInventoryNumber;
    }

    public void setVmInventoryNumber(int vmInventoryNumber) {
        this.vmInventoryNumber = vmInventoryNumber;
    }

    public int getTestPhyUsedNumber() {
        return testPhyUsedNumber;
    }

    public void setTestPhyUsedNumber(int testPhyUsedNumber) {
        this.testPhyUsedNumber = testPhyUsedNumber;
    }

    public int getOnlinePhyUsedNumber() {
        return onlinePhyUsedNumber;
    }

    public void setOnlinePhyUsedNumber(int onlinePhyUsedNumber) {
        this.onlinePhyUsedNumber = onlinePhyUsedNumber;
    }

    public String getTestVmPhyNumber() {
        return testVmPhyNumber;
    }

    public void setTestVmPhyNumber(String testVmPhyNumber) {
        this.testVmPhyNumber = testVmPhyNumber;
    }

    public String getOnlineVmPhyNumber() {
        return onlineVmPhyNumber;
    }

    public void setOnlineVmPhyNumber(String onlineVmPhyNumber) {
        this.onlineVmPhyNumber = onlineVmPhyNumber;
    }

    public String getTestVmUsedNumber() {
        return testVmUsedNumber;
    }

    public String getTestUnused() {
        return testUnused;
    }

    public void setTestUnused(String testUnused) {
        this.testUnused = testUnused;
    }

    public String getOnlineUnused() {
        return onlineUnused;
    }

    public void setOnlineUnused(String onlineUnused) {
        this.onlineUnused = onlineUnused;
    }

    public void setTestVmUsedNumber(String testVmUsedNumber) {
        this.testVmUsedNumber = testVmUsedNumber;
    }

    public String getOnlineVmUsedNumber() {
        return onlineVmUsedNumber;
    }

    public void setOnlineVmUsedNumber(String onlineVmUsedNumber) {
        this.onlineVmUsedNumber = onlineVmUsedNumber;
    }

    public String getTestBuyNumber() {
        return testBuyNumber;
    }

    public void setTestBuyNumber(String testBuyNumber) {
        this.testBuyNumber = testBuyNumber;
    }

    public String getTestFromNumber() {
        return testFromNumber;
    }

    public void setTestFromNumber(String testFromNumber) {
        this.testFromNumber = testFromNumber;
    }

    public String getTestPhyInventoryNumber() {
        return testPhyInventoryNumber;
    }

    public void setTestPhyInventoryNumber(String testPhyInventoryNumber) {
        this.testPhyInventoryNumber = testPhyInventoryNumber;
    }

    public String getTestVmInventoryNumber() {
        return testVmInventoryNumber;
    }

    public void setTestVmInventoryNumber(String testVmInventoryNumber) {
        this.testVmInventoryNumber = testVmInventoryNumber;
    }

    public String getOnlineBuyNumber() {
        return onlineBuyNumber;
    }

    public void setOnlineBuyNumber(String olineBuyNumber) {
        this.onlineBuyNumber = olineBuyNumber;
    }

    public String getOnlineFromNumber() {
        return onlineFromNumber;
    }

    public void setOnlineFromNumber(String onlineFromNumber) {
        this.onlineFromNumber = onlineFromNumber;
    }

    public String getOnlinePhyInventoryNumber() {
        return onlinePhyInventoryNumber;
    }

    public void setOnlinePhyInventoryNumber(String onlinePhyInventoryNumber) {
        this.onlinePhyInventoryNumber = onlinePhyInventoryNumber;
    }

    public String getOnlineVmInventoryNumber() {
        return onlineVmInventoryNumber;
    }

    public void setOnlineVmInventoryNumber(String onlineVmInventoryNumber) {
        this.onlineVmInventoryNumber = onlineVmInventoryNumber;
    }

    public int getUnused() {
        return unused;
    }

    public void setUnused(int unused) {
        this.unused = unused;
    }

    public int getFromInventory() {
        return fromInventory;
    }

    public void setFromInventory(int fromInventory) {
        this.fromInventory = fromInventory;
    }

    public int getVmPrice() {
        return vmPrice;
    }

    public void setVmPrice(int vmPrice) {
        this.vmPrice = vmPrice;
    }

    public int getVmUnitsUsed() {
        return vmUnitsUsed;
    }

    public void setVmUnitsUsed(int vmUnitsUsed) {
        this.vmUnitsUsed = vmUnitsUsed;
    }

    public int getPhyVmNumber() {
        return phyVmNumber;
    }

    public void setPhyVmNumber(int phyVmNumber) {
        this.phyVmNumber = phyVmNumber;
    }

    public int getPhyUsed() {
        return phyUsed;
    }

    public void setPhyUsed(int phyUsed) {
        this.phyUsed = phyUsed;
    }

    public int getPhyInventoryNumber() {
        return phyInventoryNumber;
    }

    public void setPhyInventoryNumber(int phyInventoryNumber) {
        this.phyInventoryNumber = phyInventoryNumber;
    }

    public int getBuyNumber() {
        return buyNumber;
    }

    public void setBuyNumber(int buyNumber) {
        this.buyNumber = buyNumber;
    }

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
