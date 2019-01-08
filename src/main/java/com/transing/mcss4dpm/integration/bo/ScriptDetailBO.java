package com.transing.mcss4dpm.integration.bo;

/**
 * ${脚本信息详情模型}
 *
 * @author haolen
 * @version 1.0 2018/1/22
 */
public class ScriptDetailBO {
    private int id;
    private int scriptid;
    private int step;
    private int elementtype; //获取元素方法id
    private String elementvalue; //获取元素输入值
    private String elementinputparam; //获取元素输入参数
    private String elementInputParamType;//表达式为1  输入参数为2
    private int actiontype; //操作动作id
    private String actionvalue; //操作动作输入值
    private String actionInputParam; //操作动作输入值
    private String actionafterprocessor; //操作动作后置处理器(以key,value的形式存储)
    private String actionInputParamType;//固定值1  输入参数为2
    private int block;//是否阻断
    private int blockType;//阻断类型
    private String blockValue;//阻断值
    private int back; //回退步骤
    private String path;
    private int circle; //是否翻页
    private int circlestep; //翻页步骤
    private int circlestepsize; //'步长'
    private int circlemaxsize; //最长条数
    private String circleinputtype; //最长条数
    private String circleinputparamId; //输入参数dpm值
    private String circleinputparam; //输入参数固定值
    private String circleCutOffType; //分页条件类型
    private String circleCutOffValue; //分页条件值
    private String circleRegulation; //分页条件

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScriptid() {
        return scriptid;
    }

    public void setScriptid(int scriptid) {
        this.scriptid = scriptid;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public int getElementtype() {
        return elementtype;
    }

    public void setElementtype(int elementtype) {
        this.elementtype = elementtype;
    }

    public String getElementvalue() {
        return elementvalue;
    }

    public void setElementvalue(String elementvalue) {
        this.elementvalue = elementvalue;
    }

    public String getElementinputparam() {
        return elementinputparam;
    }

    public void setElementinputparam(String elementinputparam) {
        this.elementinputparam = elementinputparam;
    }

    public int getActiontype() {
        return actiontype;
    }

    public void setActiontype(int actiontype) {
        this.actiontype = actiontype;
    }

    public String getActionvalue() {
        return actionvalue;
    }

    public void setActionvalue(String actionvalue) {
        this.actionvalue = actionvalue;
    }

    public String getActionafterprocessor() {
        return actionafterprocessor;
    }

    public void setActionafterprocessor(String actionafterprocessor) {
        this.actionafterprocessor = actionafterprocessor;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getCircle() {
        return circle;
    }

    public void setCircle(int circle) {
        this.circle = circle;
    }

    public int getCirclestep() {
        return circlestep;
    }

    public void setCirclestep(int circlestep) {
        this.circlestep = circlestep;
    }

    public int getCirclestepsize() {
        return circlestepsize;
    }

    public void setCirclestepsize(int circlestepsize) {
        this.circlestepsize = circlestepsize;
    }

    public int getCirclemaxsize() {
        return circlemaxsize;
    }

    public void setCirclemaxsize(int circlemaxsize) {
        this.circlemaxsize = circlemaxsize;
    }

    public String getCircleinputtype() {
        return circleinputtype;
    }

    public void setCircleinputtype(String circleinputtype) {
        this.circleinputtype = circleinputtype;
    }

    public String getCircleinputparamId() {
        return circleinputparamId;
    }

    public void setCircleinputparamId(String circleinputparamId) {
        this.circleinputparamId = circleinputparamId;
    }

    public String getCircleinputparam() {
        return circleinputparam;
    }

    public void setCircleinputparam(String circleinputparam) {
        this.circleinputparam = circleinputparam;
    }

    public String getCircleRegulation() {
        return circleRegulation;
    }

    public void setCircleRegulation(String circleRegulation) {
        this.circleRegulation = circleRegulation;
    }

    public String getElementInputParamType() {
        return elementInputParamType;
    }

    public void setElementInputParamType(String elementInputParamType) {
        this.elementInputParamType = elementInputParamType;
    }

    public String getActionInputParamType() {
        return actionInputParamType;
    }

    public void setActionInputParamType(String actionInputParamType) {
        this.actionInputParamType = actionInputParamType;
    }

    public String getActionInputParam() {
        return actionInputParam;
    }

    public void setActionInputParam(String actionInputParam) {
        this.actionInputParam = actionInputParam;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getBlockType() {
        return blockType;
    }

    public void setBlockType(int blockType) {
        this.blockType = blockType;
    }

    public String getBlockValue() {
        return blockValue;
    }

    public void setBlockValue(String blockValue) {
        this.blockValue = blockValue;
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
