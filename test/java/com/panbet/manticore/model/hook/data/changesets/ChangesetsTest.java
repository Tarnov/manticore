package com.panbet.manticore.model.hook.data.changesets;


import com.panbet.manticore.model.hook.data.changesets.values.ChangesetsValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;


class ChangesetsTest {
    private static final Long size = 1L;

    private static final Long limit = 1L;

    private static final Boolean isLastPage = true;

    private static final Collection<ChangesetsValue> values = new ArrayList<>();

    private static final Long start = 1L;


    @Test
    void testSuccess() {
        final ChangesetsBuilder changesetsBuilder = new ChangesetsBuilder();

        final Changesets changesets = changesetsBuilder
                .setSize(size)
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setValues(values)
                .setStart(start)
                .build();

        Assertions.assertThat(changesets.getSize()).isEqualTo(size);
        Assertions.assertThat(changesets.getLimit()).isEqualTo(limit);
        Assertions.assertThat(changesets.isLastPage()).isEqualTo(isLastPage);
        Assertions.assertThat(changesets.getValues()).isEqualTo(values);
        Assertions.assertThat(changesets.getStart()).isEqualTo(start);
    }


    @Test
    void testSizeFail() {
        ChangesetsBuilder builder = new ChangesetsBuilder()
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setValues(values)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testLimitFail() {
        ChangesetsBuilder builder = new ChangesetsBuilder()
                .setSize(size)
                .setIsLastPage(isLastPage)
                .setValues(values)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIsLastPageFail() {
        ChangesetsBuilder builder = new ChangesetsBuilder()
                .setSize(size)
                .setLimit(limit)
                .setValues(values)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testValuesFail() {
        ChangesetsBuilder builder = new ChangesetsBuilder()
                .setSize(size)
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setStart(start);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStartFail() {
        ChangesetsBuilder builder = new ChangesetsBuilder()
                .setSize(size)
                .setLimit(limit)
                .setIsLastPage(isLastPage)
                .setValues(values);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}