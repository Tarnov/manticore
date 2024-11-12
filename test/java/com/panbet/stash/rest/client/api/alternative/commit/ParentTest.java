package com.panbet.stash.rest.client.api.alternative.commit;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ParentTest {
    private final String id = "id";

    private final String displayId = "displayId";

    @Test
    void testCorrectConstructor() {
        Assertions.assertThat(new MinimalParent(id, displayId)).isNotNull();
    }


    @Test
    void testNotIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MinimalParent(null, displayId));
    }


    @Test
    void testNotDisplayIdConstructor() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new MinimalParent(id, null));
    }
}