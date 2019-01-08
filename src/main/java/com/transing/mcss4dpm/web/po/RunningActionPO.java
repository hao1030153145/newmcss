package com.transing.mcss4dpm.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/26
 */
@ApiModel(value = "运行脚本")
public class RunningActionPO {
    @ApiModelProperty(value = "脚本Id ")
    private int scriptId;

    public int getScriptId() {
        return scriptId;
    }

    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }
}
