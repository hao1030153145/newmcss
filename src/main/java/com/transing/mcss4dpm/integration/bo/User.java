package com.transing.mcss4dpm.integration.bo;

import com.jeeframework.logicframework.integration.bo.AbstractBO;

/**
 * 用户对象
 *
 * @author lanceyan
 * @version 1.0
 * @see AbstractBO
 */
public class User extends UserBase {
    private String mobile;
    private String birthday;
    private String token;
    private Integer sex;
    private String city;
    private String province;
    private String country;
    private Integer source;

    public static final  int SEX_MALE = 1; //男
    public static final  int SEX_FEMALE = 2;//女

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }
}