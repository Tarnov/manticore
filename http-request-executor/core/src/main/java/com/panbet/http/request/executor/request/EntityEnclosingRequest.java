package com.panbet.http.request.executor.request;


/**
 * This interface represents a http request which contains
 * a body within itself.
 */
public interface EntityEnclosingRequest extends Request {
    /**
     * @return - body of http request in a form of {@link String}
     */
    String getEntity();
}
