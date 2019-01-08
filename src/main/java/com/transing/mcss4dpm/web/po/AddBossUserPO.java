/*
 * @project: mcss4dpm
 * @package: com.transing.mcss4dpm.web.po
 * @title:   AddBossUserPO.java 
 *
 * Copyright (c) 2017 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 添加后台管理用户的返回
 *
 * @author lance
 * @version 1.0 2017-02-24 18:39
 */
@ApiModel(value = "添加后台管理用户数据模型")
public class AddBossUserPO {
    @ApiModelProperty(value = "用户id",notes = "",  required = true)
    public long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }
}
