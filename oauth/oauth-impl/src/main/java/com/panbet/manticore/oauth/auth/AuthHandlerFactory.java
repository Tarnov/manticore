package com.panbet.manticore.oauth.auth;


import com.panbet.http.request.executor.auth.AuthHandler;
import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;


public interface AuthHandlerFactory {
    AuthHandler createRequestTokenAuthHandler(RequestTokenData requestTokenData);

    AuthHandler createAccessTokenAuthHandler(AccessTokenData accessTokenData);

    AuthHandler createRequestResourceAuthHandler(ResourceAccessData resourceAccessData);
}
