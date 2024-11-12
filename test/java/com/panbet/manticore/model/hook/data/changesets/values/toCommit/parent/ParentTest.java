package com.panbet.manticore.model.hook.data.changesets.values.toCommit.parent;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ParentTest {
    private static final String id = "id";

    private static final String displayId = "displayId";


    @Test
    void testCorrectConstructor() {
        ParentImpl parent = new ParentImpl(id, displayId);
        Assertions.assertThat(parent.getId()).isEqualTo(id);
        Assertions.assertThat(parent.getDisplayId()).isEqualTo(displayId);
    }


    @Test
    void testNotIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ParentImpl(null, displayId));
    }


    @Test
    void testNotDisplayIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ParentImpl(id, null));
    }
}