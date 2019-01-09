package com.transing.mcss4dpm.integration.bo;


import java.util.List;

public class CrawlWeiXinArticleBO {
    private String tid;
    private String url;
    private String formerUrl;
    private String replytimes;
    private String viewtimes;
    private String publishTime;
    private String title;
    private String author;
    private String source;
    private String weixinId;
    private String summary;
    private String content;
    private String type;
    private String imgUrl;
    private int sougoid;
    private List<WeixinCommentBO> commentlist;

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormerUrl() {
        return formerUrl;
    }

    public void setFormerUrl(String formerUrl) {
        this.formerUrl = formerUrl;
    }

    public String getReplytimes() {
        return replytimes;
    }

    public void setReplytimes(String replytimes) {
        this.replytimes = replytimes;
    }

    public String getViewtimes() {
        return viewtimes;
    }

    public void setViewtimes(String viewtimes) {
        this.viewtimes = viewtimes;
    }

    public List<WeixinCommentBO> getCommentlist() {
        return commentlist;
    }

    public void setCommentlist(List<WeixinCommentBO> commentlist) {
        this.commentlist = commentlist;
    }

    public int getSougoid() {
        return sougoid;
    }

    public void setSougoid(int sougoid) {
        this.sougoid = sougoid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getWeixinId() {
        return weixinId;
    }

    public void setWeixinId(String weixinId) {
        this.weixinId = weixinId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
