<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ACCESS_MANTICORE-DEBUG')" var="hasManticoreDebug"/>

<c:set var="taskReasonCounterAll" scope="session" value="0"/>
<c:forEach var="reasonCount" items="${taskReasonsCounter.values()}">
    <c:set var="taskReasonCounterAll" scope="session" value="${taskReasonCounterAll + reasonCount}"/>
</c:forEach>

<c:if test="${hasManticoreDebug}">
    <div id="not-in-master-test-load-time" class="text-right color-gray font-style-italic font-size-smaller"
         style="margin-top: 5px"></div>
</c:if>
<div id="not-in-master-test-cache-update-time-ago" class="text-right color-gray font-style-italic font-size-smaller"></div>
<table id="nimtFilters" class="margin-auto" style="margin-bottom: 13px;">
    <tr>
        <td rowspan="2">
            <div class="checkbox checkbox-inline" style="float: left;">
                <input id="nimt-filter-all" type="checkbox" value="all"
                       onclick="toggleSelectAll('nimt-filter-', $(this), function() { filterNimt() })"><label
                    for="nimt-filter-all"
                    style="padding-right: 30px">All&thinsp;<span class="counter">${taskReasonCounterAll}</span></label>
            </div>
        </td>
        <td class="align-right" style="word-spacing: 0.01em;">
            <c:forEach var="noteType" items="${taskReasonsCounter.keySet()}">
                <c:if test="${noteType.colorId == 1}">
                    <div class="checkbox checkbox-inline">&nbsp;&nbsp;
                        <input id="nimt-filter-${noteType.type}" type="checkbox" value="${noteType.type}"
                               onclick="filterNimt()"><label
                            class="closed" for="nimt-filter-${noteType.type}">${noteType.type}&thinsp;<span
                            class="counter">${taskReasonsCounter.get(noteType)}</span></label>
                    </div>
                </c:if>
            </c:forEach>
        </td>
    </tr>
    <tr>
        <td class="align-right" style="word-spacing: 0.01em;">
            <c:forEach var="noteType" items="${taskReasonsCounter.keySet()}">
                <c:if test="${noteType.colorId == 2}">
                    <div class="checkbox checkbox-inline">&nbsp;&nbsp;
                        <input id="nimt-filter-${noteType.type}" type="checkbox" value="${noteType.type}"
                               onclick="filterNimt()"><label
                            class="tested" for="nimt-filter-${noteType.type}">${noteType.type}&thinsp;<span
                            class="counter">${taskReasonsCounter.get(noteType)}</span></label>
                    </div>
                </c:if>
            </c:forEach>
        </td>
    </tr>
</table>

