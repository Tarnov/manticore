package com.panbet.manticore.oauth.headers.requestData.impl;


import com.google.common.base.Preconditions;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import org.apache.commons.lang3.StringUtils;

import java.net.URI;


public class AccessTokenDataImpl extends OAuthRequestDataImpl implements AccessTokenData {
    private final String requestToken;

    private final String verificationCode;


    AccessTokenDataImpl(final String httpMethod, final URI requestUri, final String consumerKey,
                        final String requestToken, final String verificationCode) {
        super(httpMethod, requestUri, consumerKey);

        Preconditions.checkArgument(StringUtils.isNotBlank(requestToken), "requestToken must be not empty");
        Preconditions.checkArgument(StringUtils.isNotBlank(verificationCode), "verificationCode must be not empty");

        this.requestToken = requestToken;
        this.verificationCode = verificationCode;
    }


    @Override
    public String getRequestToken() {
        return requestToken;
    }


    @Override
    public String getVerificationCode() {
        return verificationCode;
    }
}
