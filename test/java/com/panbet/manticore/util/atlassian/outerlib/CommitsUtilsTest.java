package com.panbet.manticore.util.atlassian.outerlib;


import com.google.common.collect.Lists;
import com.panbet.manticore.model.utils.TrackedAtlassianProjectsKeys;
import com.panbet.manticore.service.system.ManticoreSystemData;
import com.panbet.manticore.util.atlassian.outerlib.stash.commit.StashCommit;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;


/**
 * @author Sukhov Dmitry
 */
@ExtendWith(MockitoExtension.class)
class CommitsUtilsTest {

    @Mock
    private StashCommit commit;

    @Mock
    private static ManticoreSystemData manticoreSystemData;

    @Mock
    private static List<TrackedAtlassianProjectsKeys> atlassianProjectsKeys;

    private static final CommitsUtils COMMITS_UTILS = new CommitsUtils(manticoreSystemData, atlassianProjectsKeys);

    private static final String HASH1 = "af399df84a152b863d17b6f5210ac3eac2a149ac";

    private static final String HASH2 = "bf399df84a152b863d17b6f5210ac3eac2a149ac";

    private static final String HASH3 = "cf399df84a152b863d17b6f5210ac3eac2a149ac";

    private static final String ANOTHER_COMMIT_MESSAGE = "anotherCommit";

    private static final String COMMIT_MESSAGE = "commitMessage";


    @Test
    void testDeleteIfReverted() {
        final StashCommit reverted = mock(StashCommit.class);
        final StashCommit commitWithCommitIdInTheMessage = mock(StashCommit.class);
        final StashCommit anotherCommit = mock(StashCommit.class);

        when(reverted.getId()).thenReturn(HASH1);
        when(reverted.getMessage()).thenReturn(COMMIT_MESSAGE);
        when(commitWithCommitIdInTheMessage.getId()).thenReturn(HASH2);
        when(commitWithCommitIdInTheMessage.getMessage()).thenReturn(HASH1);
        when(anotherCommit.getId()).thenReturn(HASH3);
        when(anotherCommit.getMessage()).thenReturn(ANOTHER_COMMIT_MESSAGE);

        final Collection<StashCommit> commits = Stream.<StashCommit>builder()
                .add(reverted)
                .add(commitWithCommitIdInTheMessage)
                .add(anotherCommit)
                .build()
                .collect(Collectors.toCollection(ArrayList::new));

        COMMITS_UTILS.deleteIfReverted(commits);

        Assertions.assertThat(commits).hasSize(1);
        Assertions.assertThat(commits.iterator().next().getMessage()).isEqualTo(ANOTHER_COMMIT_MESSAGE);
    }


    @Test
    void testDeleteIfRevertedNoRevertedCommits() {
        final StashCommit reverted = mock(StashCommit.class);
        final StashCommit commitWithCommitIdInTheMessage = mock(StashCommit.class);
        final StashCommit anotherCommit = mock(StashCommit.class);

        lenient().when(reverted.getId()).thenReturn(HASH1);
        lenient().when(reverted.getMessage()).thenReturn(COMMIT_MESSAGE);
        lenient().when(commitWithCommitIdInTheMessage.getId()).thenReturn(HASH2);
        lenient().when(commitWithCommitIdInTheMessage.getMessage()).thenReturn(HASH1);
        lenient().when(anotherCommit.getId()).thenReturn(HASH3);
        lenient().when(anotherCommit.getMessage()).thenReturn(ANOTHER_COMMIT_MESSAGE);

        final Collection<StashCommit> commits = Stream.<StashCommit>builder()
                .add(anotherCommit)
                .add(anotherCommit)
                .add(anotherCommit)
                .build()
                .collect(Collectors.toCollection(ArrayList::new));

        COMMITS_UTILS.deleteIfReverted(commits);

        Assertions.assertThat(commits).hasSize(3);
        Assertions.assertThat(commits.iterator().next().getMessage()).isEqualTo(ANOTHER_COMMIT_MESSAGE);
    }


