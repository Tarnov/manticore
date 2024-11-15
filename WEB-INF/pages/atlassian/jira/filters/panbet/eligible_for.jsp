<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="manticore" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ACCESS_MANTICORE-DEBUG')" var="isManticoreDebug"/>

<div id="eligible-for-${attributeName}-load-time" class="text-right color-gray font-style-italic font-size-smaller"
     style="margin-top: 5px"></div>
<table style="margin: auto">
    <tr>
        <td class="align-center" style="width: 45%; border-bottom: 1px solid #505050">
            <a class="primary-color m-label panel-header" target="_blank"
               href="/atlassian/jira/filters/${projectName}/eligible_for_${attributeName}">Eligible
                for ${attributeName}&nbsp;<span class="counter">${eligibleForAmount}</span></a>
        </td>
        <td class="align-center" style="width: 10%">
            <c:choose>
                <c:when test="${isManticoreDebug}">
                    <div class="align-center">
                        <a href="/panbet_controller/eligible_for_${attributeName}/download"><i class=" fa fa-bug"
                                                                                           style="color: yellow;"></i></a>
                    </div>
                </c:when>
                <c:otherwise>
                    <i class="fa fa-fw"></i>
                </c:otherwise>
            </c:choose>
        </td>
        <td class="align-center" style="width: 45%; border-bottom: 1px solid #505050">
            <a class="primary-color m-label panel-header" target="_blank"
               href="/atlassian/jira/filters/${projectName}/not_eligible_for_${attributeName}">Not eligible
                for ${attributeName}&nbsp;<span class="counter">${notEligibleForAmount}</span></a></td>
    </tr>
    <tr>
        <td style="vertical-align: top; white-space: nowrap">
            <c:if test="${eligibleGroups.size() > 0}">
                <div style="text-align : center">
                    <div style="display: inline-block; text-align: left">
                        <ul class="list-style-type-none">
                            <c:forEach var="eligibleGroup" items="${eligibleGroups}">
                                <c:forEach var="viewElement" items="${eligibleGroup.eligibleForViewElements}">

                                    <c:set var="color" scope="page" value="reason-minus"/>
                                    <c:if test="${viewElement.eligible}">
                                        <c:set var="color" scope="page" value="reason-plus"/>
                                    </c:if>
                                    <li class="${color}">
                                        <manticore:tabBuilder count="${viewElement.depth}"/>


                                        <c:if test="${attributeName == 'rtm'}">
                                            <c:choose>
                                                <c:when test="${viewElement.eligibleForNode.present &&
                                                viewElement.eligibleForNode.get().isAutoRTM() &&
                                                viewElement.depth == 0}">
                                                    <i class="fa fa-magic fa-fw" style="color: yellow;" title="AutoRTM"
                                                       aria-hidden="true"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fa fa-fw"></i>
                                                </c:otherwise>
                                            </c:choose>
                                            <c:choose>
                                                <c:when test="${viewElement.eligibleForNode.present &&
                                                viewElement.eligibleForNode.get().isBlocks() &&
                                                viewElement.getDepth() == 0}">
                                                    <i class="fa fa-exclamation fa-fw error-color"
                                                       title="Blocks not closed tasks"
                                                       aria-hidden="true"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fa fa-fw"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>


                                        <c:if test="${viewElement.depth > 0}">
                                            <c:choose>
                                                <c:when test="${viewElement.description == 'has subtask'}">
                                                    <c:choose>
                                                        <c:when test="${viewElement.eligible}">
                                                            <i class="fa fa-check" style="color: #669866;"
                                                               title="${viewElement.title}"
                                                               aria-hidden="true"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="fa fa-times" style="color: #df7d7d;"
                                                               title="${viewElement.title}"
                                                               aria-hidden="true"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise><span
                                                        title="${viewElement.title}">${viewElement.description}&nbsp;</span></c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <c:if test="${viewElement.eligibleForNode.present}">
                                            <img src="<c:out value="${viewElement.eligibleForNode.get().eligibleIssue.status.iconUrl}" escapeXml="false"/>"
                                                 title="${viewElement.eligibleForNode.get().eligibleIssue.status.name}"/><a
                                                href="${viewElement.eligibleForNode.get().issueLinkURI}"
                                                target="_blank">${viewElement.eligibleForNode.get().eligibleIssue.key}</a>
                                        </c:if>

                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>
        </td>
        <td></td>
        <td style="vertical-align: top; white-space: nowrap">
            <c:if test="${notEligibleGroups.size() > 0}">
                <div style="text-align : center; ">
                    <div style="display: inline-block; text-align: left">
                        <ul class="list-style-type-none">
                            <c:forEach var="notEligibleGroup" items="${notEligibleGroups}">

                                <c:forEach var="viewElement" items="${notEligibleGroup.eligibleForViewElements}">

                                    <c:set var="color" scope="page" value="reason-minus"/>
                                    <c:if test="${viewElement.eligible}">
                                        <c:set var="color" scope="page" value="reason-plus"/>
                                    </c:if>
                                    <li class="${color}">
                                        <manticore:tabBuilder count="${viewElement.depth}"/>


                                        <c:if test="${attributeName == 'merge'}">
                                            <i class="fa fa-fw"></i>
                                            <c:choose>
                                                <c:when test="${notEligibleGroup.isNotInMasterTest() && viewElement.getDepth() == 0}">
                                                    <i class="fa fa-exclamation fa-fw error-color"
                                                       title="Task is not in master_test"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fa fa-fw"></i>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>


                                        <c:if test="${viewElement.depth > 0}">
                                            <c:choose>
                                                <c:when test="${viewElement.description == 'has subtask'}">
                                                    <c:choose>
                                                        <c:when test="${viewElement.eligible}">
                                                            <i class="fa fa-check" style="color: #669866;"
                                                               title="${viewElement.title}"
                                                               aria-hidden="true"></i>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <i class="fa fa-times" style="color: #df7d7d;"
                                                               title="${viewElement.title}"
                                                               aria-hidden="true"></i>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:when>
                                                <c:otherwise><span
                                                        title="${viewElement.title}">${viewElement.description}&nbsp;</span></c:otherwise>
                                            </c:choose>
                                        </c:if>
                                        <c:if test="${viewElement.eligibleForNode.present}">
                                            <img src="<c:out value="${viewElement.eligibleForNode.get().eligibleIssue.status.iconUrl}" escapeXml="false"/>"
                                                 title="${viewElement.eligibleForNode.get().eligibleIssue.status.name}"/><a
                                                href="${viewElement.eligibleForNode.get().issueLinkURI}"
                                                target="_blank">${viewElement.eligibleForNode.get().eligibleIssue.key}</a>
                                        </c:if>

                                    </li>
                                </c:forEach>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </c:if>
        </td>
    </tr>
</table>
<script type="text/javascript">

    if (${eligibleForRtmAmount != null} &&
    ${notEligibleForRtmAmount != null})
    {
        setTabEligibleForRtmAmounts('${eligibleForRtmAmount}', '${notEligibleForRtmAmount}');
    }
    if (${eligibleForMergeAmount != null} &&
    ${notEligibleForMergeAmount != null})
    {
        setTabEligibleForMergeAmounts('${eligibleForMergeAmount}', '${notEligibleForMergeAmount}');
    }
    if (${eligibleForTestAmount != null} &&
    ${notEligibleForTestAmount != null})
    {
        setTabEligibleForTestAmounts('${eligibleForTestAmount}', '${notEligibleForTestAmount}');
    }

    $("#eligible-for-${attributeName}-load-time").html('Loaded in ' + '${timer.stop()}' + '.');

</script>