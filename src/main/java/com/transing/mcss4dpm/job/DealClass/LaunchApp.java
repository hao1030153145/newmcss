package com.transing.mcss4dpm.job.DealClass;

import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.*;
import com.transing.mcss4dpm.util.CallRemoteServiceUtil;
import io.appium.java_client.android.AndroidDriver;
import net.sf.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ${启动,加载app}
 *
 * @author haolen
 * @version 1.0 2018/1/16
 */
public class LaunchApp {
    private static LaunchApp inst = null;

    public static LaunchApp getInstance() {
        if (inst == null) {
            inst = new LaunchApp();
        }
        return inst;
    }

    public void execue(String task) {

        String dataTypeId = null;
        String deviceId = null;

        //初始化/获取drverManager
        DriverManager2 driverManager = DriverManager2.getInstance();
        //根据数据源类型id获取启动参数
        AppiumSettingBo appiumSettingBo = driverManager.getApplicationSettingBo(dataTypeId);
        AppiumDriverManager appiumDriverManager = driverManager.getDriverByStatusAndDevicesId("0", Long.parseLong(deviceId));
        if (appiumDriverManager != null) {
            //启动service
            String servicePid = driverManager.startAppiumService(appiumDriverManager.getPort(), appiumDriverManager.getBp(), appiumDriverManager.getCp(), appiumDriverManager.getDeviceInfo().getDevicesName(), String.valueOf(appiumSettingBo.getNewCommandTimeout()));
            appiumDriverManager.setServicePid(servicePid);
            appiumDriverManager.setStatus("1");
        }

        appiumDriverManager = driverManager.getDriverByStatusAndDevicesId("1", Long.parseLong(deviceId));
        if (appiumDriverManager != null) {
            //启动client
            DevicesInfoBo devicesInfoBo = appiumDriverManager.getDeviceInfo();
            int bp = appiumDriverManager.getBp();
            int cp = appiumDriverManager.getCp();
            appiumSettingBo.setDevicesName(devicesInfoBo.getDevicesName());
            appiumSettingBo.setUdid(devicesInfoBo.getDevicesName());
            appiumSettingBo.setBootstrapPort(bp);
            appiumSettingBo.setChromedriverPort(cp);
            DesiredCapabilities desiredCapabilities = driverManager.appiumSetting(appiumSettingBo);
            if (appiumSettingBo.getNewCommandTimeout() != null && appiumSettingBo.getNewCommandTimeout() > 0) {
                appiumDriverManager.setDelay(appiumSettingBo.getNewCommandTimeout()*1000);
            } else {
                appiumDriverManager.setDelay(300*1000);
            }
            AndroidDriver androidDriver = driverManager.launchAppium(desiredCapabilities, appiumDriverManager, 10);
            appiumDriverManager.setAndroidDriver(androidDriver);
            appiumDriverManager.setBindApp(appiumSettingBo.getName());
            if (androidDriver.getSessionId() != null) {
                appiumDriverManager.setSession(androidDriver.getSessionId().toString());
            }
            appiumDriverManager.setStatus("2");

            //截图,压缩图片
            AppiumAction action = new AppiumAction();
            action.sleep(6 * 1000);
            File filerOiginal = action.getScreenImgToFile(androidDriver);
            File fileDire = new File("img");
            if (!fileDire.exists()) {
                fileDire.mkdir();
            }
            File resultFile = new File("img", "temporary.png");
            if (!resultFile.exists()) {
                try {
                    resultFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File fileImg = action.reduceImgFile(filerOiginal, resultFile, 0, 0, 0.3f);
            //获取源码
            String screenSrc = action.getScreenSrc(androidDriver);
            //获取屏幕分辨率
            ScreenSize screenSize = action.getScreenSize(androidDriver);

            //调用文件上传接口
            String baseServer = System.getProperty("mcss_url");
            String getApplicationInfoUrl2 = "/scriptRegulation/uploadingImg.json";
            Map<String, String> inputParamMap2 = new HashMap<String, String>();
            if (fileImg == null) {
                System.out.println("获取图片失败    >>>>>>>>>>   ");
                inputParamMap2.put("haveFile", "0");
            }
            System.out.println("文件大小    >>>>>>>>>>   " + fileImg.length());
            inputParamMap2.put("dataTypeId", dataTypeId);
            inputParamMap2.put("deviceId", deviceId);
            CallRemoteServiceUtil.upload2(fileImg, baseServer + getApplicationInfoUrl2, inputParamMap2);
            //调用源码上传接口
            System.out.println("源码上传    >>>>>>>>>>   ");
            String getApplicationInfoUrl = "/scriptRegulation/uploadingScreenInfo.json";
            Map<String, String> inputParamMap = new HashMap<String, String>();
            inputParamMap.put("dataTypeId", dataTypeId);
            inputParamMap.put("deviceId", deviceId);
            inputParamMap.put("height", screenSize.getHeight() + "");
            inputParamMap.put("width", screenSize.getWidth() + "");
            inputParamMap.put("src", screenSrc);
            CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), baseServer + getApplicationInfoUrl, "post", inputParamMap);
        }
    }

