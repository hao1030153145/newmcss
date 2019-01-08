/**
 * @project: apptest2
 * @Title: HtmlLoginInterceptor.java
 * @Package: com.transing.mcss.web.inteceptor
 * <p/>
 * Copyright (c) 2014-2017 Jeeframework.com Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss.web.inteceptor;

/**
 * Ajax拦截器，拦截ajax请求
 * <p/>
 *
 * @Description: 成功跳转到后续请求，失败返回json
 * @author lance
 * @version 1.0 2015-2-26 上午09:39:15
 */

import com.jeeframework.util.cookie.CookieHelper;
import com.jeeframework.webframework.controller.DataResponse;
import com.jeeframework.webframework.exception.SystemCode;
import com.transing.mcss.constant.Constants;
import com.transing.mcss.integration.bo.BossUser;
import com.transing.mcss.util.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;


public class AjaxLoginInterceptor extends LoginInterceptor {

    public AjaxLoginInterceptor() {
    }


    private String mappingURL;//利用正则映射到需要拦截的路径    

    public void setMappingURL(String mappingURL) {
        this.mappingURL = mappingURL;
    }

    /**
     * 在业务处理器处理请求之前被调用
     * 如果返回false
     * 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * <p/>
     * 如果返回true
     * 执行下一个拦截器,直到所有的拦截器都执行完毕
     * 再执行被拦截的Controller
     * 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle()
     * 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            return true;
//        response.setCharacterEncoding("utf-8");
//
//        String currentUri = WebUtil.getUri(request);
//
//        HttpSession session = request.getSession();
//
//        BossUser userBO = null;
//        response.setCharacterEncoding("UTF-8"); // 避免乱码
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
//
//        try {
//            if (validNeedVerifyLoginStatus(request)) return true;
//
//            request.setAttribute("currentUri", currentUri);
//
//            userBO = (BossUser) session.getAttribute(Constants.WITH_SESSION_USER);
//            if (userBO != null) {
//                return true;
//            }
//
//            String userInfoCookie = CookieHelper.getCookieValue(request, Constants.LOGIN_COOKIE_SIGN);
//            if (validateLoginExpired(session, userInfoCookie)) return true;
//
//
//            DataResponse dataResponse = new DataResponse();
//            dataResponse.setCode(SystemCode.BIZ_LOGIN_EXPIRED_EXCEPTION);
//            dataResponse.setMessage(SystemCode.getSystemCodeMessageByCode(SystemCode.BIZ_LOGIN_EXPIRED_EXCEPTION));
//
//            PrintWriter writer = response.getWriter();
//            writer.write(dataResponse.dataResponseToJsonStr());
//            writer.flush();
//            writer.close();
//            return false;
//        } catch (Exception e) {
//            DataResponse dataResponse = new DataResponse();
//            dataResponse.setCode(SystemCode.SYS_CONTROLLER_EXCEPTION);
//            dataResponse.setMessage(SystemCode.getSystemCodeMessageByCode(SystemCode.SYS_CONTROLLER_EXCEPTION) + " " + e);
//
//            PrintWriter writer = response.getWriter();
//            writer.write(dataResponse.dataResponseToJsonStr());
//            writer.flush();
//            writer.close();
//            return false;
//        } finally {
//            if (request != null) {
//                // 清除用户的角色信息
//                request.setAttribute(Constants.REQUEST_USERBO, userBO);
//            }
//
//        }
    }


    //在业务处理器处理请求执行完成后,生成视图之前执行的动作
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String staticServer = WebUtil.getStaticServerByEnv();

        request.setAttribute("staticServer", staticServer);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * <p/>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
    }


}