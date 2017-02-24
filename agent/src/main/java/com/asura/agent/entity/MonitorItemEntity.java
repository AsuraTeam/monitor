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
 * @date 2016-08-20 16:45:10
 * @since 1.0
 */
public class MonitorItemEntity {

    private int resend;
    private int isRecover;
    private String alarmMessages;
    private String recoverMessages;
    private String fileName;
    private String arg1comm;
    private String isAdmin;

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    // 是否发送合并后报警短信
    private int isMerge;

    public int getIsMerge() {
        return isMerge;
    }

    public void setIsMerge(int isMerge) {
        this.isMerge = isMerge;
    }

    public int getResend() {
        return resend;
    }

    public void setResend(int resend) {
        this.resend = resend;
    }

    public int getIsRecover() {
        return isRecover;
    }

    public void setIsRecover(int isRecover) {
        this.isRecover = isRecover;
    }

    public String getAlarmMessages() {
        return alarmMessages;
    }

    public void setAlarmMessages(String alarmMessages) {
        this.alarmMessages = alarmMessages;
    }

    public String getRecoverMessages() {
        return recoverMessages;
    }

    public void setRecoverMessages(String recoverMessages) {
        this.recoverMessages = recoverMessages;
    }

    public String getArg1comm() {
        return arg1comm;
    }

    public void setArg1comm(String arg1comm) {
        this.arg1comm = arg1comm;
    }

    public String getArg2comm() {
        return arg2comm;
    }

    public void setArg2comm(String arg2comm) {
        this.arg2comm = arg2comm;
    }

    public String getArg3comm() {
        return arg3comm;
    }

    public void setArg3comm(String arg3comm) {
        this.arg3comm = arg3comm;
    }

    public String getArg4comm() {
        return arg4comm;
    }

    public void setArg4comm(String arg4comm) {
        this.arg4comm = arg4comm;
    }

    public String getArg5comm() {
        return arg5comm;
    }

    public void setArg5comm(String arg5comm) {
        this.arg5comm = arg5comm;
    }

    public String getArg6comm() {
        return arg6comm;
    }

    public void setArg6comm(String arg6comm) {
        this.arg6comm = arg6comm;
    }

    public String getArg7comm() {
        return arg7comm;
    }

    public void setArg7comm(String arg7comm) {
        this.arg7comm = arg7comm;
    }

    public String getArg8comm() {
        return arg8comm;
    }

    public void setArg8comm(String arg8comm) {
        this.arg8comm = arg8comm;
    }

    private String arg2comm;
    private String arg3comm;
    private String arg4comm;
    private String arg5comm;
    private String arg6comm;
    private String arg7comm;
    private String arg8comm;
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * This field corresponds to the database column monitor_item.item_id
     * Comment: 
     * @param itemId the value for monitor_item.item_id
     */

    private Integer itemId;


    /**
     * This field corresponds to the database column monitor_item.item_name
     * Comment:
     * @param itemName the value for monitor_item.item_name
     */

    private String itemName;


    /**
     * This field corresponds to the database column monitor_item.description
     * Comment: 描述信息
     * @param description the value for monitor_item.description
     */

    private String description;


    /**
     * This field corresponds to the database column monitor_item.monitor_time
     * Comment:
     * @param monitorTime the value for monitor_item.monitor_time
     */

    private String monitorTime;


    /**
     * This field corresponds to the database column monitor_item.check_interval
     * Comment:
     * @param checkInterval the value for monitor_item.check_interval
     */

    private String checkInterval;


    /**
     * This field corresponds to the database column monitor_item.alarm_count
     * Comment: 报警次数
     * @param alarmCount the value for monitor_item.alarm_count
     */

    private Integer alarmCount;


    /**
     * This field corresponds to the database column monitor_item.alarm_interval
     * Comment: 2次报警间隔
     * @param alarmInterval the value for monitor_item.alarm_interval
     */

    private Integer alarmInterval;


    /**
     * This field corresponds to the database column monitor_item.script_id
     * Comment: 参考脚本id
     * @param scriptId the value for monitor_item.script_id
     */

    private Integer scriptId;


    /**
     * This field corresponds to the database column monitor_item.arg1
     * Comment:
     * @param arg1 the value for monitor_item.arg1
     */

    private String arg1;


    /**
     * This field corresponds to the database column monitor_item.arg2
     * Comment:
     * @param arg2 the value for monitor_item.arg2
     */

    private String arg2;


    /**
     * This field corresponds to the database column monitor_item.arg3
     * Comment:
     * @param arg3 the value for monitor_item.arg3
     */

    private String arg3;


