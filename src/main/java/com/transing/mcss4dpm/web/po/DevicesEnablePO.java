package com.transing.mcss4dpm.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel(value = "可用设备返回 对象")
public class DevicesEnablePO {
    @ApiModelProperty(value = "设备id",notes = "")
    private String id;
    @ApiModelProperty(value = "", notes = "")
    private String serverName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }
}
