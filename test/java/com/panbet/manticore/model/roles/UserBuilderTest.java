package com.panbet.manticore.model.roles;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


class UserBuilderTest {
    private static final long USER_ID = 1;

    private static final String USER_LOGIN = "test";

    private static final String USER_PASSWORD = "test";

    private static final boolean USER_ENABLED = true;

    private static final String USER_FULL_NAME = "test";

    private static final String USER_EMAIL = "test";

    private Set<Role> roles;


    @BeforeEach
    void init() {
        final LinkedHashSet<Access> accesses = new LinkedHashSet<>();
        accesses.add(new AccessImpl(1, "access1", new HashSet<>()));

        this.roles = new HashSet<>();
        roles.add(new RoleImpl(1, "role1", new LinkedHashSet<>()));
        roles.add(new RoleImpl(2, "role2", accesses));
    }


    @Test
    void testAllArgumentsPresent() {
        final UserBuilder builder = new UserBuilder();
        builder.setId(USER_ID);
        builder.setLogin(USER_LOGIN);
        builder.setPassword(USER_PASSWORD);
        builder.setEnabled(USER_ENABLED);
        builder.setFullName(USER_FULL_NAME);
        builder.setEmail(USER_EMAIL);
        builder.setRoles(roles);

        final User user = builder.build();

        Assertions.assertThat(user.getId()).isEqualTo(USER_ID);
        Assertions.assertThat(user.getLogin()).isEqualTo(USER_LOGIN);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        Assertions.assertThat(user.isEnabled()).isEqualTo(USER_ENABLED);
        Assertions.assertThat(user.getFullName()).isEqualTo(USER_FULL_NAME);
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getRoles()).isEqualTo(roles);
    }


    @Test
    void testEmptyId() {
        final UserBuilder builder = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Id must be positive")
                .isThrownBy(builder::build);
    }


    @Test
    void testEmptyLogin() {
        final UserBuilder builder = new UserBuilder()
                .setId(USER_ID)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Login must be not null")
                .isThrownBy(builder::build);
    }


    @Test
    void testEmptyPassword() {
        final UserBuilder builder = new UserBuilder()
                .setId(USER_ID)
                .setLogin(USER_LOGIN)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Password must be not null")
                .isThrownBy(builder::build);
    }


    @Test
    void testEmptyEnabled() {
        final User user = new UserBuilder()
                .setId(USER_ID)
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles)
                .build();

        Assertions.assertThat(user.getId()).isEqualTo(USER_ID);
        Assertions.assertThat(user.getLogin()).isEqualTo(USER_LOGIN);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        Assertions.assertThat(user.isEnabled()).isFalse();
        Assertions.assertThat(user.getFullName()).isEqualTo(USER_FULL_NAME);
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getRoles()).isEqualTo(roles);
    }


    @Test
    void testEmptyFullName() {
        final UserBuilder builder = new UserBuilder()
                .setId(USER_ID)
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Full name must be not null")
                .isThrownBy(builder::build);
    }


    @Test
    void testEmptyEmail() {
        final UserBuilder builder = new UserBuilder()
                .setId(USER_ID)
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Email must be not null")
                .isThrownBy(builder::build);
    }


    @Test
    void testEmptyRoles() {
        final User user = new UserBuilder()
                .setId(USER_ID)
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .build();

        Assertions.assertThat(user.getId()).isEqualTo(USER_ID);
        Assertions.assertThat(user.getLogin()).isEqualTo(USER_LOGIN);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        Assertions.assertThat(user.isEnabled()).isEqualTo(USER_ENABLED);
        Assertions.assertThat(user.getFullName()).isEqualTo(USER_FULL_NAME);
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getRoles()).isEmpty();
    }


    @Test
    void testNewUserBuildAllArgumentsPresent() {
        final NewUser user = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles)
                .buildNewUser();

        Assertions.assertThat(user.getLogin()).isEqualTo(USER_LOGIN);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        Assertions.assertThat(user.isEnabled()).isEqualTo(USER_ENABLED);
        Assertions.assertThat(user.getFullName()).isEqualTo(USER_FULL_NAME);
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getRoles()).isEqualTo(roles);
    }


    @Test
    void testNewUserBuildEmptyLogin() {
        final UserBuilder builder = new UserBuilder()
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Login must be not null")
                .isThrownBy(builder::build);
    }


    @Test
    void testNewUserBuildEmptyPassword() {
        final UserBuilder builder = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Password must be not null")
                .isThrownBy(builder::buildNewUser);
    }


    @Test
    void testNewUserBuildEmptyEnabled() {
        final NewUser user = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .setRoles(roles)
                .buildNewUser();

        Assertions.assertThat(user.getLogin()).isEqualTo(USER_LOGIN);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        Assertions.assertThat(user.isEnabled()).isFalse();
        Assertions.assertThat(user.getFullName()).isEqualTo(USER_FULL_NAME);
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getRoles()).isEqualTo(roles);
    }


    @Test
    void testNewUserBuildEmptyRoles() {
        final NewUser user = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setEmail(USER_EMAIL)
                .buildNewUser();

        Assertions.assertThat(user.getLogin()).isEqualTo(USER_LOGIN);
        Assertions.assertThat(user.getPassword()).isEqualTo(USER_PASSWORD);
        Assertions.assertThat(user.isEnabled()).isEqualTo(USER_ENABLED);
        Assertions.assertThat(user.getFullName()).isEqualTo(USER_FULL_NAME);
        Assertions.assertThat(user.getEmail()).isEqualTo(USER_EMAIL);
        Assertions.assertThat(user.getRoles()).isEmpty();
    }


    @Test
    void testNewUserBuildEmptyFullName() {
        final UserBuilder builder = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setEmail(USER_EMAIL)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Full name must be not null")
                .isThrownBy(builder::buildNewUser);
    }


    @Test
    void testNewUserBuildEmptyEmail() {
        final UserBuilder builder = new UserBuilder()
                .setLogin(USER_LOGIN)
                .setPassword(USER_PASSWORD)
                .setEnabled(USER_ENABLED)
                .setFullName(USER_FULL_NAME)
                .setRoles(roles);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .describedAs("Email must be not null")
                .isThrownBy(builder::buildNewUser);
    }

}