    @Test
    void testDeleteIfRevertedEmptyIncomingCommits() {
        final StashCommit reverted = mock(StashCommit.class);
        final StashCommit commitWithCommitIdInTheMessage = mock(StashCommit.class);
        final StashCommit anotherCommit = mock(StashCommit.class);

        lenient().when(reverted.getId()).thenReturn(HASH1);
        lenient().when(reverted.getMessage()).thenReturn(COMMIT_MESSAGE);
        lenient().when(commitWithCommitIdInTheMessage.getId()).thenReturn(HASH2);
        lenient().when(commitWithCommitIdInTheMessage.getMessage()).thenReturn(HASH1);
        lenient().when(anotherCommit.getId()).thenReturn(HASH3);
        lenient().when(anotherCommit.getMessage()).thenReturn(ANOTHER_COMMIT_MESSAGE);

        final Collection<StashCommit> commits = Stream.<StashCommit>builder()
                .build()
                .collect(Collectors.toCollection(ArrayList::new));

        COMMITS_UTILS.deleteIfReverted(commits);

        Assertions.assertThat(commits.size()).isZero();
    }


    @Test
    void testFindHashes7() {
        final String hashes = " [409c75d8, 3896202a, 7b58b49e, 9838b3e9, b72ee663, 87e223b7, 6c8688d0]";
        final Collection<String> result = COMMITS_UTILS.findHashes(hashes);

        Assertions.assertThat(result.size()).isEqualTo(7);
    }


    @Test
    void testFindHashes2() {
        final String hashes = " [409c75d8, 3896202a]";
        final Collection<String> result = COMMITS_UTILS.findHashes(hashes);

        Assertions.assertThat(result.size()).isEqualTo(2);
    }


//    @Test
//    void testFindHashesWrongString1()
//    {
//        final String hashes = " [409c75d8, 3896202a";
//        final Collection<String> result = COMMITS_UTILS.findHashes(hashes);
//
//        assertEquals(0, result.size());
//    }


    @Test
    void testFindHashesWrongString2() {
        final String hashes = "409c75d8, 3896202a]";
        final Collection<String> result = COMMITS_UTILS.findHashes(hashes);

        Assertions.assertThat(result.size()).isEqualTo(0);
    }


//    @Test(expected = IllegalStateException.class)
//    void testFindHashesWrongString3()
//    {
//        final String hashes = " [409, 3896202z]";
//        COMMITS_UTILS.findHashes(hashes);
//    }


    @Test
    void testGetHashesFromMessagesSuccess() {
        final StashCommit commit1 = mock(StashCommit.class);
        final StashCommit commit2 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);

