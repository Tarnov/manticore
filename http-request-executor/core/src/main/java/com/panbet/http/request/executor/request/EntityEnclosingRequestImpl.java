package com.panbet.http.request.executor.request;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.headers.Header;

import java.net.URI;
import java.util.Collection;


public class EntityEnclosingRequestImpl extends RequestImpl implements EntityEnclosingRequest {
    private final String entity;


    public EntityEnclosingRequestImpl(final HttpMethod method, final URI uri, final Collection<Header> headers,
                                      final String entity) {
        super(method, uri, headers);

        Preconditions.checkArgument(entity != null, "entity must be not null");
        this.entity = entity;
    }


    public EntityEnclosingRequestImpl(final Request request, final String entity) {
        super(request.getMethod(), request.getUri(), request.getHeaders());

        this.entity = entity;
    }


    @Override
    public String getEntity() {
        return entity;
    }
}
