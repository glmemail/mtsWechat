/**
 * 
 */
package com.nrib.wechat;

/**
 * @author gaolm
 *
 *         2017/11/27
 */
public class UserDetail {
    /** 成员UserID */
    private String userid;
    /** 成员姓名 */
    private String name;
    /** 成员所属部门 */
    private org.json.JSONArray department;
    /** 职位信息 */
    private String position;
    /** 成员手机号 */
    private String mobile;
    /** 性别 */
    private String gender;
    /** 成员邮箱 */
    private String email;
    /** 头像url */
    private String avatar;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public org.json.JSONArray getDepartment() {
        return department;
    }

    public void setDepartment(org.json.JSONArray department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "UserDetail [userid=" + userid + ", name=" + name + ", department=" + department + ", position="
                + position + ", mobile=" + mobile + ", gender=" + gender + ", email=" + email + ", avatar=" + avatar
                + "]";
    }

}
