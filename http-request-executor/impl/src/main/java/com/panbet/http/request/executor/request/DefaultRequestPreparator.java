package com.panbet.http.request.executor.request;


import com.panbet.http.request.executor.EntityEnclosingHttpRequest;
import com.panbet.http.request.executor.NoEntityHttpRequest;
import com.panbet.http.request.executor.headers.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;


public class DefaultRequestPreparator implements RequestPreparator {
    @Override
    public HttpUriRequest prepareRequest(final Request data) {
        final HttpUriRequest request = createNoEntityHttpRequest(data);
        setRequestHeaders(request, data.getHeaders());

        return request;
    }


    private HttpUriRequest createNoEntityHttpRequest(final Request request) {
        return new NoEntityHttpRequest(request);
    }


    @Override
    public HttpUriRequest prepareRequest(final EntityEnclosingRequest data) throws IOException {
        final HttpEntityEnclosingRequestBase request = createEntityEnclosingHttpRequest(data);
        setRequestHeaders(request, data.getHeaders());

        request.setEntity(createHttpEntity(data.getEntity()));

        return request;
    }


    private HttpEntityEnclosingRequestBase createEntityEnclosingHttpRequest(final Request request) {
        return new EntityEnclosingHttpRequest(request);
    }


    private HttpEntity createHttpEntity(final String content) throws UnsupportedEncodingException {
        return new StringEntity(content);
    }


    private void setRequestHeaders(final HttpRequest request, final Collection<Header> headers) {
        headers.forEach(header -> request.setHeader(header.getName(), header.getValue()));
    }
}
