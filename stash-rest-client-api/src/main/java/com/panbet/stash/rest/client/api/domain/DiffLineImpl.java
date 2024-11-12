package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.content.ConflictMarker;
import com.atlassian.stash.content.DiffLine;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class DiffLineImpl implements DiffLine {
    private List<Long> commentIds;
    private ConflictMarker conflictMarker;
    private int destination;
    private String line;
    private int source;
    private boolean conflicting;
    private boolean truncated;


    public DiffLineImpl(List<Long> commentIds, ConflictMarker conflictMarker, int destination, String line,
                        int source, boolean conflicting, boolean truncated) {
        this.commentIds = commentIds;
        this.conflictMarker = conflictMarker;
        this.destination = destination;
        this.line = line;
        this.source = source;
        this.conflicting = conflicting;
        this.truncated = truncated;
    }


    @Nullable
    @Override
    public List<Long> getCommentIds() {
        return commentIds;
    }


    @Nullable
    @Override
    public ConflictMarker getConflictMarker() {
        return conflictMarker;
    }


    @Override
    public int getDestination() {
        return destination;
    }


    @Nonnull
    @Override
    public String getLine() {
        return line;
    }


    @Override
    public int getSource() {
        return source;
    }

    @Override
    public boolean isConflicting() {
        return conflicting;
    }


    @Override
    public boolean isTruncated() {
        return truncated;
    }
}
