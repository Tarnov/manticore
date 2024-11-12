package com.panbet.manticore.util.bl.workbench.mttb.view;


import com.panbet.stash.rest.client.core.entities.response.alternative.commit.Author;
import com.panbet.stash.rest.client.core.entities.response.alternative.repo.Repository;
import com.panbet.stash.rest.client.core.entities.response.domain.BranchInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.util.Collection;
import java.util.Date;


@ExtendWith(MockitoExtension.class)
class MttbCommitBuilderTest {
    private static final String id = "id";

    @Mock
    private Collection<BranchInfo> branches;

    @Mock
    private Repository repository;

    private static final String formattedTimeStamp = "formattedTimeStamp";

    private static final String howLongCommited = "howLongCommited";

    @Mock
    private Collection<String> branchNamesCollection;

    private static final boolean merge = true;

    @Mock
    private Author author;

    private static final String messageWithLinks = "messageWithLinks";

    private static final URI selfURI = UriBuilder.fromUri("selfURI").build();


    @Mock
    private Date authorTimestamp;


    @Test
    void testSuccess() {
        final MttbCommit commit = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI)
                .build();

        Assertions.assertThat(commit.getAuthor()).isEqualTo(author);
        Assertions.assertThat(commit.getAuthorTimestamp()).isEqualTo(authorTimestamp);
        Assertions.assertThat(commit.getBranches()).isEqualTo(branches);
        Assertions.assertThat(commit.getBranchNamesCollection()).isEqualTo(branchNamesCollection);
        Assertions.assertThat(commit.getFormattedTimeStamp()).isEqualTo(formattedTimeStamp);
        Assertions.assertThat(commit.howLongCommited()).isEqualTo(howLongCommited);
        Assertions.assertThat(commit.getId()).isEqualTo(id);
        Assertions.assertThat(commit.isMerge()).isEqualTo(merge);
        Assertions.assertThat(commit.getMessageWithLinks()).isEqualTo(messageWithLinks);
        Assertions.assertThat(commit.getRepository()).isEqualTo(repository);
        Assertions.assertThat(commit.getSelfURI()).isEqualTo(selfURI);
    }


    @Test
    void testAuthorFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testAuthorTimestampFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBranchesFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBranchNamesCollectionFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testFormattedTimeStampFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testHowLongCommittedFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIdFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testMessageWithLinksFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setRepository(repository)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testRepositoryFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setSelfURI(selfURI);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testSelfURIFail() {
        MttbCommitBuilder builder = new MttbCommitBuilder()
                .setAuthor(author)
                .setAuthorTimestamp(authorTimestamp)
                .setBranches(branches)
                .setBranchNamesCollection(branchNamesCollection)
                .setFormattedTimeStamp(formattedTimeStamp)
                .setHowLongCommited(howLongCommited)
                .setId(id)
                .setIsMerge(merge)
                .setMessageWithLinks(messageWithLinks)
                .setRepository(repository);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}