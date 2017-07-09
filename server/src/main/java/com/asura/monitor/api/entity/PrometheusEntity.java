package com.asura.monitor.api.entity;

import java.util.ArrayList;

/**
 * Created by zhaoyun on 2017/7/4.
 */
public class PrometheusEntity {

    private String status;
    private ArrayList data;
    private String query;
    private String start;
    private String end;
    private String step;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }
}
