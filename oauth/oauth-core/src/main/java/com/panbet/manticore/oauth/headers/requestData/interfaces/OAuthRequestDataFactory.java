package com.panbet.manticore.oauth.headers.requestData.interfaces;


import java.net.URI;


/**
 * Provides methods for creation of different OAuth request datas.
 */
public interface OAuthRequestDataFactory {
    RequestTokenData createRequestTokenData(String httpMethod, URI requestUri);

    RequestTokenData createRequestTokenData(String httpMethod, URI requestUri, String callback);

    AccessTokenData createAccessTokenData(String httpMethod, URI requestUri, String requestToken,
                                          String verificationCode);

    ResourceAccessData createResourceAccessData(String httpMethod, URI requestUri, String accessToken);
}
