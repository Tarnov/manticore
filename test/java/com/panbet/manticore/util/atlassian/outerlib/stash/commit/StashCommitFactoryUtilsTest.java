package com.panbet.manticore.util.atlassian.outerlib.stash.commit;


import com.atlassian.jira.rest.client.api.domain.Issue;
import com.atlassian.jira.rest.client.api.domain.Status;
import com.google.common.collect.ImmutableMap;
import com.panbet.manticore.service.system.ManticoreSystemData;
import com.panbet.manticore.util.atlassian.outerlib.CommitsUtils;
import com.panbet.stash.rest.client.api.alternative.commit.Commit;
import com.panbet.stash.rest.client.api.alternative.repo.Project;
import com.panbet.stash.rest.client.api.alternative.repo.Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class StashCommitFactoryUtilsTest {
    @Mock
    private CommitsUtils commitsUtils;

    @Mock
    private Repository repository;

    @Mock
    private Project project;

    @Mock
    private Commit commit;

    @Mock
    private Issue issue1;

    @Mock
    private Issue issue2;

    @Mock
    private Issue issue3;

    @Mock
    private Issue issue4;

    @Mock
    private Issue issue5;

    @Mock
    private Issue issue6;

    private static final String issueKey1 = "PAN-11111";

    private static final String issueKey2 = "PAN-22222";

    private static final String issueKey3 = "PAN-33333";

    private static final String issueKey4 = "MANTICORE-11111";

    private static final String issueKey5 = "MANTICORE-22222";

    private static final String issueKey6 = "MANTICORE-33333";

    @Mock
    private Status status1;

    @Mock
    private Status status2;

    @Mock
    private Status status3;

    @Mock
    private ManticoreSystemData manticoreSystemData;


    @Test
    void testSuccess() {
        Assertions.assertThat(new StashCommitFactoryUtils(commitsUtils)).isNotNull();
    }


    @Test
    void testCommitUtilsFail() {
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new StashCommitFactoryUtils(null));
    }


    @Test
    void testGetMessageWithLinksSuccessOneTaskAndOneHash() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .build();

        final String message = "PAN-11111 test тест [1111111d]";

        final String projectKey = "PAN";
        final String repo = "panbet";
        final String hash1 = "1111111d";
        final URI commitUri = URI.create("http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/1111111d");

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест [<a target=\"_blank\" " +
                "href=\"" + commitUri + "\">1111111d</a>]";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";

        final Collection<String> hashes = new ArrayList<>();
        hashes.add(hash1);
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(commitsUtils.findHashesWithoutCheck(message)).thenReturn(hashes);
        when(repository.getProject()).thenReturn(project);
        when(project.getKey()).thenReturn(projectKey);
        when(repository.getSlug()).thenReturn(repo);
        when(commitsUtils.createHashUriByProjectAndRepo(hash1, projectKey, repo)).thenReturn(commitUri);

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessOneTaskAndManyHash() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .build();

        final String message = "PAN-11111 test тест [1111111d, 2222222d]";
        final String projectKey = "PAN";
        final String repo = "panbet";
        final String hash1 = "1111111d";
        final String hash2 = "2222222d";
        final URI commitUri1 = URI.create("http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/" + hash1);
        final URI commitUri2 = URI.create("http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/" + hash2);

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест [<a target=\"_blank\" " +
                "href=\"http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/1111111d\">1111111d</a>, " +
                "<a target=\"_blank\" href=\"http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/2222222d\">2222222d</a>]";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        final Collection<String> hashes = new ArrayList<>();
        hashes.add("1111111d");
        hashes.add("2222222d");
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(commitsUtils.findHashesWithoutCheck(message)).thenReturn(hashes);
        when(repository.getProject()).thenReturn(project);
        when(project.getKey()).thenReturn("PAN");
        when(repository.getSlug()).thenReturn("panbet");
        when(commitsUtils.createHashUriByProjectAndRepo(hash1, projectKey, repo)).thenReturn(commitUri1);
        when(commitsUtils.createHashUriByProjectAndRepo(hash2, projectKey, repo)).thenReturn(commitUri2);

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessOneTaskAndOneSvnHash() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .build();

        final String message = "PAN-11111 test тест [dev. 1111111]";

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест [dev. <a target=\"_blank\" " +
                "href=\"http://10.0.1.136/websvn/revision.php?repname=panbet&path=/&rev=1111111\">1111111</a>]";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        final Collection<String> hashes = new ArrayList<>();
        hashes.add("1111111");
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(commitsUtils.findHashesWithoutCheck(message)).thenReturn(hashes);
        when(commitsUtils.isSvnFormat(message)).thenReturn(true);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("PAN");
        when(repository.getSlug()).thenReturn("panbet");

        Assertions.assertThat(URLDecoder.decode(stashCommitFactoryUtils
                .getMessageWithLinks(commit, repository, issueMap), StandardCharsets.UTF_8)).isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessOneTaskAndManySvnHash() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .build();

        final String message = "PAN-11111 test тест [dev. 1111111, 2222222]";

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест [dev. <a target=\"_blank\" " +
                "href=\"http://10.0.1.136/websvn/revision.php?repname=panbet&path=/&rev=1111111\">1111111</a>, " +
                "<a target=\"_blank\" " +
                "href=\"http://10.0.1.136/websvn/revision.php?repname=panbet&path=/&rev=2222222\">2222222</a>]";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        final Collection<String> hashes = new ArrayList<>();
        hashes.add("1111111");
        hashes.add("2222222");
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(commitsUtils.findHashesWithoutCheck(message)).thenReturn(hashes);
        when(commitsUtils.isSvnFormat(message)).thenReturn(true);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("PAN");
        when(repository.getSlug()).thenReturn("panbet");

        Assertions.assertThat(URLDecoder.decode(stashCommitFactoryUtils
                .getMessageWithLinks(commit, repository, issueMap), StandardCharsets.UTF_8)).isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessManyTasksAndEndOfLine() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .build();

        final String message = "PAN-11111 test тест PAN-22222";

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-22222\">PAN-22222</a> ";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(issue2.getStatus()).thenReturn(status2);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status2.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        lenient().when(status2.getName()).thenReturn(statusName);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("PAN");
        lenient().when(repository.getSlug()).thenReturn("panbet");

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessManyTasksAndOneSpace() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .build();

        final String message = "PAN-11111 test тест PAN-22222 ";

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-22222\">PAN-22222</a>  ";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(issue2.getStatus()).thenReturn(status2);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status2.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(status2.getName()).thenReturn(statusName);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("PAN");
        lenient().when(repository.getSlug()).thenReturn("panbet");

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessManyTasksAndManyText() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .build();

        final String message = "PAN-11111 test тест PAN-22222 test тест";

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-22222\">PAN-22222</a>  test тест";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(issue2.getStatus()).thenReturn(status2);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status2.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(status2.getName()).thenReturn(statusName);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("PAN");
        lenient().when(repository.getSlug()).thenReturn("panbet");

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessManyTasks() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .build();

        final String message = "PAN-11111 test тест PAN-22222, PAN-33333";

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-22222\">PAN-22222</a> , " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-33333\">PAN-33333</a> ";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(issue3.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(issue2.getStatus()).thenReturn(status2);
        when(issue3.getStatus()).thenReturn(status3);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status2.getIconUrl()).thenReturn(statusIconUrl);
        when(status3.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(status2.getName()).thenReturn(statusName);
        when(status3.getName()).thenReturn(statusName);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn("PAN");
        lenient().when(repository.getSlug()).thenReturn("panbet");

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetMessageWithLinksSuccessManyTasksAndManyHash() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .build();

        final String message = "PAN-11111 test тест PAN-22222, PAN-33333 [1111111d, 2222222d]";

        final String projectKey = "PAN";
        final String repo = "panbet";
        final String hash1 = "1111111d";
        final String hash2 = "2222222d";
        final URI commitUri1 = URI.create("http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/" + hash1);
        final URI commitUri2 = URI.create("http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/" + hash2);

        final String messageWithLinks = "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-11111\">PAN-11111</a>  test тест " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-22222\">PAN-22222</a> , " +
                "<img src=\"http://jira.mbet.spb.ru/images/icons/statuses/resolved.png\" " +
                "title=\"Resolved\"><a class=\"m-label\" target=\"_blank\" " +
                "href=\"http://jira.mbet.spb.ru/browse/PAN-33333\">PAN-33333</a>  [<a target=\"_blank\" " +
                "href=\"http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/1111111d\">1111111d</a>, " +
                "<a target=\"_blank\" href=\"http://git.mbet.spb.ru/projects/PAN/repos/panbet/commits/2222222d\">2222222d</a>]";

        final URI self = UriBuilder.fromUri("http://jira.mbet.spb.ru/rest/api/latest/issue/98100").build();
        final URI statusIconUrl =
                UriBuilder.fromUri("http://jira.mbet.spb.ru/images/icons/statuses/resolved.png").build();
        final URI stashURI = UriBuilder.fromUri("http://git.mbet.spb.ru").build();
        final String statusName = "Resolved";
        final Collection<String> hashes = new ArrayList<>();
        hashes.add(hash1);
        hashes.add(hash2);
        lenient().when(manticoreSystemData.getStashURI()).thenReturn(stashURI);
        when(commit.getMessage()).thenReturn(message);
        when(issue1.getSelf()).thenReturn(self);
        when(issue2.getSelf()).thenReturn(self);
        when(issue3.getSelf()).thenReturn(self);
        when(issue1.getStatus()).thenReturn(status1);
        when(issue2.getStatus()).thenReturn(status2);
        when(issue3.getStatus()).thenReturn(status3);
        when(status1.getIconUrl()).thenReturn(statusIconUrl);
        when(status2.getIconUrl()).thenReturn(statusIconUrl);
        when(status3.getIconUrl()).thenReturn(statusIconUrl);
        when(status1.getName()).thenReturn(statusName);
        when(status2.getName()).thenReturn(statusName);
        when(status3.getName()).thenReturn(statusName);
        when(commitsUtils.findHashesWithoutCheck(message)).thenReturn(hashes);
        when(repository.getProject()).thenReturn(project);
        when(project.getKey()).thenReturn("PAN");
        when(repository.getSlug()).thenReturn("panbet");
        when(commitsUtils.createHashUriByProjectAndRepo(hash1, projectKey, repo)).thenReturn(commitUri1);
        when(commitsUtils.createHashUriByProjectAndRepo(hash2, projectKey, repo)).thenReturn(commitUri2);

        Assertions.assertThat(stashCommitFactoryUtils.getMessageWithLinks(commit, repository, issueMap))
                .isEqualTo(messageWithLinks);
    }


    @Test
    void testGetIssueMapByCurrentCommitPANProjectAllTasks() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .put(issueKey4, issue4)
                .put(issueKey5, issue5)
                .put(issueKey6, issue6)
                .build();

        final ImmutableMap<String, Issue> issueMapAnswer = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .build();

        final String projectKey = "PAN";
        final Set<String> issueKeysSet = new HashSet<>();
        issueKeysSet.add(issueKey1);
        issueKeysSet.add(issueKey2);
        issueKeysSet.add(issueKey3);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(commit, projectKey)).thenReturn(issueKeysSet);

        Assertions.assertThat(stashCommitFactoryUtils.getIssueMapByCurrentCommit(issueMap, commit, issueKeysSet))
                .isEqualTo(issueMapAnswer);
    }


    @Test
    void testGetIssueMapByCurrentCommitPANProjectOneTask() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .put(issueKey4, issue4)
                .put(issueKey5, issue5)
                .put(issueKey6, issue6)
                .build();

        final ImmutableMap<String, Issue> issueMapAnswer = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .build();

        final String projectKey = "PAN";
        final Set<String> issueKeysSet = new HashSet<>();
        issueKeysSet.add(issueKey1);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(commit, projectKey)).thenReturn(issueKeysSet);

        Assertions.assertThat(stashCommitFactoryUtils.getIssueMapByCurrentCommit(issueMap, commit, issueKeysSet))
                .isEqualTo(issueMapAnswer);
    }


    @Test
    void testGetIssueMapByCurrentCommitMANTICOREProjectAllTasks() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .put(issueKey4, issue4)
                .put(issueKey5, issue5)
                .put(issueKey6, issue6)
                .build();

        final ImmutableMap<String, Issue> issueMapAnswer = ImmutableMap.<String, Issue>builder()
                .put(issueKey4, issue4)
                .put(issueKey5, issue5)
                .put(issueKey6, issue6)
                .build();

        final String projectKey = "MANTICORE";
        final Set<String> issueKeysSet = new HashSet<>();
        issueKeysSet.add(issueKey4);
        issueKeysSet.add(issueKey5);
        issueKeysSet.add(issueKey6);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(commit, projectKey)).thenReturn(issueKeysSet);

        Assertions.assertThat(stashCommitFactoryUtils.getIssueMapByCurrentCommit(issueMap, commit, issueKeysSet))
                .isEqualTo(issueMapAnswer);
    }


    @Test
    void testGetIssueMapByCurrentCommitMANTICOREProjectOneTask() {
        final StashCommitFactoryUtils stashCommitFactoryUtils = new StashCommitFactoryUtils(commitsUtils);
        final ImmutableMap<String, Issue> issueMap = ImmutableMap.<String, Issue>builder()
                .put(issueKey1, issue1)
                .put(issueKey2, issue2)
                .put(issueKey3, issue3)
                .put(issueKey4, issue4)
                .put(issueKey5, issue5)
                .put(issueKey6, issue6)
                .build();

        final ImmutableMap<String, Issue> issueMapAnswer = ImmutableMap.<String, Issue>builder()
                .put(issueKey5, issue5)
                .build();

        final String projectKey = "MANTICORE";
        final Set<String> issueKeysSet = new HashSet<>();
        issueKeysSet.add(issueKey5);
        lenient().when(repository.getProject()).thenReturn(project);
        lenient().when(project.getKey()).thenReturn(projectKey);
        lenient().when(commitsUtils.getIssueKeysFromCommitByProjectKey(commit, projectKey)).thenReturn(issueKeysSet);

        Assertions.assertThat(stashCommitFactoryUtils.getIssueMapByCurrentCommit(issueMap, commit, issueKeysSet))
                .isEqualTo(issueMapAnswer);
    }
}