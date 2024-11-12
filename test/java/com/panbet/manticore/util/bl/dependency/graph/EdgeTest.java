package com.panbet.manticore.util.bl.dependency.graph;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class EdgeTest {
    private Edge edge;

    private Map<String, String> attributes;


    @BeforeEach
    void setUp() {
        attributes = new HashMap<>();
        attributes.put("attribute1", "test1");
        attributes.put("attribute2", "test2");
        edge = new Edge("from", "to", attributes);
    }


    @Test
    void testGetIssueKeyFrom() {
        Assertions.assertThat(edge.getIssueKeyFrom()).isEqualTo("from");
    }


    @Test
    void testGetIssueKeyTo() {
        Assertions.assertThat(edge.getIssueKeyTo()).isEqualTo("to");
    }


    @Test
    void testGetAttributes() {
        Assertions.assertThat(edge.getAttributes()).isEqualTo(attributes);
    }
}