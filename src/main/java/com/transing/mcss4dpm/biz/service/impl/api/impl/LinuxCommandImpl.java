package com.transing.mcss4dpm.biz.service.impl.api.impl;

import com.transing.mcss4dpm.biz.service.impl.api.ShellProcess;

import java.util.Scanner;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/12
 */
public interface LinuxCommandImpl {
    public ShellProcess executeShell(String shell, boolean isWait);

    public Scanner getShellResultContent(Process process);
}
