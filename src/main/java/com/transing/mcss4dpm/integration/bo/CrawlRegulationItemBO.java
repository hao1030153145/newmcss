package com.transing.mcss4dpm.integration.bo;

import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/4/25
 */
public class CrawlRegulationItemBO {
    private CrawlRegulationListBO crawlRegulationListBO;

    private List<CrawlRegulationBO> crawlRegulationBOList;

    public CrawlRegulationListBO getCrawlRegulationListBO() {
        return crawlRegulationListBO;
    }

    public void setCrawlRegulationListBO(CrawlRegulationListBO crawlRegulationListBO) {
        this.crawlRegulationListBO = crawlRegulationListBO;
    }

    public List<CrawlRegulationBO> getCrawlRegulationBOList() {
        return crawlRegulationBOList;
    }

    public void setCrawlRegulationBOList(List<CrawlRegulationBO> crawlRegulationBOList) {
        this.crawlRegulationBOList = crawlRegulationBOList;
    }
}
