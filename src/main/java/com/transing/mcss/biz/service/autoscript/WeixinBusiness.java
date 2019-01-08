

package com.transing.mcss.biz.service.autoscript;

import com.transing.mcss.biz.service.ProjectService;
import com.transing.mcss.integration.bo.WeixinBrandBO;
import com.transing.mcss.integration.bo.WeixinCommentBO;
import com.transing.mcss.util.DateUtil;
import com.transing.mcss.util.XpathUtil;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component("weixinBusiness")
public class WeixinBusiness {
    private static int SUCCEED = 1;
    private static int ERROR_FIND = 9;
    private static int ERROR = 9;
    private static String PORT = "4726";
    @Resource
    private ProjectService projectService;

    private List<TaskPojo> taskPojoList = new ArrayList<>();
    private AndroidDriver driver;
    private int height;
    private int width;
    private boolean isComplete = false;
    private List<WebElement> webElements;
    private List<Date> webElementTimes = new ArrayList<>();
    private int webElementComplete = 0;
    private int webElementInsert = 0;
    private boolean brandBuisNull = false;

    public WeixinBusiness() {
    }

    public void clear() {
        isComplete = false;
        brandBuisNull = false;
        if (webElements != null) {
            webElements.clear();
        }
        if (webElementTimes != null) {
            webElementTimes.clear();
        }
        webElementComplete = 0;
        webElementInsert = 0;
    }

    public void setTaskPojoList(List<TaskPojo> taskPojoList) {
        this.taskPojoList = taskPojoList;
    }

    public void weixin2() throws InterruptedException, MalformedURLException {
        TaskPojo task = taskPojoList.get(0);
        projectService.updateTaskStatusBegin(task.getId());
        while (true) {
            startAppium();
            if (!isComplete) {
//                initAppium(PORT, "ZX1G422DTW");
                initAppium(PORT, "192.168.2.105:5555");
                height = driver.manage().window().getSize().getHeight();
                width = driver.manage().window().getSize().getWidth();
                WeixinBrandBO weixinBrandBO = new WeixinBrandBO();
                weixinBrandBO.setTaskId(task.getId());
                weixinBrandBO.setPid(task.getPid());
                weixinBrandBO.setDataSourceId(task.getDataSourceId());
                weixinBrandBO.setDataTypeId(task.getDataTypeId());
                whileBrand(task, weixinBrandBO);
                stopAppium();
            } else {
                projectService.updateTaskStatus(task.getId());
                stopAppium();
                break;
            }
        }
    }

    private int whileBrand(TaskPojo task, WeixinBrandBO weixinBrandBO) throws InterruptedException {
        try {
            String crawlName = task.getCrawlContent(); //抓取公众号名称
            String type = task.getCrawlTimeType();  //抓取类型

            delayClick(8, By.xpath("//*[@text='通讯录']"));
            delayClick(8, By.xpath("//*[@text='通讯录']"));
            delayClick(8, By.xpath("//*[@text='公众号']"));
            driver.findElementByAccessibilityId("添加").click();

            driver.findElementById("com.tencent.mm:id/gn").sendKeys(crawlName);
//            excutePinyinCommand();
            Thread.currentThread().sleep(1000);
            driver.swipe(width - 16, height - 16, width - 16, height - 16, 10);
            Thread.currentThread().sleep(3000);
            driver.swipe(width / 2, height / 4, width / 2, height / 4, 10);
            try {
                Thread.currentThread().sleep(1500);
                driver.swipe(width / 2, height / 2, width / 2, height / 4, 600);
                driver.findElementByXPath("//*[@text='进入公众号'] | //*[@text='关注']").click();
            } catch (Exception e) {
//                excutePinyinCommand();
                isComplete = true;
                driver.quit();
                return ERROR_FIND;
            }
            driver.findElementByAccessibilityId("聊天信息").click();
            weixinBrandBO.setBrandName(driver.findElement(By.id("com.tencent.mm:id/l5")).getText());
            try {
                if (!brandBuisNull) {
                    String brandNu = driver.findElement(By.id("com.tencent.mm:id/abn")).getText();
                    if (brandNu != null && brandNu.contains(":")) {
                        weixinBrandBO.setBrandNu(driver.findElement(By.id("com.tencent.mm:id/abn")).getText().split(":")[1]);
                    }
                } else {
                    weixinBrandBO.setBrandNu("");
                }
            } catch (Exception e) {
                brandBuisNull = true;
                weixinBrandBO.setBrandNu("");
            }
            driver.findElementByXPath("//*[@text='查看历史消息']").click();
            whileArticle(task, weixinBrandBO);
            return SUCCEED;
        } catch (Exception e) {
            isComplete = false;
            driver.quit();
            return ERROR;
        }
    }

