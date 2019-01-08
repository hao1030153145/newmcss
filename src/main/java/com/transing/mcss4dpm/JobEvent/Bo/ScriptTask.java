package com.transing.mcss4dpm.JobEvent.Bo;

import com.jeeframework.jeetask.task.Task;
import com.jeeframework.logicframework.integration.sao.zookeeper.BaseSaoZookeeper;

public class ScriptTask extends Task {
    private String dataTypeId;
    private String deviceId;
    private String serverName;
    private String dealClass;
    private BaseSaoZookeeper baseSaoZookeeper;

    public String getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(String dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getDealClass() {
        return dealClass;
    }

    public void setDealClass(String dealClass) {
        this.dealClass = dealClass;
    }

    public BaseSaoZookeeper getBaseSaoZookeeper() {
        return baseSaoZookeeper;
    }

    public void setBaseSaoZookeeper(BaseSaoZookeeper baseSaoZookeeper) {
        this.baseSaoZookeeper = baseSaoZookeeper;
    }
}
