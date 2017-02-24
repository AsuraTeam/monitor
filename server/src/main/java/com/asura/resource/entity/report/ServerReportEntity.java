package com.asura.resource.entity.report;

/**
 * <p></p>
 * <p/>
 * <PRE>
 * <BR>
 * <BR>-----------------------------------------------
 * <BR>
 * </PRE>
 *  报表使用的实体对象
 * @author zhaozq14
 * @version 1.0
 * @date 2016-08-02 14:02:56
 * @since 1.0
 */
public class ServerReportEntity {

    // 图标的名字
    private String name;
    // 图表的值饼图
    private int y;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
