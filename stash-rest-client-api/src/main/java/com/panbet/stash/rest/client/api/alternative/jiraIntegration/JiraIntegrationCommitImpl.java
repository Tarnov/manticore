package com.panbet.stash.rest.client.api.alternative.jiraIntegration;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.commit.MinimalAuthor;
import com.panbet.stash.rest.client.api.alternative.commit.MinimalParent;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;

import java.util.Collection;
import java.util.Date;


public class JiraIntegrationCommitImpl implements JiraIntegrationCommit {
    private final Commit commit;

    private final Repository repository;


    JiraIntegrationCommitImpl(final Commit commit, final Repository repository) {
        Preconditions.checkArgument(commit != null, "commit require not null");
        Preconditions.checkArgument(repository != null, "repository require not null");

        this.commit = commit;
        this.repository = repository;
    }


    @Override
    public Repository getRepository() {
        return repository;
    }


    @Override
    public String getId() {
        return commit.getId();
    }


    @Override
    public String getDisplayId() {
        return commit.getDisplayId();
    }


    @Override
    public MinimalAuthor getMinimalAuthor() {
        return commit.getMinimalAuthor();
    }


    @Override
    public Date getAuthorTimestamp() {
        return commit.getAuthorTimestamp();
    }


    @Override
    public String getMessage() {
        return commit.getMessage();
    }


    @Override
    public Collection<MinimalParent> getParents() {
        return commit.getParents();
    }


    @Override
    public ImmutableSet<String> getJiraKeys() {
        return commit.getJiraKeys();
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commit)) {
            return false;
        }

        final Commit that = (Commit) o;

        return !(commit.getId() != null ? !commit.getId().equals(that.getId()) : that.getId() != null);

    }


    @Override
    public int hashCode() {
        return commit != null ? commit.hashCode() : 0;
    }
}
