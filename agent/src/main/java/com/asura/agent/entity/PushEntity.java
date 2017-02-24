package com.asura.agent.entity;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 * 画图数据实体
 * @author zhaozq14
 * @version 1.0
 * @date 2016-08-12 09:07:17
 * @since 1.0
 *
 **/
public class PushEntity {

    /**
     * 客户端ip
    */
    private String ip;

    /**
     * 图像名
    */
    private String name;

    /**
     * 图像组
    */
    private String groups;

    /**
     * 图像值
    **/
    private String value;

    /**
     * 消息日志
     */
    private String messages;

    /**
     * 报警状态1,2,3,4
     */
    private String status;

    /**
     * 脚本名称
     */
    private String command;

    /**
     * 处理时间
     */
    private String time;

    /**
     * 业务线
     */
    private String groupsName;

    /**
     * 配置文件ID
     */
    private String configId;

    /**
     * 处理服务器
     */
    private String server;

    /**
     * 设置脚本id
     * @return
     */
    private String scriptId;

    /**
     * 服务器ID
     * @return
     */
    private String serverId;


    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getScriptId() {
        return scriptId;
    }

    public void setScriptId(String scriptId) {
        this.scriptId = scriptId;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}