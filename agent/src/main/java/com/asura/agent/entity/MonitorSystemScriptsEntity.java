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
 * @date 2016-11-05 20:19:24
 * @since 1.0
 */
public class MonitorSystemScriptsEntity {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    /**
     * This field corresponds to the database column monitor_system_scripts.scripts_id
     * Comment: 主键
     * @param scriptsId the value for monitor_system_scripts.scripts_id
     */

    private Integer scriptsId;


    /**
     * This field corresponds to the database column monitor_system_scripts.scripts_content
     * Comment: 脚本内容
     * @param scriptsContent the value for monitor_system_scripts.scripts_content
     */

    private String scriptsContent;


    /**
     * This field corresponds to the database column monitor_system_scripts.os_name
     * Comment: 操作系统类型
     * @param osName the value for monitor_system_scripts.os_name
     */

    private String osName;


    /**
     * This field corresponds to the database column monitor_system_scripts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_system_scripts.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_system_scripts.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_system_scripts.last_modify_user
     */

    private String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_system_scripts.scripts_id
     * Comment: 主键
     * @param scriptsId the value for monitor_system_scripts.scripts_id
     */
    public void setScriptsId(Integer scriptsId){
       this.scriptsId = scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.scripts_content
     * Comment: 脚本内容
     * @param scriptsContent the value for monitor_system_scripts.scripts_content
     */
    public void setScriptsContent(String scriptsContent){
       this.scriptsContent = scriptsContent;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.os_name
     * Comment: 操作系统类型
     * @param osName the value for monitor_system_scripts.os_name
     */
    public void setOsName(String osName){
       this.osName = osName;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_system_scripts.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_system_scripts.last_modify_user
     */
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.scripts_id
     * Comment: 主键
     * @return the value of monitor_system_scripts.scripts_id
     */
     public Integer getScriptsId() {
        return scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.scripts_content
     * Comment: 脚本内容
     * @return the value of monitor_system_scripts.scripts_content
     */
     public String getScriptsContent() {
        return scriptsContent;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.os_name
     * Comment: 操作系统类型
     * @return the value of monitor_system_scripts.os_name
     */
     public String getOsName() {
        return osName;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_system_scripts.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_system_scripts.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_system_scripts.last_modify_user
     */
     public String getLastModifyUser() {
        return lastModifyUser;
    }
}
