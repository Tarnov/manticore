package com.panbet.bamboo.rest.client.api;

import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;

import java.net.URI;

public interface BambooRestClientFactory {
    BambooRestClient create(final URI serverUri, final AuthenticationHandler authenticationHandler);

    /**
     * Creates an instance of RestClient with default HttpClient settings. HttpClient will conduct a
     * basic authentication for given credentials.
     *
     * @param serverUri - URI or Atlassian REST API instance.
     * @param username  - username of the user used to log in to Atlassian REST API.
     * @param password  - password of the user used to log in to Atlassian REST API.
     */
    BambooRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username, final String password);

    /**
     * Creates an instance of RestClient with given Atlassian HttpClient.
     * Please note, that this com.atlassian.jira.rest.client.api has to be fully configured to do the request authentication.
     *
     * @param serverUri  - URI of Atlassian REST API instance.
     * @param httpClient - instance of Atlassian HttpClient.
     */
    BambooRestClient create(final URI serverUri, final HttpClient httpClient);
}
