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
 * @date 2017-01-09 14:22:59
 * @since 1.0
 */
public class AuthorityPagesEntity extends BaseEntity{

    private String modelName;

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    /**
     * This field corresponds to the database column authority_pages.pages_id
     * Comment: 主键ID
     * @param pagesId the value for authority_pages.pages_id
     */

    private java.lang.Integer pagesId;


    /**
     * This field corresponds to the database column authority_pages.pages_url
     * Comment: 访问页面URL
     * @param pagesUrl the value for authority_pages.pages_url
     */

    private java.lang.String pagesUrl;


    /**
     * This field corresponds to the database column authority_pages.pages_name
     * Comment: 菜单名字
     * @param pagesName the value for authority_pages.pages_name
     */

    private java.lang.String pagesName;


    /**
     * This field corresponds to the database column authority_pages.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for authority_pages.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column authority_pages.create_time
     * Comment: 创建时间
     * @param createTime the value for authority_pages.create_time
     */

    private java.math.BigInteger createTime;


    /**
     * This field corresponds to the database column authority_pages.last_modify_time
     * Comment: 最后修改时间
     * @param lastModifyTime the value for authority_pages.last_modify_time
     */

    private java.math.BigInteger lastModifyTime;


    /**
     * This field corresponds to the database column authority_pages.is_valid
     * Comment: 是否有效
     * @param isValid the value for authority_pages.is_valid
     */

    private java.lang.Integer isValid;


    /**
     * This field corresponds to the database column authority_pages.pages_id
     * Comment: 主键ID
     * @param pagesId the value for authority_pages.pages_id
     */
    public void setPagesId(java.lang.Integer pagesId){
       this.pagesId = pagesId;
    }

    /**
     * This field corresponds to the database column authority_pages.pages_url
     * Comment: 访问页面URL
     * @param pagesUrl the value for authority_pages.pages_url
     */
    public void setPagesUrl(java.lang.String pagesUrl){
       this.pagesUrl = pagesUrl;
    }

    /**
     * This field corresponds to the database column authority_pages.pages_name
     * Comment: 菜单名字
     * @param pagesName the value for authority_pages.pages_name
     */
    public void setPagesName(java.lang.String pagesName){
       this.pagesName = pagesName;
    }

    /**
     * This field corresponds to the database column authority_pages.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for authority_pages.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column authority_pages.create_time
     * Comment: 创建时间
     * @param createTime the value for authority_pages.create_time
     */
    public void setCreateTime(java.math.BigInteger createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column authority_pages.last_modify_time
     * Comment: 最后修改时间
     * @param lastModifyTime the value for authority_pages.last_modify_time
     */
    public void setLastModifyTime(java.math.BigInteger lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column authority_pages.is_valid
     * Comment: 是否有效
     * @param isValid the value for authority_pages.is_valid
     */
    public void setIsValid(java.lang.Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column authority_pages.pages_id
     * Comment: 主键ID
     * @return the value of authority_pages.pages_id
     */
     public java.lang.Integer getPagesId() {
        return pagesId;
    }

    /**
     * This field corresponds to the database column authority_pages.pages_url
     * Comment: 访问页面URL
     * @return the value of authority_pages.pages_url
     */
     public java.lang.String getPagesUrl() {
        return pagesUrl;
    }

    /**
     * This field corresponds to the database column authority_pages.pages_name
     * Comment: 菜单名字
     * @return the value of authority_pages.pages_name
     */
     public java.lang.String getPagesName() {
        return pagesName;
    }

    /**
     * This field corresponds to the database column authority_pages.last_modify_user
     * Comment: 最近修改用户
     * @return the value of authority_pages.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column authority_pages.create_time
     * Comment: 创建时间
     * @return the value of authority_pages.create_time
     */
     public java.math.BigInteger getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column authority_pages.last_modify_time
     * Comment: 最后修改时间
     * @return the value of authority_pages.last_modify_time
     */
     public java.math.BigInteger getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column authority_pages.is_valid
     * Comment: 是否有效
     * @return the value of authority_pages.is_valid
     */
     public java.lang.Integer getIsValid() {
        return isValid;
    }
}
