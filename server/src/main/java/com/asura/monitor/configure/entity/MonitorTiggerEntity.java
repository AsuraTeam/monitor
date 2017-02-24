package com.asura.monitor.configure.entity;
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
 * @date 2016-08-20 09:51:18
 * @since 1.0
 */
public class MonitorTiggerEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_tigger.tigger_id
     * Comment: 
     * @param tiggerId the value for monitor_tigger.tigger_id
     */

    private java.lang.Integer tiggerId;


    /**
     * This field corresponds to the database column monitor_tigger.tigger_name
     * Comment: 触发器名字
     * @param tiggerName the value for monitor_tigger.tigger_name
     */

    private java.lang.String tiggerName;


    /**
     * This field corresponds to the database column monitor_tigger.expression
     * Comment: 表达式,监控项  > == < memory.totle > 100 
     * @param expression the value for monitor_tigger.expression
     */

    private java.lang.String expression;


    /**
     * This field corresponds to the database column monitor_tigger.is_valid
     * Comment: 是否有效,0无效,1有效
     * @param isValid the value for monitor_tigger.is_valid
     */

    private java.lang.Integer isValid;


    /**
     * This field corresponds to the database column monitor_tigger.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_tigger.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_tigger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_tigger.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_tigger.severity_id
     * Comment: 参考报警级别ID
     * @param severityId the value for monitor_tigger.severity_id
     */

    private java.lang.Integer severityId;


    /**
     * This field corresponds to the database column monitor_tigger.template_id
     * Comment: 所属模板ID，参考template_id
     * @param templateId the value for monitor_tigger.template_id
     */

    private java.lang.Integer templateId;


    /**
     * This field corresponds to the database column monitor_tigger.tigger_id
     * Comment: 
     * @param tiggerId the value for monitor_tigger.tigger_id
     */
    public void setTiggerId(java.lang.Integer tiggerId){
       this.tiggerId = tiggerId;
    }

    /**
     * This field corresponds to the database column monitor_tigger.tigger_name
     * Comment: 触发器名字
     * @param tiggerName the value for monitor_tigger.tigger_name
     */
    public void setTiggerName(java.lang.String tiggerName){
       this.tiggerName = tiggerName;
    }

    /**
     * This field corresponds to the database column monitor_tigger.expression
     * Comment: 表达式,监控项  > == < memory.totle > 100 
     * @param expression the value for monitor_tigger.expression
     */
    public void setExpression(java.lang.String expression){
       this.expression = expression;
    }

    /**
     * This field corresponds to the database column monitor_tigger.is_valid
     * Comment: 是否有效,0无效,1有效
     * @param isValid the value for monitor_tigger.is_valid
     */
    public void setIsValid(java.lang.Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_tigger.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_tigger.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_tigger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_tigger.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_tigger.severity_id
     * Comment: 参考报警级别ID
     * @param severityId the value for monitor_tigger.severity_id
     */
    public void setSeverityId(java.lang.Integer severityId){
       this.severityId = severityId;
    }

    /**
     * This field corresponds to the database column monitor_tigger.template_id
     * Comment: 所属模板ID，参考template_id
     * @param templateId the value for monitor_tigger.template_id
     */
    public void setTemplateId(java.lang.Integer templateId){
       this.templateId = templateId;
    }

    /**
     * This field corresponds to the database column monitor_tigger.tigger_id
     * Comment: 
     * @return the value of monitor_tigger.tigger_id
     */
     public java.lang.Integer getTiggerId() {
        return tiggerId;
    }

    /**
     * This field corresponds to the database column monitor_tigger.tigger_name
     * Comment: 触发器名字
     * @return the value of monitor_tigger.tigger_name
     */
     public java.lang.String getTiggerName() {
        return tiggerName;
    }

    /**
     * This field corresponds to the database column monitor_tigger.expression
     * Comment: 表达式,监控项  > == < memory.totle > 100 
     * @return the value of monitor_tigger.expression
     */
     public java.lang.String getExpression() {
        return expression;
    }

    /**
     * This field corresponds to the database column monitor_tigger.is_valid
     * Comment: 是否有效,0无效,1有效
     * @return the value of monitor_tigger.is_valid
     */
     public java.lang.Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_tigger.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_tigger.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_tigger.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_tigger.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_tigger.severity_id
     * Comment: 参考报警级别ID
     * @return the value of monitor_tigger.severity_id
     */
     public java.lang.Integer getSeverityId() {
        return severityId;
    }

    /**
     * This field corresponds to the database column monitor_tigger.template_id
     * Comment: 所属模板ID，参考template_id
     * @return the value of monitor_tigger.template_id
     */
     public java.lang.Integer getTemplateId() {
        return templateId;
    }
}
