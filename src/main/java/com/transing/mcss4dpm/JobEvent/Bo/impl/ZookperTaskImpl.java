package com.transing.mcss4dpm.JobEvent.Bo.impl;
import com.jeeframework.jeetask.task.Task;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/16
 */
public class ZookperTaskImpl extends Task {
    private String dealClass;

    public String getDealClass() {
        return dealClass;
    }

    public void setDealClass(String dealClass) {
        this.dealClass = dealClass;
    }
}
