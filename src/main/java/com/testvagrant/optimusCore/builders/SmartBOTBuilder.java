package com.testvagrant.optimusCore.builders;

import com.testvagrant.optimusCore.models.SmartBOT;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import org.openqa.selenium.remote.DesiredCapabilities;

public class SmartBOTBuilder {

    SmartBOT smartBOT = new SmartBOT();

    public SmartBOT build(){
        return smartBOT;
    }

    public SmartBOTBuilder withBelongsTo(String deviceOwner) {
        smartBOT.setBelongsTo(deviceOwner);
        return this;
    }


    public SmartBOTBuilder withRunsOn(String runsOn) {
        smartBOT.setRunsOn(runsOn);
        return this;
    }


    public SmartBOTBuilder withCapabilities(DesiredCapabilities capabilities) {
        smartBOT.setCapabilities(capabilities);
        return this;
    }


    public SmartBOTBuilder withDeviceUdid(String udid) {
        smartBOT.setDeviceUdid(udid);
        return this;
    }


    public SmartBOTBuilder withDeviceId(String id) {
        smartBOT.setDeviceId(id);
        return this;
    }


    public SmartBOTBuilder withAppiumServiceUrl(String url) {
        smartBOT.setAppiumServiceUrl(url);
        return this;
    }

    public SmartBOTBuilder withAppPackageName(String appPackage) {
        smartBOT.setAppPackageName(appPackage);
        return this;
    }
}
