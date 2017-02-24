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
 * @date 2016-08-15 18:10:32
 * @since 1.0
 */
public class CmdbResourceNetworkEntity extends BaseEntity{


    private int cnt;

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    private int free;
    private int used;
    private int status;


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getFree() {
        return free;
    }

    public void setFree(int free) {
        this.free = free;
    }

    public int getUsed() {
        return used;
    }

    public void setUsed(int used) {
        this.used = used;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.network_id
     * Comment: 
     * @param networkId the value for cmdb_resource_network.network_id
     */

    private java.lang.Integer networkId;


    /**
     * This field corresponds to the database column cmdb_resource_network.network_prefix
     * Comment: 前缀
     * @param networkPrefix the value for cmdb_resource_network.network_prefix
     */

    private java.lang.String networkPrefix;


    /**
     * This field corresponds to the database column cmdb_resource_network.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for cmdb_resource_network.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column cmdb_resource_network.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_network.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_network.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_network.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_network.create_time
     * Comment: 
     * @param createTime the value for cmdb_resource_network.create_time
     */

    private java.math.BigInteger createTime;


    /**
     * This field corresponds to the database column cmdb_resource_network.network_suffix
     * Comment: 后缀
     * @param networkSuffix the value for cmdb_resource_network.network_suffix
     */

    private java.lang.Integer networkSuffix;


    /**
     * This field corresponds to the database column cmdb_resource_network.description
     * Comment: 描述信息
     * @param description the value for cmdb_resource_network.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column cmdb_resource_network.network_id
     * Comment: 
     * @param networkId the value for cmdb_resource_network.network_id
     */
    public void setNetworkId(java.lang.Integer networkId){
       this.networkId = networkId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.network_prefix
     * Comment: 前缀
     * @param networkPrefix the value for cmdb_resource_network.network_prefix
     */
    public void setNetworkPrefix(java.lang.String networkPrefix){
       this.networkPrefix = networkPrefix;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for cmdb_resource_network.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_network.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_network.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.create_time
     * Comment: 
     * @param createTime the value for cmdb_resource_network.create_time
     */
    public void setCreateTime(java.math.BigInteger createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.network_suffix
     * Comment: 后缀
     * @param networkSuffix the value for cmdb_resource_network.network_suffix
     */
    public void setNetworkSuffix(java.lang.Integer networkSuffix){
       this.networkSuffix = networkSuffix;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.description
     * Comment: 描述信息
     * @param description the value for cmdb_resource_network.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.network_id
     * Comment: 
     * @return the value of cmdb_resource_network.network_id
     */
     public java.lang.Integer getNetworkId() {
        return networkId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.network_prefix
     * Comment: 前缀
     * @return the value of cmdb_resource_network.network_prefix
     */
     public java.lang.String getNetworkPrefix() {
        return networkPrefix;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.last_modify_time
     * Comment: 
     * @return the value of cmdb_resource_network.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_network.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_network.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.create_time
     * Comment: 
     * @return the value of cmdb_resource_network.create_time
     */
     public java.math.BigInteger getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.network_suffix
     * Comment: 后缀
     * @return the value of cmdb_resource_network.network_suffix
     */
     public java.lang.Integer getNetworkSuffix() {
        return networkSuffix;
    }

    /**
     * This field corresponds to the database column cmdb_resource_network.description
     * Comment: 描述信息
     * @return the value of cmdb_resource_network.description
     */
     public java.lang.String getDescription() {
        return description;
    }
}
