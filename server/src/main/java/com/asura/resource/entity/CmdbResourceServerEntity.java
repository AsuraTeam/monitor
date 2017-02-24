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
 * @date 2016-07-28 11:43:59
 * @since 1.0
 */
public class CmdbResourceServerEntity extends BaseEntity{

    private java.lang.String lastModifyTime;
    private String groupsName;
    private String entName;
    private String userName;
    private String osName;
    private String cabinetName;
    private String typeName;
    private String serviceName;
    private String hostIpAddress;
    private String description;
    private int cnt;
    private int number;
    private int status;
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    // 过保时间
    private String expire;
    // 资产编码
    private String assetCoding;

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    public String getAssetCoding() {
        return assetCoding;
    }

    public void setAssetCoding(String assetCoding) {
        this.assetCoding = assetCoding;
    }

    private String cabinetScope;

    public String getCabinetScope() {
        return cabinetScope;
    }

    public void setCabinetScope(String cabinetScope) {
        this.cabinetScope = cabinetScope;
    }


    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    // 报表使用的时间
    private String dates;

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getHostIpAddress() {
        return hostIpAddress;
    }

    public void setHostIpAddress(String hostIpAddress) {
        this.hostIpAddress = hostIpAddress;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }



    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }



    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    /**
     * This field corresponds to the database column cmdb_resource_server.server_id
     * Comment: 服务器ID
     * @param serverId the value for cmdb_resource_server.server_id
     */

    private java.lang.Integer serverId;


    /**
     * This field corresponds to the database column cmdb_resource_server.groups_id
     * Comment: 业务线组
     * @param groupsId the value for cmdb_resource_server.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column cmdb_resource_server.type_id
     * Comment: 服务器类型
     * @param typeId the value for cmdb_resource_server.type_id
     */

    private java.lang.Integer typeId;


    /**
     * This field corresponds to the database column cmdb_resource_server.os_id
     * Comment: 参考操作 系统类型
     * @param osId the value for cmdb_resource_server.os_id
     */

    private java.lang.Integer osId;


    /**
     * This field corresponds to the database column cmdb_resource_server.cabinet_id
     * Comment: 机柜ID
     * @param cabinetId the value for cmdb_resource_server.cabinet_id
     */

    private java.lang.Integer cabinetId;


    /**
     * This field corresponds to the database column cmdb_resource_server.user_id
     * Comment: 参考用户id,管理员
     * @param userId the value for cmdb_resource_server.user_id
     */

    private java.lang.String userId;


    /**
     * This field corresponds to the database column cmdb_resource_server.service_id
     * Comment: 参考服务类型ID,逗号分隔
     * @param serviceId the value for cmdb_resource_server.service_id
     */

    private java.lang.String serviceId;


    /**
     * This field corresponds to the database column cmdb_resource_server.memory
     * Comment: 内存大小
     * @param memory the value for cmdb_resource_server.memory
     */

    private java.lang.String memory;


    /**
     * This field corresponds to the database column cmdb_resource_server.cpu
     * Comment: cpu个数
     * @param cpu the value for cmdb_resource_server.cpu
     */

    private java.lang.String cpu;


    /**
     * This field corresponds to the database column cmdb_resource_server.host_id
     * Comment: 参考宿主机ID,自己的表,类型为宿主机的
     * @param hostId the value for cmdb_resource_server.host_id
     */

    private java.lang.Integer hostId;


    /**
     * This field corresponds to the database column cmdb_resource_server.manager_ip
     * Comment: 远程管理卡IP
     * @param managerIp the value for cmdb_resource_server.manager_ip
     */

    private java.lang.String managerIp;


    /**
     * This field corresponds to the database column cmdb_resource_server.domain_name
     * Comment: 域名
     * @param domainName the value for cmdb_resource_server.domain_name
     */

    private java.lang.String domainName;


    /**
     * This field corresponds to the database column cmdb_resource_server.ent_id
     * Comment: 参考环境管理,所属环境
     * @param entId the value for cmdb_resource_server.ent_id
     */

    private java.lang.Integer entId;


    /**
     * This field corresponds to the database column cmdb_resource_server.disk_size
     * Comment: 硬盘大小
     * @param diskSize the value for cmdb_resource_server.disk_size
     */

    private java.lang.String diskSize;


    /**
     * This field corresponds to the database column cmdb_resource_server.ip_address
     * Comment: 服务器IP地址 ,多个逗号分隔
     * @param ipAddress the value for cmdb_resource_server.ip_address
     */

    private java.lang.String ipAddress;


    /**
     * This field corresponds to the database column cmdb_resource_server.open_port
     * Comment: 开放端口
     * @param openPort the value for cmdb_resource_server.open_port
     */

    private java.lang.String openPort;


    /**
     * This field corresponds to the database column cmdb_resource_server.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_server.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_server.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_server.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_server.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_server.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_server.cabinet_level
     * Comment: 所在机柜的位置
     * @param cabinetLevel the value for cmdb_resource_server.cabinet_level
     */

    private java.lang.Integer cabinetLevel;


    /**
     * This field corresponds to the database column cmdb_resource_server.server_id
     * Comment: 服务器ID
     * @param serverId the value for cmdb_resource_server.server_id
     */
    public void setServerId(java.lang.Integer serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.groups_id
     * Comment: 业务线组
     * @param groupsId the value for cmdb_resource_server.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.type_id
     * Comment: 服务器类型
     * @param typeId the value for cmdb_resource_server.type_id
     */
    public void setTypeId(java.lang.Integer typeId){
       this.typeId = typeId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.os_id
     * Comment: 参考操作 系统类型
     * @param osId the value for cmdb_resource_server.os_id
     */
    public void setOsId(java.lang.Integer osId){
       this.osId = osId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.cabinet_id
     * Comment: 机柜ID
     * @param cabinetId the value for cmdb_resource_server.cabinet_id
     */
    public void setCabinetId(java.lang.Integer cabinetId){
       this.cabinetId = cabinetId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.user_id
     * Comment: 参考用户id,管理员
     * @param userId the value for cmdb_resource_server.user_id
     */
    public void setUserId(java.lang.String userId){
       this.userId = userId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.service_id
     * Comment: 参考服务类型ID,逗号分隔
     * @param serviceId the value for cmdb_resource_server.service_id
     */
    public void setServiceId(java.lang.String serviceId){
       this.serviceId = serviceId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.memory
     * Comment: 内存大小
     * @param memory the value for cmdb_resource_server.memory
     */
    public void setMemory(java.lang.String memory){
       this.memory = memory;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.cpu
     * Comment: cpu个数
     * @param cpu the value for cmdb_resource_server.cpu
     */
    public void setCpu(java.lang.String cpu){
       this.cpu = cpu;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.host_id
     * Comment: 参考宿主机ID,自己的表,类型为宿主机的
     * @param hostId the value for cmdb_resource_server.host_id
     */
    public void setHostId(java.lang.Integer hostId){
       this.hostId = hostId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.manager_ip
     * Comment: 远程管理卡IP
     * @param managerIp the value for cmdb_resource_server.manager_ip
     */
    public void setManagerIp(java.lang.String managerIp){
       this.managerIp = managerIp;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.domain_name
     * Comment: 域名
     * @param domainName the value for cmdb_resource_server.domain_name
     */
    public void setDomainName(java.lang.String domainName){
       this.domainName = domainName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.ent_id
     * Comment: 参考环境管理,所属环境
     * @param entId the value for cmdb_resource_server.ent_id
     */
    public void setEntId(java.lang.Integer entId){
       this.entId = entId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.disk_size
     * Comment: 硬盘大小
     * @param diskSize the value for cmdb_resource_server.disk_size
     */
    public void setDiskSize(java.lang.String diskSize){
       this.diskSize = diskSize;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.ip_address
     * Comment: 服务器IP地址 ,多个逗号分隔
     * @param ipAddress the value for cmdb_resource_server.ip_address
     */
    public void setIpAddress(java.lang.String ipAddress){
       this.ipAddress = ipAddress;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.open_port
     * Comment: 开放端口
     * @param openPort the value for cmdb_resource_server.open_port
     */
    public void setOpenPort(java.lang.String openPort){
       this.openPort = openPort;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_server.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_server.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_server.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.cabinet_level
     * Comment: 所在机柜的位置
     * @param cabinetLevel the value for cmdb_resource_server.cabinet_level
     */
    public void setCabinetLevel(java.lang.Integer cabinetLevel){
       this.cabinetLevel = cabinetLevel;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.server_id
     * Comment: 服务器ID
     * @return the value of cmdb_resource_server.server_id
     */
     public java.lang.Integer getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.groups_id
     * Comment: 业务线组
     * @return the value of cmdb_resource_server.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.type_id
     * Comment: 服务器类型
     * @return the value of cmdb_resource_server.type_id
     */
     public java.lang.Integer getTypeId() {
        return typeId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.os_id
     * Comment: 参考操作 系统类型
     * @return the value of cmdb_resource_server.os_id
     */
     public java.lang.Integer getOsId() {
        return osId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.cabinet_id
     * Comment: 机柜ID
     * @return the value of cmdb_resource_server.cabinet_id
     */
     public java.lang.Integer getCabinetId() {
        return cabinetId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.user_id
     * Comment: 参考用户id,管理员
     * @return the value of cmdb_resource_server.user_id
     */
     public java.lang.String getUserId() {
        return userId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.service_id
     * Comment: 参考服务类型ID,逗号分隔
     * @return the value of cmdb_resource_server.service_id
     */
     public java.lang.String getServiceId() {
        return serviceId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.memory
     * Comment: 内存大小
     * @return the value of cmdb_resource_server.memory
     */
     public java.lang.String getMemory() {
        return memory;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.cpu
     * Comment: cpu个数
     * @return the value of cmdb_resource_server.cpu
     */
     public java.lang.String getCpu() {
        return cpu;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.host_id
     * Comment: 参考宿主机ID,自己的表,类型为宿主机的
     * @return the value of cmdb_resource_server.host_id
     */
     public java.lang.Integer getHostId() {
        return hostId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.manager_ip
     * Comment: 远程管理卡IP
     * @return the value of cmdb_resource_server.manager_ip
     */
     public java.lang.String getManagerIp() {
        return managerIp;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.domain_name
     * Comment: 域名
     * @return the value of cmdb_resource_server.domain_name
     */
     public java.lang.String getDomainName() {
        return domainName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.ent_id
     * Comment: 参考环境管理,所属环境
     * @return the value of cmdb_resource_server.ent_id
     */
     public java.lang.Integer getEntId() {
        return entId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.disk_size
     * Comment: 硬盘大小
     * @return the value of cmdb_resource_server.disk_size
     */
     public java.lang.String getDiskSize() {
        return diskSize;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.ip_address
     * Comment: 服务器IP地址 ,多个逗号分隔
     * @return the value of cmdb_resource_server.ip_address
     */
     public java.lang.String getIpAddress() {
        return ipAddress;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.open_port
     * Comment: 开放端口
     * @return the value of cmdb_resource_server.open_port
     */
     public java.lang.String getOpenPort() {
        return openPort;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_server.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_server.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_server.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server.cabinet_level
     * Comment: 所在机柜的位置
     * @return the value of cmdb_resource_server.cabinet_level
     */
     public java.lang.Integer getCabinetLevel() {
        return cabinetLevel;
    }
}
