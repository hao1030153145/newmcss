/*
 * @project: mcss
 * @package: com.transing.mcss.web.inteceptor
 * @title:   LoginInterceptor.java 
 *
 * Copyright (c) 2017 jeeframework Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss.web.inteceptor;

import com.jeeframework.util.encrypt.BASE64Util;
import com.jeeframework.util.encrypt.MD5Util;
import com.jeeframework.util.validate.Validate;
import com.transing.mcss.biz.service.BossUserService;
import com.transing.mcss.constant.Constants;
import com.transing.mcss.integration.bo.BossUser;
import com.transing.mcss.web.filter.BossUserFilter;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 通用登录拦截器
 *
 * @author lance
 * @version 1.0 2017-02-14 9:32
 */
public abstract class LoginInterceptor implements HandlerInterceptor {

    @Resource
    protected BossUserService bossUserService;


    protected boolean validateLoginExpired(HttpSession session, String userInfoCookie) throws IOException {
        if (!Validate.isEmpty(userInfoCookie)) {
            userInfoCookie = new String(BASE64Util.decode(userInfoCookie));
            String[] loginInfo = userInfoCookie.split(":");

            String userId = loginInfo[0];
            String validTime = loginInfo[1];
            String cookieValueWithMd5 = loginInfo[2];

            BossUserFilter bossUserFilter = new BossUserFilter();
            bossUserFilter.setUid(Long.valueOf(userId));
            BossUser userObj = bossUserService.getBossUser(bossUserFilter);

            if (userObj != null) {
                String compareMd5 = MD5Util.encrypt(userObj.getUid() + ":" + userObj.getPasswd() + ":" + validTime + ":" + Constants.LOGIN_KEY);

                if (cookieValueWithMd5.equals(compareMd5)) {
                    session.setAttribute(Constants.WITH_SESSION_USER, userObj);
                    return true;
//                        }
                }
            }
        }
        return false;
    }

    protected boolean validNeedVerifyLoginStatus(HttpServletRequest request) {
        String confEnv = null;
        try {
            confEnv = System.getProperty("conf.env");
        } catch (Exception e) {
        }
        if (!Validate.isEmpty(confEnv) && (confEnv.equalsIgnoreCase("local") || confEnv.equalsIgnoreCase("dev") || confEnv.equalsIgnoreCase("ci")) ) {
            String testInLogin = request.getParameter("testInLogin");

            if (!Validate.isEmpty(testInLogin) && testInLogin.equals("no")) {
                return true;
            }

        }
        return false;
    }
}
