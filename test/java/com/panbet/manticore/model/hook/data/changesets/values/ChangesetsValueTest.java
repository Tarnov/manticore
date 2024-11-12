package com.panbet.manticore.model.hook.data.changesets.values;


import com.panbet.manticore.model.hook.data.changesets.link.Link;
import com.panbet.manticore.model.hook.data.changesets.links.Links;
import com.panbet.manticore.model.hook.data.changesets.values.changes.Changes;
import com.panbet.manticore.model.hook.data.changesets.values.fromCommit.FromCommit;
import com.panbet.manticore.model.hook.data.changesets.values.toCommit.ToCommit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ChangesetsValueTest {
    @Mock
    private FromCommit fromCommit;

    @Mock
    private ToCommit toCommit;

    @Mock
    private Changes changes;

    @Mock
    private Link link;

    @Mock
    private Links links;


    @Test
    void testSuccess() {
        final ChangesetsValueBuilder changesetsValueBuilder = new ChangesetsValueBuilder();

        final ChangesetsValue changesetsValue = changesetsValueBuilder
                .setFromCommit(fromCommit)
                .setToCommit(toCommit)
                .setChanges(changes)
                .setLink(link)
                .setLinks(links)
                .build();

        Assertions.assertThat(changesetsValue.getFromCommit()).isEqualTo(fromCommit);
        Assertions.assertThat(changesetsValue.getToCommit()).isEqualTo(toCommit);
        Assertions.assertThat(changesetsValue.getChanges()).isEqualTo(changes);
        Assertions.assertThat(changesetsValue.getLink()).contains(link);
        Assertions.assertThat(changesetsValue.getLinks()).isEqualTo(links);
    }


    @Test
    void testNullLink() {
        final ChangesetsValueBuilder changesetsValueBuilder = new ChangesetsValueBuilder();

        final ChangesetsValue changesetsValue = changesetsValueBuilder
                .setFromCommit(fromCommit)
                .setToCommit(toCommit)
                .setChanges(changes)
                .setLink(null)
                .setLinks(links)
                .build();

        Assertions.assertThat(changesetsValue.getFromCommit()).isEqualTo(fromCommit);
        Assertions.assertThat(changesetsValue.getToCommit()).isEqualTo(toCommit);
        Assertions.assertThat(changesetsValue.getChanges()).isEqualTo(changes);
        Assertions.assertThat(changesetsValue.getLink()).isEmpty();
        Assertions.assertThat(changesetsValue.getLinks()).isEqualTo(links);
    }


    @Test
    void testFromCommitFail() {
        ChangesetsValueBuilder builder = new ChangesetsValueBuilder()
                .setToCommit(toCommit)
                .setChanges(changes)
                .setLink(link)
                .setLinks(links);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToCommitFail() {
        ChangesetsValueBuilder builder = new ChangesetsValueBuilder()
                .setFromCommit(fromCommit)
                .setChanges(changes)
                .setLink(link)
                .setLinks(links);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testChangesFail() {
        ChangesetsValueBuilder builder = new ChangesetsValueBuilder()
                .setFromCommit(fromCommit)
                .setToCommit(toCommit)
                .setLink(link)
                .setLinks(links);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testLinksFail() {
        ChangesetsValueBuilder builder = new ChangesetsValueBuilder()
                .setFromCommit(fromCommit)
                .setToCommit(toCommit)
                .setChanges(changes)
                .setLink(link);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}