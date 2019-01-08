package com.transing.mcss4dpm.integration.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/4/19
 */
public class CircleBO {
    private int circleStep;
    private int circlemaxsize;
    private CircleRegulationRootBO circleRegulationArrayBO;
    private String circleCutOffType; //分页条件类型
    private String circleCutOffValue; //分页条件值

    public int getCircleStep() {
        return circleStep;
    }

    public void setCircleStep(int circleStep) {
        this.circleStep = circleStep;
    }

    public int getCirclemaxsize() {
        return circlemaxsize;
    }

    public void setCirclemaxsize(int circlemaxsize) {
        this.circlemaxsize = circlemaxsize;
    }

    public CircleRegulationRootBO getCircleRegulationArrayBO() {
        return circleRegulationArrayBO;
    }

    public void setCircleRegulationArrayBO(CircleRegulationRootBO circleRegulationArrayBO) {
        this.circleRegulationArrayBO = circleRegulationArrayBO;
    }

    public String getCircleCutOffType() {
        return circleCutOffType;
    }

    public void setCircleCutOffType(String circleCutOffType) {
        this.circleCutOffType = circleCutOffType;
    }

    public String getCircleCutOffValue() {
        return circleCutOffValue;
    }

    public void setCircleCutOffValue(String circleCutOffValue) {
        this.circleCutOffValue = circleCutOffValue;
    }
}
