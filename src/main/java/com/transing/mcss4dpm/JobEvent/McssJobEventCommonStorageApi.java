package com.transing.mcss4dpm.JobEvent;

import com.jeeframework.jeetask.event.api.impl.JobEvenApiProcessor;
import com.jeeframework.jeetask.event.type.JobExecutionEvent;
import com.transing.mcss4dpm.JobEvent.Bo.McssTask;
import com.transing.mcss4dpm.util.CallRemoteServiceUtil;

import java.util.HashMap;
import java.util.Map;

public class McssJobEventCommonStorageApi extends JobEvenApiProcessor {
    @Override
    protected boolean updateJobExecutionEventWhenFinished(JobExecutionEvent jobExecutionEvent) {
        super.updateJobExecutionEventWhenFinished(jobExecutionEvent);
        System.out.println("XXXX: location_mcss : finish");
        return true;
    }

    @Override
    protected boolean updateJobExecutionEventFailure(JobExecutionEvent jobExecutionEvent) {
        super.updateJobExecutionEventFailure(jobExecutionEvent);
        System.out.println("XXXX: location_mcss : failure"+jobExecutionEvent.getFailureCause());
        //更新任务,子任务状态
        McssTask task = (McssTask) jobExecutionEvent.getTask();
        String subTaskId = task.getSubTaskId();
        //完成操作,更新任务,子任务状态
        String baseServer = System.getProperty("mcss_url");
        String getDataUrl = "/crawlTask/updataCopleteNu.json" + "?subTaskId=" + subTaskId + "&status=9";
        Map<String, String> dataMap = new HashMap<String, String>();
        Object firstObject = CallRemoteServiceUtil.callRemoteService(this.getClass().getName(), baseServer + getDataUrl, "get", dataMap);
        return true;
    }
}
