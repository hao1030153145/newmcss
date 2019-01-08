/**
 * @project: mcss
 * @Title: BossUserController.java
 * @Package: com.transing.mcss.web.controller
 * <p>
 * Copyright (c) 2014-2017 Jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss.web.controller;

import com.jeeframework.util.cookie.CookieHelper;
import com.jeeframework.util.encrypt.BASE64Util;
import com.jeeframework.util.encrypt.MD5Util;
import com.jeeframework.util.validate.Validate;
import com.jeeframework.webframework.exception.SystemCode;
import com.jeeframework.webframework.exception.WebException;
import com.transing.mcss.biz.service.BossUserService;
import com.transing.mcss.biz.service.UserService;
import com.transing.mcss.constant.Constants;
import com.transing.mcss.integration.bo.BossUser;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.mangofactory.swagger.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller("bossUserController")
@Api(value = "后台用户登录注册，资料设置", description = "后台用户相关的访问接口", position = 1)
@ApiIgnore //忽略这个api生成文档
public class BossUserController {

    @Resource
    private BossUserService bossUserService;

    @Resource
    private UserService userService;

    @RequestMapping(value = "/login.html", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    @ApiOperation(value = "后台用户登录接口", position = 0)
    public ModelAndView login(HttpServletRequest req, HttpServletResponse res) {

        if (req.getMethod().equalsIgnoreCase("get")) {
            ModelAndView mv = new ModelAndView("user/login");
            mv.addObject("test", "hello");
			//freemarker页面可直接使用${test}获取变量
            return mv;
        }

        String userName = req.getParameter("userName");
        String passwd = req.getParameter("password");

        try {
            if (Validate.isEmpty(userName)) {
                throw new WebException(SystemCode.BIZ_LOGIN_NAME_EXCEPTION);
            }
            if (Validate.isEmpty(passwd)) {
                throw new WebException(SystemCode.BIZ_LOGIN_PASSWORD_EXCEPTION);
            }

            userName = userName.trim();

            BossUser bossUserFilter = new BossUser();
            bossUserFilter.setUserName(userName);
            bossUserFilter.setPasswd(passwd);

            BossUser bossUser = bossUserService.getBossUserByPasswd(bossUserFilter);

            if (bossUser == null) {
                throw new WebException(SystemCode.BIZ_LOGIN_PASSNOTRIGHT_EXCEPTION);
            }

            String remember = req.getParameter("remember");
            if (!Validate.isEmpty(remember)) {
                StringBuffer cookieLogin = new StringBuffer();
                cookieLogin.append(bossUser.getUid());

                long validTime = System.currentTimeMillis() + (Constants.COOKIE_MAX_AGE * 1000);
                // MD5加密用户详细信息
                String cookieValueWithMd5 = MD5Util.encrypt(bossUser.getUid() + ":" + bossUser.getPasswd() + ":" + validTime + ":" + Constants.LOGIN_KEY);
                // 将要被保存的完整的Cookie值
                String cookieValue = bossUser.getUid() + ":" + validTime + ":" + cookieValueWithMd5;
                // 再一次对Cookie的值进行BASE64编码
                String cookieValueBase64 = new String(BASE64Util.encode(cookieValue.getBytes()));
                // 是自动登录则设置cookie
                CookieHelper.setCookie(res, Constants.LOGIN_COOKIE_SIGN, cookieValueBase64, null, "/", Constants.COOKIE_ONE_YEAR_AGE); // 设置了自动登录，cookie在客户端保存2年
            }
            req.getSession().setMaxInactiveInterval(2 * 3600); // Session保存两小时
            req.getSession().setAttribute(Constants.WITH_SESSION_USER, bossUser);

            //            String randomString = StringUtils.getRandomString(6);
            //
            //            Map<String, String> result = new HashMap<String, String>();
            //            result.put("checkCode", randomString);
            //            SessionUtils.putCheckCode(mobile, randomString);

            req.setAttribute("user", bossUser);

            return new ModelAndView("redirect:/dashboard.html");
        } catch (WebException e) {
            req.setAttribute("error", e);
            return new ModelAndView("user/login");
        }


    }

    @RequestMapping(value = "/dashboard.html", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "后台用户综合访问界面", position = 1)
    public ModelAndView dashboard(HttpServletRequest req, HttpServletResponse res) {

        Map<String, Object> retMap = new HashMap<String, Object>();

        long userCount = userService.getUserCount(null);

        retMap.put("userCount", userCount);

        req.setAttribute("result", retMap);


        return new ModelAndView("user/dashboard");
    }

    @RequestMapping(value = "/logout.html", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "后台用户退出", position = 2)
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse res) {

        CookieHelper.setCookie(res, Constants.LOGIN_COOKIE_SIGN, "", null, "/", 0);

        req.getSession().removeAttribute(Constants.WITH_SESSION_USER);

        req.getSession().invalidate();

        return new ModelAndView("user/login");
    }

}
