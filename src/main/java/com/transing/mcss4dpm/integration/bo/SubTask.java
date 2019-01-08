package com.transing.mcss4dpm.integration.bo;

/**
 * 子任务对象
 */
public class SubTask {
    private long id;
    private String taskId;
    private String dealClass;
    private String dataSource;
    private String dataType;
    private String inputPara;
    private String status;//0:待执行

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDealClass() {
        return dealClass;
    }

    public void setDealClass(String dealClass) {
        this.dealClass = dealClass;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getInputPara() {
        return inputPara;
    }

    public void setInputPara(String inputPara) {
        this.inputPara = inputPara;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

