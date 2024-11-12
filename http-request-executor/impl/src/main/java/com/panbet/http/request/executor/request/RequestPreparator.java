package com.panbet.http.request.executor.request;


import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;


/**
 * This interface serves the purpose of converting
 * {@link Request} and {@link EntityEnclosingRequest}
 * to a {@link HttpUriRequest} of apache http client
 * library.
 */
public interface RequestPreparator {
    /**
     * @param data - request to convert
     * @return - converted request
     */
    HttpUriRequest prepareRequest(Request data);

    /**
     * @param data - request to convert
     * @return - converted request
     * @throws IOException - if an exception occured during conversion
     */
    HttpUriRequest prepareRequest(EntityEnclosingRequest data) throws IOException;
}
