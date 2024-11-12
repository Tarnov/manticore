package com.panbet.stash.rest.client.api.domain;


import com.atlassian.stash.content.DiffHunk;
import com.atlassian.stash.content.DiffSegment;

import javax.annotation.Nonnull;
import java.util.List;


public class DiffHunkImpl implements DiffHunk {

    private final int destinationLine;

    private final int destinationSpan;

    private final List<DiffSegment> segments;

    private final int sourceLine;

    private final int sourceSpan;

    private final boolean truncated;


    public DiffHunkImpl(int destinationLine, int destinationSpan, List<DiffSegment> segments,
                        int sourceLine, int sourceSpan, boolean truncated) {
        this.destinationLine = destinationLine;
        this.destinationSpan = destinationSpan;
        this.segments = segments;
        this.sourceLine = sourceLine;
        this.sourceSpan = sourceSpan;
        this.truncated = truncated;
    }


    @Override
    public int getDestinationLine() {
        return destinationLine;
    }


    @Override
    public int getDestinationSpan() {
        return destinationSpan;
    }


    @Nonnull
    @Override
    public List<DiffSegment> getSegments() {
        return segments;
    }


    @Override
    public int getSourceLine() {
        return sourceLine;
    }


    @Override
    public int getSourceSpan() {
        return sourceSpan;
    }


    @Override
    public boolean isTruncated() {
        return truncated;
    }
}
