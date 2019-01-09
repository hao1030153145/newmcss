package com.transing.mcss4dpm.biz.service.impl.api;

import com.transing.mcss4dpm.biz.service.impl.api.impl.LinuxCommandImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2019/1/7
 */
public class DevicesManager implements LinuxCommandImpl {

    // 通过命令模式 adb devices 获得连接的设备ip
    public List<String> getAndroidDevices() {
        List<String> devicesNameList = new ArrayList<>();
        ShellProcess shellProcess = executeShell("adb devices");
        if (shellProcess.isSuccessful()) {
            Scanner in = getShellResultContent(shellProcess.getProcess());
            if (in.hasNext()) {
                while (in.hasNext()) {
                    String processInf = in.nextLine();
                    if (processInf.contains(":")) {
                        String ip = processInf.trim().split(":")[0];
                        ip = ip + ":5555";
                        System.out.println("通过adb devices命令获得ip为：" + ip);
                        devicesNameList.add(ip);
                    }
                }
            }
        }
        return devicesNameList;
    }

    // 通过命令模式 adb devices 获得连接的设备ip
    public void improtContacts(List<String> devicesName, String path) {

        System.out.println("进入通讯录导入方法, 通讯录文件路径为：" + path);
        for (String str : devicesName) {
            try {
                ShellProcess shellProcess1 = executeShell("adb -s " + str + " shell pm clear com.android.providers.contacts");
                ShellProcess shellProcess2 = executeShell("adb -s " + str + "  push " + path + " /sdcard/contacts.vcf");
                ShellProcess shellProcess3 = executeShell("adb -s " + str + "  shell am start -t \"text/x-vcard\" -d \"file:///sdcard/contacts.vcf\" -a android.intent.action.VIEW com.android.contacts");
                if (shellProcess3.isSuccessful()) {
                    Scanner in = getShellResultContent(shellProcess3.getProcess());
                    System.out.println(in);
                }
            } catch (Exception e) {
                System.out.println("下面是导入通讯录报的错误：" + e);
            }

        }


    }

    public ShellProcess executeShell(String shell) {
        return executeShell(shell, true);
    }


    @Override
    public ShellProcess executeShell(String shell, boolean isWait) {
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

    /**
     * 简单描述：获取执行shell后打印结果
     */
    @Override
    public Scanner getShellResultContent(Process process) {
        return new Scanner(process.getInputStream());
    }

}
