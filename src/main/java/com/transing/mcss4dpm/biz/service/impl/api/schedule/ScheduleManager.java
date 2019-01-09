

package com.transing.mcss4dpm.biz.service.impl.api.schedule;

import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component("schedulemanager")
public class ScheduleManager {

    @PostConstruct
    private void RegisterMcssToZK() {

        DriverManager2.getInstance();
        // baseSaoZookeeper.createEphemeral(basePath, name);
        System.out.println("初始化设备节点    >>>>>>>>>>   OK");

    }
}


