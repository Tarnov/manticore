package com.panbet.http.request.executor;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.response.ResponseStatus;


/**
 * Signals that request was executed and response from server
 * was obtained, but response handling has failed.
 */
public class ResponseHandlingException extends RequestExecutorException {
    private final ResponseStatus responseStatus;


    ResponseHandlingException(final ResponseStatus responseStatus, final Throwable cause) {
        super(cause);

        Preconditions.checkArgument(responseStatus != null, "responseStatus must be not null");

        this.responseStatus = responseStatus;
    }


    /**
     * @return - http response status returned by server
     */
    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }
}
