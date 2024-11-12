package com.panbet.manticore.model.hook.data.changesets.values.changes;


import com.panbet.manticore.model.hook.data.changesets.values.changes.values.ChangesValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;


class ChangesTest {
    private static final Long size = 1L;

    private static final Long limit = 1L;

    private static final Boolean isLastPage = true;

    private static final Collection<ChangesValue> values = new ArrayList<>();

    private static final Long start = 1L;


    @Test
    void testSuccess() {
        final ChangesBuilder changesBuilder = new ChangesBuilder();

        final Changes changes = changesBuilder
                .setSize(size)
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setValues(values)
                .setStart(start)
                .build();

        Assertions.assertThat(changes.getSize()).isEqualTo(size);
        Assertions.assertThat(changes.getLimit()).isEqualTo(limit);
        Assertions.assertThat(changes.isLastPage()).isEqualTo(isLastPage);
        Assertions.assertThat(changes.getValues()).isEqualTo(values);
        Assertions.assertThat(changes.getStart()).isEqualTo(start);
    }


    @Test
    void testSizeFail() {
        ChangesBuilder builder = new ChangesBuilder()
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setValues(values)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testLimitFail() {
        ChangesBuilder builder = new ChangesBuilder()
                .setSize(size)
                .setIsLastPage(isLastPage)
                .setValues(values)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIsLastPageFail() {
        ChangesBuilder builder = new ChangesBuilder()
                .setSize(size)
                .setLimit(limit)
                .setValues(values)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testValuesFail() {
        ChangesBuilder builder = new ChangesBuilder()
                .setSize(size)
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStartFail() {
        ChangesBuilder builder = new ChangesBuilder()
                .setSize(size)
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setValues(values);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }

}