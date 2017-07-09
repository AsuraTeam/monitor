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
 * @date 2017-07-08 17:32:01
 * @since 1.0
 */
public class DataSourceEntity extends BaseEntity{
    /**
     * This field corresponds to the database column data_source.id
     * Comment: 
     * @param id the value for data_source.id
     */

    private java.math.BigInteger id;


    /**
     * This field corresponds to the database column data_source.org_id
     * Comment: 
     * @param orgId the value for data_source.org_id
     */

    private java.math.BigInteger orgId;


    /**
     * This field corresponds to the database column data_source.version
     * Comment: 
     * @param version the value for data_source.version
     */

    private java.lang.Integer version;


    /**
     * This field corresponds to the database column data_source.type
     * Comment: 
     * @param type the value for data_source.type
     */

    private java.lang.String type;


    /**
     * This field corresponds to the database column data_source.name
     * Comment: 
     * @param name the value for data_source.name
     */

    private java.lang.String name;


    /**
     * This field corresponds to the database column data_source.access
     * Comment: 
     * @param access the value for data_source.access
     */

    private java.lang.String access;


    /**
     * This field corresponds to the database column data_source.url
     * Comment: 
     * @param url the value for data_source.url
     */

    private java.lang.String url;


    /**
     * This field corresponds to the database column data_source.password
     * Comment: 
     * @param password the value for data_source.password
     */

    private java.lang.String password;


    /**
     * This field corresponds to the database column data_source.user
     * Comment: 
     * @param user the value for data_source.user
     */

    private java.lang.String user;


    /**
     * This field corresponds to the database column data_source.database
     * Comment: 
     * @param database the value for data_source.database
     */

    private java.lang.String database;


    /**
     * This field corresponds to the database column data_source.basic_auth
     * Comment: 
     * @param basicAuth the value for data_source.basic_auth
     */

    private java.lang.Integer basicAuth;


    /**
     * This field corresponds to the database column data_source.basic_auth_user
     * Comment: 
     * @param basicAuthUser the value for data_source.basic_auth_user
     */

    private java.lang.String basicAuthUser;


    /**
     * This field corresponds to the database column data_source.basic_auth_password
     * Comment: 
     * @param basicAuthPassword the value for data_source.basic_auth_password
     */

    private java.lang.String basicAuthPassword;


    /**
     * This field corresponds to the database column data_source.is_default
     * Comment: 
     * @param isDefault the value for data_source.is_default
     */

    private java.lang.Integer isDefault;


    /**
     * This field corresponds to the database column data_source.json_data
     * Comment: 
     * @param jsonData the value for data_source.json_data
     */

    private java.lang.String jsonData;


    /**
     * This field corresponds to the database column data_source.created
     * Comment: 
     * @param created the value for data_source.created
     */

    private java.sql.Timestamp created;


    /**
     * This field corresponds to the database column data_source.updated
     * Comment: 
     * @param updated the value for data_source.updated
     */

    private java.sql.Timestamp updated;


    /**
     * This field corresponds to the database column data_source.with_credentials
     * Comment: 
     * @param withCredentials the value for data_source.with_credentials
     */

    private java.lang.Integer withCredentials;


    /**
     * This field corresponds to the database column data_source.secure_json_data
     * Comment: 
     * @param secureJsonData the value for data_source.secure_json_data
     */

    private java.lang.String secureJsonData;


    /**
     * This field corresponds to the database column data_source.id
     * Comment: 
     * @param id the value for data_source.id
     */
    public void setId(java.math.BigInteger id){
       this.id = id;
    }

    /**
     * This field corresponds to the database column data_source.org_id
     * Comment: 
     * @param orgId the value for data_source.org_id
     */
    public void setOrgId(java.math.BigInteger orgId){
       this.orgId = orgId;
    }

    /**
     * This field corresponds to the database column data_source.version
     * Comment: 
     * @param version the value for data_source.version
     */
    public void setVersion(java.lang.Integer version){
       this.version = version;
    }

    /**
     * This field corresponds to the database column data_source.type
     * Comment: 
     * @param type the value for data_source.type
     */
    public void setType(java.lang.String type){
       this.type = type;
    }

    /**
     * This field corresponds to the database column data_source.name
     * Comment: 
     * @param name the value for data_source.name
     */
    public void setName(java.lang.String name){
       this.name = name;
    }

    /**
     * This field corresponds to the database column data_source.access
     * Comment: 
     * @param access the value for data_source.access
     */
    public void setAccess(java.lang.String access){
       this.access = access;
    }

    /**
     * This field corresponds to the database column data_source.url
     * Comment: 
     * @param url the value for data_source.url
     */
    public void setUrl(java.lang.String url){
       this.url = url;
    }

    /**
     * This field corresponds to the database column data_source.password
     * Comment: 
     * @param password the value for data_source.password
     */
    public void setPassword(java.lang.String password){
       this.password = password;
    }

    /**
     * This field corresponds to the database column data_source.user
     * Comment: 
     * @param user the value for data_source.user
     */
    public void setUser(java.lang.String user){
       this.user = user;
    }

    /**
     * This field corresponds to the database column data_source.database
     * Comment: 
     * @param database the value for data_source.database
     */
    public void setDatabase(java.lang.String database){
       this.database = database;
    }

    /**
     * This field corresponds to the database column data_source.basic_auth
     * Comment: 
     * @param basicAuth the value for data_source.basic_auth
     */
    public void setBasicAuth(java.lang.Integer basicAuth){
       this.basicAuth = basicAuth;
    }

