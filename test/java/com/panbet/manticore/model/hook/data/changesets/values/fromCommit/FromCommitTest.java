package com.panbet.manticore.model.hook.data.changesets.values.fromCommit;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class FromCommitTest {
    private static final String id = "id";

    private static final String displayId = "displayId";


    @Test
    void testCorrectConstructor() {
        FromCommitImpl commit = new FromCommitImpl(id, displayId);
        Assertions.assertThat(commit.getId()).isEqualTo(id);
        Assertions.assertThat(commit.getDisplayId()).isEqualTo(displayId);
    }


    @Test
    void testNotIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new FromCommitImpl(null, displayId));
    }


    @Test
    void testNotDisplayIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new FromCommitImpl(id, null));
    }
}