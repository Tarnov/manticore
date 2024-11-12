package com.panbet.bamboo.rest.client.api.domain;

public interface BasicProjectPlan {
    String getShortName();

    String getShortKey();

    Type getProjectType();

    boolean isEnabled();

    String getKey();

    String getName();

    enum Type {
        CHAIN,
        CHAIN_BRANCH;

        private Type() {

        }
    }
}
