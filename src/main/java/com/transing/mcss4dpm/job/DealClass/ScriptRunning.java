package com.transing.mcss4dpm.job.DealClass;

import com.alibaba.fastjson.JSON;
import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.CrawlAction;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.integration.bo.*;
import com.transing.mcss4dpm.util.CallRemoteServiceUtil;
import com.transing.mcss4dpm.util.DateUtil;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/30
 */
public class ScriptRunning {
    private static ScriptRunning inst = null;

    public static ScriptRunning getInstance() {
        if (inst == null) {
            inst = new ScriptRunning();
        }
        return inst;
    }

    public void execue(String task) {
        String datasourceTypeId = null;
        String subTaskid = null;
        String param = null;
        String workFlowId = null;
        System.out.println("workFlowId :>>>>>>>>>>>    " + workFlowId);
        System.out.println("datasourceTypeId :>>>>>>>>>>   " + datasourceTypeId);
        System.out.println("subTaskid :>>>>>>>>>>   " + subTaskid);
        System.out.println("param :>>>>>>>>>>   " + param);
        //解析输入参数
        List<SubTaskParam> subTaskParams = JSON.parseArray(param, SubTaskParam.class);
        List<ScriptDetailBO> scriptDetailBOList = new ArrayList<>();
        int startStep = 0;
        int backStep = 0;
        //获取可用设备
        DriverManager2 driverManager = DriverManager2.getInstance();
        //获取抓取规则
        List<CrawlRegulationBO> crawlRegulationBOList = new ArrayList<>();
        CrawlRegulationListBO crawlRegulationListBO = new CrawlRegulationListBO();
        CrawlRegulationItemBO crawlRegulationItemBO = driverManager.getCrawlRegulationByTypeid(datasourceTypeId);
        if (crawlRegulationItemBO != null) {
            crawlRegulationBOList = crawlRegulationItemBO.getCrawlRegulationBOList();
            crawlRegulationListBO = crawlRegulationItemBO.getCrawlRegulationListBO();
        }
        //初始化执行实现类
        AppiumAction appiumAction = new AppiumAction();
        //寻找是否有已经启动,并且在等待
        AppiumDriverManager appiumDriverManager = driverManager.getDriverByStatusAndDataTypeId("3", datasourceTypeId);

        System.out.println("appiumDriverManager1=================" + appiumDriverManager);
        List s1 = driverManager.getDriverManagerList();
        System.out.println("driverManager.getDriverManagerList()=======================" + driverManager.getDriverManagerList().toString());
        if (appiumDriverManager == null) {
            //没有启动对应应用的设备
            //获取一个空闲设备
            appiumDriverManager = driverManager.getDriverByStatus("0");
            if (appiumDriverManager == null) {
                appiumDriverManager = driverManager.changeDevicesStatus("3", datasourceTypeId);
            }
            System.out.println("appiumDriverManager2=================" + appiumDriverManager);
            if (appiumDriverManager != null) {
                System.out.println("启动新设备  >>>>>>>>>>   : " + appiumDriverManager.getDeviceInfo().getDevicesName());
                //根据数据源类型id获取启动参数
                AppiumSettingBo appiumSettingBo = driverManager.getApplicationSettingBo(datasourceTypeId);
                if (appiumSettingBo == null) {
                    System.out.println("没有被配置的数据源类型>>>>>>>>>>  ");
                    return;
                }
                //启动services
                String servicePid = driverManager.startAppiumService(appiumDriverManager.getPort(), appiumDriverManager.getBp(), appiumDriverManager.getCp(), appiumDriverManager.getDeviceInfo().getDevicesName(), String.valueOf(appiumSettingBo.getNewCommandTimeout()));
                appiumDriverManager.setServicePid(servicePid);
                System.out.println(appiumDriverManager.getDeviceInfo().getDevicesName() + "的 servicePid 是  >>>>>>>>>>   : " + servicePid);
                appiumDriverManager.setStatus("1");
                //启动client
                DesiredCapabilities desiredCapabilities = driverManager.appiumSetting(appiumSettingBo);
                if (appiumSettingBo.getNewCommandTimeout() != null && appiumSettingBo.getNewCommandTimeout() > 0) {
                    appiumDriverManager.setDelay(appiumSettingBo.getNewCommandTimeout() * 1000);
                } else {
                    appiumDriverManager.setDelay(300 * 1000);
                }
                AndroidDriver androidDriver = driverManager.launchAppium(desiredCapabilities, appiumDriverManager, 10);
                //把该设备调整为启动运行状态
                appiumDriverManager.setAndroidDriver(androidDriver);
                appiumDriverManager.setBindApp(appiumSettingBo.getName());
                appiumDriverManager.setStatus("2");
                //获取脚本详情
                scriptDetailBOList = driverManager.getScriptBytypeId(datasourceTypeId);
                //找出循环范围
                DivingBO divingBO = findDiving(scriptDetailBOList);
                startStep = 1;
                backStep = divingBO.getBack();
            } else {
                System.out.println("没有空闲设备  >>>>>>>>>>   寻找是否有异常设备,尝试启动异常设备");
                appiumDriverManager = driverManager.getDriverByStatusAndDataTypeId("9");
                System.out.println("appiumDriverManager3=================" + appiumDriverManager);
                if (appiumDriverManager != null) {
                    System.out.println("启动新设备  >>>>>>>>>>   : " + appiumDriverManager.getDeviceInfo().getDevicesName());
                    //根据数据源类型id获取启动参数
                    AppiumSettingBo appiumSettingBo = driverManager.getApplicationSettingBo(datasourceTypeId);
                    //启动services
                    String servicePid = driverManager.startAppiumService(appiumDriverManager.getPort(), appiumDriverManager.getBp(), appiumDriverManager.getCp(), appiumDriverManager.getDeviceInfo().getDevicesName(), String.valueOf(appiumSettingBo.getNewCommandTimeout()));
                    appiumDriverManager.setServicePid(servicePid);
                    System.out.println(appiumDriverManager.getDeviceInfo().getDevicesName() + "的 servicePid 是  >>>>>>>>>>   : " + servicePid);
                    appiumDriverManager.setStatus("1");
                    //启动client
                    DesiredCapabilities desiredCapabilities = driverManager.appiumSetting(appiumSettingBo);
                    if (appiumSettingBo.getNewCommandTimeout() != null && appiumSettingBo.getNewCommandTimeout() > 0) {
                        appiumDriverManager.setDelay(appiumSettingBo.getNewCommandTimeout() * 1000);
                    } else {
                        appiumDriverManager.setDelay(300 * 1000);
                    }
                    AndroidDriver androidDriver = driverManager.launchAppium(desiredCapabilities, appiumDriverManager, 20);
                    //把该设备调整为启动运行状态
                    appiumDriverManager.setAndroidDriver(androidDriver);
                    appiumDriverManager.setBindApp(appiumSettingBo.getName());
                    appiumDriverManager.setStatus("2");
                    //获取脚本详情
                    scriptDetailBOList = driverManager.getScriptBytypeId(datasourceTypeId);
                    //找出循环范围
                    DivingBO divingBO = findDiving(scriptDetailBOList);
                    startStep = 1;
                    backStep = divingBO.getBack();
                } else {
                    System.out.println("没有可用设备  等待 30 秒>>>>>>>>>>   ");
                    appiumAction.sleep(30 * 1000);

                    //获取一个空闲设备
                    appiumDriverManager = driverManager.getDriverByStatus("0");
                    if (appiumDriverManager == null) {
                        System.out.println("启动新设备  >>>>>>>>>>   : " + appiumDriverManager.getDeviceInfo().getDevicesName());
                        //根据数据源类型id获取启动参数
                        AppiumSettingBo appiumSettingBo = driverManager.getApplicationSettingBo(datasourceTypeId);
                        if (appiumSettingBo == null) {
                            return;
                        }
                        //启动services
                        String servicePid = driverManager.startAppiumService(appiumDriverManager.getPort(), appiumDriverManager.getBp(), appiumDriverManager.getCp(), appiumDriverManager.getDeviceInfo().getDevicesName(), String.valueOf(appiumSettingBo.getNewCommandTimeout()));
                        appiumDriverManager.setServicePid(servicePid);
                        System.out.println(appiumDriverManager.getDeviceInfo().getDevicesName() + "的 servicePid 是  >>>>>>>>>>   : " + servicePid);
                        appiumDriverManager.setStatus("1");
                        //启动client
                        DesiredCapabilities desiredCapabilities = driverManager.appiumSetting(appiumSettingBo);
                        if (appiumSettingBo.getNewCommandTimeout() != null && appiumSettingBo.getNewCommandTimeout() > 0) {
                            appiumDriverManager.setDelay(appiumSettingBo.getNewCommandTimeout() * 1000);
                        } else {
                            appiumDriverManager.setDelay(300 * 1000);
                        }
                        AndroidDriver androidDriver = driverManager.launchAppium(desiredCapabilities, appiumDriverManager, 10);
                        //把该设备调整为启动运行状态
                        appiumDriverManager.setAndroidDriver(androidDriver);
                        appiumDriverManager.setBindApp(appiumSettingBo.getName());
                        appiumDriverManager.setStatus("2");
                        //获取脚本详情
                        scriptDetailBOList = driverManager.getScriptBytypeId(datasourceTypeId);
                        //找出循环范围
                        DivingBO divingBO = findDiving(scriptDetailBOList);
                        startStep = 1;
                        backStep = divingBO.getBack();
                        //return;
                    } else {
                        return;
                    }
                }
            }
        } else {
            //有对应启动的设备
            //获取脚本详情
            System.out.println("启动已有设备  >>>>>>>>>>   ");
            scriptDetailBOList = driverManager.getScriptBytypeId(datasourceTypeId);
            //找出循环范围
            DivingBO divingBO = findDiving(scriptDetailBOList);
            startStep = divingBO.getBack();
            backStep = divingBO.getBack();
        }

        try {
            AndroidDriver androidDriver = appiumDriverManager.getAndroidDriver();
            String pid = appiumDriverManager.getServicePid();
            String devicesName = appiumDriverManager.getDeviceInfo().getDevicesName();
            List<Map<String, Object>> crawlMapList = new ArrayList<>();
            Map<String, Object> crawlMap = new HashMap<>();
            //判断是否有循环,并获取循环操作相关参数
            System.out.println("获得dpm传过来的参数subTaskParams===" + subTaskParams.toString() + "=====");
            CircleBO circleBO = getCircleParam(scriptDetailBOList, subTaskParams);
            System.out.println("获得dpm传过来的参数circleBO===" + circleBO.getCircleCutOffValue() + "=====");
            int circleStep = circleBO.getCircleStep();
            System.out.println("circleStep ============================" + circleStep);
            int circlemaxsize = 20;
            CircleRegulationRootBO circleRegulationArrayBO = circleBO.getCircleRegulationArrayBO();
            String circleCutOffType = circleBO.getCircleCutOffType();
            String circleCutOffValue = circleBO.getCircleCutOffValue();

            // 下面的操作是识别该路径规则下是否有分页设置
            int flag = 0;
            for (ScriptDetailBO s : scriptDetailBOList) {
                if (s.getCircle() == 1) {
                    flag = 1;
                }
            }
            if (flag == 0) {
                for (int i = 1; i <= circleStep; i++) {
                    execueScript(i, scriptDetailBOList.get(i - 1), subTaskParams, driverManager, appiumDriverManager, appiumAction);
                    //抓取数据
                    try {
                        crawlMapList.addAll(getCrawlData(crawlRegulationBOList, crawlRegulationListBO, i, appiumAction, androidDriver, subTaskParams, crawlMap));
                    } catch (Exception e) {
                        System.out.println("抓取第" + i + "异常,跳过继续处理  >>>>>>>>>>   " + e.getMessage());
                    }
                    String jsonParam = net.sf.json.JSONArray.fromObject(crawlMapList).toString();
                    callMcss(jsonParam, workFlowId, subTaskid, datasourceTypeId, subTaskParams);
                    crawlMapList.clear();
                    crawlMap.clear();
                }
            }
            if (flag != 0) {
                //1.开始执行
                for (int i = startStep; i < backStep; i++) {
                    System.out.println("这里开始第" + i + "步 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                    execueScript(i, scriptDetailBOList.get(i - 1), subTaskParams, driverManager, appiumDriverManager, appiumAction);
                    //抓取数据
                    try {
                        crawlMapList.addAll(getCrawlData(crawlRegulationBOList, crawlRegulationListBO, i, appiumAction, androidDriver, subTaskParams, crawlMap));
                    } catch (Exception e) {
                        System.out.println("抓取第" + i + "异常,跳过继续处理  >>>>>>>>>>   " + e.getMessage());
                    }
                    String jsonParam = net.sf.json.JSONArray.fromObject(crawlMapList).toString();
                    System.out.println("准备调用远程接口存储数据，要存储的数据为===================" + jsonParam + "===============================");
                    callMcss(jsonParam, workFlowId, subTaskid, datasourceTypeId, subTaskParams);
                    crawlMapList.clear();
                    crawlMap.clear();
                }

                //2.开始循环
                int itemSize = 0;
                while (true) {
                    if (flag == 0) {
                        break;
                    }
                    for (int i = backStep; i <= circleStep; i++) {
                        System.out.println("for循环中的backStep = >>>>>>>>>>>>>>>>>>>>" + backStep);
                        System.out.println("for循环中的circleStep = >>>>>>>>>>>>>>>>>>>>" + circleStep);
                        execueScript(i, scriptDetailBOList.get(i - 1), subTaskParams, driverManager, appiumDriverManager, appiumAction);
                        //抓取数据
                        try {
                            crawlMapList.addAll(getCrawlData(crawlRegulationBOList, crawlRegulationListBO, i, appiumAction, androidDriver, subTaskParams, crawlMap));
                        } catch (Exception e) {
                            System.out.println("抓取第" + i + "异常,跳过继续处理  >>>>>>>>>>   ");
                        }
                    }
                    String jsonParam = net.sf.json.JSONArray.fromObject(crawlMapList).toString();
                    System.out.println("准备调用远程接口存储数据，要存储的数据为===================" + jsonParam + "===============================");
                    callMcss(jsonParam, workFlowId, subTaskid, datasourceTypeId, subTaskParams);
                    crawlMapList.clear();
                    crawlMap.clear();

                    // 下面这个是如果选择了输入类型，就将值赋给他
                    if (("1").equals(circleCutOffType)) {
                        try {
                            circlemaxsize = Integer.parseInt(circleCutOffValue);
                        } catch (Exception e) {
                            circlemaxsize = 20;
                        }
                    }
                    if (circleCutOffType.equals("1")) {
                        //截止条件为  数值
                        if (itemSize < circlemaxsize) {
                            System.out.println("因为截止条件 为数值，所以现在开始循环执行，itemSize === " + itemSize + " >>>>>>>> circlemaxsize ==== " + circlemaxsize + "》》》》》》》》》》》》》");
                            if (circleRegulationArrayBO != null) {
                                for (CircleRegulationBO circleRegulationBO : circleRegulationArrayBO.getCircleRegulationBO()) {
                                    String circleActionType = circleRegulationBO.getCircleRegulationType();
                                    String circleElementValue = circleRegulationBO.getCircleRegulationValue();
                                    driverManager.execuseAction("0", circleElementValue, circleActionType, "", appiumDriverManager, appiumAction);
                                }
                            }
                        } else {
                            System.out.println("满足截止条件并退出循环，截止条件为非数值");
                            break;
                        }
                    } else {
                        //截止条件为  非数值
                        /*if (itemSize > circlemaxsize) {
                            break;
                        }*/
                        //appiumDriverManager.setAndroidDriver(androidDriver);
                        if (!appiumAction.haveElementBySource(appiumDriverManager.getAndroidDriver(), circleCutOffValue)) {
                            System.out.println("现在判断该页面是否有分页截止条件的元素，截止条件为====" + circleCutOffValue + "该页面是否有该元素？ " + appiumAction.haveElementBySource(appiumDriverManager.getAndroidDriver(), circleCutOffValue));
                            if (circleRegulationArrayBO != null) {
                                for (CircleRegulationBO circleRegulationBO : circleRegulationArrayBO.getCircleRegulationBO()) {
                                    String circleActionType = circleRegulationBO.getCircleRegulationType();
                                    String circleElementValue = circleRegulationBO.getCircleRegulationValue();
                                    driverManager.execuseAction("0", circleElementValue, circleActionType, "", appiumDriverManager, appiumAction);
                                }
                            }
                        } else {
                            System.out.println("满足截止条件并退出循环，截止条件为非数值");
                            break;
                        }
                    }
                    itemSize++;
                }
                startStep = circleStep + 1;
                //3.结束执行
                for (int i = startStep; i <= scriptDetailBOList.size(); i++) {
                    System.out.println("成功进入最后结束执行阶段，itemSize === " + startStep + " >>>>>>>> scriptDetailBOList.size() ==== " + scriptDetailBOList.size() + "》》》》》》》》》》》》》");
                    execueScript(i, scriptDetailBOList.get(i - 1), subTaskParams, driverManager, appiumDriverManager, appiumAction);
                    //抓取数据
                    try {
                        crawlMapList.addAll(getCrawlData(crawlRegulationBOList, crawlRegulationListBO, i, appiumAction, androidDriver, subTaskParams, crawlMap));
                    } catch (Exception e) {
                        System.out.println("抓取第" + i + "异常,跳过继续处理  >>>>>>>>>>   ");
                    }
                    //调用mcss存储接口
                    String jsonParam = net.sf.json.JSONArray.fromObject(crawlMapList).toString();
                    System.out.println("准备调用远程接口存储数据，要存储的数据为===================" + jsonParam + "===============================");
                    callMcss(jsonParam, workFlowId, subTaskid, datasourceTypeId, subTaskParams);
                    crawlMapList.clear();
                    crawlMap.clear();
                }
            }
            //设置为运行等待状态
            appiumDriverManager.setStatus("3");
            System.out.println("===============运行完成得到最后的状态===================" + appiumDriverManager.getStatus() + "===============================");
        } catch (Exception e) {
            System.out.println("scriptRuning异常 >>>>>>>" + e);
            e.printStackTrace();
            driverManager.releaseAppiumDriver(appiumDriverManager, "9");
        }
    }

    private void execueScript(int i, ScriptDetailBO scriptDetailBO, List<SubTaskParam> subTaskParams, DriverManager2 driverManager, AppiumDriverManager appiumDriverManager, AppiumAction appiumAction) {
        System.out.println(appiumDriverManager.getDeviceInfo().getDevicesName() + "  脚本运行step  >>>>>>>>>>   " + i);
        //Element相关
        String elementType = String.valueOf(scriptDetailBO.getElementtype());
        String elementInputParamType = scriptDetailBO.getElementInputParamType();
        String elementValue = scriptDetailBO.getElementvalue();
        String elementinputparam = scriptDetailBO.getElementinputparam();
        String block = String.valueOf(scriptDetailBO.getBlock());
        String blockType = String.valueOf(scriptDetailBO.getBlockType());
        String blockValue = scriptDetailBO.getBlockValue();

        //Action相关
        String actionType = String.valueOf(scriptDetailBO.getActiontype());
        String actionInputParamType = scriptDetailBO.getActionInputParamType();
        String actionValue = scriptDetailBO.getActionvalue();
        String actionInputParam = scriptDetailBO.getActionInputParam();

        if (elementInputParamType != null && elementInputParamType.equals("2")) {
            for (SubTaskParam subTaskParam : subTaskParams) {
                if (subTaskParam.getParamEnName().equals(elementinputparam)) {
                    elementValue = subTaskParam.getSubParam();
                    break;
                }
            }
        }
        if (actionInputParamType != null && actionInputParamType.equals("2")) {
            for (SubTaskParam subTaskParam : subTaskParams) {
                if (subTaskParam.getParamEnName().equals(actionInputParam)) {
                    actionValue = subTaskParam.getSubParam();
                    break;
                }
            }
        }
        //运行脚本
        if (block.equals("1")) {
            System.out.println("滑动阻断运行   >>>>>>>>>> ");
            int maxWhile = 0;
            if (maxWhile < 5) {
                System.out.println("滑动阻断执行第   >>>>>>>>>> " + maxWhile + "次");
                while (true) {
                    if (appiumAction.haveElementBySource(appiumDriverManager.getAndroidDriver(), blockValue)) {
                        System.out.println("滑动阻断执行满足条件  退出 >>>>>>>>>> " + blockValue);
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
            System.out.println("没有滑动阻断运行    >>>>>>>>>>>>>>>");
            driverManager.execuseAction(elementType, elementValue, actionType, actionValue, appiumDriverManager, appiumAction);
        }
    }

    private DivingBO findDiving(List<ScriptDetailBO> scriptDetailBOList) {
        DivingBO divingBO = new DivingBO();
        divingBO.setBack(1);
        //找出循环
        for (int i = 0; i < scriptDetailBOList.size(); i++) {
            if (scriptDetailBOList.get(i).getBack() != 0) {
                divingBO.setBack(scriptDetailBOList.get(i).getBack());
            }
        }
        return divingBO;
    }

    private CircleBO getCircleParam(List<ScriptDetailBO> scriptDetailBOList, List<SubTaskParam> subTaskParams) {
        CircleBO circleBO = new CircleBO();
        int circleStep = scriptDetailBOList.size();
        int circlemaxsize = 0;
        CircleRegulationRootBO circleRegulationArrayBO = null;
        String circleCutOffType = "1";
        String circleCutOffValue = "11";
        for (ScriptDetailBO scriptDetailBO : scriptDetailBOList) {
            if (scriptDetailBO.getCircle() == 1) {
                // 首先看有不有输入参数，如果没有输入参数，直接将后面的截止条件进行赋值
                System.out.println("因为有分页设置，所以现在进入了");
                circleStep = scriptDetailBO.getCirclestep();
                String circleInputParam = scriptDetailBO.getCircleinputparamId();
                System.out.println("分页参数中，circleInputParam===" + circleInputParam);
                if (circleInputParam == null || circleInputParam.equals("")) {
                    circleCutOffValue = scriptDetailBO.getCircleCutOffValue();
                    circleCutOffType = scriptDetailBO.getCircleCutOffType();
                    System.out.println("分页参数中，circleCutOffType1===" + circleCutOffType + "=====circleCutOffValue1===" + circleCutOffValue);
                } else {
                    // 循环dpm传过来的集合
                    for (SubTaskParam subTaskParam : subTaskParams) {
                        if (subTaskParam.getParamEnName().equals(circleInputParam)) {
                            try {
                                // 如果dpm传过来的值为空，直接获得截取条件内容
                                if (subTaskParam.getSubParam() == null || ("").equals(subTaskParam.getSubParam())) {
                                    circleCutOffValue = scriptDetailBO.getCircleCutOffValue();
                                    circleCutOffType = scriptDetailBO.getCircleCutOffType();
                                    System.out.println("分页参数中，因为将dpm传过来的值为null，获取分页中的截止条件的值，现在的circlemaxsize===" + circlemaxsize + "》》》》》》》》》》》》》》》》》》》》》");
                                    break;
                                }
                                // 如果dpm传过来的值不为空，将截止条件的值换成 dpm 的值，截止条件的类型不变
                                circleCutOffValue = subTaskParam.getSubParam();
                                circleCutOffType = "1";
                                System.out.println("分页参数中，circleCutOffType2===" + circleCutOffType + "=====circleCutOffValue2===" + circleCutOffValue);
                            } catch (Exception e) {
                                circleCutOffValue = scriptDetailBO.getCircleCutOffValue();
                                circleCutOffType = scriptDetailBO.getCircleCutOffType();
                            }
                            break;
                        }
                    }
                }

                String circleRegulation = scriptDetailBO.getCircleRegulation();
                if (circleRegulation != null && !circleRegulation.equals("")) {
                    circleRegulationArrayBO = JSON.parseObject(circleRegulation, CircleRegulationRootBO.class);
                }
                break;
            }
        }
        circleBO.setCircleStep(circleStep);
        circleBO.setCirclemaxsize(circlemaxsize);
        circleBO.setCircleRegulationArrayBO(circleRegulationArrayBO);
        circleBO.setCircleCutOffType(circleCutOffType);
        System.out.println("分页参数中，circleCutOffType3===" + circleCutOffType + "=====circleCutOffValue3===" + circleCutOffValue);
        circleBO.setCircleCutOffValue(circleCutOffValue);
        return circleBO;
    }

    private List<Map<String, Object>> getCrawlData(List<CrawlRegulationBO> crawlRegulationBOList, CrawlRegulationListBO crawlRegulationListBO, int i, AppiumAction appiumAction, AndroidDriver androidDriver, List<SubTaskParam> subTaskParams, Map<String, Object> crawlMap) throws Exception {
        List<Map<String, Object>> crawlMapList = new ArrayList<>();
        String content = appiumAction.getScreenSrc(androidDriver);
        List<String> contentList = new ArrayList<>();
        //执行抓取分页脚本
        if (crawlRegulationListBO != null && crawlRegulationListBO.getStep() == i) {
            //前置处理
            CrawlAction crawlAction = new CrawlAction();
            String beforeprocessorArray = crawlRegulationListBO.getBeforeprocessorArray();
            content = crawlAction.beforeProcessorAction(beforeprocessorArray, content);
            //抓取规则
            String crawlArray = crawlRegulationListBO.getCrawlArray();
            List<String> crawlStringList = crawlAction.listCrawl(crawlArray, content);
            for (String crawlString : crawlStringList) {
                //后置处理
                String afterProcessorArray = crawlRegulationListBO.getAfterprocessorArray();
                crawlString = crawlAction.afterProcessorAction(afterProcessorArray, crawlString);
                contentList.add(crawlString);
            }
        } else {
            contentList.add(content);
        }

        for (String contents : contentList) {
            Map<String, Object> crawlMap2 = new HashMap<>();
            //执行抓取内容脚本
            List<CrawlRegulationBO> crawlRegulationBOStepList = new ArrayList<>();
            for (CrawlRegulationBO crawlRegulationBO : crawlRegulationBOList) {
                if (crawlRegulationBO.getStep().equals(i + "")) {
                    crawlRegulationBOStepList.add(crawlRegulationBO);
                }
            }
            //如果该步骤有抓取任务,获取页面,执行抓取脚本
            if (crawlRegulationBOStepList.size() > 0) {
                for (CrawlRegulationBO crawlRegulationBO : crawlRegulationBOStepList) {
                    CrawlAction crawlAction = new CrawlAction();
                    String crawlString = crawlAction.crawl(crawlRegulationBO, contents, subTaskParams, androidDriver);
                    Object crawlItem = crawlString;
                    if (crawlRegulationBO.getType().equalsIgnoreCase("int")) {
                        if (crawlString == null) {
                            crawlItem = 0;
                        } else {
                            try {
                                crawlItem = Integer.parseInt(crawlString);
                            } catch (Exception e) {
                                crawlItem = 0;
                            }
                        }
                    } else if (crawlRegulationBO.getType().equalsIgnoreCase("datetime")) {
                        if (crawlString == null) {
                            crawlItem = System.currentTimeMillis();
                        } else {
                            crawlItem = DateUtil.parseDate(crawlString);
                        }
                    }
                    crawlMap2.put(crawlRegulationBO.getItem(), crawlItem);
                }
                System.out.println("现在是匹配数据中匹配到的crawlMap2 == " + crawlMap2 + "》》》》》》》》》》");
                crawlMapList.add(crawlMap2);
            }
        }
        System.out.println("现在抓取的数据为 jsonParam ======" + crawlMapList + "==============================");
        return crawlMapList;
    }

    private void callMcss(String jsonParam, String workFlowId, String subTaskid, String datasourceTypeId, List<SubTaskParam> subTaskParams) {
        //调用mcss存储接口
        String mcssServer = System.getProperty("mcss_url");
        String getDataUrl = "/crawlTask/preserveData.json";
        Map<String, String> dataTypePassMap = new HashMap<String, String>();
        dataTypePassMap.put("taskId", subTaskid);
        dataTypePassMap.put("datasourceTypeId", datasourceTypeId);
        dataTypePassMap.put("jsonParam", jsonParam);
        dataTypePassMap.put("workFlowId", workFlowId);
        //dataTypePassMap.put("formerUrl", System.currentTimeMillis() + "");
        for (SubTaskParam subTaskParam : subTaskParams) {
            if (subTaskParam.getParamEnName().equalsIgnoreCase("url") || subTaskParam.getParamEnName().equalsIgnoreCase("tempurl")) {
                if (("").equals(subTaskParam.getSubParam()) || subTaskParam.getSubParam() == null) {
                    dataTypePassMap.put("formerUrl", System.currentTimeMillis() + "");
                    System.out.println("因为传过来的链接是空值 所以formerUrl    >>>>>>>>>>   " + subTaskParam.getSubParam());
                    break;
                }
                dataTypePassMap.put("formerUrl", subTaskParam.getSubParam());
                System.out.println("因为dpm传过来的链接不为空 所以formerUrl    >>>>>>>>>>   " + subTaskParam.getSubParam());
                break;
            } else {
                dataTypePassMap.put("formerUrl", System.currentTimeMillis() + "");
                System.out.println("因为dpm传过来的字段不是链接 所以formerUrl    >>>>>>>>>>   " + subTaskParam.getSubParam());
                break;
            }
        }
        System.out.println("jsonParam    >>>>>>>>>>   " + jsonParam);
        System.out.println("taskId    >>>>>>>>>>   " + subTaskid);
        System.out.println("datasourceTypeId    >>>>>>>>>>   " + datasourceTypeId);
        System.out.println("workFlowId    >>>>>>>>>>   " + workFlowId);
        try {
            CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), mcssServer + getDataUrl, "post", dataTypePassMap);
        } catch (Exception e) {
            System.out.println("=======================================================");
            e.printStackTrace();
            System.out.println("=======================================================");
        }

    }

}
