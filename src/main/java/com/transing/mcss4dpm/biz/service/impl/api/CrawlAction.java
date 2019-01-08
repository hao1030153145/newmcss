package com.transing.mcss4dpm.biz.service.impl.api;

import com.alibaba.fastjson.JSON;
import com.transing.mcss4dpm.integration.bo.*;
import com.transing.mcss4dpm.job.DealClass.BuildInClass;
import com.transing.mcss4dpm.util.ParseUtil;
import io.appium.java_client.android.AndroidDriver;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/3/9
 */
public class CrawlAction {

    public String crawl(CrawlRegulationBO crawlRegulationBO, String content, List<SubTaskParam> subTaskParams, AndroidDriver androidDriver) {
        String crawlString = null;
        int datafrom = crawlRegulationBO.getDatafrom();
        String crawlParam = crawlRegulationBO.getCrawlParam();
        String crawlArray = crawlRegulationBO.getCrawlArray();
        String item = crawlRegulationBO.getItem();
        switch (datafrom) {
            //输入参数
            case 0:
                for (SubTaskParam subTaskParam : subTaskParams) {
                    if (subTaskParam.getParamEnName().equalsIgnoreCase(crawlParam)) {
                        crawlString = subTaskParam.getSubParam();
                        break;
                    }
                }
                System.out.println("输入参数  " + item + " >>>>>>>>>>   " + crawlString);
                break;
            //内置变量
            case 1:
                try {
                    BuildInClass buildInClass = new BuildInClass();
                    if (crawlParam.equalsIgnoreCase("crawlTime")) {
                        crawlString = buildInClass.getCrawlTime();
                    } else if (crawlParam.equalsIgnoreCase("getPageUrl")) {
                        crawlString = buildInClass.getUrl(androidDriver);
                    }
                    System.out.println("内置变量  " + item + " >>>>>>>>>>   " + crawlString);
                } catch (Exception e) {
                    System.out.println("内置变量 >>>>>>>>>>   " + e.getMessage());
                    e.printStackTrace();
                }
                break;
            //抓取参数
            case 2:
                try {
                    crawlString = getCrawlParam(crawlArray, content, item);
                } catch (Exception e) {
                    System.out.println("抓取参数异常 >>>>>>>>>>   " + e.getMessage());
                    e.printStackTrace();
                }

                if (crawlString != null && crawlString.length() > 35) {
                    System.out.println("抓取参数  " + item + " >>>>>>>>>>   " + crawlString.substring(0, 20));
                } else {
                    System.out.println("抓取参数  " + item + " >>>>>>>>>>   " + crawlString);
                }
                break;
        }

        //后置处理器
        String afterProcessorArray = crawlRegulationBO.getAfterProcessorArray();
        if (afterProcessorArray != null && !afterProcessorArray.equals("")) {
            AfterProcessorsBO afterProcessorBO = JSON.parseObject(afterProcessorArray, com.transing.mcss4dpm.integration.bo.AfterProcessorsBO.class);
            List<AfterProcessorArrayBO> afterProcessorArrayBOList = afterProcessorBO.getAfterProcessorArray();
            if (afterProcessorArrayBOList != null && afterProcessorArrayBOList.size() > 0) {
                for (AfterProcessorArrayBO afterProcessorArrayBO : afterProcessorArrayBOList) {
                    if (afterProcessorArrayBO.getAfterProcessorType() == null || afterProcessorArrayBO.getAfterProcessorType().equals("")) {
                        break;
                    }
                    try {
                        List<String> parseList = new ArrayList<>();
                        StringBuffer stringBuffer = new StringBuffer();
                        String crawlType = afterProcessorArrayBO.getAfterProcessorType();
                        String crawlValue = afterProcessorArrayBO.getAfterProcessorValue();
                        content = crawlString;
                        parseList = getCrawlParam(crawlType, crawlValue, content, item);
                        if (parseList == null) {
                            continue;
                        }
                        for (int i = 0; i < parseList.size(); i++) {
                            if (parseList.size() - 1 == i) {
                                stringBuffer.append(parseList.get(i));
                            } else {
                                stringBuffer.append(parseList.get(i) + ";");
                            }
                        }
                        if (stringBuffer.toString().length() > 0) {
                            crawlString = stringBuffer.toString();
                            break;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return crawlString;
    }

    public String beforeProcessorAction(String beforeprocessorArray, String content) {
        BeforeProcessorsBO beforeProcessorsBO = JSON.parseObject(beforeprocessorArray, com.transing.mcss4dpm.integration.bo.BeforeProcessorsBO.class);
        List<BeforeProcessorArrayBO> beforeProcessorArrayBOList = beforeProcessorsBO.getBeforeProcessorArrayBOs();
        if (beforeProcessorArrayBOList != null && beforeProcessorArrayBOList.size() > 0) {
            for (BeforeProcessorArrayBO beforeProcessorArrayBO : beforeProcessorArrayBOList) {
                if (beforeProcessorArrayBO.getBeforeProcessorType() == null || beforeProcessorArrayBO.getBeforeProcessorValue().equals("")) {
                    break;
                }
                List<String> parseList = new ArrayList<>();
                StringBuffer stringBuffer = new StringBuffer();
                String crawlType = beforeProcessorArrayBO.getBeforeProcessorType();
                String crawlValue = beforeProcessorArrayBO.getBeforeProcessorValue();
                parseList = getCrawlParam(crawlType, crawlValue, content, null);
                if (parseList == null) {
                    continue;
                }
                for (int i = 0; i < parseList.size(); i++) {
                    if (parseList.size() - 1 == i) {
                        stringBuffer.append(parseList.get(i));
                    } else {
                        stringBuffer.append(parseList.get(i) + ";");
                    }
                }
                if (stringBuffer.toString().length() > 0) {
                    content = stringBuffer.toString();
                    break;
                }
            }
        }
        return content;
    }

    public String afterProcessorAction(String afterProcessorBO, String content) {
        AfterProcessorsBO afterProcessorsBO = JSON.parseObject(afterProcessorBO, com.transing.mcss4dpm.integration.bo.AfterProcessorsBO.class);
        List<AfterProcessorArrayBO> afterProcessorArrayBOList = afterProcessorsBO.getAfterProcessorArray();
        if (afterProcessorArrayBOList != null && afterProcessorArrayBOList.size() > 0) {
            for (AfterProcessorArrayBO afterProcessorArrayBO : afterProcessorArrayBOList) {
                if (afterProcessorArrayBO.getAfterProcessorType() == null || afterProcessorArrayBO.getAfterProcessorValue().equals("")) {
                    break;
                }
                List<String> parseList;
                StringBuffer stringBuffer = new StringBuffer();
                String crawlType = afterProcessorArrayBO.getAfterProcessorType();
                String crawlValue = afterProcessorArrayBO.getAfterProcessorValue();
                parseList = getCrawlParam(crawlType, crawlValue, content, null);
                if (parseList == null) {
                    return "";
                }
                for (int i = 0; i < parseList.size(); i++) {
                    if (parseList.size() - 1 == i) {
                        stringBuffer.append(parseList.get(i));
                    } else {
                        stringBuffer.append(parseList.get(i) + ";");
                    }
                }
                if (stringBuffer.toString().length() > 0) {
                    content = stringBuffer.toString();
                    break;
                }
            }
        }
        return content;
    }

    public List<String> listCrawl(String cutwayArray, String content) {
        List<String> parseList = new ArrayList<>();
        CrawlArrayRootBO crawlArrayRootBO = JSON.parseObject(cutwayArray, com.transing.mcss4dpm.integration.bo.CrawlArrayRootBO.class);
        List<CrawlArrayBO> crawlArrayBOList = crawlArrayRootBO.getCrawlArray();
        if (crawlArrayBOList != null && crawlArrayBOList.size() > 0) {
            for (CrawlArrayBO crawlArrayBO : crawlArrayBOList) {
                if (crawlArrayBO.getCrawlType() == null || crawlArrayBO.getCrawlValue().equals("")) {
                    break;
                }
                String crawlType = crawlArrayBO.getCrawlType();
                String crawlValue = crawlArrayBO.getCrawlValue();
                parseList = getCrawlParam(crawlType, crawlValue, content, null);
                if (parseList != null && parseList.size() > 0) {
                    break;
                }
            }
        }
        return parseList;
    }

    private List getCrawlParam(String crawlType, String crawlValue, String content, String item) {
        List parseList = new ArrayList<>();
        try {
            if (crawlType != null && !crawlType.equals("")) {
                if (crawlType.equalsIgnoreCase("xpath")) {
                    parseList = ParseUtil.parseValue(content, crawlValue, "xpath", null);
                } else if (crawlType.equalsIgnoreCase("regex")|| ("正则表达式").equals(crawlType)) {
                    parseList = ParseUtil.parseValue(content, crawlValue, "regex", null);
                } else if (crawlType.equalsIgnoreCase("JS")) {
                    try {
                        //if (content != null && !content.equals("")) {
                            if (content.startsWith("[") && content.endsWith("]")) {
                                content = content.substring(1, content.length() - 2);
                            }
                            if (content.contains("\r\n")) {
                                content = content.replace("\r\n", "");
                            }
                            content = content.replaceAll("([\\s\\t]*)", "");
                            if (crawlValue != null && item != null) {
                                if (crawlValue.contains("@" + item + "@")) {
                                    crawlValue = crawlValue.replace("@" + item + "@", content);
                                }
                            }
                        //}
                        JSONObject fixedValues = JSONObject.fromObject("{}");
                        fixedValues.put("_URL_", "js");
                        parseList = ParseUtil.parseValue(content, crawlValue, "js", fixedValues);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseList;
    }

    private String getCrawlParam(String crawlArray, String content, String item) throws Exception {
        String crawlString = null;
        CrawlArrayRootBO crawlArrayRootBO = JSON.parseObject(crawlArray, CrawlArrayRootBO.class);
        if (crawlArrayRootBO.getCrawlArray() != null && crawlArrayRootBO.getCrawlArray().size() > 0) {
            for (CrawlArrayBO crawlArrayBO : crawlArrayRootBO.getCrawlArray()) {
                List<String> parseList = new ArrayList<>();
                StringBuffer stringBuffer = new StringBuffer();
                String crawlType = crawlArrayBO.getCrawlType();
                String crawlValue = crawlArrayBO.getCrawlValue();
                parseList = getCrawlParam(crawlType, crawlValue, content, item);
                if (parseList == null) {
                    continue;
                }
                for (int i = 0; i < parseList.size(); i++) {
                    if (parseList.size() - 1 == i) {
                        stringBuffer.append(parseList.get(i));
                    } else {
                        stringBuffer.append(parseList.get(i)).append(";");
                    }
                }
                if (stringBuffer.toString().length() > 0) {
                    crawlString = stringBuffer.toString();
                    break;
                }
            }
        }
        return crawlString;
    }

    private String linkCrawlString(List<String> parseList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < parseList.size(); i++) {
            if (parseList.size() - 1 == i) {
                stringBuffer.append(parseList.get(i));
            } else {
                stringBuffer.append(parseList.get(i) + ";");
            }
        }
        return stringBuffer.toString();
    }
}
