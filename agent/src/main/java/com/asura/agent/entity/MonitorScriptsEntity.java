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
 * @date 2016-08-20 10:37:54
 * @since 1.0
 */
public class MonitorScriptsEntity {
    /**
     * This field corresponds to the database column monitor_scripts.scripts_id
     * Comment: 主键
     * @param scriptsId the value for monitor_scripts.scripts_id
     */

    private Integer scriptsId;


    /**
     * This field corresponds to the database column monitor_scripts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_scripts.last_modify_time
     */

    private java.sql.Timestamp lastModifyTime;


    /**
     * This field corresponds to the database column monitor_scripts.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_scripts.last_modify_user
     */

    private String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_scripts.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_scripts.is_valid
     */

    private Integer isValid;


    /**
     * This field corresponds to the database column monitor_scripts.alarm_mess
     * Comment: 报警消息
     * @param alarmMess the value for monitor_scripts.alarm_mess
     */

    private String alarmMess;


    /**
     * This field corresponds to the database column monitor_scripts.file_name
     * Comment: 脚本名称
     * @param fileName the value for monitor_scripts.file_name
     */

    private String fileName;


    /**
     * This field corresponds to the database column monitor_scripts.content
     * Comment: 脚本内容
     * @param content the value for monitor_scripts.content
     */

    private String content;


    /**
     * This field corresponds to the database column monitor_scripts.recover_mess
     * Comment: 恢复信息
     * @param recoverMess the value for monitor_scripts.recover_mess
     */

    private String recoverMess;


    /**
     * This field corresponds to the database column monitor_scripts.description
     * Comment: 描述信息
     * @param description the value for monitor_scripts.description
     */

    private String description;


    /**
     * This field corresponds to the database column monitor_scripts.monitor_name
     * Comment: 监控名称
     * @param monitorName the value for monitor_scripts.monitor_name
     */

    private String monitorName;


    /**
     * This field corresponds to the database column monitor_scripts.arg1
     * Comment: 参数1
     * @param arg1 the value for monitor_scripts.arg1
     */

    private String arg1;


    /**
     * This field corresponds to the database column monitor_scripts.arg2
     * Comment: 参数2
     * @param arg2 the value for monitor_scripts.arg2
     */

    private String arg2;


    /**
     * This field corresponds to the database column monitor_scripts.arg3
     * Comment: 参数3
     * @param arg3 the value for monitor_scripts.arg3
     */

    private String arg3;


    /**
     * This field corresponds to the database column monitor_scripts.arg4
     * Comment: 参数4
     * @param arg4 the value for monitor_scripts.arg4
     */

    private String arg4;


    /**
     * This field corresponds to the database column monitor_scripts.arg5
     * Comment: 参数5
     * @param arg5 the value for monitor_scripts.arg5
     */

    private String arg5;


    /**
     * This field corresponds to the database column monitor_scripts.arg6
     * Comment: 参数6
     * @param arg6 the value for monitor_scripts.arg6
     */

    private String arg6;


    /**
     * This field corresponds to the database column monitor_scripts.arg7
     * Comment: 参数7
     * @param arg7 the value for monitor_scripts.arg7
     */

    private String arg7;


    /**
     * This field corresponds to the database column monitor_scripts.arg8
     * Comment: 参数8
     * @param arg8 the value for monitor_scripts.arg8
     */

    private String arg8;


    /**
     * This field corresponds to the database column monitor_scripts.arg8comm
     * Comment: 参数8描述
     * @param arg8comm the value for monitor_scripts.arg8comm
     */

    private String arg8comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg7comm
     * Comment: 参数7描述
     * @param arg7comm the value for monitor_scripts.arg7comm
     */

    private String arg7comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg6comm
     * Comment: 参数6描述
     * @param arg6comm the value for monitor_scripts.arg6comm
     */

    private String arg6comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg5comm
     * Comment: 参数5描述
     * @param arg5comm the value for monitor_scripts.arg5comm
     */

    private String arg5comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg4comm
     * Comment: 参数1描述
     * @param arg4comm the value for monitor_scripts.arg4comm
     */

    private String arg4comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg3comm
     * Comment: 参数2描述
     * @param arg3comm the value for monitor_scripts.arg3comm
     */

    private String arg3comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg2comm
     * Comment: 参数3描述
     * @param arg2comm the value for monitor_scripts.arg2comm
     */

    private String arg2comm;


    /**
     * This field corresponds to the database column monitor_scripts.arg1comm
     * Comment: 参数4描述
     * @param arg1comm the value for monitor_scripts.arg1comm
     */

    private String arg1comm;


    /**
     * This field corresponds to the database column monitor_scripts.anew
     * Comment: 多长时间重新发送
     * @param anew the value for monitor_scripts.anew
     */

    private Integer anew;


    /**
     * This field corresponds to the database column monitor_scripts.isrecover
     * Comment: 是否发送恢复
     * @param isrecover the value for monitor_scripts.isrecover
     */

    private String isrecover;


