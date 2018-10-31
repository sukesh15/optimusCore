package com.testvagrant.optimusCore.responses;


import com.testvagrant.optimusCore.requests.Build;
import com.testvagrant.optimusCore.requests.Link;

import java.util.List;

public class BuildsResponse {

    private List<Link> links;
    private List<Build> content;

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

    public List<Build> getContent() {
        return content;
    }

    public void setContent(List<Build> content) {
        this.content = content;
    }

}
