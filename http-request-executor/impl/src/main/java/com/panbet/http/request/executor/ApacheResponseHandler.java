package com.panbet.http.request.executor;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.response.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.io.IOException;


/**
 * {@inheritDoc}
 * <p>
 * This implementation is working with body enclosing responses only.
 * If response doesn't contain any body or if it is not meant
 * to contain any content(status codes 204 and 205) then
 * {@link ResponseHandlerException} will be thrown.
 * If body can not be parsed successfully, same exception will be thrown.
 */
public class ApacheResponseHandler<R> implements ResponseHandler<R> {
    private final EntityEnclosingResponseHandler<R> responseHandler;


    public ApacheResponseHandler(final EntityEnclosingResponseHandler<R> responseHandler) {
        Preconditions.checkArgument(responseHandler != null, "responseHandler must be not null");

        this.responseHandler = responseHandler;
    }


    @Override
    public R handleResponse(final HttpResponse response) throws IOException {
        final StatusLine statusLine = response.getStatusLine();

        if (statusLine.getStatusCode() == HttpStatus.SC_NO_CONTENT ||
                statusLine.getStatusCode() == HttpStatus.SC_RESET_CONTENT) {
            final ResponseStatus responseStatus = createResponseStatus(statusLine);

            throw new ResponseHandlerException(responseStatus, "Content is missing because response is not meant " +
                    "to contain any content due to status code.");
        }

        final HttpEntity entity = response.getEntity();
        if (entity == null) {
            final ResponseStatus responseStatus = createResponseStatus(statusLine);

            throw new ResponseHandlerException(responseStatus, "Response contains no content due to unknown reason.");
        }

        final ResponseData responseData = new ResponseDataImpl(statusLine.getStatusCode(), statusLine.getReasonPhrase(),
                entity.getContent());
        try {
            return responseHandler.handle(responseData);
        } catch (final Exception e) {
            final ResponseStatus responseStatus = createResponseStatus(statusLine);

            throw new ResponseHandlerException(responseStatus, "Unexpected exception while handling response. ", e);
        } finally {
            EntityUtils.consume(entity);
        }
    }


    private ResponseStatus createResponseStatus(final StatusLine statusLine) {
        return new ResponseStatusImpl(statusLine.getStatusCode(), statusLine.getReasonPhrase());
    }
}
