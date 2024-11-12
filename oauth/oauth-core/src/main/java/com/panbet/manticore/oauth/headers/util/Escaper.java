package com.panbet.manticore.oauth.headers.util;


/**
 * Escapes string using the [RFC3986] percent-encoding (%xx) mechanism.
 * Encodes all characters not in the unreserved character set ([RFC3986] section 2.3).
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface Escaper {
    /**
     * @param s - string to escape
     * @return - escaped string
     */
    String escape(String s);
}