    /**
     * This field corresponds to the database column monitor_item.arg4
     * Comment:
     * @param arg4 the value for monitor_item.arg4
     */

    private String arg4;


    /**
     * This field corresponds to the database column monitor_item.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_item.is_valid
     */

    private Integer isValid;


    /**
     * This field corresponds to the database column monitor_item.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_item.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_item.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_item.last_modify_user
     */

    private String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_item.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @param templateId the value for monitor_item.template_id
     */

    private String templateId;


    /**
     * This field corresponds to the database column monitor_item.arg5
     * Comment: 第5个参数
     * @param arg5 the value for monitor_item.arg5
     */

    private String arg5;


    /**
     * This field corresponds to the database column monitor_item.arg6
     * Comment: 第5个参数
     * @param arg6 the value for monitor_item.arg6
     */

    private String arg6;


    /**
     * This field corresponds to the database column monitor_item.arg7
     * Comment: 第5个参数
     * @param arg7 the value for monitor_item.arg7
     */

    private String arg7;


    /**
     * This field corresponds to the database column monitor_item.arg8
     * Comment: 第8个参数
     * @param arg8 the value for monitor_item.arg8
     */

    private String arg8;


    /**
     * This field corresponds to the database column monitor_item.retry
     * Comment: 失败重试次数
     * @param retry the value for monitor_item.retry
     */

    private Integer retry;


    /**
     * This field corresponds to the database column monitor_item.item_id
     * Comment:
     * @param itemId the value for monitor_item.item_id
     */
    public void setItemId(Integer itemId){
       this.itemId = itemId;
    }

    /**
     * This field corresponds to the database column monitor_item.item_name
     * Comment:
     * @param itemName the value for monitor_item.item_name
     */
    public void setItemName(String itemName){
       this.itemName = itemName;
    }

