package com.panbet.http.request.executor.response;


import com.google.common.base.Preconditions;

import java.io.IOException;


/**
 * Signals about an exception occured during response handling.
 */
public class ResponseHandlerException extends IOException {
    private final ResponseStatus responseStatus;


    public ResponseHandlerException(final ResponseStatus responseStatus, final String message) {
        super(message);

        Preconditions.checkArgument(responseStatus != null, "responseStatus must be not null");

        this.responseStatus = responseStatus;
    }


    public ResponseHandlerException(final ResponseStatus responseStatus, final String message, final Throwable cause) {
        super(message, cause);

        Preconditions.checkArgument(responseStatus != null, "responseStatus must be not null");

        this.responseStatus = responseStatus;
    }


    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
