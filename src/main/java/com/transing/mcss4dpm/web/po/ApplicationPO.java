package com.transing.mcss4dpm.web.po;

import com.transing.mcss4dpm.biz.service.impl.api.bo.ChromeOption;
import com.transing.mcss4dpm.biz.service.impl.api.bo.KeystoreOption;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/22
 */
public class ApplicationPO {
    private int id;
    private String name;
    private String app;
    private String appPackage;
    private String appActivity;
    private String platformName;
    private String automationName;
    private int fullReset;
    private int noReset;
    private int unicodeKeyBoard;
    private int resetKeyboard;
    private int autoLaunch;
    private int newCommandTimeout;
    private KeystoreOption keystoreOption;
    private ChromeOption chromeOption;

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

    public int getFullReset() {
        return fullReset;
    }

    public void setFullReset(int fullReset) {
        this.fullReset = fullReset;
    }

    public int getNoReset() {
        return noReset;
    }

    public void setNoReset(int noReset) {
        this.noReset = noReset;
    }

    public int getUnicodeKeyBoard() {
        return unicodeKeyBoard;
    }

    public void setUnicodeKeyBoard(int unicodeKeyBoard) {
        this.unicodeKeyBoard = unicodeKeyBoard;
    }

    public int getResetKeyboard() {
        return resetKeyboard;
    }

    public void setResetKeyboard(int resetKeyboard) {
        this.resetKeyboard = resetKeyboard;
    }

    public int getAutoLaunch() {
        return autoLaunch;
    }

    public void setAutoLaunch(int autoLaunch) {
        this.autoLaunch = autoLaunch;
    }

    public int getNewCommandTimeout() {
        return newCommandTimeout;
    }

    public void setNewCommandTimeout(int newCommandTimeout) {
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
