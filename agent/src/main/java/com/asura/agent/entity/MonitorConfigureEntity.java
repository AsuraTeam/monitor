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
 * @date 2016-09-15 22:44:11
 * @since 1.0
 */
public class MonitorConfigureEntity {
    /**
     * This field corresponds to the database column monitor_configure.configure_id
     * Comment: 
     * @param configureId the value for monitor_configure.configure_id
     */

    private Integer configureId;


    /**
     * This field corresponds to the database column monitor_configure.host_id
     * Comment: 主机,参考资源server_id
     * @param hostId the value for monitor_configure.host_id
     */

    private Integer hostId;


    /**
     * This field corresponds to the database column monitor_configure.description
     * Comment: 描述信息
     * @param description the value for monitor_configure.description
     */

    private String description;


    /**
     * This field corresponds to the database column monitor_configure.monitor_time
     * Comment:
     * @param monitorTime the value for monitor_configure.monitor_time
     */

    private String monitorTime;


    /**
     * This field corresponds to the database column monitor_configure.alarm_count
     * Comment: 报警次数
     * @param alarmCount the value for monitor_configure.alarm_count
     */

    private Integer alarmCount;


    /**
     * This field corresponds to the database column monitor_configure.alarm_interval
     * Comment: 报警间隔
     * @param alarmInterval the value for monitor_configure.alarm_interval
     */

    private Integer alarmInterval;


    /**
     * This field corresponds to the database column monitor_configure.script_id
     * Comment: 脚本名，参考脚本id
     * @param scriptId the value for monitor_configure.script_id
     */

    private Integer scriptId;


    /**
     * This field corresponds to the database column monitor_configure.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_configure.is_valid
     */

    private Integer isValid;


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

    private String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_configure.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @param templateId the value for monitor_configure.template_id
     */

    private String templateId;


    /**
     * This field corresponds to the database column monitor_configure.groups_id
     * Comment: 使用组,多个用逗号分隔
     * @param groupsId the value for monitor_configure.groups_id
     */

    private String groupsId;


    /**
     * This field corresponds to the database column monitor_configure.retry
     * Comment: 失败重试次数
     * @param retry the value for monitor_configure.retry
     */

    private Integer retry;


    /**
     * This field corresponds to the database column monitor_configure.monitor_configure_tp
     * Comment:
     * @param monitorConfigureTp the value for monitor_configure.monitor_configure_tp
     */

    private String monitorConfigureTp;


    /**
     * This field corresponds to the database column monitor_configure.monitor_hosts_tp
     * Comment:
     * @param monitorHostsTp the value for monitor_configure.monitor_hosts_tp
     */

    private String monitorHostsTp;


    /**
     * This field corresponds to the database column monitor_configure.hosts
     * Comment: 监控服务器IP地址，参考cmdb的server_id
     * @param hosts the value for monitor_configure.hosts
     */

    private String hosts;


    /**
     * This field corresponds to the database column monitor_configure.arg1
     * Comment: 参数1
     * @param arg1 the value for monitor_configure.arg1
     */

    private String arg1;


    /**
     * This field corresponds to the database column monitor_configure.arg2
     * Comment: 参数2
     * @param arg2 the value for monitor_configure.arg2
     */

    private String arg2;


    /**
     * This field corresponds to the database column monitor_configure.arg3
     * Comment: 参数3
     * @param arg3 the value for monitor_configure.arg3
     */

    private String arg3;


    /**
     * This field corresponds to the database column monitor_configure.arg4
     * Comment: 参数4
     * @param arg4 the value for monitor_configure.arg4
     */

    private String arg4;


    /**
     * This field corresponds to the database column monitor_configure.arg5
     * Comment: 参数5
     * @param arg5 the value for monitor_configure.arg5
     */

    private String arg5;


    /**
     * This field corresponds to the database column monitor_configure.arg6
     * Comment: 参数6
     * @param arg6 the value for monitor_configure.arg6
     */

    private String arg6;


    /**
     * This field corresponds to the database column monitor_configure.arg7
     * Comment: 参数7
     * @param arg7 the value for monitor_configure.arg7
     */

    private String arg7;


    /**
     * This field corresponds to the database column monitor_configure.arg8
     * Comment: 参数8
     * @param arg8 the value for monitor_configure.arg8
     */

    private String arg8;


    /**
     * This field corresponds to the database column monitor_configure.check_interval
     * Comment: 脚本检查间隔
     * @param checkInterval the value for monitor_configure.check_interval
     */

    private Integer checkInterval;