    /**
     * This field corresponds to the database column monitor_item.description
     * Comment: 描述信息
     * @param description the value for monitor_item.description
     */
    public void setDescription(String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_item.monitor_time
     * Comment:
     * @param monitorTime the value for monitor_item.monitor_time
     */
    public void setMonitorTime(String monitorTime){
       this.monitorTime = monitorTime;
    }

    /**
     * This field corresponds to the database column monitor_item.check_interval
     * Comment:
     * @param checkInterval the value for monitor_item.check_interval
     */
    public void setCheckInterval(String checkInterval){
       this.checkInterval = checkInterval;
    }

    /**
     * This field corresponds to the database column monitor_item.alarm_count
     * Comment: 报警次数
     * @param alarmCount the value for monitor_item.alarm_count
     */
    public void setAlarmCount(Integer alarmCount){
       this.alarmCount = alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_item.alarm_interval
     * Comment: 2次报警间隔
     * @param alarmInterval the value for monitor_item.alarm_interval
     */
    public void setAlarmInterval(Integer alarmInterval){
       this.alarmInterval = alarmInterval;
    }

    /**
     * This field corresponds to the database column monitor_item.script_id
     * Comment: 参考脚本id
     * @param scriptId the value for monitor_item.script_id
     */
    public void setScriptId(Integer scriptId){
       this.scriptId = scriptId;
    }

    /**
     * This field corresponds to the database column monitor_item.arg1
     * Comment:
     * @param arg1 the value for monitor_item.arg1
     */
    public void setArg1(String arg1){
       this.arg1 = arg1;
    }

    /**
     * This field corresponds to the database column monitor_item.arg2
     * Comment:
     * @param arg2 the value for monitor_item.arg2
     */
    public void setArg2(String arg2){
       this.arg2 = arg2;
    }

    /**
     * This field corresponds to the database column monitor_item.arg3
     * Comment:
     * @param arg3 the value for monitor_item.arg3
     */
    public void setArg3(String arg3){
       this.arg3 = arg3;
    }

    /**
     * This field corresponds to the database column monitor_item.arg4
     * Comment:
     * @param arg4 the value for monitor_item.arg4
     */
    public void setArg4(String arg4){
       this.arg4 = arg4;
    }

    /**
     * This field corresponds to the database column monitor_item.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_item.is_valid
     */
    public void setIsValid(Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_item.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_item.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_item.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_item.last_modify_user
     */
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_item.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @param templateId the value for monitor_item.template_id
     */
    public void setTemplateId(String templateId){
       this.templateId = templateId;
    }

    /**
     * This field corresponds to the database column monitor_item.arg5
     * Comment: 第5个参数
     * @param arg5 the value for monitor_item.arg5
     */
    public void setArg5(String arg5){
       this.arg5 = arg5;
    }

    /**
     * This field corresponds to the database column monitor_item.arg6
     * Comment: 第5个参数
     * @param arg6 the value for monitor_item.arg6
     */
    public void setArg6(String arg6){
       this.arg6 = arg6;
    }

    /**
     * This field corresponds to the database column monitor_item.arg7
     * Comment: 第5个参数
     * @param arg7 the value for monitor_item.arg7
     */
    public void setArg7(String arg7){
       this.arg7 = arg7;
    }

    /**
     * This field corresponds to the database column monitor_item.arg8
     * Comment: 第8个参数
     * @param arg8 the value for monitor_item.arg8
     */
    public void setArg8(String arg8){
       this.arg8 = arg8;
    }

    /**
     * This field corresponds to the database column monitor_item.retry
     * Comment: 失败重试次数
     * @param retry the value for monitor_item.retry
     */
    public void setRetry(Integer retry){
       this.retry = retry;
    }

    /**
     * This field corresponds to the database column monitor_item.item_id
     * Comment:
     * @return the value of monitor_item.item_id
     */
     public Integer getItemId() {
        return itemId;
    }

    /**
     * This field corresponds to the database column monitor_item.item_name
     * Comment:
     * @return the value of monitor_item.item_name
     */
     public String getItemName() {
        return itemName;
    }

    /**
     * This field corresponds to the database column monitor_item.description
     * Comment: 描述信息
     * @return the value of monitor_item.description
     */
     public String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_item.monitor_time
     * Comment:
     * @return the value of monitor_item.monitor_time
     */
     public String getMonitorTime() {
        return monitorTime;
    }

    /**
     * This field corresponds to the database column monitor_item.check_interval
     * Comment:
     * @return the value of monitor_item.check_interval
     */
     public String getCheckInterval() {
        return checkInterval;
    }

    /**
     * This field corresponds to the database column monitor_item.alarm_count
     * Comment: 报警次数
     * @return the value of monitor_item.alarm_count
     */
     public Integer getAlarmCount() {
        return alarmCount;
    }

    /**
     * This field corresponds to the database column monitor_item.alarm_interval
     * Comment: 2次报警间隔
     * @return the value of monitor_item.alarm_interval
     */
     public Integer getAlarmInterval() {
        return alarmInterval;
    }

    /**
     * This field corresponds to the database column monitor_item.script_id
     * Comment: 参考脚本id
     * @return the value of monitor_item.script_id
     */
     public Integer getScriptId() {
        return scriptId;
    }

    /**
     * This field corresponds to the database column monitor_item.arg1
     * Comment:
     * @return the value of monitor_item.arg1
     */
     public String getArg1() {
        return arg1;
    }

    /**
     * This field corresponds to the database column monitor_item.arg2
     * Comment:
     * @return the value of monitor_item.arg2
     */
     public String getArg2() {
        return arg2;
    }

    /**
     * This field corresponds to the database column monitor_item.arg3
     * Comment:
     * @return the value of monitor_item.arg3
     */
     public String getArg3() {
        return arg3;
    }

    /**
     * This field corresponds to the database column monitor_item.arg4
     * Comment:
     * @return the value of monitor_item.arg4
     */
     public String getArg4() {
        return arg4;
    }

    /**
     * This field corresponds to the database column monitor_item.is_valid
     * Comment: 是否有效
     * @return the value of monitor_item.is_valid
     */
     public Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_item.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_item.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_item.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_item.last_modify_user
     */
     public String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_item.template_id
     * Comment: 使用模板,可以使用多个用逗号分隔
     * @return the value of monitor_item.template_id
     */
     public String getTemplateId() {
        return templateId;
    }

    /**
     * This field corresponds to the database column monitor_item.arg5
     * Comment: 第5个参数
     * @return the value of monitor_item.arg5
     */
     public String getArg5() {
        return arg5;
    }

    /**
     * This field corresponds to the database column monitor_item.arg6
     * Comment: 第5个参数
     * @return the value of monitor_item.arg6
     */
     public String getArg6() {
        return arg6;
    }

    /**
     * This field corresponds to the database column monitor_item.arg7
     * Comment: 第5个参数
     * @return the value of monitor_item.arg7
     */
     public String getArg7() {
        return arg7;
    }

    /**
     * This field corresponds to the database column monitor_item.arg8
     * Comment: 第8个参数
     * @return the value of monitor_item.arg8
     */
     public String getArg8() {
        return arg8;
    }

    /**
     * This field corresponds to the database column monitor_item.retry
     * Comment: 失败重试次数
     * @return the value of monitor_item.retry
     */
     public Integer getRetry() {
        return retry;
    }
}
