package com.transing.mcss4dpm.biz.service.impl.api.bo;

import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/11
 */
public class WechatBOs {

   List<WechatBO> wechatBOS;
   Integer count;

    public List<WechatBO> getWechatBOS() {
        return wechatBOS;
    }

    public void setWechatBOS(List<WechatBO> wechatBOS) {
        this.wechatBOS = wechatBOS;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
