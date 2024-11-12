package com.panbet.manticore.oauth;


import com.panbet.http.request.executor.HttpRequestExecutor;
import com.panbet.http.request.executor.response.ResponseParser;
import com.panbet.manticore.oauth.auth.AuthHandlerFactory;
import com.panbet.manticore.oauth.entities.RequestToken;
import com.panbet.manticore.oauth.entities.Token;


public class OAuthClientImplBuilder {
    private AuthHandlerFactory authHandlerFactory;

    private HttpRequestExecutor httpRequestExecutor;

    private ResponseParser<RequestToken> requestTokenParser;

    private ResponseParser<Token> accessTokenParser;


    public OAuthClientImplBuilder setAuthHandlerFactory(final AuthHandlerFactory authHandlerFactory) {
        this.authHandlerFactory = authHandlerFactory;
        return this;
    }


    public OAuthClientImplBuilder setHttpRequestExecutor(final HttpRequestExecutor httpRequestExecutor) {
        this.httpRequestExecutor = httpRequestExecutor;
        return this;
    }


    public OAuthClientImplBuilder setRequestTokenParser(final ResponseParser<RequestToken> requestTokenParser) {
        this.requestTokenParser = requestTokenParser;
        return this;
    }


    public OAuthClientImplBuilder setAccessTokenParser(final ResponseParser<Token> accessTokenParser) {
        this.accessTokenParser = accessTokenParser;
        return this;
    }


    public OAuthClient build() {
        return new OAuthClientImpl(authHandlerFactory, httpRequestExecutor, requestTokenParser, accessTokenParser);
    }
}