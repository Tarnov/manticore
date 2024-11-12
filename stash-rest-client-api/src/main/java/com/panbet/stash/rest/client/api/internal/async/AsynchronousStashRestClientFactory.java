package com.panbet.stash.rest.client.api.internal.async;


import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.panbet.stash.rest.client.api.StashRestClient;
import com.panbet.stash.rest.client.api.StashRestClientFactory;

import java.net.URI;



public class AsynchronousStashRestClientFactory implements StashRestClientFactory {
    @Override
    public StashRestClient create(final URI serverUri, final DisposableHttpClient defaultHttpClient) {
        return new AsynchronousStashRestClient(serverUri, defaultHttpClient);
    }
}
