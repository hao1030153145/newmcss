package com.transing.mcss4dpm.biz.service.impl.api.script;

import com.transing.mcss4dpm.biz.service.ScriptService;
import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.DevicesInfoBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
import com.transing.mcss4dpm.util.ApplicationContextHelper;
import com.transing.mcss4dpm.util.FileUtil;
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
    public List<WechatBO> operateProcess(AppiumSettingBo appiumSettingBo) throws InterruptedException {

        ScriptService scriptService = (ScriptService) ApplicationContextHelper.getBean("scriptService");
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

        driver = driverManager.launchAppium(desiredCapabilities, appiumDriverManager, 10);
        //把该设备调整为启动运行状态
        appiumDriverManager.setAndroidDriver(driver);
        appiumDriverManager.setBindApp(appiumSettingBo.getName());
        appiumDriverManager.setStatus("2");

        // 获得appium执行方法
        AppiumAction appiumAction = new AppiumAction();

        // ====================================打开应用,脚本开始========================================
        Thread.sleep(5000);
        driver.findElementByXPath("//*[@text='通讯录']").click(); // 点击 通讯录
        Thread.sleep(1000);
        driver.findElementByXPath("//*[@text='新的朋友']").click(); // 点击 新的朋友
        Thread.sleep(1000);
        driver.findElementByXPath("//*[@text='添加朋友']").click(); // 点击 添加手机联系人
        Thread.sleep(1000);
        driver.findElementByXPath("//*[@text='手机联系人']").click(); // 点击 手机联系人

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
                System.out.println("当前源码与上一次源码相同，滑到最低，退出循环");
                break;
            }

            // 首先xpath整个联系人列表
            List<String> contentList = getContactsList(content);
            if (contentList != null) {
                int i = 0;
                for (int j = 0; j < contentList.size(); j++) {
                    String contentDetail = contentList.get(j);
                    // 如果存在添加，
                    if (contentDetail.contains("添加")) {
                        String contactName = getWeChatTel(contentDetail, j); // 首先抓取联系人名字
                        String weChatName = getWeChatName(contentDetail, j); // 在获得联系人的微信名
                        List<WebElement> webElements = driver.findElementsByXPath("//*[@text='添加']");
                        System.out.println("存在添加字段，获取webElement的数量为：" + webElements.size());
                        // 如果添加的webElement的数量等于添加字段的数量，那下面这个是没啥问题的
                        WebElement webElement = webElements.get(i);
                        Point point = webElement.getLocation();
                        int X = point.getX() - 200;
                        int Y = point.getY();
                        // driver.swipe(X, Y, X, Y, 10);
                        System.out.println("最终坐标为x:" + X + ", Y坐标为:" + Y);
                        // appiumAction.pressObvious(driver, X, Y); // 点击该坐标
                        driver.findElementByXPath("//*[@text='" + contactName + "']").click();
                        // 获得源码进行抓取
                        String contactContent = appiumAction.getScreenSrc(driver);
                        Thread.sleep(1000);
                        // 将源码进行xpath匹配, 并进行封装
                        WechatBO wechatBO = getWeChatData(contactContent);
                        wechatBO.setContactName(contactName);
                        wechatBO.setWeChatName(weChatName);
                        // 最后将这个联系人对象放入集合,存入数据库
                        scriptService.insertWeChatData(wechatBO);
                        weChatBOList.add(wechatBO);
                        // 点击返回,回到列表
                        driver.findElementById("com.tencent.mm:id/k5").click();
                        // 然后点击添加
                        appiumAction.click(webElement);
                        // 解析一下源码，如果源码中包含验证申请
                        String validaContent = appiumAction.getScreenSrc(driver);
                        if (valida(validaContent)) {
                            // 如果进入到验证页面，点击发送
                            driver.findElementByXPath("//*[@text='发送']").click();
                            Thread.sleep(1000);
                            // 然后点击返回
                            driver.findElementById("com.tencent.mm:id/k5").click();
                            Thread.sleep(1000);
                        }
                        i++;
                    }
                }
            }

            // 上面出了之后就开始滑动，滑动距离为屏幕的3/4

            Thread.sleep(2 * 1000);

            driver.swipe(300, 1000, 300, 2000, 10);  // x,y坐标一致的情况下是点击

            // 最后将源码赋值过去
            contentFirst = content;
        }

        return weChatBOList;
    }

    // 这个是解析微信联系人页面,一个页面一个对象
    private static WechatBO getWeChatData(String httpContent) {
        WechatBO wechatBO = new WechatBO();
        try {
            // 该规则已验证可用
            List<String> weChatIdList = ParseUtil.parseValue(httpContent, "微信号:(.*?)\"", "regex", null);
            if (weChatIdList != null && weChatIdList.size() > 0) {
                wechatBO.setWeChatId(weChatIdList.get(0));
                System.out.println("微信号 >>>>>>>" + weChatIdList.get(0));
            } else {
                wechatBO.setWeChatId("");
            }
            // 该规则已验证可用
            List<String> sexList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.tencent.mm:id/b2y']/@content-desc", "xpath", null);
            if (sexList != null && sexList.size() > 0) {
                wechatBO.setSex(sexList.get(0));
                System.out.println("性别 >>>>>>>" + sexList.get(0));
            } else {
                wechatBO.setSex("");
            }
            // 该规则已验证可用
            List<String> regionList = ParseUtil.parseValue(httpContent, "地区:(.*?)\"", "regex", null);
            if (regionList != null && regionList.size() > 0) {
                wechatBO.setRegion(regionList.get(0));
                System.out.println("地区 >>>>>>>" + regionList.get(0));
            } else {
                wechatBO.setRegion("");
            }
            // 该规则已验证可用
            List<String> tagList = ParseUtil.parseValue(httpContent, "个性签名:(.*?)\"", "regex", null);
            if (tagList != null && tagList.size() > 0) {
                wechatBO.setTag(tagList.get(0));
                System.out.println("个性签名: >>>>>>>" + tagList.get(0));
            } else {
                wechatBO.setTag("");
            }

        } catch (Exception e) {
            e.printStackTrace();
            return wechatBO;
        }
        return wechatBO;
    }

    // 这个是解析微信联系人页面,一个页面一个对象
    private String getWeChatTel(String httpContent, int i) {
        String contactName = "";
        try {
            // 该规则已验证可用
            List<String> contactNameList = ParseUtil.parseValue(httpContent, "//*[@resource-id='com.tencent.mm:id/btx']/@text", "xpath", null);
            if (contactNameList != null && contactNameList.size() > 0) {
                contactName = contactNameList.get(0);
                System.out.println("联系人名字 >>>>>>>" + contactNameList.get(0));
            } else {
                contactName = "test1";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return contactName;
        }
        return contactName;
    }

    // 这个是解析微信联系人页面,一个页面一个对象
    private String getWeChatName(String httpContent, int i) {
        String weChatName = "";
        try {
            // 该规则已验证可用
            List<String> nameList = ParseUtil.parseValue(httpContent, "微信:(.*?)\"", "regex", null);
            if (nameList != null && nameList.size() > 0) {
                weChatName = nameList.get(0);
                System.out.println("微信名 >>>>>>>" + nameList.get(0));
            } else {
                weChatName = "";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return weChatName;
        }
        return weChatName;
    }

    // 这个是解析微信联系人页面
    private List<String> getContactsList(String content) {
        List<String> nameList;
        try {
            // 该规则已验证可用
            nameList = ParseUtil.parseValue(content, "//android.widget.ListView/android.widget.LinearLayout", "xpath", null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return nameList;
    }

    // 这个是验证页面解析
    private boolean valida(String content) {
        // nameList = ParseUtil.parseValue(content, "//android.widget.ListView/android.widget.LinearLayout", "xpath", null);
        return content.contains("验证申请");
    }

    public static void main(String[] args) {
        try {
//          String content = readFile("D:\\script.txt");
//          List<String> nameList = ParseUtil.parseValue(content,"//android.widget.ListView/android.widget.LinearLayout","xpath",null);
//
//          for (String s : nameList){
//              List<String> ssdad = ParseUtil.parseValue(s, "//*[@resource-id='com.tencent.mm:id/btx']/@text", "xpath", null);
//              for (String ss : ssdad){
//                  System.out.println(ss);
//              }
//          }
            String content = FileUtil.readFileByPath("D:\\script4.txt");
            WechatBO wechatBO = getWeChatData(content);

            System.out.println(wechatBO);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
