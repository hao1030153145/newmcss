package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/9
 */
public class WechatBO {

    private String weChatName;   // 微信名
    private String weChatId;     // 微信号
    private String region;       // 地区
    private String tag;          // 个性标签
    private String contactName;  // 联系名 ，后面跟数据库的做匹配
    private String sex;          // 性别

    public String getWeChatName() {
        return weChatName;
    }

    public void setWeChatName(String weChatName) {
        this.weChatName = weChatName;
    }

    public String getWeChatId() {
        return weChatId;
    }

    public void setWeChatId(String weChatId) {
        this.weChatId = weChatId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
