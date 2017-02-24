package com.asura.monitor.monitor.entity;

import java.util.Map;

/**
 * Created by admin on 2016/7/23.
 */
public class CheckCountEntity {

    // 正常的数量
    private int ok;

    // 脚本的名称
    private String name;

    // 未知的数量
    private int unknown;

    // 警告的数量
    private int warning;

    // 危险的数量
    private int danger;

    // 所有的数据
    private Map<String,String> okMap;
    private Map<String,String> faildMap;
    private Map<String,String> unknownMap;
    private Map<String,String> warningMap;

    // id
    private  int id;


    public Map<String, String> getOkMap() {
        return okMap;
    }

    public void setOkMap(Map<String, String> okMap) {
        this.okMap = okMap;
    }

    public Map<String, String> getFaildMap() {
        return faildMap;
    }

    public void setFaildMap(Map<String, String> faildMap) {
        this.faildMap = faildMap;
    }

    public Map<String, String> getUnknownMap() {
        return unknownMap;
    }

    public void setUnknownMap(Map<String, String> unknownMap) {
        this.unknownMap = unknownMap;
    }

    public Map<String, String> getWarningMap() {
        return warningMap;
    }

    public void setWarningMap(Map<String, String> warningMap) {
        this.warningMap = warningMap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDanger() {
        return danger;
    }

    public void setDanger(int danger) {
        this.danger = danger;
    }

    public int getOk() {
        return ok;
    }

    public void setOk(int ok) {
        this.ok = ok;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUnknown() {
        return unknown;
    }

    public void setUnknown(int unknown) {
        this.unknown = unknown;
    }

    public int getWarning() {
        return warning;
    }

    public void setWarning(int warning) {
        this.warning = warning;
    }


}
