package com.panbet.http.request.executor.response;


import java.io.IOException;


/**
 * This interface describes a response handler for a http response
 * which encloses an entity inside itself. Response handler
 * maps http response to a java object of a desired type.
 *
 * @param <R> - type of object to which http response is mapped.
 */
public interface EntityEnclosingResponseHandler<R> {
    /**
     * This method maps http response, which is represented by
     * {@link ResponseData} object to an object of a desired type
     * {@link R}
     *
     * @param responseData - representation of http response
     * @return - result of http response mapping
     * @throws IOException - if an error occured during http response mapping
     */
    R handle(ResponseData responseData) throws IOException;
}
