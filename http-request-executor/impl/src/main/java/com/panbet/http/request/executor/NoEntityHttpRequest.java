package com.panbet.http.request.executor;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.request.HttpMethod;
import com.panbet.http.request.executor.request.Request;
import org.apache.http.client.methods.HttpRequestBase;

import java.net.URI;


public class NoEntityHttpRequest extends HttpRequestBase {
    private final HttpMethod method;


    public NoEntityHttpRequest(final HttpMethod method) {
        Preconditions.checkArgument(method != null, "method must be not null");

        this.method = method;
    }


    public NoEntityHttpRequest(final HttpMethod method, final URI uri) {
        Preconditions.checkArgument(uri != null, "uri must be not null");
        Preconditions.checkArgument(method != null, "method must be not null");

        this.method = method;
        setURI(uri);
    }


    public NoEntityHttpRequest(final HttpMethod method, final String uri) {
        Preconditions.checkArgument(method != null, "method must be not null");
        Preconditions.checkArgument(uri != null, "uri must be not null");

        this.method = method;
        setURI(URI.create(uri));
    }


    public NoEntityHttpRequest(final Request request) {
        Preconditions.checkArgument(request != null, "request must be not null");

        this.method = request.getMethod();
        setURI(request.getUri());
    }


    @Override
    public String getMethod() {
        return method.getName();
    }
}
