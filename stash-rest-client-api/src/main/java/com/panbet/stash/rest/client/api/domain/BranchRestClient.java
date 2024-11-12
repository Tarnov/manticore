package com.panbet.stash.rest.client.api.domain;


import com.atlassian.util.concurrent.Promise;

import java.util.Collection;


public interface BranchRestClient {
    Promise<Collection<BranchInfo>> getBranchesByCommitId(final String commitId, final String project,
                                                          final String repo);

    Promise<Collection<BranchInfo>> getBranches(final String project, final String repo);
}
