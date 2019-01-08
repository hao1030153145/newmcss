package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${appium启动参数}
 *
 * @author haolen
 * @version 1.0 2018/1/8
 */
public class AppiumSettingBo {
    private int id;  //id
    private String name;  //应用名称
    private String app;  //安装包路径
    private String appPackage;  //启动的包名
    private String appActivity; //应用包中启动的
    private String devicesName; //运行的Android应用的包名
    private String udid;
    private String platformName;    //你要测试的手机操作系统|`iOS`, `Android`, 或 `FirefoxOS`|
    private String automationName;  //使用的自动化测试引擎|`Appium` (默认) 或 `Selendroid`|
    private Boolean fullReset;  //在 Android 上，这也会在。默认值`false`|`true`, `false`|
    private Boolean noReset;    //在会话前重置应用状态。默认值`false`。|`true`, `false`|
    private Integer bootstrapPort;  //bootstrap 端口 可能无效,在启动appium services指定即可
    private Integer chromedriverPort;  //chrome 端口 可能无效,在启动appium services指定即可
    private Boolean unicodeKeyboard;   //使用appium 输入法
    private Boolean resetKeyboard;   //重置 输入法
    private Boolean autoLaunch; //Appium是否需要自动安装(如app指定路径时)和启动应用。默认值`true`|`true`, `false`|
    private Integer newCommandTimeout;  //设置命令超时时间，单位：秒.达到超时时间仍未接收到新的命令时 Appium 会假设客户端退出然后自动结束会话。
    private KeystoreOption keystoreOption; //签名相关设置
    private ChromeOption chromeOption; //chrome相关设置

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getAppPackage() {
        return appPackage;
    }

    public void setAppPackage(String appPackage) {
        this.appPackage = appPackage;
    }

    public String getAppActivity() {
        return appActivity;
    }

    public void setAppActivity(String appActivity) {
        this.appActivity = appActivity;
    }

    public String getDevicesName() {
        return devicesName;
    }

    public void setDevicesName(String devicesName) {
        this.devicesName = devicesName;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public String getPlatformName() {
        return platformName;
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public String getAutomationName() {
        return automationName;
    }

    public void setAutomationName(String automationName) {
        this.automationName = automationName;
    }

    public Boolean getFullReset() {
        return fullReset;
    }

    public void setFullReset(Boolean fullReset) {
        this.fullReset = fullReset;
    }

    public Boolean getNoReset() {
        return noReset;
    }

    public void setNoReset(Boolean noReset) {
        this.noReset = noReset;
    }

    public Integer getBootstrapPort() {
        return bootstrapPort;
    }

    public void setBootstrapPort(Integer bootstrapPort) {
        this.bootstrapPort = bootstrapPort;
    }

    public Integer getChromedriverPort() {
        return chromedriverPort;
    }

    public void setChromedriverPort(Integer chromedriverPort) {
        this.chromedriverPort = chromedriverPort;
    }

    public Boolean getUnicodeKeyboard() {
        return unicodeKeyboard;
    }

    public void setUnicodeKeyboard(Boolean unicodeKeyboard) {
        this.unicodeKeyboard = unicodeKeyboard;
    }

    public Boolean getResetKeyboard() {
        return resetKeyboard;
    }

    public void setResetKeyboard(Boolean resetKeyboard) {
        this.resetKeyboard = resetKeyboard;
    }

    public Boolean getAutoLaunch() {
        return autoLaunch;
    }

    public void setAutoLaunch(Boolean autoLaunch) {
        this.autoLaunch = autoLaunch;
    }

    public Integer getNewCommandTimeout() {
        return newCommandTimeout;
    }

    public void setNewCommandTimeout(Integer newCommandTimeout) {
        this.newCommandTimeout = newCommandTimeout;
    }

    public KeystoreOption getKeystoreOption() {
        return keystoreOption;
    }

    public void setKeystoreOption(KeystoreOption keystoreOption) {
        this.keystoreOption = keystoreOption;
    }

    public ChromeOption getChromeOption() {
        return chromeOption;
    }

    public void setChromeOption(ChromeOption chromeOption) {
        this.chromeOption = chromeOption;
    }
}
