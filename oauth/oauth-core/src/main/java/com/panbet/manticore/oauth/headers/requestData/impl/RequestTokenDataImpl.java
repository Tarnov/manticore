package com.panbet.manticore.oauth.headers.requestData.impl;


import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;

import java.net.URI;


public class RequestTokenDataImpl extends OAuthRequestDataImpl implements RequestTokenData {
    private final String callback;


    RequestTokenDataImpl(final String httpMethod, final URI requestUri, final String consumerKey,
                         final String callback) {
        super(httpMethod, requestUri, consumerKey);

        this.callback = callback == null ? "oob" : callback;
    }


    @Override
    public String getCallback() {
        return callback;
    }
}
