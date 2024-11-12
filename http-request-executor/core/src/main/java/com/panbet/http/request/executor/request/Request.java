package com.panbet.http.request.executor.request;


import com.panbet.http.request.executor.headers.Header;

import java.net.URI;
import java.util.Collection;


/**
 * This interface represents a http request
 * which contains NO body.
 */
public interface Request {
    /**
     * @return - http method - one of possible http request types
     */
    HttpMethod getMethod();

    /**
     * @return - path to a target resource
     */
    URI getUri();

    /**
     * @return - request headers
     */
    Collection<Header> getHeaders();
}
