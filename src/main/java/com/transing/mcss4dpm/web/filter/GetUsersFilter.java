/**
 * @project: with
 * @Title: UserPartyFilter.java
 * @Package: com.transing.mcss4dpm.web.filter
 * <p/>
 * Copyright (c) 2014-2017 Jeeframework.com Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss4dpm.web.filter;

/**
 * 用户查询对象
 * <p>
 *
 * @author lance
 * @version 1.0 2015-3-4 下午08:07:47
 * @Description: 用户频道查询对象
 */
public class GetUsersFilter {

    private String orderBy;
    private String orderDirect;

    private String nickName;

    private Integer source;

    private Integer startRow;
    private Integer pageSize;

    private String greatCreateTime;
    private String lessCreateTime;

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderDirect() {
        return orderDirect;
    }

    public void setOrderDirect(String orderDirect) {
        this.orderDirect = orderDirect;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getStartRow() {
        return startRow;
    }

    public void setStartRow(Integer startRow) {
        this.startRow = startRow;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getGreatCreateTime() {
        return greatCreateTime;
    }

    public void setGreatCreateTime(String greatCreateTime) {
        this.greatCreateTime = greatCreateTime;
    }

    public String getLessCreateTime() {
        return lessCreateTime;
    }

    public void setLessCreateTime(String lessCreateTime) {
        this.lessCreateTime = lessCreateTime;
    }
}
