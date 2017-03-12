package com.asura.monitor.cluster.entity;
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
 * @date 2017-03-09 18:27:05
 * @since 1.0
 */
public class MonitorClusterConfigureEntity extends BaseEntity{

    private String groupsName;
    private String description;

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_id
     * Comment: 
     * @param clusterId the value for monitor_cluster_configure.cluster_id
     */

    private java.lang.Integer clusterId;


    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_name
     * Comment: 集群名字
     * @param clusterName the value for monitor_cluster_configure.cluster_name
     */

    private java.lang.String clusterName;


    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_hosts
     * Comment: 集群host
     * @param clusterHosts the value for monitor_cluster_configure.cluster_hosts
     */

    private java.lang.String clusterHosts;


    /**
     * This field corresponds to the database column monitor_cluster_configure.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_cluster_configure.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_cluster_configure.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_cluster_configure.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_cluster_configure.groups_id
     * Comment: 参考cmdb组id
     * @param groupsId the value for monitor_cluster_configure.groups_id
     */

    private java.lang.Integer groupsId;


    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_id
     * Comment: 
     * @param clusterId the value for monitor_cluster_configure.cluster_id
     */
    public void setClusterId(java.lang.Integer clusterId){
       this.clusterId = clusterId;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_name
     * Comment: 集群名字
     * @param clusterName the value for monitor_cluster_configure.cluster_name
     */
    public void setClusterName(java.lang.String clusterName){
       this.clusterName = clusterName;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_hosts
     * Comment: 集群host
     * @param clusterHosts the value for monitor_cluster_configure.cluster_hosts
     */
    public void setClusterHosts(java.lang.String clusterHosts){
       this.clusterHosts = clusterHosts;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_cluster_configure.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_cluster_configure.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.groups_id
     * Comment: 参考cmdb组id
     * @param groupsId the value for monitor_cluster_configure.groups_id
     */
    public void setGroupsId(java.lang.Integer groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_id
     * Comment: 
     * @return the value of monitor_cluster_configure.cluster_id
     */
     public java.lang.Integer getClusterId() {
        return clusterId;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_name
     * Comment: 集群名字
     * @return the value of monitor_cluster_configure.cluster_name
     */
     public java.lang.String getClusterName() {
        return clusterName;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.cluster_hosts
     * Comment: 集群host
     * @return the value of monitor_cluster_configure.cluster_hosts
     */
     public java.lang.String getClusterHosts() {
        return clusterHosts;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_cluster_configure.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_cluster_configure.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_cluster_configure.groups_id
     * Comment: 参考cmdb组id
     * @return the value of monitor_cluster_configure.groups_id
     */
     public java.lang.Integer getGroupsId() {
        return groupsId;
    }
}
