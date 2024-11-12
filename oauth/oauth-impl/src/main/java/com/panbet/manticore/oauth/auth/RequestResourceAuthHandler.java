package com.panbet.manticore.oauth.auth;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.HeaderGenerator;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;


public class RequestResourceAuthHandler extends OAuthAuthHandler {
    private final ResourceAccessData resourceAccessData;


    public RequestResourceAuthHandler(final HeaderGenerator headerGenerator, final ResourceAccessData resourceAccessData) {
        super(headerGenerator);

        Preconditions.checkArgument(resourceAccessData != null, "resourceAccessData must be not null");
        this.resourceAccessData = resourceAccessData;
    }


    @Override
    protected String generateAuthHeader() {
        return headerGenerator.generateRequestResourceHeader(resourceAccessData);
    }
}
