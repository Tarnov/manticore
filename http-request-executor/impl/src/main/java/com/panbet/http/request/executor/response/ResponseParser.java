package com.panbet.http.request.executor.response;


import java.io.InputStream;


/**
 * This interface serves the purpose of parsing a java
 * object from an {@link InputStream}.
 *
 * @param <T> - type of parsed object
 */
public interface ResponseParser<T> {
    /**
     * Parses object from incoming {@link InputStream}
     *
     * @param responseStream - inputStream which contains data to parse
     * @return - object parsed from responseStream
     */
    T parseResponse(final InputStream responseStream);
}
