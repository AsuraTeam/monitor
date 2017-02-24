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
 * @date 2017-02-14 14:58:43
 * @since 1.0
 */
public class MonitorImagesCollectionEntity extends BaseEntity{

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.collection_id
     * Comment: 主键
     * @param collectionId the value for monitor_images_collection.collection_id
     */

    private java.lang.Integer collectionId;


    /**
     * This field corresponds to the database column monitor_images_collection.images
     * Comment: 收录图片地址
     * @param images the value for monitor_images_collection.images
     */

    private java.lang.String images;


    /**
     * This field corresponds to the database column monitor_images_collection.create_time
     * Comment: 收录时间
     * @param createTime the value for monitor_images_collection.create_time
     */

    private java.lang.String createTime;


    /**
     * This field corresponds to the database column monitor_images_collection.user
     * Comment: 收录人
     * @param user the value for monitor_images_collection.user
     */

    private java.lang.String user;


    /**
     * This field corresponds to the database column monitor_images_collection.description
     * Comment: 描述信息
     * @param description the value for monitor_images_collection.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_images_collection.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_images_collection.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_images_collection.collection_id
     * Comment: 主键
     * @param collectionId the value for monitor_images_collection.collection_id
     */
    public void setCollectionId(java.lang.Integer collectionId){
       this.collectionId = collectionId;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.images
     * Comment: 收录图片地址
     * @param images the value for monitor_images_collection.images
     */
    public void setImages(java.lang.String images){
       this.images = images;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.create_time
     * Comment: 收录时间
     * @param createTime the value for monitor_images_collection.create_time
     */
    public void setCreateTime(java.lang.String createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.user
     * Comment: 收录人
     * @param user the value for monitor_images_collection.user
     */
    public void setUser(java.lang.String user){
       this.user = user;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.description
     * Comment: 描述信息
     * @param description the value for monitor_images_collection.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_images_collection.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.collection_id
     * Comment: 主键
     * @return the value of monitor_images_collection.collection_id
     */
     public java.lang.Integer getCollectionId() {
        return collectionId;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.images
     * Comment: 收录图片地址
     * @return the value of monitor_images_collection.images
     */
     public java.lang.String getImages() {
        return images;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.create_time
     * Comment: 收录时间
     * @return the value of monitor_images_collection.create_time
     */
     public java.lang.String getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.user
     * Comment: 收录人
     * @return the value of monitor_images_collection.user
     */
     public java.lang.String getUser() {
        return user;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.description
     * Comment: 描述信息
     * @return the value of monitor_images_collection.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_images_collection.last_modify_time
     * Comment: 
     * @return the value of monitor_images_collection.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }
}
