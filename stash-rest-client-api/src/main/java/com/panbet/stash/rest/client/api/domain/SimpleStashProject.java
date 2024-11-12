package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.project.Project;
import com.atlassian.stash.project.ProjectType;
import com.atlassian.stash.project.ProjectVisitor;

import javax.annotation.Nonnull;
import java.io.Serializable;


public class SimpleStashProject implements Project, Serializable {
    private static final String message;

    static {
        message = "This is SimpleStashProject only with 'key' property. For full implementation use StashProject class";
    }

    private final String key;


    public SimpleStashProject(final String key) {
        this.key = key;
    }


    @Override
    public <T> T accept(final ProjectVisitor<T> projectVisitor) {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public String getDescription() {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public Integer getId() {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public boolean isPublic() {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public String getKey() {
        return key;
    }


    @Override
    public String getName() {
        throw new UnsupportedOperationException(message);
    }


    @Nonnull
    @Override
    public ProjectType getType() {
        throw new UnsupportedOperationException(message);
    }
}
