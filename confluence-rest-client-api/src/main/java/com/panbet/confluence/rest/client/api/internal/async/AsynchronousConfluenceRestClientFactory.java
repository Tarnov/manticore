package com.panbet.confluence.rest.client.api.internal.async;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.panbet.confluence.rest.client.api.internal.ConfluenceRestClient;
import com.panbet.confluence.rest.client.api.internal.ConfluenceRestClientFactory;

import java.net.URI;


public class AsynchronousConfluenceRestClientFactory implements ConfluenceRestClientFactory {
    @Override
    public ConfluenceRestClient create(final URI serverUri, final AuthenticationHandler authenticationHandler) {
        final DisposableHttpClient httpClient = new AsynchronousHttpClientFactory().createClient(serverUri,
                authenticationHandler);

        return new AsynchronousConfluenceRestClient(serverUri, httpClient);
    }


    @Override
    public ConfluenceRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username, final String password) {
        return create(serverUri, new BasicHttpAuthenticationHandler(username, password));
    }


    @Override
    public ConfluenceRestClient create(final URI serverUri, final HttpClient httpClient) {
        final DisposableHttpClient disposableHttpClient = new AsynchronousHttpClientFactory().createClient(httpClient);

        return new AsynchronousConfluenceRestClient(serverUri, disposableHttpClient);
    }
}
