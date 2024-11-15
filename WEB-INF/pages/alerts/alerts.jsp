<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:choose>
    <c:when test="${fn:length(alerts) > 0}">
        <div>
            <table class="border-collapse">
                <c:forEach items="${alerts}" var="viewAlert">
                    <tbody class="mark-out" <c:if test='${viewAlert.alertedToday}'>style="background-color: #3c2c2c"</c:if>>
                        <tr class="color-gray upper-border-bold">
                            <td nowrap class="default-selected-color m-label text-center">
                                <div>
                                    <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${viewAlert.receivedDate}"/>
                                </div>
                            </td>
                            <td nowrap class="text-center">
                                ${viewAlert.summary}
                            </td>
                            <td>
                                <p style="white-space: pre-line">${viewAlert.htmlDescription}</p>
                            </td>
                            <td nowrap id="issueLinkForDbId-${viewAlert.dbId}" class="text-right m-label">
                                <c:choose>
                                    <c:when test="${viewAlert.jiraIssue.isPresent()}">
                                        <c:set var="jiraIssue" value="${viewAlert.jiraIssue.get()}"/>
                                        <c:set var="status" value="${viewAlert.status.get()}"/>
                                        <img src="${status.iconUrl}" title="${status.name}"><a href='${jiraUri}browse/${jiraIssue.key}' target="_blank">${jiraIssue.key}</a>
                                    </c:when>
                                    <c:otherwise>
                                        <div>
                                            <span style="color: red; text-shadow: 0 0 13px red">ERROR</span>
                                            <i class="fa fa-refresh fa-lg clickable"
                                                onclick="recreateTask(${viewAlert.dbId});"></i>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <c:if test="${fn:length(viewAlert.alertComments) > 0}">
                            <c:forEach items="${viewAlert.alertComments}" var="comment">
                                <tr class="color-gray vertical-align-middle upper-border">
                                    <td></td>
                                    <td nowrap class="text-right">
                                        <fmt:formatDate pattern="dd-MM-yyyy HH:mm:ss" value="${comment.receivedDate}"/>
                                    </td>
                                    <td>
                                        <p style="white-space: pre-line">${comment.htmlText}</p>
                                    </td>
                                    <td nowrap id="commentLinkForDbId-${comment.dbId}" class="text-right m-label">
                                        <c:if test="${!comment.jiraId.isPresent()}">
                                            <div>
                                                <span style="color: red; text-shadow: 0 0 13px red">ERROR</span>
                                                <c:if test="${viewAlert.jiraIssue.isPresent()}">
                                                    <i class="fa fa-refresh fa-lg clickable"
                                                        onclick="recreateComment(${viewAlert.dbId}, ${comment.dbId});"></i>
                                                </c:if>
                                            </div>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:if>
                    </tbody>
                </c:forEach>
            </table>
        </div>
    </c:when>
    <c:otherwise>
        <div class="text-center">
            <span class="m-label default-selected-color">No issues were found</span>
        </div>
    </c:otherwise>
</c:choose>

<script type="text/javascript">
    function recreateTask(dbId)
    {
        var issueLinkColumn = $("#issueLinkForDbId-" + dbId);
        var currentHtml = issueLinkColumn.html();
        issueLinkColumn.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $.ajax({
            type: "GET",
            url: "/alerts_controller/createIssue",
            data: {dbId: dbId}
        }).done(function (response) {
            issueLinkColumn.html(getHtmlForIssue(response.issueKey, response.statusIconUrl, response.statusName));
            $.each(response.commentDbIdJiraId, function (key, value) {
                $("#commentLinkForDbId-" + key).html('');
            })
        }).fail(function ()
        {
            setTimeout(function () {
                issueLinkColumn.html(currentHtml);
            }, 1000);
        });
    }


    function getHtmlForIssue(issueKey, statusIconUrl, statusName)
    {
        return "<img src=" + statusIconUrl + " title=" + statusName + "><a href='${jiraUri}browse/" + issueKey + "'>" + issueKey + "</a>";
    }


    function recreateComment(issueDbId, commentDbId)
    {
        var commentLinkColumn = $("#commentLinkForDbId-" + commentDbId);
        var currentHtml = commentLinkColumn.html();
        commentLinkColumn.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $.ajax({
            type: "GET",
            url: "/alerts_controller/createComment",
            data: {issueDbId: issueDbId, commentDbId: commentDbId}
        }).done(function (response) {
            fadingSuccess(commentLinkColumn, commentDbId);
        }).fail(function ()
        {
            setTimeout(function () {
                commentLinkColumn.html(currentHtml);
            }, 1000);
        });
    }

    function fadingSuccess(object, commentDbId) {
        var successBlockId = 'success-block-' + commentDbId;
        object.html('<div style="color: #5cb85c; text-shadow: 0 0 13px #5cb85c" id="' + successBlockId + '">' +
                'Success' + '</div>');
        var successBlock = $('#' + successBlockId);
        successBlock.delay(1000).fadeOut(600, function () {
            object.html('');
        });
    }
</script>
