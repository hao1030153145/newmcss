package com.transing.mcss4dpm.integration.bo;

public class DevicesInf {
    private long id;
    private String devicesName;
    private String serialNumber;
    private String registServer;
    private String mobiletype;
    private int height;
    private int width;
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

    public String getMobiletype() {
        return mobiletype;
    }

    public void setMobiletype(String mobiletype) {
        this.mobiletype = mobiletype;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getFuncation() {
        return funcation;
    }

    public void setFuncation(String funcation) {
        this.funcation = funcation;
    }
}
