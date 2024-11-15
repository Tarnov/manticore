<%@ attribute name="taskElements" required="true" type="java.util.List<com.panbet.manticore.util.bl.eligiblefor.view.EligibleForViewElement>"%>
<%@ attribute name="title" required="true" %>
<%@ attribute name="childrenElementsMap" required="true" type="java.util.Map<java.lang.String,com.panbet.manticore.util.bl.eligiblefor.view.EligibleForViewElement>" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="manticore" tagdir="/WEB-INF/tags" %>

<c:if test="${taskElements.size() > 0}">
    ${title}
    <br>
    <ul class="list-style-type-none">
        <c:forEach var="eligibleElement" items="${taskElements}">
            <li>
            <manticore:tabBuilder count="${eligibleElement.depth}"/>

            <c:if test="${eligibleElement.eligibleForNode.present}">
                <img src="${eligibleElement.eligibleForNode.get().eligibleIssue.issueType.iconUri}" title="${eligibleElement.eligibleForNode.get().eligibleIssue.issueType.name}">
                <a href="${eligibleElement.eligibleForNode.get().issueLinkURI}"
                   target="_blank">${eligibleElement.eligibleForNode.get().eligibleIssue.key}</a>
                ${eligibleElement.eligibleForNode.get().eligibleIssue.summary}
                </li>
                <c:forEach var="viewElement" items="${childrenElementsMap.get(eligibleElement.eligibleForNode.get().eligibleIssue.key)}">
                    <li>
                        <manticore:tabBuilder count="${viewElement.depth}"/>

                        <c:if test="${viewElement.eligibleForNode.present}">
                            <img src="${viewElement.eligibleForNode.get().eligibleIssue.issueType.iconUri}" title="${viewElement.eligibleForNode.get().eligibleIssue.issueType.name}">
                            <a href="${viewElement.eligibleForNode.get().issueLinkURI}"
                               target="_blank">${viewElement.eligibleForNode.get().eligibleIssue.key}</a>
                            ${viewElement.eligibleForNode.get().eligibleIssue.summary}
                        </c:if>
                    </li>
                </c:forEach>
            </c:if>
        </c:forEach>
    </ul>
    <br>
</c:if>