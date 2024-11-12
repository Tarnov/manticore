package com.panbet.manticore.util.atlassian.confluence;


import com.atlassian.confluence.api.model.content.Content;
import com.atlassian.confluence.api.model.content.ContentRepresentation;
import com.atlassian.confluence.api.model.content.ContentType;
import com.atlassian.confluence.api.model.content.Space;
import com.panbet.manticore.util.atlassian.confluence.domain.SimpleContentBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class SimpleContentBuilderTest {
    private static final String type = "page";

    private static final String space = "space";

    private static final String title = "title";

    private static final String body = "body";

    private static final String ancestorId = "12345";


    @Test
    void testBuildAllParameters() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(title)
                .setSpace(space)
                .setBody(body)
                .setAncestorId(ancestorId);

        Content content = builder.build();

        Assertions.assertThat(content.getType()).isEqualTo(ContentType.PAGE);
        Assertions.assertThat(Space.getSpaceKey(content.getSpaceRef())).isEqualTo(space);
        Assertions.assertThat(content.getTitle()).isEqualTo(title);
        Assertions.assertThat(content.getBody().get(ContentRepresentation.STORAGE).getValue()).isEqualTo(body);
        Assertions.assertThat(content.getAncestors()).hasSize(1);
        Assertions.assertThat(content.getAncestors().get(0).getId().asLong()).isEqualTo(Long.parseLong(ancestorId));
    }


    @Test
    void testBuildEmptyType() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType("")
                .setTitle(title)
                .setSpace(space)
                .setBody(body)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildNullType() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(null)
                .setTitle(title)
                .setSpace(space)
                .setBody(body)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildEmptyTitle() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle("")
                .setSpace(space)
                .setBody(body)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildNullTitle() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(null)
                .setSpace(space)
                .setBody(body)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildEmptySpace() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(title)
                .setSpace("")
                .setBody(body)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildNullSpace() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(title)
                .setSpace(null)
                .setBody(body)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildNullBody() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(title)
                .setSpace(space)
                .setBody(null)
                .setAncestorId(ancestorId);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildNullAncestors() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(title)
                .setSpace(space)
                .setBody(body)
                .setAncestorId(null);

        Content content = builder.build();

        Assertions.assertThat(content.getType()).isEqualTo(ContentType.PAGE);
        Assertions.assertThat(Space.getSpaceKey(content.getSpaceRef())).isEqualTo(space);
        Assertions.assertThat(content.getTitle()).isEqualTo(title);
        Assertions.assertThat(content.getBody().get(ContentRepresentation.STORAGE).getValue()).isEqualTo(body);
        Assertions.assertThat(content.getAncestors()).isEmpty();
        Assertions.assertThat(content.getAncestors()).hasToString("null (CollapsedList)");
    }


    @Test
    void testBuildEmptyAncestors() {
        SimpleContentBuilder builder = new SimpleContentBuilder()
                .setType(type)
                .setTitle(title)
                .setSpace(space)
                .setBody(body)
                .setAncestorId("");

        Content content = builder.build();

        Assertions.assertThat(content.getType()).isEqualTo(ContentType.PAGE);
        Assertions.assertThat(Space.getSpaceKey(content.getSpaceRef())).isEqualTo(space);
        Assertions.assertThat(content.getTitle()).isEqualTo(title);
        Assertions.assertThat(content.getBody().get(ContentRepresentation.STORAGE).getValue()).isEqualTo(body);
        Assertions.assertThat(content.getAncestors()).isEmpty();
        Assertions.assertThat(content.getAncestors()).hasToString("null (CollapsedList)");
    }
}
