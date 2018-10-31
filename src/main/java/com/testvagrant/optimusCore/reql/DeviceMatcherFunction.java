package com.testvagrant.optimusCore.reql;

import com.testvagrant.optimusCore.requests.Device;
import com.testvagrant.optimusCore.utils.TestFeedDetails;
import com.testvagrant.optimusCore.utils.TestFeedParser;
import org.json.simple.JSONObject;

public class DeviceMatcherFunction {
    public Device getDeviceQuery(JSONObject testfeed) {
        Device device = new Device();
        TestFeedParser testFeedParser = new TestFeedParser(testfeed);
        TestFeedDetails testFeedDetails = testFeedParser.parse();
        device.setPlatform(testFeedDetails.getPlatform().toUpperCase());
        device.setUdid(testFeedDetails.getUdid());
        device.setPlatformVersion(testFeedDetails.getPlatformVersion());
        device.setRunsOn(testFeedDetails.getRunsOn().toUpperCase());
        device.setDeviceName(testFeedDetails.getDeviceName());
        return device;
    }

}
