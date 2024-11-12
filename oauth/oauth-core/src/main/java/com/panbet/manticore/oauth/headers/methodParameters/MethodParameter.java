package com.panbet.manticore.oauth.headers.methodParameters;


/**
 * Represents an OAuth 1.0a Protocol Parameters
 * used in Authorization header.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface MethodParameter {
    /**
     * @return - OAuth protocol parameter key
     */
    String getKey();

    /**
     * @return - OAuth protocol parameter value
     */
    String getValue();
}
