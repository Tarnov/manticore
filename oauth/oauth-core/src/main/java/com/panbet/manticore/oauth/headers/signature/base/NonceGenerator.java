package com.panbet.manticore.oauth.headers.signature.base;


/**
 * Nonce generator is used to generate nonce for request - value
 * that is unique for all requests with same timestamp.
 * A nonce is a random string, uniquely generated for each request.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface NonceGenerator {
    /**
     * @return - generated nonce
     */
    String generateNonce();
}
