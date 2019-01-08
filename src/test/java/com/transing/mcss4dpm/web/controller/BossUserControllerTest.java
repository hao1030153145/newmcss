package com.transing.mcss4dpm.web.controller;

import com.jeeframework.testframework.AbstractSpringBaseControllerTest;
import com.jeeframework.util.encrypt.BASE64Util;
import com.jeeframework.util.encrypt.MD5Util;
import com.jeeframework.webframework.exception.SystemCode;
import com.jeeframework.webframework.exception.WebException;
import com.transing.mcss.integration.bo.BossUser;
import com.transing.mcss4dpm.constant.Constants;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.servlet.http.Cookie;
import java.util.HashMap;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 系统用户管理单元测试类
 *
 * @author lance
 * @version 1.0 2017-02-14 14:17
 */
public class BossUserControllerTest extends AbstractSpringBaseControllerTest {
    @Test
    public void doGetLoginHtml() throws Exception {
        String requestURI = "/login.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/login"));
    }

    @Test
    public void doGetLoginJSON() throws Exception {
        String requestURI = "/login.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI)).andDo(print()).andExpect(status().isOk()).andReturn();
    }

    @Test
    public void login() throws Exception {
        String requestURI = "/login.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("userName", "admin").param("password", "111").param("remember", "on")).andDo(print()).andExpect(status().is3xxRedirection()).andReturn();
        assertTrue(mvcResult.getResponse().getRedirectedUrl().equals("/dashboard.html"));
    }
	
    @Test
    public void loginNoRemember() throws Exception {
        String requestURI = "/login.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("userName", "admin").param("password", "111")).andDo(print()).andExpect(status().is3xxRedirection()).andReturn();
        assertTrue(mvcResult.getResponse().getRedirectedUrl().equals("/dashboard.html"));
    }
	
    @Test
    public void loginNoName() throws Exception {
        String requestURI = "/login.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("userName", "").param("password", "111").param("remember", "on")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/login"));

        Object exceptionObject = mvcResult.getRequest().getAttribute("error");
        assertTrue(exceptionObject instanceof WebException);
        assertTrue(((WebException) exceptionObject).getErrorCode() == SystemCode.BIZ_LOGIN_NAME_EXCEPTION);

    }

    @Test
    public void loginNoPassword() throws Exception {
        String requestURI = "/login.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("userName", "admin").param("password", "").param("remember", "on")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/login"));

        Object exceptionObject = mvcResult.getRequest().getAttribute("error");
        assertTrue(exceptionObject instanceof WebException);
        assertTrue(((WebException) exceptionObject).getErrorCode() == SystemCode.BIZ_LOGIN_PASSWORD_EXCEPTION);

    }

    @Test
    public void loginInvalidUserNameAndPassword() throws Exception {
        String requestURI = "/login.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("userName", "admin").param("password", "xxx").param("remember", "on")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/login"));

        Object exceptionObject = mvcResult.getRequest().getAttribute("error");
        assertTrue(exceptionObject instanceof WebException);
        assertTrue(((WebException) exceptionObject).getErrorCode() == SystemCode.BIZ_LOGIN_PASSNOTRIGHT_EXCEPTION);

    }

    @Test
    public void accessWithCookie() throws Exception {
        long uid = 1L;
        String passwd = "*832EB84CB764129D05D498ED9CA7E5CE9B8F83EB";
        Cookie cookie = getCookie(uid, passwd);

        String requestURI = "/dashboard.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI).cookie(cookie)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/dashboard"));


        cookie = getCookie(999, passwd);
        mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI).cookie(cookie)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getForwardedUrl().equals("/login.html"));

        cookie = getCookie(uid, "");
        mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI).cookie(cookie)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getForwardedUrl().equals("/login.html"));
    }

    private Cookie getCookie(long uid, String passwd) {
        StringBuilder cookieLogin = new StringBuilder();
        cookieLogin.append(uid);

        long validTime = System.currentTimeMillis() + (Constants.COOKIE_MAX_AGE * 1000);
        // MD5加密用户详细信息
        String cookieValueWithMd5 = MD5Util.encrypt(uid + ":" + passwd + ":" + validTime + ":" + Constants.LOGIN_KEY);
        // 将要被保存的完整的Cookie值
        String cookieValue = uid + ":" + validTime + ":" + cookieValueWithMd5;
        // 再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = BASE64Util.encode(cookieValue.getBytes());
        // 是自动登录则设置cookie
        return new Cookie(Constants.LOGIN_COOKIE_SIGN, cookieValueBase64);
    }

    @Test
    public void dashboard() throws Exception {
        String requestURI = "/dashboard.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI).param("testInLogin", "no")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/dashboard"));
    }
	
	@Test
    public void testHtmlInterceptorException() throws Exception {
        // 再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = BASE64Util.encode("11".getBytes());
        // 是自动登录则设置cookie
        Cookie cookie = new Cookie(Constants.LOGIN_COOKIE_SIGN, cookieValueBase64);

        String requestURI = "/dashboard.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI).cookie(cookie)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getResponse().getForwardedUrl().equals("/login.html"));

        Object exceptionObject = mvcResult.getRequest().getAttribute("errorMsg");
        assertTrue(exceptionObject != null);
    }
	
	@Test
    public void testInterceptorSession() throws Exception {

        HashMap<String, Object> sessionattr = new HashMap<>();
        sessionattr.put(Constants.WITH_SESSION_USER, new BossUser());

        String requestURIBusiness = "/dashboard.html";
        MvcResult mvcResultBusiness = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURIBusiness).sessionAttrs(sessionattr)).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResultBusiness.getModelAndView().getViewName().equals("user/dashboard"));
    }
	
	@Test
    public void testAjaxInterceptorException() throws Exception {
        // 再一次对Cookie的值进行BASE64编码
        String cookieValueBase64 = BASE64Util.encode("11".getBytes());
        // 是自动登录则设置cookie
        Cookie cookie = new Cookie(Constants.LOGIN_COOKIE_SIGN, cookieValueBase64);

        String requestURI = "/addBossUser.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).cookie(cookie)).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == SystemCode.SYS_CONTROLLER_EXCEPTION);
    }

    @Test
    public void logout() throws Exception {
        String requestURI = "/logout.html";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.get(requestURI).param("testInLogin", "no")).andDo(print()).andExpect(status().isOk()).andReturn();
        assertTrue(mvcResult.getModelAndView().getViewName().equals("user/login"));
    }

    @Test
