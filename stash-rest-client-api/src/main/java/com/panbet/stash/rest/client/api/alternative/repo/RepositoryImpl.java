package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.clone.Clone;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;

import java.net.URI;
import java.util.Objects;
import java.util.Optional;


//TODO - Fix all optional links, cloneUrls, etc.
//TODO - Now it's a temporary solution for new version of Bitbucket server.
public class RepositoryImpl implements Repository {
    private final String slug;

    private final Integer id;

    private final String name;

    private final String scmId;

    private final State state;

    private final String statusMessage;

    private final boolean isForkable;

    private final Project project;

    private final boolean isPublic;

    private final Link link;

    private final URI cloneUrl;

    private final ImmutableCollection<Self> self;

    private final ImmutableCollection<Clone> clone;


    RepositoryImpl(final String slug, final Integer id, final String name, final String scmId,
                   final State state, final String statusMessage, final boolean isForkable,
                   final Project project, final boolean isPublic, final Link link, final String cloneUrl,
                   final ImmutableCollection<Self> self, final ImmutableCollection<Clone> clone) {
        Preconditions.checkArgument(self != null, "self require not null");
        Preconditions.checkArgument(clone != null, "clone require not null");

        this.slug = slug;
        this.id = id;
        this.name = name;
        this.scmId = scmId;
        this.state = state;
        this.statusMessage = statusMessage;
        this.isForkable = isForkable;
        this.project = project;
        this.isPublic = isPublic;
        this.link = link;
        this.cloneUrl = URI.create(cloneUrl);
        this.self = self;
        this.clone = clone;
    }


    @Override
    public String getSlug() {
        return slug;
    }


    @Override
    public Integer getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getScmId() {
        return scmId;
    }


    @Override
    public State getState() {
        return state;
    }


    @Override
    public String getStatusMessage() {
        return statusMessage;
    }


    @Override
    public boolean isForkable() {
        return isForkable;
    }


    @Override
    public Project getProject() {
        return project;
    }


    @Override
    public boolean isPublic() {
        return isPublic;
    }


    @Override
    public Optional<Link> getLink() {
        return Optional.ofNullable(link);
    }


    @Override
    public Optional<URI> getCloneUrl() {
        return Optional.ofNullable(cloneUrl);
    }


    @Override
    public ImmutableCollection<Self> getSelf() {
        return self;
    }


    @Override
    public ImmutableCollection<Clone> getClone() {
        return clone;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Repository that = (RepositoryImpl) o;
        return Objects.equals(id, that.getId());
    }


    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
