package com.panbet.manticore.oauth.headers.requestData.impl;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;


public class ResourceAccessDataImpl extends OAuthRequestDataImpl implements ResourceAccessData {
    private final String accessToken;


    ResourceAccessDataImpl(final String httpMethod, final URI requestUri,
                           final String consumerKey, final String accessToken) {
        super(httpMethod, requestUri, consumerKey);

        Preconditions.checkArgument(StringUtils.isNotBlank(accessToken), "accessToken must be not empty");
        this.accessToken = accessToken;
    }


    @Override
    public String getAccessToken() {
        return accessToken;
    }
}
