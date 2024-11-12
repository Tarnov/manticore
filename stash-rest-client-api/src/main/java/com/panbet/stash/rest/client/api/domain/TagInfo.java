package com.panbet.stash.rest.client.api.domain;

import com.atlassian.stash.repository.Tag;

import javax.annotation.Nonnull;

public class TagInfo implements Tag {
    private String id;
    private String displayId;
    private String latestChangeset;
    private String hash;

    public TagInfo(String id, String displayId, String latestChangeset, String hash) {
        this.id = id;
        this.displayId = displayId;
        this.latestChangeset = latestChangeset;
        this.hash = hash;
    }

    @Override
    public String getHash() {
        return hash;
    }

    @Nonnull
    @Override
    public String getLatestChangeset() {
        return latestChangeset;
    }

    @Nonnull
    @Override
    public String getDisplayId() {
        return displayId;
    }

    @Nonnull
    @Override
    public String getId() {
        return id;
    }
}
