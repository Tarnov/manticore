package com.panbet.manticore.util.bl.mail;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.mail.Message;
import javax.mail.Session;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class MailServiceImplTest {
    private static final String mailSmtpPort = "mailSmtpPort";

    private static final String mailSmtpHost = "mailSmtpHost";

    private static final String login = "login";

    private static final String password = "password";

    private static final String serviceAddress = "serviceAddress";

    @Mock
    private MailMessage mailMessage;

    @Mock
    private MessageFactory messageFactory;

    @Mock
    private MailTransport mailTransport;

    @Mock
    private Message message;

    private MailServiceImpl mailService;


    @BeforeEach
    void setUp() {
        mailService = new MailServiceImpl(mailSmtpPort, mailSmtpHost,
                login, password, serviceAddress, messageFactory, mailTransport);
    }


    @Test
    void testSendSuccess() {
        ArgumentCaptor<Message> message = ArgumentCaptor.forClass(Message.class);

        mailService.send(mailMessage);

        verify(mailTransport, times(1)).send(message.capture());
    }


    @Test
    void testTransportFail() {
        when(messageFactory.create(ArgumentMatchers.eq(mailMessage), ArgumentMatchers.any(Session.class))).thenReturn(message);
        doThrow(new IllegalStateException()).when(mailTransport).send(ArgumentMatchers.any(Message.class));

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> mailService.send(mailMessage));
    }


    @Test
    void testCreateMessageFail() {
        doThrow(new IllegalStateException()).when(messageFactory).create(ArgumentMatchers.any(MailMessage.class),
                ArgumentMatchers.any(Session.class));

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> mailService.send(mailMessage));
    }


    @Test
    void testGetServiceEmailAddress() {
        Assertions.assertThat(mailService.getServiceEmailAddress()).isEqualTo(serviceAddress);
    }
}