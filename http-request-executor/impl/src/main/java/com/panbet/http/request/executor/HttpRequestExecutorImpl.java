package com.panbet.http.request.executor;


import com.google.common.base.Preconditions;
import com.panbet.http.request.executor.request.*;
import com.panbet.http.request.executor.response.EntityEnclosingResponseHandler;
import com.panbet.http.request.executor.response.ResponseHandlerException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;

import java.io.IOException;


/**
 * {@inheritDoc}
 * <p>
 * This implementation of {@link HttpRequestExecutor} executes
 * http requests using apache http client library.
 * <p>
 * For lib details, see <a href="http://hc.apache.org/">http://hc.apache.org/</a>
 */
public class HttpRequestExecutorImpl implements HttpRequestExecutor {
    private final CloseableHttpClient httpClient;

    private final RequestPreparator requestPreparator;


    public HttpRequestExecutorImpl(final CloseableHttpClient httpClient, final RequestPreparator requestPreparator) {
        Preconditions.checkArgument(httpClient != null, "httpClient must be not null");
        Preconditions.checkArgument(requestPreparator != null, "requestPreparator must be not null");

        this.httpClient = httpClient;
        this.requestPreparator = requestPreparator;
    }


    @Override
    public <R> R executeRequest(final Request request, final EntityEnclosingResponseHandler<R> responseHandler)
            throws RequestExecutorException {
        final WrappedRequest wrappedRequest = new WrappedSimpleRequest(request);

        return tryExecute(wrappedRequest, responseHandler);
    }


    @Override
    public <R> R executeRequest(final EntityEnclosingRequest request,
                                final EntityEnclosingResponseHandler<R> responseHandler) throws RequestExecutorException {
        final WrappedRequest wrappedRequest = new WrappedEntityEnclosingRequest(request);

        return tryExecute(wrappedRequest, responseHandler);
    }


    private <R> R tryExecute(final WrappedRequest wrappedRequest,
                             final EntityEnclosingResponseHandler<R> responseHandler) throws RequestExecutorException {
        try {
            final HttpUriRequest apacheRequest = wrappedRequest.prepareRequest(requestPreparator);

            return httpClient.execute(apacheRequest, new ApacheResponseHandler<>(responseHandler));
        } catch (final ResponseHandlerException e) {
            throw new ResponseHandlingException(e.getResponseStatus(), e);
        } catch (final Exception e) {
            throw new RequestExecutorException(e);
        }
    }


    @Override
    public void close() throws IOException {
        httpClient.close();
    }
}
