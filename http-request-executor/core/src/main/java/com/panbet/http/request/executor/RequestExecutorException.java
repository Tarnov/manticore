package com.panbet.http.request.executor;


import java.io.IOException;


/**
 * Signals that request execution has failed and no
 * response from server was obtained.
 */
public class RequestExecutorException extends IOException {
    RequestExecutorException(final Throwable cause) {
        super(cause);
    }
}
