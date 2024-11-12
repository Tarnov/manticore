package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.project.Project;
import com.atlassian.stash.repository.Repository;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;


public class StashRepo implements Repository, Serializable {
    private final String slug;

    private final Integer id;

    private final String name;

    private final String scmId;

    private final State state;

    private final String statusMessage;

    private final boolean isForkable;

    private final boolean isPublic;

    private final Project project;


    public StashRepo(final String slug, final Integer id, final String name, final String scmId,
                     final State state, final String statusMessage, final boolean isForkable, final boolean isPublic,
                     final Project project) {
        this.slug = slug;
        this.id = id;
        this.name = name;
        this.scmId = scmId;
        this.state = state;
        this.statusMessage = statusMessage;
        this.isForkable = isForkable;
        this.isPublic = isPublic;
        this.project = project;
    }


    @Nonnull
    @Override
    public String getHierarchyId() {
        throw new UnsupportedOperationException("Nothing to return");
    }


    @Nullable
    @Override
    public Integer getId() {
        return id;
    }


    @Nonnull
    @Override
    public String getName() {
        return name;
    }


    @Nullable
    @Override
    public Repository getOrigin() {
        return null;
    }


    @Nonnull
    @Override
    public Project getProject() {
        return project;
    }


    @Nonnull
    @Override
    public String getScmId() {
        return scmId;
    }


    @Nonnull
    @Override
    public String getSlug() {
        return slug;
    }


    @Nonnull
    @Override
    public State getState() {
        return state;
    }


    @Nonnull
    @Override
    public String getStatusMessage() {
        return statusMessage;
    }


    @Override
    public boolean isFork() {
        throw new UnsupportedOperationException("Nothing to return");
    }


    @Override
    public boolean isForkable() {
        return isForkable;
    }


    @Override
    public boolean isPublic() {
        return isPublic;
    }
}
