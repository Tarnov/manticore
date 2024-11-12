package com.panbet.manticore.util.bl.dependency.graph;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;


class TaskDependencyGraphDataTest {
    private TaskDependencyGraphData data;

    private String jql;

    private Collection<Relations> relations;

    private Collection<Long> status;


    @BeforeEach
    public void setUp() {
        jql = "jql";

        relations = new ArrayList<>();
        relations.add(Relations.BLOCKS);
        relations.add(Relations.IS_BLOCKED_BY);

        status = new ArrayList<>();
        status.add(6L);
        status.add(1L);

        data = new TaskDependencyGraphData(jql, relations, status, 2);
    }


    @Test
    void testGetStatus() {
        Assertions.assertThat(data.getStatus()).isEqualTo(status);
    }


    @Test
    void testGetRelations() {
        Assertions.assertThat(data.getRelations()).isEqualTo(relations);
    }


    @Test
    void testGetJql() {
        Assertions.assertThat(data.getJql()).isEqualTo(jql);
    }
}