    /**
     * This field corresponds to the database column data_source.basic_auth_user
     * Comment: 
     * @param basicAuthUser the value for data_source.basic_auth_user
     */
    public void setBasicAuthUser(java.lang.String basicAuthUser){
       this.basicAuthUser = basicAuthUser;
    }

    /**
     * This field corresponds to the database column data_source.basic_auth_password
     * Comment: 
     * @param basicAuthPassword the value for data_source.basic_auth_password
     */
    public void setBasicAuthPassword(java.lang.String basicAuthPassword){
       this.basicAuthPassword = basicAuthPassword;
    }

    /**
     * This field corresponds to the database column data_source.is_default
     * Comment: 
     * @param isDefault the value for data_source.is_default
     */
    public void setIsDefault(java.lang.Integer isDefault){
       this.isDefault = isDefault;
    }

    /**
     * This field corresponds to the database column data_source.json_data
     * Comment: 
     * @param jsonData the value for data_source.json_data
     */
    public void setJsonData(java.lang.String jsonData){
       this.jsonData = jsonData;
    }

    /**
     * This field corresponds to the database column data_source.created
     * Comment: 
     * @param created the value for data_source.created
     */
    public void setCreated(java.sql.Timestamp created){
       this.created = created;
    }

    /**
     * This field corresponds to the database column data_source.updated
     * Comment: 
     * @param updated the value for data_source.updated
     */
    public void setUpdated(java.sql.Timestamp updated){
       this.updated = updated;
    }

    /**
     * This field corresponds to the database column data_source.with_credentials
     * Comment: 
     * @param withCredentials the value for data_source.with_credentials
     */
    public void setWithCredentials(java.lang.Integer withCredentials){
       this.withCredentials = withCredentials;
    }

    /**
     * This field corresponds to the database column data_source.secure_json_data
     * Comment: 
     * @param secureJsonData the value for data_source.secure_json_data
     */
    public void setSecureJsonData(java.lang.String secureJsonData){
       this.secureJsonData = secureJsonData;
    }

    /**
     * This field corresponds to the database column data_source.id
     * Comment: 
     * @return the value of data_source.id
     */
     public java.math.BigInteger getId() {
        return id;
    }

    /**
     * This field corresponds to the database column data_source.org_id
     * Comment: 
     * @return the value of data_source.org_id
     */
     public java.math.BigInteger getOrgId() {
        return orgId;
    }

    /**
     * This field corresponds to the database column data_source.version
     * Comment: 
     * @return the value of data_source.version
     */
     public java.lang.Integer getVersion() {
        return version;
    }

    /**
     * This field corresponds to the database column data_source.type
     * Comment: 
     * @return the value of data_source.type
     */
     public java.lang.String getType() {
        return type;
    }

    /**
     * This field corresponds to the database column data_source.name
     * Comment: 
     * @return the value of data_source.name
     */
     public java.lang.String getName() {
        return name;
    }

    /**
     * This field corresponds to the database column data_source.access
     * Comment: 
     * @return the value of data_source.access
     */
     public java.lang.String getAccess() {
        return access;
    }

    /**
     * This field corresponds to the database column data_source.url
     * Comment: 
     * @return the value of data_source.url
     */
     public java.lang.String getUrl() {
        return url;
    }

    /**
     * This field corresponds to the database column data_source.password
     * Comment: 
     * @return the value of data_source.password
     */
     public java.lang.String getPassword() {
        return password;
    }

    /**
     * This field corresponds to the database column data_source.user
     * Comment: 
     * @return the value of data_source.user
     */
     public java.lang.String getUser() {
        return user;
    }

    /**
     * This field corresponds to the database column data_source.database
     * Comment: 
     * @return the value of data_source.database
     */
     public java.lang.String getDatabase() {
        return database;
    }

    /**
     * This field corresponds to the database column data_source.basic_auth
     * Comment: 
     * @return the value of data_source.basic_auth
     */
     public java.lang.Integer getBasicAuth() {
        return basicAuth;
    }

    /**
     * This field corresponds to the database column data_source.basic_auth_user
     * Comment: 
     * @return the value of data_source.basic_auth_user
     */
     public java.lang.String getBasicAuthUser() {
        return basicAuthUser;
    }

    /**
     * This field corresponds to the database column data_source.basic_auth_password
     * Comment: 
     * @return the value of data_source.basic_auth_password
     */
     public java.lang.String getBasicAuthPassword() {
        return basicAuthPassword;
    }

    /**
     * This field corresponds to the database column data_source.is_default
     * Comment: 
     * @return the value of data_source.is_default
     */
     public java.lang.Integer getIsDefault() {
        return isDefault;
    }

    /**
     * This field corresponds to the database column data_source.json_data
     * Comment: 
     * @return the value of data_source.json_data
     */
     public java.lang.String getJsonData() {
        return jsonData;
    }

    /**
     * This field corresponds to the database column data_source.created
     * Comment: 
     * @return the value of data_source.created
     */
     public java.sql.Timestamp getCreated() {
        return created;
    }

    /**
     * This field corresponds to the database column data_source.updated
     * Comment: 
     * @return the value of data_source.updated
     */
     public java.sql.Timestamp getUpdated() {
        return updated;
    }

    /**
     * This field corresponds to the database column data_source.with_credentials
     * Comment: 
     * @return the value of data_source.with_credentials
     */
     public java.lang.Integer getWithCredentials() {
        return withCredentials;
    }

    /**
     * This field corresponds to the database column data_source.secure_json_data
     * Comment: 
     * @return the value of data_source.secure_json_data
     */
     public java.lang.String getSecureJsonData() {
        return secureJsonData;
    }
}
