package com.model;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Description:条件过滤
 * @Author:wx6_2
 * @Date:2017/6/22
 **/
public class GroupBy implements Serializable {
    private String type;
    private Integer count;
    private Date date;

    public GroupBy() {
    }

    public GroupBy(Integer count, Date date) {
        this.count = count;
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "GroupBy{" +
                "type='" + type + '\'' +
                ", count=" + count +
                ", date=" + date +
                '}';
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
