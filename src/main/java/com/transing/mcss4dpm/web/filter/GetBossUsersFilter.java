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
 * 查询用户列表对象
 * <p>
 *
 * @author lance
 * @version 1.0 2015-3-4 下午08:07:47
 * @Description: 查询用户列表对象
 */
public class GetBossUsersFilter {
    private Long startRow;
    private Long pageSize;

    public Long getStartRow() {
        return startRow;
    }

    public void setStartRow(Long startRow) {
        this.startRow = startRow;
    }

    public Long getPageSize() {
        return pageSize;
    }

    public void setPageSize(Long pageSize) {
        this.pageSize = pageSize;
    }
}
