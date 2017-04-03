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
 * @date 2017-04-02 05:47:52
 * @since 1.0
 */
public class MonitorImagesMergerEntity extends BaseEntity{
    /**
     * This field corresponds to the database column monitor_images_merger.image_id
     * Comment: 主键
     * @param imageId the value for monitor_images_merger.image_id
     */

    private java.lang.Integer imageId;


    /**
     * This field corresponds to the database column monitor_images_merger.image_tp
     * Comment: 图像类型
     * @param imageTp the value for monitor_images_merger.image_tp
     */

    private java.lang.String imageTp;


    /**
     * This field corresponds to the database column monitor_images_merger.image_left
     * Comment: 图像左面位置
     * @param imageLeft the value for monitor_images_merger.image_left
     */

    private java.lang.Integer imageLeft;


    /**
     * This field corresponds to the database column monitor_images_merger.image_top
     * Comment: 图像上面位置
     * @param imageTop the value for monitor_images_merger.image_top
     */

    private java.lang.Integer imageTop;


    /**
     * This field corresponds to the database column monitor_images_merger.image_to
     * Comment: 所属图像
     * @param imageTo the value for monitor_images_merger.image_to
     */

    private java.lang.String imageTo;


    /**
     * This field corresponds to the database column monitor_images_merger.is_grid
     * Comment: 是否显示网格
     * @param isGrid the value for monitor_images_merger.is_grid
     */

    private java.lang.String isGrid;


    /**
     * This field corresponds to the database column monitor_images_merger.image_grid_size
     * Comment: 网格粗细
     * @param imageGridSize the value for monitor_images_merger.image_grid_size
     */

    private java.lang.String imageGridSize;


    /**
     * This field corresponds to the database column monitor_images_merger.is_legend
     * Comment: 是否显示图例
     * @param isLegend the value for monitor_images_merger.is_legend
     */

    private java.lang.String isLegend;


    /**
     * This field corresponds to the database column monitor_images_merger.image_legend_location
     * Comment: 图例位置
     * @param imageLegendLocation the value for monitor_images_merger.image_legend_location
     */

    private java.lang.String imageLegendLocation;


    /**
     * This field corresponds to the database column monitor_images_merger.image_data_source
     * Comment: 数据源
     * @param imageDataSource the value for monitor_images_merger.image_data_source
     */

    private java.lang.String imageDataSource;


    /**
     * This field corresponds to the database column monitor_images_merger.image_data_tp
     * Comment: 数据类型
     * @param imageDataTp the value for monitor_images_merger.image_data_tp
     */

    private java.lang.String imageDataTp;


    /**
     * This field corresponds to the database column monitor_images_merger.image_background_colr
     * Comment: 背景颜色
     * @param imageBackgroundColr the value for monitor_images_merger.image_background_colr
     */

    private java.lang.String imageBackgroundColr;


    /**
     * This field corresponds to the database column monitor_images_merger.image_color
     * Comment: 图像颜色
     * @param imageColor the value for monitor_images_merger.image_color
     */

    private java.lang.String imageColor;


    /**
     * This field corresponds to the database column monitor_images_merger.image_name
     * Comment: 图像名称
     * @param imageName the value for monitor_images_merger.image_name
     */

    private java.lang.String imageName;


    /**
     * This field corresponds to the database column monitor_images_merger.image_line_size
     * Comment: 线条粗细
     * @param imageLineSize the value for monitor_images_merger.image_line_size
     */

    private java.lang.String imageLineSize;


    /**
     * This field corresponds to the database column monitor_images_merger.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_images_merger.last_modify_time
     */

    private java.lang.String lastModifyTime;


    /**
     * This field corresponds to the database column monitor_images_merger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_images_merger.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column monitor_images_merger.description
     * Comment: 描述信息
     * @param description the value for monitor_images_merger.description
     */

    private java.lang.String description;


    /**
     * This field corresponds to the database column monitor_images_merger.tilte
     * Comment: 图像title
     * @param tilte the value for monitor_images_merger.tilte
     */

    private java.lang.String tilte;


    /**
     * This field corresponds to the database column monitor_images_merger.images_width
     * Comment: 图像宽度
     * @param imagesWidth the value for monitor_images_merger.images_width
     */

    private java.lang.String imagesWidth;


    /**
     * This field corresponds to the database column monitor_images_merger.images_height
     * Comment: 图像高度
     * @param imagesHeight the value for monitor_images_merger.images_height
     */

    private java.lang.String imagesHeight;


    /**
     * This field corresponds to the database column monitor_images_merger.images_data_interval
     * Comment: 图像数据时长
     * @param imagesDataInterval the value for monitor_images_merger.images_data_interval
     */

    private java.lang.String imagesDataInterval;


    /**
     * This field corresponds to the database column monitor_images_merger.images_left_title
     * Comment: 左面title, x轴
     * @param imagesLeftTitle the value for monitor_images_merger.images_left_title
     */

