package com.panbet.manticore.oauth.response;


import java.util.Optional;


/**
 * This interface represents information about
 * http response status.
 */
public interface OAuthResponseStatus {
    /**
     * @return - status code of http response.
     */
    int getStatusCode();

    /**
     * @return - reason phrase of http response.
     */
    Optional<String> getReasonPhrase();
}
