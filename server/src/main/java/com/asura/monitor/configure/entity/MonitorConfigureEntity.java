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
 * @date 2016-09-15 22:44:11
 * @since 1.0
 */
public class MonitorConfigureEntity extends BaseEntity{

    private String groupsName;

    public String getGroupsName() {
        return groupsName;
    }

    public void setGroupsName(String groupsName) {
        this.groupsName = groupsName;
    }

    /**
     * This field corresponds to the database column monitor_configure.configure_id
     * Comment: 
     * @param configureId the value for monitor_configure.configure_id
     */

    private java.lang.Integer configureId;


    /**
     * This field corresponds to the database column monitor_configure.host_id
     * Comment: 主机,参考资源server_id
     * @param hostId the value for monitor_configure.host_id
     */

    private java.lang.Integer hostId;


    /**
     * This field corresponds to the database column monitor_configure.description
     * Comment: 描述信息
     * @param description the value for monitor_configure.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_configure.monitor_time
     * Comment: 
     * @param monitorTime the value for monitor_configure.monitor_time
     */

    private java.lang.String monitorTime;


    /**
     * This field corresponds to the database column monitor_configure.alarm_count
     * Comment: 报警次数
     * @param alarmCount the value for monitor_configure.alarm_count
     */

    private java.lang.Integer alarmCount;


    /**
     * This field corresponds to the database column monitor_configure.alarm_interval
     * Comment: 报警间隔
     * @param alarmInterval the value for monitor_configure.alarm_interval
     */

    private java.lang.Integer alarmInterval;


    /**
     * This field corresponds to the database column monitor_configure.script_id
     * Comment: 脚本名，参考脚本id
     * @param scriptId the value for monitor_configure.script_id
     */

    private java.lang.Integer scriptId;


    /**
     * This field corresponds to the database column monitor_configure.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_configure.is_valid
     */

    private java.lang.Integer isValid;


    /**
     * This field corresponds to the database column monitor_configure.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_configure.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_configure.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_configure.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_configure.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @param templateId the value for monitor_configure.template_id
     */

    private java.lang.String templateId;


    /**
     * This field corresponds to the database column monitor_configure.groups_id
     * Comment: 使用组,多个用逗号分隔
     * @param groupsId the value for monitor_configure.groups_id
     */

    private java.lang.String groupsId;


    /**
     * This field corresponds to the database column monitor_configure.retry
     * Comment: 失败重试次数
     * @param retry the value for monitor_configure.retry
     */

    private java.lang.Integer retry;


    /**
     * This field corresponds to the database column monitor_configure.monitor_configure_tp
     * Comment: 
     * @param monitorConfigureTp the value for monitor_configure.monitor_configure_tp
     */

    private java.lang.String monitorConfigureTp;


    /**
     * This field corresponds to the database column monitor_configure.monitor_hosts_tp
     * Comment: 
     * @param monitorHostsTp the value for monitor_configure.monitor_hosts_tp
     */

    private java.lang.String monitorHostsTp;


    /**
     * This field corresponds to the database column monitor_configure.hosts
     * Comment: 监控服务器IP地址，参考cmdb的server_id
     * @param hosts the value for monitor_configure.hosts
     */

    private java.lang.String hosts;


    /**
     * This field corresponds to the database column monitor_configure.arg1
     * Comment: 参数1
     * @param arg1 the value for monitor_configure.arg1
     */

    private java.lang.String arg1;


    /**
     * This field corresponds to the database column monitor_configure.arg2
     * Comment: 参数2
     * @param arg2 the value for monitor_configure.arg2
     */

    private java.lang.String arg2;


    /**
     * This field corresponds to the database column monitor_configure.arg3
     * Comment: 参数3
     * @param arg3 the value for monitor_configure.arg3
     */

    private java.lang.String arg3;


    /**
     * This field corresponds to the database column monitor_configure.arg4
     * Comment: 参数4
     * @param arg4 the value for monitor_configure.arg4
     */

    private java.lang.String arg4;


