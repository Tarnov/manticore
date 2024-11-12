package com.panbet.manticore.oauth.auth;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.HeaderGenerator;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;


public class AccessTokenAuthHandler extends OAuthAuthHandler {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    private final AccessTokenData accessTokenData;


    public AccessTokenAuthHandler(final HeaderGenerator headerGenerator, final AccessTokenData accessTokenData) {
        super(headerGenerator);

        Preconditions.checkArgument(accessTokenData != null, "accessTokenData must be not null");
        this.accessTokenData = accessTokenData;
    }


    @Override
    protected String generateAuthHeader() {
        return headerGenerator.generateAccessTokenHeader(accessTokenData);
    }
}
