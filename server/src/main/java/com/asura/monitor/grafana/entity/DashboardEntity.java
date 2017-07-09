package com.asura.monitor.grafana.entity;
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
 * @date 2017-07-08 17:33:29
 * @since 1.0
 */
public class DashboardEntity extends BaseEntity{
    /**
     * This field corresponds to the database column dashboard.id
     * Comment: 
     * @param id the value for dashboard.id
     */

    private java.math.BigInteger id;


    /**
     * This field corresponds to the database column dashboard.version
     * Comment: 
     * @param version the value for dashboard.version
     */

    private java.lang.Integer version;


    /**
     * This field corresponds to the database column dashboard.slug
     * Comment: 
     * @param slug the value for dashboard.slug
     */

    private java.lang.String slug;


    /**
     * This field corresponds to the database column dashboard.title
     * Comment: 
     * @param title the value for dashboard.title
     */

    private java.lang.String title;


    /**
     * This field corresponds to the database column dashboard.data
     * Comment: 
     * @param data the value for dashboard.data
     */

    private java.lang.String data;


    /**
     * This field corresponds to the database column dashboard.org_id
     * Comment: 
     * @param orgId the value for dashboard.org_id
     */

    private java.math.BigInteger orgId;


    /**
     * This field corresponds to the database column dashboard.created
     * Comment: 
     * @param created the value for dashboard.created
     */

    private java.sql.Timestamp created;


    /**
     * This field corresponds to the database column dashboard.updated
     * Comment: 
     * @param updated the value for dashboard.updated
     */

    private java.sql.Timestamp updated;


    /**
     * This field corresponds to the database column dashboard.updated_by
     * Comment: 
     * @param updatedBy the value for dashboard.updated_by
     */

    private java.lang.Integer updatedBy;


    /**
     * This field corresponds to the database column dashboard.created_by
     * Comment: 
     * @param createdBy the value for dashboard.created_by
     */

    private java.lang.Integer createdBy;


    /**
     * This field corresponds to the database column dashboard.gnet_id
     * Comment: 
     * @param gnetId the value for dashboard.gnet_id
     */

    private java.math.BigInteger gnetId;


    /**
     * This field corresponds to the database column dashboard.plugin_id
     * Comment: 
     * @param pluginId the value for dashboard.plugin_id
     */

    private java.lang.String pluginId;


    /**
     * This field corresponds to the database column dashboard.id
     * Comment: 
     * @param id the value for dashboard.id
     */
    public void setId(java.math.BigInteger id){
       this.id = id;
    }

    /**
     * This field corresponds to the database column dashboard.version
     * Comment: 
     * @param version the value for dashboard.version
     */
    public void setVersion(java.lang.Integer version){
       this.version = version;
    }

    /**
     * This field corresponds to the database column dashboard.slug
     * Comment: 
     * @param slug the value for dashboard.slug
     */
    public void setSlug(java.lang.String slug){
       this.slug = slug;
    }

    /**
     * This field corresponds to the database column dashboard.title
     * Comment: 
     * @param title the value for dashboard.title
     */
    public void setTitle(java.lang.String title){
       this.title = title;
    }

    /**
     * This field corresponds to the database column dashboard.data
     * Comment: 
     * @param data the value for dashboard.data
     */
    public void setData(java.lang.String data){
       this.data = data;
    }

    /**
     * This field corresponds to the database column dashboard.org_id
     * Comment: 
     * @param orgId the value for dashboard.org_id
     */
    public void setOrgId(java.math.BigInteger orgId){
       this.orgId = orgId;
    }

    /**
     * This field corresponds to the database column dashboard.created
     * Comment: 
     * @param created the value for dashboard.created
     */
    public void setCreated(java.sql.Timestamp created){
       this.created = created;
    }

    /**
     * This field corresponds to the database column dashboard.updated
     * Comment: 
     * @param updated the value for dashboard.updated
     */
    public void setUpdated(java.sql.Timestamp updated){
       this.updated = updated;
    }

    /**
     * This field corresponds to the database column dashboard.updated_by
     * Comment: 
     * @param updatedBy the value for dashboard.updated_by
     */
    public void setUpdatedBy(java.lang.Integer updatedBy){
       this.updatedBy = updatedBy;
    }

    /**
     * This field corresponds to the database column dashboard.created_by
     * Comment: 
     * @param createdBy the value for dashboard.created_by
     */
    public void setCreatedBy(java.lang.Integer createdBy){
       this.createdBy = createdBy;
    }

    /**
     * This field corresponds to the database column dashboard.gnet_id
     * Comment: 
     * @param gnetId the value for dashboard.gnet_id
     */
    public void setGnetId(java.math.BigInteger gnetId){
       this.gnetId = gnetId;
    }

    /**
     * This field corresponds to the database column dashboard.plugin_id
     * Comment: 
     * @param pluginId the value for dashboard.plugin_id
     */
    public void setPluginId(java.lang.String pluginId){
       this.pluginId = pluginId;
    }

    /**
     * This field corresponds to the database column dashboard.id
     * Comment: 
     * @return the value of dashboard.id
     */
     public java.math.BigInteger getId() {
        return id;
    }

    /**
     * This field corresponds to the database column dashboard.version
     * Comment: 
     * @return the value of dashboard.version
     */
     public java.lang.Integer getVersion() {
        return version;
    }

    /**
     * This field corresponds to the database column dashboard.slug
     * Comment: 
     * @return the value of dashboard.slug
     */
     public java.lang.String getSlug() {
        return slug;
    }

    /**
     * This field corresponds to the database column dashboard.title
     * Comment: 
     * @return the value of dashboard.title
     */
     public java.lang.String getTitle() {
        return title;
    }

    /**
     * This field corresponds to the database column dashboard.data
     * Comment: 
     * @return the value of dashboard.data
     */
     public java.lang.String getData() {
        return data;
    }

    /**
     * This field corresponds to the database column dashboard.org_id
     * Comment: 
     * @return the value of dashboard.org_id
     */
     public java.math.BigInteger getOrgId() {
        return orgId;
    }

    /**
     * This field corresponds to the database column dashboard.created
     * Comment: 
     * @return the value of dashboard.created
     */
     public java.sql.Timestamp getCreated() {
        return created;
    }

    /**
     * This field corresponds to the database column dashboard.updated
     * Comment: 
     * @return the value of dashboard.updated
     */
     public java.sql.Timestamp getUpdated() {
        return updated;
    }

    /**
     * This field corresponds to the database column dashboard.updated_by
     * Comment: 
     * @return the value of dashboard.updated_by
     */
     public java.lang.Integer getUpdatedBy() {
        return updatedBy;
    }

    /**
     * This field corresponds to the database column dashboard.created_by
     * Comment: 
     * @return the value of dashboard.created_by
     */
     public java.lang.Integer getCreatedBy() {
        return createdBy;
    }

    /**
     * This field corresponds to the database column dashboard.gnet_id
     * Comment: 
     * @return the value of dashboard.gnet_id
     */
     public java.math.BigInteger getGnetId() {
        return gnetId;
    }

    /**
     * This field corresponds to the database column dashboard.plugin_id
     * Comment: 
     * @return the value of dashboard.plugin_id
     */
     public java.lang.String getPluginId() {
        return pluginId;
    }
}
