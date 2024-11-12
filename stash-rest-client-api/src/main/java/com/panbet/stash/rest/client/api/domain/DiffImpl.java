package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.content.AttributeMap;
import com.atlassian.stash.content.Diff;
import com.atlassian.stash.content.DiffHunk;
import com.atlassian.stash.content.Path;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DiffImpl implements Diff {
    private Path destination;
    private List<DiffHunk> hunks;
    private Path source;
    private boolean binary;
    private boolean truncated;
    private AttributeMap attributes;


    public DiffImpl(Path destination, List<DiffHunk> hunks, Path source, boolean binary, boolean truncated) {
        this.destination = destination;
        this.hunks = hunks;
        this.source = source;
        this.binary = binary;
        this.truncated = truncated;
        this.attributes = AttributeMap.EMPTY;
    }


    @Nullable
    @Override
    public Path getDestination() {
        return destination;
    }


    @Nonnull
    @Override
    public List<DiffHunk> getHunks() {
        return hunks;
    }


    @Nullable
    @Override
    public Path getSource() {
        return source;
    }


    @Override
    public boolean isBinary() {
        return binary;
    }


    @Override
    public boolean isTruncated() {
        return truncated;
    }


    @Nonnull
    @Override
    public AttributeMap getAttributes() {
        return attributes;
    }


    @Nonnull
    @Override
    public Set<String> getAttributeValues(String s) {
        return attributes.getOrDefault(s, new HashSet<>());
    }
}
