package com.transing.mcss4dpm.biz.service.impl.api;

import com.transing.mcss4dpm.biz.service.impl.api.impl.AppiumActionImpl;
import io.appium.java_client.android.AndroidDriver;

import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/24
 */
public class AppiumAction extends AppiumActionImpl {
    @Override
    public Object findByName(Object o, String param) {
        return ((AndroidDriver) o).findElementByName(param);
    }

    @Override
    public List findsByName(Object o, String param) {
        return ((AndroidDriver) o).findElementsByName(param);
    }

    @Override
    public Object findByClassName(Object o, String param) {
        return ((AndroidDriver) o).findElementByClassName(param);
    }

    @Override
    public List findsByClassName(Object o) {
        return null;
    }

    @Override
    public Object findById(Object o, String param) {
        return ((AndroidDriver) o).findElementById(param);
    }

    @Override
    public List findsById(Object o, String param) {
        return ((AndroidDriver) o).findElementsById(param);
    }

    @Override
    public Object findByXpath(Object o, String param) {
        return ((AndroidDriver) o).findElementByXPath(param);
    }

    @Override
    public List findsByXpath(Object o) {
        return null;
    }

    @Override
    public Object findByAccessibilityId(Object o, String param) {
        return ((AndroidDriver) o).findElementByAccessibilityId(param);
    }

    @Override
    public List findsByAccessibilityId(Object o) {
        return null;
    }

    @Override
    public void sleep(long o) {
        try {
            Thread.sleep(o);
        } catch (InterruptedException e) {
            System.out.println("错误 >>>>>>>>>>   Threed sleep出错");
            e.printStackTrace();
        }
    }

    @Override
    public boolean haveElementBySource(Object o, String content) {
        return ((AndroidDriver) o).getPageSource().contains(content);
    }

}
