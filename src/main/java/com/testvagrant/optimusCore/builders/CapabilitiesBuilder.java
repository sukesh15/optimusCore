package com.testvagrant.optimusCore.builders;

import com.testvagrant.optimusCore.requests.Device;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.util.Iterator;

public class CapabilitiesBuilder {
    private DesiredCapabilities capabilities = new DesiredCapabilities();
    private JSONObject testFeedJSON;

    public CapabilitiesBuilder(JSONObject testFeedJSON, Device device) {

        this.testFeedJSON = testFeedJSON;
        JSONObject appiumServerCapabilities = (JSONObject) ((JSONObject) testFeedJSON.get("optimusDesiredCapabilities")).get("appiumServerCapabilities");
        if (!isNativeApp()) {
            buildWebAppCapabilities(appiumServerCapabilities, device);
            return;
        }
        File app = getAppFile((String) testFeedJSON.get("appDir"), (String) appiumServerCapabilities.get("app"));
        capabilities.setCapability("app", app.getAbsolutePath());
        capabilities.setCapability("udid", device.getUdid());
        capabilities.setCapability("deviceName", device.getDeviceName());
        initializeCapabilities();
    }

    private void buildWebAppCapabilities(JSONObject appiumServerCapabilities, Device deviceDetails) {
        if (isBrowserAppProvided(appiumServerCapabilities)) {
            File app = getAppFile((String) testFeedJSON.get("appDir"), (String) appiumServerCapabilities.get("app"));
            capabilities.setCapability("app", app.getAbsolutePath());
        }
        capabilities.setCapability("udid", deviceDetails.getUdid());
        capabilities.setCapability("deviceName", deviceDetails.getDeviceName());
        initializeCapabilities();
    }

    private boolean isAndroid(JSONObject testFeedJSON) {
        String platformName = (String) testFeedJSON.get("platformName");
        return platformName.equalsIgnoreCase("Android");
    }

    private boolean isNativeApp() {
        Boolean nativeApp = testFeedJSON.getBoolean("nativeApp");
        return nativeApp;
    }

    private boolean isBrowserAppProvided(JSONObject testFeedJSON) {
        try {
            String appName = testFeedJSON.getString("app");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void initializeCapabilities() {
        JSONObject appiumServerCapabilities = (JSONObject) ((JSONObject) testFeedJSON.get("optimusDesiredCapabilities")).get("appiumServerCapabilities");
        JSONObject platformSpecificCapabilities = null;

        if (appiumServerCapabilities.get("platformName").toString().equalsIgnoreCase("Android")) {
            platformSpecificCapabilities = (JSONObject) ((JSONObject) testFeedJSON.get("optimusDesiredCapabilities")).get("androidOnlyCapabilities");
        } else if (appiumServerCapabilities.get("platformName").toString().equalsIgnoreCase("iOS")) {
            platformSpecificCapabilities = (JSONObject) ((JSONObject) testFeedJSON.get("optimusDesiredCapabilities")).get("iOSOnlyCapabilities");
        }
        setDesiredCapabilities(appiumServerCapabilities, capabilities);
        setDesiredCapabilities(platformSpecificCapabilities, capabilities);
    }

    private void setDesiredCapabilities(JSONObject platformSpecificCapabilities, DesiredCapabilities desiredCapabilities) {
        Iterator<String> keys = platformSpecificCapabilities.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            if (key.equalsIgnoreCase("app")) {
                continue;
            }
            Object value = platformSpecificCapabilities.get(key);
            if (value instanceof Boolean) {
                desiredCapabilities.setCapability(key, platformSpecificCapabilities.getBoolean(key));
            } else if (value instanceof String) {
                desiredCapabilities.setCapability(key, platformSpecificCapabilities.get(key));
            } else if (value instanceof Integer) {
                desiredCapabilities.setCapability(key, platformSpecificCapabilities.getInt(key));
            } else if (value instanceof JSONArray) {
                desiredCapabilities.setCapability(key, platformSpecificCapabilities.getJSONArray(key));
            }
        }
    }


    public DesiredCapabilities buildCapabilities() {
        return capabilities;
    }


    private static File getAppFile(String appLocation, String appName) {
        File appDir = new File(appLocation);
        return new File(appDir, appName);
    }
}
