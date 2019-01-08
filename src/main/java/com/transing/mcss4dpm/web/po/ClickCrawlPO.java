package com.transing.mcss4dpm.web.po;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "点击抓取,保存源码")
public class ClickCrawlPO {
    private String path;

    private int count;
    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
