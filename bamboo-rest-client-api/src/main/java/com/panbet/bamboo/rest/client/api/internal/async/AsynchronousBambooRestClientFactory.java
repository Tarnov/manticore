package com.panbet.bamboo.rest.client.api.internal.async;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;
import com.atlassian.jira.rest.client.auth.BasicHttpAuthenticationHandler;
import com.atlassian.jira.rest.client.internal.async.AsynchronousHttpClientFactory;
import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.panbet.bamboo.rest.client.api.BambooRestClient;
import com.panbet.bamboo.rest.client.api.BambooRestClientFactory;
import com.panbet.bamboo.rest.client.api.internal.BambooConfiguredExecutor;

import java.net.URI;

public class AsynchronousBambooRestClientFactory implements BambooRestClientFactory {
    public AsynchronousBambooRestClientFactory() {
    }

    public AsynchronousBambooRestClientFactory(int threads) {
        BambooConfiguredExecutor.THREADS = threads;
    }

    @Override
    public BambooRestClient create(URI serverUri, AuthenticationHandler authenticationHandler) {
        final DisposableHttpClient httpClient = new AsynchronousHttpClientFactory()
                .createClient(serverUri, authenticationHandler);
        return new AsynchronousBambooRestClient(serverUri, httpClient);
    }

    @Override
    public BambooRestClient createWithBasicHttpAuthentication(URI serverUri, String username, String password) {
        return create(serverUri, new BasicHttpAuthenticationHandler(username, password));
    }

    @Override
    public BambooRestClient create(URI serverUri, HttpClient httpClient) {
        final DisposableHttpClient disposableHttpClient = new AsynchronousHttpClientFactory().createClient(httpClient);
        return new AsynchronousBambooRestClient(serverUri, disposableHttpClient);
    }
}
