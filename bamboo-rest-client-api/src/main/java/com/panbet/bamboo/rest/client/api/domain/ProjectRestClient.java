package com.panbet.bamboo.rest.client.api.domain;

import com.atlassian.util.concurrent.Promise;

public interface ProjectRestClient {
    Promise<Iterable<BambooProject>> getAllProjects();
}