    /**
     * This field corresponds to the database column monitor_configure.arg5
     * Comment: 参数5
     * @param arg5 the value for monitor_configure.arg5
     */

    private java.lang.String arg5;


    /**
     * This field corresponds to the database column monitor_configure.arg6
     * Comment: 参数6
     * @param arg6 the value for monitor_configure.arg6
     */

    private java.lang.String arg6;


    /**
     * This field corresponds to the database column monitor_configure.arg7
     * Comment: 参数7
     * @param arg7 the value for monitor_configure.arg7
     */

    private java.lang.String arg7;


    /**
     * This field corresponds to the database column monitor_configure.arg8
     * Comment: 参数8
     * @param arg8 the value for monitor_configure.arg8
     */

    private java.lang.String arg8;


    /**
     * This field corresponds to the database column monitor_configure.check_interval
     * Comment: 脚本检查间隔
     * @param checkInterval the value for monitor_configure.check_interval
     */

    private java.lang.Integer checkInterval;


    /**
     * This field corresponds to the database column monitor_configure.is_mobile
     * Comment: 报警发送给手机,1有效，0无效
     * @param isMobile the value for monitor_configure.is_mobile
     */

    private java.lang.Integer isMobile;


    /**
     * This field corresponds to the database column monitor_configure.is_email
     * Comment: 报警发送给手机,1有效，0无效
     * @param isEmail the value for monitor_configure.is_email
     */

    private java.lang.Integer isEmail;


    /**
     * This field corresponds to the database column monitor_configure.is_ding
     * Comment: 报警发送给钉钉,1有效，0无效
     * @param isDing the value for monitor_configure.is_ding
     */

    private java.lang.Integer isDing;


    /**
     * This field corresponds to the database column monitor_configure.is_weixin
     * Comment: 报警发送给微信,1有效，0无效
     * @param isWeixin the value for monitor_configure.is_weixin
     */

    private java.lang.Integer isWeixin;


    /**
     * This field corresponds to the database column monitor_configure.weixin_groups
     * Comment: 
     * @param weixinGroups the value for monitor_configure.weixin_groups
     */

    private java.lang.String weixinGroups;


    /**
     * This field corresponds to the database column monitor_configure.ding_groups
     * Comment: 
     * @param dingGroups the value for monitor_configure.ding_groups
     */

    private java.lang.String dingGroups;


    /**
     * This field corresponds to the database column monitor_configure.mobile_groups
     * Comment: 
     * @param mobileGroups the value for monitor_configure.mobile_groups
     */

    private java.lang.String mobileGroups;


    /**
     * This field corresponds to the database column monitor_configure.email_groups
     * Comment: 
     * @param emailGroups the value for monitor_configure.email_groups
     */

    private java.lang.String emailGroups;


    /**
     * This field corresponds to the database column monitor_configure.all_groups
     * Comment: 
     * @param allGroups the value for monitor_configure.all_groups
     */

    private java.lang.String allGroups;


    /**
     * This field corresponds to the database column monitor_configure.item_id
     * Comment: 项目id，参考监控项目的id
     * @param itemId the value for monitor_configure.item_id
     */

    private String itemId;


    /**
     * This field corresponds to the database column monitor_configure.gname
     * Comment: 
     * @param gname the value for monitor_configure.gname
     */

    private java.lang.String gname;


    /**
     * This field corresponds to the database column monitor_configure.configure_id
     * Comment: 
     * @param configureId the value for monitor_configure.configure_id
     */
    public void setConfigureId(java.lang.Integer configureId){
       this.configureId = configureId;
    }

    /**
     * This field corresponds to the database column monitor_configure.host_id
     * Comment: 主机,参考资源server_id
     * @param hostId the value for monitor_configure.host_id
     */
    public void setHostId(java.lang.Integer hostId){
       this.hostId = hostId;
    }

