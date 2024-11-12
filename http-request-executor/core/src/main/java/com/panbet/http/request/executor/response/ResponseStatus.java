package com.panbet.http.request.executor.response;


import java.util.Optional;


/**
 * This interface represents information about
 * http response status.
 */
public interface ResponseStatus {
    /**
     * @return - status code of http response.
     */
    int getStatusCode();

    /**
     * @return - reason phrase of http response.
     */
    Optional<String> getReasonPhrase();
}
