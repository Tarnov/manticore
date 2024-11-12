package com.panbet.stash.rest.client.api.alternative.repo;


public enum ProjectType {
    NORMAL(0),
    PERSONAL(1);

    private final int id;


    private ProjectType(final int id) {
        this.id = id;
    }


    public static ProjectType fromId(final int id) {
        for (final ProjectType value : values()) {
            if (value.getId() == id) {
                return value;
            }
        }

        throw new IllegalArgumentException("No ProjectType is available for ID [" + id + "]");
    }


    public int getId() {
        return this.id;
    }
}