//    @Rollback(true)
    public void addBossUserJSONNoLogin() throws Exception {
        String requestURI = "/addBossUser.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("testInLogin", "no")).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0 && JSONObject.fromObject(response).getJSONObject("data").getInt("uid") > 0);
    }

    @Test
    public void updateBossUserJSONNoLogin() throws Exception {
        String requestURI = "/updateBossUser.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("testInLogin", "no")).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0 && JSONObject.fromObject(response).getJSONObject("data").getInt("updateRows") > 0);
    }

    @Test
    public void deleteBossUserJSONNoLogin() throws Exception {
        String requestURI = "/deleteBossUser.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("testInLogin", "no")).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0 && JSONObject.fromObject(response).getJSONObject("data").getInt("updateRows") > 0);
    }

    @Test
    public void getBossUsersJSONNoLogin() throws Exception {
        String requestURI = "/getBossUsers.json";
        MvcResult mvcResult = this.mockMvc.perform(
                MockMvcRequestBuilders.post(requestURI).param("testInLogin", "no")).andDo(print()).andExpect(status().isOk()).andReturn();
        String response = mvcResult.getResponse().getContentAsString();
        assertTrue(com.jeeframework.util.json.JSONUtils.isJSONValid(response));
        assertTrue(JSONObject.fromObject(response).getInt("code") == 0 && JSONObject.fromObject(response).getJSONArray("data").size() > 0);
    }

}