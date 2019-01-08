/**
 * @project: with
 * @Title: UserPartyFilter.java
 * @Package: com.transing.mcss4dpm.web.filter
 * <p/>
 * Copyright (c) 2014-2017 Jeeframework.com Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.filter;

/**
 * 用户更新对象
 * <p>
 *
 * @author lance
 * @version 1.0 2015-3-4 下午08:07:47
 * @Description: 用户更新对象
 */
public class UpdateBossUserFilter {
    private Long uid;
    private String newPassword;
    private String avatar;
    private String nickName;
    private String description;

    private String email;
    private Integer type;

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
