package com.asura.resource.entity;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  存储机柜名称，和机柜区域
 * @author zhaozq14
 * @version 1.0
 * @date 2016-07-31 17:29:56
 * @since 1.0
 */
public class CabinetScopeEntity {

    private String cabinetScope;
    private String cabinetName;

    public String getCabinetScope() {
        return cabinetScope;
    }

    public void setCabinetScope(String cabinetScope) {
        this.cabinetScope = cabinetScope;
    }

    public String getCabinetName() {
        return cabinetName;
    }

    public void setCabinetName(String cabinetName) {
        this.cabinetName = cabinetName;
    }
}
