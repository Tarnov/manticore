package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;


public class MinimalParent implements Serializable {
    private final String id;

    private final String displayId;


    MinimalParent(final String id, final String displayId) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "Id require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(displayId), "DisplayId require not null");

        this.id = id;
        this.displayId = displayId;
    }


    public String getId() {
        return id;
    }


    public String getDisplayId() {
        return displayId;
    }
}
