package com.panbet.manticore.oauth.headers.requestData.impl;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.requestData.interfaces.OAuthRequestData;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;


public class OAuthRequestDataImpl implements OAuthRequestData {
    private final String httpMethod;

    private final URI requestUri;

    private final String consumerKey;


    OAuthRequestDataImpl(final String httpMethod, final URI requestUri, final String consumerKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(httpMethod), "httpMethod must be not empty");
        Preconditions.checkArgument(requestUri != null, "requestUri must be not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(consumerKey), "consumerKey must be not empty");

        this.httpMethod = httpMethod;
        this.requestUri = requestUri;
        this.consumerKey = consumerKey;
    }


    @Override
    public String getHttpMethod() {
        return httpMethod;
    }


    @Override
    public URI getRequestUri() {
        return requestUri;
    }


    @Override
    public String getConsumerKey() {
        return consumerKey;
    }
}
