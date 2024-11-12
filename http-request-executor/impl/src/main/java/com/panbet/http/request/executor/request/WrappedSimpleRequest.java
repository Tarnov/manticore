package com.panbet.http.request.executor.request;


import com.google.common.base.Preconditions;
import org.apache.http.client.methods.HttpUriRequest;


public class WrappedSimpleRequest implements WrappedRequest {
    private final Request request;


    public WrappedSimpleRequest(final Request request) {
        Preconditions.checkArgument(request != null, "request must be not null");

        this.request = request;
    }


    @Override
    public HttpUriRequest prepareRequest(final RequestPreparator requestPreparator) {
        return requestPreparator.prepareRequest(request);
    }
}