    /**
     * This field corresponds to the database column monitor_configure.is_mobile
     * Comment: 报警发送给手机,1有效，0无效
     * @param isMobile the value for monitor_configure.is_mobile
     */

    private Integer isMobile;


    /**
     * This field corresponds to the database column monitor_configure.is_email
     * Comment: 报警发送给手机,1有效，0无效
     * @param isEmail the value for monitor_configure.is_email
     */

    private Integer isEmail;


    /**
     * This field corresponds to the database column monitor_configure.is_ding
     * Comment: 报警发送给钉钉,1有效，0无效
     * @param isDing the value for monitor_configure.is_ding
     */

    private Integer isDing;


    /**
     * This field corresponds to the database column monitor_configure.is_weixin
     * Comment: 报警发送给微信,1有效，0无效
     * @param isWeixin the value for monitor_configure.is_weixin
     */

    private Integer isWeixin;


    /**
     * This field corresponds to the database column monitor_configure.weixin_groups
     * Comment:
     * @param weixinGroups the value for monitor_configure.weixin_groups
     */

    private String weixinGroups;


    /**
     * This field corresponds to the database column monitor_configure.ding_groups
     * Comment:
     * @param dingGroups the value for monitor_configure.ding_groups
     */

    private String dingGroups;


    /**
     * This field corresponds to the database column monitor_configure.mobile_groups
     * Comment:
     * @param mobileGroups the value for monitor_configure.mobile_groups
     */

    private String mobileGroups;


    /**
     * This field corresponds to the database column monitor_configure.email_groups
     * Comment:
     * @param emailGroups the value for monitor_configure.email_groups
     */

    private String emailGroups;


    /**
     * This field corresponds to the database column monitor_configure.all_groups
     * Comment:
     * @param allGroups the value for monitor_configure.all_groups
     */

    private String allGroups;


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

    private String gname;


    /**
     * This field corresponds to the database column monitor_configure.configure_id
     * Comment:
     * @param configureId the value for monitor_configure.configure_id
     */
    public void setConfigureId(Integer configureId){
       this.configureId = configureId;
    }

    /**
     * This field corresponds to the database column monitor_configure.host_id
     * Comment: 主机,参考资源server_id
     * @param hostId the value for monitor_configure.host_id
     */
    public void setHostId(Integer hostId){
       this.hostId = hostId;
    }

