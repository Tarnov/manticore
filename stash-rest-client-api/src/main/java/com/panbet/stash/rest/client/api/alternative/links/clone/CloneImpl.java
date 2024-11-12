package com.panbet.stash.rest.client.api.alternative.links.clone;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;


public class CloneImpl implements Clone {
    private final URI href;

    private final String name;


    CloneImpl(final String href, final String name) {
        Preconditions.checkArgument(StringUtils.isNotBlank(href), "Href require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name require not null");

        this.href = URI.create(href);
        this.name = name;
    }


    @Override
    public URI getHref() {
        return href;
    }


    @Override
    public String getName() {
        return name;
    }
}
