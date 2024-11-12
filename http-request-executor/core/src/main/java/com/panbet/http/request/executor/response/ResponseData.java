package com.panbet.http.request.executor.response;


import java.io.InputStream;
import java.util.Optional;


/**
 * This interface represents information about a http response
 * which contains a body within itself.
 */
public interface ResponseData {
    /**
     * @return - http response status code
     */
    int getStatusCode();

    /**
     * @return - http response reason phrase
     */
    Optional<String> getStatusReasonPhrase();

    /**
     * @return - body of http response in a form of {@link InputStream}
     */
    InputStream getEntityContent();
}
