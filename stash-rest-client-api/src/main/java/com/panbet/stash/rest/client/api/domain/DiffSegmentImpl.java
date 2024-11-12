package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.content.DiffLine;
import com.atlassian.stash.content.DiffSegment;
import com.atlassian.stash.content.DiffSegmentType;

import javax.annotation.Nonnull;
import java.util.List;


public class DiffSegmentImpl implements DiffSegment {
    private final List<DiffLine> lines;

    private final DiffSegmentType type;

    private final boolean truncated;


    public DiffSegmentImpl(final List<DiffLine> lines, final DiffSegmentType type, final boolean truncated) {
        this.lines = lines;
        this.type = type;
        this.truncated = truncated;
    }


    @Nonnull
    @Override
    public List<DiffLine> getLines() {
        return lines;
    }


    @Nonnull
    @Override
    public DiffSegmentType getType() {
        return type;
    }


    @Override
    public boolean isTruncated() {
        return truncated;
    }
}