    /**
     * This field corresponds to the database column monitor_configure.description
     * Comment: 描述信息
     * @param description the value for monitor_configure.description
     */
    public void setDescription(String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_time
     * Comment:
     * @param monitorTime the value for monitor_configure.monitor_time
     */
    public void setMonitorTime(String monitorTime){
       this.monitorTime = monitorTime;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_count
     * Comment: 报警次数
     * @param alarmCount the value for monitor_configure.alarm_count
     */
    public void setAlarmCount(Integer alarmCount){
       this.alarmCount = alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_interval
     * Comment: 报警间隔
     * @param alarmInterval the value for monitor_configure.alarm_interval
     */
    public void setAlarmInterval(Integer alarmInterval){
       this.alarmInterval = alarmInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.script_id
     * Comment: 脚本名，参考脚本id
     * @param scriptId the value for monitor_configure.script_id
     */
    public void setScriptId(Integer scriptId){
       this.scriptId = scriptId;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_configure.is_valid
     */
    public void setIsValid(Integer isValid){
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
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_configure.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @param templateId the value for monitor_configure.template_id
     */
    public void setTemplateId(String templateId){
       this.templateId = templateId;
    }

    /**
     * This field corresponds to the database column monitor_configure.groups_id
     * Comment: 使用组,多个用逗号分隔
     * @param groupsId the value for monitor_configure.groups_id
     */
    public void setGroupsId(String groupsId){
       this.groupsId = groupsId;
    }

    /**
     * This field corresponds to the database column monitor_configure.retry
     * Comment: 失败重试次数
     * @param retry the value for monitor_configure.retry
     */
    public void setRetry(Integer retry){
       this.retry = retry;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_configure_tp
     * Comment:
     * @param monitorConfigureTp the value for monitor_configure.monitor_configure_tp
     */
    public void setMonitorConfigureTp(String monitorConfigureTp){
       this.monitorConfigureTp = monitorConfigureTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_hosts_tp
     * Comment:
     * @param monitorHostsTp the value for monitor_configure.monitor_hosts_tp
     */
    public void setMonitorHostsTp(String monitorHostsTp){
       this.monitorHostsTp = monitorHostsTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.hosts
     * Comment: 监控服务器IP地址，参考cmdb的server_id
     * @param hosts the value for monitor_configure.hosts
     */
    public void setHosts(String hosts){
       this.hosts = hosts;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg1
     * Comment: 参数1
     * @param arg1 the value for monitor_configure.arg1
     */
    public void setArg1(String arg1){
       this.arg1 = arg1;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg2
     * Comment: 参数2
     * @param arg2 the value for monitor_configure.arg2
     */
    public void setArg2(String arg2){
       this.arg2 = arg2;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg3
     * Comment: 参数3
     * @param arg3 the value for monitor_configure.arg3
     */
    public void setArg3(String arg3){
       this.arg3 = arg3;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg4
     * Comment: 参数4
     * @param arg4 the value for monitor_configure.arg4
     */
    public void setArg4(String arg4){
       this.arg4 = arg4;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg5
     * Comment: 参数5
     * @param arg5 the value for monitor_configure.arg5
     */
    public void setArg5(String arg5){
       this.arg5 = arg5;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg6
     * Comment: 参数6
     * @param arg6 the value for monitor_configure.arg6
     */
    public void setArg6(String arg6){
       this.arg6 = arg6;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg7
     * Comment: 参数7
     * @param arg7 the value for monitor_configure.arg7
     */
    public void setArg7(String arg7){
       this.arg7 = arg7;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg8
     * Comment: 参数8
     * @param arg8 the value for monitor_configure.arg8
     */
    public void setArg8(String arg8){
       this.arg8 = arg8;
    }

    /**
     * This field corresponds to the database column monitor_configure.check_interval
     * Comment: 脚本检查间隔
     * @param checkInterval the value for monitor_configure.check_interval
     */
    public void setCheckInterval(Integer checkInterval){
       this.checkInterval = checkInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_mobile
     * Comment: 报警发送给手机,1有效，0无效
     * @param isMobile the value for monitor_configure.is_mobile
     */
    public void setIsMobile(Integer isMobile){
       this.isMobile = isMobile;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_email
     * Comment: 报警发送给手机,1有效，0无效
     * @param isEmail the value for monitor_configure.is_email
     */
    public void setIsEmail(Integer isEmail){
       this.isEmail = isEmail;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_ding
     * Comment: 报警发送给钉钉,1有效，0无效
     * @param isDing the value for monitor_configure.is_ding
     */
    public void setIsDing(Integer isDing){
       this.isDing = isDing;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_weixin
     * Comment: 报警发送给微信,1有效，0无效
     * @param isWeixin the value for monitor_configure.is_weixin
     */
    public void setIsWeixin(Integer isWeixin){
       this.isWeixin = isWeixin;
    }

    /**
     * This field corresponds to the database column monitor_configure.weixin_groups
     * Comment:
     * @param weixinGroups the value for monitor_configure.weixin_groups
     */
    public void setWeixinGroups(String weixinGroups){
       this.weixinGroups = weixinGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.ding_groups
     * Comment:
     * @param dingGroups the value for monitor_configure.ding_groups
     */
    public void setDingGroups(String dingGroups){
       this.dingGroups = dingGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.mobile_groups
     * Comment:
     * @param mobileGroups the value for monitor_configure.mobile_groups
     */
    public void setMobileGroups(String mobileGroups){
       this.mobileGroups = mobileGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.email_groups
     * Comment:
     * @param emailGroups the value for monitor_configure.email_groups
     */
    public void setEmailGroups(String emailGroups){
       this.emailGroups = emailGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.all_groups
     * Comment:
     * @param allGroups the value for monitor_configure.all_groups
     */
    public void setAllGroups(String allGroups){
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
    public void setGname(String gname){
       this.gname = gname;
    }

    /**
     * This field corresponds to the database column monitor_configure.configure_id
     * Comment:
     * @return the value of monitor_configure.configure_id
     */
     public Integer getConfigureId() {
        return configureId;
    }

    /**
     * This field corresponds to the database column monitor_configure.host_id
     * Comment: 主机,参考资源server_id
     * @return the value of monitor_configure.host_id
     */
     public Integer getHostId() {
        return hostId;
    }

    /**
     * This field corresponds to the database column monitor_configure.description
     * Comment: 描述信息
     * @return the value of monitor_configure.description
     */
     public String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_time
     * Comment:
     * @return the value of monitor_configure.monitor_time
     */
     public String getMonitorTime() {
        return monitorTime;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_count
     * Comment: 报警次数
     * @return the value of monitor_configure.alarm_count
     */
     public Integer getAlarmCount() {
        return alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_configure.alarm_interval
     * Comment: 报警间隔
     * @return the value of monitor_configure.alarm_interval
     */
     public Integer getAlarmInterval() {
        return alarmInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.script_id
     * Comment: 脚本名，参考脚本id
     * @return the value of monitor_configure.script_id
     */
     public Integer getScriptId() {
        return scriptId;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_valid
     * Comment: 是否有效
     * @return the value of monitor_configure.is_valid
     */
     public Integer getIsValid() {
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
     public String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_configure.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @return the value of monitor_configure.template_id
     */
     public String getTemplateId() {
        return templateId;
    }

    /**
     * This field corresponds to the database column monitor_configure.groups_id
     * Comment: 使用组,多个用逗号分隔
     * @return the value of monitor_configure.groups_id
     */
     public String getGroupsId() {
        return groupsId;
    }

    /**
     * This field corresponds to the database column monitor_configure.retry
     * Comment: 失败重试次数
     * @return the value of monitor_configure.retry
     */
     public Integer getRetry() {
        return retry;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_configure_tp
     * Comment:
     * @return the value of monitor_configure.monitor_configure_tp
     */
     public String getMonitorConfigureTp() {
        return monitorConfigureTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.monitor_hosts_tp
     * Comment:
     * @return the value of monitor_configure.monitor_hosts_tp
     */
     public String getMonitorHostsTp() {
        return monitorHostsTp;
    }

    /**
     * This field corresponds to the database column monitor_configure.hosts
     * Comment: 监控服务器IP地址，参考cmdb的server_id
     * @return the value of monitor_configure.hosts
     */
     public String getHosts() {
        return hosts;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg1
     * Comment: 参数1
     * @return the value of monitor_configure.arg1
     */
     public String getArg1() {
        return arg1;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg2
     * Comment: 参数2
     * @return the value of monitor_configure.arg2
     */
     public String getArg2() {
        return arg2;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg3
     * Comment: 参数3
     * @return the value of monitor_configure.arg3
     */
     public String getArg3() {
        return arg3;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg4
     * Comment: 参数4
     * @return the value of monitor_configure.arg4
     */
     public String getArg4() {
        return arg4;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg5
     * Comment: 参数5
     * @return the value of monitor_configure.arg5
     */
     public String getArg5() {
        return arg5;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg6
     * Comment: 参数6
     * @return the value of monitor_configure.arg6
     */
     public String getArg6() {
        return arg6;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg7
     * Comment: 参数7
     * @return the value of monitor_configure.arg7
     */
     public String getArg7() {
        return arg7;
    }

    /**
     * This field corresponds to the database column monitor_configure.arg8
     * Comment: 参数8
     * @return the value of monitor_configure.arg8
     */
     public String getArg8() {
        return arg8;
    }

    /**
     * This field corresponds to the database column monitor_configure.check_interval
     * Comment: 脚本检查间隔
     * @return the value of monitor_configure.check_interval
     */
     public Integer getCheckInterval() {
        return checkInterval;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_mobile
     * Comment: 报警发送给手机,1有效，0无效
     * @return the value of monitor_configure.is_mobile
     */
     public Integer getIsMobile() {
        return isMobile;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_email
     * Comment: 报警发送给手机,1有效，0无效
     * @return the value of monitor_configure.is_email
     */
     public Integer getIsEmail() {
        return isEmail;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_ding
     * Comment: 报警发送给钉钉,1有效，0无效
     * @return the value of monitor_configure.is_ding
     */
     public Integer getIsDing() {
        return isDing;
    }

    /**
     * This field corresponds to the database column monitor_configure.is_weixin
     * Comment: 报警发送给微信,1有效，0无效
     * @return the value of monitor_configure.is_weixin
     */
     public Integer getIsWeixin() {
        return isWeixin;
    }

    /**
     * This field corresponds to the database column monitor_configure.weixin_groups
     * Comment:
     * @return the value of monitor_configure.weixin_groups
     */
     public String getWeixinGroups() {
        return weixinGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.ding_groups
     * Comment:
     * @return the value of monitor_configure.ding_groups
     */
     public String getDingGroups() {
        return dingGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.mobile_groups
     * Comment:
     * @return the value of monitor_configure.mobile_groups
     */
     public String getMobileGroups() {
        return mobileGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.email_groups
     * Comment:
     * @return the value of monitor_configure.email_groups
     */
     public String getEmailGroups() {
        return emailGroups;
    }

    /**
     * This field corresponds to the database column monitor_configure.all_groups
     * Comment:
     * @return the value of monitor_configure.all_groups
     */
     public String getAllGroups() {
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
     public String getGname() {
        return gname;
    }
}
