package com.panbet.stash.rest.client.api.domain;


public class BranchInfo {
    private final String id;

    private final String displayId;

    private final boolean isDefault;


    public BranchInfo(final String id, final String displayId, final boolean isDefault) {
        this.id = id;
        this.displayId = displayId;
        this.isDefault = isDefault;
    }


    public String getId() {
        return id;
    }


    public String getDisplayId() {
        return displayId;
    }


    public boolean isDefault() {
        return isDefault;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BranchInfo)) {
            return false;
        }

        final BranchInfo that = (BranchInfo) o;

        return !(id != null ? !id.equals(that.id) : that.id != null);

    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
