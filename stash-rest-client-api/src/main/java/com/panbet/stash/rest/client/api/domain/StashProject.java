package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.project.Project;
import com.atlassian.stash.project.ProjectType;
import com.atlassian.stash.project.ProjectVisitor;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;


/**
 * Complete information about single Stash project.
 *
 * @since v0.0.1
 */
public class StashProject implements Project, Serializable {
    private final String key;

    private final Long id;

    private final String name;

    private final String description;

    private final boolean isPublic;

    private final ProjectType type;

    private final Boolean isPersonal;


    public StashProject(final String key, @Nullable final Long id, final String name, final boolean isPublic,
                        final String description, final ProjectType type, final Boolean isPersonal) {
        this.key = key;
        this.id = id;
        this.name = name;
        this.description = description;
        this.isPublic = isPublic;
        this.type = type;
        this.isPersonal = isPersonal;
    }


    @Override
    public <T> T accept(final ProjectVisitor<T> projectVisitor) {
        throw new UnsupportedOperationException("Nothing to return");
    }


    @Override
    public String getDescription() {
        return description;
    }


    @Override
    public Integer getId() {
        return id == null ? null : id.intValue();
    }


    @Override
    public boolean isPublic() {
        return isPublic;
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getName() {
        return name;
    }


    @Nonnull
    @Override
    public ProjectType getType() {
        return type;
    }
}
