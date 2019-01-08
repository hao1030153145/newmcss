package com.transing.mcss4dpm.biz.service.impl.api.TaskInputParam;

/**
 * 简单描述：根据url抓取微信文章输入参数类
 * <p/>
 *
 * @
 */
public class WeixinArticleTask {
    private String url;
    private String crawlComment;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCrawlComment() {
        return crawlComment;
    }

    public void setCrawlComment(String crawlComment) {
        this.crawlComment = crawlComment;
    }
}
