package com.transing.mcss.biz.service.autoscript;

public class TaskJson {
    private String crawlStartTime;

    private String crawlEndTime;

    private String crawlTimeType;

    public TaskJson() {

    }

    public TaskJson(String crawlStartTime, String crawlEndTime, String crawlTimeType) {
        this.crawlStartTime = crawlStartTime;
        this.crawlEndTime = crawlEndTime;
        this.crawlTimeType = crawlTimeType;
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
