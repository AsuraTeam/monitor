package com.asura.monitor.top.entity;
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
 * @date 2017-08-18 08:57:38
 * @since 1.0
 */
public class MonitorTopEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_top.top_id
     * Comment: 主键
     * @param topId the value for monitor_top.top_id
     */

    private java.lang.Integer topId;


    /**
     * This field corresponds to the database column monitor_top.server_id
     * Comment: top的ip地址，依赖cmdb
     * @param serverId the value for monitor_top.server_id
     */

    private java.lang.Integer serverId;


    /**
     * This field corresponds to the database column monitor_top.description
     * Comment: 描述信息
     * @param description the value for monitor_top.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_top.top_comm
     * Comment: top链接的描述信息
     * @param topComm the value for monitor_top.top_comm
     */

    private java.lang.String topComm;


    /**
     * This field corresponds to the database column monitor_top.top_left
     * Comment: 图像离页面左面的距离
     * @param topLeft the value for monitor_top.top_left
     */

    private java.lang.Integer topLeft;


    /**
     * This field corresponds to the database column monitor_top.top
     * Comment: 图像离页面上方的距离
     * @param top the value for monitor_top.top
     */

    private java.lang.Integer top;


    /**
     * This field corresponds to the database column monitor_top.top_to
     * Comment: 图像连接到的设备ID
     * @param topTo the value for monitor_top.top_to
     */

    private java.lang.String topTo;


    /**
     * This field corresponds to the database column monitor_top.top_graph_id
     * Comment: top图像的id，一个组的ID
     * @param topGraphId the value for monitor_top.top_graph_id
     */

    private java.lang.Integer topGraphId;


    /**
     * This field corresponds to the database column monitor_top.image_name
     * Comment: 图像名称
     * @param imageName the value for monitor_top.image_name
     */

    private java.lang.String imageName;


    /**
     * This field corresponds to the database column monitor_top.gson_data
     * Comment: 存储图像top数据
     * @param gsonData the value for monitor_top.gson_data
     */

    private java.lang.String gsonData;


    /**
     * This field corresponds to the database column monitor_top.top_id
     * Comment: 主键
     * @param topId the value for monitor_top.top_id
     */
    public void setTopId(java.lang.Integer topId){
       this.topId = topId;
    }

    /**
     * This field corresponds to the database column monitor_top.server_id
     * Comment: top的ip地址，依赖cmdb
     * @param serverId the value for monitor_top.server_id
     */
    public void setServerId(java.lang.Integer serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column monitor_top.description
     * Comment: 描述信息
     * @param description the value for monitor_top.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_top.top_comm
     * Comment: top链接的描述信息
     * @param topComm the value for monitor_top.top_comm
     */
    public void setTopComm(java.lang.String topComm){
       this.topComm = topComm;
    }

    /**
     * This field corresponds to the database column monitor_top.top_left
     * Comment: 图像离页面左面的距离
     * @param topLeft the value for monitor_top.top_left
     */
    public void setTopLeft(java.lang.Integer topLeft){
       this.topLeft = topLeft;
    }

    /**
     * This field corresponds to the database column monitor_top.top
     * Comment: 图像离页面上方的距离
     * @param top the value for monitor_top.top
     */
    public void setTop(java.lang.Integer top){
       this.top = top;
    }

    /**
     * This field corresponds to the database column monitor_top.top_to
     * Comment: 图像连接到的设备ID
     * @param topTo the value for monitor_top.top_to
     */
    public void setTopTo(java.lang.String topTo){
       this.topTo = topTo;
    }

    /**
     * This field corresponds to the database column monitor_top.top_graph_id
     * Comment: top图像的id，一个组的ID
     * @param topGraphId the value for monitor_top.top_graph_id
     */
    public void setTopGraphId(java.lang.Integer topGraphId){
       this.topGraphId = topGraphId;
    }

    /**
     * This field corresponds to the database column monitor_top.image_name
     * Comment: 图像名称
     * @param imageName the value for monitor_top.image_name
     */
    public void setImageName(java.lang.String imageName){
       this.imageName = imageName;
    }

    /**
     * This field corresponds to the database column monitor_top.gson_data
     * Comment: 存储图像top数据
     * @param gsonData the value for monitor_top.gson_data
     */
    public void setGsonData(java.lang.String gsonData){
       this.gsonData = gsonData;
    }

    /**
     * This field corresponds to the database column monitor_top.top_id
     * Comment: 主键
     * @return the value of monitor_top.top_id
     */
     public java.lang.Integer getTopId() {
        return topId;
    }

    /**
     * This field corresponds to the database column monitor_top.server_id
     * Comment: top的ip地址，依赖cmdb
     * @return the value of monitor_top.server_id
     */
     public java.lang.Integer getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column monitor_top.description
     * Comment: 描述信息
     * @return the value of monitor_top.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_top.top_comm
     * Comment: top链接的描述信息
     * @return the value of monitor_top.top_comm
     */
     public java.lang.String getTopComm() {
        return topComm;
    }

    /**
     * This field corresponds to the database column monitor_top.top_left
     * Comment: 图像离页面左面的距离
     * @return the value of monitor_top.top_left
     */
     public java.lang.Integer getTopLeft() {
        return topLeft;
    }

    /**
     * This field corresponds to the database column monitor_top.top
     * Comment: 图像离页面上方的距离
     * @return the value of monitor_top.top
     */
     public java.lang.Integer getTop() {
        return top;
    }

    /**
     * This field corresponds to the database column monitor_top.top_to
     * Comment: 图像连接到的设备ID
     * @return the value of monitor_top.top_to
     */
     public java.lang.String getTopTo() {
        return topTo;
    }

    /**
     * This field corresponds to the database column monitor_top.top_graph_id
     * Comment: top图像的id，一个组的ID
     * @return the value of monitor_top.top_graph_id
     */
     public java.lang.Integer getTopGraphId() {
        return topGraphId;
    }

    /**
     * This field corresponds to the database column monitor_top.image_name
     * Comment: 图像名称
     * @return the value of monitor_top.image_name
     */
     public java.lang.String getImageName() {
        return imageName;
    }

    /**
     * This field corresponds to the database column monitor_top.gson_data
     * Comment: 存储图像top数据
     * @return the value of monitor_top.gson_data
     */
     public java.lang.String getGsonData() {
        return gsonData;
    }
}
