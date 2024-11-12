package com.panbet.manticore.oauth.headers.util;


import com.panbet.manticore.oauth.headers.methodParameters.MethodParameter;


/**
 * Creates a normalized strings which represent supplied parameters
 * as strings. Normal form of a parameter is declared in OAuth 1.0a
 * specification.
 * <p>
 * See <a href="http://oauth.net/core/1.0a/">http://oauth.net/core/1.0a/</a> for
 * OAuth 1.0a specification details.
 */
public interface KeyValueAppender {
    /**
     * Returns method parameter in a form of a normalized String
     * for using in signature.
     * <p>
     * Normalized string is created using this rule:
     * Key is separated from the corresponding value by an '=' character.
     * <p>
     * Example:
     * key=value
     *
     * @param parameter - parameter to represent as a normalized String
     * @return - parameter as a normalized string
     */
    String appendForSignature(MethodParameter parameter);

    /**
     * Returns method parameter in a form of a normalized String
     * for using in signature.
     * <p>
     * Normalized string is created using this rule:
     * Key is separated from the corresponding value by an '=' character,
     * value should be enclosed in a double quotes '"'.
     * <p>
     * Example:
     * key="value"
     *
     * @param parameter - parameter to represent as a normalized String
     * @return - parameter as a normalized string
     */
    String appendForHeader(MethodParameter parameter);
}
