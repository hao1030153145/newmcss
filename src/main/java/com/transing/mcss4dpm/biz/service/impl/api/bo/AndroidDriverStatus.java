package com.transing.mcss4dpm.biz.service.impl.api.bo;

import io.appium.java_client.android.AndroidDriver;

public class AndroidDriverStatus {
    private AndroidDriver androidDriver;
    private String status; //0:可用,1:正在被使用,9:创建失败,10:被销毁
    private String servicePid;
    private String deviceName;
    private String bindApp;
    private Thread thread;
    private int port;
    private int bp;
    private int cp;

    public AndroidDriver getAndroidDriver() {
        return androidDriver;
    }

    public void setAndroidDriver(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServicePid() {
        return servicePid;
    }

    public void setServicePid(String servicePid) {
        this.servicePid = servicePid;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBp() {
        return bp;
    }

    public void setBp(int bp) {
        this.bp = bp;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }

    public String getBindApp() {
        return bindApp;
    }

    public void setBindApp(String bindApp) {
        this.bindApp = bindApp;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }
}
