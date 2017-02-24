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
 * @date 2016-10-30 16:37:47
 * @since 1.0
 */
public class MonitorIndexFromScriptsEntity extends BaseEntity{

    private String fileName;
    private String unit;


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.index_id
     * Comment: 
     * @param indexId the value for monitor_index_from_scripts.index_id
     */

    private java.lang.Integer indexId;


    /**
     * This field corresponds to the database column monitor_index_from_scripts.index_name
     * Comment: 指标名称
     * @param indexName the value for monitor_index_from_scripts.index_name
     */

    private java.lang.String indexName;


    /**
     * This field corresponds to the database column monitor_index_from_scripts.scripts_id
     * Comment: 指标属于的脚本
     * @param scriptsId the value for monitor_index_from_scripts.scripts_id
     */

    private java.lang.Integer scriptsId;


    /**
     * This field corresponds to the database column monitor_index_from_scripts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_index_from_scripts.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_index_from_scripts.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_index_from_scripts.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_index_from_scripts.description
     * Comment:  描述信息
     * @param description the value for monitor_index_from_scripts.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_index_from_scripts.index_id
     * Comment: 
     * @param indexId the value for monitor_index_from_scripts.index_id
     */
    public void setIndexId(java.lang.Integer indexId){
       this.indexId = indexId;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.index_name
     * Comment: 指标名称
     * @param indexName the value for monitor_index_from_scripts.index_name
     */
    public void setIndexName(java.lang.String indexName){
       this.indexName = indexName;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.scripts_id
     * Comment: 指标属于的脚本
     * @param scriptsId the value for monitor_index_from_scripts.scripts_id
     */
    public void setScriptsId(java.lang.Integer scriptsId){
       this.scriptsId = scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_index_from_scripts.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_index_from_scripts.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.description
     * Comment:  描述信息
     * @param description the value for monitor_index_from_scripts.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.index_id
     * Comment: 
     * @return the value of monitor_index_from_scripts.index_id
     */
     public java.lang.Integer getIndexId() {
        return indexId;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.index_name
     * Comment: 指标名称
     * @return the value of monitor_index_from_scripts.index_name
     */
     public java.lang.String getIndexName() {
        return indexName;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.scripts_id
     * Comment: 指标属于的脚本
     * @return the value of monitor_index_from_scripts.scripts_id
     */
     public java.lang.Integer getScriptsId() {
        return scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_index_from_scripts.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_index_from_scripts.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_index_from_scripts.description
     * Comment:  描述信息
     * @return the value of monitor_index_from_scripts.description
     */
     public java.lang.String getDescription() {
        return description;
    }
}
