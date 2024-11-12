package com.panbet.http.request.executor.response;


import com.google.common.base.Preconditions;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;


/**
 * {@inheritDoc}
 * <p>
 * This implementation of {@link EntityEnclosingResponseHandler} maps
 * http response to an object in a simple way. If response has
 * a non-2xx status code (which means a non-success status code) - an exception is thrown.
 */
public class SimpleResponseHandler<R> implements EntityEnclosingResponseHandler<R> {
    private final ResponseParser<R> resultParser;


    public SimpleResponseHandler(final ResponseParser<R> resultParser) {
        Preconditions.checkArgument(resultParser != null, "resultParser must be not null");

        this.resultParser = resultParser;
    }


    @Override
    public R handle(final ResponseData responseData) throws IOException {
        if (responseData.getStatusCode() >= 300 || responseData.getStatusCode() < 200) {
            throw new HttpResponseException(responseData.getStatusCode(), "Response returned with error status code.");
        }

        return resultParser.parseResponse(responseData.getEntityContent());
    }
}
