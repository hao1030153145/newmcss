package com.transing.mcss4dpm.util;

import com.jeeframework.util.validate.Validate;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.springframework.mock.web.MockHttpServletRequest;

import static junit.framework.TestCase.assertTrue;

/**
 * 描述
 *
 * @author lance
 * @version 1.0 2017-02-26 21:01
 */
public class WebUtilTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();


    @Test
    public void combinateStaticURL() throws Exception {
        String staticURL = WebUtil.combinateStaticURL("http://localhost");
        assertTrue(Validate.isUrl(staticURL));
    }

    @Test
    public void combinateStaticURLEmptyString() throws Exception {
        String staticURL = WebUtil.combinateStaticURL("");
        assertTrue(Validate.isEmpty(staticURL));
    }

    @Test
    public void combinateStaticURLNoHttpPrefix() throws Exception {
        String staticURL = WebUtil.combinateStaticURL("localhost");
        assertTrue(!Validate.isEmpty(staticURL));
    }

    @Test
    public void getWebServerByEnv() throws Exception {
        String webServerURL = WebUtil.getWebServerByEnv();
        assertTrue(!Validate.isEmpty(webServerURL));
    }

	@Test
    public void getUri() throws Exception {
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setAttribute("javax.servlet.include.servlet_path", "xxxx");
        String uri = WebUtil.getUri(httpServletRequest);
        assertTrue("xxxx".equals(uri));

        httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setServletPath("xxxx");
        String uri1 = WebUtil.getUri(httpServletRequest);
        assertTrue("xxxx".equals(uri1));
		
		httpServletRequest = new MockHttpServletRequest();
        httpServletRequest.setServletPath("");
        String uri2 = WebUtil.getUri(httpServletRequest);
        assertTrue("".equals(uri2));
    }

    @Test
    public void getUserAvatar() throws Exception {
        String avatar = WebUtil.getUserAvatar("/user_640.jpg", 120);
        assertTrue(Validate.isUrl(avatar) && avatar.endsWith("user_120.jpg"));

        String avatar1 = WebUtil.getUserAvatar("", 120);
        assertTrue(Validate.isEmpty(avatar1));

        String avatar2 = WebUtil.getUserAvatar("http://", 120);
        assertTrue("http://".equals(avatar2));

        String avatar3 = WebUtil.getUserAvatar("/user.jpg", 120);
        assertTrue(Validate.isUrl(avatar3) && avatar3.endsWith("user_120.jpg"));
		
		String avatar4 = WebUtil.getUserAvatar("user.jpg", 120);
        assertTrue(Validate.isUrl(avatar4) && avatar3.endsWith("user_120.jpg"));

        String avatar5 = WebUtil.getUserAvatar("user", 120);
        assertTrue(Validate.isUrl(avatar5) && avatar5.endsWith("user"));
    }

    @Test
    public void getHostNameByEnv() throws Exception {
        String confEnvOld = System.getProperty("conf.env");

        try {
            //test no conf.env
            System.setProperty("conf.env", "");
            assertTrue(WebUtil.getHostNameByEnv("localhost", "localhost", "dev.jeeframework.com", "test.jeeframework.com", "www.jeeframework.com").equals("localhost"));
            //test  conf.env  local
            System.setProperty("conf.env", "local");
            assertTrue(WebUtil.getHostNameByEnv("localhost", "localhost", "dev.jeeframework.com", "test.jeeframework.com", "www.jeeframework.com").equals("localhost"));
            //test  conf.env  dev
            System.setProperty("conf.env", "dev");
            assertTrue(WebUtil.getHostNameByEnv("localhost", "localhost", "dev.jeeframework.com", "test.jeeframework.com", "www.jeeframework.com").equals("dev.jeeframework.com"));
            //test  conf.env  test
            System.setProperty("conf.env", "test");
            assertTrue(WebUtil.getHostNameByEnv("localhost", "localhost", "dev.jeeframework.com", "test.jeeframework.com", "www.jeeframework.com").equals("test.jeeframework.com"));
            //test  conf.env  idc
            System.setProperty("conf.env", "idc");
            assertTrue(WebUtil.getHostNameByEnv("localhost", "localhost", "dev.jeeframework.com", "test.jeeframework.com", "www.jeeframework.com").equals("www.jeeframework.com"));
        } finally {
            if (null != confEnvOld) {
                System.setProperty("conf.env", confEnvOld);
            }
        }
    }


}