package com.testvagrant.optimusCore.entities;

public enum Platform {
    ANDROID("ANDROID"),
    IOS("IOS");

    private String name;

    Platform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

