package com.panbet.stash.rest.client.api;


import com.atlassian.jira.rest.client.internal.async.DisposableHttpClient;

import java.net.URI;


/**
 * Factory for producing Atlassian REST API Client with selected authentication handler
 *
 * @since v0.0.1
 */
public interface StashRestClientFactory {
    /**
     * Creates an instance of RestClient with given Atlassian HttpClient.
     * Please note, that this com.atlassian.jira.rest.client.api has to be fully configured to do the request authentication.
     *
     * @param serverUri  - URI of Atlassian REST API instance.
     * @param httpClient - instance of Atlassian HttpClient.
     */
    StashRestClient create(final URI serverUri, final DisposableHttpClient httpClient);
}
