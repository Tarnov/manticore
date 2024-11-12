package com.panbet.http.request.executor.response;


import java.util.Optional;


public class ResponseStatusImpl implements ResponseStatus {
    private final int statusCode;

    private final String reasonPhrase;


    public ResponseStatusImpl(final int statusCode, final String reasonPhrase) {
        this.statusCode = statusCode;
        this.reasonPhrase = reasonPhrase;
    }


    @Override
    public int getStatusCode() {
        return statusCode;
    }


    @Override
    public Optional<String> getReasonPhrase() {
        return Optional.ofNullable(reasonPhrase);
    }
}