    /**
     * This field corresponds to the database column monitor_scripts.scripts_id
     * Comment: 主键
     * @param scriptsId the value for monitor_scripts.scripts_id
     */
    public void setScriptsId(Integer scriptsId){
       this.scriptsId = scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_scripts.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_scripts.last_modify_time
     */
    public void setLastModifyTime(java.sql.Timestamp lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_scripts.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_scripts.last_modify_user
     */
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_scripts.is_valid
     * Comment: 是否有效
     * @param isValid the value for monitor_scripts.is_valid
     */
    public void setIsValid(Integer isValid){
       this.isValid = isValid;
    }

    /**
     * This field corresponds to the database column monitor_scripts.alarm_mess
     * Comment: 报警消息
     * @param alarmMess the value for monitor_scripts.alarm_mess
     */
    public void setAlarmMess(String alarmMess){
       this.alarmMess = alarmMess;
    }

    /**
     * This field corresponds to the database column monitor_scripts.file_name
     * Comment: 脚本名称
     * @param fileName the value for monitor_scripts.file_name
     */
    public void setFileName(String fileName){
       this.fileName = fileName;
    }

    /**
     * This field corresponds to the database column monitor_scripts.content
     * Comment: 脚本内容
     * @param content the value for monitor_scripts.content
     */
    public void setContent(String content){
       this.content = content;
    }

    /**
     * This field corresponds to the database column monitor_scripts.recover_mess
     * Comment: 恢复信息
     * @param recoverMess the value for monitor_scripts.recover_mess
     */
    public void setRecoverMess(String recoverMess){
       this.recoverMess = recoverMess;
    }

    /**
     * This field corresponds to the database column monitor_scripts.description
     * Comment: 描述信息
     * @param description the value for monitor_scripts.description
     */
    public void setDescription(String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_scripts.monitor_name
     * Comment: 监控名称
     * @param monitorName the value for monitor_scripts.monitor_name
     */
    public void setMonitorName(String monitorName){
       this.monitorName = monitorName;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg1
     * Comment: 参数1
     * @param arg1 the value for monitor_scripts.arg1
     */
    public void setArg1(String arg1){
       this.arg1 = arg1;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg2
     * Comment: 参数2
     * @param arg2 the value for monitor_scripts.arg2
     */
    public void setArg2(String arg2){
       this.arg2 = arg2;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg3
     * Comment: 参数3
     * @param arg3 the value for monitor_scripts.arg3
     */
    public void setArg3(String arg3){
       this.arg3 = arg3;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg4
     * Comment: 参数4
     * @param arg4 the value for monitor_scripts.arg4
     */
    public void setArg4(String arg4){
       this.arg4 = arg4;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg5
     * Comment: 参数5
     * @param arg5 the value for monitor_scripts.arg5
     */
    public void setArg5(String arg5){
       this.arg5 = arg5;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg6
     * Comment: 参数6
     * @param arg6 the value for monitor_scripts.arg6
     */
    public void setArg6(String arg6){
       this.arg6 = arg6;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg7
     * Comment: 参数7
     * @param arg7 the value for monitor_scripts.arg7
     */
    public void setArg7(String arg7){
       this.arg7 = arg7;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg8
     * Comment: 参数8
     * @param arg8 the value for monitor_scripts.arg8
     */
    public void setArg8(String arg8){
       this.arg8 = arg8;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg8comm
     * Comment: 参数8描述
     * @param arg8comm the value for monitor_scripts.arg8comm
     */
    public void setArg8comm(String arg8comm){
       this.arg8comm = arg8comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg7comm
     * Comment: 参数7描述
     * @param arg7comm the value for monitor_scripts.arg7comm
     */
    public void setArg7comm(String arg7comm){
       this.arg7comm = arg7comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg6comm
     * Comment: 参数6描述
     * @param arg6comm the value for monitor_scripts.arg6comm
     */
    public void setArg6comm(String arg6comm){
       this.arg6comm = arg6comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg5comm
     * Comment: 参数5描述
     * @param arg5comm the value for monitor_scripts.arg5comm
     */
    public void setArg5comm(String arg5comm){
       this.arg5comm = arg5comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg4comm
     * Comment: 参数1描述
     * @param arg4comm the value for monitor_scripts.arg4comm
     */
    public void setArg4comm(String arg4comm){
       this.arg4comm = arg4comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg3comm
     * Comment: 参数2描述
     * @param arg3comm the value for monitor_scripts.arg3comm
     */
    public void setArg3comm(String arg3comm){
       this.arg3comm = arg3comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg2comm
     * Comment: 参数3描述
     * @param arg2comm the value for monitor_scripts.arg2comm
     */
    public void setArg2comm(String arg2comm){
       this.arg2comm = arg2comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg1comm
     * Comment: 参数4描述
     * @param arg1comm the value for monitor_scripts.arg1comm
     */
    public void setArg1comm(String arg1comm){
       this.arg1comm = arg1comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.anew
     * Comment: 多长时间重新发送
     * @param anew the value for monitor_scripts.anew
     */
    public void setAnew(Integer anew){
       this.anew = anew;
    }

    /**
     * This field corresponds to the database column monitor_scripts.isrecover
     * Comment: 是否发送恢复
     * @param isrecover the value for monitor_scripts.isrecover
     */
    public void setIsrecover(String isrecover){
       this.isrecover = isrecover;
    }

    /**
     * This field corresponds to the database column monitor_scripts.scripts_id
     * Comment: 主键
     * @return the value of monitor_scripts.scripts_id
     */
     public Integer getScriptsId() {
        return scriptsId;
    }

    /**
     * This field corresponds to the database column monitor_scripts.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_scripts.last_modify_time
     */
     public java.sql.Timestamp getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_scripts.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_scripts.last_modify_user
     */
     public String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_scripts.is_valid
     * Comment: 是否有效
     * @return the value of monitor_scripts.is_valid
     */
     public Integer getIsValid() {
        return isValid;
    }

    /**
     * This field corresponds to the database column monitor_scripts.alarm_mess
     * Comment: 报警消息
     * @return the value of monitor_scripts.alarm_mess
     */
     public String getAlarmMess() {
        return alarmMess;
    }

    /**
     * This field corresponds to the database column monitor_scripts.file_name
     * Comment: 脚本名称
     * @return the value of monitor_scripts.file_name
     */
     public String getFileName() {
        return fileName;
    }

    /**
     * This field corresponds to the database column monitor_scripts.content
     * Comment: 脚本内容
     * @return the value of monitor_scripts.content
     */
     public String getContent() {
        return content;
    }

    /**
     * This field corresponds to the database column monitor_scripts.recover_mess
     * Comment: 恢复信息
     * @return the value of monitor_scripts.recover_mess
     */
     public String getRecoverMess() {
        return recoverMess;
    }

    /**
     * This field corresponds to the database column monitor_scripts.description
     * Comment: 描述信息
     * @return the value of monitor_scripts.description
     */
     public String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_scripts.monitor_name
     * Comment: 监控名称
     * @return the value of monitor_scripts.monitor_name
     */
     public String getMonitorName() {
        return monitorName;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg1
     * Comment: 参数1
     * @return the value of monitor_scripts.arg1
     */
     public String getArg1() {
        return arg1;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg2
     * Comment: 参数2
     * @return the value of monitor_scripts.arg2
     */
     public String getArg2() {
        return arg2;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg3
     * Comment: 参数3
     * @return the value of monitor_scripts.arg3
     */
     public String getArg3() {
        return arg3;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg4
     * Comment: 参数4
     * @return the value of monitor_scripts.arg4
     */
     public String getArg4() {
        return arg4;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg5
     * Comment: 参数5
     * @return the value of monitor_scripts.arg5
     */
     public String getArg5() {
        return arg5;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg6
     * Comment: 参数6
     * @return the value of monitor_scripts.arg6
     */
     public String getArg6() {
        return arg6;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg7
     * Comment: 参数7
     * @return the value of monitor_scripts.arg7
     */
     public String getArg7() {
        return arg7;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg8
     * Comment: 参数8
     * @return the value of monitor_scripts.arg8
     */
     public String getArg8() {
        return arg8;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg8comm
     * Comment: 参数8描述
     * @return the value of monitor_scripts.arg8comm
     */
     public String getArg8comm() {
        return arg8comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg7comm
     * Comment: 参数7描述
     * @return the value of monitor_scripts.arg7comm
     */
     public String getArg7comm() {
        return arg7comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg6comm
     * Comment: 参数6描述
     * @return the value of monitor_scripts.arg6comm
     */
     public String getArg6comm() {
        return arg6comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg5comm
     * Comment: 参数5描述
     * @return the value of monitor_scripts.arg5comm
     */
     public String getArg5comm() {
        return arg5comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg4comm
     * Comment: 参数1描述
     * @return the value of monitor_scripts.arg4comm
     */
     public String getArg4comm() {
        return arg4comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg3comm
     * Comment: 参数2描述
     * @return the value of monitor_scripts.arg3comm
     */
     public String getArg3comm() {
        return arg3comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg2comm
     * Comment: 参数3描述
     * @return the value of monitor_scripts.arg2comm
     */
     public String getArg2comm() {
        return arg2comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.arg1comm
     * Comment: 参数4描述
     * @return the value of monitor_scripts.arg1comm
     */
     public String getArg1comm() {
        return arg1comm;
    }

    /**
     * This field corresponds to the database column monitor_scripts.anew
     * Comment: 多长时间重新发送
     * @return the value of monitor_scripts.anew
     */
     public Integer getAnew() {
        return anew;
    }

    /**
     * This field corresponds to the database column monitor_scripts.isrecover
     * Comment: 是否发送恢复
     * @return the value of monitor_scripts.isrecover
     */
     public String getIsrecover() {
        return isrecover;
    }
}
