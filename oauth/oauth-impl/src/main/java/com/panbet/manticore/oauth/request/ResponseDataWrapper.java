package com.panbet.manticore.oauth.request;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.response.ResponseData;

import java.io.InputStream;
import java.util.Optional;


public class ResponseDataWrapper implements OAuthResponseData {
    private final ResponseData responseData;


    public ResponseDataWrapper(final ResponseData responseData) {
        Preconditions.checkArgument(responseData != null, "responseData must be not null");

        this.responseData = responseData;
    }


    @Override
    public int getStatusCode() {
        return responseData.getStatusCode();
    }


    @Override
    public Optional<String> getStatusReasonPhrase() {
        return responseData.getStatusReasonPhrase();
    }


    @Override
    public InputStream getEntityContent() {
        return responseData.getEntityContent();
    }
}
