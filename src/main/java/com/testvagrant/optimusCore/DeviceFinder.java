package com.testvagrant.optimusCore;


import com.testvagrant.optimusCore.entities.DeviceDetails;
import com.testvagrant.optimusCore.exceptions.DeviceEngagedException;
import com.testvagrant.optimusCore.requests.Device;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.List;

public class DeviceFinder {
    public Device getAvailableDeviceAndUpdateToEngaged(JSONObject testFeed) throws DeviceEngagedException {
        List<DeviceDetails> deviceDetailsList = new DevicesServiceImpl().getAllDevices();

        System.out.println("-------- All devices and there status --------");

        for (DeviceDetails deviceDetails : deviceDetailsList) {
            System.out.println(deviceDetails.getUdid() + " --- " + deviceDetails.getStatus());
        }
        org.json.simple.JSONObject jsonObject = null;
        try {
            jsonObject = (org.json.simple.JSONObject) new JSONParser().parse(testFeed.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
//        DeviceDetails deviceDetails = new DeviceMiner(deviceOpDetailsList, testFeed).getAvailableDevice();

        Device deviceDetails = null;

        synchronized (this) {
            if (isUDIDAvailable(testFeed)) {
                String udid = getUDID(testFeed);
                System.out.println("Finding device in DB with udid - " + udid);
                deviceDetails = new DevicesServiceImpl().updateFirstAvailableDeviceToEngaged(udid);
            } else {
                deviceDetails = new DevicesServiceImpl().updateFirstAvailableDeviceToEngaged(jsonObject);
            }
        }
        return deviceDetails;
    }


    public boolean isUDIDAvailable(JSONObject testFeed) {
        try {
            JSONObject appiumServerCapabilities = (JSONObject) ((JSONObject) testFeed.get("optimusDesiredCapabilities")).get("appiumServerCapabilities");
            String udid = appiumServerCapabilities.getString("udid");
            return udid == null;
        } catch (Exception e) {

        }
        return false;

    }

    public String getUDID(JSONObject testFeed) {
        JSONObject appiumServerCapabilities = (JSONObject) ((JSONObject) testFeed.get("optimusDesiredCapabilities")).get("appiumServerCapabilities");
        String udid = appiumServerCapabilities.getString("udid");
        return udid;
    }



}
