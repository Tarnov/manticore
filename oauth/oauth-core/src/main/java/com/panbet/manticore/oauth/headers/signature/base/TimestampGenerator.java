package com.panbet.manticore.oauth.headers.signature.base;


/**
 * Timestamp generator is used to generate timestamp for request.
 * The timestamp MUST be equal or greater than the timestamp used in previous requests.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface TimestampGenerator {
    /**
     * @return - generated timestamp
     */
    long generateTimestamp();
}
