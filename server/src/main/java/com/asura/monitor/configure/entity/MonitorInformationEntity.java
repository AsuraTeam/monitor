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
 * @date 2016-08-20 17:17:43
 * @since 1.0
 */
public class MonitorInformationEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_information.information_id
     * Comment: 主键
     * @param informationId the value for monitor_information.information_id
     */

    private java.lang.Integer informationId;


    /**
     * This field corresponds to the database column monitor_information.server_id
     * Comment: 主机ip，参考资源的server_id
     * @param serverId the value for monitor_information.server_id
     */

    private java.lang.Integer serverId;


    /**
     * This field corresponds to the database column monitor_information.script_id
     * Comment: 脚本id，参考脚本id
     * @param scriptId the value for monitor_information.script_id
     */

    private java.lang.Integer scriptId;


    /**
     * This field corresponds to the database column monitor_information.last_time
     * Comment: 最近检查时间
     * @param lastTime the value for monitor_information.last_time
     */

    private java.lang.Integer lastTime;


    /**
     * This field corresponds to the database column monitor_information.next_time
     * Comment: 下次检查时间
     * @param nextTime the value for monitor_information.next_time
     */

    private java.lang.Integer nextTime;


    /**
     * This field corresponds to the database column monitor_information.severity_id
     * Comment: 报警状态，参考serverity_id
     * @param severityId the value for monitor_information.severity_id
     */

    private java.lang.Integer severityId;


    /**
     * This field corresponds to the database column monitor_information.info
     * Comment: 监控输出信息
     * @param info the value for monitor_information.info
     */

    private java.lang.String info;


    /**
     * This field corresponds to the database column monitor_information.notice_number
     * Comment: 监控报警次数
     * @param noticeNumber the value for monitor_information.notice_number
     */

    private java.lang.Integer noticeNumber;


    /**
     * This field corresponds to the database column monitor_information.server_address
     * Comment: 执行监控的机器
     * @param serverAddress the value for monitor_information.server_address
     */

    private java.lang.String serverAddress;


    /**
     * This field corresponds to the database column monitor_information.information_id
     * Comment: 主键
     * @param informationId the value for monitor_information.information_id
     */
    public void setInformationId(java.lang.Integer informationId){
       this.informationId = informationId;
    }

    /**
     * This field corresponds to the database column monitor_information.server_id
     * Comment: 主机ip，参考资源的server_id
     * @param serverId the value for monitor_information.server_id
     */
    public void setServerId(java.lang.Integer serverId){
       this.serverId = serverId;
    }

    /**
     * This field corresponds to the database column monitor_information.script_id
     * Comment: 脚本id，参考脚本id
     * @param scriptId the value for monitor_information.script_id
     */
    public void setScriptId(java.lang.Integer scriptId){
       this.scriptId = scriptId;
    }

    /**
     * This field corresponds to the database column monitor_information.last_time
     * Comment: 最近检查时间
     * @param lastTime the value for monitor_information.last_time
     */
    public void setLastTime(java.lang.Integer lastTime){
       this.lastTime = lastTime;
    }

    /**
     * This field corresponds to the database column monitor_information.next_time
     * Comment: 下次检查时间
     * @param nextTime the value for monitor_information.next_time
     */
    public void setNextTime(java.lang.Integer nextTime){
       this.nextTime = nextTime;
    }

    /**
     * This field corresponds to the database column monitor_information.severity_id
     * Comment: 报警状态，参考serverity_id
     * @param severityId the value for monitor_information.severity_id
     */
    public void setSeverityId(java.lang.Integer severityId){
       this.severityId = severityId;
    }

    /**
     * This field corresponds to the database column monitor_information.info
     * Comment: 监控输出信息
     * @param info the value for monitor_information.info
     */
    public void setInfo(java.lang.String info){
       this.info = info;
    }

    /**
     * This field corresponds to the database column monitor_information.notice_number
     * Comment: 监控报警次数
     * @param noticeNumber the value for monitor_information.notice_number
     */
    public void setNoticeNumber(java.lang.Integer noticeNumber){
       this.noticeNumber = noticeNumber;
    }

    /**
     * This field corresponds to the database column monitor_information.server_address
     * Comment: 执行监控的机器
     * @param serverAddress the value for monitor_information.server_address
     */
    public void setServerAddress(java.lang.String serverAddress){
       this.serverAddress = serverAddress;
    }

    /**
     * This field corresponds to the database column monitor_information.information_id
     * Comment: 主键
     * @return the value of monitor_information.information_id
     */
     public java.lang.Integer getInformationId() {
        return informationId;
    }

    /**
     * This field corresponds to the database column monitor_information.server_id
     * Comment: 主机ip，参考资源的server_id
     * @return the value of monitor_information.server_id
     */
     public java.lang.Integer getServerId() {
        return serverId;
    }

    /**
     * This field corresponds to the database column monitor_information.script_id
     * Comment: 脚本id，参考脚本id
     * @return the value of monitor_information.script_id
     */
     public java.lang.Integer getScriptId() {
        return scriptId;
    }

    /**
     * This field corresponds to the database column monitor_information.last_time
     * Comment: 最近检查时间
     * @return the value of monitor_information.last_time
     */
     public java.lang.Integer getLastTime() {
        return lastTime;
    }

    /**
     * This field corresponds to the database column monitor_information.next_time
     * Comment: 下次检查时间
     * @return the value of monitor_information.next_time
     */
     public java.lang.Integer getNextTime() {
        return nextTime;
    }

    /**
     * This field corresponds to the database column monitor_information.severity_id
     * Comment: 报警状态，参考serverity_id
     * @return the value of monitor_information.severity_id
     */
     public java.lang.Integer getSeverityId() {
        return severityId;
    }

    /**
     * This field corresponds to the database column monitor_information.info
     * Comment: 监控输出信息
     * @return the value of monitor_information.info
     */
     public java.lang.String getInfo() {
        return info;
    }

    /**
     * This field corresponds to the database column monitor_information.notice_number
     * Comment: 监控报警次数
     * @return the value of monitor_information.notice_number
     */
     public java.lang.Integer getNoticeNumber() {
        return noticeNumber;
    }

    /**
     * This field corresponds to the database column monitor_information.server_address
     * Comment: 执行监控的机器
     * @return the value of monitor_information.server_address
     */
     public java.lang.String getServerAddress() {
        return serverAddress;
    }
}
