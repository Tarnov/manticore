package com.panbet.stash.rest.client.api.alternative.links.self;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;


public class SelfImpl implements Self {
    private final URI href;


    SelfImpl(final String href) {
        Preconditions.checkArgument(StringUtils.isNotBlank(href), "Href require not null");

        this.href = URI.create(href);
    }


    public URI getHref() {
        return href;
    }
}
