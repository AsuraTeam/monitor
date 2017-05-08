package com.asura.resource.configure.server.entity;
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
 * @date 2017-05-08 12:34:43
 * @since 1.0
 */
public class CmdbResourceServerHistoryEntity extends BaseEntity{
    /**
     * This field corresponds to the database column cmdb_resource_server_history.server_id
     * Comment: 服务器ID
     * @param serverId the value for cmdb_resource_server_history.server_id
     */

    private java.lang.Integer serverId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.groups_id
     * Comment: 业务线组
     * @param groupsId the value for cmdb_resource_server_history.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.type_id
     * Comment: 服务器类型
     * @param typeId the value for cmdb_resource_server_history.type_id
     */

    private java.lang.Integer typeId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.os_id
     * Comment: 参考操作 系统类型
     * @param osId the value for cmdb_resource_server_history.os_id
     */

    private java.lang.Integer osId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.cabinet_id
     * Comment: 机柜ID
     * @param cabinetId the value for cmdb_resource_server_history.cabinet_id
     */

    private java.lang.Integer cabinetId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.user_id
     * Comment: 参考用户id,管理员
     * @param userId the value for cmdb_resource_server_history.user_id
     */

    private java.lang.String userId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.service_id
     * Comment: 参考服务类型ID,逗号分隔
     * @param serviceId the value for cmdb_resource_server_history.service_id
     */

    private java.lang.String serviceId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.memory
     * Comment: 内存大小
     * @param memory the value for cmdb_resource_server_history.memory
     */

    private java.lang.String memory;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.cpu
     * Comment: cpu个数
     * @param cpu the value for cmdb_resource_server_history.cpu
     */

    private java.lang.String cpu;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.host_id
     * Comment: 参考宿主机ID,自己的表,类型为宿主机的
     * @param hostId the value for cmdb_resource_server_history.host_id
     */

    private java.lang.Integer hostId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.manager_ip
     * Comment: 远程管理卡IP
     * @param managerIp the value for cmdb_resource_server_history.manager_ip
     */

    private java.lang.String managerIp;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.domain_name
     * Comment: 域名
     * @param domainName the value for cmdb_resource_server_history.domain_name
     */

    private java.lang.String domainName;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.ent_id
     * Comment: 参考环境管理,所属环境
     * @param entId the value for cmdb_resource_server_history.ent_id
     */

    private java.lang.Integer entId;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.disk_size
     * Comment: 硬盘大小
     * @param diskSize the value for cmdb_resource_server_history.disk_size
     */

    private java.lang.String diskSize;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.ip_address
     * Comment: 
     * @param ipAddress the value for cmdb_resource_server_history.ip_address
     */

    private java.lang.String ipAddress;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.open_port
     * Comment: 开放端口
     * @param openPort the value for cmdb_resource_server_history.open_port
     */

    private java.lang.String openPort;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for cmdb_resource_server_history.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_server_history.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_server_history.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.cabinet_level
     * Comment: 所在机柜的位置
     * @param cabinetLevel the value for cmdb_resource_server_history.cabinet_level
     */

    private java.lang.Integer cabinetLevel;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.create_time
     * Comment: 
     * @param createTime the value for cmdb_resource_server_history.create_time
     */

    private java.math.BigInteger createTime;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.description
     * Comment: 设备描述信息
     * @param description the value for cmdb_resource_server_history.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.expire
     * Comment: 过期时间
     * @param expire the value for cmdb_resource_server_history.expire
     */

    private java.lang.String expire;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.asset_coding
     * Comment: 资产编码
     * @param assetCoding the value for cmdb_resource_server_history.asset_coding
     */

    private java.lang.String assetCoding;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.status
     * Comment: 简单测试连通性,ping，检查是否活着
     * @param status the value for cmdb_resource_server_history.status
     */

    private java.lang.Integer status;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.buy_user
     * Comment: 购买人
     * @param buyUser the value for cmdb_resource_server_history.buy_user
     */

    private java.lang.String buyUser;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.buy_time
     * Comment: 购买时间
     * @param buyTime the value for cmdb_resource_server_history.buy_time
     */

    private java.lang.String buyTime;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.use_user
     * Comment: 使用人
     * @param useUser the value for cmdb_resource_server_history.use_user
     */

    private java.lang.String useUser;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.use_time
     * Comment: 开始使用时间
     * @param useTime the value for cmdb_resource_server_history.use_time
     */

    private java.lang.String useTime;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.resource_code
     * Comment: 资产编码
     * @param resourceCode the value for cmdb_resource_server_history.resource_code
     */

    private java.lang.String resourceCode;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.resource_price
     * Comment: 资产价格
     * @param resourcePrice the value for cmdb_resource_server_history.resource_price
     */

    private java.lang.String resourcePrice;


