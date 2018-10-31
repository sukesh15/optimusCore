package com.testvagrant.optimusCore.exceptions;

public class DeviceEngagedException extends Exception {
    public DeviceEngagedException() {
        super("Unable to Mark Device to 'Engaged'. Check for device State using `adb devices`. If it is offline, kill the adb server `adb kill-server` and try again.");
    }

}
