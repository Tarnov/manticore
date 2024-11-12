package com.panbet.confluence.rest.client.api.internal;


import com.atlassian.httpclient.api.HttpClient;
import com.atlassian.jira.rest.client.api.AuthenticationHandler;

import java.net.URI;


public interface ConfluenceRestClientFactory {
    ConfluenceRestClient create(final URI serverUri, final AuthenticationHandler authenticationHandler);


    ConfluenceRestClient createWithBasicHttpAuthentication(final URI serverUri, final String username,
                                                           final String password);


    ConfluenceRestClient create(final URI serverUri, final HttpClient httpClient);
}
