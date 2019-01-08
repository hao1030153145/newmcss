package com.transing.mcss4dpm.web.po;

import com.transing.mcss4dpm.integration.bo.DevicesInf;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

@ApiModel(value = "链接设备返回 对象")
public class DevicesPO {
    @ApiModelProperty(value = "",notes = "",  required = true)
    private List<DevicesInf> data=new ArrayList<>();

    public List<DevicesInf> getData() {
        return data;
    }

    public void setData(List<DevicesInf> data) {
        this.data = data;
    }

    public String filebyte;
}
