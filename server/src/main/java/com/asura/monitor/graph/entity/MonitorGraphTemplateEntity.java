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
 * @date 2017-04-21 10:55:00
 * @since 1.0
 */
public class MonitorGraphTemplateEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_graph_template.template_id
     * Comment: 
     * @param templateId the value for monitor_graph_template.template_id
     */

    private java.lang.Integer templateId;


    /**
     * This field corresponds to the database column monitor_graph_template.graph_ids
     * Comment: 图像ID
     * @param graphIds the value for monitor_graph_template.graph_ids
     */

    private java.lang.String graphIds;


    /**
     * This field corresponds to the database column monitor_graph_template.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_template.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_graph_template.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_template.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_graph_template.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_graph_template.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_graph_template.page
     * Comment: 页面名称
     * @param page the value for monitor_graph_template.page
     */

    private java.lang.String page;


    /**
     * This field corresponds to the database column monitor_graph_template.gson_data
     * Comment: 
     * @param gsonData the value for monitor_graph_template.gson_data
     */

    private java.lang.String gsonData;


    /**
     * This field corresponds to the database column monitor_graph_template.template_id
     * Comment: 
     * @param templateId the value for monitor_graph_template.template_id
     */
    public void setTemplateId(java.lang.Integer templateId){
       this.templateId = templateId;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.graph_ids
     * Comment: 图像ID
     * @param graphIds the value for monitor_graph_template.graph_ids
     */
    public void setGraphIds(java.lang.String graphIds){
       this.graphIds = graphIds;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_template.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_template.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_graph_template.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.page
     * Comment: 页面名称
     * @param page the value for monitor_graph_template.page
     */
    public void setPage(java.lang.String page){
       this.page = page;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.gson_data
     * Comment: 
     * @param gsonData the value for monitor_graph_template.gson_data
     */
    public void setGsonData(java.lang.String gsonData){
       this.gsonData = gsonData;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.template_id
     * Comment: 
     * @return the value of monitor_graph_template.template_id
     */
     public java.lang.Integer getTemplateId() {
        return templateId;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.graph_ids
     * Comment: 图像ID
     * @return the value of monitor_graph_template.graph_ids
     */
     public java.lang.String getGraphIds() {
        return graphIds;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.last_modify_time
     * Comment: 
     * @return the value of monitor_graph_template.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.description
     * Comment: 描述信息
     * @return the value of monitor_graph_template.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_graph_template.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.page
     * Comment: 页面名称
     * @return the value of monitor_graph_template.page
     */
     public java.lang.String getPage() {
        return page;
    }

    /**
     * This field corresponds to the database column monitor_graph_template.gson_data
     * Comment: 
     * @return the value of monitor_graph_template.gson_data
     */
     public java.lang.String getGsonData() {
        return gsonData;
    }
}
