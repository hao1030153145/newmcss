/*
 * @project: mcss4dpm
 * @package: com.transing.mcss4dpm.integration.bo
 * @title:   UserBase.java 
 *
 * Copyright (c) 2017 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.integration.bo;

import com.jeeframework.logicframework.integration.bo.AbstractBO;

import java.util.Date;

/**
 * 用户对象基类
 *
 * @author lance
 * @version 1.0 2017-02-23 17:55
 */
public class UserBase extends AbstractBO {
    protected long uid;
    protected String passwd;
    protected String nickName;
    protected String description;
    protected String avatar;
    protected Date createTime;
    protected Date lastModifyTime;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }
}
