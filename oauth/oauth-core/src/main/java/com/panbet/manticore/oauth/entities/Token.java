package com.panbet.manticore.oauth.entities;


/**
 * An OAuth token as it is returned by service provider.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface Token {
    /**
     * @return - the token itself
     */
    String getToken();

    /**
     * @return - the token secret
     */
    String getTokenSecret();
}
