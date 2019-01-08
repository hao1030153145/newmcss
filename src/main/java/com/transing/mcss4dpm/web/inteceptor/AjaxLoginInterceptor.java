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
 * Ajax拦截器，拦截ajax请求
 * <p/>
 *
 * @Description: 成功跳转到后续请求，失败返回json
 * @author lance
 * @version 1.0 2015-2-26 上午09:39:15
 */

import com.jeeframework.webframework.controller.DataResponse;
import com.jeeframework.webframework.exception.SystemCode;
import com.transing.mcss4dpm.util.WebUtil;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.IOException;

public class AjaxLoginInterceptor extends LoginInterceptor {

    public static String LOGIN_JSON_URI="/login.html";
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

        response.setCharacterEncoding("UTF-8"); // 避免乱码
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); // 设置ContentType
        String currentUri = WebUtil.getUri(request);
        if (currentUri.equals(LOGIN_JSON_URI)) {
            return true;
        }
        if (validUserLoginStatus(request)) {
            return true;
        } else {
            outputException(response, SystemCode.BIZ_LOGIN_EXPIRED_EXCEPTION, SystemCode.getSystemCodeMessageByCode(SystemCode.BIZ_LOGIN_EXPIRED_EXCEPTION));
            return false;
        }
    }

    private void outputException(HttpServletResponse response,int errorCode,String errorMessage) throws IOException {
        DataResponse dataResponse = new DataResponse();
        dataResponse.setCode(errorCode);
        dataResponse.setMessage(errorMessage);

        PrintWriter writer = response.getWriter();
        writer.write(dataResponse.dataResponseToJsonStr());
        writer.flush();
        writer.close();
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
        // Do nothing
    }


}