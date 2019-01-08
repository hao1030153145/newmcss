package com.transing.mcss.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

@ApiModel(value = "通知带你程序")
public class NotifyPO {
    @ApiModelProperty(value = "待抓取的url", required = true)
    private String url;
    @ApiModelProperty(value = "任务id", required = true)
    private long taskid;
    @ApiModelProperty(value = "项目id", required = true)
    private long pid;
    @ApiModelProperty(value = "数据源id", required = true)
    private long datasourceid;
    @ApiModelProperty(value = "数据类型id", required = true)
    private long datatypeid;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTaskid() {
        return taskid;
    }

    public void setTaskid(long taskid) {
        this.taskid = taskid;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getDatasourceid() {
        return datasourceid;
    }

    public void setDatasourceid(long datasourceid) {
        this.datasourceid = datasourceid;
    }

    public long getDatatypeid() {
        return datatypeid;
    }

    public void setDatatypeid(long datatypeid) {
        this.datatypeid = datatypeid;
    }
}
