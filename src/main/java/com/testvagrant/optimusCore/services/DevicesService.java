package com.testvagrant.optimusCore.services;

import com.testvagrant.optimusCore.entities.DeviceDetails;
import com.testvagrant.optimusCore.exceptions.DeviceEngagedException;
import com.testvagrant.optimusCore.exceptions.DeviceReleaseException;
import com.testvagrant.optimusCore.requests.Device;
import org.json.simple.JSONObject;

import java.util.List;

public interface DevicesService {
    DeviceDetails getDeviceByUdid(String udid);

    List<DeviceDetails> getAllDevices();

    void insertDeviceList(List<DeviceDetails> deviceDetailsList);

    Device updateFirstAvailableDeviceToEngaged(JSONObject testFeed) throws DeviceEngagedException;

    Device updateFirstAvailableDeviceToEngaged(String udid) throws DeviceEngagedException;

    void updateDeviceScreenshot(String udid, byte[] screenshot);

    void updateStatusToAvailableForDevice(String udid) throws DeviceReleaseException;

}
