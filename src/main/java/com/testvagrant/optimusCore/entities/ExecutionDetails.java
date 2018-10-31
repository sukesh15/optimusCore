package com.testvagrant.optimusCore.entities;

public class ExecutionDetails {

    private String appium_js_path = "/usr/local/bin/appium";
    private String appium_node_path = "/usr/local/bin/node";
    private String runConfig = "default";


    public String getAppium_js_path() {
        return appium_js_path;
    }

    public void setAppium_js_path(String appium_js_path) {
        this.appium_js_path = appium_js_path;
    }

    public String getAppium_node_path() {
        return appium_node_path;
    }

    public void setAppium_node_path(String appium_node_path) {
        this.appium_node_path = appium_node_path;
    }

    public String getRunConfig() {
        return runConfig;
    }

    public void setRunConfig(String runConfig) {
        this.runConfig = runConfig;
    }

}
