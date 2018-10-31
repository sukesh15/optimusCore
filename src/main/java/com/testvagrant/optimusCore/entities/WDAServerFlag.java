package com.testvagrant.optimusCore.entities;

import io.appium.java_client.service.local.flags.ServerArgument;

public enum WDAServerFlag implements ServerArgument {

    WDA_PORT("--webdriveragent-port");

    private String arg;

    WDAServerFlag(String arg) {
        this.arg = arg;
    }

    @Override
    public String getArgument() {
        return arg;
    }

}
