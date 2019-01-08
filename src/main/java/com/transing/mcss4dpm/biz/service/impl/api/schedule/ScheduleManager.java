

package com.transing.mcss4dpm.biz.service.impl.api.schedule;

import com.jeeframework.logicframework.integration.sao.zookeeper.BaseSaoZookeeper;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("schedulemanager")
public class ScheduleManager {
    @Autowired
    BaseSaoZookeeper baseSaoZookeeper;

    @PostConstruct
    private void RegisterMcssToZK() {
        //TODO 只需本地mcss调用
        System.out.println("初始化设备节点    >>>>>>>>>>   begin");
        //zp上根目录
        String root = System.getProperty("mcss_device_list");
        //本地服务器名称
        String name = System.getProperty("mcss_service_name");
        //注册本机器到zk
        String basePath = root + name;
        baseSaoZookeeper.delete(basePath, true);
        //创建根目录
        if (!baseSaoZookeeper.isExisted(basePath)) {
            DriverManager2.getInstance();
            // baseSaoZookeeper.createEphemeral(basePath, name);
            System.out.println("初始化设备节点    >>>>>>>>>>   OK");
        }
    }
}


