package com.testvagrant.optimusCore.utils;

import com.testvagrant.optimusCore.entities.ExecutionDetails;
import com.testvagrant.optimusCore.entities.WDAServerFlag;
import com.testvagrant.optimusCore.utils.OptimusConfigParser;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.AndroidServerFlag;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.jayway.awaitility.Awaitility.await;
import static io.appium.java_client.service.local.flags.GeneralServerFlag.SESSION_OVERRIDE;

public class AppiumServerManager {


    private ExecutionDetails executionDetails;
    private boolean isAndroid;
    private Optional<Integer> port;

    public AppiumServerManager(OptimusConfigParser configParser) {
        this.executionDetails = configParser.getExecutionDetails();
        this.isAndroid = configParser.isForAndroid();
        port = Optional.empty();
    }


    public AppiumDriverLocalService startAppiumService(String scenarioName, String udid) {
        AppiumDriverLocalService appiumService;
        int wdaPort = aRandomOpenPortOnAllLocalInterfaces();
        AppiumServiceBuilder appiumServiceBuilder = new AppiumServiceBuilder()
                .usingDriverExecutable(new File(executionDetails.getAppium_node_path()))
                .withAppiumJS(new File(executionDetails.getAppium_js_path()))
                .withIPAddress("127.0.0.1")
                .withArgument(SESSION_OVERRIDE)
                .usingAnyFreePort()
                .withLogFile(new File(String.format("build" + File.separator + "%s.log", scenarioName + "_" + udid)));
        if (isAndroid) {
            appiumServiceBuilder.withArgument(AndroidServerFlag.BOOTSTRAP_PORT_NUMBER, String.valueOf(aRandomOpenPortOnAllLocalInterfaces()));
        } else {
            appiumServiceBuilder.withArgument(WDAServerFlag.WDA_PORT, String.valueOf(wdaPort));
        }
        appiumService = AppiumDriverLocalService.buildService(appiumServiceBuilder);
        appiumService.start();
        await().atMost(5, TimeUnit.SECONDS).until(() -> appiumService.isRunning());
        return appiumService;

    }


    public Integer aRandomOpenPortOnAllLocalInterfaces() {
        try (
                ServerSocket socket = new ServerSocket(0);
        ) {
            return socket.getLocalPort();

        } catch (IOException e) {
            throw new RuntimeException("no open ports found for bootstrap");
        }
    }

}
