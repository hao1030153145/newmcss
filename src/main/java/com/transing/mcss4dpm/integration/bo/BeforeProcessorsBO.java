package com.transing.mcss4dpm.integration.bo;

import java.util.ArrayList;
import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/4/27
 */
public class BeforeProcessorsBO {
    private List<BeforeProcessorArrayBO> beforeProcessorArrayBOs=new ArrayList<>();

    public List<BeforeProcessorArrayBO> getBeforeProcessorArrayBOs() {
        return beforeProcessorArrayBOs;
    }

    public void setBeforeProcessorArrayBOs(List<BeforeProcessorArrayBO> beforeProcessorArrayBOs) {
        this.beforeProcessorArrayBOs = beforeProcessorArrayBOs;
    }
}
