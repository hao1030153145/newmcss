package com.transing.mcss4dpm.job.DealClass;

import com.transing.mcss4dpm.biz.service.ScriptService;
import com.transing.mcss4dpm.biz.service.impl.api.bo.WechatBO;
import com.transing.mcss4dpm.util.ApplicationContextHelper;

import java.util.List;

/**
 * ${微信处理类}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public class HelpClass {


    public static void getWeChatData() {

        ScriptService scriptService = (ScriptService) ApplicationContextHelper.getBean("scriptService");

        List<WechatBO> weChatBOList =  scriptService.getWeChatData();

        for (WechatBO wechatBO:weChatBOList){
            System.out.println("===============下面是获得的数据=================");
            System.out.println(wechatBO.getContactName());
            System.out.println(wechatBO.getSex());
            System.out.println(wechatBO.getRegion());
            System.out.println(wechatBO.getTag());
            System.out.println(wechatBO.getWeChatId());
            System.out.println(wechatBO.getWeChatName());
            System.out.println("===============上面是获得的数据=================");
        }
    }

}
