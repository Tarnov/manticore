package com.panbet.manticore.util.bl.dependency.graph;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class RelationsTest {
    @Test
    void testGetRelation() {
        Assertions.assertThat(Relations.BLOCKS.getRelation()).isEqualTo("Blocks");
        Assertions.assertThat(Relations.IS_BLOCKED_BY.getRelation()).isEqualTo("Is blocked by");
        Assertions.assertThat(Relations.IS_REQUIRED_FOR.getRelation()).isEqualTo("Is required for");
        Assertions.assertThat(Relations.DEPENDS_ON.getRelation()).isEqualTo("Depends on");
        Assertions.assertThat(Relations.IS_CONFLICT_FOR.getRelation()).isEqualTo("Is conflict for");
        Assertions.assertThat(Relations.CONFLICTS_WITH.getRelation()).isEqualTo("Conflicts with");
        Assertions.assertThat(Relations.SUBTASK.getRelation()).isEqualTo("subtask");
    }


    @Test
    void testGetRelationByCorrectString() {
        Assertions.assertThat(Relations.getRelationByString("Blocks")).contains(Relations.BLOCKS);
        Assertions.assertThat(Relations.getRelationByString("Is blocked by")).contains(Relations.IS_BLOCKED_BY);
        Assertions.assertThat(Relations.getRelationByString("Is required for")).contains(Relations.IS_REQUIRED_FOR);
        Assertions.assertThat(Relations.getRelationByString("Depends on")).contains(Relations.DEPENDS_ON);
        Assertions.assertThat(Relations.getRelationByString("Is conflict for")).contains(Relations.IS_CONFLICT_FOR);
        Assertions.assertThat(Relations.getRelationByString("Conflicts with")).contains(Relations.CONFLICTS_WITH);
        Assertions.assertThat(Relations.getRelationByString("subtask")).contains(Relations.SUBTASK);
    }

    @Test
    void testGetRelationByIncorrectString() {
        Assertions.assertThat(Relations.getRelationByString("test").isPresent()).isFalse();
    }
}