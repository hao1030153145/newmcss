package com.transing.mcss.integration.bo;


public class TaskBO {
    private long id;
    private long pid;
    private String crawlContent;
    private long dataSourceId;
    private long dataTypeId;
    private String paramter;

    public TaskBO() {
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCrawlContent() {
        return crawlContent;
    }

    public void setCrawlContent(String crawlContent) {
        this.crawlContent = crawlContent;
    }

    public long getDataSourceId() {
        return dataSourceId;
    }

    public void setDataSourceId(long dataSourceId) {
        this.dataSourceId = dataSourceId;
    }

    public long getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(long dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getParamter() {
        return paramter;
    }

    public void setParamter(String paramter) {
        this.paramter = paramter;
    }
}
