package com.transing.mcss4dpm.web.form;

public class TaskJsonParamRequest {
    private int count;

    private String crawlFreq;

    private String datasourceName;

    private String datasourceTypeName;

    private String inputParams;

    private SubJsonParam jsonParam;

    private int status;

    private String taskName;

    private String flowId;

    private String flowDetailId;

    private String typeNo;

    private String paramType;

    private String projectId;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCrawlFreq() {
        return crawlFreq;
    }

    public void setCrawlFreq(String crawlFreq) {
        this.crawlFreq = crawlFreq;
    }

    public String getDatasourceName() {
        return datasourceName;
    }

    public void setDatasourceName(String datasourceName) {
        this.datasourceName = datasourceName;
    }

    public String getDatasourceTypeName() {
        return datasourceTypeName;
    }

    public void setDatasourceTypeName(String datasourceTypeName) {
        this.datasourceTypeName = datasourceTypeName;
    }

    public String getInputParams() {
        return inputParams;
    }

    public void setInputParams(String inputParams) {
        this.inputParams = inputParams;
    }

    public SubJsonParam getJsonParam() {
        return jsonParam;
    }

    public void setJsonParam(SubJsonParam jsonParam) {
        this.jsonParam = jsonParam;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getFlowId() {
        return flowId;
    }

    public void setFlowId(String flowId) {
        this.flowId = flowId;
    }

    public String getFlowDetailId() {
        return flowDetailId;
    }

    public void setFlowDetailId(String flowDetailId) {
        this.flowDetailId = flowDetailId;
    }

    public String getTypeNo() {
        return typeNo;
    }

    public void setTypeNo(String typeNo) {
        this.typeNo = typeNo;
    }

    public String getParamType() {
        return paramType;
    }

    public void setParamType(String paramType) {
        this.paramType = paramType;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
}
