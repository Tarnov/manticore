package com.panbet.manticore.model.hook.data.refChanges;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class RefChangesTest {
    private static final String refId = "refId";

    private static final String fromHash = "fromHash";

    private static final String toHash = "toHash";

    private static final RefChangeType type = RefChangeType.ADD;


    @Test
    void testSuccess() {
        final RefChangesBuilder refChangesBuilder = new RefChangesBuilder();

        final RefChanges refChanges = refChangesBuilder
                .setRefId(refId)
                .setFromHash(fromHash)
                .setToHash(toHash)
                .setType(type)
                .build();

        Assertions.assertThat(refChanges.getRefId()).isEqualTo(refId);
        Assertions.assertThat(refChanges.getFromHash()).isEqualTo(fromHash);
        Assertions.assertThat(refChanges.getToHash()).isEqualTo(toHash);
        Assertions.assertThat(refChanges.getType()).isEqualTo(type);
    }


    @Test
    void testRefIdFail() {
        RefChangesBuilder builder = new RefChangesBuilder()
                .setFromHash(fromHash)
                .setToHash(toHash)
                .setType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testFromHashFail() {
        RefChangesBuilder builder = new RefChangesBuilder()
                .setRefId(refId)
                .setToHash(toHash)
                .setType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToHashFail() {
        RefChangesBuilder builder = new RefChangesBuilder()
                .setRefId(refId)
                .setFromHash(fromHash)
                .setType(type);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testTypeFail() {
        RefChangesBuilder builder = new RefChangesBuilder()
                .setRefId(refId)
                .setFromHash(fromHash)
                .setToHash(toHash);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}