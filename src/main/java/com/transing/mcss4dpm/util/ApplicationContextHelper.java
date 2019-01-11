package com.transing.mcss4dpm.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 用于在非controller里调用service
 * @author haolen
 * @Date 2019-1-11 18:10:55
 */
public class ApplicationContextHelper implements ApplicationContextAware {

    private static ApplicationContext appCtx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextHelper.appCtx = applicationContext;
    }

    public static ApplicationContext getApplicationContext(){
        return appCtx;
    }


    public static Object getBean(String beanName) {
        return appCtx.getBean(beanName);
    }

    public static <T> T getBean(Class<T> clz) {
        return (T)appCtx.getBean(clz);
    }
}
