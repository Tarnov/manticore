package com.panbet.manticore.util.bl.statistics;


import com.panbet.manticore.model.statistics.NewCommandInfo;
import org.assertj.core.api.Assertions;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.mockito.Mockito.when;


class CommandStatisticsTableParserTest {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");

    private static final String DATE_STRING1 = "2015-08-31 05:00:00 UTC";

    private static final String DATE_STRING2 = "2015-09-31 05:00:00 UTC";

    private static final String COMMAND_NAME1 = "command1";

    private static final String COMMAND_NAME2 = "command2";

    private CommandStatisticsTableParser parser = new CommandStatisticsTableParser();

    @Mock
    private Element pageBody;

    @Mock
    private Elements pageElements;

    @Mock
    private Element tBody;

    @Mock
    private Elements rows;

    @Mock
    private Element row;

    @Mock
    private Elements columns;

    @Mock
    private Element column0;

    @Mock
    private Element column1;

    @Mock
    private Element column2;

    @Mock
    private Iterator<Element> iterator;

    @Mock
    private Date lastDateInDb;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    void testGetNewCommandInfosSingleRow() throws ParseException {
        when(pageBody.getElementsByTag("tbody")).thenReturn(pageElements);
        when(pageElements.get(0)).thenReturn(tBody);
        when(tBody.getElementsByTag("tr")).thenReturn(rows);
        when(rows.size()).thenReturn(1);
        when(rows.get(0)).thenReturn(row);
        when(row.getElementsByTag("td")).thenReturn(columns);

        when(columns.get(0)).thenReturn(column0);
        when(columns.get(1)).thenReturn(column1);
        when(columns.get(2)).thenReturn(column2);

        when(column0.text()).thenReturn(DATE_STRING1);
        final Long numberOfCalls = 1L;
        final Long memoryPerCall = 10L;
        when(column1.text()).thenReturn("1");
        when(column2.text()).thenReturn("10");

        final Set<NewCommandInfo> commandInfoSet = parser.getNewCommandInfos(lastDateInDb, pageBody);

        Assertions.assertThat(commandInfoSet).hasSize(1);
        final NewCommandInfo commandInfo = commandInfoSet.iterator().next();
        Assertions.assertThat(datesEqual(commandInfo.getDate(), DATE_FORMAT.parse(DATE_STRING1))).isTrue();
        Assertions.assertThat(commandInfo.getNumberOfCalls()).isEqualTo(numberOfCalls);
        Assertions.assertThat(commandInfo.getMemoryPerCall()).isEqualTo(memoryPerCall);
    }


    @Test
    void testGetNewCommandInfosTwoRows() throws ParseException {
        when(pageBody.getElementsByTag("tbody")).thenReturn(pageElements);
        when(pageElements.get(0)).thenReturn(tBody);
        when(tBody.getElementsByTag("tr")).thenReturn(rows);
        when(rows.size()).thenReturn(2);
        when(rows.get(0)).thenReturn(row);
        when(rows.get(1)).thenReturn(row);
        when(row.getElementsByTag("td")).thenReturn(columns);

        when(columns.get(0)).thenReturn(column0);
        when(columns.get(1)).thenReturn(column1);
        when(columns.get(2)).thenReturn(column2);

        when(column0.text()).thenReturn(DATE_STRING1, DATE_STRING2);
        final Long numberOfCalls1 = 1L;
        final Long numberOfCalls2 = 5L;
        final Long memoryPerCall1 = 10L;
        final Long memoryPerCall2 = 50L;
        when(column1.text()).thenReturn("1", "5");
        when(column2.text()).thenReturn("10", "50");

        final Set<NewCommandInfo> commandInfoSet = parser.getNewCommandInfos(lastDateInDb, pageBody);

        Assertions.assertThat(commandInfoSet).hasSize(2);
        final Iterator<NewCommandInfo> iterator = commandInfoSet.iterator();
        final NewCommandInfo commandInfo1 = iterator.next();
        Assertions.assertThat(datesEqual(commandInfo1.getDate(), DATE_FORMAT.parse(DATE_STRING1))).isTrue();
        Assertions.assertThat(commandInfo1.getNumberOfCalls()).isEqualTo(numberOfCalls1);
        Assertions.assertThat(commandInfo1.getMemoryPerCall()).isEqualTo(memoryPerCall1);

        final NewCommandInfo commandInfo2 = iterator.next();
        Assertions.assertThat(datesEqual(commandInfo2.getDate(), DATE_FORMAT.parse(DATE_STRING2))).isTrue();
        Assertions.assertThat(commandInfo2.getNumberOfCalls()).isEqualTo(numberOfCalls2);
        Assertions.assertThat(commandInfo2.getMemoryPerCall()).isEqualTo(memoryPerCall2);
    }


    @Test
    void testGetListOfCommandsOneCommand() {
        final List<Element> rowsList = new ArrayList<>();
        rowsList.add(row);
        final Elements rows = new Elements(rowsList);

        when(pageBody.getElementsByTag("tbody")).thenReturn(pageElements);
        when(pageElements.get(0)).thenReturn(tBody);
        when(tBody.getElementsByTag("tr")).thenReturn(rows);

        when(row.getElementsByTag("td")).thenReturn(columns);
        when(columns.get(0)).thenReturn(column0);
        when(column0.text()).thenReturn(COMMAND_NAME1);

        List<String> commandsList = parser.getListOfCommands(pageBody);

        Assertions.assertThat(commandsList).hasSize(1);
        Assertions.assertThat(commandsList.get(0)).isEqualTo(COMMAND_NAME1);
    }


    @Test
    void testGetListOfCommandsTwoCommands() {
        final List<Element> rowsList = new ArrayList<>();
        rowsList.add(row);
        rowsList.add(row);
        final Elements rows = new Elements(rowsList);

        when(pageBody.getElementsByTag("tbody")).thenReturn(pageElements);
        when(pageElements.get(0)).thenReturn(tBody);
        when(tBody.getElementsByTag("tr")).thenReturn(rows);

        when(row.getElementsByTag("td")).thenReturn(columns);
        when(columns.get(0)).thenReturn(column0);
        when(column0.text()).thenReturn(COMMAND_NAME1, COMMAND_NAME2);

        final List<String> commandsList = parser.getListOfCommands(pageBody);

        Assertions.assertThat(commandsList).hasSize(2);
        Assertions.assertThat(commandsList.get(0)).isEqualTo(COMMAND_NAME1);
        Assertions.assertThat(commandsList.get(1)).isEqualTo(COMMAND_NAME2);
    }


    private boolean datesEqual(Date date1, Date date2) {
        DateFormat dayFormat = new SimpleDateFormat("yyyyMMdd");
        return dayFormat.format(date1).equals(dayFormat.format(date2));
    }
}
