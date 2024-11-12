package com.panbet.manticore.oauth.auth;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.auth.AuthHandler;
import com.panbet.manticore.oauth.headers.HeaderGenerator;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;


public class AuthHandlerFactoryImpl implements AuthHandlerFactory {
    private final HeaderGenerator headerGenerator;


    public AuthHandlerFactoryImpl(final HeaderGenerator headerGenerator) {
        Preconditions.checkArgument(headerGenerator != null, "headerGenerator must be not null");

        this.headerGenerator = headerGenerator;
    }


    @Override
    public AuthHandler createRequestTokenAuthHandler(final RequestTokenData requestTokenData) {
        return new RequestTokenAuthHandler(headerGenerator, requestTokenData);
    }


    @Override
    public AuthHandler createAccessTokenAuthHandler(final AccessTokenData accessTokenData) {
        return new AccessTokenAuthHandler(headerGenerator, accessTokenData);
    }


    @Override
    public AuthHandler createRequestResourceAuthHandler(final ResourceAccessData resourceAccessData) {
        return new RequestResourceAuthHandler(headerGenerator, resourceAccessData);
    }
}
