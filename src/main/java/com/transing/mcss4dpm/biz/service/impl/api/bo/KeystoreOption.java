package com.transing.mcss4dpm.biz.service.impl.api.bo;

/**
 * ${appium setting中签名相关设置}
 *
 * @author haolen
 * @version 1.0 2018/1/8
 */
public class KeystoreOption {
    private Boolean useKeystore; //使用一个自定义的 keystore 来对 apk 进行重签名。默认值 `false`
    private String keystorePath;    //自定义 keystore 的路径
    private String keystorePassword;    //自定义 keystore 的密码
    private String keyAlias;    //key 的别名
    private String keyPassword; //key 的密码

    public Boolean getUseKeystore() {
        return useKeystore;
    }

    public void setUseKeystore(Boolean useKeystore) {
        this.useKeystore = useKeystore;
    }

    public String getKeystorePath() {
        return keystorePath;
    }

    public void setKeystorePath(String keystorePath) {
        this.keystorePath = keystorePath;
    }

    public String getKeystorePassword() {
        return keystorePassword;
    }

    public void setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public void setKeyPassword(String keyPassword) {
        this.keyPassword = keyPassword;
    }
}