<table>
    <tr>
        <c:if test="${issueListRTM.size()>0}">
            <td style="vertical-align: top">
                <table id="nimt-table-rtm">
                    <tr class="m-label default-selected-color">
                        <th></th>
                        <th colspan="2">ELIGIBLE FOR MASTER_TEST</th>
                        <th></th>
                    </tr>
                    <c:forEach var="priorityKey" items="${groupedIssues.keySet()}" varStatus="number">
                        <c:set var="priorityGroup" value="${groupedIssues.get(priorityKey)}"/>
                        <c:if test="${number.index != 0}">
                            <tr><td colspan="4">&nbsp;</td></tr>
                        </c:if>
                        <tr><td colspan="4">${priorityKey.getName()}</td></tr>
                        <c:forEach var="rtmIssuesGroup" items="${priorityGroup}" varStatus="status">
                            <c:set var="issuesList" value="${rtmIssuesGroup.issues}"/>
                            <c:if test="${status.index != 0}">
                                <tr><td colspan="4">&nbsp;</td></tr>
                            </c:if>
                            <tr>
                                <td colspan="4" class="text-right color-gray font-style-italic font-size-smaller">${rtmIssuesGroup.title}</td>
                            </tr>
                            <c:forEach var="issue" items="${issuesList}" varStatus="counter">
                                <c:set var="noteTypes" scope="session" value=""/>
                                <c:forEach var="note" items="${issue.notes}">
                                    <c:set var="noteTypes" scope="session"
                                           value="${noteTypes.concat(', ').concat(note.getNoteType().getType())}"/>
                                </c:forEach>
                                <tr id="issue-${issue.key}"
                                    class="mark-out <c:if test="${counter.index > 0}">upper-border</c:if>"
                                    noteTypes="${noteTypes}">
                                    <c:set var="styleRTM" scope="session" value=''/>
                                    <c:set var="plus" scope="session" value='
                    <a id="plus-${issue.key}" href="/workbench/merge_task_to_branch/task/${issue.key}" target="_blank">
                        <i class="fa fa-plus clickable"></i>
                    </a>'/>
                                    <td>
                                            ${plus}
                                    </td>
                                    <c:set var="commentExistsAsterisk" scope="session" value=""/>
                                    <c:if test="${comments.contains(issue.key)}">
                                        <c:set var="commentExistsAsterisk" scope="session" value="<span style=\"color: red\">*</span>"/>
                                    </c:if>
                                    <td class="white-space-nowrap no-padding m-label">
                                        <img src="<c:out value="${issue.priority.iconUrl}" escapeXml="false" />"
                                             title="${issue.priority.name}"/>
                                        <img src="<c:out value="${issue.issueType.iconUrl}" escapeXml="false" />"
                                             title="${issue.issueType.name}"/>
                                        <img src="<c:out value="${issue.status.iconUrl}" escapeXml="false" />"
                                             title="${issue.status.name}"/><a href="${issue.link}"
                                                                              target="_blank" ${styleRTM}>${issue.key}</a>${commentExistsAsterisk}
                                    </td>
                                    <td class="no-padding">
                                        <ul class="list-style-type-none">
                                            <c:set var="reasonStyle" scope="session" value=""/>
                                            <c:forEach var="note" items="${issue.notes}">
                                                <c:choose>
                                                    <c:when test="${note.colorId == 1}">
                                                        <c:set var="reasonStyle" scope="session" value="reason-plus"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:set var="reasonStyle" scope="session" value="reason-minus"/>
                                                    </c:otherwise>
                                                </c:choose>
                                                <li class="${reasonStyle}">${note.message}</li>
                                            </c:forEach>
                                        </ul>
                                    </td>
                                    <td>
                                        <i class="fa fa-pencil fa-lg clickable" data-toggle="modal"
                                           onclick="generateCommentDialog('${issue.key}', 'NIMT')">
                                        </i>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:forEach>
                    </c:forEach>
                </table>
            </td>
        </c:if>
        <td style="vertical-align: top">
            <table id="nimt-table-not-rtm">
                <tr class="m-label default-selected-color">
                    <th colspan="2">NOT ELIGIBLE FOR MASTER_TEST</th>
                    <th></th>
                </tr>
                <c:forEach var="issue" items="${issueListNotRTM}" varStatus="counter">
                    <c:set var="noteTypes" scope="session" value=""/>
                    <c:forEach var="note" items="${issue.notes}">
                        <c:set var="noteTypes" scope="session"
                               value="${noteTypes.concat(', ').concat(note.getNoteType().getType())}"/>
                    </c:forEach>
                    <tr id="issue-${issue.key}" class="mark-out upper-border" noteTypes="${noteTypes}">
                        <c:set var="styleNotRTM" scope="session"
                               value='style="text-decoration: line-through; color: gray; font-style: italic;"'/>
                        <c:set var="commentExistsAsterisk" scope="session" value=""/>
                        <c:if test="${comments.contains(issue.key)}">
                            <c:set var="commentExistsAsterisk" scope="session" value="<span style=\"color: red\">*</span>"/>
                        </c:if>
                        <td class="white-space-nowrap no-padding m-label">
                            <img src="<c:out value="${issue.priority.iconUrl}" escapeXml="false" />"
                                 title="${issue.priority.name}"/>
                            <img src="<c:out value="${issue.issueType.iconUrl}" escapeXml="false" />"
                                 title="${issue.issueType.name}"/>
                            <img src="<c:out value="${issue.status.iconUrl}" escapeXml="false" />"
                                 title="${issue.status.name}"/><a href="${issue.link}" target="_blank"
                                 ${styleNotRTM}>${issue.key}</a>${commentExistsAsterisk}
                        </td>
                        <td class="no-padding">
                            <ul class="list-style-type-none">
                                <c:set var="reasonStyle" scope="session" value=""/>
                                <c:forEach var="note" items="${issue.notes}">
                                    <c:choose>
                                        <c:when test="${note.colorId == 1}">
                                            <c:set var="reasonStyle" scope="session" value="reason-plus"/>
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="reasonStyle" scope="session" value="reason-minus"/>
                                        </c:otherwise>
                                    </c:choose>
                                    <li class="${reasonStyle}">${note.message}</li>
                                </c:forEach>
                            </ul>
                        </td>
                        <td>
                            <i class="fa fa-pencil fa-lg clickable" data-toggle="modal"
                               onclick="generateCommentDialog('${issue.key}', 'NIMT')">
                            </i>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<script type="text/javascript">

    <c:if test="${hasManticoreDebug}">
        $("#not-in-master-test-load-time").html('Loaded in ' + wrapNumbersWithBold('${timer.stop()}') + '.');
    </c:if>

    var nimtViewDataCreationTime = ${nimtViewDataCreationTime};
    updateTimestamps();
    setInterval(updateTimestamps, 5000);


    function updateTimestamps()
    {
        var currentTime = new Date().getTime();
        $("#not-in-master-test-cache-update-time-ago").html('Cache updated ' +
                wrapNumbersWithBold(millisToString(currentTime - nimtViewDataCreationTime)) + '.');
    }


    if (${branchMasterTestCounter != null}) {
        setTabBranchMasterTestNumber(${branchMasterTestCounter});
    }
    if (${notInMasterTestCounter != null}) {
        setTabNotInMasterTestNumber(${notInMasterTestCounter});
    }

    function filterNimt() {
        $('table[id^="nimt-table-"]').each(function () {
            $(this).attr("style", "display:none");
        });
        $('tr[id^="issue-"]').each(function () {
            $(this).attr("style", "display:none");
        });
        $('input[id^="nimt-filter-"]:checked').each(function () {
            var includeNoteType = $(this).val();
            $('tr[id^="issue-"]').each(function () {
                var noteTypes = $(this).attr('noteTypes');
                if (noteTypes.indexOf(includeNoteType) > -1) {
                    $(this).attr("style", "");
                }
            })
        });
        $('table[id^="nimt-table-"]').each(function () {
            if ($(this).find("tr[style='']").size() > 0) {
                $(this).attr("style", "");
            }
            else {
                $(this).attr("style", "display:none");
            }
        });
    }

    $("input[type='checkbox'][id='nimt-filter-all']").click();
</script>


