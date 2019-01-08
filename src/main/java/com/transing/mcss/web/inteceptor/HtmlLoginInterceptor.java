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
 * html通用拦截器，拦截所有html页面请求校验用户是否登录
 * <p/>
 *
 * @Description: 拦截用户是否登录，没有登录跳到登陆页面，登录了跳到最后访问页面
 * @author lance
 * @version 1.0 2015-2-26 上午09:39:15
 */

import com.jeeframework.util.cookie.CookieHelper;
import com.transing.mcss.constant.Constants;
import com.transing.mcss.integration.bo.BossUser;
import com.transing.mcss.util.WebUtil;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HtmlLoginInterceptor extends LoginInterceptor {

    public HtmlLoginInterceptor() {
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

//        response.setCharacterEncoding("utf-8");
//
//        String currentUri = WebUtil.getUri(request);
//
//        HttpSession session = request.getSession();
//
//        BossUser userBO = null;
//
//        try {
//            if (validNeedVerifyLoginStatus(request)) return true;
//
//
//            // 获取登录用户，用于显示到页面的头部上
//            // boss菜单上的menuId，回写在页面参数里
////            String menuId = (String) request.getParameter("menuId");
////            if (Validate.isEmpty(menuId)) {
////                menuId = CookieHelper.getCookieValue(request, Constants.BOSS_MENU_ID);
////                if (Validate.isEmpty(menuId)) {
////                    menuId = "1";
////                }
////            }
////
////            if (!menuId.equals("1")) {
////                CookieHelper.setCookie(response, Constants.BOSS_MENU_ID, menuId, null, "/", Constants.COOKIE_ONE_YEAR_AGE); // 设置了自动登录，cookie在客户端保存2年
////            }
//
//            request.setAttribute("currentUri", currentUri);
//
//            userBO = (BossUser) session.getAttribute(Constants.WITH_SESSION_USER);
//            if (userBO != null) {
////                request.getRequestDispatcher("/dashboard.html").forward(request, response);
//                return true;
//            }
//
//
//            String userInfoCookie = CookieHelper.getCookieValue(request, Constants.LOGIN_COOKIE_SIGN);
//            if (validateLoginExpired(session, userInfoCookie)) return true;
//
//            request.getRequestDispatcher("/login.html").forward(request, response);
//            return false;
//        } catch (Exception e) {
//            request.setAttribute("errorMsg", e.getMessage());
////            return "ERRORMESSAGE";
//            request.getRequestDispatcher("/login.html").forward(request, response);
//        } finally {
//            if (request != null) {
//                // 清除用户的角色信息
//                request.setAttribute(Constants.REQUEST_USERBO, userBO);
//
//
//            }
//
//        }
        return true;
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