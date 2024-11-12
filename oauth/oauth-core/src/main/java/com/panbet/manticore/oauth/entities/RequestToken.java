package com.panbet.manticore.oauth.entities;


/**
 * An OAuth request token as it is returned by Service Provider.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface RequestToken extends Token {
    /**
     * @return - information about callback confirmation - informs
     * whether Service Provider received callback value.
     */
    boolean callbackConfirmed();
}