    /**
     * This field corresponds to the database column cmdb_resource_server_history.server_id
     * Comment: 服务器ID
     * @param serverId the value for cmdb_resource_server_history.server_id
     */
    public void setServerId(java.lang.Integer serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.groups_id
     * Comment: 业务线组
     * @param groupsId the value for cmdb_resource_server_history.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.type_id
     * Comment: 服务器类型
     * @param typeId the value for cmdb_resource_server_history.type_id
     */
    public void setTypeId(java.lang.Integer typeId){
       this.typeId = typeId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.os_id
     * Comment: 参考操作 系统类型
     * @param osId the value for cmdb_resource_server_history.os_id
     */
    public void setOsId(java.lang.Integer osId){
       this.osId = osId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.cabinet_id
     * Comment: 机柜ID
     * @param cabinetId the value for cmdb_resource_server_history.cabinet_id
     */
    public void setCabinetId(java.lang.Integer cabinetId){
       this.cabinetId = cabinetId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.user_id
     * Comment: 参考用户id,管理员
     * @param userId the value for cmdb_resource_server_history.user_id
     */
    public void setUserId(java.lang.String userId){
       this.userId = userId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.service_id
     * Comment: 参考服务类型ID,逗号分隔
     * @param serviceId the value for cmdb_resource_server_history.service_id
     */
    public void setServiceId(java.lang.String serviceId){
       this.serviceId = serviceId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.memory
     * Comment: 内存大小
     * @param memory the value for cmdb_resource_server_history.memory
     */
    public void setMemory(java.lang.String memory){
       this.memory = memory;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.cpu
     * Comment: cpu个数
     * @param cpu the value for cmdb_resource_server_history.cpu
     */
    public void setCpu(java.lang.String cpu){
       this.cpu = cpu;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.host_id
     * Comment: 参考宿主机ID,自己的表,类型为宿主机的
     * @param hostId the value for cmdb_resource_server_history.host_id
     */
    public void setHostId(java.lang.Integer hostId){
       this.hostId = hostId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.manager_ip
     * Comment: 远程管理卡IP
     * @param managerIp the value for cmdb_resource_server_history.manager_ip
     */
    public void setManagerIp(java.lang.String managerIp){
       this.managerIp = managerIp;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.domain_name
     * Comment: 域名
     * @param domainName the value for cmdb_resource_server_history.domain_name
     */
    public void setDomainName(java.lang.String domainName){
       this.domainName = domainName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.ent_id
     * Comment: 参考环境管理,所属环境
     * @param entId the value for cmdb_resource_server_history.ent_id
     */
    public void setEntId(java.lang.Integer entId){
       this.entId = entId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.disk_size
     * Comment: 硬盘大小
     * @param diskSize the value for cmdb_resource_server_history.disk_size
     */
    public void setDiskSize(java.lang.String diskSize){
       this.diskSize = diskSize;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.ip_address
     * Comment: 
     * @param ipAddress the value for cmdb_resource_server_history.ip_address
     */
    public void setIpAddress(java.lang.String ipAddress){
       this.ipAddress = ipAddress;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.open_port
     * Comment: 开放端口
     * @param openPort the value for cmdb_resource_server_history.open_port
     */
    public void setOpenPort(java.lang.String openPort){
       this.openPort = openPort;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for cmdb_resource_server_history.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_server_history.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_server_history.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.cabinet_level
     * Comment: 所在机柜的位置
     * @param cabinetLevel the value for cmdb_resource_server_history.cabinet_level
     */
    public void setCabinetLevel(java.lang.Integer cabinetLevel){
       this.cabinetLevel = cabinetLevel;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.create_time
     * Comment: 
     * @param createTime the value for cmdb_resource_server_history.create_time
     */
    public void setCreateTime(java.math.BigInteger createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.description
     * Comment: 设备描述信息
     * @param description the value for cmdb_resource_server_history.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.expire
     * Comment: 过期时间
     * @param expire the value for cmdb_resource_server_history.expire
     */
    public void setExpire(java.lang.String expire){
       this.expire = expire;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.asset_coding
     * Comment: 资产编码
     * @param assetCoding the value for cmdb_resource_server_history.asset_coding
     */
    public void setAssetCoding(java.lang.String assetCoding){
       this.assetCoding = assetCoding;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.status
     * Comment: 简单测试连通性,ping，检查是否活着
     * @param status the value for cmdb_resource_server_history.status
     */
    public void setStatus(java.lang.Integer status){
       this.status = status;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.buy_user
     * Comment: 购买人
     * @param buyUser the value for cmdb_resource_server_history.buy_user
     */
    public void setBuyUser(java.lang.String buyUser){
       this.buyUser = buyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.buy_time
     * Comment: 购买时间
     * @param buyTime the value for cmdb_resource_server_history.buy_time
     */
    public void setBuyTime(java.lang.String buyTime){
       this.buyTime = buyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.use_user
     * Comment: 使用人
     * @param useUser the value for cmdb_resource_server_history.use_user
     */
    public void setUseUser(java.lang.String useUser){
       this.useUser = useUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.use_time
     * Comment: 开始使用时间
     * @param useTime the value for cmdb_resource_server_history.use_time
     */
    public void setUseTime(java.lang.String useTime){
       this.useTime = useTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.resource_code
     * Comment: 资产编码
     * @param resourceCode the value for cmdb_resource_server_history.resource_code
     */
    public void setResourceCode(java.lang.String resourceCode){
       this.resourceCode = resourceCode;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.resource_price
     * Comment: 资产价格
     * @param resourcePrice the value for cmdb_resource_server_history.resource_price
     */
    public void setResourcePrice(java.lang.String resourcePrice){
       this.resourcePrice = resourcePrice;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.server_id
     * Comment: 服务器ID
     * @return the value of cmdb_resource_server_history.server_id
     */
     public java.lang.Integer getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.groups_id
     * Comment: 业务线组
     * @return the value of cmdb_resource_server_history.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.type_id
     * Comment: 服务器类型
     * @return the value of cmdb_resource_server_history.type_id
     */
     public java.lang.Integer getTypeId() {
        return typeId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.os_id
     * Comment: 参考操作 系统类型
     * @return the value of cmdb_resource_server_history.os_id
     */
     public java.lang.Integer getOsId() {
        return osId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.cabinet_id
     * Comment: 机柜ID
     * @return the value of cmdb_resource_server_history.cabinet_id
     */
     public java.lang.Integer getCabinetId() {
        return cabinetId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.user_id
     * Comment: 参考用户id,管理员
     * @return the value of cmdb_resource_server_history.user_id
     */
     public java.lang.String getUserId() {
        return userId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.service_id
     * Comment: 参考服务类型ID,逗号分隔
     * @return the value of cmdb_resource_server_history.service_id
     */
     public java.lang.String getServiceId() {
        return serviceId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.memory
     * Comment: 内存大小
     * @return the value of cmdb_resource_server_history.memory
     */
     public java.lang.String getMemory() {
        return memory;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.cpu
     * Comment: cpu个数
     * @return the value of cmdb_resource_server_history.cpu
     */
     public java.lang.String getCpu() {
        return cpu;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.host_id
     * Comment: 参考宿主机ID,自己的表,类型为宿主机的
     * @return the value of cmdb_resource_server_history.host_id
     */
     public java.lang.Integer getHostId() {
        return hostId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.manager_ip
     * Comment: 远程管理卡IP
     * @return the value of cmdb_resource_server_history.manager_ip
     */
     public java.lang.String getManagerIp() {
        return managerIp;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.domain_name
     * Comment: 域名
     * @return the value of cmdb_resource_server_history.domain_name
     */
     public java.lang.String getDomainName() {
        return domainName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.ent_id
     * Comment: 参考环境管理,所属环境
     * @return the value of cmdb_resource_server_history.ent_id
     */
     public java.lang.Integer getEntId() {
        return entId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.disk_size
     * Comment: 硬盘大小
     * @return the value of cmdb_resource_server_history.disk_size
     */
     public java.lang.String getDiskSize() {
        return diskSize;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.ip_address
     * Comment: 
     * @return the value of cmdb_resource_server_history.ip_address
     */
     public java.lang.String getIpAddress() {
        return ipAddress;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.open_port
     * Comment: 开放端口
     * @return the value of cmdb_resource_server_history.open_port
     */
     public java.lang.String getOpenPort() {
        return openPort;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.last_modify_time
     * Comment: 
     * @return the value of cmdb_resource_server_history.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_server_history.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_server_history.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.cabinet_level
     * Comment: 所在机柜的位置
     * @return the value of cmdb_resource_server_history.cabinet_level
     */
     public java.lang.Integer getCabinetLevel() {
        return cabinetLevel;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.create_time
     * Comment: 
     * @return the value of cmdb_resource_server_history.create_time
     */
     public java.math.BigInteger getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.description
     * Comment: 设备描述信息
     * @return the value of cmdb_resource_server_history.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.expire
     * Comment: 过期时间
     * @return the value of cmdb_resource_server_history.expire
     */
     public java.lang.String getExpire() {
        return expire;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.asset_coding
     * Comment: 资产编码
     * @return the value of cmdb_resource_server_history.asset_coding
     */
     public java.lang.String getAssetCoding() {
        return assetCoding;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.status
     * Comment: 简单测试连通性,ping，检查是否活着
     * @return the value of cmdb_resource_server_history.status
     */
     public java.lang.Integer getStatus() {
        return status;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.buy_user
     * Comment: 购买人
     * @return the value of cmdb_resource_server_history.buy_user
     */
     public java.lang.String getBuyUser() {
        return buyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.buy_time
     * Comment: 购买时间
     * @return the value of cmdb_resource_server_history.buy_time
     */
     public java.lang.String getBuyTime() {
        return buyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.use_user
     * Comment: 使用人
     * @return the value of cmdb_resource_server_history.use_user
     */
     public java.lang.String getUseUser() {
        return useUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.use_time
     * Comment: 开始使用时间
     * @return the value of cmdb_resource_server_history.use_time
     */
     public java.lang.String getUseTime() {
        return useTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.resource_code
     * Comment: 资产编码
     * @return the value of cmdb_resource_server_history.resource_code
     */
     public java.lang.String getResourceCode() {
        return resourceCode;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_history.resource_price
     * Comment: 资产价格
     * @return the value of cmdb_resource_server_history.resource_price
     */
     public java.lang.String getResourcePrice() {
        return resourcePrice;
    }
}
