package com.transing.mcss4dpm.JobEvent;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.jeeframework.jeetask.startup.JeeTaskServer;
import com.transing.mcss4dpm.util.CallRemoteServiceUtil;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class McssJeeTaskServer extends JeeTaskServer {

    public McssJeeTaskServer(DataSource taskDataSource) throws IOException {
        super(taskDataSource);
    }

    public McssJeeTaskServer() throws IOException {
        super();
    }


    @Override
    protected int getMaxAllowedWorkerCount() {
        //TODO 只需本地mcss调用
        System.getProperty("mcss_url");
        String baseServer = System.getProperty("mcss_url");
        String serviceName = System.getProperty("mcss_service_name");
        if (serviceName == null) {
            return 0;
        }
        if (serviceName.startsWith("/")) {
            serviceName = serviceName.substring(1);
        }
        String getDataUrl = "/getDevicesList.json?registServer=" + serviceName + "&funcation=taskCrawl";
        Map<String, String> dataMap = new HashMap<String, String>();
        Object firstObject = CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), baseServer + getDataUrl, "get", dataMap);
        if (null != firstObject) {
            try {
                net.sf.json.JSONObject jsonObject = (net.sf.json.JSONObject) firstObject;
                String devicesArray = jsonObject.getString("data");
                JSONArray jsonDataStorageFieldArray = JSON.parseArray(devicesArray);
                System.out.println("XXXX: devices_nu : " + jsonDataStorageFieldArray.size());
                if (jsonDataStorageFieldArray.size() > 0) {
                    return jsonDataStorageFieldArray.size();
                }
            } catch (Exception e) {
                return 0;
            }
        }
        return 0;
    }
}
