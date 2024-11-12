package com.panbet.manticore.util.bl.auth;


import com.panbet.manticore.model.roles.User;
import com.panbet.manticore.model.roles.UserDao;
import com.panbet.manticore.util.bl.auth.logout.LocalSessionRegistry;
import com.panbet.manticore.util.atlassian.Atlassian;
import com.unboundid.ldap.sdk.LDAPException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.session.SessionRegistry;

import javax.servlet.http.HttpSession;
import java.util.concurrent.Executor;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class LdapAuthenticationTest {
    private static final String PASSWORD = "password";

    private static final String USERNAME = "username";

    private static final boolean LDAP_CONNECTOR_IS_VALID = true;

    private static final boolean LDAP_CONNECTOR_IS_NOT_VALID = false;

    private static final boolean USER_ENABLED = true;

    private static final boolean USER_DISABLED = false;

    @Mock
    private ManticoreAuthenticationService manticoreAuthenticationService;

    @Mock
    private LdapConnector ldapConnector;

    @Mock
    private SessionRegistry sessionRegistry;

    @Mock
    private UserCreator userCreator;

    @Mock
    private Atlassian atlassian;

    @Mock
    private LocalSessionRegistry localSessionRegistry;

    @Mock
    private AuthenticationData data;

    @Mock
    private HttpSession httpSession;

    @Mock
    private User user;

    @Mock
    private org.springframework.security.core.Authentication authentication;

    @Mock
    private Executor taskExecutor;

    @Mock
    private UserDao userDao;

    @InjectMocks
    private LdapAuthentication ldapAuthentication;


    @BeforeEach
    void setUp() {
        initAuthenticationData();
        initHttpSession();
    }


    private void initHttpSession() {
        lenient().when(httpSession.getId()).thenReturn("httpSessionId");
    }


    private void initAuthenticationData() {
        lenient().when(data.getHttpSession()).thenReturn(httpSession);
        when(data.getIncomingPassword()).thenReturn(PASSWORD);
        when(data.getIncomingUsername()).thenReturn(USERNAME);
    }


    @Test
    void testAuthenticateSuccessUserInDB() throws LDAPException {
        when(ldapConnector.isValid(data.getIncomingUsername(), data.getIncomingPassword()))
                .thenReturn(LDAP_CONNECTOR_IS_VALID);
        when(user.isEnabled()).thenReturn(USER_ENABLED);
        lenient().doThrow(new RuntimeException("An exception for stubbing atlassian authentication")).when(atlassian)
                .getAuthentication(ArgumentMatchers.any());
        final ManticoreAuthentication manticoreAuthentication = new ManticoreAuthentication(user, authentication);
        when(manticoreAuthenticationService.auth(user)).thenReturn(manticoreAuthentication);
        when(userDao.findUserByLoginWithRoles(data.getIncomingUsername())).thenReturn(user);
        final Result result = ldapAuthentication.authenticate(data);

        verify(manticoreAuthenticationService, times(1)).auth(user);
        verify(sessionRegistry, times(1)).registerNewSession(data.getHttpSession().getId(), manticoreAuthentication);

        Assertions.assertThat(result.getStatus()).isEqualTo(Result.Status.Success);
    }


    @Test
    void testAuthenticateSuccessUserNotInDB() throws Exception {
        when(ldapConnector.isValid(data.getIncomingUsername(), data.getIncomingPassword()))
                .thenReturn(LDAP_CONNECTOR_IS_VALID);
        when(user.isEnabled()).thenReturn(USER_ENABLED);
        lenient().doThrow(new RuntimeException("An exception for stubbing atlassian authentication")).when(atlassian)
                .getAuthentication(ArgumentMatchers.any());
        final ManticoreAuthentication manticoreAuthentication = new ManticoreAuthentication(user, authentication);
        when(manticoreAuthenticationService.auth(user)).thenReturn(manticoreAuthentication);
        when(userDao.findUserByLoginWithRoles(data.getIncomingUsername())).thenReturn(null);
        when(userCreator.createUser(data)).thenReturn(user);
        final Result result = ldapAuthentication.authenticate(data);

        verify(manticoreAuthenticationService, times(1)).auth(user);
        verify(sessionRegistry, times(1)).registerNewSession(data.getHttpSession().getId(), manticoreAuthentication);

        Assertions.assertThat(result.getStatus()).isEqualTo(Result.Status.Success);
    }


    @Test
    void testAuthenticateWrongCredentials() throws LDAPException {
        when(ldapConnector.isValid(data.getIncomingUsername(), data.getIncomingPassword()))
                .thenReturn(LDAP_CONNECTOR_IS_NOT_VALID);

        final Result result = ldapAuthentication.authenticate(data);

        verify(user, never()).isEnabled();
        verify(atlassian, never()).getAuthentication(any());
        verify(manticoreAuthenticationService, never()).auth(any());
        verify(sessionRegistry, never()).registerNewSession(any(), any());

        Assertions.assertThat(result.getStatus()).isEqualTo(Result.Status.WrongCredentials);
    }


    @Test
    void testAuthenticateExceptionInIsValid() throws LDAPException {
        when(ldapConnector.isValid(data.getIncomingUsername(), data.getIncomingPassword()))
                .thenThrow(new LDAPException(null, "Test exception"));

        final Result result = ldapAuthentication.authenticate(data);

        verify(manticoreAuthenticationService, never()).auth(any());
        verify(sessionRegistry, never()).registerNewSession(any(), any());

        Assertions.assertThat(result.getStatus()).isEqualTo(Result.Status.Error);
    }


    @Test
    void testAuthenticateUserDisabled() throws LDAPException {
        when(ldapConnector.isValid(data.getIncomingUsername(), data.getIncomingPassword()))
                .thenReturn(LDAP_CONNECTOR_IS_VALID);
        when(user.isEnabled()).thenReturn(USER_DISABLED);
        when(userDao.findUserByLoginWithRoles(data.getIncomingUsername())).thenReturn(user);

        final Result result = ldapAuthentication.authenticate(data);

        verify(manticoreAuthenticationService, never()).auth(any());
        verify(sessionRegistry, never()).registerNewSession(any(), any());

        Assertions.assertThat(result.getStatus()).isEqualTo(Result.Status.Error);
    }


    @Test
    void testCreationOfNewUserIfNull() throws Exception {
        when(ldapConnector.isValid(data.getIncomingUsername(), data.getIncomingPassword()))
                .thenReturn(LDAP_CONNECTOR_IS_VALID);
        when(userCreator.createUser(data)).thenReturn(user);

        ldapAuthentication.authenticate(data);

        verify(userCreator, times(1)).createUser(data);
    }
}