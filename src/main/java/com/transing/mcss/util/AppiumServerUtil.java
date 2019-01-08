package com.transing.mcss.util;

import java.io.IOException;

public class AppiumServerUtil {

    public static void KillAll() {
        KillTask("node.exe");
    }

    public static void KillTask(String taskname) {
        String Command = "taskkill /F /im " + taskname;
        runCommand(Command);
    }

    public void startAppium() {
        String Command = "echo ";
        Command = "cmd /c start D:\\showdevices.bat";
        runCommand(Command);
    }

    private static void runCommand(String command){
        try {
            Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
