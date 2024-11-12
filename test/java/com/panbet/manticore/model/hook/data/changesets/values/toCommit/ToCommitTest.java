package com.panbet.manticore.model.hook.data.changesets.values.toCommit;


import com.panbet.manticore.model.hook.data.changesets.values.toCommit.author.Author;
import com.panbet.manticore.model.hook.data.changesets.values.toCommit.parent.Parent;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@ExtendWith(MockitoExtension.class)
class ToCommitTest {
    private static final String id = "id";

    private static final String displayId = "displayId";

    @Mock
    private Author author;

    @Mock
    private Date authorTimestamp;

    private static final String message = "message";

    private static final Collection<Parent> parents = new ArrayList<>();


    @Test
    void testSuccess() {
        final ToCommitBuilder toCommitBuilder = new ToCommitBuilder();

        final ToCommit toCommit = toCommitBuilder
                .setId(id)
                .setDisplayId(displayId)
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setMessage(message)
                .setParents(parents)
                .build();

        Assertions.assertThat(toCommit.getId()).isEqualTo(id);
        Assertions.assertThat(toCommit.getDisplayId()).isEqualTo(displayId);
        Assertions.assertThat(toCommit.getAuthor()).isEqualTo(author);
        Assertions.assertThat(toCommit.getAuthorTimestamp()).isEqualTo(authorTimestamp);
        Assertions.assertThat(toCommit.getMessage()).isEqualTo(message);
        Assertions.assertThat(toCommit.getParents()).isEqualTo(parents);
    }


    @Test
    void testIdFail() {
        ToCommitBuilder builder = new ToCommitBuilder()
                .setDisplayId(displayId)
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setMessage(message)
                .setParents(parents);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testDisplayIdFail() {
        ToCommitBuilder builder = new ToCommitBuilder()
                .setId(id)
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setMessage(message)
                .setParents(parents);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testAuthorFail() {
        ToCommitBuilder builder = new ToCommitBuilder()
                .setId(id)
                .setDisplayId(displayId)
                .setAuthorTimestamp(authorTimestamp)
                .setMessage(message)
                .setParents(parents);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testAuthorTimestampFail() {
        ToCommitBuilder builder = new ToCommitBuilder()
                .setId(id)
                .setDisplayId(displayId)
                .setAuthor(author)
                .setMessage(message)
                .setParents(parents);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testMessageFail() {
        ToCommitBuilder builder = new ToCommitBuilder()
                .setId(id)
                .setDisplayId(displayId)
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setParents(parents);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testParentsFail() {
        ToCommitBuilder builder = new ToCommitBuilder()
                .setId(id)
                .setDisplayId(displayId)
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setMessage(message);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}