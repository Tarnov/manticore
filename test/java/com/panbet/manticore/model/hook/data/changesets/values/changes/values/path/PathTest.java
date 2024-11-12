package com.panbet.manticore.model.hook.data.changesets.values.changes.values.path;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;


class PathTest {
    private static final Collection<String> components = new ArrayList<>();

    private static final String parent = "parent";

    private static final String name = "name";

    private static final String extension = "extension";

    private static final String toString = "toString";


    @Test
    void testSuccess() {
        final PathBuilder pathBuilder = new PathBuilder();

        final Path path = pathBuilder
                .setComponents(components)
                .setParent(parent)
                .setName(name)
                .setExtension(extension)
                .setToString(toString)
                .build();

        Assertions.assertThat(path.getComponents()).isEqualTo(components);
        Assertions.assertThat(path.getParent()).isEqualTo(parent);
        Assertions.assertThat(path.getName()).isEqualTo(name);
        Assertions.assertThat(path.getExtension()).contains(extension);
        Assertions.assertThat(path.getToString()).isEqualTo(toString);
    }


    @Test
    void testComponentsFail() {
        PathBuilder builder = new PathBuilder()
                .setParent(parent)
                .setName(name)
                .setExtension(extension)
                .setToString(toString);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testParentFail() {
        PathBuilder builder = new PathBuilder()
                .setComponents(components)
                .setName(name)
                .setExtension(extension)
                .setToString(toString);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNameFail() {
        PathBuilder builder = new PathBuilder()
                .setComponents(components)
                .setParent(parent)
                .setExtension(extension)
                .setToString(toString);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testExtensionFail() {
        PathBuilder builder = new PathBuilder()
                .setComponents(components)
                .setParent(parent)
                .setName(name)
                .setToString(toString);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToStringFail() {
        PathBuilder builder = new PathBuilder()
                .setComponents(components)
                .setParent(parent)
                .setName(name)
                .setExtension(extension);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}