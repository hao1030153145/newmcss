

package com.transing.mcss.biz.service.schedule;


//import com.alibaba.fastjson.JSON;

import com.alibaba.fastjson.JSON;
import com.jeeframework.logicframework.integration.sao.zookeeper.BaseSaoZookeeper;
import com.transing.mcss.biz.service.ProjectService;
import com.transing.mcss.biz.service.autoscript.TaskPojo;
import com.transing.mcss.biz.service.autoscript.WeixinBusiness;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.DevicesInfoBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component("project")
public class ScheduleManager implements ApplicationListener<ContextRefreshedEvent> {
    private List<TaskPojo> taskPojoList = new ArrayList<>();
    @Resource
    private ProjectService projectService;
    @Resource
    private WeixinBusiness weixinBusiness;
    @Autowired
    BaseSaoZookeeper baseSaoZookeeper;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
//            RegisterMcssToZK();
//            while (true) {
//                taskPojoList.clear();
//                List<TaskBO> taskBOList = projectService.getTaskList();
//                taskBOList.size();
//                for (TaskBO taskBO : taskBOList) {
//                    TaskPojo taskPojo = new TaskPojo();
//                    TaskJson taskJson = JSON.parseObject(taskBO.getParamter(), TaskJson.class);
//                    taskPojo.setCrawlContent(taskBO.getCrawlContent());
//                    taskPojo.setCrawlEndTime(taskJson.getCrawlEndTime());
//                    taskPojo.setCrawlStartTime(taskJson.getCrawlStartTime());
//                    taskPojo.setCrawlTimeType(taskJson.getCrawlTimeType());
//                    taskPojo.setDataSourceId(taskBO.getDataSourceId());
//                    taskPojo.setDataTypeId(taskBO.getDataTypeId());
//                    taskPojo.setId(taskBO.getId());
//                    taskPojo.setPid(taskBO.getPid());
//                    taskPojoList.add(taskPojo);
//                }
//
//                try {
//                    if (taskPojoList != null && taskPojoList.size() > 0) {
//                        weixinBusiness.clear();
//                        weixinBusiness.setTaskPojoList(taskPojoList);
//                        weixinBusiness.weixin2();
//                        long pid = projectService.getProjectId(taskBOList.get(0).getId());
//                        ProjectBO projectBO = projectService.getProjectById(pid);
//                        if (projectBO.getAllTaskcnt() == projectBO.getCompleteTaskcnt() + 1) {
//                            projectBO.setStatus(3);
//                        }
//                        projectService.updateProjectById(projectBO);
//                    }
//                } catch (InterruptedException | MalformedURLException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }

        }
    }

    private void RegisterMcssToZK() {
        String basePath = "/mcss_devices_dev";
        //创建根目录
        if (!baseSaoZookeeper.isExisted(basePath)) {
            baseSaoZookeeper.create(basePath, "root");
        }
        DriverManager2 driverManager = DriverManager2.getInstance();
        List<AppiumDriverManager> appiumDriverManagerList = driverManager.getDriverManagerList();
        for (AppiumDriverManager appiumDriverManager : appiumDriverManagerList) {
            if (!baseSaoZookeeper.isExisted(basePath + "/" + appiumDriverManager.getDeviceInfo().getId())) {
                DevicesInfoBo devicesInfoBo = appiumDriverManager.getDeviceInfo();
                String devicesInfoParam = JSON.toJSONString(devicesInfoBo);
                baseSaoZookeeper.create(basePath + "/" + appiumDriverManager.getDeviceInfo().getId(), devicesInfoParam);
            }
        }
    }
}


