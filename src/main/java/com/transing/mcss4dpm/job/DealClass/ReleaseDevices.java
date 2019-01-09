package com.transing.mcss4dpm.job.DealClass;

import com.jeeframework.logicframework.integration.sao.zookeeper.BaseSaoZookeeper;
import com.jeeframework.webframework.exception.WebException;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.web.exception.MySystemCode;

/**
 * ${释放设备}
 *
 * @author haolen
 * @version 1.0 2018/2/28
 */
public class ReleaseDevices {

    private static ReleaseDevices inst = null;

    public static ReleaseDevices getInstance() {
        if (inst == null) {
            inst = new ReleaseDevices();
        }
        return inst;
    }

    public void execue(String task) {
        BaseSaoZookeeper baseSaoZookeeper =null;
        String deviceId = null;
        String serverName = null;
        DriverManager2 driverManager2 = DriverManager2.getInstance();
        AppiumDriverManager driverManager = driverManager2.getDriverByDevicesId(Long.parseLong(deviceId));
        driverManager2.releaseAppiumDriver(driverManager);
        String baseUsedPath = "/mcss_device_used";
        String path = baseUsedPath + "/" + serverName + "_" + deviceId;
        System.out.println("准备删除zookeeper");
        for (int i = 0; i < 3; i++) {
            System.out.println("进入循环准备删除zookeeper");
            try {
                System.out.println("zook的路径path================="+path+"==============该路径是否存在======"+baseSaoZookeeper.isExisted(path));
                if (baseSaoZookeeper.isExisted(path)) {
                    System.out.println("存在准备删除zookeeper");
                    baseSaoZookeeper.delete(path, true);
                    System.out.println("删除zookeeper成功。删除的路径为path==========="+path);
                }
                break;
            } catch (Exception e) {
                System.out.println("zk删除设备失败    >>>>>>>>>>   " + path);
                throw new WebException(MySystemCode.GET_DELETE_DEVICE_NULL);
            }
        }
    }
}
