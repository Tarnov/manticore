package com.panbet.stash.rest.client.api.domain;


import com.atlassian.util.concurrent.Promise;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;

import java.util.Collection;


public interface ReposRestClient {
    Promise<Repository> getRepository(final String projectName, final String repoName);

    Promise<Collection<Repository>> getRepositories(final String projectName);
}
