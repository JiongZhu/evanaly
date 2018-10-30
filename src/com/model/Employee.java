package com.model;

import java.io.Serializable;

/**
 * @Description:employee
 * @Author:wx6_2
 * @Date:2017/5/19
 **/
public class Employee implements Serializable {
    /**
     *员工id
     */
    private Integer id;

    /**
     *员工工号
     */
    private String no;

    /**
     * 员工姓名
     */
    private String name;

    /**
     * 员工电话
     */
    private String phone;

    /**
     * 员工密码
     */
    private String password;

    /**
     * 员工邮箱
     */
    private String email;

    /**
     *  员工权限
     */
    private Integer authority;

    /**
     * 是否在职
     */
    private Boolean onduty;

    public Employee() {
    }

    public Employee(String no, String name, String phone, String email, Integer authority) {
        this.no = no;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.authority = authority;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOnduty(Boolean onduty) {
        this.onduty = onduty;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public Boolean getOnduty() {
        return onduty;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
