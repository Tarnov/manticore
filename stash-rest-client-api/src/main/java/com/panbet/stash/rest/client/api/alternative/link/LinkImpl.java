package com.panbet.stash.rest.client.api.alternative.link;


import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;


public class LinkImpl implements Link {
    private final String url;

    private final String rel;


    LinkImpl(final String url, final String rel) {
        Preconditions.checkArgument(StringUtils.isNotBlank(url), "Url require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(rel), "Rel require not null");
        this.url = url;
        this.rel = rel;
    }


    public String getUrl() {
        return url;
    }


    public String getRel() {
        return rel;
    }
}
