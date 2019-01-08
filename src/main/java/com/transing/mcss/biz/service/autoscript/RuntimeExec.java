package com.transing.mcss.biz.service.autoscript;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RuntimeExec {
    public StreamWrapper getStreamWrapper(InputStream is, String type){
        return new StreamWrapper(is, type);
    }
    private class StreamWrapper extends Thread {
        InputStream is = null;
        String type = null;
        String message = null;

        StreamWrapper(InputStream is, String type) {
            this.is = is;
            this.type = type;
        }

        public void run() {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuffer buffer = new StringBuffer();
                String line = null;
                while ( (line = br.readLine()) != null) {
                    buffer.append(line);//.append("\n");
                }
                message = buffer.toString();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
    // this is where the action is
    public static void startAppium(String comand) {
        Runtime rt = Runtime.getRuntime();
        RuntimeExec rte = new RuntimeExec();
        StreamWrapper error, output;

        try {
            Process proc = rt.exec(comand);
            error = rte.getStreamWrapper(proc.getErrorStream(), "ERROR");
            output = rte.getStreamWrapper(proc.getInputStream(), "OUTPUT");
            int exitVal = 0;

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String s;
            while((s = stdInput.readLine()) != null){
                System.out.println(s);
                if(s.contains("Appium REST http")){
                    System.out.println("STARTED!");
                }
            }

            error.start();
            output.start();
            error.join(3000);
            output.join(3000);
            exitVal = proc.waitFor();
            System.out.println("Output: "+output.message+"\nError: "+error.message);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
