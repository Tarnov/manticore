package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;


public class ProjectBuilder {
    private String key;

    private Long id;

    private String name;

    private String description;

    private boolean isPublic;

    private ProjectType projectType;

    private Link link;

    private ImmutableCollection<Self> self;


    public ProjectBuilder setKey(final String key) {
        this.key = key;
        return this;
    }


    public ProjectBuilder setId(final Long id) {
        this.id = id;
        return this;
    }


    public ProjectBuilder setName(final String name) {
        this.name = name;
        return this;
    }


    public ProjectBuilder setDescription(final String description) {
        this.description = description;
        return this;
    }


    public ProjectBuilder setIsPublic(final boolean isPublic) {
        this.isPublic = isPublic;
        return this;
    }


    public ProjectBuilder setProjectType(final ProjectType projectType) {
        this.projectType = projectType;
        return this;
    }


    public ProjectBuilder setLink(final Link link) {
        this.link = link;
        return this;
    }


    public ProjectBuilder setSelf(final ImmutableCollection<Self> self) {
        this.self = self;
        return this;
    }


    public Project createProject() {
        return new ProjectImpl(key, id, name, description, isPublic, projectType, link, self);
    }
}