package com.panbet.manticore.oauth.response;


import java.util.Optional;


public class OAuthResponseStatusImpl implements OAuthResponseStatus {
    private final int statusCode;

    private final String reasonPhrase;


    public OAuthResponseStatusImpl(final int statusCode, final String reasonPhrase) {
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
