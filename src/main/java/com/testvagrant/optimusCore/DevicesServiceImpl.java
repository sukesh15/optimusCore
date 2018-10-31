package com.testvagrant.optimusCore;

import com.testvagrant.optimusCore.clients.DevicesClient;
import com.testvagrant.optimusCore.entities.DeviceDetails;
import com.testvagrant.optimusCore.exceptions.DeviceEngagedException;
import com.testvagrant.optimusCore.exceptions.DeviceReleaseException;
import com.testvagrant.optimusCore.reql.DeviceMatcherFunction;
import com.testvagrant.optimusCore.requests.Device;
import com.testvagrant.optimusCore.services.DevicesService;
import com.testvagrant.optimusCore.utils.DeviceToDeviceDetailsMapper;
import org.json.simple.JSONObject;

import java.util.List;

import static com.testvagrant.optimusCore.utils.DeviceToDeviceDetailsMapper.getDevicesFromDeviceDetails;

public class DevicesServiceImpl extends OptimusServiceImpl implements DevicesService {
    @Override
    public DeviceDetails getDeviceByUdid(String udid) {
        Device deviceByUdid = new DevicesClient().getDeviceByUdid(getLatestBuild(), udid);
        return DeviceToDeviceDetailsMapper.getDeviceDetails(deviceByUdid);
    }

    @Override
    public List<DeviceDetails> getAllDevices() {
        return new DevicesClient().getAllDevices(getLatestBuild());
    }

    @Override
    public void insertDeviceList(List<DeviceDetails> deviceDetailsList) {
        List<Device> devicesFromDeviceDetails = getDevicesFromDeviceDetails(getLatestBuild(), deviceDetailsList);
        new DevicesClient().storeDevices(devicesFromDeviceDetails);
    }

    @Override
    public Device updateFirstAvailableDeviceToEngaged(JSONObject testFeed) throws DeviceEngagedException {
        Device matchingDevice = new DeviceMatcherFunction().getDeviceQuery(testFeed);
        System.out.println("Matching Device is " + matchingDevice.toString());
        return new DevicesClient().getDevice(getLatestBuild(), matchingDevice);
    }


    @Override
    public Device updateFirstAvailableDeviceToEngaged(String udid) throws DeviceEngagedException {
        return new DevicesClient().getDeviceByUdid(getLatestBuild(), udid);
    }

    @Override
    public void updateDeviceScreenshot(String udid, byte[] screenshot) {
        Device deviceToUpdate = new DevicesClient().getDeviceByUdid(getLatestBuild(), udid);
        deviceToUpdate.setScreenshot(screenshot);
        new DevicesClient().storeScreenShot(getLatestBuild(), deviceToUpdate);
    }

    @Override
    public void updateStatusToAvailableForDevice(String udid) throws DeviceReleaseException {
        Device deviceToUpdate = new DevicesClient().getDeviceByUdid(getLatestBuild(), udid);
        deviceToUpdate.setStatus("Available");
        new DevicesClient().releaseDevice(deviceToUpdate);
    }
}
