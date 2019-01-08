package com.transing.mcss4dpm.biz.service.impl.api;

import com.transing.mcss4dpm.biz.service.impl.api.bo.AndroidDriverStatus;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2017/12/5
 */
public class DriverWaitRunable implements Runnable {
    private AndroidDriverStatus androidDriverStatus;

    public DriverWaitRunable(AndroidDriverStatus androidDriverStatus) {
        this.androidDriverStatus = androidDriverStatus;
    }

    @Override
    public void run() {
        while (true) {
            String status = androidDriverStatus.getStatus();
            try {
                Thread.currentThread().sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (status.equals("0")) {
                System.out.println("XXXX: location_mcss : While is click");
                androidDriverStatus.getAndroidDriver().findElementById("com.tencent.mm:id/a27").click();
            }
        }
    }
}
