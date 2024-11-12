package com.panbet.stash.rest.client.api.alternative.commit;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Date;


public class CommitImpl implements Commit {
    private final String id;

    private final String displayId;

    private final MinimalAuthor minimalAuthor;

    private final Date authorTimestamp;

    private final String message;

    private final Collection<MinimalParent> parents;

    private final ImmutableSet<String> jiraKeys;


    CommitImpl(final String id, final String displayId, final MinimalAuthor minimalAuthor, final Date authorTimestamp,
               final String message, final Collection<MinimalParent> parents, final ImmutableSet<String> jiraKeys) {
        Preconditions.checkArgument(StringUtils.isNotBlank(id), "Id require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(displayId), "DisplayId require not null");
        Preconditions.checkArgument(minimalAuthor != null, "Author require not null");
        Preconditions.checkArgument(authorTimestamp != null, "AuthorTimestamp require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(message), "Message require not null");
        Preconditions.checkArgument(parents != null, "Parents require not null");
        Preconditions.checkArgument(jiraKeys != null, "JiraKeys require not null");
        this.id = id;
        this.displayId = displayId;
        this.minimalAuthor = minimalAuthor;
        this.authorTimestamp = authorTimestamp;
        this.message = message;
        this.parents = parents;
        this.jiraKeys = jiraKeys;
    }


    public String getId() {
        return id;
    }


    public String getDisplayId() {
        return displayId;
    }


    public MinimalAuthor getMinimalAuthor() {
        return minimalAuthor;
    }


    public Date getAuthorTimestamp() {
        return authorTimestamp;
    }


    public String getMessage() {
        return message;
    }


    public Collection<MinimalParent> getParents() {
        return parents;
    }


    public ImmutableSet<String> getJiraKeys() {
        return jiraKeys;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Commit)) {
            return false;
        }

        final Commit commit = (Commit) o;

        return !(id != null ? !id.equals(commit.getId()) : commit.getId() != null);

    }


    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
