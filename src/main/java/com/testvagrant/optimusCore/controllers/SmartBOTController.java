package com.testvagrant.optimusCore.controllers;

import com.testvagrant.optimusCore.*;
import com.testvagrant.optimusCore.builders.SmartBOTBuilder;
import com.testvagrant.optimusCore.entities.DeviceDetails;
import com.testvagrant.optimusCore.exceptions.DeviceEngagedException;
import com.testvagrant.optimusCore.exceptions.DeviceMatchingException;
import com.testvagrant.optimusCore.models.SmartBOT;
import com.testvagrant.optimusCore.utils.AppiumServerManager;
import com.testvagrant.optimusCore.utils.OptimusConfigParser;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class SmartBOTController {


    private AppiumDriverLocalService appiumService;

    @RequestMapping("/smartBOT")
    public List<SmartBOT> smartBOT(@RequestParam(value="testfeed") String testFeed) throws DeviceEngagedException, DeviceMatchingException {

        OptimusConfigParser optimusConfigParser = new OptimusConfigParser(testFeed);
        List<SmartBOT> smartBOTs = new ArrayList();
        List<DeviceAllocation> deviceAllocations = optimusConfigParser.allocateDevicesForCurrentScenario();
        Iterator var3 = deviceAllocations.iterator();

        while(var3.hasNext()) {
            DeviceAllocation allocatedDevice = (DeviceAllocation)var3.next();
            String udid = allocatedDevice.getDevice().getUdid();

            AppiumServerManager appiumServerManager = new AppiumServerManager(optimusConfigParser);

                appiumService = appiumServerManager.startAppiumService("dummy", udid);


            String appPackage = (String)allocatedDevice.getCapabilities().getCapability("appPackage");
            SmartBOT bot = (new SmartBOTBuilder()).withBelongsTo(allocatedDevice.getOwner())
                    .withRunsOn(getRunsOnBasedOn(udid))
                    .withCapabilities(allocatedDevice.getCapabilities())
                    .withDeviceUdid(udid)
                    .withDeviceId(allocatedDevice.getDevice().getId())
                    .withAppiumServiceUrl(appiumService.getUrl().toString())
                    .withAppPackageName(appPackage).build();


//            (new BuildsServiceImpl()).updateBuildRunMode(System.getProperty("runMode"));
            smartBOTs.add(bot);
//            (new OnDevice(bot)).clearADBLogs();
//            logger.info(this.scenario + "---BOT registered successfully for -- " + udid);
        }

//        if (this.optimusConfigParser.isMonitoring() && this.optimusConfigParser.isForAndroid()) {
//            (new Radiator(smartBOTs)).notifyScenarioStart();
//            this.listener.setSmartBOTs(smartBOTs);
//            this.listener.start();
//        }


        return smartBOTs;

    }


    private String getRunsOnBasedOn(String udid) throws DeviceMatchingException {
        System.out.println("UDID: " + udid);
        DeviceDetails deviceByUdid = new DevicesServiceImpl().getDeviceByUdid(udid);
        System.out.println("DeviceName" + deviceByUdid.getDeviceName());
        return deviceByUdid.getRunsOn().name();

    }


}