    /**
     * This field corresponds to the database column monitor_configure.description
     * Comment: 描述信息
     * @param description the value for monitor_configure.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_time
     * Comment: 
     * @param monitorTime the value for monitor_configure.monitor_time
     */
    public void setMonitorTime(java.lang.String monitorTime){
       this.monitorTime = monitorTime;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_count
     * Comment: 报警次数
     * @param alarmCount the value for monitor_configure.alarm_count
     */
    public void setAlarmCount(java.lang.Integer alarmCount){
       this.alarmCount = alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_interval
     * Comment: 报警间隔
     * @param alarmInterval the value for monitor_configure.alarm_interval
     */
    public void setAlarmInterval(java.lang.Integer alarmInterval){
       this.alarmInterval = alarmInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.script_id
     * Comment: 脚本名，参考脚本id
     * @param scriptId the value for monitor_configure.script_id
     */
    public void setScriptId(java.lang.Integer scriptId){
       this.scriptId = scriptId;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_configure.is_valid
     */
    public void setIsValid(java.lang.Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_configure.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_configure.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_configure.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_configure.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_configure.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @param templateId the value for monitor_configure.template_id
     */
    public void setTemplateId(java.lang.String templateId){
       this.templateId = templateId;
    }

    /**
     * This field corresponds to the database column monitor_configure.groups_id
     * Comment: 使用组,多个用逗号分隔
     * @param groupsId the value for monitor_configure.groups_id
     */
    public void setGroupsId(java.lang.String groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_configure.retry
     * Comment: 失败重试次数
     * @param retry the value for monitor_configure.retry
     */
    public void setRetry(java.lang.Integer retry){
       this.retry = retry;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_configure_tp
     * Comment: 
     * @param monitorConfigureTp the value for monitor_configure.monitor_configure_tp
     */
    public void setMonitorConfigureTp(java.lang.String monitorConfigureTp){
       this.monitorConfigureTp = monitorConfigureTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_hosts_tp
     * Comment: 
     * @param monitorHostsTp the value for monitor_configure.monitor_hosts_tp
     */
    public void setMonitorHostsTp(java.lang.String monitorHostsTp){
       this.monitorHostsTp = monitorHostsTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.hosts
     * Comment: 监控服务器IP地址，参考cmdb的server_id
     * @param hosts the value for monitor_configure.hosts
     */
    public void setHosts(java.lang.String hosts){
       this.hosts = hosts;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg1
     * Comment: 参数1
     * @param arg1 the value for monitor_configure.arg1
     */
    public void setArg1(java.lang.String arg1){
       this.arg1 = arg1;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg2
     * Comment: 参数2
     * @param arg2 the value for monitor_configure.arg2
     */
    public void setArg2(java.lang.String arg2){
       this.arg2 = arg2;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg3
     * Comment: 参数3
     * @param arg3 the value for monitor_configure.arg3
     */
    public void setArg3(java.lang.String arg3){
       this.arg3 = arg3;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg4
     * Comment: 参数4
     * @param arg4 the value for monitor_configure.arg4
     */
    public void setArg4(java.lang.String arg4){
       this.arg4 = arg4;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg5
     * Comment: 参数5
     * @param arg5 the value for monitor_configure.arg5
     */
    public void setArg5(java.lang.String arg5){
       this.arg5 = arg5;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg6
     * Comment: 参数6
     * @param arg6 the value for monitor_configure.arg6
     */
    public void setArg6(java.lang.String arg6){
       this.arg6 = arg6;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg7
     * Comment: 参数7
     * @param arg7 the value for monitor_configure.arg7
     */
    public void setArg7(java.lang.String arg7){
       this.arg7 = arg7;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg8
     * Comment: 参数8
     * @param arg8 the value for monitor_configure.arg8
     */
    public void setArg8(java.lang.String arg8){
       this.arg8 = arg8;
    }

    /**
     * This field corresponds to the database column monitor_configure.check_interval
     * Comment: 脚本检查间隔
     * @param checkInterval the value for monitor_configure.check_interval
     */
    public void setCheckInterval(java.lang.Integer checkInterval){
       this.checkInterval = checkInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_mobile
     * Comment: 报警发送给手机,1有效，0无效
     * @param isMobile the value for monitor_configure.is_mobile
     */
    public void setIsMobile(java.lang.Integer isMobile){
       this.isMobile = isMobile;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_email
     * Comment: 报警发送给手机,1有效，0无效
     * @param isEmail the value for monitor_configure.is_email
     */
    public void setIsEmail(java.lang.Integer isEmail){
       this.isEmail = isEmail;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_ding
     * Comment: 报警发送给钉钉,1有效，0无效
     * @param isDing the value for monitor_configure.is_ding
     */
    public void setIsDing(java.lang.Integer isDing){
       this.isDing = isDing;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_weixin
     * Comment: 报警发送给微信,1有效，0无效
     * @param isWeixin the value for monitor_configure.is_weixin
     */
    public void setIsWeixin(java.lang.Integer isWeixin){
       this.isWeixin = isWeixin;
    }

    /**
     * This field corresponds to the database column monitor_configure.weixin_groups
     * Comment: 
     * @param weixinGroups the value for monitor_configure.weixin_groups
     */
    public void setWeixinGroups(java.lang.String weixinGroups){
       this.weixinGroups = weixinGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.ding_groups
     * Comment: 
     * @param dingGroups the value for monitor_configure.ding_groups
     */
    public void setDingGroups(java.lang.String dingGroups){
       this.dingGroups = dingGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.mobile_groups
     * Comment: 
     * @param mobileGroups the value for monitor_configure.mobile_groups
     */
    public void setMobileGroups(java.lang.String mobileGroups){
       this.mobileGroups = mobileGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.email_groups
     * Comment: 
     * @param emailGroups the value for monitor_configure.email_groups
     */
    public void setEmailGroups(java.lang.String emailGroups){
       this.emailGroups = emailGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.all_groups
     * Comment: 
     * @param allGroups the value for monitor_configure.all_groups
     */
    public void setAllGroups(java.lang.String allGroups){
       this.allGroups = allGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.item_id
     * Comment: 项目id，参考监控项目的id
     * @param itemId the value for monitor_configure.item_id
     */
    public void setItemId(String itemId){
       this.itemId = itemId;
    }

    /**
     * This field corresponds to the database column monitor_configure.gname
     * Comment: 
     * @param gname the value for monitor_configure.gname
     */
    public void setGname(java.lang.String gname){
       this.gname = gname;
    }

    /**
     * This field corresponds to the database column monitor_configure.configure_id
     * Comment: 
     * @return the value of monitor_configure.configure_id
     */
     public java.lang.Integer getConfigureId() {
        return configureId;
    }

    /**
     * This field corresponds to the database column monitor_configure.host_id
     * Comment: 主机,参考资源server_id
     * @return the value of monitor_configure.host_id
     */
     public java.lang.Integer getHostId() {
        return hostId;
    }

    /**
     * This field corresponds to the database column monitor_configure.description
     * Comment: 描述信息
     * @return the value of monitor_configure.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_time
     * Comment: 
     * @return the value of monitor_configure.monitor_time
     */
     public java.lang.String getMonitorTime() {
        return monitorTime;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_count
     * Comment: 报警次数
     * @return the value of monitor_configure.alarm_count
     */
     public java.lang.Integer getAlarmCount() {
        return alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_interval
     * Comment: 报警间隔
     * @return the value of monitor_configure.alarm_interval
     */
     public java.lang.Integer getAlarmInterval() {
        return alarmInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.script_id
     * Comment: 脚本名，参考脚本id
     * @return the value of monitor_configure.script_id
     */
     public java.lang.Integer getScriptId() {
        return scriptId;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_valid
     * Comment: 是否有效
     * @return the value of monitor_configure.is_valid
     */
     public java.lang.Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_configure.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_configure.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_configure.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_configure.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_configure.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @return the value of monitor_configure.template_id
     */
     public java.lang.String getTemplateId() {
        return templateId;
    }

    /**
     * This field corresponds to the database column monitor_configure.groups_id
     * Comment: 使用组,多个用逗号分隔
     * @return the value of monitor_configure.groups_id
     */
     public java.lang.String getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column monitor_configure.retry
     * Comment: 失败重试次数
     * @return the value of monitor_configure.retry
     */
     public java.lang.Integer getRetry() {
        return retry;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_configure_tp
     * Comment: 
     * @return the value of monitor_configure.monitor_configure_tp
     */
     public java.lang.String getMonitorConfigureTp() {
        return monitorConfigureTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_hosts_tp
     * Comment: 
     * @return the value of monitor_configure.monitor_hosts_tp
     */
     public java.lang.String getMonitorHostsTp() {
        return monitorHostsTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.hosts
     * Comment: 监控服务器IP地址，参考cmdb的server_id
     * @return the value of monitor_configure.hosts
     */
     public java.lang.String getHosts() {
        return hosts;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg1
     * Comment: 参数1
     * @return the value of monitor_configure.arg1
     */
     public java.lang.String getArg1() {
        return arg1;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg2
     * Comment: 参数2
     * @return the value of monitor_configure.arg2
     */
     public java.lang.String getArg2() {
        return arg2;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg3
     * Comment: 参数3
     * @return the value of monitor_configure.arg3
     */
     public java.lang.String getArg3() {
        return arg3;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg4
     * Comment: 参数4
     * @return the value of monitor_configure.arg4
     */
     public java.lang.String getArg4() {
        return arg4;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg5
     * Comment: 参数5
     * @return the value of monitor_configure.arg5
     */
     public java.lang.String getArg5() {
        return arg5;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg6
     * Comment: 参数6
     * @return the value of monitor_configure.arg6
     */
     public java.lang.String getArg6() {
        return arg6;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg7
     * Comment: 参数7
     * @return the value of monitor_configure.arg7
     */
     public java.lang.String getArg7() {
        return arg7;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg8
     * Comment: 参数8
     * @return the value of monitor_configure.arg8
     */
     public java.lang.String getArg8() {
        return arg8;
    }

    /**
     * This field corresponds to the database column monitor_configure.check_interval
     * Comment: 脚本检查间隔
     * @return the value of monitor_configure.check_interval
     */
     public java.lang.Integer getCheckInterval() {
        return checkInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_mobile
     * Comment: 报警发送给手机,1有效，0无效
     * @return the value of monitor_configure.is_mobile
     */
     public java.lang.Integer getIsMobile() {
        return isMobile;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_email
     * Comment: 报警发送给手机,1有效，0无效
     * @return the value of monitor_configure.is_email
     */
     public java.lang.Integer getIsEmail() {
        return isEmail;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_ding
     * Comment: 报警发送给钉钉,1有效，0无效
     * @return the value of monitor_configure.is_ding
     */
     public java.lang.Integer getIsDing() {
        return isDing;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_weixin
     * Comment: 报警发送给微信,1有效，0无效
     * @return the value of monitor_configure.is_weixin
     */
     public java.lang.Integer getIsWeixin() {
        return isWeixin;
    }

    /**
     * This field corresponds to the database column monitor_configure.weixin_groups
     * Comment: 
     * @return the value of monitor_configure.weixin_groups
     */
     public java.lang.String getWeixinGroups() {
        return weixinGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.ding_groups
     * Comment: 
     * @return the value of monitor_configure.ding_groups
     */
     public java.lang.String getDingGroups() {
        return dingGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.mobile_groups
     * Comment: 
     * @return the value of monitor_configure.mobile_groups
     */
     public java.lang.String getMobileGroups() {
        return mobileGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.email_groups
     * Comment: 
     * @return the value of monitor_configure.email_groups
     */
     public java.lang.String getEmailGroups() {
        return emailGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.all_groups
     * Comment: 
     * @return the value of monitor_configure.all_groups
     */
     public java.lang.String getAllGroups() {
        return allGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.item_id
     * Comment: 项目id，参考监控项目的id
     * @return the value of monitor_configure.item_id
     */
     public String getItemId() {
        return itemId;
    }

    /**
     * This field corresponds to the database column monitor_configure.gname
     * Comment: 
     * @return the value of monitor_configure.gname
     */
     public java.lang.String getGname() {
        return gname;
    }
}
