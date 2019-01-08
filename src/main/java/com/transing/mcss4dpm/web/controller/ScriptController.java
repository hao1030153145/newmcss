/**
 * @project: mcss4dpm
 * @Title: UserController.java
 * @Package: com.transing.mcss4dpm.web.controller
 * <p>
 * Copyright (c) 2014-2017 Jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.controller;

import com.transing.mcss4dpm.biz.service.ScriptService;
import com.transing.mcss4dpm.biz.service.impl.api.bo.AppiumSettingBo;
import com.transing.mcss4dpm.biz.service.impl.api.bo.ChromeOption;
import com.transing.mcss4dpm.integration.bo.ContactBO;
import com.transing.mcss4dpm.job.DealClass.WeiXin;
import com.transing.mcss4dpm.web.po.AppVersionPO;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller("scriptController")
@Api(value = "脚本操作接口", description = "脚本操作相应接口", position = 2)
public class ScriptController {

    @Resource
    private ScriptService scriptService;


    @RequestMapping(value = "/importContacts.json", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "导入通讯录", notes = "", response = AppVersionPO.class, position = 0)
    public Map getAppVersionJSON(HttpServletRequest request, HttpServletResponse response) throws IOException {


        String path = request.getContextPath();
        File file = new File(path + "content.vcf");//实例化File类对象
        if (!file.exists()) {//文件判断，没有则生成
            file.createNewFile();
        }
        List<ContactBO> contactBOList = scriptService.getContactList();
        ScriptController.content(file, contactBOList);//生成通讯录的方法
        WeiXin weiXin = new WeiXin();
        weiXin.importContacts(path + "content.vcf");
        return new HashMap();
    }

    @RequestMapping(value = "/launchWeChat.json", method = {RequestMethod.POST})
    @ResponseBody
    @ApiOperation(value = "启动微信", notes = "", response = AppVersionPO.class, position = 0)
    public Map launchWeChat(@RequestParam(value = "choseApp", required = true) @ApiParam String choseApp,
                            HttpServletRequest request, HttpServletResponse response) throws IOException {

        WeiXin weiXin = new WeiXin();
        // 根据传入的数据，来启动对应的app
        switch (choseApp) {
            case "1":
                weiXin.execueWeChat(weChatParam());
                break;
            case "2":
                System.out.println("启动message");
                break;
            case "3":
                System.out.println("启动Facebook");
                break;
            default:
                System.out.println("没得这个应用");
                break;
        }
        return new HashMap();
    }


    private static AppiumSettingBo weChatParam() throws IOException {

        AppiumSettingBo appiumSettingBo = new AppiumSettingBo();
        // 这里是微信的启动参数, 其它应用只用改哈包名和类名就可以了
        appiumSettingBo.setApp("");
        appiumSettingBo.setAppActivity(".ui.LauncherUI");
        appiumSettingBo.setAppPackage("com.tencent.mm");
        appiumSettingBo.setUnicodeKeyboard(true);
        appiumSettingBo.setNoReset(true);
        appiumSettingBo.setNewCommandTimeout(300);
        appiumSettingBo.setFullReset(false);
        appiumSettingBo.setPlatformName("Android");
        appiumSettingBo.setAutomationName("Appium");

        ChromeOption chromeOption = new ChromeOption();
        chromeOption.setAndroidActivity(".plugin.webview.ui.tools.WebViewUI");
        chromeOption.setAndroidPackage("com.tencent.mm");
        chromeOption.setAndroidProcess("com.tencent.mm:tools");
        chromeOption.setAndroidUseRunningApp(true);
        appiumSettingBo.setChromeOption(chromeOption);

        return appiumSettingBo;
    }

    public static void content(File file, List<ContactBO> contactBOList) throws IOException {
        // 通讯录格式
        // BEGIN:VCARD
        // VERSION:3.0
        // N;CHARSET=UTF-8:haolen
        // FN;CHARSET=UTF-8:haolen
        // TEL;TYPE=CELL: 13158780233
        // END:VCARD
        OutputStream out = new FileOutputStream(file, true);//实现追加输出
        PrintStream print = new PrintStream(out, true, "UTF-8");
        for (ContactBO contactBO : contactBOList) {
            String N = "N;CHARSET=UTF-8:";//必须加
            N = N + contactBO.getName();
            String FN = "FN;CHARSET=UTF-8:";//必须加上
            FN = FN + contactBO.getName();
            String TEL = "TEL;TYPE=CELL:";
            TEL = TEL + contactBO.getPhone();//追加手机号
            print.println("BEGIN:VCARD");//按照格式打印出来
            print.println("VERSION:3.0");
            print.println(N);
            print.println(FN);
            print.println(TEL);
            print.println("END:VCARD");
        }
        out.close();
        print.close();//打印流也需要手动关闭
    }

}
