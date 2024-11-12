package com.panbet.bamboo.rest.client.api.domain;


import java.io.Serializable;


public class BasicProjectPlanImpl implements BasicProjectPlan, Serializable {
    private String shortName;
    private String shortKey;
    private Type type;
    private boolean enabled;
    private String key;
    private String name;


    public BasicProjectPlanImpl(String shortName, String shortKey,
                                Type type, boolean enabled, String key, String name) {
        this.shortName = shortName;
        this.shortKey = shortKey;
        this.type = type;
        this.enabled = enabled;
        this.key = key;
        this.name = name;
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
    public Type getProjectType() {
        return type;
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
