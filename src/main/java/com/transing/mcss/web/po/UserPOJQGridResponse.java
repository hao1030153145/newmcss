/*
 * @project: mcss 
 * @package: com.transing.mcss.web.po
 * @title:   JQGridResponse.java 
 *
 * Copyright (c) 2015 Hyfay Limited, Inc.
 * All rights reserved.
 */
package com.transing.mcss.web.po;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;

/**
 * jqgrid 响应内容
 * <p>
 * {
 * "total": "xxx", //总页数
 * "page": "yyy",  //当前页
 * "records": "zzz", //本次查询的记录数
 * "rows" : [
 * {"id" :"1", "name" : "xxxx", "addr" : "xxxx", ...},
 * {"id" :"2", "name" : "xxxx", "addr" : "xxxx", ...},
 * ...
 * ]
 * }
 *
 * @author lance
 * @version 1.0 2015-08-13 18:09
 */
@ApiModel(value = "JQ分页数据模型")
public class UserPOJQGridResponse {
    @ApiModelProperty(value = "总页数", required = true)
    private int total; //total pages
    @ApiModelProperty(value = "当前页", required = true)
    private int page; //current page
    @ApiModelProperty(value = "总记录数", required = true)
    private int records; //total records
    @ApiModelProperty(value = "数据列表", required = true)
    private ArrayList<UserPO> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public ArrayList<UserPO> getRows() {
        return rows;
    }

    public void setRows(ArrayList<UserPO> rows) {
        this.rows = rows;
    }
}
