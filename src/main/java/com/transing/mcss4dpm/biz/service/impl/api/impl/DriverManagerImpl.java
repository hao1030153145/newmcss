package com.transing.mcss4dpm.biz.service.impl.api.impl;

import com.transing.mcss4dpm.biz.service.impl.api.bo.*;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ${设备控制类接口,如需添加新功能,请在这里添加}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public abstract class DriverManagerImpl {
    protected static Lock lock = new ReentrantLock();
    public List<AppiumDriverManager> driverManagerList = new ArrayList<>();

    public List<AppiumDriverManager> getDriverManagerList() {
        return driverManagerList;
    }

    public DesiredCapabilities appiumSetting(AppiumSettingBo appiumSettingBo) {
        DesiredCapabilities capability = new DesiredCapabilities();
        Map<String, Object> map = new HashMap<>();
        String app = appiumSettingBo.getApp();
        String appPackage = appiumSettingBo.getAppPackage();
        String appActivity = appiumSettingBo.getAppActivity();
        String devicesName = appiumSettingBo.getDevicesName();
        String udid = appiumSettingBo.getUdid();
        String platformName = appiumSettingBo.getPlatformName();
        String automationName = appiumSettingBo.getAutomationName();
        String platformVersion = appiumSettingBo.getPlatformVersion();
        Boolean fullReset = appiumSettingBo.getFullReset();
        Boolean noReset = appiumSettingBo.getNoReset();
        Integer bootstrapPort = appiumSettingBo.getBootstrapPort();
        Integer chromedriverort = appiumSettingBo.getChromedriverPort();
        Boolean unicodeKeyboard = appiumSettingBo.getUnicodeKeyboard();
        Boolean resetKeyboard = appiumSettingBo.getResetKeyboard();
        Boolean autoLaunch = appiumSettingBo.getAutoLaunch();
        Integer newCommandTimeout = appiumSettingBo.getNewCommandTimeout();
        KeystoreOption keystoreOption = appiumSettingBo.getKeystoreOption();
        boolean useKeystore;
        if (keystoreOption == null) {
            useKeystore = false;
        } else {
            useKeystore = appiumSettingBo.getKeystoreOption().getUseKeystore();
        }
        ChromeOption chromeOption = appiumSettingBo.getChromeOption();

        if (app != null) {
            map.put("app", app);
            if (autoLaunch) {
                map.put("autoLaunch", autoLaunch);
            }
        } else {
            map.put("app", "");
        }
        map.put("appPackage", appPackage);
        map.put("appActivity", appActivity);
        map.put("deviceName", devicesName);
        map.put("udid", udid);
        if (platformName != null) {
            map.put("platformName", platformName);
        }
        if (automationName != null) {
            map.put("automationName", automationName);
        }
        map.put("fullReset", fullReset);
        map.put("noReset", noReset);
        map.put("bootstrapPort", bootstrapPort);
        map.put("chromedriverPort", chromedriverort);
        map.put("unicodeKeyboard", unicodeKeyboard);
        map.put("resetKeyboard", resetKeyboard);
        map.put("newCommandTimeout", newCommandTimeout);
        map.put("platformVersion",platformVersion);
        for (String key : map.keySet()) {
            if (map.get(key) != null) {
                capability.setCapability(key, map.get(key));
            }
        }
        if (useKeystore) {
            capability.setCapability("useKeystore", true);
            capability.setCapability("keystorePath", keystoreOption.getKeystorePath());
            capability.setCapability("keystorePassword", keystoreOption.getKeystorePassword());
            capability.setCapability("keyAlias", keystoreOption.getKeyAlias());
            capability.setCapability("keyPassword", keystoreOption.getKeyPassword());
        }
        if (chromeOption != null) {
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("androidPackage", chromeOption.getAndroidPackage());
            options.setExperimentalOption("androidUseRunningApp", chromeOption.getAndroidUseRunningApp());
            options.setExperimentalOption("androidActivity", chromeOption.getAndroidActivity());
            options.setExperimentalOption("androidProcess", chromeOption.getAndroidProcess());
//            options.setExperimentalOption("androidDeviceSerial", chromeOption.getAndroidDeviceSerial());
            capability.setCapability(ChromeOptions.CAPABILITY, options);
        }

        return capability;
    }

    /**
     * 简单描述:在服务器启动 appium services
     *
     * @param capability          参数类
     * @param appiumDriverManager 启动相关信息类
     * @param waitTime            driver 无响应等待时间
     * @return
     */
    public AndroidDriver launchAppium(DesiredCapabilities capability, AppiumDriverManager appiumDriverManager, int waitTime) {
        try {
            int port = appiumDriverManager.getPort();
            AndroidDriver driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"),
                    capability);
            driver.manage().timeouts().implicitlyWait(waitTime, TimeUnit.SECONDS);
            appiumDriverManager.timeInit();
            System.out.println("启动成功");
            return driver;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 简单描述:在服务器启动 appium services
     *
     * @param port        监听的端口
     * @param bp          mobile device和appium server通信的端口
     * @param cp          ChromeDriver运行的端口
     * @param devicesName 设备名称
     * @return 启动的 node servers 进程号
     */
    public abstract String startAppiumService(int port, int bp, int cp, String devicesName, String timeout);

    /**
     * 简单描述:在服务器停止 appium services
     *
     * @param pid        进程id
     * @param deviceName 设备名称
     * @return 是否停止
     */
    public abstract boolean stopAppiumService(String pid, String deviceName);

    /**
     * 简单描述:释放设备
     *
     * @param driverManager
     * @return 是否停止
     */
    public abstract boolean releaseAppiumDriver(AppiumDriverManager driverManager);

    /**
     * 简单描述:释放设备
     *
     * @param driverManager
     * @return 是否停止
     */
    public abstract boolean releaseAppiumDriver(AppiumDriverManager driverManager,String status);

    /**
     * 简单描述:改变设备状态
     *
     * @return 链接设备的信息列表
     */
    public abstract AppiumDriverManager changeDevicesStatus(String status, String dataTypeId);

    /**
     * 简单描述:根据运行状态获取设备
     *
     * @param status AndroidDriverStatus状态
     * @return 获取到的设备
     */
    public abstract AppiumDriverManager getDriverByStatus(String status);

    /**
     * 简单描述:根据运行状态获取设备列表
     *
     * @param status AndroidDriverStatus状态
     * @return 获取到的设备
     */
    public abstract List<AppiumDriverManager> getDriverListsByStatus(String status);

    /**
     * 简单描述:根据运行状态获取设备
     *
     * @param id devicesId
     * @return 获取到的设备
     */
    public abstract AppiumDriverManager getDriverByDevicesId(long id);

    /**
     * 简单描述:根据运行状态获取设备
     *
     * @param status AndroidDriverStatus状态
     * @param id     devicesId
     * @return 获取到的设备
     */
    public abstract AppiumDriverManager getDriverByStatusAndDevicesId(String status, long id);

    /**
     * 简单描述:根据运行状态获取设备
     *
     * @param status     AndroidDriverStatus状态
     * @param dataTypeId 数据源类型id
     * @return 获取到的设备
     */
    public abstract AppiumDriverManager getDriverByStatusAndDataTypeId(String status, String dataTypeId);

    /**
     * 简单描述:根据运行状态获取设备
     *
     * @param status     AndroidDriverStatus状态
     * @return 获取到的设备
     */
    public abstract AppiumDriverManager getDriverByStatusAndDataTypeId(String status);

    /**
     * 简单描述:根据绑定的services进程id获取设备
     *
     * @param servicePid 进程Id
     * @return 获取到的设备
     */
    public abstract AndroidDriverStatus getDriverById(String servicePid);

    /**
     * 简单描述:根据设备名获取设备
     *
     * @param deviceName 设备名
     * @return 获取到的设备
     */
    public abstract AndroidDriverStatus getDriverByName(String deviceName);

    /**
     * 简单描述:根据的app进程id获取设备
     *
     * @param bindApp 绑定的app
     * @return 获取到的设备
     */
    public abstract AndroidDriverStatus getDriverByBindApp(String bindApp);

    /**
     * 简单描述:设备状态
     *
     * @param androidDriverStatus
     * @param status              待设置的状态
     * @return 获取到的设备
     */
    public abstract void setAndroidDriverStatusStatus(AndroidDriverStatus androidDriverStatus, String status);

    /**
     * 简单描述:刷新计时
     *
     * @param time 刷新时间
     * @return 获取到的设备
     */
    public void resetScheduled(Timer timer, TimerTask timerTask, long time) {
        timer.cancel();
        timer.schedule(timerTask, time);
    }
}
