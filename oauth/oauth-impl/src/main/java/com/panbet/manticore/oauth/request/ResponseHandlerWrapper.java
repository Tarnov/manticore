package com.panbet.manticore.oauth.request;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.response.EntityEnclosingResponseHandler;
import com.panbet.http.request.executor.response.ResponseData;

import java.io.IOException;


public class ResponseHandlerWrapper<T> implements EntityEnclosingResponseHandler<T> {
    private final OAuthResponseHandler<T> oAuthResponseHandler;


    public ResponseHandlerWrapper(final OAuthResponseHandler<T> oAuthResponseHandler) {
        Preconditions.checkArgument(oAuthResponseHandler != null, "oAuthResponseHandler must be not null");

        this.oAuthResponseHandler = oAuthResponseHandler;
    }


    @Override
    public T handle(final ResponseData responseData) throws IOException {
        final OAuthResponseData oAuthResponseData = new ResponseDataWrapper(responseData);

        return oAuthResponseHandler.handle(oAuthResponseData);
    }
}
