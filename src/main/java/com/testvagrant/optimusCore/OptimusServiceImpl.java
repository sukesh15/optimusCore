package com.testvagrant.optimusCore;

import com.testvagrant.optimusCore.clients.BuildsClient;
import com.testvagrant.optimusCore.services.OptimusService;

public class OptimusServiceImpl implements OptimusService {
    private static String latestBuildID;

    @Override
    public String getLatestBuild() {
        return new BuildsClient().getLatestBuildId();
    }
}
