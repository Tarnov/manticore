package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.collect.ImmutableSet;

import java.util.Collection;
import java.util.Date;


public class CommitImplBuilder {
    private String id;

    private String displayId;

    private MinimalAuthor minimalAuthor;

    private Date authorTimestamp;

    private String message;

    private Collection<MinimalParent> parents;

    private ImmutableSet<String> jiraKeys;


    public CommitImplBuilder setId(final String id) {
        this.id = id;
        return this;
    }


    public CommitImplBuilder setDisplayId(final String displayId) {
        this.displayId = displayId;
        return this;
    }


    public CommitImplBuilder setAuthor(final MinimalAuthor minimalAuthor) {
        this.minimalAuthor = minimalAuthor;
        return this;
    }


    public CommitImplBuilder setAuthorTimestamp(final Date authorTimestamp) {
        this.authorTimestamp = authorTimestamp;
        return this;
    }


    public CommitImplBuilder setMessage(final String message) {
        this.message = message;
        return this;
    }


    public CommitImplBuilder setParents(final Collection<MinimalParent> parents) {
        this.parents = parents;
        return this;
    }


    public CommitImplBuilder setJiraKeys(final ImmutableSet<String> jiraKeys) {
        this.jiraKeys = jiraKeys;
        return this;
    }


    public CommitImpl createCommitImpl() {
        return new CommitImpl(id, displayId, minimalAuthor, authorTimestamp, message, parents, jiraKeys);
    }
}