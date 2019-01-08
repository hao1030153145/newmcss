package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2017/11/27
 */
public class ChromeOption {
    private String androidPackage;
    private String androidActivity;
    private String androidProcess;
    private String androidDeviceSerial;
    private Boolean androidUseRunningApp;

    public String getAndroidPackage() {
        return androidPackage;
    }

    public void setAndroidPackage(String androidPackage) {
        this.androidPackage = androidPackage;
    }

    public String getAndroidActivity() {
        return androidActivity;
    }

    public void setAndroidActivity(String androidActivity) {
        this.androidActivity = androidActivity;
    }

    public String getAndroidProcess() {
        return androidProcess;
    }

    public void setAndroidProcess(String androidProcess) {
        this.androidProcess = androidProcess;
    }

    public String getAndroidDeviceSerial() {
        return androidDeviceSerial;
    }

    public void setAndroidDeviceSerial(String androidDeviceSerial) {
        this.androidDeviceSerial = androidDeviceSerial;
    }

    public Boolean getAndroidUseRunningApp() {
        return androidUseRunningApp;
    }

    public void setAndroidUseRunningApp(Boolean androidUseRunningApp) {
        this.androidUseRunningApp = androidUseRunningApp;
    }
}
