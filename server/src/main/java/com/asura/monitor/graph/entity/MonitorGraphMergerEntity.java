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
 * @date 2017-04-10 14:23:52
 * @since 1.0
 */
public class MonitorGraphMergerEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_graph_merger.graph_id
     * Comment: 
     * @param graphId the value for monitor_graph_merger.graph_id
     */

    private java.lang.Integer graphId;


    /**
     * This field corresponds to the database column monitor_graph_merger.groups
     * Comment: 图片组
     * @param groups the value for monitor_graph_merger.groups
     */

    private java.lang.String groups;


    /**
     * This field corresponds to the database column monitor_graph_merger.names
     * Comment: 数据指标
     * @param names the value for monitor_graph_merger.names
     */

    private java.lang.String names;


    /**
     * This field corresponds to the database column monitor_graph_merger.ip
     * Comment: ip地址
     * @param ip the value for monitor_graph_merger.ip
     */

    private java.lang.String ip;


    /**
     * This field corresponds to the database column monitor_graph_merger.title
     * Comment: 图像标题
     * @param title the value for monitor_graph_merger.title
     */

    private java.lang.String title;


    /**
     * This field corresponds to the database column monitor_graph_merger.shared
     * Comment: 是否共享鼠标移动线
     * @param shared the value for monitor_graph_merger.shared
     */

    private java.lang.String shared;


    /**
     * This field corresponds to the database column monitor_graph_merger.lengend_show
     * Comment: 是否显示图例
     * @param lengendShow the value for monitor_graph_merger.lengend_show
     */

    private java.lang.String lengendShow;


    /**
     * This field corresponds to the database column monitor_graph_merger.units
     * Comment: 单位
     * @param units the value for monitor_graph_merger.units
     */

    private java.lang.String units;


    /**
     * This field corresponds to the database column monitor_graph_merger.alarm_base
     * Comment: 图像监控基础线
     * @param alarmBase the value for monitor_graph_merger.alarm_base
     */

    private java.lang.String alarmBase;


    /**
     * This field corresponds to the database column monitor_graph_merger.series_marker
     * Comment: 线条是否有标记点
     * @param seriesMarker the value for monitor_graph_merger.series_marker
     */

    private java.lang.String seriesMarker;


    /**
     * This field corresponds to the database column monitor_graph_merger.width
     * Comment: 图像宽度
     * @param width the value for monitor_graph_merger.width
     */

    private java.lang.String width;


    /**
     * This field corresponds to the database column monitor_graph_merger.height
     * Comment: 图像高度
     * @param height the value for monitor_graph_merger.height
     */

    private java.lang.String height;


    /**
     * This field corresponds to the database column monitor_graph_merger.colors
     * Comment: 图像颜色
     * @param colors the value for monitor_graph_merger.colors
     */

    private java.lang.String colors;


    /**
     * This field corresponds to the database column monitor_graph_merger.refresh_interval
     * Comment: 刷新间隔
     * @param refreshInterval the value for monitor_graph_merger.refresh_interval
     */

    private java.lang.String refreshInterval;


    /**
     * This field corresponds to the database column monitor_graph_merger.last_time
     * Comment: 最近时间毫秒
     * @param lastTime the value for monitor_graph_merger.last_time
     */

    private java.lang.String lastTime;


    /**
     * This field corresponds to the database column monitor_graph_merger.calc
     * Comment: 对数据进行处理公式, 20/20/20
     * @param calc the value for monitor_graph_merger.calc
     */

    private java.lang.String calc;


    /**
     * This field corresponds to the database column monitor_graph_merger.ip_title
     * Comment: 是否在title显示IP地址
     * @param ipTitle the value for monitor_graph_merger.ip_title
     */

    private java.lang.String ipTitle;


    /**
     * This field corresponds to the database column monitor_graph_merger.legend_align
     * Comment: 图例对齐方式
     * @param legendAlign the value for monitor_graph_merger.legend_align
     */

    private java.lang.String legendAlign;


    /**
     * This field corresponds to the database column monitor_graph_merger.legend_layout
     * Comment: 图例布局,vertical|
     * @param legendLayout the value for monitor_graph_merger.legend_layout
     */

    private java.lang.String legendLayout;


    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_merger.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_graph_merger.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_graph_merger.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_merger.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_graph_merger.graph_id
     * Comment: 
     * @param graphId the value for monitor_graph_merger.graph_id
     */
    public void setGraphId(java.lang.Integer graphId){
       this.graphId = graphId;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.groups
     * Comment: 图片组
     * @param groups the value for monitor_graph_merger.groups
     */
    public void setGroups(java.lang.String groups){
       this.groups = groups;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.names
     * Comment: 数据指标
     * @param names the value for monitor_graph_merger.names
     */
    public void setNames(java.lang.String names){
       this.names = names;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.ip
     * Comment: ip地址
     * @param ip the value for monitor_graph_merger.ip
     */
    public void setIp(java.lang.String ip){
       this.ip = ip;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.title
     * Comment: 图像标题
     * @param title the value for monitor_graph_merger.title
     */
    public void setTitle(java.lang.String title){
       this.title = title;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.shared
     * Comment: 是否共享鼠标移动线
     * @param shared the value for monitor_graph_merger.shared
     */
    public void setShared(java.lang.String shared){
       this.shared = shared;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.lengend_show
     * Comment: 是否显示图例
     * @param lengendShow the value for monitor_graph_merger.lengend_show
     */
    public void setLengendShow(java.lang.String lengendShow){
       this.lengendShow = lengendShow;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.units
     * Comment: 单位
     * @param units the value for monitor_graph_merger.units
     */
    public void setUnits(java.lang.String units){
       this.units = units;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.alarm_base
     * Comment: 图像监控基础线
     * @param alarmBase the value for monitor_graph_merger.alarm_base
     */
    public void setAlarmBase(java.lang.String alarmBase){
       this.alarmBase = alarmBase;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.series_marker
     * Comment: 线条是否有标记点
     * @param seriesMarker the value for monitor_graph_merger.series_marker
     */
    public void setSeriesMarker(java.lang.String seriesMarker){
       this.seriesMarker = seriesMarker;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.width
     * Comment: 图像宽度
     * @param width the value for monitor_graph_merger.width
     */
    public void setWidth(java.lang.String width){
       this.width = width;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.height
     * Comment: 图像高度
     * @param height the value for monitor_graph_merger.height
     */
    public void setHeight(java.lang.String height){
       this.height = height;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.colors
     * Comment: 图像颜色
     * @param colors the value for monitor_graph_merger.colors
     */
    public void setColors(java.lang.String colors){
       this.colors = colors;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.refresh_interval
     * Comment: 刷新间隔
     * @param refreshInterval the value for monitor_graph_merger.refresh_interval
     */
    public void setRefreshInterval(java.lang.String refreshInterval){
       this.refreshInterval = refreshInterval;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_time
     * Comment: 最近时间毫秒
     * @param lastTime the value for monitor_graph_merger.last_time
     */
    public void setLastTime(java.lang.String lastTime){
       this.lastTime = lastTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.calc
     * Comment: 对数据进行处理公式, 20/20/20
     * @param calc the value for monitor_graph_merger.calc
     */
    public void setCalc(java.lang.String calc){
       this.calc = calc;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.ip_title
     * Comment: 是否在title显示IP地址
     * @param ipTitle the value for monitor_graph_merger.ip_title
     */
    public void setIpTitle(java.lang.String ipTitle){
       this.ipTitle = ipTitle;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.legend_align
     * Comment: 图例对齐方式
     * @param legendAlign the value for monitor_graph_merger.legend_align
     */
    public void setLegendAlign(java.lang.String legendAlign){
       this.legendAlign = legendAlign;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.legend_layout
     * Comment: 图例布局,vertical|
     * @param legendLayout the value for monitor_graph_merger.legend_layout
     */
    public void setLegendLayout(java.lang.String legendLayout){
       this.legendLayout = legendLayout;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_time
     * Comment: 
     * @param lastModifyTime the value for monitor_graph_merger.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_graph_merger.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.description
     * Comment: 描述信息
     * @param description the value for monitor_graph_merger.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.graph_id
     * Comment: 
     * @return the value of monitor_graph_merger.graph_id
     */
     public java.lang.Integer getGraphId() {
        return graphId;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.groups
     * Comment: 图片组
     * @return the value of monitor_graph_merger.groups
     */
     public java.lang.String getGroups() {
        return groups;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.names
     * Comment: 数据指标
     * @return the value of monitor_graph_merger.names
     */
     public java.lang.String getNames() {
        return names;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.ip
     * Comment: ip地址
     * @return the value of monitor_graph_merger.ip
     */
     public java.lang.String getIp() {
        return ip;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.title
     * Comment: 图像标题
     * @return the value of monitor_graph_merger.title
     */
     public java.lang.String getTitle() {
        return title;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.shared
     * Comment: 是否共享鼠标移动线
     * @return the value of monitor_graph_merger.shared
     */
     public java.lang.String getShared() {
        return shared;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.lengend_show
     * Comment: 是否显示图例
     * @return the value of monitor_graph_merger.lengend_show
     */
     public java.lang.String getLengendShow() {
        return lengendShow;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.units
     * Comment: 单位
     * @return the value of monitor_graph_merger.units
     */
     public java.lang.String getUnits() {
        return units;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.alarm_base
     * Comment: 图像监控基础线
     * @return the value of monitor_graph_merger.alarm_base
     */
     public java.lang.String getAlarmBase() {
        return alarmBase;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.series_marker
     * Comment: 线条是否有标记点
     * @return the value of monitor_graph_merger.series_marker
     */
     public java.lang.String getSeriesMarker() {
        return seriesMarker;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.width
     * Comment: 图像宽度
     * @return the value of monitor_graph_merger.width
     */
     public java.lang.String getWidth() {
        return width;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.height
     * Comment: 图像高度
     * @return the value of monitor_graph_merger.height
     */
     public java.lang.String getHeight() {
        return height;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.colors
     * Comment: 图像颜色
     * @return the value of monitor_graph_merger.colors
     */
     public java.lang.String getColors() {
        return colors;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.refresh_interval
     * Comment: 刷新间隔
     * @return the value of monitor_graph_merger.refresh_interval
     */
     public java.lang.String getRefreshInterval() {
        return refreshInterval;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_time
     * Comment: 最近时间毫秒
     * @return the value of monitor_graph_merger.last_time
     */
     public java.lang.String getLastTime() {
        return lastTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.calc
     * Comment: 对数据进行处理公式, 20/20/20
     * @return the value of monitor_graph_merger.calc
     */
     public java.lang.String getCalc() {
        return calc;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.ip_title
     * Comment: 是否在title显示IP地址
     * @return the value of monitor_graph_merger.ip_title
     */
     public java.lang.String getIpTitle() {
        return ipTitle;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.legend_align
     * Comment: 图例对齐方式
     * @return the value of monitor_graph_merger.legend_align
     */
     public java.lang.String getLegendAlign() {
        return legendAlign;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.legend_layout
     * Comment: 图例布局,vertical|
     * @return the value of monitor_graph_merger.legend_layout
     */
     public java.lang.String getLegendLayout() {
        return legendLayout;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_time
     * Comment: 
     * @return the value of monitor_graph_merger.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_graph_merger.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_graph_merger.description
     * Comment: 描述信息
     * @return the value of monitor_graph_merger.description
     */
     public java.lang.String getDescription() {
        return description;
    }
}
