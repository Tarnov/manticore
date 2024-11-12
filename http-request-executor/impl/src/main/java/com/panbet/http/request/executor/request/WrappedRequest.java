package com.panbet.http.request.executor.request;


import org.apache.http.client.methods.HttpUriRequest;

import java.io.IOException;


public interface WrappedRequest {
    HttpUriRequest prepareRequest(RequestPreparator requestPreparator) throws IOException;
}
