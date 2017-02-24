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
 * @date 2016-07-28 11:38:20
 * @since 1.0
 */
public class CmdbResourceServerTypeEntity extends BaseEntity{

    private String lastModifyTime;
    private String imagePath;
    private String depend;

    //引用的列明
    private String tname;



    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getDepend() {
        return depend;
    }

    public void setDepend(String depend) {
        this.depend = depend;
    }

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
     * This field corresponds to the database column cmdb_resource_server_type.type_id
     * Comment: 服务类型ID
     * @param typeId the value for cmdb_resource_server_type.type_id
     */

    private Integer typeId;


    /**
     * This field corresponds to the database column cmdb_resource_server_type.type_name
     * Comment: 
     * @param typeName the value for cmdb_resource_server_type.type_name
     */

    private String typeName;


    /**
     * This field corresponds to the database column cmdb_resource_server_type.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_server_type.create_time
     */

    private Integer createTime;


    /**
     * This field corresponds to the database column cmdb_resource_server_type.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_server_type.create_user
     */

    private String createUser;


    /**
     * This field corresponds to the database column cmdb_resource_server_type.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_server_type.last_modify_user
     */

    private String lastModifyUser;


    /**
     * This field corresponds to the database column cmdb_resource_server_type.type_id
     * Comment: 服务类型ID
     * @param typeId the value for cmdb_resource_server_type.type_id
     */
    public void setTypeId(java.lang.Integer typeId){
       this.typeId = typeId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.type_name
     * Comment: 
     * @param typeName the value for cmdb_resource_server_type.type_name
     */
    public void setTypeName(String typeName){
       this.typeName = typeName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.create_time
     * Comment: 创建时间
     * @param createTime the value for cmdb_resource_server_type.create_time
     */
    public void setCreateTime(Integer createTime){
       this.createTime = createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.create_user
     * Comment: 创建人
     * @param createUser the value for cmdb_resource_server_type.create_user
     */
    public void setCreateUser(String createUser){
       this.createUser = createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.last_modify_user
     * Comment: 最近修改 用户
     * @param lastModifyUser the value for cmdb_resource_server_type.last_modify_user
     */
    public void setLastModifyUser(String lastModifyUser){
       this.lastModifyUser = lastModifyUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.type_id
     * Comment: 服务类型ID
     * @return the value of cmdb_resource_server_type.type_id
     */
     public java.lang.Integer getTypeId() {
        return typeId;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.type_name
     * Comment: 
     * @return the value of cmdb_resource_server_type.type_name
     */
     public String getTypeName() {
        return typeName;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.create_time
     * Comment: 创建时间
     * @return the value of cmdb_resource_server_type.create_time
     */
     public Integer getCreateTime() {
        return createTime;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.create_user
     * Comment: 创建人
     * @return the value of cmdb_resource_server_type.create_user
     */
     public String getCreateUser() {
        return createUser;
    }

    /**
     * This field corresponds to the database column cmdb_resource_server_type.last_modify_user
     * Comment: 最近修改 用户
     * @return the value of cmdb_resource_server_type.last_modify_user
     */
     public String getLastModifyUser() {
        return lastModifyUser;
    }
}
