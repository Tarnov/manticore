package com.panbet.manticore.oauth.headers.requestData.interfaces;


import java.net.URI;


/**
 * Describes common attributes for an OAuth 1.0a request.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface OAuthRequestData {
    String getHttpMethod();

    URI getRequestUri();

    String getConsumerKey();
}
