<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ACCESS_WORKBENCH-MERGE-TASK-TO-BRANCH-TASK-OPERATIONS')"
                    var="isAccessWorkbenchMergeTaskToBranchTaskOperations"/>

<c:set var="taskListMargin" scope="session" value="9px"/>

<c:if test="${isConfigManager}">
    <c:set var="taskListMargin" scope="session" value="23px"/>
</c:if>

<div id="master-test-load-time" class="text-right color-gray font-style-italic font-size-smaller"
     style="margin-top: 5px">
</div>

<div id="mttb-error" class="error"></div>

<table class="border-collapse margin-auto" style="margin-top: ${taskListMargin}; width :100%">
    <tr>
<c:choose>
    <c:when test="${issue == null}">
        <td class="m-label error-color error-text-shadow" style="font-size: 20px">ISSUE DOES NOT EXISTS</td>
    </tr>
</table>
    </c:when>
    <c:otherwise>
        <c:if test="${! issue.getParent().isPresent()}">
            <c:set var="commentExistsAsterisk" scope="session" value=""/>
            <c:if test="${comments.contains(issue.key)}">
                <c:set var="commentExistsAsterisk" scope="session" value="<span style=\"color: red\">*</span>"/>
            </c:if>
            <td style="width: 15%"></td>
            <td>
                <div id="${issue.key}" title="${issue.key}&nbsp;${issueEscapedSummary}" class="m-label cursor-pointer text-center ${viewStyles.taskStyle.style}" style="font-size: 20px">
                    <img src="${issue.status.iconUrl}" title="${issue.status.name}"><a href="${jiraHref}/${issue.key}" class="${viewStyles.taskStyle.selectedStyle}" target="_blank">${issue.key}</a>${commentExistsAsterisk}&nbsp;${issueEscapedSummary}
                </div>
                <ol class="align-left m-label" style="font-size: 15px; margin-top: 5px">
                    <c:forEach var="subTask" items="${issue.subtasks}">
                        <c:set var="subtaskStyle" value="${viewStyles.subtaskStyleMap[subTask]}"/>
                        <li id="${subTask.key}" title="${subTask.key}&nbsp;${fn:replace(subTask.summary, '\"', '&quot;')}" class="cursor-pointer ${subtaskStyle.style}">
                            <img src="${subTask.status.iconUrl}" title="${subTask.status.name}"><a href="${jiraHref}/${subTask.key}" class="${subtaskStyle.selectedStyle}" target="_blank">${subTask.key}</a>
                            <c:if test="${comments.contains(subTask.key)}">
                                <span style="color: red">*</span>
                            </c:if> ${subTask.summary}
                        </li>
                    </c:forEach>
                </ol>
            <div id="tagMap" title="tagMap" class="m-label text-center" style="font-size: 20px">
                <c:forEach var="tag" items="${tagNumberMap}">
                    <span class="<c:out value='${tag.value}'/>-color"><c:out value='${tag.key}'/></span>
                </c:forEach>
            </div>
        </c:if>
        </td>
        <td class="text-right" style="width: 15%">
            <span>
                        <i class="fa fa-pencil fa-lg clickable" data-toggle="modal"
                           onclick="generateCommentDialog('${key}', 'MTTB')">
                        </i>
            </span>
        </td>
    </tr>
</table>

        <c:if test="${isAccessWorkbenchMergeTaskToBranchTaskOperations}">
    <div id="taskOperationsContainer">
        <table class="border-collapse margin-auto" style="margin-bottom: 30px">
            <tr>
                <c:if test="${viewButtons.showRequestUpdate}">
                    <!-- Request update -->
                    <td>
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button id="requestUpdateButton" type="button" class="btn btn-primary"
                                        onclick="requestUpdate($('#requestUpdateSelect option:selected').val())"
                                        disabled>
                                    <i class="fa fa-level-down"></i> Request update
                                </button>
                                <select id="requestUpdateSelect" class="selectpicker" data-style="btn-primary" data-width="155px"
                                        title="Update type" onchange="$('#requestUpdateButton').attr('disabled', false)">
                                    <option data-hidden="true" data-content="<span class='bootstrap-select-disabled'>Update type</span>"></option>
                                    <option value="rebase">rebase</option>
                                    <option value="merge">merge</option>
                                    <option value="cherry-pick" disabled>cherry-pick</option>
                                </select>
                            </div>
                        </div>
                    </td>
                </c:if>
                <c:if test="${viewButtons.showMarkMerged}">
                    <!-- Mark merged -->
                    <td>
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button id="markMergedButton" type="button" class="btn btn-primary" onclick="markMerged()">
                                    <i class="fa fa-level-up"></i> Mark merged
                                </button>
                            </div>
                        </div>
                    </td>
                </c:if>
                <c:if test="${viewButtons.showLinkConflicts}">
                    <!-- Link conflicts -->
                    <td>
                        <div class="input-group">
                            <input id="conflictTaskList" class="form-control text-center m-label" placeholder="Task list"
                                   name="selectedBranch" type="text" onfocus="this.placeholder = ''"
                                   onblur="this.placeholder = 'Task list'" onkeyup="linkConflictsValidate()"/>
                            <div class="input-group-btn">
                                <button id="linkConflictsButton" type="button" class="btn btn-primary" disabled
                                        onclick="linkConflicts()">
                                    <i class="fa fa-exchange"></i> Link conflicts
                                </button>
                            </div>
                        </div>
                    </td>
                </c:if>
            </tr>
        </table>
    </div>
