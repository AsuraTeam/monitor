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
 * @date 2016-08-20 20:29:04
 * @since 1.0
 */
public class MonitorContactsEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_contacts.contacts_id
     * Comment: 
     * @param contactsId the value for monitor_contacts.contacts_id
     */

    private java.lang.Integer contactsId;


    /**
     * This field corresponds to the database column monitor_contacts.member_name
     * Comment: 
     * @param memberName the value for monitor_contacts.member_name
     */

    private java.lang.String memberName;


    /**
     * This field corresponds to the database column monitor_contacts.mobile
     * Comment: 
     * @param mobile the value for monitor_contacts.mobile
     */

    private java.lang.String mobile;


    /**
     * This field corresponds to the database column monitor_contacts.mail
     * Comment: 
     * @param mail the value for monitor_contacts.mail
     */

    private java.lang.String mail;


    /**
     * This field corresponds to the database column monitor_contacts.no
     * Comment: 工号
     * @param no the value for monitor_contacts.no
     */

    private java.lang.String no;


    /**
     * This field corresponds to the database column monitor_contacts.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_contacts.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_contacts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_contacts.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_contacts.contacts_id
     * Comment: 
     * @param contactsId the value for monitor_contacts.contacts_id
     */
    public void setContactsId(java.lang.Integer contactsId){
       this.contactsId = contactsId;
    }

    /**
     * This field corresponds to the database column monitor_contacts.member_name
     * Comment: 
     * @param memberName the value for monitor_contacts.member_name
     */
    public void setMemberName(java.lang.String memberName){
       this.memberName = memberName;
    }

    /**
     * This field corresponds to the database column monitor_contacts.mobile
     * Comment: 
     * @param mobile the value for monitor_contacts.mobile
     */
    public void setMobile(java.lang.String mobile){
       this.mobile = mobile;
    }

    /**
     * This field corresponds to the database column monitor_contacts.mail
     * Comment: 
     * @param mail the value for monitor_contacts.mail
     */
    public void setMail(java.lang.String mail){
       this.mail = mail;
    }

    /**
     * This field corresponds to the database column monitor_contacts.no
     * Comment: 工号
     * @param no the value for monitor_contacts.no
     */
    public void setNo(java.lang.String no){
       this.no = no;
    }

    /**
     * This field corresponds to the database column monitor_contacts.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_contacts.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_contacts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_contacts.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_contacts.contacts_id
     * Comment: 
     * @return the value of monitor_contacts.contacts_id
     */
     public java.lang.Integer getContactsId() {
        return contactsId;
    }

    /**
     * This field corresponds to the database column monitor_contacts.member_name
     * Comment: 
     * @return the value of monitor_contacts.member_name
     */
     public java.lang.String getMemberName() {
        return memberName;
    }

    /**
     * This field corresponds to the database column monitor_contacts.mobile
     * Comment: 
     * @return the value of monitor_contacts.mobile
     */
     public java.lang.String getMobile() {
        return mobile;
    }

    /**
     * This field corresponds to the database column monitor_contacts.mail
     * Comment: 
     * @return the value of monitor_contacts.mail
     */
     public java.lang.String getMail() {
        return mail;
    }

    /**
     * This field corresponds to the database column monitor_contacts.no
     * Comment: 工号
     * @return the value of monitor_contacts.no
     */
     public java.lang.String getNo() {
        return no;
    }

    /**
     * This field corresponds to the database column monitor_contacts.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_contacts.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_contacts.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_contacts.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }
}
