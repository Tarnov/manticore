package com.panbet.manticore.util.bl.releaseCandidateReporting.data;


import com.panbet.manticore.service.system.ManticoreSystemData;
import com.panbet.manticore.util.atlassian.confluence.ConfluenceAdapter;
import com.panbet.manticore.util.atlassian.outerlib.jira.JiraAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
class ReleaseCandidateReportDataBuilderTest {
    @Mock
    private JiraAdapter jiraAdapter;

    @Mock
    private ConfluenceAdapter confluenceAdapter;

    private static final String contentId = "contentId";

    @Mock
    private ManticoreSystemData manticoreSystemData;


    @Test
    void testSuccess() {
        final ReleaseCandidateReportData releaseCandidateReportData = new ReleaseCandidateReportDataBuilder()
                .setConfluenceAdapter(confluenceAdapter)
                .setJiraAdapter(jiraAdapter)
                .setManticoreSystemData(manticoreSystemData)
                .setContentId(contentId)
                .build();

        Assertions.assertThat(releaseCandidateReportData).isNotNull();
        Assertions.assertThat(releaseCandidateReportData.getConfluenceAdapter()).isEqualTo(confluenceAdapter);
        Assertions.assertThat(releaseCandidateReportData.getJiraAdapter()).isEqualTo(jiraAdapter);
        Assertions.assertThat(releaseCandidateReportData.getManticoreSystemData()).isEqualTo(manticoreSystemData);
        Assertions.assertThat(releaseCandidateReportData.getContentId()).isEqualTo(contentId);
    }


    @Test
    void testConfluenceAdapterFail() {
        ReleaseCandidateReportDataBuilder builder = new ReleaseCandidateReportDataBuilder()
                .setJiraAdapter(jiraAdapter)
                .setManticoreSystemData(manticoreSystemData)
                .setContentId(contentId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testJiraAdapterFail() {
        ReleaseCandidateReportDataBuilder builder = new ReleaseCandidateReportDataBuilder()
                .setConfluenceAdapter(confluenceAdapter)
                .setManticoreSystemData(manticoreSystemData)
                .setContentId(contentId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testJiraURIFail() {
        ReleaseCandidateReportDataBuilder builder = new ReleaseCandidateReportDataBuilder()
                .setConfluenceAdapter(confluenceAdapter)
                .setJiraAdapter(jiraAdapter)
                .setContentId(contentId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testContentIdFail() {
        ReleaseCandidateReportDataBuilder builder = new ReleaseCandidateReportDataBuilder()
                .setConfluenceAdapter(confluenceAdapter)
                .setJiraAdapter(jiraAdapter)
                .setManticoreSystemData(manticoreSystemData);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}