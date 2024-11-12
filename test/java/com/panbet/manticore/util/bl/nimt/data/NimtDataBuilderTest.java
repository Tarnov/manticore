package com.panbet.manticore.util.bl.nimt.data;


import com.panbet.manticore.util.bl.nimt.IssuesByCondition;
import com.panbet.manticore.util.bl.nimt.MasterTestUtils;
import com.panbet.manticore.util.bl.nimt.filters.NimtFilter;
import com.panbet.manticore.util.atlassian.outerlib.CommitsUtils;
import com.panbet.manticore.util.atlassian.outerlib.jira.JiraAdapter;
import com.panbet.manticore.util.atlassian.outerlib.stash.StashAdapter;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;


@ExtendWith(MockitoExtension.class)
class NimtDataBuilderTest {
    private final IssuesByCondition[] issuesByConditions = {};

    @Mock
    private MasterTestUtils masterTestUtils;

    @Mock
    private CommitsUtils commitsUtils;

    @Mock
    private Collection<NimtFilter> nimtFilters;

    @Mock
    private JiraAdapter jiraAdapter;

    @Mock
    private StashAdapter stashAdapter;


    @Test
    void testSuccess() {
        final NimtData data = NimtDataBuilder.create()
                .setCommitsUtils(commitsUtils)
                .setNimtFilters(nimtFilters)
                .setIssuesByConditions(issuesByConditions)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setMasterTestUtils(masterTestUtils)
                .build();

        Assertions.assertThat(data.getCommitsUtils()).isEqualTo(commitsUtils);
        Assertions.assertThat(data.getIssuesByConditions()).isEqualTo(issuesByConditions);
        Assertions.assertThat(data.getJiraAdapter()).isEqualTo(jiraAdapter);
        Assertions.assertThat(data.getStashAdapter()).isEqualTo(stashAdapter);
        Assertions.assertThat(data.getMasterTestUtils()).isEqualTo(masterTestUtils);
    }


    @Test
    void testCommitUtilsFail() {
        NimtDataBuilder builder = NimtDataBuilder.create()
                .setNimtFilters(nimtFilters)
                .setIssuesByConditions(issuesByConditions)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setMasterTestUtils(masterTestUtils);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testNimtFiltersFail() {
        NimtDataBuilder builder = NimtDataBuilder.create()
                .setCommitsUtils(commitsUtils)
                .setIssuesByConditions(issuesByConditions)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setMasterTestUtils(masterTestUtils);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testIssuesByConditionsFail() {
        NimtDataBuilder builder = NimtDataBuilder.create()
                .setCommitsUtils(commitsUtils)
                .setNimtFilters(nimtFilters)
                .setJiraAdapter(jiraAdapter)
                .setStashAdapter(stashAdapter)
                .setMasterTestUtils(masterTestUtils);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testJiraAdapterFail() {
        NimtDataBuilder builder = NimtDataBuilder.create()
                .setCommitsUtils(commitsUtils)
                .setNimtFilters(nimtFilters)
                .setIssuesByConditions(issuesByConditions)
                .setStashAdapter(stashAdapter)
                .setMasterTestUtils(masterTestUtils);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testStashAdapterFail() {
        NimtDataBuilder builder = NimtDataBuilder.create()
                .setCommitsUtils(commitsUtils)
                .setNimtFilters(nimtFilters)
                .setIssuesByConditions(issuesByConditions)
                .setJiraAdapter(jiraAdapter)
                .setMasterTestUtils(masterTestUtils);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testMasterTestUtilsFail() {
        NimtDataBuilder builder = NimtDataBuilder.create()
                .setCommitsUtils(commitsUtils)
                .setNimtFilters(nimtFilters)
                .setIssuesByConditions(issuesByConditions)
                .setStashAdapter(stashAdapter)
                .setJiraAdapter(jiraAdapter);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}