package com.asura.monitor.graph.entity;

import java.util.ArrayList;

/**
 * <p></p>
 *
 * <PRE>
 * <BR>	修改记录
 * <BR>-----------------------------------------------
 * <BR>	修改日期			修改人			修改内容
 * </PRE>
 *
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class BindImageEntity {
    private String name;
    private String type;
    private String unit;
    private ArrayList<Double> data;
    private int valueDecimals;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public ArrayList<Double> getData() {
        return data;
    }

    public void setData(ArrayList<Double> data) {
        this.data = data;
    }

    public int getValueDecimals() {
        return valueDecimals;
    }

    public void setValueDecimals(int valueDecimals) {
        this.valueDecimals = valueDecimals;
    }
}
