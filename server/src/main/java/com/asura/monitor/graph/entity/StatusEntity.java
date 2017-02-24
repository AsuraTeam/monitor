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
 * 服务器单机树形实体
 * @author zhaozq
 * @version 1.0
 * @since 1.0
 */

public class StatusEntity {

    // 名字
    private String name;
    // title值
    private String title;
    // 状态图片1,2,3,4
    private int nation;
    private int key;
    // 上级
    private int boss;
    // 当前的目录
    private ArrayList<String> dirs;

    public ArrayList<String> getDirs() {
        return dirs;
    }

    public void setDirs(ArrayList<String> dirs) {
        this.dirs = dirs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNation() {
        return nation;
    }

    public void setNation(int nation) {
        this.nation = nation;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getBoss() {
        return boss;
    }

    public void setBoss(int boss) {
        this.boss = boss;
    }
}
