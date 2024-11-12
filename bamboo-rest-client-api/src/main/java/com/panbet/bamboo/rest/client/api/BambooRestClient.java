package com.panbet.bamboo.rest.client.api;


import com.panbet.bamboo.rest.client.api.domain.ProjectPlanRestClient;
import com.panbet.bamboo.rest.client.api.domain.ProjectRestClient;
import com.panbet.bamboo.rest.client.api.domain.ResultRestClient;

import java.io.IOException;


public interface BambooRestClient extends AutoCloseable {
    ProjectRestClient getProjectClient();

    ProjectPlanRestClient getProjectPlanRestClient();

    ResultRestClient getResultRestClient();

    /**
     * Destroys this instance of Bamboo Rest Client.
     *
     * @throws java.io.IOException
     */
    void close() throws IOException;
}
