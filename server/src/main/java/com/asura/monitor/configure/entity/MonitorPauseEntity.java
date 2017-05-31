package com.asura.monitor.configure.entity;

/**
 * 报警暂停使用功能
 * Created by zhaoyun on 2017/5/25.
 */
public class MonitorPauseEntity {

    // 机房名称
    private String floorName;

    private String hostId;
    private String serverId;
    private String ipAddress;
    private String hostIpAddress;


    // 创建时间
    private long createTime;
    // 用户ID
    private String userId;
    private String userName;
    // 脚本ID
    private String scriptsId;
    // 环境ID
    private String entId;
    private String entName;
    // 报警暂停时间
    private String pauseTime;
    // 某个机房报警
    private String floorId;
    // 创建时间
    // 创建人
    private String createUser;
    // 关闭时间
    private String closeTime;
    // 脚本名称
    private String scriptName;
    private String groupsId;
    private String groupsName;

    // 机柜
    private String cabinetId;
    private String cabinetName;

    public String getHostId() {
        return hostId;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getHostIpAddress() {
        return hostIpAddress;
    }

    public void setHostIpAddress(String hostIpAddress) {
        this.hostIpAddress = hostIpAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getGroupsId() {
        return groupsId;
    }

    public void setGroupsId(String groupsId) {
        this.groupsId = groupsId;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public long getCreateTime() {
        return createTime;
    }

    public String getEntId() {
        return entId;
    }

    public String getScriptName() {
        return scriptName;
    }

    public void setScriptName(String scriptName) {
        this.scriptName = scriptName;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = closeTime;
    }


    public String getFloorId() {
        return floorId;
    }

    public void setFloorId(String floorId) {
        this.floorId = floorId;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getScriptsId() {
        return scriptsId;
    }

    public void setScriptsId(String scriptsId) {
        this.scriptsId = scriptsId;
    }


    public void setEntId(String entId) {
        this.entId = entId;
    }


    public String getPauseTime() {
        return pauseTime;
    }

    public void setPauseTime(String pauseTime) {
        this.pauseTime = pauseTime;
    }
}
