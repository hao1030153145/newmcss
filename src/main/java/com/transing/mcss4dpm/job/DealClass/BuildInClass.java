package com.transing.mcss4dpm.job.DealClass;

import com.transing.mcss4dpm.util.DateUtil;
import io.appium.java_client.android.AndroidDriver;

import java.util.Date;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/3/28
 */
public class BuildInClass {

    public String getCrawlTime(){
        return DateUtil.formatDate(new Date());
    }

    public String getUrl(AndroidDriver androidDriver){
        return androidDriver.getCurrentUrl();
    }
}
