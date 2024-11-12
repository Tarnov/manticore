package com.panbet.http.request.executor.request;


import com.google.common.base.Preconditions;
import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;


public class WrappedEntityEnclosingRequest implements WrappedRequest {
    private final EntityEnclosingRequest request;


    public WrappedEntityEnclosingRequest(final EntityEnclosingRequest request) {
        Preconditions.checkArgument(request != null, "request must be not null");

        this.request = request;
    }


    @Override
    public HttpUriRequest prepareRequest(final RequestPreparator requestPreparator) throws IOException {
        return requestPreparator.prepareRequest(request);
    }
}
