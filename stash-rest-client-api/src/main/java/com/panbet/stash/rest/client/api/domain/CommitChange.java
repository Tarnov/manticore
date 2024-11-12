package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.content.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;


public class CommitChange implements Change {
    private final Conflict conflict;

    private final String contentId;

    private final Boolean executable;

    private final ContentTreeNode.Type nodeType;

    private final Path path;

    private final int percentUnchanged;

    private final Boolean srcExecutable;

    private final Path srcPath;

    private final ChangeType changeType;

    private final AttributeMap attributes = AttributeMap.EMPTY;

    private final Set<String> attributeValues = new HashSet<>();


    public CommitChange(Conflict conflict, String contentId, Boolean executable, ContentTreeNode.Type nodeType,
                        Path path, int percentUnchanged, Boolean srcExecutable, Path srcPath, ChangeType changeType) {
        this.conflict = conflict;
        this.contentId = contentId;
        this.executable = executable;
        this.nodeType = nodeType;
        this.path = path;
        this.percentUnchanged = percentUnchanged;
        this.srcExecutable = srcExecutable;
        this.srcPath = srcPath;
        this.changeType = changeType;
    }


    @Nullable
    @Override
    public Conflict getConflict() {
        return conflict;
    }


    @Nullable
    @Override
    public String getContentId() {
        return contentId;
    }


    @Nullable
    @Override
    public Boolean getExecutable() {
        return executable;
    }


    @Nonnull
    @Override
    public ContentTreeNode.Type getNodeType() {
        return nodeType;
    }


    @Nonnull
    @Override
    public Path getPath() {
        return path;
    }


    @Override
    public int getPercentUnchanged() {
        return percentUnchanged;
    }


    @Nullable
    @Override
    public Boolean getSrcExecutable() {
        return srcExecutable;
    }


    @Nullable
    @Override
    public Path getSrcPath() {
        return srcPath;
    }


    @Nonnull
    @Override
    public ChangeType getType() {
        return changeType;
    }


    @Nonnull
    @Override
    public AttributeMap getAttributes() {
        return attributes;
    }


    @Nonnull
    @Override
    public Set<String> getAttributeValues(String s) {
        return attributeValues;
    }
}
