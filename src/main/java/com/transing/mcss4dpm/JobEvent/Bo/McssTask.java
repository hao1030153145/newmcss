package com.transing.mcss4dpm.JobEvent.Bo;


import com.transing.mcss4dpm.JobEvent.Bo.impl.ZookperTaskImpl;

public class McssTask extends ZookperTaskImpl {
    private String subTaskId;
    private String taskId;
    private String dataTypeId;
    private String workFlowId;

    public String getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(String workFlowId) {
        this.workFlowId = workFlowId;
    }

    public String getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(String subTaskId) {
        this.subTaskId = subTaskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }
}