    private void whileArticle(TaskPojo task, WeixinBrandBO weixinBrandBO) throws InterruptedException {
        try {
            Date startTime;
            Date endTime;
            if (task.getCrawlStartTime() != null && !task.getCrawlStartTime().equals("")) {
                startTime = DateUtil.parseDate(task.getCrawlStartTime()); //开始时间
            } else {
                startTime = DateUtil.parseDate("2000-01-01");
            }
            if (task.getCrawlEndTime() != null && !task.getCrawlEndTime().equals("")) {
                endTime = DateUtil.parseDate(task.getCrawlEndTime()); //开始时间
            } else {
                endTime = new Date();
            }

            driver.context("WEBVIEW_com.tencent.mm:tools");
            Thread.currentThread().sleep(4000);
            try {
                webElements = driver.findElements(By.xpath("//*[@class='weui_media_title']"));
            } catch (Exception e) {
                driver.pressKeyCode(4);
                Thread.currentThread().sleep(1000);
                driver.quit();
                return;
            }
            if (webElements != null) {
                Updatepage();
            }

            webElementTimes.clear();
            webElementTimes = XpathUtil.getWeixinPublishTime(driver.getPageSource());
            String tolast = driver.getWindowHandle();
            int whilenu = webElementComplete + 7;
            for (int i = webElementComplete; i < whilenu; i++) {
                if (webElements.size() > i) {
                    WebElement webElement = webElements.get(i);
                    Date date = webElementTimes.get(i);
                    if (date != null && !date.after(endTime)) {
                        //文章时间在设定的结束时间之前,点击进入文章
                        if (webElement.isEnabled()) {
                            webElement.click();
                        }
                        Thread.currentThread().sleep(3500);
                        if (date != null && !date.before(startTime)) {
                            //文章时间在设定的开始时间之后,正常抓取
                            String currentWindows = null;
                            for (String string : driver.getWindowHandles()) {
                                currentWindows = string;
                            }
                            driver.switchTo().window(currentWindows);
                            weixinBrandBO.setUrl(driver.getCurrentUrl());
                            insertToWeixinBrand(driver.getPageSource(), weixinBrandBO, task.getId());
                            driver.switchTo().window(tolast);
                            webElementComplete++;
                        } else {
                            //文章时间在设定的开始时间之前,退出循环
                            isComplete = true;
                            break;
                        }
                    } else {
                        //文章时间在设定的结束时间之后,检查下一篇
                        whilenu++;
                        webElementComplete++;
                        continue;
                    }
                } else {
                    break;
                }
            }

            driver.pressKeyCode(4);
            Thread.currentThread().sleep(2000);
            driver.quit();
        } catch (Exception e) {
            isComplete = false;
            driver.pressKeyCode(4);
            Thread.currentThread().sleep(2000);
            driver.quit();
            return;
        }
    }

    private void back(int num) throws InterruptedException {
        for (int i = 0; i < num; i++) {
            Thread.currentThread().sleep(1000);
            driver.findElementByAccessibilityId("返回").click();
        }
    }

