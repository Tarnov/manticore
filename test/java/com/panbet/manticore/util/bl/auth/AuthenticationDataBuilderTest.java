package com.panbet.manticore.util.bl.auth;


import com.panbet.manticore.model.roles.User;
import com.panbet.manticore.model.roles.UserDao;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpSession;


@ExtendWith(MockitoExtension.class)
class AuthenticationDataBuilderTest {
    private static final String INCOMING_USERNAME = "username";

    private static final String INCOMING_PASSWORD = "password";

    @Mock
    private HttpSession httpSession;

    @Mock
    private UserDao userDao;

    @Mock
    private User user;


    @Test
    void testBuildAllParameters() {
        final AuthenticationData data = new AuthenticationDataBuilder()
                .setIncomingUsername(INCOMING_USERNAME)
                .setIncomingPassword(INCOMING_PASSWORD)
                .setHttpSession(httpSession)
                .build();

        Assertions.assertThat(data.getIncomingUsername()).isEqualTo(INCOMING_USERNAME);
        Assertions.assertThat(data.getIncomingPassword()).isEqualTo(INCOMING_PASSWORD);
        Assertions.assertThat(data.getHttpSession()).isEqualTo(httpSession);
    }


    @Test
    void testBuildMissingUsername() {
        final AuthenticationDataBuilder builder = new AuthenticationDataBuilder()
                .setIncomingPassword(INCOMING_PASSWORD)
                .setHttpSession(httpSession);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildMissingPassword() {
        final AuthenticationDataBuilder builder = new AuthenticationDataBuilder()
                .setIncomingUsername(INCOMING_USERNAME)
                .setHttpSession(httpSession);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBuildMissingHttpSession() {
        final AuthenticationDataBuilder builder = new AuthenticationDataBuilder()
                .setIncomingUsername(INCOMING_USERNAME)
                .setIncomingPassword(INCOMING_PASSWORD);

        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}
