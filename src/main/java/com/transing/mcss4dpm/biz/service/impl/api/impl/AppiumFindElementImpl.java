package com.transing.mcss4dpm.biz.service.impl.api.impl;


import java.util.List;

/**
 * ${appium规则接口}
 *
 * @author haolen
 * @version 1.0 2018/1/10
 */
public interface AppiumFindElementImpl<T, E> {

    /**
     * 简单描述:文字定位元素  test
     */
    public E findByName(T t, String param);

    public List<E> findsByName(T t, String param);

    /**
     * 简单描述:类名定位元素  class
     */
    public E findByClassName(T t, String param);

    public List<E> findsByClassName(T t);

    /**
     * 简单描述:控件id定位元素  resource-id
     */
    public E findById(T t, String param);

    public List<E> findsById(T t, String param);

    /**
     * 简单描述:xpath定位元素
     */
    public E findByXpath(T t, String param);

    public List<E> findsByXpath(T t);

    /**
     * 简单描述:内容描述定位元素  content-desc
     */
    public E findByAccessibilityId(T t, String param);

    public List<E> findsByAccessibilityId(T t);

    /**
     * 简单描述:内容描述定位元素  content-desc
     */
    public void sleep(long t);

    /**
     * 简单描述:寻找页面是否有某字段  content-desc
     */
    public boolean haveElementBySource(Object o,String content);
}