    private java.lang.String imagesLeftTitle;


    /**
     * This field corresponds to the database column monitor_images_merger.images_sub_title
     * Comment: 子title
     * @param imagesSubTitle the value for monitor_images_merger.images_sub_title
     */

    private java.lang.String imagesSubTitle;


    /**
     * This field corresponds to the database column monitor_images_merger.image_id
     * Comment: 主键
     * @param imageId the value for monitor_images_merger.image_id
     */
    public void setImageId(java.lang.Integer imageId){
       this.imageId = imageId;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_tp
     * Comment: 图像类型
     * @param imageTp the value for monitor_images_merger.image_tp
     */
    public void setImageTp(java.lang.String imageTp){
       this.imageTp = imageTp;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_left
     * Comment: 图像左面位置
     * @param imageLeft the value for monitor_images_merger.image_left
     */
    public void setImageLeft(java.lang.Integer imageLeft){
       this.imageLeft = imageLeft;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_top
     * Comment: 图像上面位置
     * @param imageTop the value for monitor_images_merger.image_top
     */
    public void setImageTop(java.lang.Integer imageTop){
       this.imageTop = imageTop;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_to
     * Comment: 所属图像
     * @param imageTo the value for monitor_images_merger.image_to
     */
    public void setImageTo(java.lang.String imageTo){
       this.imageTo = imageTo;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.is_grid
     * Comment: 是否显示网格
     * @param isGrid the value for monitor_images_merger.is_grid
     */
    public void setIsGrid(java.lang.String isGrid){
       this.isGrid = isGrid;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_grid_size
     * Comment: 网格粗细
     * @param imageGridSize the value for monitor_images_merger.image_grid_size
     */
    public void setImageGridSize(java.lang.String imageGridSize){
       this.imageGridSize = imageGridSize;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.is_legend
     * Comment: 是否显示图例
     * @param isLegend the value for monitor_images_merger.is_legend
     */
    public void setIsLegend(java.lang.String isLegend){
       this.isLegend = isLegend;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_legend_location
     * Comment: 图例位置
     * @param imageLegendLocation the value for monitor_images_merger.image_legend_location
     */
    public void setImageLegendLocation(java.lang.String imageLegendLocation){
       this.imageLegendLocation = imageLegendLocation;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_data_source
     * Comment: 数据源
     * @param imageDataSource the value for monitor_images_merger.image_data_source
     */
    public void setImageDataSource(java.lang.String imageDataSource){
       this.imageDataSource = imageDataSource;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_data_tp
     * Comment: 数据类型
     * @param imageDataTp the value for monitor_images_merger.image_data_tp
     */
    public void setImageDataTp(java.lang.String imageDataTp){
       this.imageDataTp = imageDataTp;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_background_colr
     * Comment: 背景颜色
     * @param imageBackgroundColr the value for monitor_images_merger.image_background_colr
     */
    public void setImageBackgroundColr(java.lang.String imageBackgroundColr){
       this.imageBackgroundColr = imageBackgroundColr;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_color
     * Comment: 图像颜色
     * @param imageColor the value for monitor_images_merger.image_color
     */
    public void setImageColor(java.lang.String imageColor){
       this.imageColor = imageColor;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_name
     * Comment: 图像名称
     * @param imageName the value for monitor_images_merger.image_name
     */
    public void setImageName(java.lang.String imageName){
       this.imageName = imageName;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_line_size
     * Comment: 线条粗细
     * @param imageLineSize the value for monitor_images_merger.image_line_size
     */
    public void setImageLineSize(java.lang.String imageLineSize){
       this.imageLineSize = imageLineSize;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.last_modify_time
     * Comment: 最近修改时间
     * @param lastModifyTime the value for monitor_images_merger.last_modify_time
     */
    public void setLastModifyTime(java.lang.String lastModifyTime){
       this.lastModifyTime = lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.last_modify_user
     * Comment: 最近修改用户
     * @param lastModifyUser the value for monitor_images_merger.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.description
     * Comment: 描述信息
     * @param description the value for monitor_images_merger.description
     */
    public void setDescription(java.lang.String description){
       this.description = description;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.tilte
     * Comment: 图像title
     * @param tilte the value for monitor_images_merger.tilte
     */
    public void setTilte(java.lang.String tilte){
       this.tilte = tilte;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_width
     * Comment: 图像宽度
     * @param imagesWidth the value for monitor_images_merger.images_width
     */
    public void setImagesWidth(java.lang.String imagesWidth){
       this.imagesWidth = imagesWidth;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_height
     * Comment: 图像高度
     * @param imagesHeight the value for monitor_images_merger.images_height
     */
    public void setImagesHeight(java.lang.String imagesHeight){
       this.imagesHeight = imagesHeight;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_data_interval
     * Comment: 图像数据时长
     * @param imagesDataInterval the value for monitor_images_merger.images_data_interval
     */
    public void setImagesDataInterval(java.lang.String imagesDataInterval){
       this.imagesDataInterval = imagesDataInterval;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_left_title
     * Comment: 左面title, x轴
     * @param imagesLeftTitle the value for monitor_images_merger.images_left_title
     */
    public void setImagesLeftTitle(java.lang.String imagesLeftTitle){
       this.imagesLeftTitle = imagesLeftTitle;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_sub_title
     * Comment: 子title
     * @param imagesSubTitle the value for monitor_images_merger.images_sub_title
     */
    public void setImagesSubTitle(java.lang.String imagesSubTitle){
       this.imagesSubTitle = imagesSubTitle;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_id
     * Comment: 主键
     * @return the value of monitor_images_merger.image_id
     */
     public java.lang.Integer getImageId() {
        return imageId;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_tp
     * Comment: 图像类型
     * @return the value of monitor_images_merger.image_tp
     */
     public java.lang.String getImageTp() {
        return imageTp;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_left
     * Comment: 图像左面位置
     * @return the value of monitor_images_merger.image_left
     */
     public java.lang.Integer getImageLeft() {
        return imageLeft;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_top
     * Comment: 图像上面位置
     * @return the value of monitor_images_merger.image_top
     */
     public java.lang.Integer getImageTop() {
        return imageTop;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_to
     * Comment: 所属图像
     * @return the value of monitor_images_merger.image_to
     */
     public java.lang.String getImageTo() {
        return imageTo;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.is_grid
     * Comment: 是否显示网格
     * @return the value of monitor_images_merger.is_grid
     */
     public java.lang.String getIsGrid() {
        return isGrid;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_grid_size
     * Comment: 网格粗细
     * @return the value of monitor_images_merger.image_grid_size
     */
     public java.lang.String getImageGridSize() {
        return imageGridSize;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.is_legend
     * Comment: 是否显示图例
     * @return the value of monitor_images_merger.is_legend
     */
     public java.lang.String getIsLegend() {
        return isLegend;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_legend_location
     * Comment: 图例位置
     * @return the value of monitor_images_merger.image_legend_location
     */
     public java.lang.String getImageLegendLocation() {
        return imageLegendLocation;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_data_source
     * Comment: 数据源
     * @return the value of monitor_images_merger.image_data_source
     */
     public java.lang.String getImageDataSource() {
        return imageDataSource;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_data_tp
     * Comment: 数据类型
     * @return the value of monitor_images_merger.image_data_tp
     */
     public java.lang.String getImageDataTp() {
        return imageDataTp;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_background_colr
     * Comment: 背景颜色
     * @return the value of monitor_images_merger.image_background_colr
     */
     public java.lang.String getImageBackgroundColr() {
        return imageBackgroundColr;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_color
     * Comment: 图像颜色
     * @return the value of monitor_images_merger.image_color
     */
     public java.lang.String getImageColor() {
        return imageColor;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_name
     * Comment: 图像名称
     * @return the value of monitor_images_merger.image_name
     */
     public java.lang.String getImageName() {
        return imageName;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.image_line_size
     * Comment: 线条粗细
     * @return the value of monitor_images_merger.image_line_size
     */
     public java.lang.String getImageLineSize() {
        return imageLineSize;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.last_modify_time
     * Comment: 最近修改时间
     * @return the value of monitor_images_merger.last_modify_time
     */
     public java.lang.String getLastModifyTime() {
        return lastModifyTime;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.last_modify_user
     * Comment: 最近修改用户
     * @return the value of monitor_images_merger.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.description
     * Comment: 描述信息
     * @return the value of monitor_images_merger.description
     */
     public java.lang.String getDescription() {
        return description;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.tilte
     * Comment: 图像title
     * @return the value of monitor_images_merger.tilte
     */
     public java.lang.String getTilte() {
        return tilte;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_width
     * Comment: 图像宽度
     * @return the value of monitor_images_merger.images_width
     */
     public java.lang.String getImagesWidth() {
        return imagesWidth;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_height
     * Comment: 图像高度
     * @return the value of monitor_images_merger.images_height
     */
     public java.lang.String getImagesHeight() {
        return imagesHeight;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_data_interval
     * Comment: 图像数据时长
     * @return the value of monitor_images_merger.images_data_interval
     */
     public java.lang.String getImagesDataInterval() {
        return imagesDataInterval;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_left_title
     * Comment: 左面title, x轴
     * @return the value of monitor_images_merger.images_left_title
     */
     public java.lang.String getImagesLeftTitle() {
        return imagesLeftTitle;
    }

    /**
     * This field corresponds to the database column monitor_images_merger.images_sub_title
     * Comment: 子title
     * @return the value of monitor_images_merger.images_sub_title
     */
     public java.lang.String getImagesSubTitle() {
        return imagesSubTitle;
    }
}
