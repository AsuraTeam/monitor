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
 * @date 2016-10-05 18:15:34
 * @since 1.0
 */
public class MonitorMessageChannelEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_message_channel.channel_id
     * Comment: 主键
     * @param channelId the value for monitor_message_channel.channel_id
     */

    private java.lang.Integer channelId;


    /**
     * This field corresponds to the database column monitor_message_channel.channel_tp
     * Comment: 
     * @param channelTp the value for monitor_message_channel.channel_tp
     */

    private java.lang.String channelTp;


    /**
     * This field corresponds to the database column monitor_message_channel.channel_script
     * Comment: 发送脚本
     * @param channelScript the value for monitor_message_channel.channel_script
     */

    private java.lang.String channelScript;


    /**
     * This field corresponds to the database column monitor_message_channel.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_message_channel.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_message_channel.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_message_channel.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_message_channel.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_message_channel.is_valid
     */

    private java.lang.Integer isValid;


    /**
     * This field corresponds to the database column monitor_message_channel.smtp_server
     * Comment: 发送有服务器地址
     * @param smtpServer the value for monitor_message_channel.smtp_server
     */

    private java.lang.String smtpServer;


    /**
     * This field corresponds to the database column monitor_message_channel.smtp_user
     * Comment: 发送有服务器用户名
     * @param smtpUser the value for monitor_message_channel.smtp_user
     */

    private java.lang.String smtpUser;


    /**
     * This field corresponds to the database column monitor_message_channel.smtp_pass
     * Comment: 发送有服务器用户名密码
     * @param smtpPass the value for monitor_message_channel.smtp_pass
     */

    private java.lang.String smtpPass;


    /**
     * This field corresponds to the database column monitor_message_channel.smtp_auth
     * Comment: 发送服务器是否使用用户名密码
     * @param smtpAuth the value for monitor_message_channel.smtp_auth
     */

    private java.lang.String smtpAuth;


    /**
     * This field corresponds to the database column monitor_message_channel.mail_template
     * Comment: 邮件模板
     * @param mailTemplate the value for monitor_message_channel.mail_template
     */

    private java.lang.String mailTemplate;


    /**
     * This field corresponds to the database column monitor_message_channel.smtp_sender
     * Comment: 向用户显示的邮件地址
     * @param smtpSender the value for monitor_message_channel.smtp_sender
     */

    private java.lang.String smtpSender;


    /**
     * This field corresponds to the database column monitor_message_channel.ding_corp_id
     * Comment: 钉钉的CorpID
     * @param dingCorpId the value for monitor_message_channel.ding_corp_id
     */

    private java.lang.String dingCorpId;


    /**
     * This field corresponds to the database column monitor_message_channel.ding_secret_id
     * Comment: 钉钉的SecretId
     * @param dingSecretId the value for monitor_message_channel.ding_secret_id
     */

    private java.lang.String dingSecretId;


    /**
     * This field corresponds to the database column monitor_message_channel.ding_agent_id
     * Comment: 钉钉的agentid
     * @param dingAgentId the value for monitor_message_channel.ding_agent_id
     */

    private java.math.BigInteger dingAgentId;


    /**
     * This field corresponds to the database column monitor_message_channel.weixin_corp_id
     * Comment: 微信CorpID
     * @param weixinCorpId the value for monitor_message_channel.weixin_corp_id
     */

    private String weixinCorpId;


    /**
     * This field corresponds to the database column monitor_message_channel.weixin_secret_id
     * Comment: 微信SecretId
     * @param weixinSecretId the value for monitor_message_channel.weixin_secret_id
     */

    private java.lang.String weixinSecretId;


    /**
     * This field corresponds to the database column monitor_message_channel.weixin_encoding_AESKey
     * Comment: 微信企业号的EncodingAESKey
     * @param weixinEncodingAESKey the value for monitor_message_channel.weixin_encoding_AESKey
     */

    private java.lang.String weixinEncodingAESKey;


    /**
     * This field corresponds to the database column monitor_message_channel.weixin_wechat_token
     * Comment: 微信订阅号token
     * @param weixinWechatToken the value for monitor_message_channel.weixin_wechat_token
     */

    private java.lang.String weixinWechatToken;


    /**
     * This field corresponds to the database column monitor_message_channel.weixin_app_secret
     * Comment: 微信订阅号secret
     * @param weixinAppSecret the value for monitor_message_channel.weixin_app_secret
     */

    private java.lang.String weixinAppSecret;


    /**
     * This field corresponds to the database column monitor_message_channel.weixin_app_id
     * Comment: 微信订阅号app_id
     * @param weixinAppId the value for monitor_message_channel.weixin_app_id
     */

    private java.lang.String weixinAppId;


    /**
     * This field corresponds to the database column monitor_message_channel.channel_id
     * Comment: 主键
     * @param channelId the value for monitor_message_channel.channel_id
     */
    public void setChannelId(java.lang.Integer channelId){
       this.channelId = channelId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.channel_tp
     * Comment: 
     * @param channelTp the value for monitor_message_channel.channel_tp
     */
    public void setChannelTp(java.lang.String channelTp){
       this.channelTp = channelTp;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.channel_script
     * Comment: 发送脚本
     * @param channelScript the value for monitor_message_channel.channel_script
     */
    public void setChannelScript(java.lang.String channelScript){
       this.channelScript = channelScript;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_message_channel.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_message_channel.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_message_channel.is_valid
     */
    public void setIsValid(java.lang.Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_server
     * Comment: 发送有服务器地址
     * @param smtpServer the value for monitor_message_channel.smtp_server
     */
    public void setSmtpServer(java.lang.String smtpServer){
       this.smtpServer = smtpServer;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_user
     * Comment: 发送有服务器用户名
     * @param smtpUser the value for monitor_message_channel.smtp_user
     */
    public void setSmtpUser(java.lang.String smtpUser){
       this.smtpUser = smtpUser;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_pass
     * Comment: 发送有服务器用户名密码
     * @param smtpPass the value for monitor_message_channel.smtp_pass
     */
    public void setSmtpPass(java.lang.String smtpPass){
       this.smtpPass = smtpPass;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_auth
     * Comment: 发送服务器是否使用用户名密码
     * @param smtpAuth the value for monitor_message_channel.smtp_auth
     */
    public void setSmtpAuth(java.lang.String smtpAuth){
       this.smtpAuth = smtpAuth;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.mail_template
     * Comment: 邮件模板
     * @param mailTemplate the value for monitor_message_channel.mail_template
     */
    public void setMailTemplate(java.lang.String mailTemplate){
       this.mailTemplate = mailTemplate;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_sender
     * Comment: 向用户显示的邮件地址
     * @param smtpSender the value for monitor_message_channel.smtp_sender
     */
    public void setSmtpSender(java.lang.String smtpSender){
       this.smtpSender = smtpSender;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.ding_corp_id
     * Comment: 钉钉的CorpID
     * @param dingCorpId the value for monitor_message_channel.ding_corp_id
     */
    public void setDingCorpId(java.lang.String dingCorpId){
       this.dingCorpId = dingCorpId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.ding_secret_id
     * Comment: 钉钉的SecretId
     * @param dingSecretId the value for monitor_message_channel.ding_secret_id
     */
    public void setDingSecretId(java.lang.String dingSecretId){
       this.dingSecretId = dingSecretId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.ding_agent_id
     * Comment: 钉钉的agentid
     * @param dingAgentId the value for monitor_message_channel.ding_agent_id
     */
    public void setDingAgentId(java.math.BigInteger dingAgentId){
       this.dingAgentId = dingAgentId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_corp_id
     * Comment: 微信CorpID
     * @param weixinCorpId the value for monitor_message_channel.weixin_corp_id
     */
    public void setWeixinCorpId(String weixinCorpId){
       this.weixinCorpId = weixinCorpId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_secret_id
     * Comment: 微信SecretId
     * @param weixinSecretId the value for monitor_message_channel.weixin_secret_id
     */
    public void setWeixinSecretId(java.lang.String weixinSecretId){
       this.weixinSecretId = weixinSecretId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_encoding_AESKey
     * Comment: 微信企业号的EncodingAESKey
     * @param weixinEncodingAESKey the value for monitor_message_channel.weixin_encoding_AESKey
     */
    public void setWeixinEncodingAESKey(java.lang.String weixinEncodingAESKey){
       this.weixinEncodingAESKey = weixinEncodingAESKey;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_wechat_token
     * Comment: 微信订阅号token
     * @param weixinWechatToken the value for monitor_message_channel.weixin_wechat_token
     */
    public void setWeixinWechatToken(java.lang.String weixinWechatToken){
       this.weixinWechatToken = weixinWechatToken;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_app_secret
     * Comment: 微信订阅号secret
     * @param weixinAppSecret the value for monitor_message_channel.weixin_app_secret
     */
    public void setWeixinAppSecret(java.lang.String weixinAppSecret){
       this.weixinAppSecret = weixinAppSecret;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_app_id
     * Comment: 微信订阅号app_id
     * @param weixinAppId the value for monitor_message_channel.weixin_app_id
     */
    public void setWeixinAppId(java.lang.String weixinAppId){
       this.weixinAppId = weixinAppId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.channel_id
     * Comment: 主键
     * @return the value of monitor_message_channel.channel_id
     */
     public java.lang.Integer getChannelId() {
        return channelId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.channel_tp
     * Comment: 
     * @return the value of monitor_message_channel.channel_tp
     */
     public java.lang.String getChannelTp() {
        return channelTp;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.channel_script
     * Comment: 发送脚本
     * @return the value of monitor_message_channel.channel_script
     */
     public java.lang.String getChannelScript() {
        return channelScript;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_message_channel.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_message_channel.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.is_valid
     * Comment: 是否有效
     * @return the value of monitor_message_channel.is_valid
     */
     public java.lang.Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_server
     * Comment: 发送有服务器地址
     * @return the value of monitor_message_channel.smtp_server
     */
     public java.lang.String getSmtpServer() {
        return smtpServer;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_user
     * Comment: 发送有服务器用户名
     * @return the value of monitor_message_channel.smtp_user
     */
     public java.lang.String getSmtpUser() {
        return smtpUser;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_pass
     * Comment: 发送有服务器用户名密码
     * @return the value of monitor_message_channel.smtp_pass
     */
     public java.lang.String getSmtpPass() {
        return smtpPass;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_auth
     * Comment: 发送服务器是否使用用户名密码
     * @return the value of monitor_message_channel.smtp_auth
     */
     public java.lang.String getSmtpAuth() {
        return smtpAuth;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.mail_template
     * Comment: 邮件模板
     * @return the value of monitor_message_channel.mail_template
     */
     public java.lang.String getMailTemplate() {
        return mailTemplate;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.smtp_sender
     * Comment: 向用户显示的邮件地址
     * @return the value of monitor_message_channel.smtp_sender
     */
     public java.lang.String getSmtpSender() {
        return smtpSender;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.ding_corp_id
     * Comment: 钉钉的CorpID
     * @return the value of monitor_message_channel.ding_corp_id
     */
     public java.lang.String getDingCorpId() {
        return dingCorpId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.ding_secret_id
     * Comment: 钉钉的SecretId
     * @return the value of monitor_message_channel.ding_secret_id
     */
     public java.lang.String getDingSecretId() {
        return dingSecretId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.ding_agent_id
     * Comment: 钉钉的agentid
     * @return the value of monitor_message_channel.ding_agent_id
     */
     public java.math.BigInteger getDingAgentId() {
        return dingAgentId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_corp_id
     * Comment: 微信CorpID
     * @return the value of monitor_message_channel.weixin_corp_id
     */
     public String getWeixinCorpId() {
        return weixinCorpId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_secret_id
     * Comment: 微信SecretId
     * @return the value of monitor_message_channel.weixin_secret_id
     */
     public java.lang.String getWeixinSecretId() {
        return weixinSecretId;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_encoding_AESKey
     * Comment: 微信企业号的EncodingAESKey
     * @return the value of monitor_message_channel.weixin_encoding_AESKey
     */
     public java.lang.String getWeixinEncodingAESKey() {
        return weixinEncodingAESKey;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_wechat_token
     * Comment: 微信订阅号token
     * @return the value of monitor_message_channel.weixin_wechat_token
     */
     public java.lang.String getWeixinWechatToken() {
        return weixinWechatToken;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_app_secret
     * Comment: 微信订阅号secret
     * @return the value of monitor_message_channel.weixin_app_secret
     */
     public java.lang.String getWeixinAppSecret() {
        return weixinAppSecret;
    }

    /**
     * This field corresponds to the database column monitor_message_channel.weixin_app_id
     * Comment: 微信订阅号app_id
     * @return the value of monitor_message_channel.weixin_app_id
     */
     public java.lang.String getWeixinAppId() {
        return weixinAppId;
    }
}
