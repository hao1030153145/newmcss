package com.transing.mcss4dpm.job.DealClass;

import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.DevicesManager;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
import com.transing.mcss4dpm.biz.service.impl.api.script.WechatScript;

import java.io.IOException;
import java.util.List;

/**
 * ${微信处理类}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public class WeiXin {

    private static WeiXin inst = null;

    public static WeiXin getInstance() {
        if (inst == null) {
            inst = new WeiXin();
        }
        return inst;
    }

    private DriverManager2 driverManager;

    // 启动微信
    public void execueWeChat(AppiumSettingBo appiumSettingBo)  {

        AppiumAction appiumAction = new AppiumAction();
        // 实例化对象，并获得本机的连接设备
        driverManager = DriverManager2.getInstance();
        // 获得设备状态
        List<AppiumDriverManager> appiumDriverManagerList = driverManager.getDriverListsByStatus("0");
        if (appiumDriverManagerList.size() == 0) {
            System.out.println("XXXX: location_mcss : 没有可用设备");
            return;
        }

        for (AppiumDriverManager appiumDriverManager : appiumDriverManagerList){
            // 再次实例化脚本对象
            WechatScript wechatScript = new WechatScript(appiumDriverManager, driverManager);
            try {
                // 微信操作脚本，并获得最后抓取的数据
                List<WechatBO> wechatBOList = wechatScript.operateProcess(appiumSettingBo);
            } catch (InterruptedException | IOException e) {
                System.out.println("一点资讯异常 >>>>>>>" + e);
                e.printStackTrace();
            }finally {
                //释放设备
                driverManager.releaseAppiumDriver(appiumDriverManager);
            }
        }

    }

    // 导入联系人
    public void importContacts(String path) {

        DevicesManager devicesManager = new DevicesManager();
        List<String> list = devicesManager.getAndroidDevices();
        devicesManager.improtContacts(list, path);
    }

}
