package com.panbet.manticore.oauth.auth;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.auth.AuthHandler;
import com.panbet.http.request.executor.headers.Header;
import com.panbet.http.request.executor.headers.HeaderImpl;
import com.panbet.manticore.oauth.headers.HeaderGenerator;

import java.util.Collection;
import java.util.Collections;


public abstract class OAuthAuthHandler implements AuthHandler {
    private static final String AUTHORIZATION_HEADER_NAME = "Authorization";

    protected final HeaderGenerator headerGenerator;


    protected OAuthAuthHandler(final HeaderGenerator headerGenerator) {
        Preconditions.checkArgument(headerGenerator != null, "headerGenerator must be not null");

        this.headerGenerator = headerGenerator;
    }


    @Override
    public Collection<Header> createAuthHeaders() {
        final String authHeader = generateAuthHeader();

        return Collections.singleton(new HeaderImpl(AUTHORIZATION_HEADER_NAME, authHeader));
    }


    protected abstract String generateAuthHeader();
}
