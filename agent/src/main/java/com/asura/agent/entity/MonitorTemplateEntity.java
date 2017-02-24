package com.asura.agent.entity;


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
 * @date 2016-09-05 16:41:59
 * @since 1.0
 */
public class MonitorTemplateEntity {

    private String description;
    private String itemName;
    private String itemId;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    /**
     * This field corresponds to the database column monitor_template.template_id
     * Comment: 模板ID
     * @param templateId the value for monitor_template.template_id
     */

    private Integer templateId;


    /**
     * This field corresponds to the database column monitor_template.template_name
     * Comment: 模板名称
     * @param templateName the value for monitor_template.template_name
     */

    private String templateName;


    /**
     * This field corresponds to the database column monitor_template.templates
     * Comment: 模板拥有的模板
     * @param templates the value for monitor_template.templates
     */

    private String templates;


    /**
     * This field corresponds to the database column monitor_template.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_template.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_template.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_template.last_modify_user
     */

    private String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_template.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_template.is_valid
     */

    private Integer isValid;


    /**
     * This field corresponds to the database column monitor_template.items
     * Comment: 监控项目,多个用逗号分隔
     * @param items the value for monitor_template.items
     */

    private String items;


    /**
     * This field corresponds to the database column monitor_template.template_id
     * Comment: 模板ID
     * @param templateId the value for monitor_template.template_id
     */
    public void setTemplateId(Integer templateId){
       this.templateId = templateId;
    }

    /**
     * This field corresponds to the database column monitor_template.template_name
     * Comment: 模板名称
     * @param templateName the value for monitor_template.template_name
     */
    public void setTemplateName(String templateName){
       this.templateName = templateName;
    }

    /**
     * This field corresponds to the database column monitor_template.templates
     * Comment: 模板拥有的模板
     * @param templates the value for monitor_template.templates
     */
    public void setTemplates(String templates){
       this.templates = templates;
    }

    /**
     * This field corresponds to the database column monitor_template.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_template.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_template.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_template.last_modify_user
     */
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_template.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_template.is_valid
     */
    public void setIsValid(Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_template.items
     * Comment: 监控项目,多个用逗号分隔
     * @param items the value for monitor_template.items
     */
    public void setItems(String items){
       this.items = items;
    }

    /**
     * This field corresponds to the database column monitor_template.template_id
     * Comment: 模板ID
     * @return the value of monitor_template.template_id
     */
     public Integer getTemplateId() {
        return templateId;
    }

    /**
     * This field corresponds to the database column monitor_template.template_name
     * Comment: 模板名称
     * @return the value of monitor_template.template_name
     */
     public String getTemplateName() {
        return templateName;
    }

    /**
     * This field corresponds to the database column monitor_template.templates
     * Comment: 模板拥有的模板
     * @return the value of monitor_template.templates
     */
     public String getTemplates() {
        return templates;
    }

    /**
     * This field corresponds to the database column monitor_template.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_template.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_template.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_template.last_modify_user
     */
     public String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_template.is_valid
     * Comment: 是否有效
     * @return the value of monitor_template.is_valid
     */
     public Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_template.items
     * Comment: 监控项目,多个用逗号分隔
     * @return the value of monitor_template.items
     */
     public String getItems() {
        return items;
    }
}