    private void Updatepage() throws InterruptedException {
        int lastnu = 0;
        int whilenu = 0;
        while (true) {
            if (webElementComplete >= webElements.size() && whilenu < 4) {
                driver.executeScript("window.scroll(0,document.body.clientHeight);");
                Thread.currentThread().sleep(2000);
                lastnu = webElements.size();
                webElements.clear();
                webElements = driver.findElements(By.xpath("//*[@class='weui_media_title']"));
                if (lastnu == webElements.size()) {
                    whilenu++;
                }
            } else {
                if (whilenu == 4) {
                    isComplete = true;
                }
                break;
            }
        }
    }

    private void excutePinyinCommand() {
        CommandLine command = new CommandLine("adb");
        command.addArgument("shell");
        command.addArgument("ime");
        command.addArgument("set");
//        command.addArgument("com.example.android.softkeyboard/.SoftKeyboard");//夜神模拟器默认输入法
        command.addArgument("com.google.android.inputmethod.pinyin/.PinyinIME");//Nexus默认输入法
        DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
        DefaultExecutor executor = new DefaultExecutor();
        executor.setExitValue(1);
        try {
            executor.execute(command, resultHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initAppium(String port, String udid) throws MalformedURLException {
//        RuntimeExec appiumObj = new RuntimeExec();
//        appiumObj.startAppium("appium");

        DesiredCapabilities capability = new DesiredCapabilities();
        capability.setCapability("app", "");
        capability.setCapability("appPackage", "com.tencent.mm");
        capability.setCapability("appActivity", ".ui.LauncherUI");
        capability.setCapability("deviceName", udid);
        capability.setCapability("fastReset", "false");
        capability.setCapability("fullReset", "false");
        capability.setCapability("noReset", "true");

        //关键是加上这段
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("androidPackage", "com.tencent.mm");
        options.setExperimentalOption("androidUseRunningApp", true);
        options.setExperimentalOption("androidActivity", ".plugin.webview.ui.tools.WebViewUI");
        options.setExperimentalOption("androidProcess", "com.tencent.mm:tools");
        capability.setCapability(ChromeOptions.CAPABILITY, options);

        driver = new AndroidDriver(new URL("http://127.0.0.1:" + port + "/wd/hub"),
                capability);
        driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
    }

    private void delay(int timeInSeconds, By method) {
        (new WebDriverWait(driver, timeInSeconds)).until(ExpectedConditions.presenceOfElementLocated(method));
    }

    private void delayClick(int timeInSeconds, By method) {
        (new WebDriverWait(driver, timeInSeconds)).until(ExpectedConditions.presenceOfElementLocated(method)).click();
    }

    private void insertToWeixinBrand(String httpContent, WeixinBrandBO weixinBrand, long taskId) {
        Map<String, Object> map = XpathUtil.getWeixinBrandByHtml(httpContent, weixinBrand);
        WeixinBrandBO weixinBrandBO = (WeixinBrandBO) map.get("weixinBrandBO");
        if (!weixinBrandBO.getTitle().equals("") && !weixinBrandBO.getPublishTime().equals("")) {
            projectService.addWeixinBrand((WeixinBrandBO) map.get("weixinBrandBO"));
            projectService.updateTaskCrawlNum(taskId);
            if (((List<WeixinCommentBO>) map.get("weixinCommentBOList")).size() > 0) {
                projectService.insertWeixinCommentList((List<WeixinCommentBO>) map.get("weixinCommentBOList"));
            }
        }
    }

    private void stopAppium() throws InterruptedException {
        try {
            Runtime.getRuntime().exec("taskkill /F /IM node.exe");
            Runtime.getRuntime().exec("cmd.exe /C start wmic process where name='cmd.exe' call terminate");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Thread.currentThread().sleep(2000);
    }

    public void startAppium() {
        String cmd = "cmd /c start D:\\showdevices.bat";
        try {
            Process ps = Runtime.getRuntime().exec(cmd);
            ps.waitFor();
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
        }
        try {
            Thread.currentThread().sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

