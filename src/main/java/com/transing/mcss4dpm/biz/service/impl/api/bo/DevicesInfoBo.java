package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${设备信息类}
 *
 * @author haolen
 * @version 1.0 2018/1/9
 */
public class DevicesInfoBo {
    private long id;
    private String devicesName;
    private String serialNumber;
    private String registServer;
    private String funcation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDevicesName() {
        return devicesName;
    }

    public void setDevicesName(String devicesName) {
        this.devicesName = devicesName;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getRegistServer() {
        return registServer;
    }

    public void setRegistServer(String registServer) {
        this.registServer = registServer;
    }

    public String getFuncation() {
        return funcation;
    }

    public void setFuncation(String funcation) {
        this.funcation = funcation;
    }
}
