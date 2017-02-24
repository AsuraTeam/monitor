package com.asura.authority.entity;
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
 * @date 2017-01-09 14:22:24
 * @since 1.0
 */
public class AuthorityPermissionEntity extends BaseEntity{

    private String pagesName;
    private String pagesUrl;
    private String user;

    public String getPagesName() {
        return pagesName;
    }

    public void setPagesName(String pagesName) {
        this.pagesName = pagesName;
    }

    public String getPagesUrl() {
        return pagesUrl;
    }

    public void setPagesUrl(String pagesUrl) {
        this.pagesUrl = pagesUrl;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    /**
     * This field corresponds to the database column authority_permission.permission_id
     * Comment: 主键
     * @param permissionId the value for authority_permission.permission_id
     */

    private java.lang.Integer permissionId;


    /**
     * This field corresponds to the database column authority_permission.pages_id
     * Comment: 页面id参考pages
     * @param pagesId the value for authority_permission.pages_id
     */

    private java.lang.Integer pagesId;


    /**
     * This field corresponds to the database column authority_permission.last_modify_user
     * Comment: 最近修改时间
     * @param lastModifyUser the value for authority_permission.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column authority_permission.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for authority_permission.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column authority_permission.is_valid
     * Comment: 是否有效
     * @param isValid the value for authority_permission.is_valid
     */

    private java.lang.Integer isValid;


    /**
     * This field corresponds to the database column authority_permission.permission_id
     * Comment: 主键
     * @param permissionId the value for authority_permission.permission_id
     */
    public void setPermissionId(java.lang.Integer permissionId){
       this.permissionId = permissionId;
    }

    /**
     * This field corresponds to the database column authority_permission.pages_id
     * Comment: 页面id参考pages
     * @param pagesId the value for authority_permission.pages_id
     */
    public void setPagesId(java.lang.Integer pagesId){
       this.pagesId = pagesId;
    }

    /**
     * This field corresponds to the database column authority_permission.last_modify_user
     * Comment: 最近修改时间
     * @param lastModifyUser the value for authority_permission.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column authority_permission.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for authority_permission.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column authority_permission.is_valid
     * Comment: 是否有效
     * @param isValid the value for authority_permission.is_valid
     */
    public void setIsValid(java.lang.Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column authority_permission.permission_id
     * Comment: 主键
     * @return the value of authority_permission.permission_id
     */
     public java.lang.Integer getPermissionId() {
        return permissionId;
    }

    /**
     * This field corresponds to the database column authority_permission.pages_id
     * Comment: 页面id参考pages
     * @return the value of authority_permission.pages_id
     */
     public java.lang.Integer getPagesId() {
        return pagesId;
    }

    /**
     * This field corresponds to the database column authority_permission.last_modify_user
     * Comment: 最近修改时间
     * @return the value of authority_permission.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column authority_permission.last_modify_time
     * Comment: 
     * @return the value of authority_permission.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column authority_permission.is_valid
     * Comment: 是否有效
     * @return the value of authority_permission.is_valid
     */
     public java.lang.Integer getIsValid() {
        return isValid;
    }
}