        when(commit1.getMessage()).thenReturn(" [409c75d8, 3896202a]");
        when(commit2.getMessage())
                .thenReturn(" [409c75d8, 3896202a, 7b58b49e, 9838b3e9, b72ee663, 87e223b7, 6c8688d0]");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(7);
    }


    @Test
    void testGetHashesFromMessagesSuccessWithSvnFormat() {
        final StashCommit commit1 = mock(StashCommit.class);
        final StashCommit commit2 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);

        when(commit1.getMessage()).thenReturn(" [409c75d8, 3896202a]");
        when(commit2.getMessage()).thenReturn(" [rev. 143460]");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(3);
    }


    @Test
    void testGetHashesFromMessagesSuccessWithSvnFormat2() {
        final StashCommit commit1 = mock(StashCommit.class);
        final StashCommit commit2 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);

        when(commit1.getMessage()).thenReturn(" [409c75d8, 3896202a]");
        when(commit2.getMessage()).thenReturn(" [rev. 143460, 143461]");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(4);
    }


    @Test
    void testGetHashesFromMessagesSuccessNewRegExp() {
        final StashCommit commit1 = mock(StashCommit.class);
        final StashCommit commit2 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);

        when(commit1.getMessage()).thenReturn(" [409c75d8, 3896202a] ");
        when(commit2.getMessage()).thenReturn(" [rev. 143460, 143461]");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(4);
    }


    @Test
    void testGetHashesFromMessagesSuccessNewRegExpWithSymbolsAfterHashes() {
        final StashCommit commit1 = mock(StashCommit.class);
        final StashCommit commit2 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);

        when(commit1.getMessage()).thenReturn(" [409c75d8, 3896202a] de");
        when(commit2.getMessage()).thenReturn(" [rev. 143460, 143461]de");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(2);
    }


    @Test
    void testGetHashesFromMessagesSuccessNewRegExpRealCase1() {
        final StashCommit commit1 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1);

        when(commit1.getMessage()).thenReturn("PAN-35594 CachedAcce[s]RateLimiter -&gt; CachedAcce[ss]RateLimiter");
        when(commit1.getId()).thenReturn("TestId");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(0);
    }


    @Test
    void testGetHashesFromMessagesSuccessNewRegExpRealCase2() {
        final StashCommit commit1 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1);

        final String message = "PAN-34484 [com.panbet.web.pages.betslip.ViewAccumulators] ERROR Fail to process " +
                "request. java.util.ConcurrentModificationException: null";

        when(commit1.getMessage()).thenReturn(message);
        when(commit1.getId()).thenReturn("TestId");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(0);
    }


    @Test
    void testGetHashesFromMessagesSuccessNewRegExpRealCase3() {
        final StashCommit commit1 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1);

        final String message = "Revert \"PAN-36801 Расхождение коэффициента АЭ в линии и при постановки " +
                "ставки [2635669a, 1b098674, 9829cfdc, 438224d2]\"\n\n" +
                "This reverts commit 077a76824accb713fdce7fb90f01e82e429d48b3.";

        when(commit1.getMessage()).thenReturn(message);
        lenient().when(commit1.getId()).thenReturn("TestId");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(4);
    }


//    @Test
//    void testGetHashesFromMessagesSuccessNewRegExpWithoutSpaceBeforeHashes()
//    {
//        final StashCommit commit1 = mock(StashCommit.class);
//        final StashCommit commit2 = mock(StashCommit.class);
//        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);
//
//        when(commit1.getMessage()).thenReturn("[409c75d8, 3896202a]");
//        when(commit2.getMessage()).thenReturn("[rev. 143460, 143461]");
//
//        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);
//
//        assertEquals(0, result.size());
//    }


    @Test
    void testGetHashesFromMessagesSuccessNewReg2Strings() {
        final StashCommit commit1 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1);

        final String message = "PAN-35591 CachedAcce[s]RateLimiter -&gt; CachedAcce[ss]RateLimiter [109c75d8, " +
                "3896202a]\nPAN-35594 CachedAcce[s]RateLimiter -&gt; CachedAcce[ss]RateLimiter [209c75d8, 4896202a]";

        when(commit1.getMessage()).thenReturn(message);

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(4);
    }


//    @Test(expected = IllegalStateException.class)
//    void testGetHashesFromMessagesWrongSvnFormat()
//    {
//        final StashCommit commit1 = mock(StashCommit.class);
//        final StashCommit commit2 = mock(StashCommit.class);
//        final Collection<StashCommit> commits = Lists.newArrayList(commit1, commit2);
//
//        when(commit1.getMessage()).thenReturn(" [409c75d8, 3896202a]");
//        when(commit2.getMessage()).thenReturn(" [reyv. 143460]");
//        when(commit2.getId()).thenReturn("some commitId");
//
//        COMMITS_UTILS.getHashesFromMessages(commits);
//    }


