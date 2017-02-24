package com.asura.monitor.configure.entity;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 * 报警信息发送统计
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class MonitorMessagesSendReportEntity {
    private String messagesTime;
    private String dingCount;
    private String emailCount;
    private String mobileCount;
    private String weixinCount;

    public String getMessagesTime() {
        return messagesTime;
    }

    public void setMessagesTime(String messagesTime) {
        this.messagesTime = messagesTime;
    }

    public String getDingCount() {
        return dingCount;
    }

    public void setDingCount(String dingCount) {
        this.dingCount = dingCount;
    }

    public String getEmailCount() {
        return emailCount;
    }

    public void setEmailCount(String emailCount) {
        this.emailCount = emailCount;
    }

    public String getMobileCount() {
        return mobileCount;
    }

    public void setMobileCount(String mobileCount) {
        this.mobileCount = mobileCount;
    }

    public String getWeixinCount() {
        return weixinCount;
    }

    public void setWeixinCount(String weixinCount) {
        this.weixinCount = weixinCount;
    }
}
