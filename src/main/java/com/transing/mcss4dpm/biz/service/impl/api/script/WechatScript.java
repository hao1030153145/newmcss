package com.transing.mcss4dpm.biz.service.impl.api.script;

import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.DevicesInfoBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
import com.transing.mcss4dpm.util.ParseUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.OutputType;
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
        boolean tryLoopNu = true;


        byte[] bytes = driver.getScreenshotAs(OutputType.BYTES);
        driver.findElementById("com.tencent.mm:id/a2c").click();//点击发送
        //延迟0.3秒,为了等待消息发送显示到对话框中
        Thread.currentThread().sleep(300);

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


        List<WechatBO> weChatBOList = new ArrayList<>();
        // 一直循环到最后
        String contentFirst = "";
        while (tryLoopNu) {

            // 获取源码
            String content = driver.getPageSource();
            System.out.println("本页的源码为：" + content);

            // 如果本次源码跟上次源码一样，说明已经滑到最底，但也有可能源码本来就一样（需要注意）
            if (content.equalsIgnoreCase(contentFirst)) {
                tryLoopNu = false;
            }

            List<WechatBO> commentList = getWeChatData(content);
            for (WechatBO wechatBO : commentList) {
                weChatBOList.add(wechatBO);
            }

            Thread.currentThread().sleep(2 * 1000);

            driver.swipe(60, 100, 60, 100, 10);  // x,y坐标一致的情况下是点击

            // 最后将源码赋值过去
            contentFirst = content;
        }

        return weChatBOList;
    }

    // 这个是解析微信联系人页面
    private List<WechatBO> getWeChatData(String httpContent) {

        List<WechatBO> weChatBOList = new ArrayList<>();
        try {
            List<String> commentList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.hipu.yidian:id/normal_comment']", "xpath", null);
            if (commentList != null) {
                for (String comment : commentList) {

                    WechatBO wechatBO = new WechatBO();
                    List<String> nameList = ParseUtil.parseValue(comment, "//*[@resource-id='com.hipu.yidian:id/likeCount']/@text", "xpath", null);
                    if (nameList != null && nameList.size() > 0) {
                        wechatBO.setName(nameList.get(0));
                        System.out.println("微信名 >>>>>>>" + nameList.get(0));
                    } else {
                        wechatBO.setName("");
                    }
                    List<String> sexList = ParseUtil.parseValue(comment, "//*[@resource-id='com.hipu.yidian:id/name']/@text", "xpath", null);
                    if (sexList != null && sexList.size() > 0) {
                        wechatBO.setSex(sexList.get(0));
                        System.out.println("性别 >>>>>>>" + sexList.get(0));
                    } else {
                        wechatBO.setSex("");
                    }
                    List<String> regionList = ParseUtil.parseValue(comment, "//*[@resource-id='com.hipu.yidian:id/comment']/@text", "xpath", null);
                    if (regionList != null && regionList.size() > 0) {
                        wechatBO.setRegion(regionList.get(0));
                        System.out.println("地区 >>>>>>>" + regionList.get(0));
                    } else {
                        wechatBO.setRegion("");
                    }

                    List<String> tagList = ParseUtil.parseValue(comment, "//*[@resource-id='com.hipu.yidian:id/comment']/@text", "xpath", null);
                    if (tagList != null && tagList.size() > 0) {
                        wechatBO.setTag(tagList.get(0));
                        System.out.println("标签 >>>>>>>" + tagList.get(0));
                    } else {
                        wechatBO.setTag("");
                    }

                    List<String> numberList = ParseUtil.parseValue(comment, "//*[@resource-id='com.hipu.yidian:id/comment']/@text", "xpath", null);
                    if (numberList != null && numberList.size() > 0) {
                        wechatBO.setNumber(numberList.get(0));
                        System.out.println("电话 >>>>>>>" + numberList.get(0));
                    } else {
                        wechatBO.setNumber("");
                    }
                    weChatBOList.add(wechatBO);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weChatBOList;
    }

}
