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
 * @date 2016-08-15 18:28:17
 * @since 1.0
 */
public class CmdbResourceNetworkAddressEntity extends BaseEntity{

    private int cnt;
    private String ipAddress;

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.id
     * Comment: 
     * @param id the value for cmdb_resource_network_address.id
     */

    private java.lang.Integer id;


    /**
     * This field corresponds to the database column cmdb_resource_network_address.ip_prefix_id
     * Comment: 参考网络的id
     * @param ipPrefixId the value for cmdb_resource_network_address.ip_prefix_id
     */

    private java.lang.Integer ipPrefixId;


    /**
     * This field corresponds to the database column cmdb_resource_network_address.ip_subffix
     * Comment: ip地址后缀
     * @param ipSubffix the value for cmdb_resource_network_address.ip_subffix
     */

    private java.lang.Integer ipSubffix;


    /**
     * This field corresponds to the database column cmdb_resource_network_address.status
     * Comment: 使用状态
     * @param status the value for cmdb_resource_network_address.status
     */

    private java.lang.Integer status;


    /**
     * This field corresponds to the database column cmdb_resource_network_address.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for cmdb_resource_network_address.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column cmdb_resource_network_address.id
     * Comment: 
     * @param id the value for cmdb_resource_network_address.id
     */
    public void setId(java.lang.Integer id){
       this.id = id;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.ip_prefix_id
     * Comment: 参考网络的id
     * @param ipPrefixId the value for cmdb_resource_network_address.ip_prefix_id
     */
    public void setIpPrefixId(java.lang.Integer ipPrefixId){
       this.ipPrefixId = ipPrefixId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.ip_subffix
     * Comment: ip地址后缀
     * @param ipSubffix the value for cmdb_resource_network_address.ip_subffix
     */
    public void setIpSubffix(java.lang.Integer ipSubffix){
       this.ipSubffix = ipSubffix;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.status
     * Comment: 使用状态
     * @param status the value for cmdb_resource_network_address.status
     */
    public void setStatus(java.lang.Integer status){
       this.status = status;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for cmdb_resource_network_address.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.id
     * Comment: 
     * @return the value of cmdb_resource_network_address.id
     */
     public java.lang.Integer getId() {
        return id;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.ip_prefix_id
     * Comment: 参考网络的id
     * @return the value of cmdb_resource_network_address.ip_prefix_id
     */
     public java.lang.Integer getIpPrefixId() {
        return ipPrefixId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.ip_subffix
     * Comment: ip地址后缀
     * @return the value of cmdb_resource_network_address.ip_subffix
     */
     public java.lang.Integer getIpSubffix() {
        return ipSubffix;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.status
     * Comment: 使用状态
     * @return the value of cmdb_resource_network_address.status
     */
     public java.lang.Integer getStatus() {
        return status;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network_address.last_modify_time
     * Comment: 最近修改时间
     * @return the value of cmdb_resource_network_address.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }
}
