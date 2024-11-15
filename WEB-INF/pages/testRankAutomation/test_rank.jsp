<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div>
    <c:choose>
        <c:when test="${issuePriorities.size() > 0}">
            <table>
                <tr class="m-label">
                    <th></th>
                    <th>Key</th>
                    <th>Summary</th>
                    <c:forEach var="criterion" items="${appliedCriterions}">
                        <th>${criterion.getName()}</th>
                    </c:forEach>
                    <th>Rank</th>
                </tr>

                <c:set var="index" value="1"/>
                <c:forEach var="rankValue" items="${issuePriorities.keySet()}">
                    <tr><td>&nbsp;</td></tr>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <c:forEach begin="0" end="${appliedCriterions.size() - 1}">
                            <td></td>
                        </c:forEach>
                        <td class="default-selected-color text-center" style="font-size: 18px">${rankValue}</td>
                    </tr>

                    <c:forEach var="priority" items="${issuePriorities.get(rankValue)}">
                        <c:set var="key" value="${priority.getIssueKey()}"/>
                        <c:set var="criterionValues" value="${priority.getPriority().getCriterionValues()}"/>
                        <tr class="mark-out upper-border">
                            <td>${index}</td>
                            <td style="white-space: nowrap"><a href="${jiraIssueUrl}/${key}" target="_blank">${key}</a></td>
                            <td>${priority.getSummary()}</td>
                            <c:forEach var="criterion" items="${appliedCriterions}">
                                <td class="text-center">${criterionValues.get(criterion)}</td>
                            </c:forEach>
                            <td class="default-selected-color text-center" style="font-size: 15px">${priority.getTotal()}</td>
                        </tr>
                        <c:set var="index" value="${index + 1}"/>
                    </c:forEach>
                </c:forEach>
            </table>
        </c:when>

        <c:when test="${issuePriorities.size() == 0}">
            <p class="m-label text-center default-selected-color">No issues found</p>
        </c:when>
    </c:choose>
</div>
