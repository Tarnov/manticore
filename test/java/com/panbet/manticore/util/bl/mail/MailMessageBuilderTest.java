package com.panbet.manticore.util.bl.mail;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Authenticator;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;


@ExtendWith(MockitoExtension.class)
class MailMessageBuilderTest {
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


    @Test
    void testSuccess() throws AddressException {
        final Collection<MultipartFile> files = new ArrayList<>();
        files.add(file);

        final MailMessage mailMessage = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(files)
                .build();

        final Collection<InternetAddress> toAddressesCollection = new ArrayList<>();
        toAddressesCollection.add(new InternetAddress(toAddress));

        final Collection<InternetAddress> ccAddressesCollection = new ArrayList<>();
        ccAddressesCollection.add(new InternetAddress(ccAddress));

        final Collection<InternetAddress> bccAddressesCollection = new ArrayList<>();
        bccAddressesCollection.add(new InternetAddress(bccAddress));

        Assertions.assertThat(new InternetAddress(fromAddress)).isEqualTo(mailMessage.getFromAddress());
        Assertions.assertThat(mailMessage.getToAddresses()).isEqualTo(toAddressesCollection);
        Assertions.assertThat(mailMessage.getCcAddresses()).isEqualTo(ccAddressesCollection);
        Assertions.assertThat(mailMessage.getBccAddresses()).isEqualTo(bccAddressesCollection);
        Assertions.assertThat(mailMessage.getSubject()).isEqualTo(subject);
        Assertions.assertThat(mailMessage.getContent()).isEqualTo(content);
        Assertions.assertThat(mailMessage.getFiles()).isEqualTo(files);
    }


    @Test
    void testFromAddressFail1() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress("qwemail.ru")
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testFromAddressFail2() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress("qwe@.ru")
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testFromAddressFail3() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress("qwe1@email.ru, qwe2@email.ru")
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testFromAddressFail4() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(null)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToAddressFail1() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(null)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToAddressFail2() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(",qwe@mail.ru")
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToAddressFail3() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses("qwe1@mail.ru,, qwe2@mail.ru")
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testToAddressFail4() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses("qwe1@mail.ru, qwe2mail.ru")
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testCcAddressFail1() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(null)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testCcAddressFail2() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(",qwe@mail.ru")
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testCcAddressFail3() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses("qwe1@mail.ru,, qwe2@mail.ru")
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testCcAddressFail4() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses("qwe1@mail.ru, qwe2mail.ru")
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBccAddressFail1() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(null)
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBccAddressFail2() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(",qwe@mail.ru")
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBccAddressFail3() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses("qwe1@mail.ru,, qwe2@mail.ru")
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testBccAddressFail4() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses("qwe1@mail.ru, qwe2mail.ru")
                .setContent(content)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testContentFail() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(null)
                .setSubject(subject)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testSubjectFail() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(null)
                .setFiles(new ArrayList<>());
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testFilesFail() {
        MailMessageBuilder builder = new MailMessageBuilder()
                .setFromAddress(fromAddress)
                .setToAddresses(toAddress)
                .setCcAddresses(ccAddress)
                .setBccAddresses(bccAddress)
                .setContent(content)
                .setSubject(subject)
                .setFiles(null);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}