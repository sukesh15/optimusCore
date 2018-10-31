package com.testvagrant.optimusCore.utils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.testvagrant.optimusCore.builders.CapabilitiesBuilder;
import com.testvagrant.optimusCore.DeviceAllocation;
import com.testvagrant.optimusCore.DeviceFinder;
import com.testvagrant.optimusCore.entities.ExecutionDetails;
import com.testvagrant.optimusCore.exceptions.DeviceEngagedException;
import com.testvagrant.optimusCore.requests.Device;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.ArrayList;
import java.util.List;

public class OptimusConfigParser {

    String EXEC_DETAILS="executionDetails";
    String APPIUM_JS_PATH="appium_js_path";
    String APPIUM_NODE_PATH="appium_node_path";
    String TEST_FEED = "testFeed";
    String MONITORING = "monitoring";
    String BELONGS_TO = "belongsTo";
    String RUNS_ON = "runsOn";
    String OPTIMUS_DESIRED_CAPABILITIES = "optimusDesiredCapabilities";
    String APPIUM_SERVER_CAPABILITIES = "appiumServerCapabilities";
    String PLATFORM_NAME = "platformName";
    String APP = "app";

    JSONObject jsonObject;
    JsonObject gsonObject;


    public OptimusConfigParser(String appJson) {
        jsonObject = new JSONObject(appJson);
        gsonObject = getObjectFromJson(appJson, JsonObject.class);

    }

    public ExecutionDetails getExecutionDetails() {
        ExecutionDetails execDetails = getObjectFromJson(gsonObject.get(EXEC_DETAILS), ExecutionDetails.class);
        return execDetails;
    }

    public boolean isMonitoring() {
        return true;
    }

    private void updateTestFeed(JSONObject testFeed, String appName) {
        testFeed.getJSONObject("optimusDesiredCapabilities").getJSONObject("appiumServerCapabilities")
                .put("app", appName);
    }

    public boolean isForAndroid() {
        JSONArray testFeedArray = (JSONArray) jsonObject.get(TEST_FEED);
        for (int testFeedIterator = 0; testFeedIterator < testFeedArray.length(); testFeedIterator++) {
            JSONObject testFeedJSON = (JSONObject) testFeedArray.get(testFeedIterator);
            if (testFeedJSON.getJSONObject(OPTIMUS_DESIRED_CAPABILITIES).getJSONObject(APPIUM_SERVER_CAPABILITIES).getString(PLATFORM_NAME)
                    .equalsIgnoreCase("android")) {
                return true;
            }
        }
        return false;
    }

    public boolean isForIos() {
        JSONArray testFeedArray = (JSONArray) jsonObject.get(TEST_FEED);
        for (int testFeedIterator = 0; testFeedIterator < testFeedArray.length(); testFeedIterator++) {
            JSONObject testFeedJSON = (JSONObject) testFeedArray.get(testFeedIterator);
            if (testFeedJSON.getJSONObject(OPTIMUS_DESIRED_CAPABILITIES).getJSONObject(APPIUM_SERVER_CAPABILITIES).getString(PLATFORM_NAME)
                    .equalsIgnoreCase("ios")) {
                return true;
            }
        }
        return false;
    }

    public boolean isForInterApp() {
        JSONArray testFeedArray = (JSONArray) jsonObject.get(TEST_FEED);
        if (testFeedArray.length() > 1)
            return true;
        return false;
    }

    public String getAppBelongingTo(String appConsumer) {
        JSONArray testFeedArray = (JSONArray) jsonObject.get(TEST_FEED);
        for (int testFeedIterator = 0; testFeedIterator < testFeedArray.length(); testFeedIterator++) {
            JSONObject testFeedJSON = (JSONObject) testFeedArray.get(testFeedIterator);
            if (testFeedJSON.getString(BELONGS_TO).equalsIgnoreCase(appConsumer)) {
                String appName = testFeedJSON.getJSONObject(OPTIMUS_DESIRED_CAPABILITIES).getJSONObject(APPIUM_SERVER_CAPABILITIES).getString(APP);
                if (appName.contains(".apk") || appName.contains(".ipa") || appName.contains(".app")) {
                    return appName;
                } else {
                    return appName + getAppExtension(testFeedJSON);
                }
            }

        }
        throw new RuntimeException("No app found for -- " + appConsumer);
    }

    private String getAppExtension(JSONObject testFeed) {
        String platform = testFeed.getJSONObject(OPTIMUS_DESIRED_CAPABILITIES).getJSONObject(APPIUM_SERVER_CAPABILITIES).getString(PLATFORM_NAME);
        switch (platform.toLowerCase()) {
            case "android":
                return ".apk";
            case "ios":
                return getIOSExtension(testFeed.getString(RUNS_ON));
        }
        return "";
    }

    private String getIOSExtension(String runsOn) {
        switch (runsOn.toLowerCase()) {
            case "simulator":
                return ".app";
            case "device":
                return ".ipa";
        }
        return "";
    }

    private <T> T getObjectFromJson(String appJson, Class<T> classOfT) {
        return new Gson().fromJson(appJson, classOfT);
    }

    private <T> T getObjectFromJson(JsonElement jsonElement, Class<T> classOfT) {
        return new Gson().fromJson(jsonElement, classOfT);
    }

    public List<DeviceAllocation> allocateDevicesForCurrentScenario() throws DeviceEngagedException {

        List<DeviceAllocation> deviceAllocations = new ArrayList<>();


        JSONArray testFeedArray = (JSONArray) jsonObject.get(TEST_FEED);
        for (int testFeedIterator = 0; testFeedIterator < testFeedArray.length(); testFeedIterator++) {
            DeviceAllocation allocatedDevice = new DeviceAllocation();
            JSONObject testFeedJSON = (JSONObject) testFeedArray.get(testFeedIterator);

            System.out.println("updated testFeed -- " + testFeedJSON.toString());

            Device deviceDetails = new DeviceFinder().getAvailableDeviceAndUpdateToEngaged(testFeedJSON);
            updateTestFeed(testFeedJSON, getAppBelongingTo(testFeedJSON.getString(BELONGS_TO)));
            DesiredCapabilities desiredCapabilities = new CapabilitiesBuilder(testFeedJSON, deviceDetails).buildCapabilities();

            allocatedDevice.setOwner((String) testFeedJSON.get(BELONGS_TO));
            allocatedDevice.setDevice(deviceDetails);
            allocatedDevice.setCapabilities(desiredCapabilities);

            deviceAllocations.add(allocatedDevice);
        }
        return deviceAllocations;


    }


}
