package com.transing.mcss4dpm.integration.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/22
 */
public class CrawlRegulationBO {
    private int id;
    private int datasourceId;
    private int datatypeId;
    private int scriptId;
    private int scriptdetailId;
    private String item;
    private String description;
    private int isnull;
    private String type;
    private String step;
    private String length;
    private int datafrom;//0:输入参数 1:内置变量  2:抓取参数
    private String crawlParam;
    private String crawlArray;
    private String afterProcessorArray;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDatasourceId() {
        return datasourceId;
    }

    public void setDatasourceId(int datasourceId) {
        this.datasourceId = datasourceId;
    }

    public int getDatatypeId() {
        return datatypeId;
    }

    public void setDatatypeId(int datatypeId) {
        this.datatypeId = datatypeId;
    }

    public int getScriptId() {
        return scriptId;
    }

    public void setScriptId(int scriptId) {
        this.scriptId = scriptId;
    }

    public int getScriptdetailId() {
        return scriptdetailId;
    }

    public void setScriptdetailId(int scriptdetailId) {
        this.scriptdetailId = scriptdetailId;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIsnull() {
        return isnull;
    }

    public void setIsnull(int isnull) {
        this.isnull = isnull;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public int getDatafrom() {
        return datafrom;
    }

    public void setDatafrom(int datafrom) {
        this.datafrom = datafrom;
    }

    public String getCrawlParam() {
        return crawlParam;
    }

    public void setCrawlParam(String crawlParam) {
        this.crawlParam = crawlParam;
    }

    public String getCrawlArray() {
        return crawlArray;
    }

    public void setCrawlArray(String crawlArray) {
        this.crawlArray = crawlArray;
    }

    public String getAfterProcessorArray() {
        return afterProcessorArray;
    }

    public void setAfterProcessorArray(String afterProcessorArray) {
        this.afterProcessorArray = afterProcessorArray;
    }
}
