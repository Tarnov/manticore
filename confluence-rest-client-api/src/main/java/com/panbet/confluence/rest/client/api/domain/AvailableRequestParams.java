package com.panbet.confluence.rest.client.api.domain;


public enum AvailableRequestParams {
    TYPE("type"),
    SPACEKEY("spaceKey"),
    TITLE("title"),
    POSTINGDAY("postingDay"),
    START("start"),
    LIMIT("limit");

    private final String name;

    AvailableRequestParams(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

}