    /**
     * 简单描述:根据数据源类型id获取启动参数
     */
    private AppiumSettingBo getApplicationSettingBo(String dataTypeId) {
        AppiumSettingBo appiumSettingBo = new AppiumSettingBo();
        String baseServer = System.getProperty("mcss_url");
        String getApplicationInfoUrl = "/scriptRegulation/getApplicationInfo.json";
        Map<String, String> inputParamMap = new HashMap<String, String>();
        inputParamMap.put("datatypeId", dataTypeId);
        Object firstObject = CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), baseServer + getApplicationInfoUrl, "post", inputParamMap);
        if (null != firstObject) {
            KeystoreOption keystoreOption = new KeystoreOption();
            ChromeOption chromeOption = new ChromeOption();
            net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject) firstObject;
            String id = jsonObject.getString("id");
            String name = jsonObject.getString("name");
            String app = jsonObject.getString("app");
            String appPackage = jsonObject.getString("appPackage");
            String appActivity = jsonObject.getString("appActivity");
            String platformName = jsonObject.getString("platformName");
            String automationName = jsonObject.getString("automationName");
            boolean fullReset = !jsonObject.getString("fullReset").equals("0");
            boolean noReset = !jsonObject.getString("noReset").equals("0");
            boolean unicodeKeyboard = !jsonObject.getString("unicodeKeyBoard").equals("0");
            boolean resetKeyboard = !jsonObject.getString("resetKeyboard").equals("0");
            boolean autoLaunch = !jsonObject.getString("autoLaunch").equals("0");
            String newCommandTimeout = jsonObject.getString("newCommandTimeout");
            JSONObject keystoreOptionJson = jsonObject.getJSONObject("keystoreOption");
            if (keystoreOptionJson != null && keystoreOptionJson.size() > 0) {
                boolean useKeystore = !keystoreOptionJson.getString("useKeystore").equals(0);
                String keystorePath = keystoreOptionJson.getString("keystorePath");
                String keystorePassword = keystoreOptionJson.getString("keystorePassword");
                String keyAlias = keystoreOptionJson.getString("keyAlias");
                String keyPassword = keystoreOptionJson.getString("keyPassword");
                keystoreOption.setUseKeystore(useKeystore);
                keystoreOption.setKeystorePath(keystorePath);
                keystoreOption.setKeystorePassword(keystorePassword);
                keystoreOption.setKeyAlias(keyAlias);
                keystoreOption.setKeyPassword(keyPassword);
            } else {
                keystoreOption = null;
            }
            JSONObject chromeOptionJson = jsonObject.getJSONObject("chromeOption");
            if (chromeOptionJson != null && chromeOptionJson.size() > 0) {
                String androidPackage = chromeOptionJson.getString("androidPackage");
                String androidActivity = chromeOptionJson.getString("androidActivity");
                String androidProcess = chromeOptionJson.getString("androidProcess");
                boolean androidUseRunningApp = chromeOptionJson.getString("androidUseRunningApp").equals("true");
                chromeOption.setAndroidPackage(androidPackage);
                chromeOption.setAndroidActivity(androidActivity);
                chromeOption.setAndroidProcess(androidProcess);
                chromeOption.setAndroidUseRunningApp(androidUseRunningApp);
            } else {
                chromeOption = null;
            }


            appiumSettingBo.setId(Integer.parseInt(id));
            appiumSettingBo.setName(name);
            if (app == null || app.equals("")) {
                appiumSettingBo.setApp(null);
            } else {
                appiumSettingBo.setApp("app");
            }
            appiumSettingBo.setAppPackage(appPackage);
            appiumSettingBo.setAppActivity(appActivity);
            if (platformName.equalsIgnoreCase("Android") || platformName.equalsIgnoreCase("iOS")) {
                appiumSettingBo.setPlatformName(platformName);
            } else {
                appiumSettingBo.setPlatformName(null);
            }
            if (automationName.equalsIgnoreCase("Appium") || automationName.equalsIgnoreCase("Selendroid")) {
                appiumSettingBo.setAutomationName(platformName);
            } else {
                appiumSettingBo.setAutomationName(null);
            }
            appiumSettingBo.setFullReset(fullReset);
            appiumSettingBo.setNoReset(noReset);
            appiumSettingBo.setUnicodeKeyboard(unicodeKeyboard);
            appiumSettingBo.setResetKeyboard(resetKeyboard);
            appiumSettingBo.setAutoLaunch(autoLaunch);
            appiumSettingBo.setNewCommandTimeout(Integer.valueOf(newCommandTimeout));
            appiumSettingBo.setKeystoreOption(keystoreOption);
            appiumSettingBo.setChromeOption(chromeOption);
        }
        return appiumSettingBo;
    }
}