//    @Test(expected = IllegalStateException.class)
//    void testGetHashesFromMessagesWrongHashesWithException()
//    {
//        final StashCommit commit1 = mock(StashCommit.class);
//        final Collection<StashCommit> commits = Lists.newArrayList(commit1);
//
//        when(commit1.getMessage()).thenReturn(" [409c75d8, 3802az]");
//        when(commit1.getId()).thenReturn("someCommitHash");
//
//        COMMITS_UTILS.getHashesFromMessages(commits);
//    }


    @Test
    void testGetHashesFromMessagesWrongHashesWithWarningToLog() {
        final StashCommit commit1 = mock(StashCommit.class);
        final Collection<StashCommit> commits = Lists.newArrayList(commit1);

        when(commit1.getMessage()).thenReturn("409c75d8, 3802az]");
        when(commit1.getId()).thenReturn("someCommitHash");

        final Collection<String> result = COMMITS_UTILS.getHashesFromMessages(commits);

        Assertions.assertThat(result.size()).isEqualTo(0);
        verify(commit1).getId();
    }


    @Test
    void testHowLongCommitedLessThanAMinuteAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS) * 30;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo("less than a minute ago");
    }


    @Test
    void testHowLongCommitedMinuteAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(" minute ago");
    }


    @Test
    void testHowLongCommitedMinutesAgo() {
        final int countMinutes = 30;
        final Long time =
                System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES) * countMinutes;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(countMinutes + " minutes ago");
    }


    @Test
    void testHowLongCommitedHourAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS);
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(" hour ago");
    }


    @Test
    void testHowLongCommitedHoursAgo() {
        final int countHours = 5;
        final Long time =
                System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS) * countHours;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(countHours + " hours ago");
    }


    @Test
    void testHowLongCommitedDayAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS);
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(" day ago");
    }


    @Test
    void testHowLongCommitedDaysAgo() {
        final int countDays = 2;
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * countDays;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(countDays + " days ago");
    }


    @Test
    void testHowLongCommitedWeekAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 7;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(" week ago");
    }


    @Test
    void testHowLongCommitedWeeksAgo() {
        final int countWeeks = 2;
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * countWeeks * 7;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(countWeeks + " weeks ago");
    }


    @Test
    void testHowLongCommitedMonthAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 30;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(" month ago");
    }


    @Test
    void testHowLongCommitedMonthsAgo() {
        final int countMonths = 3;
        final Long time =
                System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * countMonths * 30;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(countMonths + " months ago");
    }


    @Test
    void testHowLongCommitedYearAgo() {
        final Long time = System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * 365;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(" year ago");
    }


    @Test
    void testHowLongCommitedYearsAgo() {
        final int countYears = 3;
        final Long time =
                System.currentTimeMillis() - TimeUnit.MILLISECONDS.convert(1, TimeUnit.DAYS) * countYears * 365;
        final Date date = new Date(time);
        when(commit.getAuthorTimestamp()).thenReturn(date);

        Assertions.assertThat(COMMITS_UTILS.howLongCommited(commit)).isEqualTo(countYears + " years ago");
    }


    @Test
    void testFindHashesWithoutCheckWithSevenValidHashes() {
        final String message = " [409c75d8, 3896202a, 7b58b49e, 9838b3e9, b72ee663, 87e223b7, 6c8688d0]";
        final Collection<String> hashesWithoutCheck = COMMITS_UTILS.findHashesWithoutCheck(message);

        Assertions.assertThat(hashesWithoutCheck).hasSize(7);
    }


    @Test
    void testFindHashesWithoutCheckWithTwoSVNValidHashes() {
        final String message = " [rev. 143460, 143461]";
        final Collection<String> hashesWithoutCheck = COMMITS_UTILS.findHashesWithoutCheck(message);

        Assertions.assertThat(hashesWithoutCheck).hasSize(2);
    }


//    @Test
//    void testFindHashesWithoutCheckWithNotValidHashes()
//    {
//        final String message = " [409c75d8 3802aaa]";
//        final Collection<String> hashesWithoutCheck = COMMITS_UTILS.findHashesWithoutCheck(message);
//        System.out.println(hashesWithoutCheck);
//
//        assertEquals(0, hashesWithoutCheck.size());
//    }
}