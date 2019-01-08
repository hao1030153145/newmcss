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
 * 更新删除后台管理用户数的返回
 *
 * @author lance
 * @version 1.0 2017-02-24 18:39
 */
@ApiModel(value = "更新删除后台管理用户数的数据模型")
public class UpdateBossUserPO {
    @ApiModelProperty(value = "更新生效数", notes = "", required = true)
    public long updateRows;

    public long getUpdateRows() {
        return updateRows;
    }

    public void setUpdateRows(long updateRows) {
        this.updateRows = updateRows;
    }
}
