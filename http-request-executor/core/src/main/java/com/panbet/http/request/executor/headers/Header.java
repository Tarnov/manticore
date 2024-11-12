package com.panbet.http.request.executor.headers;


/**
 * This interface represents a http message header.
 */
public interface Header {
    /**
     * @return - name of http message header
     */
    String getName();

    /**
     * @return - value of http message header
     */
    String getValue();
}
