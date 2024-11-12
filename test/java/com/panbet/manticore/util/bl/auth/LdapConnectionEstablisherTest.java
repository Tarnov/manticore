package com.panbet.manticore.util.bl.auth;


import com.panbet.manticore.service.LdapConfig;
import com.unboundid.ldap.sdk.LDAPConnection;
import com.unboundid.ldap.sdk.LDAPException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@PrepareForTest({LDAPConnection.class})
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
class LdapConnectionEstablisherTest {
    private static final Integer LDAP_TIMEOUT = 1000;

    private static final String SERVER_ONE = "127.0.0.1";

    private static final String SERVER_TWO = "127.0.0.2";

    private static final List<String> LDAP_SERVER_LIST = Arrays.asList(SERVER_ONE, SERVER_TWO);

    @Mock
    private LdapConfig ldapConfig;

    @Mock
    private LdapConnectionCreator connectionCreator;

    @Mock
    private LDAPConnection ldapConnection;

    @InjectMocks
    private LdapConnectionEstablisher connectionEstablisher;


    @BeforeEach
    void setUp() {
        initLdapConfig();
    }


    private void initLdapConfig() {
        lenient().when(ldapConfig.getTimeout()).thenReturn(LDAP_TIMEOUT);
        when(ldapConfig.getServerList()).thenReturn(LDAP_SERVER_LIST);
    }


    @Test
    void testGetConnectionFirstServerAvailable() throws LDAPException {
        when(connectionCreator.createConnection(SERVER_ONE)).thenReturn(ldapConnection);

        LDAPConnection result = connectionEstablisher.createConnection();

        Assertions.assertThat(result).isEqualTo(ldapConnection);

        verify(connectionCreator, times(1)).createConnection(any());
    }


    @Test
    void testFirstServerUnavailableSecondAvailable() throws LDAPException {
        when(connectionCreator.createConnection(SERVER_ONE)).thenThrow(new LDAPException(null, "Test exception"));
        when(connectionCreator.createConnection(SERVER_TWO)).thenReturn(ldapConnection);

        LDAPConnection result = connectionEstablisher.createConnection();

        Assertions.assertThat(result).isEqualTo(ldapConnection);

        verify(connectionCreator, times(2)).createConnection(any());
    }


    @Test
    void testAllServersUnavailable() throws LDAPException {
        when(connectionCreator.createConnection(any())).thenThrow(new LDAPException(null, "Test exception"));

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> connectionEstablisher.createConnection());
    }
}
