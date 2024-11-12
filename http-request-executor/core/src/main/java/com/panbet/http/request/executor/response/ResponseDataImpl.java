package com.panbet.http.request.executor.response;


import com.google.common.base.Preconditions;

import java.io.InputStream;
import java.util.Optional;


public class ResponseDataImpl implements ResponseData {
    private final int statusCode;

    private final String reasonPhrase;

    private final InputStream entityContent;


    public ResponseDataImpl(final int statusCode, final String reasonPhrase, final InputStream entityContent) {
        Preconditions.checkArgument(reasonPhrase != null, "reasonPhrase must be not null");
        Preconditions.checkArgument(entityContent != null, "entityContent must be not null");

        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
        this.entityContent = entityContent;
    }


    @Override
    public int getStatusCode() {
        return statusCode;
    }


    @Override
    public Optional<String> getStatusReasonPhrase() {
        return Optional.ofNullable(reasonPhrase);
    }


    @Override
    public InputStream getEntityContent() {
        return entityContent;
    }
}
