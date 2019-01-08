/**
 * 1.0   lanceyan  2008-5-20  Create
 */

package com.transing.mcss4dpm.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

/**
 * 注册用户页面对象
 */
@ApiModel(value = "用户数据模型")
public class UserPO {
    @ApiModelProperty(value = "用户id", required = true)
    private long uid;
    @ApiModelProperty(value = "用户昵称", required = true)
    private String nickName;
    @ApiModelProperty(value = "用户描述信息", required = true)
    private String description;
    @ApiModelProperty(value = "用户头像", required = true)
    private String avatar;
    @ApiModelProperty(value = "用户注册时间", required = true)
    private String createTime;
    @ApiModelProperty(value = "用户性别", required = true)
    private String sex;
    @ApiModelProperty(value = "用户城市信息", required = true)
    private String city;
    @ApiModelProperty(value = "用户省份信息", required = true)
    private String province;
    @ApiModelProperty(value = "用户国家信息", required = true)
    private String country;
    @ApiModelProperty(value = "用户注册来源", required = true)
    private String source;


    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
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

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}


