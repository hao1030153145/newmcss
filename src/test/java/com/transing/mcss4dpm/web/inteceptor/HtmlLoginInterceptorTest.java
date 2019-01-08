package com.transing.mcss4dpm.web.inteceptor;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import static junit.framework.TestCase.assertTrue;

/**
 * 测试登录拦截器的代码
 *
 * @author lance
 * @version 1.0 2017-02-28 22:32
 */
public class HtmlLoginInterceptorTest {
    @Test
    public void preHandle() throws Exception {

        String confEnvOld = System.getProperty("conf.env");

        try {

            HtmlLoginInterceptor htmlLoginInterceptor = new HtmlLoginInterceptor();

            MockHttpServletRequest mockHttpServletRequest = new MockHttpServletRequest();
            MockHttpServletResponse mockHttpServletResponse = new MockHttpServletResponse();
            mockHttpServletRequest.setAttribute("javax.servlet.include.servlet_path", "xxxx");

            boolean result;

            //test no conf.env
            System.setProperty("conf.env", "");
            result = htmlLoginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null);
            assertTrue(!result);
            //test  conf.env  local
            System.setProperty("conf.env", "local");
            result = htmlLoginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null);
            assertTrue(!result);
            //test  conf.env  ci
            System.setProperty("conf.env", "ci");
            result = htmlLoginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null);
            assertTrue(!result);
            //test  conf.env  dev
            System.setProperty("conf.env", "dev");
            mockHttpServletRequest.setParameter("testInLogin", "xxx");
            result = htmlLoginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null);
            assertTrue(!result);

            mockHttpServletRequest = new MockHttpServletRequest();

            //test  conf.env  test
            System.setProperty("conf.env", "test");
            result = htmlLoginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null);
            assertTrue(!result);
            //test  conf.env  idc
            System.setProperty("conf.env", "idc");
            result = htmlLoginInterceptor.preHandle(mockHttpServletRequest, mockHttpServletResponse, null);
            assertTrue(!result);


        } finally {
            if (null != confEnvOld) {
                System.setProperty("conf.env", confEnvOld);
            }
        }

    }
}