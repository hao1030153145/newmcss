package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/25
 */
public class WechatBO {
    private String name;
    private String region;
    private String tag;
    private String number;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
