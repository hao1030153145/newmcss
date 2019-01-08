package com.transing.mcss.web.controller;

import com.transing.mcss.biz.service.UserService;
import com.transing.mcss.web.po.NotifyPO;
import com.transing.mcss.web.po.UserPOJQGridResponse;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller("weixinController")
@Api(value = "微信管理", description = "微信相关的访问接口", position = 2)
public class WeixinController {

    @RequestMapping(value = "/weixin/notify.json", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通知代理程序准备抓取", position = 1)
    public NotifyPO weixinNotifyJSON(HttpServletRequest req, HttpServletResponse res){
        NotifyPO response=new NotifyPO();
        response.setUrl("www.baidu.com");
        response.setTaskid(12);
        response.setPid(2);
        response.setDatasourceid(1);
        response.setDatatypeid(1);
        return response;
    }
}