</c:if>

<table>
    <tr class="m-label default-selected-color">
        <td>
            <div class="checkbox" style="margin-bottom: 10px">
                <input type="checkbox" id="selectAll" name=""
                       onclick="toggleSelectAll('checkbox-', $(this), function() { createMttbStrings() })"> <label
                    for="selectAll"></label>
            </div>
        </td>
        <th>Revision</th>
        <th>User</th>
        <th>Repo</th>
        <th>Branch</th>
        <th>Message</th>
    </tr>
    <c:forEach var="commit" items="${commits}">
        <c:set var="id" scope="session" value="${commit.id}" />
        <c:set var="rowColorClass" scope="session" value="base-text-color" />
        <c:set var="repoColorClass" scope="session" value="base-text-color" />
        <c:forEach var="branch" items="${commit.branches}">
            <c:choose>
                <c:when test="${branch.displayId == 'master' or branch.displayId == 'master_rc'}">
                    <c:set var="rowColorClass" scope="session" value="closed" />
                </c:when>
                <c:when test="${branch.displayId == 'master_test' or branch.displayId == 'master_autotest'}">
                    <c:set var="rowColorClass" scope="session" value="dark-text-color" />
                </c:when>
                <c:when test="${branch.displayId == 'master_hotfix'}">
                    <c:set var="rowColorClass" scope="session" value="rtm" />
                </c:when>
                <c:otherwise>
                    <c:set var="rowColorClass" scope="session" value="base-text-color" />
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${commit.repository.project.key == projectKey}"/>
            <c:when test="${commit.repository.project.key == 'CONF'}">
                <c:set var="repoColorClass" scope="session" value="config-color" />
            </c:when>
            <c:otherwise>
                <c:set var="rowColorClass" scope="session" value="disabled-color" />
            </c:otherwise>
        </c:choose>
        <tr class="upper-border mark-out ${rowColorClass}">
            <td>
                <c:if test="${!commit.merge}">
                    <div class="checkbox">
                        <input type="checkbox" id="checkbox-${commit.id}"
                               name="${commit.id}" onclick="createMttbStrings()"> <label
                            for="checkbox-${commit.id}"></label>
                    </div>
                </c:if>
            </td>
            <td class="text-center vertical-align-middle" style="line-height:75%; padding-bottom: 5px; padding-top: 5px"
                title="${commit.formattedTimeStamp}">
                <a target="_blank" class="m-label transition revision" style="position: relative"
                   href="${commit.selfURI}">${commit.id}</a><br> <span
                    class="nowrap font-size-xx-small">${commit.howLongCommited()}</span>
            </td>
            <td class="text-center vertical-align-middle white-space-nowrap">
                <c:choose>
                    <c:when test="${commit.author.active}">
                        <c:set var="authorName" scope="session" value="${commit.author.authorDetails.get().displayName}"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="authorName" scope="session" value="${commit.author.name}"/>
                    </c:otherwise>
                </c:choose>
                <c:choose>
                    <c:when test="${commit.author.emailAddress.isPresent()}">
                        <span id="author-${id}" title="${authorName}&nbsp;<${commit.author.emailAddress.get()}>">${authorName}</span>
                    </c:when>
                    <c:otherwise>
                        <span id="author-${id}" title="${authorName}&nbsp;<Email not returned>">${authorName}</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="text-center ${repoColorClass}">${commit.repository.project.key}:${commit.repository.name}</td>
            <td id="branch-${id}" class="white-space-nowrap text-center vertical-align-middle">
                <c:forEach var="branchName" items="${commit.branchNamesCollection}">
                    <div><a target="_blank" style="color: inherit"
                            href="${stashHref}projects/${commit.repository.project.key}/repos/${commit.repository.slug}/compare/diff?targetBranch=refs%2Fheads%2Fmaster&sourceBranch=refs%2Fheads%2F${branchName}">${branchName}</a>
                    </div>
                </c:forEach>
            </td>
            <td>
                <div id="msg-${id}" class="word-wrap-break-word" style="overflow: auto">${commit.messageWithLinks}</div>
            </td>
        </tr>

    </c:forEach>
