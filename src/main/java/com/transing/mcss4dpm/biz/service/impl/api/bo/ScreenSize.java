package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${description}
 *
 * @author haolen
 * @version 1.0 2018/1/27
 */
public class ScreenSize {
    private int height;
    private int width;

    public ScreenSize() {
    }

    public ScreenSize(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
