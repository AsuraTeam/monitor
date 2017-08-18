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
 * @date 2017-08-01 18:10:41
 * @since 1.0
 */
public class MonitorTopImagesEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_top_images.images_id
     * Comment: ä¸»é”®
     * @param imagesId the value for monitor_top_images.images_id
     */

    private java.lang.Integer imagesId;


    /**
     * This field corresponds to the database column monitor_top_images.name
     * Comment: 
     * @param name the value for monitor_top_images.name
     */

    private java.lang.String name;


    /**
     * This field corresponds to the database column monitor_top_images.description
     * Comment: æè¿°ä¿¡æ¯
     * @param description the value for monitor_top_images.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_top_images.create_time
     * Comment: åˆ›å»ºæ—¶é—´
     * @param createTime the value for monitor_top_images.create_time
     */

    private java.lang.String createTime;


    /**
     * This field corresponds to the database column monitor_top_images.create_user
     * Comment: åˆ›å»ºç”¨æˆ·
     * @param createUser the value for monitor_top_images.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column monitor_top_images.images_id
     * Comment: ä¸»é”®
     * @param imagesId the value for monitor_top_images.images_id
     */
    public void setImagesId(java.lang.Integer imagesId){
       this.imagesId = imagesId;
    }

    /**
     * This field corresponds to the database column monitor_top_images.name
     * Comment: 
     * @param name the value for monitor_top_images.name
     */
    public void setName(java.lang.String name){
       this.name = name;
    }

    /**
     * This field corresponds to the database column monitor_top_images.description
     * Comment: æè¿°ä¿¡æ¯
     * @param description the value for monitor_top_images.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_top_images.create_time
     * Comment: åˆ›å»ºæ—¶é—´
     * @param createTime the value for monitor_top_images.create_time
     */
    public void setCreateTime(java.lang.String createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column monitor_top_images.create_user
     * Comment: åˆ›å»ºç”¨æˆ·
     * @param createUser the value for monitor_top_images.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column monitor_top_images.images_id
     * Comment: ä¸»é”®
     * @return the value of monitor_top_images.images_id
     */
     public java.lang.Integer getImagesId() {
        return imagesId;
    }

    /**
     * This field corresponds to the database column monitor_top_images.name
     * Comment: 
     * @return the value of monitor_top_images.name
     */
     public java.lang.String getName() {
        return name;
    }

    /**
     * This field corresponds to the database column monitor_top_images.description
     * Comment: æè¿°ä¿¡æ¯
     * @return the value of monitor_top_images.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_top_images.create_time
     * Comment: åˆ›å»ºæ—¶é—´
     * @return the value of monitor_top_images.create_time
     */
     public java.lang.String getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column monitor_top_images.create_user
     * Comment: åˆ›å»ºç”¨æˆ·
     * @return the value of monitor_top_images.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }
}
