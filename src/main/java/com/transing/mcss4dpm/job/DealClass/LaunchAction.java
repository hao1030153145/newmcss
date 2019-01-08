package com.transing.mcss4dpm.job.DealClass;

import com.alibaba.fastjson.JSON;
import com.transing.mcss4dpm.JobEvent.Bo.ScriptTask;
import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.ActionBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.ScreenSize;
import com.transing.mcss4dpm.util.CallRemoteServiceUtil;
import io.appium.java_client.android.AndroidDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/24
 */
public class LaunchAction {
    private static LaunchAction inst = null;

    public static LaunchAction getInstance() {
        if (inst == null) {
            inst = new LaunchAction();
        }
        return inst;
    }

    public void execue(ScriptTask task) {
        String dataTypeId = task.getDataTypeId();
        String param = task.getParam();

        ActionBo subTaskParam = JSON.parseObject(param, ActionBo.class);
        String elementType = subTaskParam.getElementType();
        String elementValue = subTaskParam.getElementValue();
        String actionType = subTaskParam.getActionType();
        String actionValue = subTaskParam.getActionValue();
        String block = subTaskParam.getBlock();
        String blockType = subTaskParam.getBlockType();
        String blockValue = subTaskParam.getBlockValue();

        String deviceId = subTaskParam.getDeviceId();

        AppiumAction appiumAction = new AppiumAction();
        DriverManager2 driverManager = DriverManager2.getInstance();
        AppiumDriverManager appiumDriverManager = driverManager.getDriverByStatusAndDevicesId("2", Long.parseLong(deviceId));
        AndroidDriver androidDriver = appiumDriverManager.getAndroidDriver();

        if (block.equals("1")) {
            System.out.println("滑动阻断运行   >>>>>>>>>> ");
            int maxWhile = 0;
            if (maxWhile < 5) {
                while (true) {
                    if (appiumAction.haveElementBySource(appiumDriverManager.getAndroidDriver(), blockValue)) {
                        System.out.println("滑动阻断执行满足条件  退出 >>>>>>>>>> "+blockValue);
                        break;
                    } else {
                        System.out.println("滑动阻断执行第   >>>>>>>>>> " + maxWhile + "次");
                        driverManager.execuseAction(elementType, elementValue, actionType, actionValue, appiumDriverManager, appiumAction);
                        maxWhile++;
                        appiumAction.sleep(1 * 1000);
                    }
                }
            }
        } else {
            driverManager.execuseAction(elementType, elementValue, actionType, actionValue, appiumDriverManager, appiumAction);
        }
        File fileImg = new File("img", "temporary.png");
        ScreenSize screenSize = new ScreenSize();
        //webview与原生切换时图片不截取,应为webview driver下不能获取屏幕截图!!!!!
        if (androidDriver.getContext().contains("NATIVE_APP")) {
            System.out.println("操作执行完成    >>>>>>>>>>   在原生环境下");
            //截图,如果截图2次仍失败则丢弃
            appiumAction.sleep(1 * 1000);
            File filerOiginal = null;
            int circle = 0;
            while (true) {
                if (circle > 1) {
                    break;
                }
                filerOiginal = appiumAction.getScreenImgToFile(androidDriver);
                if (filerOiginal != null) {
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

                    System.out.println("获取图片成功原始文件    >>>>>>>>>> " + filerOiginal.length());
                    if (filerOiginal.length() > 102400) {
                        fileImg = appiumAction.reduceImgFile(filerOiginal, resultFile, 0, 0, 0.25f);
                    } else {
                        fileImg = filerOiginal;
                    }
                    //获取屏幕分辨率
                    screenSize = appiumAction.getScreenSize(androidDriver);
                    System.out.println("获取图片成功压缩后文件    >>>>>>>>>> " + fileImg.length());
                    break;
                }
                appiumAction.sleep(1 * 1000);
                circle++;
            }
        }
        //获取源码
        String screenSrc = appiumAction.getScreenSrc(androidDriver);

        //调用文件上传接口
        System.out.println("调用文件上传接口    >>>>>>>>>>   ");
        String baseServer = System.getProperty("mcss_url");
        String getApplicationInfoUrl2 = "/scriptRegulation/uploadingImg.json";
        Map<String, String> inputParamMap2 = new HashMap<String, String>();
        if (!androidDriver.getContext().contains("NATIVE_APP")) {
            inputParamMap2.put("haveFile", "0");
        }
        inputParamMap2.put("dataTypeId", dataTypeId);
        inputParamMap2.put("deviceId", deviceId);
        CallRemoteServiceUtil.upload2(fileImg, baseServer + getApplicationInfoUrl2, inputParamMap2);
        //调用源码上传接口
        System.out.println("获取源码    >>>>>>>>>>   ");
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
