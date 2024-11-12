package com.panbet.http.request.executor.request;


/**
 * Contains the list of possible http methods.
 */
public enum HttpMethod {
    GET("GET"),
    POST("POST");


    private final String name;


    HttpMethod(final String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }
}
