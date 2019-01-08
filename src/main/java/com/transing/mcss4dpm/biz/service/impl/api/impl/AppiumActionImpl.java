package com.transing.mcss4dpm.biz.service.impl.api.impl;

import com.jeeframework.webframework.exception.WebException;
import com.transing.mcss4dpm.biz.service.impl.api.ShellProcess;
import com.transing.mcss4dpm.biz.service.impl.api.appiumModule.AndroidDriverWait;
import com.transing.mcss4dpm.biz.service.impl.api.bo.ScreenSize;
import com.transing.mcss4dpm.web.exception.MySystemCode;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/11
 */
public abstract class AppiumActionImpl<T extends WebElement> implements AppiumFindElementImpl, LinuxCommandImpl {

    /**
     * 简单描述:点击
     */
    public void click(T t) {
        t.click();
    }

    /**
     * 简单描述:输入
     */
    public void sendText(T t, String text) {
        t.sendKeys(text);
    }

    /**
     * 简单描述:获取控件Rect
     */
    public org.openqa.selenium.Rectangle getRect(T t) {
        return t.getRect();
    }

    /**
     * 简单描述:暂停
     */
    public void sleep(T t, String text) {

    }

    /**
     * 简单描述:清除
     */
    public void clearText(T t) {
        t.clear();
    }

    /**
     * 简单描述:获取文本
     */
    public String getText(T t) {
        return t.getText();
    }

    /**
     * 简单描述:是否显示
     */
    public boolean isDisplayed(T t) {
        return t.isDisplayed();
    }

    /**
     * 简单描述:是否被勾选
     */
    public boolean isSelected(T t) {
        return t.isSelected();
    }

    /**
     * 简单描述:控件是否可用
     */
    public boolean isEnabled(T t) {
        return t.isEnabled();
    }

    /**
     * 简单描述:截屏
     */
    public String getScreenImg(AndroidDriver driver) {
        return driver.getScreenshotAs(OutputType.BASE64);
    }

    /**
     * 简单描述:截屏
     */
    public File getScreenImgToFile(AndroidDriver driver) {
        return driver.getScreenshotAs(OutputType.FILE);
    }

    /**
     * 简单描述:获取页面源码
     */
    public String getScreenSrc(AndroidDriver driver) {
        return driver.getPageSource();
    }

    /**
     * 简单描述:获取屏幕分辨率
     */
    public ScreenSize getScreenSize(AndroidDriver driver) {
        int height = driver.manage().window().getSize().height;
        int width = driver.manage().window().getSize().width;
        return new ScreenSize(height, width);
    }

    /**
     * 简单描述:点击绝对位置
     */
    public void pressObvious(AndroidDriver driver, int x, int y) {
        new TouchAction(driver).press(x, y).release().perform();
    }

    /**
     * 简单描述:长按绝对位置
     */
    public void longPressObvious(AndroidDriver driver, int x, int y) {
        new TouchAction(driver).longPress(x, y).release().perform();
    }


    /**
     * 简单描述:长按指定element
     */
    public void longPressSpecific(AndroidDriver driver, T i) {
        new TouchAction(driver).longPress(i).release().perform();
    }

    /**
     * 简单描述:滑动
     */
    public void moveSpecific(AndroidDriver driver, int startX, int startY, int endX, int endY) {
        new TouchAction(driver).press(startX, startY).waitAction(1000).moveTo(endX, endY).release().perform();
    }

    /**
     * 简单描述:滑动
     */
    public void moveObvious(AndroidDriver driver, int startX, int startY, int endX, int endY) {
        System.out.println("滑动   >>>>>>>>>> " + "[" + startX + "," + startY + "]" + "[" + endX + "," + endY + "]");
        driver.swipe(startX, startY, endX, endY, 1000);
    }

    /**
     * 简单描述:切换
     */
    public void changeContext(AndroidDriver driver, String pid, String value, String devicesName) {
        try {
            //为等待页面加载完成默认暂停2s
            Thread.sleep(1 * 1000);
        } catch (InterruptedException e) {
            System.out.println(devicesName + ": 暂停出错");
            e.printStackTrace();
        }
        String changeValue = getContextValue(driver, value);
        System.out.println(devicesName + "切换context到   >>>>>>>>>> " + changeValue);
        String chromeDriverId = null;
        int tryTime = 0;
        while (true) {
            if (tryTime > 8) {
                break;
            }
            try {

                Future<Integer> future = Executors.newSingleThreadExecutor().submit(() -> {
                    System.out.print("=====================================进入submit=================================");
                    driver.context(changeValue);
                    System.out.print("=====================================进入submit=================================");
                    return 1;
                });
                future.get(6, TimeUnit.SECONDS);
                System.out.println(devicesName + ": context change   >>>>>>>>>>   succeful");
                break;
            } catch (Exception e) {
                System.out.println("=================================下面是转切换异常信息=========================================");
                e.printStackTrace();
                System.out.println("=================================上面是转切换异常信息=========================================");
                System.out.println(devicesName + ": context change   >>>>>>>>>>   error!!!!");
                Process process = executeShell("pstree " + pid + " -p", true).getProcess();
                System.out.println("执行shell命令1=====================" + "pstree " + pid + " -p");
                Scanner in = new Scanner(process.getInputStream());
                while (in.hasNext()) {
                    String processInf = in.nextLine();
                    if (processInf.contains("chromedriver_64")) {
                        String id = processInf.trim().split("chromedriver_64")[1];
                        id = id.split("-\\+-")[0];
                        id = id.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                        chromeDriverId = id;
                    }
                }

                if (chromeDriverId != null) {
                    if (!chromeDriverId.equals("")) {
                        executeShell("kill -s 9 " + chromeDriverId, true);
                        System.out.println("执行shell命令1=====================" + "kill -s 9 " + chromeDriverId);
                    }
                }
            }
            tryTime++;
        }

        if (changeValue != null && changeValue.contains("NATIVE_APP")) {
            Process process = executeShell("pstree " + pid + " -p", true).getProcess();
            System.out.println("执行shell命令1=====================" + "pstree " + pid + " -p");
            Scanner in = new Scanner(process.getInputStream());
            while (in.hasNext()) {
                String processInf = in.nextLine();
                if (processInf.contains("chromedriver_64")) {
                    String id = processInf.trim().split("chromedriver_64")[1];
                    id = id.split("-\\+-")[0];
                    id = id.replaceAll("[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……& ;*（）——+|{}【】‘；：”“’。，、？|-]", "");
                    chromeDriverId = id;
                }
            }

            if (chromeDriverId != null) {
                if (!chromeDriverId.equals("")) {
                    executeShell("kill -s 9 " + chromeDriverId, true);
                    System.out.println("执行shell命令2=====================" + "kill -s 9 " + chromeDriverId);
                }
            }
        }
    }

