package com.panbet.stash.rest.client.api.alternative.jiraIntegration;


import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;


public interface JiraIntegrationCommit extends Commit {
    Repository getRepository();
}
