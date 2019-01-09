package com.transing.mcss4dpm.biz.service.impl.api.script;

import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.DevicesInfoBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
import com.transing.mcss4dpm.util.ParseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WechatScript {
    private AppiumDriverManager appiumDriverManager;
    private DriverManager2 driverManager;
    private AndroidDriver driver;
    private String pid;

    public WechatScript(AppiumDriverManager appiumDriverManager, DriverManager2 driverManager) {
        this.appiumDriverManager = appiumDriverManager;
        this.driverManager = driverManager;
        driver = appiumDriverManager.getAndroidDriver();
        pid = appiumDriverManager.getServicePid();
    }

    /**
     * 简单描述：原生页面操作
     */
    public List<WechatBO> operateProcess(AppiumSettingBo appiumSettingBo) throws InterruptedException, IOException {


        //启动services
        pid = driverManager.startAppiumService(appiumDriverManager.getPort(), appiumDriverManager.getBp(), appiumDriverManager.getCp(), appiumDriverManager.getDeviceInfo().getDevicesName(), String.valueOf(appiumSettingBo.getNewCommandTimeout()));
        appiumDriverManager.setServicePid(pid);
        System.out.println(appiumDriverManager.getDeviceInfo().getDevicesName() + "的 servicePid 是  >>>>>>>>>>   : " + pid);
        appiumDriverManager.setStatus("1");

        // 启动客户端的参数
        DevicesInfoBo devicesInfoBo = appiumDriverManager.getDeviceInfo(); // 设备参数 在 实例化 DriverManager2 的时候已经赋值了
        appiumSettingBo.setDevicesName(devicesInfoBo.getDevicesName());
        appiumSettingBo.setUdid(devicesInfoBo.getDevicesName());
        int bp = appiumDriverManager.getBp();
        int cp = appiumDriverManager.getCp();
        appiumSettingBo.setBootstrapPort(bp);
        appiumSettingBo.setChromedriverPort(cp);

        //启动client
        DesiredCapabilities desiredCapabilities = driverManager.appiumSetting(appiumSettingBo);
        if (appiumSettingBo.getNewCommandTimeout() != null && appiumSettingBo.getNewCommandTimeout() > 0) {
            appiumDriverManager.setDelay(appiumSettingBo.getNewCommandTimeout() * 1000);
        } else {
            appiumDriverManager.setDelay(300 * 1000);
        }
        AndroidDriver driver = driverManager.launchAppium(desiredCapabilities, appiumDriverManager, 10);
        //把该设备调整为启动运行状态
        appiumDriverManager.setAndroidDriver(driver);
        appiumDriverManager.setBindApp(appiumSettingBo.getName());
        appiumDriverManager.setStatus("2");

        // 获得appium执行方法
        AppiumAction appiumAction = new AppiumAction();

        // ====================================打开应用,脚本开始========================================
//        driver.findElementByXPath("//*[@class='通讯录']").click(); // 点击 通讯录
//        driver.findElementByXPath("//*[@class='新的朋友']").click(); // 点击 新的朋友
//        driver.findElementByXPath("//*[@class='添加手机联系人']").click(); // 点击 添加手机联系人
        appiumAction.findByXpath(driver, "//*[@class='通讯录']");
        appiumAction.findByXpath(driver, "//*[@class='新的朋友']");
        appiumAction.findByXpath(driver, "//*[@class='添加手机联系人']");

        List<WechatBO> weChatBOList = new ArrayList<>();
        // 一直循环到最后
        String contentFirst = "";
        while (true) {

            // 获取源码
            // String content = driver.getPageSource();
            String content = appiumAction.getScreenSrc(driver);
            // System.out.println("本页的源码为：" + content);

            // 如果本次源码跟上次源码一样，说明已经滑到最底，但也有可能源码本来就一样（需要注意）
            if (content.equalsIgnoreCase(contentFirst)) {
                break;
            }

            if (content.contains("添加")) {
                List<WebElement> list = driver.findElementsById("com.tencent.mm:id/bu0");
                for (WebElement webElement : list) {
                    // 获得‘添加按钮’的坐标点，稍微往左偏200px进行点击
                    Point point = webElement.getLocation();
                    int X = point.getX() - 200;
                    int Y = point.getY() - 200;
                    // driver.swipe(X, Y, X, Y, 10);
                    appiumAction.pressObvious(driver, X, Y); // 点击该坐标
                    // 获得源码进行抓取
                    String contactContent = appiumAction.getScreenSrc(driver);
                    WechatBO wechatBO = getWeChatData(contactContent);
                    // 进入联系人来源获得电话号码
                    driver.findElementByXPath("//*[@class='添加手机联系人']").click();
                    String telContent = appiumAction.getScreenSrc(driver);
                    String tel = getWeChatTel(telContent);
                    wechatBO.setNumber(tel);
                    // 最后将这个联系人对象放入集合
                    weChatBOList.add(wechatBO);

                    // 连续返回,回到列表
                    driver.findElementById("").click();
                    driver.findElementById("").click();
                }
            }

            // 上面出了之后就开始滑动，滑动距离为屏幕的3/4

            Thread.currentThread().sleep(2 * 1000);

            driver.swipe(60, 100, 60, 100, 10);  // x,y坐标一致的情况下是点击

            // 最后将源码赋值过去
            contentFirst = content;
        }

        return weChatBOList;
    }

    // 这个是解析微信联系人页面,一个页面一个对象
    private WechatBO getWeChatData(String httpContent) {
        WechatBO wechatBO = new WechatBO();
        try {
            List<String> nameList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/likeCount']/@text", "xpath", null);
            if (nameList != null && nameList.size() > 0) {
                wechatBO.setName(nameList.get(0));
                System.out.println("微信名 >>>>>>>" + nameList.get(0));
            } else {
                wechatBO.setName("");
            }
            List<String> sexList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/name']/@text", "xpath", null);
            if (sexList != null && sexList.size() > 0) {
                wechatBO.setSex(sexList.get(0));
                System.out.println("性别 >>>>>>>" + sexList.get(0));
            } else {
                wechatBO.setSex("");
            }
            List<String> regionList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/comment']/@text", "xpath", null);
            if (regionList != null && regionList.size() > 0) {
                wechatBO.setRegion(regionList.get(0));
                System.out.println("地区 >>>>>>>" + regionList.get(0));
            } else {
                wechatBO.setRegion("");
            }

            List<String> tagList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/comment']/@text", "xpath", null);
            if (tagList != null && tagList.size() > 0) {
                wechatBO.setTag(tagList.get(0));
                System.out.println("标签 >>>>>>>" + tagList.get(0));
            } else {
                wechatBO.setTag("");
            }

            List<String> numberList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/comment']/@text", "xpath", null);
            if (numberList != null && numberList.size() > 0) {
                wechatBO.setNumber(numberList.get(0));
                System.out.println("电话 >>>>>>>" + numberList.get(0));
            } else {
                wechatBO.setNumber("");
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return wechatBO;
    }

    // 这个是解析微信联系人页面,一个页面一个对象
    private String getWeChatTel(String httpContent) {
        String tel = "";
        try {
            List<String> telList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/likeCount']/@text", "xpath", null);
            if (telList != null && telList.size() > 0) {
                tel = telList.get(0);
                System.out.println("电话 >>>>>>>" + telList.get(0));
            } else {
                tel = "0123-5878-4596";
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
        return tel;
    }

}
