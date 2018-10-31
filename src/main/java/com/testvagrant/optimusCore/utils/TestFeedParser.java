package com.testvagrant.optimusCore.utils;

import org.json.simple.JSONObject;

public class TestFeedParser {
    private JSONObject testFeed;

    public TestFeedParser(JSONObject testFeed) {
        this.testFeed = testFeed;
    }

    public TestFeedDetails parse() {
        String runsOn = (String) testFeed.get("runsOn");
        JSONObject optimusDesiredCapabilities = (JSONObject) testFeed.get("optimusDesiredCapabilities");
        JSONObject appiumDesiredCapabilities = (JSONObject) optimusDesiredCapabilities.get("appiumServerCapabilities");
        String udid = (String) appiumDesiredCapabilities.get("udid");
        String platformVersion = (String) appiumDesiredCapabilities.get("platformVersion");
        String platformName = (String) appiumDesiredCapabilities.get("platformName");
        TestFeedDetails testFeedDetails = new TestFeedDetails();
        testFeedDetails.setPlatform(platformName);
        testFeedDetails.setRunsOn(runsOn);
        testFeedDetails.setUdid(udid);
        testFeedDetails.setPlatformVersion(platformVersion);
        return testFeedDetails;
    }

}
