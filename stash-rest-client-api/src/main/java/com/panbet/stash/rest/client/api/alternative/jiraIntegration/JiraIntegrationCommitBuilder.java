package com.panbet.stash.rest.client.api.alternative.jiraIntegration;


import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;


public class JiraIntegrationCommitBuilder {
    private Commit commit;

    private Repository repository;


    public JiraIntegrationCommitBuilder setCommit(final Commit commit) {
        this.commit = commit;
        return this;
    }


    public JiraIntegrationCommitBuilder setRepository(final Repository repository) {
        this.repository = repository;
        return this;
    }


    public JiraIntegrationCommit build() {
        return new JiraIntegrationCommitImpl(commit, repository);
    }
}
