package com.transing.mcss4dpm.biz.service.impl.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.transing.mcss4dpm.biz.service.impl.api.bo.*;
import com.transing.mcss4dpm.biz.service.impl.api.impl.DriverManagerImpl;
import com.transing.mcss4dpm.biz.service.impl.api.impl.LinuxCommandImpl;
import com.transing.mcss4dpm.integration.bo.CrawlRegulationItemBO;
import com.transing.mcss4dpm.integration.bo.ScriptDetailBO;
import com.transing.mcss4dpm.util.CallRemoteServiceUtil;
import com.transing.mcss4dpm.util.XpathUtil;
import io.appium.java_client.android.AndroidDriver;
import net.sf.json.JSONObject;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.*;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public class DriverManager2 extends DriverManagerImpl implements LinuxCommandImpl {
    private static DriverManager2 instance = null;

    public static DriverManager2 getInstance() {
        if (instance == null) {
            instance = new DriverManager2();
        }
        return instance;
    }

    private DriverManager2() {
        DevicesManager devicesManager = new DevicesManager();
        List<String> devicesInfoBoList = devicesManager.getAndroidDevices();
        int port = 4726;
        int bp = 4724;
        int cp = 9515;
        for (int i = 0; i < devicesInfoBoList.size(); i++) {
            AppiumDriverManager appiumDriverManager = new AppiumDriverManager();
            DevicesInfoBo devicesInfoBo = new DevicesInfoBo();
            devicesInfoBo.setDevicesName(devicesInfoBoList.get(0));
            devicesInfoBo.setFuncation("taskCrawl");
            devicesInfoBo.setRegistServer("mcss_1");
            devicesInfoBo.setId((long)i);
            appiumDriverManager.setDeviceInfo(devicesInfoBo);
            appiumDriverManager.setBp(bp - i);
            appiumDriverManager.setCp(cp + i);
            appiumDriverManager.setPort(port + i);
            appiumDriverManager.setStatus("0");
            driverManagerList.add(appiumDriverManager);
        }
    }

    @Override
    public String startAppiumService(int port, int bp, int cp, String devicesName, String timeout) {
        lock.lock();
        if (timeout == null || timeout.equals("")) {
            timeout = "100";
        }
        List<String> nodeProcessIdBe = new ArrayList<>();
        List<String> nodeProcessIdAf = new ArrayList<>();
        String pid = "";
        try {
            Process processBe = Runtime.getRuntime().exec("ps -aux");
            processBe.waitFor();
            Scanner in = new Scanner(processBe.getInputStream());
            nodeProcessIdBe.addAll(getNodePidList(in));
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
        if (timeout == null || timeout.equals("")) {
            timeout = "60";
        }
        String str = "appium -p " + port + " -bp " + bp + " --chromedriver-port " + cp + " --device-name " + devicesName + " -U " + devicesName + " --command-timeout " + timeout;
        System.out.println("启动serives    >>>>>>>>>>   " + str);
        try {
            Runtime.getRuntime().exec(str);
            Thread.sleep(6 * 1000);
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
        //遍历系统进程,获取启动的node.exe进程号
        try {
            Process processAf = Runtime.getRuntime().exec("ps -aux");
            processAf.waitFor();
            Scanner in = new Scanner(processAf.getInputStream());
            nodeProcessIdAf.addAll(getNodePidList(in));
            int nodeProcessIdAfLength = nodeProcessIdAf.size();
            for (int i = nodeProcessIdAfLength - 1; i >= 0; i--) {
                String string = nodeProcessIdAf.get(i);
                if (nodeProcessIdBe.contains(string)) {
                    nodeProcessIdAf.remove(i);
                }
            }
            if (nodeProcessIdAf.size() > 0) {
                pid = nodeProcessIdAf.get(0);
            }
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        } finally {
            lock.unlock();
        }
        return pid;
    }

    @Override
    public boolean stopAppiumService(String pid, String deviceName) {
        return false;
    }

    @Override
    public boolean releaseAppiumDriver(AppiumDriverManager driverManager) {
        return releaseAppiumDriver(driverManager, "0");
    }

    @Override
    public boolean releaseAppiumDriver(AppiumDriverManager driverManager, String status) {
        //kill掉 appium-service,并检查是否kill掉,知道kill掉为止
        String pid = driverManager.getServicePid();
        String devicesName = driverManager.getDeviceInfo().getDevicesName();
        String serviceId;
        out:
        while (true) {
            serviceId = pid;
            System.out.println("删除设备nodeid    >>>>>>>>>>   " + serviceId);
            executeShell("kill -s 9 " + serviceId, true);
            ShellProcess shellProcess = executeShell("ps -aux", true);
            if (shellProcess.isSuccessful()) {
                Scanner scanner = getShellResultContent(shellProcess.getProcess());
                while (scanner.hasNext()) {
                    String processInf = scanner.nextLine();
                    if (processInf.contains("node") && processInf.contains(devicesName)) {
                        String[] strings = processInf.split("\\s+");
                        serviceId = strings[1];
                        System.out.println("删除设备nodeid_2    >>>>>>>>>>   " + serviceId);
                    } else {
                        break out;
                    }
                }
            }
        }
        if (serviceId != null && !serviceId.equals("")) {
            driverManager.setStatus(status);
            driverManager.setAndroidDriver(null);
            driverManager.setServicePid("");
            driverManager.setBindApp("");
            driverManager.setSession("");
            driverManager.cancleTimer();
        }
        return true;
    }

    @Override
    public AppiumDriverManager changeDevicesStatus(String status, String dataTypeId) {
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getStatus().equals(status) ) {
                appiumDriverManager.setStatus("8");
                return appiumDriverManager;
            }
        }
        return null;
    }

    @Override
    public AppiumDriverManager getDriverByStatus(String status) {
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getStatus().equals(status) ) {
                appiumDriverManager.setStatus("8");
                return appiumDriverManager;
            }
        }
        return null;
    }

    @Override
    public List<AppiumDriverManager> getDriverListsByStatus(String status) {

        List<AppiumDriverManager> list = new ArrayList<>();
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getStatus().equals(status) ) {
                appiumDriverManager.setStatus("8");
                list.add(appiumDriverManager);
            }
        }
        return list;
    }

    @Override
    public AppiumDriverManager getDriverByDevicesId(long id) {
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getDeviceInfo().getId() == id) {
                return appiumDriverManager;
            }
        }
        return null;
    }

    @Override
    public AppiumDriverManager getDriverByStatusAndDevicesId(String status, long id) {
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getStatus().equals(status) && appiumDriverManager.getDeviceInfo().getId() == id) {
                return appiumDriverManager;
            }
        }
        return null;
    }

    @Override
    public AppiumDriverManager getDriverByStatusAndDataTypeId(String status, String dataTypeId) {
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getStatus().equals(status) && appiumDriverManager.getDeviceInfo().getFuncation().equals("taskCrawl")) {
                appiumDriverManager.setStatus("8");
                return appiumDriverManager;
            }
        }
        return null;
    }

    @Override
    public AppiumDriverManager getDriverByStatusAndDataTypeId(String status) {
        for (AppiumDriverManager appiumDriverManager : driverManagerList) {
            if (appiumDriverManager.getStatus().equals(status) && appiumDriverManager.getDeviceInfo().getFuncation().equals("taskCrawl")) {
                appiumDriverManager.setStatus("8");
                return appiumDriverManager;
            }
        }
        return null;
    }

    @Override
    public AndroidDriverStatus getDriverById(String servicePid) {
        return null;
    }

    @Override
    public AndroidDriverStatus getDriverByName(String deviceName) {
        return null;
    }

    @Override
    public AndroidDriverStatus getDriverByBindApp(String bindApp) {
        return null;
    }

    @Override
    public void setAndroidDriverStatusStatus(AndroidDriverStatus androidDriverStatus, String status) {

    }

    private List<String> getNodePidList(Scanner in) {
        List<String> pidList = new ArrayList<>();
        while (in.hasNext()) {
            String processInf = in.nextLine();
            if (processInf.contains("node")) {
                String[] strings = processInf.split("\\s+");
                pidList.add(strings[1]);
            }
        }
        return pidList;
    }

    @Override
    public ShellProcess executeShell(String shell, boolean isWait) {
        ShellProcess shellProcess = new ShellProcess();
        try {
            Process process = Runtime.getRuntime().exec(shell);
            if (isWait) {
                process.waitFor();
                shellProcess.setProcess(process);
                shellProcess.setSuccessful(true);
            } else {
                Thread.sleep(6 * 1000);
                shellProcess.setProcess(process);
                shellProcess.setSuccessful(true);
            }
        } catch (IOException | InterruptedException e) {
            shellProcess.setProcess(null);
            shellProcess.setSuccessful(false);
            e.printStackTrace();
        }
        return shellProcess;
    }

    /**
     * 简单描述:根据数据源类型id获取脚本信息
     */
    public CrawlRegulationItemBO getCrawlRegulationByTypeid(String dataTypeId) {
        CrawlRegulationItemBO crawlRegulationItemBO = new CrawlRegulationItemBO();
        String mcssServer = System.getProperty("mcss_url");
        String getDataUrl = "/scriptRegulation/getCrawlRegulationByTypeid.json";
        Map<String, String> dataTypePassMap = new HashMap<String, String>();
        dataTypePassMap.put("datasourceTypeId", dataTypeId);
        Object firstObject = CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), mcssServer + getDataUrl, "post", dataTypePassMap);
        if (null != firstObject) {
            String jsonString = firstObject.toString();
            crawlRegulationItemBO = JSON.parseObject(jsonString, CrawlRegulationItemBO.class);
        }
        return crawlRegulationItemBO;
    }

    /**
     * 简单描述:根据数据源类型id获取脚本信息
     */
    public List<ScriptDetailBO> getScriptBytypeId(String dataTypeId) {
        List<ScriptDetailBO> scriptDetailBOList = new ArrayList<>();
        String mcssServer = System.getProperty("mcss_url");
        String getDataUrl = "/scriptRegulation/getScriptBytypeId.json";
        Map<String, String> dataTypePassMap = new HashMap<String, String>();
        dataTypePassMap.put("datatypeId", dataTypeId);
        Object firstObject = CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), mcssServer + getDataUrl, "post", dataTypePassMap);
        if (null != firstObject) {
            String jsonString = firstObject.toString();
            scriptDetailBOList = JSON.parseArray(jsonString, ScriptDetailBO.class);
        }
        return scriptDetailBOList;
    }

    /**
     * 简单描述：获取执行shell后打印结果
     */
    @Override
    public Scanner getShellResultContent(Process process) {
        return new Scanner(process.getInputStream());
    }

    /**
     * 简单描述:根据数据源类型id获取启动参数
     */

    public AppiumSettingBo getApplicationSettingBo(String dataTypeId) {
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
                boolean useKeystore = false;
                String keystorePath = keystoreOptionJson.getString("keystorePath");
                String keystorePassword = keystoreOptionJson.getString("keystorePassword");
                String keyAlias = keystoreOptionJson.getString("keyAlias");
                String keyPassword = keystoreOptionJson.getString("keyPassword");
                if (keystorePath != null && !keystorePath.equals("")) {
                    useKeystore = true;
                } else {
                    useKeystore = false;
                }
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
            if (app == null || app.equals("") || app.equals("null")) {
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
                appiumSettingBo.setAutomationName(automationName);
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
        if (appiumSettingBo.getAppPackage() == null) {
            return null;
        }
        return appiumSettingBo;
    }

    /**
     * 简单描述:执行操作
     */
    public void execuseAction(String elementType, String elementValueArray, String actionType, String actionValue, AppiumDriverManager appiumDriverManager, AppiumAction appiumAction) {
        AndroidDriver androidDriver = appiumDriverManager.getAndroidDriver();
        String devicesName = appiumDriverManager.getDeviceInfo().getDevicesName();
        String pid = appiumDriverManager.getServicePid();
        List<String> elementValueList = new ArrayList<>();
        //TODO 如果是切换,后续可以改成和抓取同样的操作
        System.out.println("actionType:    >>>>>>>>>>   " + actionType);
        if (actionType.equals("5") || actionType.equals("7")) {
            if (actionType.equals("5")) {
                actionValue = "0";
            } else {
                actionValue = "1";
            }
            appiumAction.changeContext(androidDriver, pid, actionValue, devicesName);
            appiumDriverManager.invoke();
            return;
        }
        //TODO 如果是暂停,后续可以改成和抓取同样的操作
        if (actionType.equals("6")) {
            try {
                Thread.sleep(Long.parseLong(actionValue));
            } catch (InterruptedException e) {
                System.out.println(devicesName + ":    >>>>>>>>>>   " + actionValue);
                e.printStackTrace();
            }
            return;
        }
        //TODO 如果是启用输入法,后续可以改成和抓取同样的操作
        if (actionType.equals("8")) {
            executeShell("adb -s " + devicesName + " " + actionValue, true);
            appiumDriverManager.invoke();
            return;
        }

        if (XpathUtil.isJson(elementValueArray)) {
            elementValueList = JSONArray.parseArray(elementValueArray, String.class);
        } else {
            elementValueList.add(elementValueArray);
        }
        for (String elementValue : elementValueList) {
            if (elementType.equals("1")) { // 非分页设置  坐标操作
                //为坐标
                if (!doingSthByLocal(Integer.parseInt(actionType), elementValue, androidDriver, appiumAction)) {
                    System.out.println(devicesName + ": 跳过该条规则, 执行下一条    >>>>>>>>>>   ");
                    continue;
                }
                appiumDriverManager.invoke();
            } else if (elementType.equals("0")) {// 分页设置  操作
                System.out.println("进入分页设置操作");
                String[] parmArray = new String[2];
                parmArray = elementValue.trim().split(">");
                if (parmArray.length == 1 && Integer.parseInt(actionType) == 4) {
                    List<WebElement> webElements = findWhos(4, elementValue, androidDriver, appiumAction);
                    System.out.println("获得列表元素，一共有元素 >>>>>>>>>>>>>>>>>>" + webElements.size() + "个");
                    if (webElements.size() > 0) {
                        System.out.println("");
                        for (WebElement webElement : webElements) {
                            int i = 1;
                            Dimension dimension = webElement.getSize();
                            Point point = webElement.getLocation();
                            int startY = point.getY() + dimension.getHeight();
                            int endY = point.getY();
                            System.out.println("获得第" + i + "个控件id的开始高度：" + startY + ",结束高度：" + endY);
                            if (startY - endY >= 36) {
                                doingSth(Integer.parseInt(actionType), actionValue, androidDriver, webElement, appiumAction);
                                appiumDriverManager.invoke();
                                break;
                            }
                            ++i;
                        }
                    }
                } else {
                    if (!doingSthByLocal(Integer.parseInt(actionType), elementValue, androidDriver, appiumAction)) {
                        System.out.println(devicesName + ": 跳过该条规则, 执行下一条    >>>>>>>>>>   ");
                        continue;
                    }
                    appiumDriverManager.invoke();

                }
            } else {// 非分页设置  非坐标操作
                //非坐标
                try {
                    if (Integer.parseInt(elementType) == 4 && Integer.parseInt(actionType) == 4) {
                        List<WebElement> webElements = findWhos(Integer.parseInt(elementType), elementValue, androidDriver, appiumAction);
                        System.out.println("获得列表元素，一共有元素 >>>>>>>>>>>>>>>>>>" + webElements.size() + "个");
                        if (webElements.size() > 0) {
                            for (WebElement webElement : webElements) {
                                int i = 1;
                                Dimension dimension = webElement.getSize();
                                Point point = webElement.getLocation();
                                int startY = point.getY() + dimension.getHeight();
                                int endY = point.getY();
                                System.out.println("获得第" + i + "个控件id的开始高度：" + startY + ",结束高度：" + endY);
                                if (startY - endY >= 36) {
                                    doingSth(Integer.parseInt(actionType), actionValue, androidDriver, webElement, appiumAction);
                                    appiumDriverManager.invoke();
                                    break;
                                }
                                ++i;
                            }
                        }
                    } else {
                        WebElement webElement = findWho(Integer.parseInt(elementType), elementValue, androidDriver, appiumAction);
                        if (webElement != null) {
                            doingSth(Integer.parseInt(actionType), actionValue, androidDriver, webElement, appiumAction);
                            appiumDriverManager.invoke();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(devicesName + ": " + e + "    >>>>>>>>>>   页面上没有找到对应元素");
                    continue;
                }
            }
            break;
        }
    }

    public WebElement findWho(int paramkey, String parmValue, AndroidDriver<WebElement> androidDriver, AppiumAction appiumAction) {
        WebElement webElement = null;
        switch (paramkey) {
            //控件类型
            case 2:
                webElement = (WebElement) appiumAction.findByClassName(androidDriver, parmValue);
                break;
            //xpath
            case 3:
                webElement = (WebElement) appiumAction.findByXpath(androidDriver, parmValue);
                break;
            //控件Id
            case 4:
                webElement = (WebElement) appiumAction.findById(androidDriver, parmValue);
                break;
            //文章描述
            case 5:
                webElement = (WebElement) appiumAction.findByAccessibilityId(androidDriver, parmValue);
                break;
        }
        return webElement;
    }

    //  ==================================================
    public List<WebElement> findWhos(int paramkey, String parmValue, AndroidDriver<WebElement> androidDriver, AppiumAction appiumAction) {
        List<WebElement> webElement = null;
        switch (paramkey) {
            //控件Id
            case 4:
                webElement = appiumAction.findsById(androidDriver, parmValue);
                break;
        }
        return webElement;
    }
    // ====================================================


    public void doingSth(int paramkey, String parmValue, AndroidDriver<WebElement> androidDriver, WebElement webElement, AppiumAction appiumAction) {
        switch (paramkey) {
            //点击
            case 1:
                appiumAction.click(webElement);
                break;
            //长按
            case 2:
                appiumAction.longPressSpecific(androidDriver, webElement);
                break;
            //输入
            case 3:
                appiumAction.sendText(webElement, parmValue);
                break;
            //滑动
            case 4:
                Dimension dimension = webElement.getSize();
                Point point = webElement.getLocation();
                int startX = point.getX() + dimension.getWidth() / 2;
                int startY = point.getY() + dimension.getHeight();
                int endX = point.getX() + dimension.getWidth() / 2;
                int endY = point.getY();
                appiumAction.moveObvious(androidDriver, startX, startY, endX, endY);
                break;
            //暂停
            case 6:
                try {
                    Thread.sleep(Long.parseLong(parmValue));
                } catch (InterruptedException e) {
                    System.out.println("暂停失败    >>>>>>>>>>   " + parmValue);
                    e.printStackTrace();
                }
                break;
            //linux命令
            case 7:

                break;
        }
    }

    public boolean doingSthByLocal(int paramkey, String parmValue, AndroidDriver<WebElement> androidDriver, AppiumAction appiumAction) {
        String[] parmArray = new String[2];
        parmArray = parmValue.trim().split(">");

        switch (paramkey) {
            //点击
            case 1:
                if (parmArray.length > 0) {
                    String[] coordinateArray = parmArray[0].split(",");
                    if (coordinateArray.length > 1) {
                        try {
                            int x = Integer.parseInt(coordinateArray[0].replace("(", "").replace(")", ""));
                            int y = Integer.parseInt(coordinateArray[1].replace("(", "").replace(")", ""));
                            appiumAction.pressObvious(androidDriver, x, y);
                        } catch (Exception e) {
                            System.out.println("点击坐标错误    >>>>>>>>>>   ");
                            return false;
                        }
                    }
                }
                break;
            //长按
            case 2:
                if (parmArray.length > 0) {
                    String[] coordinateArray = parmArray[0].split(",");
                    if (coordinateArray.length > 1) {
                        try {
                            int x = Integer.parseInt(coordinateArray[0].replace("(", "").replace(")", ""));
                            int y = Integer.parseInt(coordinateArray[1].replace("(", "").replace(")", ""));
                            appiumAction.longPressObvious(androidDriver, x, y);
                        } catch (Exception e) {
                            System.out.println("长按坐标错误    >>>>>>>>>>   ");
                            return false;
                        }
                    }
                }
                break;
            //滑动
            case 4:
                if (parmArray.length > 1) {
                    String[] coordinateStartArray = parmArray[0].split(",");
                    String[] coordinateEndArray = parmArray[1].split(",");
                    if (coordinateStartArray.length > 1 && coordinateEndArray.length > 1) {
                        try {
                            int startX = Integer.parseInt(coordinateStartArray[0].replace("(", "").replace(")", ""));
                            int startY = Integer.parseInt(coordinateStartArray[1].replace("(", "").replace(")", ""));
                            int endX = Integer.parseInt(coordinateEndArray[0].replace("(", "").replace(")", ""));
                            int endY = Integer.parseInt(coordinateEndArray[1].replace("(", "").replace(")", ""));
                            appiumAction.moveObvious(androidDriver, startX, startY, endX, endY);
                        } catch (Exception e) {
                            System.out.println("滑动坐标错误    >>>>>>>>>>   ");
                            return false;
                        }
                    }
                }
                break;
        }
        return true;
    }
}