</table>

<div class="help display-none">
    <%--<legend class="help">CMD</legend>--%>
    <table class="auto-margin">
        <tr>
            <td style="padding-top: 10px;">
                <div class="checkbox checkbox-inline default-selected-color">
                    <input id="revert" type="checkbox" name="revert" onclick="createMttbStrings()"> <label
                        for="revert">Revert</label>
                </div>
                <div class="checkbox checkbox-inline default-selected-color">
                    <input id="no-edit" type="checkbox" name="no-edit" onclick="createMttbStrings()"> <label
                        for="no-edit">No-edit</label>
                </div>
            </td>
        </tr>
    </table>
    <div id="mttbCmd" style="margin-left: 20px; margin-right: 20px; margin-bottom: 15px"></div>
</div>
<div class="help display-none">
    <%--<legend class="help">COMMENT</legend>--%>
    <table class="auto-margin">
        <tr>
            <td style="padding-top: 10px;">
                <div class="checkbox checkbox-inline default-selected-color">
                    <input id="squash" type="checkbox" name="squash" onclick="createMttbStrings()"> <label
                        for="squash">Squash</label>
                </div>
                <div class="checkbox checkbox-inline default-selected-color">
                    <input id="hotfix" type="checkbox" name="hotfix" onclick="createMttbStrings()"> <label
                        class="transition" for="hotfix">Hotfix</label>
                </div>
            </td>
        </tr>
    </table>
    <div id="mttbComment" style="margin-left: 20px; margin-right: 20px; margin-bottom: 15px"></div>
</div>
    </c:otherwise>
</c:choose>


<script type="text/javascript">
    $("#master-test-load-time").html('Loaded in ' + '${timer.stop()}' + '.');
    $("#mttb-error").html(showErrorBlockWithMessage('${errorMessage}'));


    /* Set page title */
    setPageTitle('WORKBENCH / MERGE TASK TO BRANCH / ' + taskOrBranch + ' ' + $('#taskID').val());

    // Go to task or branch by clicking on corresponding task
    $("div.m-label,li").click(function(e) {
        if(e.shiftKey && e.altKey) {
            let task = $('#taskID').val().toUpperCase();
            let projectKey = getProjectFromJiraTask(task);
            let repoKey = getRepoFromJiraTask(task);
            $('#taskID').val(e.target.id);
            loadMergeTaskToBranchContainerController(projectKey, repoKey, task);
        } else if (e.shiftKey) {
            $('#taskID').val(e.target.id);
            loadMergeTaskToBranchContainerController('', '', $('#taskID').val());
        }
    });

    // Link conflicts on press enter, when conflictTaskList input is focused
    $("#conflictTaskList").focus().keypress(function (event) {
        if (event.keyCode == 13)
        {
            if (!$("#linkConflictsButton").is(':disabled'))
            {
                $("#linkConflictsButton").click();
            }
        }
    });

    // Dynamically resize commit message block
    var mttbWidth = 14;
    $("tr.mark-out").first().find("td").slice(0, 5).each(function () {
        mttbWidth += $(this).width() + 14
    });
    var initialCommitMessageBlockWidth = ($("[id$=-body]").width() == $("tr.mark-out").width()) ? '' : $("div.word-wrap-break-word").first().width();
    var resizeMTTB = function () {
        var newCommitMessageBlockWidth = $(window).width() - $("div.navbar-static-side").width() - 63 - mttbWidth
        if (initialCommitMessageBlockWidth == '' || newCommitMessageBlockWidth <= initialCommitMessageBlockWidth) {
            $("div.word-wrap-break-word").width(newCommitMessageBlockWidth)
        } else {
            $("div.word-wrap-break-word").width(initialCommitMessageBlockWidth);
        }
    };
    resizeMTTB();
    $(window).resize(function () {
        resizeMTTB();
    });

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

</script>
