package com.panbet.manticore.model.hook.data;


import com.panbet.manticore.model.hook.data.changesets.Changesets;
import com.panbet.manticore.model.hook.data.refChanges.RefChanges;
import com.panbet.manticore.model.hook.data.repo.Repository;
import org.assertj.core.api.Assertions;
import org.codehaus.jettison.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;


@ExtendWith(MockitoExtension.class)
class HookDataTest {
    private final Long id = 1L;

    @Mock
    private Repository repository;

    private final Collection<RefChanges> refChanges = new ArrayList<>();

    @Mock
    private Changesets changesets;

    @Mock
    private JSONObject jsonObject;


    @Test
    void testSuccess() {
        final HookData hookData = new HookDataImplBuilder()
                .setId(id)
                .setRepository(repository)
                .setRefChanges(refChanges)
                .setChangesets(changesets)
                .setJsonObject(jsonObject)
                .build();

        Assertions.assertThat(hookData.getId()).isEqualTo(id);
        Assertions.assertThat(hookData.getChangesets()).isEqualTo(changesets);
        Assertions.assertThat(hookData.getRefChanges()).isEqualTo(refChanges);
        Assertions.assertThat(hookData.getRepository()).isEqualTo(repository);
        Assertions.assertThat(hookData.getJSONObject()).isEqualTo(jsonObject);
    }


    @Test
    void testIdFail() {
        HookDataImplBuilder builder = new HookDataImplBuilder()
                .setId(null)
                .setRepository(repository)
                .setRefChanges(refChanges)
                .setChangesets(changesets)
                .setJsonObject(jsonObject);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testRepositoryFail() {
        HookDataImplBuilder builder = new HookDataImplBuilder()
                .setId(id)
                .setRepository(null)
                .setRefChanges(refChanges)
                .setChangesets(changesets)
                .setJsonObject(jsonObject);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testRefChangesFail() {
        HookDataImplBuilder builder = new HookDataImplBuilder()
                .setId(id)
                .setRepository(repository)
                .setRefChanges(null)
                .setChangesets(changesets)
                .setJsonObject(jsonObject);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testChangesetsFail() {
        HookDataImplBuilder builder = new HookDataImplBuilder()
                .setId(id)
                .setRepository(repository)
                .setRefChanges(refChanges)
                .setChangesets(null)
                .setJsonObject(jsonObject);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testJsonObjectFail() {
        HookDataImplBuilder builder = new HookDataImplBuilder()
                .setId(id)
                .setRepository(repository)
                .setRefChanges(refChanges)
                .setChangesets(changesets)
                .setJsonObject(null);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}