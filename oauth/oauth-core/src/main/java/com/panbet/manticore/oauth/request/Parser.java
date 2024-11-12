package com.panbet.manticore.oauth.request;


import java.io.InputStream;


/**
 * This interface serves the purpose of parsing a java
 * object from an {@link InputStream}.
 *
 * @param <T> - type of parsed object
 */
public interface Parser<T> {
    /**
     * Parses object from incoming {@link InputStream}
     *
     * @param responseStream - inputStream which contains data to parse
     * @return - object parsed from responseStream
     */
    T parseResponse(final InputStream responseStream);
}
