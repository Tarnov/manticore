package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.clone.Clone;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;


public class RepositoryImplBuilder {
    private String slug;

    private Integer id;

    private String name;

    private String scmId;

    private State state;

    private String statusMessage;

    private boolean isForkable;

    private Project project;

    private boolean isPublic;

    private Link link;

    private String cloneUrl;

    private ImmutableCollection<Self> self;

    private ImmutableCollection<Clone> clone;


    public RepositoryImplBuilder setSlug(final String slug) {
        this.slug = slug;
        return this;
    }


    public RepositoryImplBuilder setId(final Integer id) {
        this.id = id;
        return this;
    }


    public RepositoryImplBuilder setName(final String name) {
        this.name = name;
        return this;
    }


    public RepositoryImplBuilder setScmId(final String scmId) {
        this.scmId = scmId;
        return this;
    }


    public RepositoryImplBuilder setState(final State state) {
        this.state = state;
        return this;
    }


    public RepositoryImplBuilder setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }


    public RepositoryImplBuilder setIsForkable(final boolean isForkable) {
        this.isForkable = isForkable;
        return this;
    }


    public RepositoryImplBuilder setProject(final Project project) {
        this.project = project;
        return this;
    }


    public RepositoryImplBuilder setIsPublic(final boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }


    public RepositoryImplBuilder setLink(final Link link) {
        this.link = link;
        return this;
    }


    public RepositoryImplBuilder setCloneUrl(final String cloneUrl) {
        this.cloneUrl = cloneUrl;
        return this;
    }


    public RepositoryImplBuilder setSelf(final ImmutableCollection<Self> self) {
        this.self = self;
        return this;
    }


    public RepositoryImplBuilder setClone(final ImmutableCollection<Clone> clone) {
        this.clone = clone;
        return this;
    }


    public RepositoryImpl createRepositoryImpl() {
        return new RepositoryImpl(slug, id, name, scmId, state, statusMessage, isForkable, project, isPublic, link,
                cloneUrl, self, clone);
    }
}