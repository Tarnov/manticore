package com.panbet.manticore.util.bl.nimt.view;


import com.panbet.manticore.util.notes.Note;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Set;


@ExtendWith(MockitoExtension.class)
class NimtIssueBuilderTest {
    private static final URI self = UriBuilder.fromUri("self").build();

    private static final String key = "key";

    private static final Long id = 1L;

    private static final URI link = UriBuilder.fromUri("link").build();

    @Mock
    private NimtIssuePriority priority;

    @Mock
    private NimtIssueType issueType;

    @Mock
    private NimtIssueStatus status;

    private static final boolean readyToMerge = true;

    @Mock
    private Set<Note> notes;


    @Test
    void testSuccess() {
        final NimtIssue issue = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType)
                .build();

        Assertions.assertThat(issue.getSelf()).isEqualTo(self);
        Assertions.assertThat(issue.getKey()).isEqualTo(key);
        Assertions.assertThat(issue.getId()).isEqualTo(id);
        Assertions.assertThat(issue.getLink()).isEqualTo(link);
        Assertions.assertThat(issue.getStatus()).isEqualTo(status);
        Assertions.assertThat(issue.isReadyToMerge()).isEqualTo(readyToMerge);
        Assertions.assertThat(issue.getNotes()).isEqualTo(notes);
        Assertions.assertThat(issue.getPriority()).isEqualTo(priority);
        Assertions.assertThat(issue.getIssueType()).isEqualTo(issueType);
    }


    @Test
    void testSelfFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testKeyFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIdFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testLinkFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStatusFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testReadyToMergeFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setNotes(notes)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNotesFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setPriority(priority)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testPriorityFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setIssueType(issueType);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIssueTypeFail() {
        NimtIssueBuilder builder = new NimtIssueBuilder()
                .setSelf(self)
                .setKey(key)
                .setId(id)
                .setLink(link)
                .setStatus(status)
                .setReadyToMerge(readyToMerge)
                .setNotes(notes)
                .setPriority(priority);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}