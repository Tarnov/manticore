package com.panbet.manticore.model.comments;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class ManticoreModuleBuilderTest {
    private static final Integer moduleId = 1;

    private static final String moduleName = "NIMT";

    private static final ManticoreModule.Type type = ManticoreModule.Type.NIMT;


    @Test
    void testNimtSuccess() {
        final ManticoreModuleBuilder manticoreModuleBuilder = new ManticoreModuleBuilder();

        final ManticoreModule manticoreModule = manticoreModuleBuilder
                .setId(moduleId)
                .setName(moduleName)
                .build();

        Assertions.assertThat(manticoreModule.getId()).isEqualTo(moduleId);
        Assertions.assertThat(manticoreModule.getType()).isEqualTo(type);
    }


    @Test
    void testModuleIdFail() {
        ManticoreModuleBuilder builder = new ManticoreModuleBuilder()
                .setName(moduleName);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testModuleNameFail() {
        ManticoreModuleBuilder builder = new ManticoreModuleBuilder()
                .setId(moduleId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIncorrectModuleNameFail() {
        ManticoreModuleBuilder builder = new ManticoreModuleBuilder()
                .setId(moduleId)
                .setName("MIMNT");
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}