package com.transing.mcss4dpm.biz.service.impl.api;

/**
 * ${执行shell命令返回对象}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public class ShellProcess {
    private Process process;
    private boolean isSuccessful=false;

    public Process getProcess() {
        return process;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public void setSuccessful(boolean successful) {
        isSuccessful = successful;
    }
}
