/**
 * @project: apptest2
 * @Title: HtmlLoginInterceptor.java
 * @Package: com.transing.mcss4dpm.web.inteceptor
 * <p/>
 * Copyright (c) 2014-2017 Jeeframework.com Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.inteceptor;

/**
 * html通用拦截器，拦截所有html页面请求校验用户是否登录
 * <p/>
 *
 * @Description: 拦截用户是否登录，没有登录跳到登陆页面，登录了跳到最后访问页面
 * @author lance
 * @version 1.0 2015-2-26 上午09:39:15
 */

import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.transing.mcss4dpm.util.WebUtil;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HtmlLoginInterceptor extends LoginInterceptor {
    public static final String LOGIN_HTML_URI = "/login.html";

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
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        response.setCharacterEncoding("utf-8");
        //不处理错误，交给全局异常处理
        String currentUri = WebUtil.getUri(request);
        if (currentUri.equals(LOGIN_HTML_URI)) {
            return true;
        }
        if (validUserLoginStatus(request)) {
            return true;
        } else {
            request.getRequestDispatcher(LOGIN_HTML_URI).forward(request, response);
            return false;
        }
    }


    //在业务处理器处理请求执行完成后,生成视图之前执行的动作
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String staticServer = WebUtil.getStaticServerByEnv();

        request.setAttribute("staticAddress", staticServer);
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用
     * <p/>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        //
    }
}