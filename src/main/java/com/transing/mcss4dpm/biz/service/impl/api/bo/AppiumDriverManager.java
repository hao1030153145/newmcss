package com.transing.mcss4dpm.biz.service.impl.api.bo;

import com.transing.mcss4dpm.biz.service.impl.api.ShellProcess;
import io.appium.java_client.android.AndroidDriver;

import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/24
 */
public class AppiumDriverManager {
    private TimerTask timeout;
    private Timer timer;
    private AndroidDriver androidDriver;
    private String status; //0:被初始化未绑定service 1:启动了services 未启动client 2:启动了services 启动client 3:启动了services 启动client 处于运行等待状态 8:占用中 9:创建失败,10:被销毁
    private String servicePid;
    private DevicesInfoBo deviceInfo;
    private String bindApp;
    private String session;
    private long delay;
    private int port;
    private int bp;
    private int cp;

    class TimeOutTask extends TimerTask {
        @Override
        public void run() {
            //释放设备
            System.out.println("执行任务定时释放任务!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! >>>>>>>>");
            long deviceId = deviceInfo.getId();
            status = "0";
        }
    }

    public AndroidDriver getAndroidDriver() {
        return androidDriver;
    }

    public void setAndroidDriver(AndroidDriver androidDriver) {
        this.androidDriver = androidDriver;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getServicePid() {
        return servicePid;
    }

    public void setServicePid(String servicePid) {
        this.servicePid = servicePid;
    }

    public DevicesInfoBo getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(DevicesInfoBo deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getBindApp() {
        return bindApp;
    }

    public void setBindApp(String bindApp) {
        this.bindApp = bindApp;
    }

    public String getSession() {
        return session;
    }

    public void setSession(String session) {
        this.session = session;
    }

    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        System.out.println("超时时间    >>>>>>>>>>   " + delay);
        this.delay = delay;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBp() {
        return bp;
    }

    public void setBp(int bp) {
        this.bp = bp;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    //开启定时器
    public void timeInit() {
        if (timeout != null) {
            timeout.cancel();//取消当前的timeout任务
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timeout = new TimeOutTask();
        timer.schedule(timeout, delay);
    }

    //重置定时器
    public void invoke() {
        if (timeout != null) {
            boolean is =timeout.cancel();//取消当前的timeout任务
            timeout = null;
            System.out.println("定时器重置成功 >>>>>>>>" + is);
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        timer = new Timer();
        timeout = new TimeOutTask();
        timer.schedule(timeout, delay);//重新开始计时
    }

    //释放定时器
    public void cancleTimer() {
        if (timeout != null) {
            boolean is =timeout.cancel();//取消当前的timeout任务
            timeout = null;
            System.out.println("定时器释放成功 >>>>>>>>" + is);
        }
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public static ShellProcess executeShell(String shell, boolean isWait) {
        ShellProcess shellProcess = new ShellProcess();
        try {
            Process process = Runtime.getRuntime().exec(shell);
            if (isWait) {
                process.waitFor();
                shellProcess.setProcess(process);
                shellProcess.setSuccessful(true);
            } else {
                Thread.sleep(6 * 1000);
                shellProcess.setProcess(process);
                shellProcess.setSuccessful(true);
            }
        } catch (IOException | InterruptedException e) {
            shellProcess.setProcess(null);
            shellProcess.setSuccessful(false);
            e.printStackTrace();
        }
        return shellProcess;
    }

    public static Scanner getShellResultContent(Process process) {
        return new Scanner(process.getInputStream());
    }

    @Override
    public String toString() {
        return "AppiumDriverManager{" +
                "timeout=" + timeout +
                ", timer=" + timer +
                ", status='" + status + '\'' +
                ", servicePid='" + servicePid + '\'' +
                ", deviceInfo=" + deviceInfo +
                ", bindApp='" + bindApp + '\'' +
                ", session='" + session + '\'' +
                ", delay=" + delay +
                ", port=" + port +
                ", bp=" + bp +
                ", cp=" + cp +
                '}';
    }
}
