package com.panbet.http.request.executor.auth;


import com.panbet.http.request.executor.headers.Header;

import java.util.Collection;


/**
 * This interface serves the purpose of creating
 * authorization headers for http requests.
 */
public interface AuthHandler {
    /**
     * @return - collection of headers, requested for
     * authorization of http request
     */
    Collection<Header> createAuthHeaders();
}
