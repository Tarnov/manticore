package com.panbet.manticore.oauth.headers;


import com.panbet.manticore.oauth.headers.requestData.interfaces.AccessTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.RequestTokenData;
import com.panbet.manticore.oauth.headers.requestData.interfaces.ResourceAccessData;


/**
 * Generator of Authorization header for OAuth requests.
 * <p>
 * Header generation must occur according to OAuth 1.0a specification.
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface HeaderGenerator {
    /**
     * Generates an Authorization header for obtaining an
     * Unauthorized Request Token request.
     *
     * @param requestTokenData - request data
     * @return - header value
     */
    String generateRequestTokenHeader(final RequestTokenData requestTokenData);

    /**
     * Generates an Authorization header for requesting
     * an Access Token request.
     *
     * @param accessTokenData - request data
     * @return - header value
     */
    String generateAccessTokenHeader(final AccessTokenData accessTokenData);

    /**
     * Generates an Authorization header for accessing
     * the Protected Resources request.
     *
     * @param resourceAccessData - request data
     * @return - header value
     */
    String generateRequestResourceHeader(final ResourceAccessData resourceAccessData);
}
