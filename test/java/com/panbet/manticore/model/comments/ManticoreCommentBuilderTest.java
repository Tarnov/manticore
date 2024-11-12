package com.panbet.manticore.model.comments;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;


@ExtendWith(MockitoExtension.class)
class ManticoreCommentBuilderTest {
    private static final String issueId = "issueId";

    private static final String message = "message";

    private static final long userId = 1L;

    private static final Integer moduleId = 1;

    @Mock
    private Date timeChange;


    @Test
    void testSuccess() {
        final ManticoreCommentBuilder manticoreCommentBuilder = new ManticoreCommentBuilder();

        final ManticoreComment manticoreComment = manticoreCommentBuilder
                .setIssueId(issueId)
                .setUserId(userId)
                .setMessage(message)
                .setTimeChange(timeChange)
                .setModuleId(moduleId)
                .build();

        Assertions.assertThat(manticoreComment.getModuleId()).isEqualTo(moduleId);
        Assertions.assertThat(manticoreComment.getIssueId()).isEqualTo(issueId);
        Assertions.assertThat(manticoreComment.getMessage()).isEqualTo(message);
        Assertions.assertThat(manticoreComment.getUserId()).isEqualTo(userId);
        Assertions.assertThat(manticoreComment.getTimeChange()).isEqualTo(timeChange);
    }


    @Test
    void testIssueIdFail() {
        ManticoreCommentBuilder builder = new ManticoreCommentBuilder()
                .setUserId(userId)
                .setMessage(message)
                .setTimeChange(timeChange)
                .setModuleId(moduleId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testUserIdFail() {
        ManticoreCommentBuilder builder = new ManticoreCommentBuilder()
                .setIssueId(issueId)
                .setMessage(message)
                .setTimeChange(timeChange)
                .setModuleId(moduleId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testMessageFail() {
        ManticoreCommentBuilder builder = new ManticoreCommentBuilder()
                .setIssueId(issueId)
                .setUserId(userId)
                .setTimeChange(timeChange)
                .setModuleId(moduleId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testTimeChangeFail() {
        ManticoreCommentBuilder builder = new ManticoreCommentBuilder()
                .setIssueId(issueId)
                .setUserId(userId)
                .setMessage(message)
                .setModuleId(moduleId);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }


    @Test
    void testModuleIdFail() {
        ManticoreCommentBuilder builder = new ManticoreCommentBuilder()
                .setIssueId(issueId)
                .setUserId(userId)
                .setMessage(message)
                .setTimeChange(timeChange);
        Assertions.assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(builder::build);
    }
}