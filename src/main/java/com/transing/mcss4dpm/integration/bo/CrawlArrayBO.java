package com.transing.mcss4dpm.integration.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/3/12
 */
public class CrawlArrayBO {
    private String crawlType;

    private String crawlValue;

    public void setCrawlType(String crawlType){
        this.crawlType = crawlType;
    }
    public String getCrawlType(){
        return this.crawlType;
    }
    public void setCrawlValue(String crawlValue){
        this.crawlValue = crawlValue;
    }
    public String getCrawlValue(){
        return this.crawlValue;
    }
}
