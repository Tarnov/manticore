package com.panbet.http.request.executor.request;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.headers.Header;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;


public class RequestImpl implements Request {
    private final HttpMethod method;

    private final URI uri;

    private final Collection<Header> headers;


    public RequestImpl(final HttpMethod httpMethod, final URI uri) {
        this(httpMethod, uri, new ArrayList<>());
    }


    public RequestImpl(final HttpMethod method, final URI uri, final Collection<Header> headers) {
        Preconditions.checkArgument(method != null, "method must be not null");
        Preconditions.checkArgument(uri != null, "uri must be not null");
        Preconditions.checkArgument(headers != null, "headers must be not null");

        this.method = method;
        this.uri = uri;
        this.headers = headers;
    }


    @Override
    public HttpMethod getMethod() {
        return method;
    }


    @Override
    public URI getUri() {
        return uri;
    }


    @Override
    public Collection<Header> getHeaders() {
        return headers;
    }
}
