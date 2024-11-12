package com.panbet.manticore.oauth.auth;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.HeaderGenerator;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;


public class RequestTokenAuthHandler extends OAuthAuthHandler {
    private final RequestTokenData requestTokenData;


    public RequestTokenAuthHandler(final HeaderGenerator headerGenerator, final RequestTokenData requestTokenData) {
        super(headerGenerator);

        Preconditions.checkArgument(requestTokenData != null, "requestTokenData must be not null");
        this.requestTokenData = requestTokenData;
    }


    @Override
    protected String generateAuthHeader() {
        return headerGenerator.generateRequestTokenHeader(requestTokenData);
    }
}
