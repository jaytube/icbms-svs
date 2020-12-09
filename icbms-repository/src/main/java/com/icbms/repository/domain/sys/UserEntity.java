package com.icbms.repository.domain.sys;

import com.icbms.repository.domain.base.BaseEntity;

import java.io.Serializable;
import java.util.List;

public class UserEntity extends BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 角色ID列表
     */
    private List<String> roleIdList;
    /**
     * 项目ID列表
     */
    private List<String> projectIdList;

    //id主键
    private String id;
    //用户名
    private String userName;
    //登陆帐户
    private String loginName;
    //密码
    private String passWord;
    //状态(0正常 -1禁用)
    private String status;
    //电话
    private String phone;
    //头像
    private String photo;
    //邮箱
    private String email;
    //salt盐加密
    private String salt;
    //所属项目，项目多个以逗号隔开
    private String projectIds;

    /**
     * 新密码
     */
    private String newPassWord;

    /**
     * 部门ids 部门数据权限
     */
    private String baids;
    /**
     * 机构ids 机构数据权限
     */
    private String bapids;

    /**
     * 设置：id主键
     */
    public void setId(String id) {
        this.id = id;
    }
    /**
     * 获取：id主键
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }
    /**
     * 获取：用户名
     */
    public String getUserName() {
        return userName;
    }
    /**
     * 设置：登陆帐户
     */
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    /**
     * 获取：登陆帐户
     */
    public String getLoginName() {
        return loginName;
    }
    /**
     * 设置：密码
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    /**
     * 获取：密码
     */
    public String getPassWord() {
        return passWord;
    }
    /**
     * 设置：状态(0正常 -1禁用)
     */
    public void setStatus(String status) {
        this.status = status;
    }
    /**
     * 获取：状态(0正常 -1禁用)
     */
    public String getStatus() {
        return status;
    }
    /**
     * 设置：电话
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * 获取：电话
     */
    public String getPhone() {
        return phone;
    }
    /**
     * 设置：头像
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    /**
     * 获取：头像
     */
    public String getPhoto() {
        return photo;
    }
    /**
     * 设置：邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * 获取：邮箱
     */
    public String getEmail() {
        return email;
    }

    public List<String> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<String> roleIdList) {
        this.roleIdList = roleIdList;
    }

    public String getNewPassWord() {
        return newPassWord;
    }

    public void setNewPassWord(String newPassWord) {
        this.newPassWord = newPassWord;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getBaids() {
        return baids;
    }

    public void setBaids(String baids) {
        this.baids = baids;
    }

    public String getBapids() {
        return bapids;
    }

    public void setBapids(String bapids) {
        this.bapids = bapids;
    }
    public String getProjectIds() {
        return projectIds;
    }
    public void setProjectIds(String projectIds) {
        this.projectIds = projectIds;
    }
    public List<String> getProjectIdList() {
        return projectIdList;
    }
    public void setProjectIdList(List<String> projectIdList) {
        this.projectIdList = projectIdList;
    }



}
