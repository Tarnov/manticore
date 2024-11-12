package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;


public enum State {
    AVAILABLE(1, "Available"),
    INITIALISATION_FAILED(2, "Failed to initialise repository"),
    INITIALISING(0, "Initialising");

    private final int id;

    private final String statusMessage;


    private State(final int id, final String statusMessage) {
        Preconditions.checkArgument(StringUtils.isNotBlank(statusMessage), "statusMessage require not blank");
        Preconditions.checkArgument(id >= 0 && id <= 2, "id must be from 0 to 2");
        this.id = id;
        this.statusMessage = statusMessage;
    }


    public int getId() {
        return this.id;
    }


    public String getStatusMessage() {
        return this.statusMessage;
    }


    public static State fromId(final int id) {
        final State[] states = values();

        for (final State value : states) {
            if (value.getId() == id) {
                return value;
            }
        }

        throw new IllegalArgumentException("No Repository.State is associated with ID [" + id + "]");
    }
}
