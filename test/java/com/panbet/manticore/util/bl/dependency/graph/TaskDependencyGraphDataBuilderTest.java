package com.panbet.manticore.util.bl.dependency.graph;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;


class TaskDependencyGraphDataBuilderTest {
    @Test
    void testCorrectBuild() {
        TaskDependencyGraphDataBuilder dataBuilder = new TaskDependencyGraphDataBuilder();
        String jql = "jql";

        Collection<Relations> relations = new ArrayList<>();
        relations.add(Relations.BLOCKS);

        Collection<Long> status = new ArrayList<>();
        status.add(6L);

        dataBuilder.setJql(jql)
                .setRelations(relations)
                .setStatus(status)
                .setLengthForRemove(2);

        TaskDependencyGraphData data = dataBuilder.build();
        Assertions.assertThat(data.getJql()).isEqualTo(jql);
        Assertions.assertThat(data.getRelations()).isEqualTo(relations);
        Assertions.assertThat(data.getStatus()).isEqualTo(status);
        Assertions.assertThat(data.getLengthForRemove()).isEqualTo(2);
    }


    @Test
    void testNullJqlBuild() {
        TaskDependencyGraphDataBuilder builder =
                new TaskDependencyGraphDataBuilder()
                        .setJql(null)
                        .setRelations(new ArrayList<>())
                        .setStatus(new ArrayList<>())
                        .setLengthForRemove(2);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNullRelationsBuild() {
        TaskDependencyGraphDataBuilder builder =
                new TaskDependencyGraphDataBuilder()
                        .setJql("")
                        .setRelations(null)
                        .setStatus(new ArrayList<>())
                        .setLengthForRemove(2);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNullStatusBuild() {
        TaskDependencyGraphDataBuilder builder =
                new TaskDependencyGraphDataBuilder().setJql("")
                        .setRelations(new ArrayList<>())
                        .setStatus(null)
                        .setLengthForRemove(2);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}