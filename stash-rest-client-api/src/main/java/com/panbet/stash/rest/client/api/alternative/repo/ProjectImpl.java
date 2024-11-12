package com.panbet.stash.rest.client.api.alternative.repo;


import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.panbet.stash.rest.client.api.alternative.link.Link;
import com.panbet.stash.rest.client.api.alternative.links.self.Self;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;


public class ProjectImpl implements Project {
    private final String key;

    private final Long id;

    private final String name;

    private final String description;

    private final boolean isPublic;

    private final ProjectType type;

    private final Link link;

    private final ImmutableCollection<Self> self;


    ProjectImpl(final String key, final Long id, final String name, final String description,
                final boolean isPublic, final ProjectType projectType, final Link link,
                final ImmutableCollection<Self> self) {
        Preconditions.checkArgument(StringUtils.isNotBlank(key), "Key require not blank");
        Preconditions.checkArgument(id != null, "id require not null");
        Preconditions.checkArgument(StringUtils.isNotBlank(name), "Name require not blank");
        Preconditions.checkArgument(projectType != null, "ProjectType require not null");
        Preconditions.checkArgument(self != null, "Links require not null");

        this.key = key;
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.type = projectType;
        this.link = link;
        this.self = self;
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public Long getId() {
        return id;
    }


    @Override
    public String getName() {
        return name;
    }


    @Override
    public Optional<String> getDescription() {
        return description == null ? Optional.empty() : Optional.of(description);
    }


    @Override
    public boolean isPublic() {
        return isPublic;
    }


    @Override
    public ProjectType getType() {
        return type;
    }


    @Override
    public Optional<Link> getLink() {
        return Optional.ofNullable(link);
    }


    @Override
    public ImmutableCollection<Self> getSelf() {
        return self;
    }
}
