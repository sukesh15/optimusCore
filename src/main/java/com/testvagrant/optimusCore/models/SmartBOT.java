package com.testvagrant.optimusCore.models;

import org.openqa.selenium.remote.DesiredCapabilities;

public class SmartBOT {

    private String belongsTo;
    private DesiredCapabilities capabilities;
    private String deviceUdid;
    private String runsOn;
    private String appPackageName;
    private String deviceId;
    private String appiumServiceUrl;

    public String getAppiumServiceUrl() {
        return appiumServiceUrl;
    }

    public void setAppiumServiceUrl(String appiumServiceUrl) {
        this.appiumServiceUrl = appiumServiceUrl;
    }

    public String getBelongsTo() {
        return belongsTo;
    }

    public void setBelongsTo(String belongsTo) {
        this.belongsTo = belongsTo;
    }

    public DesiredCapabilities getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(DesiredCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    public String getDeviceUdid() {
        return deviceUdid;
    }

    public void setDeviceUdid(String deviceUdid) {
        this.deviceUdid = deviceUdid;
    }

    public String getRunsOn() {
        return runsOn;
    }

    public void setRunsOn(String runsOn) {
        this.runsOn = runsOn;
    }

    public String getAppPackageName() {
        return appPackageName;
    }

    public void setAppPackageName(String appPackageName) {
        this.appPackageName = appPackageName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
