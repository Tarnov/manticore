package com.panbet.manticore.util.bl.auth;


import com.panbet.manticore.service.LdapConfig;
import com.unboundid.ldap.sdk.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;


@ExtendWith(MockitoExtension.class)
@PrepareForTest({LDAPConnection.class, BindResult.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
class LdapConnectorImplTest {
    private static final String USERNAME = "username";

    private static final String PASSWORD = "password";

    @Mock
    private LdapConnectionEstablisher connectionEstablisher;

    @Mock
    private LdapConfig ldapConfig;

    @InjectMocks
    private LdapConnectorImpl ldapConnector;

    private BindResult bindResult = mock(BindResult.class);

    private LDAPConnection connection = mock(LDAPConnection.class);


    @Test
    void testIsValidSuccess() throws LDAPException {
        when(connectionEstablisher.createConnection()).thenReturn(connection);
        when(connection.bind(ArgumentMatchers.any(BindRequest.class))).thenReturn(bindResult);

        when(bindResult.getResultCode()).thenReturn(ResultCode.SUCCESS);

        boolean result = ldapConnector.isValid(USERNAME, PASSWORD);

        Assertions.assertThat(result).isTrue();
    }


    @Test
    void testIsValidInvalidCredentials() throws LDAPException {
        when(connectionEstablisher.createConnection()).thenReturn(connection);
        when(connection.bind(ArgumentMatchers.any(BindRequest.class))).thenThrow(new LDAPException(ResultCode.INVALID_CREDENTIALS));

        boolean result = ldapConnector.isValid(USERNAME, PASSWORD);

        Assertions.assertThat(result).isFalse();
    }


    @Test
    void testIsValidParameterError() throws LDAPException {
        when(connectionEstablisher.createConnection()).thenReturn(connection);
        when(connection.bind(ArgumentMatchers.any(BindRequest.class))).thenThrow(new LDAPException(ResultCode.PARAM_ERROR));

        boolean result = ldapConnector.isValid(USERNAME, PASSWORD);

        Assertions.assertThat(result).isFalse();
    }


    @Test
    void testIsValidOtherLDAPException() throws LDAPException {
        when(connectionEstablisher.createConnection()).thenReturn(connection);
        when(connection.bind(ArgumentMatchers.any(BindRequest.class))).thenThrow(new LDAPException(ResultCode.UNAVAILABLE));

        Assertions.assertThatExceptionOfType(LDAPException.class)
                .isThrownBy(() -> ldapConnector.isValid(USERNAME, PASSWORD));
    }
}