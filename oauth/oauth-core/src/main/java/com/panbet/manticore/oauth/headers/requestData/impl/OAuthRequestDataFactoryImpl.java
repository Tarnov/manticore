package com.panbet.manticore.oauth.headers.requestData.impl;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.OAuthRequestDataFactory;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;


public class OAuthRequestDataFactoryImpl implements OAuthRequestDataFactory {
    private final String consumerKey;


    public OAuthRequestDataFactoryImpl(final String consumerKey) {
        Preconditions.checkArgument(StringUtils.isNotBlank(consumerKey), "consumerKey must be not empty");

        this.consumerKey = consumerKey;
    }


    @Override
    public RequestTokenData createRequestTokenData(final String httpMethod, final URI requestUri) {
        return new RequestTokenDataImpl(httpMethod, requestUri, consumerKey, null);
    }


    @Override
    public RequestTokenData createRequestTokenData(final String httpMethod, final URI requestUri, final String callback) {
        return new RequestTokenDataImpl(httpMethod, requestUri, consumerKey, callback);
    }


    @Override
    public AccessTokenData createAccessTokenData(final String httpMethod, final URI requestUri,
                                                 final String requestToken, final String verificationCode) {
        return new AccessTokenDataImpl(httpMethod, requestUri, consumerKey, requestToken, verificationCode);
    }


    @Override
    public ResourceAccessData createResourceAccessData(final String httpMethod, final URI requestUri,
                                                       final String accessToken) {
        return new ResourceAccessDataImpl(httpMethod, requestUri, consumerKey, accessToken);
    }
}
