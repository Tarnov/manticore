package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.project.Project;
import com.atlassian.stash.repository.Repository;
import com.google.common.base.Preconditions;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.Serializable;


public class SimpleStashRepo implements Repository, Serializable {
    private static final String message;

    static {
        message = "This is SimpleStashRepo only with 'project', 'name', 'slug' properties. For full implementation " +
                "use StashRepo class";
    }

    private final Project project;

    private final String name;

    private final String slug;


    public SimpleStashRepo(final String repoName, final String projectName) {
        Preconditions.checkArgument(StringUtils.isNotBlank(repoName), "repoName require not blank");
        Preconditions.checkArgument(StringUtils.isNotBlank(projectName), "projectName require not blank");

        slug = repoName.toLowerCase();
        name = firstCharToUpperCaseOtherToLower(repoName);
        project = new SimpleStashProject(projectName);
    }


    private String firstCharToUpperCaseOtherToLower(final String name) {
        final String nameLowCase = name.toLowerCase();

        final String substring = nameLowCase.substring(0, 1);

        return nameLowCase.replaceFirst("^" + substring, substring.toUpperCase());
    }


    @Nonnull
    @Override
    public String getHierarchyId() {
        throw new UnsupportedOperationException(message);
    }


    @Nullable
    @Override
    public Integer getId() {
        throw new UnsupportedOperationException(message);
    }


    @Nonnull
    @Override
    public String getName() {
        return name;
    }


    @Nullable
    @Override
    public Repository getOrigin() {
        throw new UnsupportedOperationException(message);
    }


    @Nonnull
    @Override
    public Project getProject() {
        return project;
    }


    @Nonnull
    @Override
    public String getScmId() {
        throw new UnsupportedOperationException(message);
    }


    @Nonnull
    @Override
    public String getSlug() {
        return slug;
    }


    @Nonnull
    @Override
    public State getState() {
        throw new UnsupportedOperationException(message);
    }


    @Nonnull
    @Override
    public String getStatusMessage() {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public boolean isFork() {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public boolean isForkable() {
        throw new UnsupportedOperationException(message);
    }


    @Override
    public boolean isPublic() {
        throw new UnsupportedOperationException(message);
    }
}
