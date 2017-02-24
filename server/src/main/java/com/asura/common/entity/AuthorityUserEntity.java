package com.asura.common.entity;
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
 * @date 2017-01-31 08:14:01
 * @since 1.0
 */
public class AuthorityUserEntity extends BaseEntity{
    /**
     * This field corresponds to the database column authority_user.user_id
     * Comment: 主键
     * @param userId the value for authority_user.user_id
     */

    private java.lang.Integer userId;


    /**
     * This field corresponds to the database column authority_user.username
     * Comment: 用户名
     * @param username the value for authority_user.username
     */

    private java.lang.String username;


    /**
     * This field corresponds to the database column authority_user.password
     * Comment: 密码
     * @param password the value for authority_user.password
     */

    private java.lang.String password;


    /**
     * This field corresponds to the database column authority_user.last_login
     * Comment: 最近登录时间
     * @param lastLogin the value for authority_user.last_login
     */

    private java.lang.String lastLogin;


    /**
     * This field corresponds to the database column authority_user.login_faild_count
     * Comment: 登录失败次数
     * @param loginFaildCount the value for authority_user.login_faild_count
     */

    private java.lang.Integer loginFaildCount;


    /**
     * This field corresponds to the database column authority_user.user_id
     * Comment: 主键
     * @param userId the value for authority_user.user_id
     */
    public void setUserId(java.lang.Integer userId){
       this.userId = userId;
    }

    /**
     * This field corresponds to the database column authority_user.username
     * Comment: 用户名
     * @param username the value for authority_user.username
     */
    public void setUsername(java.lang.String username){
       this.username = username;
    }

    /**
     * This field corresponds to the database column authority_user.password
     * Comment: 密码
     * @param password the value for authority_user.password
     */
    public void setPassword(java.lang.String password){
       this.password = password;
    }

    /**
     * This field corresponds to the database column authority_user.last_login
     * Comment: 最近登录时间
     * @param lastLogin the value for authority_user.last_login
     */
    public void setLastLogin(java.lang.String lastLogin){
       this.lastLogin = lastLogin;
    }

    /**
     * This field corresponds to the database column authority_user.login_faild_count
     * Comment: 登录失败次数
     * @param loginFaildCount the value for authority_user.login_faild_count
     */
    public void setLoginFaildCount(java.lang.Integer loginFaildCount){
       this.loginFaildCount = loginFaildCount;
    }

    /**
     * This field corresponds to the database column authority_user.user_id
     * Comment: 主键
     * @return the value of authority_user.user_id
     */
     public java.lang.Integer getUserId() {
        return userId;
    }

    /**
     * This field corresponds to the database column authority_user.username
     * Comment: 用户名
     * @return the value of authority_user.username
     */
     public java.lang.String getUsername() {
        return username;
    }

    /**
     * This field corresponds to the database column authority_user.password
     * Comment: 密码
     * @return the value of authority_user.password
     */
     public java.lang.String getPassword() {
        return password;
    }

    /**
     * This field corresponds to the database column authority_user.last_login
     * Comment: 最近登录时间
     * @return the value of authority_user.last_login
     */
     public java.lang.String getLastLogin() {
        return lastLogin;
    }

    /**
     * This field corresponds to the database column authority_user.login_faild_count
     * Comment: 登录失败次数
     * @return the value of authority_user.login_faild_count
     */
     public java.lang.Integer getLoginFaildCount() {
        return loginFaildCount;
    }
}
