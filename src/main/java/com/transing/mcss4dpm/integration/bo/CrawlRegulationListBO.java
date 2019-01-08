package com.transing.mcss4dpm.integration.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/4/25
 */
public class CrawlRegulationListBO {
    private int step;

    private String crawlArray;

    private String afterprocessorArray;

    private String beforeprocessorArray;

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getCrawlArray() {
        return crawlArray;
    }

    public void setCrawlArray(String crawlArray) {
        this.crawlArray = crawlArray;
    }

    public String getAfterprocessorArray() {
        return afterprocessorArray;
    }

    public void setAfterprocessorArray(String afterprocessorArray) {
        this.afterprocessorArray = afterprocessorArray;
    }

    public String getBeforeprocessorArray() {
        return beforeprocessorArray;
    }

    public void setBeforeprocessorArray(String beforeprocessorArray) {
        this.beforeprocessorArray = beforeprocessorArray;
    }
}
