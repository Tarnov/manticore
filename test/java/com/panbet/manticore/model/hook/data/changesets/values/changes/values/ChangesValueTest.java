package com.panbet.manticore.model.hook.data.changesets.values.changes.values;


import com.panbet.manticore.model.hook.data.changesets.link.Link;
import com.panbet.manticore.model.hook.data.changesets.links.Links;
import com.panbet.manticore.model.hook.data.changesets.values.changes.values.path.Path;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ChangesValueTest {
    private final String contentId = "contentId";

    private final Boolean executable = false;

    private final Long percentUnchanged = 1L;

    private final ChangesValueType type = ChangesValueType.ADD;

    private final NodeType nodeType = NodeType.DIRECTORY;

    private final Boolean srcExecutable = true;

    @Mock
    private Link link;

    @Mock
    private Links links;

    @Mock
    private Path path;


    @Test
    void testSuccess() {
        final ChangesValueBuilder changesValueBuilder = new ChangesValueBuilder();

        final ChangesValue changesValue = changesValueBuilder
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links)
                .setPath(path)
                .build();

        Assertions.assertThat(changesValue.getContentId()).isEqualTo(contentId);
        Assertions.assertThat(changesValue.getExecutable()).isEqualTo(executable);
        Assertions.assertThat(changesValue.getPercentUnchanged()).isEqualTo(percentUnchanged);
        Assertions.assertThat(changesValue.getType()).isEqualTo(type);
        Assertions.assertThat(changesValue.getNodeType()).isEqualTo(nodeType);
        Assertions.assertThat(changesValue.getSrcExecutable()).isEqualTo(srcExecutable);
        Assertions.assertThat(changesValue.getLink()).contains(link);
        Assertions.assertThat(changesValue.getLinks()).isEqualTo(links);
        Assertions.assertThat(changesValue.getPath()).isEqualTo(path);
    }


    @Test
    void testNullLink() {
        final ChangesValueBuilder changesValueBuilder = new ChangesValueBuilder();

        final ChangesValue changesValue = changesValueBuilder
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(null)
                .setLinks(links)
                .setPath(path)
                .build();

        Assertions.assertThat(changesValue.getContentId()).isEqualTo(contentId);
        Assertions.assertThat(changesValue.getExecutable()).isEqualTo(executable);
        Assertions.assertThat(changesValue.getPercentUnchanged()).isEqualTo(percentUnchanged);
        Assertions.assertThat(changesValue.getType()).isEqualTo(type);
        Assertions.assertThat(changesValue.getNodeType()).isEqualTo(nodeType);
        Assertions.assertThat(changesValue.getSrcExecutable()).isEqualTo(srcExecutable);
        Assertions.assertThat(changesValue.getLink()).isEmpty();
        Assertions.assertThat(changesValue.getLinks()).isEqualTo(links);
        Assertions.assertThat(changesValue.getPath()).isEqualTo(path);
    }


    @Test
    void testContentIdFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testExecutableFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testPercentUnchangedFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setExecutable(executable)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testTypeFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNodeTypeFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testSrcExecutableFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setLink(link)
                .setLinks(links)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testLinksFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setPath(path);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testPathFail() {
        ChangesValueBuilder builder = new ChangesValueBuilder()
                .setContentId(contentId)
                .setExecutable(executable)
                .setPercentUnchanged(percentUnchanged)
                .setType(type)
                .setNodeType(nodeType)
                .setSrcExecutable(srcExecutable)
                .setLink(link)
                .setLinks(links);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}