/**
 * @project: with
 * @Title: AppVersionPO.java
 * @Package: com.transing.mcss4dpm.integration.bo
 * <p>
 * Copyright (c) 2014-2017 Transing Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 程序版本号
 * <p>
 *
 * @author lance
 * @version 1.0 2015-3-3 上午11:55:06
 */
@ApiModel(value = "程序版本号数据模型")
public class AppVersionPO {
    @ApiModelProperty(value = "程序版本号",notes = "",  required = true)
    private String version; //版本号
    @ApiModelProperty(value = "程序版本号描述",notes = "",  required = true)
    private String description; //版本描述

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
