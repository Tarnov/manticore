<%@ attribute name="issueDataList" required="true" type="java.util.List<com.panbet.manticore.util.bl.release.domain.IssueData>"%>
<%@ attribute name="title" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="manticore" tagdir="/WEB-INF/tags" %>

<c:if test="${issueDataList.size() > 0}">
    ${title}
    <ul class="list-style-type-none">
        <c:forEach var="issueData" items="${issueDataList}">
            <li>
                <manticore:tabBuilder count="0"/>
                <img src="${issueData.typeIconUri}" title="${issueData.typeName}">
                <a href="${issueData.issueUri}" target="_blank">${issueData.key}</a>
                    ${issueData.summary}
            </li>
            <c:forEach var="subtask" items="${issueData.subtasks}">
                <li>
                    <manticore:tabBuilder count="1"/>
                    <img src="${subtask.typeIconUri}" title="${subtask.typeName}">
                    <a href="${subtask.issueUri}" target="_blank">${subtask.key}</a>
                        ${subtask.summary}
                </li>
            </c:forEach>
        </c:forEach>
    </ul>
    <br><br>
</c:if>