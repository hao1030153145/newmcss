/*
 * @project: com.jeeframework 
 * @package: com.jeeframework.logicframework.biz.service.mq.consumer
 * @title:   BaseKafkaConsumerImpl.java 
 *
 * Copyright (c) 2017 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.job;

import com.alibaba.fastjson.JSON;
import com.jeeframework.logicframework.biz.service.mq.consumer.BaseKafkaConsumer;
import com.jeeframework.logicframework.integration.sao.zookeeper.BaseSaoZookeeper;
import com.transing.mcss4dpm.JobEvent.Bo.ScriptTask;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2017-12-21 19Dri:42
 */
public class BaseKafkaConsumerImpl extends BaseKafkaConsumer {

    @Override
    public void dealMessage(String message) {
        System.out.println("收到消息    >>>>>>>>>>   " + message);
        ScriptTask task = JSON.parseObject(message, ScriptTask.class);
        String dealClass = task.getDealClass();
        if (dealClass.equals("ReleaseDevices")) {
            task.setBaseSaoZookeeper((BaseSaoZookeeper) context.getBean("baseSaoZookeeper"));
        }
        try {
            String path = "com.transing.mcss4dpm.job";
            Class c = Class.forName(path + ".DealClass." + dealClass);
            Method m = c.getMethod("execue", ScriptTask.class); //Sigleton有一个方法为print
            m.invoke(c.newInstance(), task); //调用print方法
        } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
