package com.transing.mcss.biz.service.autoscript;

public class TaskPojo {
    private long id;

    private long pid;

    private String crawlContent;

    private long dataSourceId;

    private long dataTypeId;

    private String crawlStartTime;

    private String crawlEndTime;

    private String crawlTimeType;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
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

    public String getCrawlStartTime() {
        return crawlStartTime;
    }

    public void setCrawlStartTime(String crawlStartTime) {
        this.crawlStartTime = crawlStartTime;
    }

    public String getCrawlEndTime() {
        return crawlEndTime;
    }

    public void setCrawlEndTime(String crawlEndTime) {
        this.crawlEndTime = crawlEndTime;
    }

    public String getCrawlTimeType() {
        return crawlTimeType;
    }

    public void setCrawlTimeType(String crawlTimeType) {
        this.crawlTimeType = crawlTimeType;
    }
}
