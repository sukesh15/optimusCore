package com.testvagrant.optimusCore.clients;

import com.testvagrant.optimusCore.builders.BuildsRequestBuilder;
import com.testvagrant.optimusCore.requests.Build;
import com.testvagrant.optimusCore.responses.BuildsResponse;
import com.testvagrant.optimusCore.entities.*;
import io.restassured.response.Response;

import java.util.List;

import static io.restassured.RestAssured.given;

public class BuildsClient {
    private final String BUILDS = MongoService.getMongoService()+"/builds";

    public Build createNewBuild() {
        System.out.println("Builds Path" +BUILDS);

        Build buildsRequest = new BuildsRequestBuilder().build();
        Response post = given()
                .header("Content-Type", "application/json")
                .body(buildsRequest)
                .post(BUILDS);
        return post.as(Build.class);
    }

    public Build findBuildById(String id) {
        Response get = given()
                .header("Content-Type", "application/json")
                .queryParam("id",id)
                .get(BUILDS+"/search/findById");
        return get.as(Build.class);
    }

    public String getLatestBuildId() {
        Response get = given()
                .header("Content-Type", "application/json")
                .get(BUILDS+"/search/findAllBuildsByOrderByBuildStartTimeDesc");
        BuildsResponse buildsResponse = get.as(BuildsResponse.class);
        List<Build> allBuildsInDescendingOrder = buildsResponse.getContent();
        return allBuildsInDescendingOrder.get(0).getId();
    }

    public Build updateBuildRecord(Build build) {
        Response get = given()
                .header("Content-Type", "application/json")
                .body(build)
                .patch(BUILDS+String.format("/%s",build.getId()));
        return get.as(Build.class);
    }
}
