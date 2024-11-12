package com.panbet.manticore.util.bl.dependency.graph;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;


class DependencyGraphTest {
    private final DependencyGraph dependencyGraph = new DependencyGraph();

    @Test
    void testAddEdge() throws Exception {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("attribute", "test");

        final Edge edge1 = new Edge("1", "2", attributes);
        final Edge edge2 = new Edge("2", "1", attributes);
        final Edge edge3 = new Edge("1", "4", attributes);
        final Edge edge4 = new Edge("1", "5", attributes);

        dependencyGraph.addEdge(edge1);
        dependencyGraph.addEdge(edge2);
        dependencyGraph.addEdge(edge3);
        dependencyGraph.addEdge(edge4);

        final Collection<Edge> edges = new HashSet<>();
        edges.add(edge1);
        edges.add(edge2);
        edges.add(edge3);
        edges.add(edge4);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("1")).isEqualTo(edges);

        edges.clear();
        edges.add(edge1);
        edges.add(edge2);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("2")).isEqualTo(edges);

        edges.clear();
        edges.add(edge3);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("4")).isEqualTo(edges);

        edges.clear();
        edges.add(edge4);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("5")).isEqualTo(edges);

        edges.clear();

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("3")).isEqualTo(edges);
    }


    @Test
    void testGetEdgesByIssueKey() throws Exception {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("attribute", "test");

        final Edge edge = new Edge("1", "2", attributes);

        dependencyGraph.addEdge(edge);

        Collection<Edge> edges = new HashSet<>();
        edges.add(edge);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("1")).isEqualTo(edges);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("2")).isEqualTo(edges);

        Edge edge1 = dependencyGraph.getEdgesByIssueKey("1").iterator().next();
        Edge edge2 = dependencyGraph.getEdgesByIssueKey("2").iterator().next();

        Assertions.assertThat(edge1).isEqualTo(edge);
        Assertions.assertThat(edge2).isEqualTo(edge);

        Assertions.assertThat(edge1.getAttributes()).isEqualTo(attributes);
        Assertions.assertThat(edge2.getAttributes()).isEqualTo(attributes);

        Assertions.assertThat(edge1.getIssueKeyFrom()).isEqualTo("1");
        Assertions.assertThat(edge1.getIssueKeyTo()).isEqualTo("2");

        Assertions.assertThat(edge2.getIssueKeyFrom()).isEqualTo("1");
        Assertions.assertThat(edge2.getIssueKeyTo()).isEqualTo("2");
    }


    @Test
    void testRemoveSubsetsWithLengthLessThanOrEqual() throws Exception {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("attribute", "test");

        final Edge edge1 = new Edge("1", "2", attributes);
        final Edge edge2 = new Edge("2", "3", attributes);
        final Edge edge3 = new Edge("4", "5", attributes);
        final Edge edge4 = new Edge("4", "6", attributes);
        final Edge edge5 = new Edge("4", "7", attributes);

        dependencyGraph.addEdge(edge1);
        dependencyGraph.addEdge(edge2);
        dependencyGraph.addEdge(edge3);
        dependencyGraph.addEdge(edge4);
        dependencyGraph.addEdge(edge5);

        Collection<Edge> edges = new HashSet<>();
        edges.add(edge3);
        edges.add(edge4);
        edges.add(edge5);

        dependencyGraph.removeSubsetsWithLengthLessThanOrEqual(3);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("4")).isEqualTo(edges);

        edges.clear();

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("1")).isEqualTo(edges);

        dependencyGraph.removeSubsetsWithLengthLessThanOrEqual(4);

        Assertions.assertThat(dependencyGraph.getEdgesByIssueKey("4")).isEqualTo(edges);
    }


    @Test
    void testGetAllIssueKeysInGraph() throws Exception {
        final Map<String, String> attributes = new HashMap<>();
        attributes.put("attribute", "test");

        final Edge edge1 = new Edge("1", "2", attributes);
        final Edge edge2 = new Edge("2", "3", attributes);
        final Edge edge3 = new Edge("1", "3", attributes);
        final Edge edge4 = new Edge("2", "1", attributes);
        final Edge edge5 = new Edge("1", "4", attributes);

        dependencyGraph.addEdge(edge1);
        dependencyGraph.addEdge(edge2);
        dependencyGraph.addEdge(edge3);
        dependencyGraph.addEdge(edge4);
        dependencyGraph.addEdge(edge5);

        Collection<String> edgesIssueKeys = new HashSet<>();
        edgesIssueKeys.add("1");
        edgesIssueKeys.add("2");
        edgesIssueKeys.add("3");
        edgesIssueKeys.add("4");

        Assertions.assertThat(dependencyGraph.getAllIssueKeysInGraph().keySet()).isEqualTo(edgesIssueKeys);
    }
}