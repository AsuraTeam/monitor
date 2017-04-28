package com.asura.monitor.graph.entity;
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
 * @date 2017-04-18 11:26:39
 * @since 1.0
 */
public class MonitorGraphAutoPlayEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_graph_auto_play.play_id
     * Comment: 主键
     * @param playId the value for monitor_graph_auto_play.play_id
     */

    private java.lang.Integer playId;


    /**
     * This field corresponds to the database column monitor_graph_auto_play.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_auto_play.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_graph_auto_play.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_graph_auto_play.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_graph_auto_play.url
     * Comment: 要播放的url
     * @param url the value for monitor_graph_auto_play.url
     */

    private java.lang.String url;


    /**
     * This field corresponds to the database column monitor_graph_auto_play.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_auto_play.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_graph_auto_play.intervals
     * Comment: 刷新间隔
     * @param intervals the value for monitor_graph_auto_play.intervals
     */

    private java.lang.Integer intervals;


    /**
     * This field corresponds to the database column monitor_graph_auto_play.play_id
     * Comment: 主键
     * @param playId the value for monitor_graph_auto_play.play_id
     */
    public void setPlayId(java.lang.Integer playId){
       this.playId = playId;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_auto_play.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.last_modify_user
     * Comment: 最近修改人
     * @param lastModifyUser the value for monitor_graph_auto_play.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.url
     * Comment: 要播放的url
     * @param url the value for monitor_graph_auto_play.url
     */
    public void setUrl(java.lang.String url){
       this.url = url;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_auto_play.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.intervals
     * Comment: 刷新间隔
     * @param intervals the value for monitor_graph_auto_play.intervals
     */
    public void setIntervals(java.lang.Integer intervals){
       this.intervals = intervals;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.play_id
     * Comment: 主键
     * @return the value of monitor_graph_auto_play.play_id
     */
     public java.lang.Integer getPlayId() {
        return playId;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.last_modify_time
     * Comment: 
     * @return the value of monitor_graph_auto_play.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.last_modify_user
     * Comment: 最近修改人
     * @return the value of monitor_graph_auto_play.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.url
     * Comment: 要播放的url
     * @return the value of monitor_graph_auto_play.url
     */
     public java.lang.String getUrl() {
        return url;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.description
     * Comment: 描述信息
     * @return the value of monitor_graph_auto_play.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_graph_auto_play.intervals
     * Comment: 刷新间隔
     * @return the value of monitor_graph_auto_play.intervals
     */
     public java.lang.Integer getIntervals() {
        return intervals;
    }
}