    /**
     * 简单描述:获取context名称  content-desc
     */
    public String getContextValue(AndroidDriver driver, String value) {
        String changeValue = "NATIVE_APP";
        Set<String> contextNames = driver.getContextHandles();
        if (("0").equals(value)) {
            for (String contextValue : contextNames) {
                if (contextValue.contains("NATIVE_APP")) {
                    changeValue = contextValue;
                    break;
                }
            }
        } else if (("1").equals(value)) {
            for (String contextValue : contextNames) {
                System.out.println("此应用包含的context   >>>>>>>>>> " + contextValue);
//                if (contextValue.contains("WEBVIEW_com.tencent.mm:tools")) {
//                    changeValue = contextValue;
//                }
                changeValue = "WEBVIEW_com.tencent.mm:tools";
            }
        } else {
            throw new WebException(MySystemCode.SYS_CONTROLLER_EXCEPTION);
        }
        return changeValue;
    }

    /**
     * 简单描述:等待某个timeOut控件出现,慎用,未经测试
     */
    public void wait(AndroidDriver driver, String id, long timeOut) {
        WebElement showClose = new AndroidDriverWait(driver, timeOut)
                .until(new ExpectedCondition<WebElement>() {
                    @Override
                    public WebElement apply(WebDriver webDriver) {
                        return webDriver.findElement(By.id(id));
                    }
                });
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
     * 简单描述：获取执行shell后打印结果
     */
    @Override
    public Scanner getShellResultContent(Process process) {
        return new Scanner(process.getInputStream());
    }

    /**
     * 采用指定宽度、高度或压缩比例 的方式对图片进行压缩
     *
     * @param srcfile    源图片
     * @param widthdist  压缩后图片宽度（当rate==null时，必传）
     * @param heightdist 压缩后图片高度（当rate==null时，必传）
     * @param rate       压缩比例
     */
    public String reduceImg(File srcfile, int widthdist,
                            int heightdist, Float rate) {
        String srcString = "";
        try {
            // 如果rate不为空说明是按比例压缩
            if (rate != null && rate > 0) {
                // 获取文件高度和宽度
                int[] results = getImgWidth(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return srcString;
                } else {
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = javax.imageio.ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage((int) widthdist,
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(
                    src.getScaledInstance(widthdist, heightdist,
                            Image.SCALE_SMOOTH), 0, 0, null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(tag, "png", out);
            byte[] bytes = out.toByteArray();
            BASE64Encoder encoder = new sun.misc.BASE64Encoder();


            return encoder.encodeBuffer(bytes).trim();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return srcString;
    }


    public File reduceImgFile(File srcfile, File resultfile, int widthdist,
                              int heightdist, Float rate) {
        try {
            // 如果rate不为空说明是按比例压缩
            if (rate != null && rate > 0) {
                // 获取文件高度和宽度
                int[] results = getImgWidth(srcfile);
                if (results == null || results[0] == 0 || results[1] == 0) {
                    return resultfile;
                } else {
                    System.out.println("收到消息    widthdist>>>>>>>>>>   " + results[0]);
                    System.out.println("收到消息    heightdist>>>>>>>>>>   " + results[1]);
                    widthdist = (int) (results[0] * rate);
                    heightdist = (int) (results[1] * rate);
                }
            }
            // 开始读取文件并进行压缩
            Image src = javax.imageio.ImageIO.read(srcfile);
            BufferedImage tag = new BufferedImage((int) widthdist,
                    (int) heightdist, BufferedImage.TYPE_INT_RGB);

            tag.getGraphics().drawImage(
                    src.getScaledInstance(widthdist, heightdist,
                            Image.SCALE_SMOOTH), 0, 0, null);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(tag, "png", out);
            byte[] bytes = out.toByteArray();

            FileOutputStream fos = new FileOutputStream(resultfile);
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            return resultfile;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return resultfile;
    }

    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int result[] = {0, 0};
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            result[0] = src.getWidth(null); // 得到源图宽
            result[1] = src.getHeight(null); // 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
