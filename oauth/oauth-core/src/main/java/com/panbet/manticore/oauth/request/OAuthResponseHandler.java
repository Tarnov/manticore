package com.panbet.manticore.oauth.request;


import java.io.IOException;


/**
 * This interface describes a response handler for a http response
 * which encloses an entity inside itself. Response handler
 * maps http response to a java object of a desired type.
 *
 * @param <T> - type of object to which http response is mapped.
 */
public interface OAuthResponseHandler<T> {
    /**
     * This method maps http response, which is represented by
     * {@link OAuthResponseData} object to an object of a desired type
     * {@link T}
     *
     * @param oAuthResponseData - representation of http response
     * @return - result of http response mapping
     * @throws IOException - if an error occured during http response mapping
     */
    T handle(OAuthResponseData oAuthResponseData) throws IOException;
}
