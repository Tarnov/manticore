package com.panbet.confluence.rest.client.api.internal.async;


import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;
import com.panbet.confluence.rest.client.api.domain.ContentRestClient;
import com.panbet.confluence.rest.client.api.internal.ConfluenceRestClient;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;


public class AsynchronousConfluenceRestClient implements ConfluenceRestClient {
    private final DisposableHttpClient httpClient;

    private final ContentRestClient contentRestClient;


    AsynchronousConfluenceRestClient(final URI serverUri, final DisposableHttpClient httpClient) {
        final URI baseUri = UriBuilder.fromUri(serverUri).path("/rest/api").build();

        contentRestClient = new AsynchronousConfluenceContentRestClient(baseUri, httpClient);

        this.httpClient = httpClient;
    }


    @Override
    public ContentRestClient getContentClient() {
        return contentRestClient;
    }


    @Override
    public void close() throws IOException {
        try {
            httpClient.destroy();
        } catch (final Exception e) {
            throw (e instanceof IOException) ? ((IOException) e) : new IOException(e);
        }
    }
}
