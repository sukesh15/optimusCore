package com.testvagrant.optimusCore.entities;

public class DeviceDetails {
    private String deviceName;
    private Platform platform;
    private String platformVersion;
    private DeviceType runsOn;
    private Status status;
    private String udid;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Platform getPlatform() {
        return platform;
    }

    public void setPlatform(Platform platform) {
        this.platform = platform;
    }

    public String getPlatformVersion() {
        return platformVersion;
    }

    public void setPlatformVersion(String platformVersion) {
        this.platformVersion = platformVersion;
    }

    public DeviceType getRunsOn() {
        return runsOn;
    }

    public void setRunsOn(DeviceType runsOn) {
        this.runsOn = runsOn;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    @Override
    public String toString() {
        return "DeviceDetails{" +
                "deviceName='" + deviceName + '\'' +
                ", udid='" + udid + '\'' +
                ", status='" + status + '\'' +
                ", platform=" + platform +
                ", platformVersion='" + platformVersion + '\'' +
                ", runsOn=" + runsOn.name() +
                '}';
    }

}


