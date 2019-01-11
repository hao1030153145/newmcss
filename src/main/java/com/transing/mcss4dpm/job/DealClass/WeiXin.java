package com.transing.mcss4dpm.job.DealClass;

import com.transing.mcss4dpm.biz.service.impl.api.AppiumAction;
import com.transing.mcss4dpm.biz.service.impl.api.DevicesManager;
import com.transing.mcss4dpm.biz.service.impl.api.DriverManager2;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumDriverManager;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
import com.transing.mcss4dpm.biz.service.impl.api.script.WechatScript;

import java.util.ArrayList;
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
    public List<List<WechatBO>> executeWeChat(AppiumSettingBo appiumSettingBo)  {

        List<List<WechatBO>> weChatList2 = new ArrayList<>();
        System.out.println("开始启动");
        AppiumAction appiumAction = new AppiumAction();
        // 实例化对象，并获得本机的连接设备
        driverManager = DriverManager2.getInstance();
        // 获得设备状态
        List<AppiumDriverManager> appiumDriverManagerList = driverManager.getDriverListsByStatus("0");
        if (appiumDriverManagerList.size() == 0) {
            System.out.println("XXXX: location_mcss : 没有可用设备");
            return null;
        }

        for (AppiumDriverManager appiumDriverManager : appiumDriverManagerList){
            List<WechatBO> weChatBOList = new ArrayList<>();
            // 再次实例化脚本对象
            WechatScript wechatScript = new WechatScript(appiumDriverManager, driverManager);
            try {
                // 微信操作脚本，并获得最后抓取的数据
                weChatBOList = wechatScript.operateProcess(appiumSettingBo);
                weChatList2.add(weChatBOList);
            } catch (Exception e) {
                System.out.println("执行异常 >>>>>>>" + e);
                e.printStackTrace();
                return weChatList2;
            }finally {
                //释放设备
                driverManager.releaseAppiumDriver(appiumDriverManager);
            }
        }
        return weChatList2;
    }

    // 导入联系人
    public void importContacts(String path) {

        DevicesManager devicesManager = new DevicesManager();
        List<String> list = devicesManager.getAndroidDevices();
        devicesManager.improtContacts(list, path);
    }

}
