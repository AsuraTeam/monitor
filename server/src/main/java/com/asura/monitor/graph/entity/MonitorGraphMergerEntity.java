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
 * @date 2017-04-15 18:54:18
 * @since 1.0
 */
public class MonitorGraphMergerEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_graph_merger.graph_id
     * Comment: 
     * @param graphId the value for monitor_graph_merger.graph_id
     */

    private java.lang.Integer graphId;


    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_merger.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_graph_merger.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_graph_merger.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_merger.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_graph_merger.no
     * Comment: 图像顺序
     * @param no the value for monitor_graph_merger.no
     */

    private java.lang.Integer no;


    /**
     * This field corresponds to the database column monitor_graph_merger.to_images
     * Comment: 参考no
     * @param toImages the value for monitor_graph_merger.to_images
     */

    private java.lang.Integer toImages;


    /**
     * This field corresponds to the database column monitor_graph_merger.page
     * Comment: 页面集合名称
     * @param page the value for monitor_graph_merger.page
     */

    private java.lang.String page;


    /**
     * This field corresponds to the database column monitor_graph_merger.images_gson
     * Comment: 
     * @param imagesGson the value for monitor_graph_merger.images_gson
     */

    private java.lang.String imagesGson;


    /**
     * This field corresponds to the database column monitor_graph_merger.graph_id
     * Comment: 
     * @param graphId the value for monitor_graph_merger.graph_id
     */
    public void setGraphId(java.lang.Integer graphId){
       this.graphId = graphId;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_merger.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_graph_merger.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_merger.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.no
     * Comment: 图像顺序
     * @param no the value for monitor_graph_merger.no
     */
    public void setNo(java.lang.Integer no){
       this.no = no;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.to_images
     * Comment: 参考no
     * @param toImages the value for monitor_graph_merger.to_images
     */
    public void setToImages(java.lang.Integer toImages){
       this.toImages = toImages;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.page
     * Comment: 页面集合名称
     * @param page the value for monitor_graph_merger.page
     */
    public void setPage(java.lang.String page){
       this.page = page;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.images_gson
     * Comment: 
     * @param imagesGson the value for monitor_graph_merger.images_gson
     */
    public void setImagesGson(java.lang.String imagesGson){
       this.imagesGson = imagesGson;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.graph_id
     * Comment: 
     * @return the value of monitor_graph_merger.graph_id
     */
     public java.lang.Integer getGraphId() {
        return graphId;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_time
     * Comment: 
     * @return the value of monitor_graph_merger.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_graph_merger.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.description
     * Comment: 描述信息
     * @return the value of monitor_graph_merger.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.no
     * Comment: 图像顺序
     * @return the value of monitor_graph_merger.no
     */
     public java.lang.Integer getNo() {
        return no;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.to_images
     * Comment: 参考no
     * @return the value of monitor_graph_merger.to_images
     */
     public java.lang.Integer getToImages() {
        return toImages;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.page
     * Comment: 页面集合名称
     * @return the value of monitor_graph_merger.page
     */
     public java.lang.String getPage() {
        return page;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.images_gson
     * Comment: 
     * @return the value of monitor_graph_merger.images_gson
     */
     public java.lang.String getImagesGson() {
        return imagesGson;
    }
}
