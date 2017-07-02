package com.asura.monitor.gateway.entity;
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
 * @date 2017-07-02 09:49:13
 * @since 1.0
 */
public class MonitorGatewayEntity extends BaseEntity{

    private String floorName;

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    /**
     * This field corresponds to the database column monitor_gateway.gateway_id
     * Comment: 
     * @param gatewayId the value for monitor_gateway.gateway_id
     */

    private java.lang.Integer gatewayId;


    /**
     * This field corresponds to the database column monitor_gateway.floor_id
     * Comment: 参考机房ID
     * @param floorId the value for monitor_gateway.floor_id
     */

    private java.lang.Integer floorId;


    /**
     * This field corresponds to the database column monitor_gateway.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_gateway.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_gateway.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_gateway.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_gateway.description
     * Comment: 描述信息
     * @param description the value for monitor_gateway.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_gateway.roles
     * Comment: 服务器角色,一个机房的都为一个角色，master,proxy
     * @param roles the value for monitor_gateway.roles
     */

    private java.lang.String roles;


    /**
     * This field corresponds to the database column monitor_gateway.port
     * Comment: http端口
     * @param port the value for monitor_gateway.port
     */

    private java.lang.String port;


    /**
     * This field corresponds to the database column monitor_gateway.ip_address
     * Comment: 
     * @param ipAddress the value for monitor_gateway.ip_address
     */

    private java.lang.String ipAddress;


    /**
     * This field corresponds to the database column monitor_gateway.gateway_id
     * Comment: 
     * @param gatewayId the value for monitor_gateway.gateway_id
     */
    public void setGatewayId(java.lang.Integer gatewayId){
       this.gatewayId = gatewayId;
    }

    /**
     * This field corresponds to the database column monitor_gateway.floor_id
     * Comment: 参考机房ID
     * @param floorId the value for monitor_gateway.floor_id
     */
    public void setFloorId(java.lang.Integer floorId){
       this.floorId = floorId;
    }

    /**
     * This field corresponds to the database column monitor_gateway.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_gateway.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_gateway.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_gateway.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_gateway.description
     * Comment: 描述信息
     * @param description the value for monitor_gateway.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_gateway.roles
     * Comment: 服务器角色,一个机房的都为一个角色，master,proxy
     * @param roles the value for monitor_gateway.roles
     */
    public void setRoles(java.lang.String roles){
       this.roles = roles;
    }

    /**
     * This field corresponds to the database column monitor_gateway.port
     * Comment: http端口
     * @param port the value for monitor_gateway.port
     */
    public void setPort(java.lang.String port){
       this.port = port;
    }

    /**
     * This field corresponds to the database column monitor_gateway.ip_address
     * Comment: 
     * @param ipAddress the value for monitor_gateway.ip_address
     */
    public void setIpAddress(java.lang.String ipAddress){
       this.ipAddress = ipAddress;
    }

    /**
     * This field corresponds to the database column monitor_gateway.gateway_id
     * Comment: 
     * @return the value of monitor_gateway.gateway_id
     */
     public java.lang.Integer getGatewayId() {
        return gatewayId;
    }

    /**
     * This field corresponds to the database column monitor_gateway.floor_id
     * Comment: 参考机房ID
     * @return the value of monitor_gateway.floor_id
     */
     public java.lang.Integer getFloorId() {
        return floorId;
    }

    /**
     * This field corresponds to the database column monitor_gateway.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_gateway.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_gateway.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_gateway.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_gateway.description
     * Comment: 描述信息
     * @return the value of monitor_gateway.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_gateway.roles
     * Comment: 服务器角色,一个机房的都为一个角色，master,proxy
     * @return the value of monitor_gateway.roles
     */
     public java.lang.String getRoles() {
        return roles;
    }

    /**
     * This field corresponds to the database column monitor_gateway.port
     * Comment: http端口
     * @return the value of monitor_gateway.port
     */
     public java.lang.String getPort() {
        return port;
    }

    /**
     * This field corresponds to the database column monitor_gateway.ip_address
     * Comment: 
     * @return the value of monitor_gateway.ip_address
     */
     public java.lang.String getIpAddress() {
        return ipAddress;
    }
}
