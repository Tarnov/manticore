package com.panbet.manticore.util.bl.mail;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class MessageFactoryTest {
    @Mock
    private Properties props;

    @Mock
    private Authenticator auth;

    @Mock
    private MultipartFile file;

    @Mock
    private InputStream fileInputStream;

    private final String fromAddress = "from@email.com";

    private final String toAddress = "to@email.com";

    private final String ccAddress = "cc@email.com";

    private final String bccAddress = "bcc@email.com";

    private final String subject = "subject";

    private final String content = "content";

    private final String fileContentType = "fileContentType";

    private final String fileOriginalFilename = "fileOriginalFilename";


    @Test
    void testCreateSuccess() throws MessagingException, IOException {
        when(file.getInputStream()).thenReturn(fileInputStream);
        when(file.getContentType()).thenReturn(fileContentType);
        when(file.getContentType()).thenReturn(fileContentType);
        when(file.getOriginalFilename()).thenReturn(fileOriginalFilename);

        final Collection<MultipartFile> files = new ArrayList<>();
        files.add(file);

        final MailMessage mailMessage = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setSubject(subject)
                .setContent(content)
                .setFiles(files)
                .build();

        final Session mailSession = Session.getDefaultInstance(props, auth);

        final MessageFactory messageFactory = new MessageFactory();

        final Message message = messageFactory.create(mailMessage, mailSession);

        Assertions.assertThat(mailMessage.getSubject()).isEqualTo(message.getSubject());
        Assertions.assertThat(new InternetAddress[]{mailMessage.getFromAddress()}).isEqualTo(message.getFrom());
        Assertions.assertThat(mailMessage.getToAddresses().toArray()).isEqualTo(message.getRecipients(Message.RecipientType.TO));
        Assertions.assertThat(mailMessage.getCcAddresses().toArray()).isEqualTo(message.getRecipients(Message.RecipientType.CC));
        Assertions.assertThat(mailMessage.getBccAddresses().toArray()).isEqualTo(message.getRecipients(Message.RecipientType.BCC));
    }
}