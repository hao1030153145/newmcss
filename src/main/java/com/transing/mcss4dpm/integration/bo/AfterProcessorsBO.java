package com.transing.mcss4dpm.integration.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/3/22
 */
public class AfterProcessorsBO {
    private List<AfterProcessorArrayBO> afterProcessorArray=new ArrayList<>();

    public List<AfterProcessorArrayBO> getAfterProcessorArray() {
        return afterProcessorArray;
    }

    public void setAfterProcessorArray(List<AfterProcessorArrayBO> afterProcessorArray) {
        this.afterProcessorArray = afterProcessorArray;
    }
}
