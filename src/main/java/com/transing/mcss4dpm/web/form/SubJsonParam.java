package com.transing.mcss4dpm.web.form;

import java.util.List;

public class SubJsonParam {
    private String crawlFreqType;

    private String datasourceId;

    private String datasourceName;

    private String datasourceTypeId;

    private String datasourceTypeName;

    private List<String> inputParamArray ;

    private String quartzTime;

    private int status;

    private String storageTypeTable;

    private String taskName;

    public String getCrawlFreqType() {
        return crawlFreqType;
    }

    public void setCrawlFreqType(String crawlFreqType) {
        this.crawlFreqType = crawlFreqType;
    }

    public String getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(String datasourceId) {
        this.datasourceId = datasourceId;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceTypeId() {
        return datasourceTypeId;
    }

    public void setDatasourceTypeId(String datasourceTypeId) {
        this.datasourceTypeId = datasourceTypeId;
    }

    public String getDatasourceTypeName() {
        return datasourceTypeName;
    }

    public void setDatasourceTypeName(String datasourceTypeName) {
        this.datasourceTypeName = datasourceTypeName;
    }

    public List<String> getInputParamArray() {
        return inputParamArray;
    }

    public void setInputParamArray(List<String> inputParamArray) {
        this.inputParamArray = inputParamArray;
    }

    public String getQuartzTime() {
        return quartzTime;
    }

    public void setQuartzTime(String quartzTime) {
        this.quartzTime = quartzTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStorageTypeTable() {
        return storageTypeTable;
    }

    public void setStorageTypeTable(String storageTypeTable) {
        this.storageTypeTable = storageTypeTable;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
