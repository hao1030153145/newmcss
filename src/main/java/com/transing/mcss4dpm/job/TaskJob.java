/*
 * @project: mcss4dpm
 * @package: com.transing.mcss4dpm.job
 * @title:   TaskJob.java
 *
 * Copyright (c) 2017 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.job;

import com.jeeframework.jeetask.task.Job;
import com.jeeframework.jeetask.task.context.JobContext;
import com.transing.mcss4dpm.JobEvent.Bo.McssTask;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AndroidDriverStatus;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.DriverManager;

/**
 * 测试用的作业执行类
 *
 * @author lance
 * @version 1.0 2017-08-30 17:35
 */
public class TaskJob implements Job {
    private DriverManager driverManager;
    private AndroidDriverStatus androidDriverStatus;

    @Override
    public void doJob(JobContext jobContext) {
//        zookeeper分发任务到job类
//        获取可用的driver
//        根据处理类调用相应的脚本
//        根据脚本返回的信息，
//        调用远程mcss接口,传回抓取信息
        McssTask task = (McssTask) jobContext.getTask();
        String dealClass;
        String dataTypeId = task.getDataTypeId();
        if (dataTypeId.equals("237")) {
            dealClass = "WeiXinTencentComment";
        } else if (dataTypeId.equals("240")) {
            dealClass = "YiDianInforComment";
        } else {
            dealClass = task.getDealClass();
        }
        System.out.println("XXXX: location_mcss : dealClass :" + dealClass);
        try {
            String path = this.getClass().getName().substring(0, this.getClass().getName().lastIndexOf("."));
            Class c = Class.forName(path + ".DealClass." + dealClass);
            Method m = c.getMethod("execue", McssTask.class); //Sigleton有一个方法为print
            m.invoke(c.newInstance(), task); //调用print方法
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        System.out.println("XXXX: location_mcss : doJob crawlWeiXinArticleBO: finish ");
    }
}
