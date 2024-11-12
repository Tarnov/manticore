package com.panbet.manticore.util.bl.statistics;


import com.panbet.manticore.model.statistics.*;
import com.panbet.manticore.service.system.ManticoreSystemData;
import org.assertj.core.api.Assertions;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@PrepareForTest(Jsoup.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
class CommandsStatisticsUpdaterTest {
    private static final String COMMAND1 = "command1";

    private static final String COMMAND2 = "command2";

    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    private static final String DATE_STRING1 = "2015-08-31 05:00:00 UTC";

    private Date date;

    @Mock
    private ManticoreSystemData manticoreSystemData;

    @Mock
    private CommandDao commandDao;

    @Mock
    private CommandStatisticsTableParser parser;

    @InjectMocks
    private CommandsStatisticsUpdater updater;

    @Mock
    private CommandInfo commandInfo1;

    @Mock
    private CommandInfo commandInfo2;

    @Mock
    private NewCommandInfo newCommandInfo1;

    @Mock
    private NewCommandInfo newCommandInfo2;

    @Mock
    private Command command;

    @Mock
    private NewCommand newcommand;

    @Mock
    private Connection connection;

    @Mock
    private Document document;

    @Mock
    private Element pageBody;

    private Set<CommandInfo> oldCommandInfos = new LinkedHashSet<>();

    private Set<NewCommandInfo> newCommandInfos = new LinkedHashSet<>();

    private List<String> commandsList = new ArrayList<>(Arrays.asList(COMMAND1, COMMAND2));


    @BeforeAll
    static void set() {
        Mockito.mockStatic(Jsoup.class);
    }


    @BeforeEach
    void setUp() throws ParseException {
        date = DATE_FORMAT.parse(DATE_STRING1);

        lenient().when(commandInfo1.getDate()).thenReturn(date);

        oldCommandInfos.add(commandInfo1);

        newCommandInfos.add(newCommandInfo1);
        newCommandInfos.add(newCommandInfo2);

        final URI statserviceURI = UriBuilder.fromUri("http://statservice.uri").build();
        when(manticoreSystemData.getStatserviceURI()).thenReturn(statserviceURI);

        when(Jsoup.connect(any())).thenReturn(connection);
        when(connection.timeout(anyInt())).thenReturn(connection);
        lenient().when(connection.data(any(String.class), any(String.class))).thenReturn(connection);
        lenient().when(document.body()).thenReturn(pageBody);
    }


    @Test
    void testUpdateCommandsStatisticsSuccess() throws IOException, IllegalStateException {
        when(connection.get()).thenReturn(document);

        when(parser.getListOfCommands(any())).thenReturn(commandsList);

        when(commandDao.getCommand(COMMAND1)).thenReturn(Optional.of(command));
        when(commandDao.getCommand(COMMAND2)).thenReturn(Optional.empty());

        when(command.getCommandInfos()).thenReturn(oldCommandInfos);

        when(parser.getNewCommandInfos(any(Date.class), any())).thenReturn(newCommandInfos);

        updater.updateCommandStatistics();

        verify(commandDao, times(1)).getCommand(COMMAND1);
        verify(commandDao, times(1)).getCommand(COMMAND2);
        verify(commandDao, times(1)).addNewCommand(any(NewCommand.class));
        verify(commandDao, times(1)).updateCommand(command, newCommandInfos);
    }


    @Test
    void testParseExceptionInParseCommandInfo() throws IOException {
        when(connection.get()).thenReturn(document);

        when(parser.getListOfCommands(any())).thenReturn(commandsList);

        when(commandDao.getCommand(COMMAND1)).thenReturn(Optional.of(command));
        lenient().when(commandDao.getCommand(COMMAND2)).thenReturn(Optional.empty());

        when(command.getCommandInfos()).thenReturn(oldCommandInfos);

        when(parser.getNewCommandInfos(any(Date.class), any())).thenThrow(new IllegalStateException("Test exception"));

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> updater.updateCommandStatistics());
    }


    @Test
    void testIOExceptionInParseCommandInfo() throws IOException {
        when(connection.get()).thenReturn(document).thenThrow(new IOException("Test exception"));

        when(parser.getListOfCommands(any())).thenReturn(commandsList);

        when(commandDao.getCommand(COMMAND1)).thenReturn(Optional.of(command));
        lenient().when(commandDao.getCommand(COMMAND2)).thenReturn(Optional.empty());

        when(command.getCommandInfos()).thenReturn(oldCommandInfos);

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> updater.updateCommandStatistics());
    }


    @Test
    void testIOExceptionInUpdateCommandStatistics() throws IOException, IllegalStateException {
        lenient().when(connection.get()).thenThrow(new IOException("Test exception"));

        Assertions.assertThatExceptionOfType(IllegalStateException.class)
                .isThrownBy(() -> updater.updateCommandStatistics());
    }
}
