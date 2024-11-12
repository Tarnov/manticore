package com.panbet.bamboo.rest.client.api.domain;

import java.io.Serializable;


public class BranchImpl implements Branch, Serializable {
    private String description;
    private String shortName;
    private String shortKey;
    private boolean enabled;
    private String key;
    private String name;


    public BranchImpl(String description, String shortName, String shortKey, boolean enabled, String key,
                      String name) {
        this.description = description;
        this.shortName = shortName;
        this.shortKey = shortKey;
        this.enabled = enabled;
        this.key = key;
        this.name = name;
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public String getShortName() {
        return shortName;
    }


    @Override
    public String getShortKey() {
        return shortKey;
    }


    @Override
    public boolean isEnabled() {
        return enabled;
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getName() {
        return name;
    }
}
