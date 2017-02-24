package com.asura.monitor.graph.entity;
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
 * @date 2016-08-08 08:52:15
 * @since 1.0
 */
public class CmdbGraphQuartzEntity extends BaseEntity{
    /**
     * This field corresponds to the database column cmdb_graph_quartz.name
     * Comment: 只要名字在，就不执行，等于一个锁
     * @param name the value for cmdb_graph_quartz.name
     */

    private java.lang.String name;


    /**
     * This field corresponds to the database column cmdb_graph_quartz.ip_address
     * Comment: 哪个ip地址添加的
     * @param ipAddress the value for cmdb_graph_quartz.ip_address
     */

    private java.lang.String ipAddress;


    /**
     * This field corresponds to the database column cmdb_graph_quartz.create_time
     * Comment: 添加时间
     * @param createTime the value for cmdb_graph_quartz.create_time
     */

    private java.math.BigInteger createTime;


    /**
     * This field corresponds to the database column cmdb_graph_quartz.name
     * Comment: 只要名字在，就不执行，等于一个锁
     * @param name the value for cmdb_graph_quartz.name
     */
    public void setName(java.lang.String name){
       this.name = name;
    }

    /**
     * This field corresponds to the database column cmdb_graph_quartz.ip_address
     * Comment: 哪个ip地址添加的
     * @param ipAddress the value for cmdb_graph_quartz.ip_address
     */
    public void setIpAddress(java.lang.String ipAddress){
       this.ipAddress = ipAddress;
    }

    /**
     * This field corresponds to the database column cmdb_graph_quartz.create_time
     * Comment: 添加时间
     * @param createTime the value for cmdb_graph_quartz.create_time
     */
    public void setCreateTime(java.math.BigInteger createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_graph_quartz.name
     * Comment: 只要名字在，就不执行，等于一个锁
     * @return the value of cmdb_graph_quartz.name
     */
     public java.lang.String getName() {
        return name;
    }

    /**
     * This field corresponds to the database column cmdb_graph_quartz.ip_address
     * Comment: 哪个ip地址添加的
     * @return the value of cmdb_graph_quartz.ip_address
     */
     public java.lang.String getIpAddress() {
        return ipAddress;
    }

    /**
     * This field corresponds to the database column cmdb_graph_quartz.create_time
     * Comment: 添加时间
     * @return the value of cmdb_graph_quartz.create_time
     */
     public java.math.BigInteger getCreateTime() {
        return createTime;
    }
}
