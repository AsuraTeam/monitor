package com.asura.resource.entity;
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
 * @date 2016-07-28 11:31:44
 * @since 1.0
 */
public class CmdbResourceOsTypeEntity extends BaseEntity{

    private java.lang.String lastModifyTime;
    private String imagePath;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }


    /**
     * This field corresponds to the database column cmdb_resource_os_type.os_id
     * Comment: 操作系统id
     * @param osId the value for cmdb_resource_os_type.os_id
     */

    private java.lang.Integer osId;


    /**
     * This field corresponds to the database column cmdb_resource_os_type.os_name
     * Comment: 操作系统名称
     * @param osName the value for cmdb_resource_os_type.os_name
     */

    private java.lang.String osName;


    /**
     * This field corresponds to the database column cmdb_resource_os_type.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_os_type.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_os_type.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_os_type.create_user
     */

    private java.lang.String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_os_type.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_os_type.last_modify_user
     */

    private java.lang.String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_os_type.os_id
     * Comment: 操作系统id
     * @param osId the value for cmdb_resource_os_type.os_id
     */
    public void setOsId(java.lang.Integer osId){
       this.osId = osId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.os_name
     * Comment: 操作系统名称
     * @param osName the value for cmdb_resource_os_type.os_name
     */
    public void setOsName(java.lang.String osName){
       this.osName = osName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_os_type.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_os_type.create_user
     */
    public void setCreateUser(java.lang.String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_os_type.last_modify_user
     */
    public void setLastModifyUser(java.lang.String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.os_id
     * Comment: 操作系统id
     * @return the value of cmdb_resource_os_type.os_id
     */
     public java.lang.Integer getOsId() {
        return osId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.os_name
     * Comment: 操作系统名称
     * @return the value of cmdb_resource_os_type.os_name
     */
     public java.lang.String getOsName() {
        return osName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_os_type.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_os_type.create_user
     */
     public java.lang.String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_os_type.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_os_type.last_modify_user
     */
     public java.lang.String getLastModifyUser() {
        return lastModifyUser;
    }
}
