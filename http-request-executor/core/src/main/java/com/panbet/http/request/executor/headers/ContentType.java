package com.panbet.http.request.executor.headers;


/**
 * Contains the list of common values for Content-Type header.
 */
public enum ContentType {
    JSON("application/json");


    private final Header header;


    ContentType(final String type) {
        this.header = new HeaderImpl("Content-Type", type);
    }


    public Header getHeader() {
        return header;
    }
}
