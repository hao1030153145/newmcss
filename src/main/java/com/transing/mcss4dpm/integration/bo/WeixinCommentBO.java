package com.transing.mcss4dpm.integration.bo;
import java.util.Date;


public class WeixinCommentBO {
    private String parent;
    private String author;
    private String icon;
    private Date datetime;
    private String content;
    private String reply;
//    private Date replytime;
    private int ltimes;
    private int rltimes;
    private Date crawltime;

//    private String commenter;
//    private String content;
//    private Date commentTime;
//    private String commentAvatar;
//    private int praiseNum;
//    private String replyConent;
//    private Date replyTime;
//    private int replyPraiseNum;
//    private Date createtime;
//    private String detailId;


    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

//    public Date getReplytime() {
//        return replytime;
//    }
//
//    public void setReplytime(Date replytime) {
//        this.replytime = replytime;
//    }

    public int getLtimes() {
        return ltimes;
    }

    public void setLtimes(int ltimes) {
        this.ltimes = ltimes;
    }

    public int getRltimes() {
        return rltimes;
    }

    public void setRltimes(int rltimes) {
        this.rltimes = rltimes;
    }

    public Date getCrawltime() {
        return crawltime;
    }

    public void setCrawltime(Date crawltime) {
        this.crawltime = crawltime;
    }


}
