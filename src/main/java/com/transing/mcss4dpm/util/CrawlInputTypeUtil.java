package com.transing.mcss4dpm.util;

import com.jeeframework.logicframework.util.logging.LoggerUtil;
import com.jeeframework.util.httpclient.HttpClientHelper;
import com.jeeframework.util.httpclient.HttpResponse;
import com.jeeframework.util.json.JSONUtils;
import com.jeeframework.util.validate.Validate;
import com.transing.mcss4dpm.integration.bo.SubTaskParam;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 包: com.transing.crawl.util.task
 * 源文件:CrawlInputTypeUtil.java
 * 输入参数输入值
 *
 * @author Allen  Copyright 2016 成都创行, Inc. All rights reserved.2017年08月29日
 */
public class CrawlInputTypeUtil {
    private final String interfaceString = "/dataCrawl/getUploadFile.json";
    private final String dpmbsdir = "dpm_file";

    public void InputParamSplit(com.alibaba.fastjson.JSONArray inputArray, List<List<SubTaskParam>> subTaskParamList) throws Exception {
        for (int i = 0; i < inputArray.size(); i++) {
            List<SubTaskParam> subTaskParams = new ArrayList<>();
            com.alibaba.fastjson.JSONObject job;
            try {
                job = inputArray.getJSONObject(i);
            }catch (Exception e){
                job = inputArray.getJSONArray(i).getJSONObject(0);
            }
            String styleCode = job.getString("styleCode");
            String paramCnName = job.getString("paramCnName");
            String paramEnName = job.getString("paramEnName");
            String paramValue = null;
            if (styleCode.equalsIgnoreCase("input") || styleCode.equalsIgnoreCase("datetime")) {
                // 文本输入 、时间控件
                if (job.getString("paramValue") != null){
                    paramValue = job.getString("paramValue");
                }else {
                    paramValue = "";
                }
            } else if (styleCode.equalsIgnoreCase("input-file")) {
                // 文件上传组合控件
                String paramValues = job.getString("paramValue");
                if (paramValues != null) {
                    if (JSONUtils.isJSONValid(paramValues)) {
                        JSONObject paramValueJsonObject = JSONObject.fromObject(paramValues);
                        paramValue = paramValueJsonObject.containsKey("value") ?
                                paramValueJsonObject.getString("value") :
                                "";
                        String filePath = paramValueJsonObject.containsKey("file") ?
                                paramValueJsonObject.getString("file") :
                                "";
                        if (!Validate.isEmpty(filePath)) {
                            String fileName = filePath
                                    .substring(filePath.lastIndexOf("/") + 1);
                            Map<String, String> mapParams = new HashMap<String, String>();
                            mapParams.put("url", filePath);
                            File file = downLoadDpmbsServerFile(interfaceString,
                                    dpmbsdir, fileName, mapParams);
                            List list = ExcelUtil
                                    .readExcelInfos(file);
                            if (list != null) {
                                StringBuffer sbuffer = new StringBuffer("");
                                for (Object object : list) {
                                    sbuffer.append("," + object.toString());
                                }
                                if (!Validate.isEmpty(paramValue)) {
                                    sbuffer.append(paramValue);
                                    paramValue += sbuffer.toString();
                                } else {
                                    paramValue = sbuffer.substring(1);
                                }
                            }

                        }
                    }
                }else {
                    paramValue = "";
                }
            }

            String[] subParam = paramValue.split(",");
            for (String aSubParam : subParam) {
                SubTaskParam subTaskParam = new SubTaskParam();
                subTaskParam.setParamCnName(paramCnName);
                subTaskParam.setParamEnName(paramEnName);
                subTaskParam.setSubParam(aSubParam);
                subTaskParams.add(subTaskParam);
            }
            subTaskParamList.add(subTaskParams);
        }
    }

    /**
     * 下载dpmbs的Excel
     *
     * @param url
     * @return
     */
    private File downLoadDpmbsServerFile(String url, String dirFile, String fileName, Map<String, String> param) {
        try {
            String dpmbsURI = WebUtil.getDpmServerByEnv();//dpm访问地址
            HttpClientHelper httpClientHelper = new HttpClientHelper();
            LoggerUtil.debugTrace("文件下载路径+" + dpmbsURI + url);
            HttpResponse httpResponse = httpClientHelper
                    .doPostAndRetBytes(dpmbsURI + url, param, "utf-8", "utf-8",
                            null, null);
            byte[] bytes = httpResponse.getContentBytes();

            File fileDir = new File(dpmbsdir);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            File file = new File(dirFile + "/" + fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
            fileOutputStream.close();
            return file;
        } catch (Exception e) {
            LoggerUtil.errorTrace(getClass().getName(), "下载文件：" + e.getMessage(), e);
            return null;
        }
    }
}
