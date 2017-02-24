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
 * @date 2016-08-05 21:27:24
 * @since 1.0
 */
public class CmdbGraphNameEntity extends BaseEntity{
    /**
     * This field corresponds to the database column cmdb_graph_name.name_id
     * Comment: 
     * @param nameId the value for cmdb_graph_name.name_id
     */

    private java.lang.Integer nameId;


    /**
     * This field corresponds to the database column cmdb_graph_name.name
     * Comment: 各种东西的名字
     * @param name the value for cmdb_graph_name.name
     */

    private java.lang.String name;


    /**
     * This field corresponds to the database column cmdb_graph_name.name_id
     * Comment: 
     * @param nameId the value for cmdb_graph_name.name_id
     */
    public void setNameId(java.lang.Integer nameId){
       this.nameId = nameId;
    }

    /**
     * This field corresponds to the database column cmdb_graph_name.name
     * Comment: 各种东西的名字
     * @param name the value for cmdb_graph_name.name
     */
    public void setName(java.lang.String name){
       this.name = name;
    }

    /**
     * This field corresponds to the database column cmdb_graph_name.name_id
     * Comment: 
     * @return the value of cmdb_graph_name.name_id
     */
     public java.lang.Integer getNameId() {
        return nameId;
    }

    /**
     * This field corresponds to the database column cmdb_graph_name.name
     * Comment: 各种东西的名字
     * @return the value of cmdb_graph_name.name
     */
     public java.lang.String getName() {
        return name;
    }
}
