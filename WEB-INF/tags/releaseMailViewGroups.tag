<%@ attribute name="eligibleGroups" required="true" type="java.util.Collection<com.panbet.manticore.util.bl.eligiblefor.view.EligibleForViewGroup>"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="manticore" tagdir="/WEB-INF/tags" %>

<c:if test="${eligibleGroups.size() > 0}">
    <ul class="list-style-type-none">
        <c:forEach var="eligibleGroup" items="${eligibleGroups}">
            <c:forEach var="viewElement" items="${eligibleGroup.eligibleForViewElements}">
                <c:if test="${viewElement.depth <= 1}">
                    <c:if test="${viewElement.eligibleForNode.present}">
                        <c:if test="${(viewElement.eligibleForNode.get().eligibleIssue.issueType.subtask && viewElement.depth > 0) || viewElement.depth == 0}">
                            <li>
                                <manticore:tabBuilder count="${viewElement.depth}"/>

                                <img src="${viewElement.eligibleForNode.get().eligibleIssue.issueType.iconUri}" title="${viewElement.eligibleForNode.get().eligibleIssue.issueType.name}">
                                <a href="${viewElement.eligibleForNode.get().issueLinkURI}"
                                   target="_blank">${viewElement.eligibleForNode.get().eligibleIssue.key}</a>
                                ${viewElement.eligibleForNode.get().eligibleIssue.summary}
                            </li>
                        </c:if>
                    </c:if>
                </c:if>
            </c:forEach>
        </c:forEach>
    </ul>
</c:if>
