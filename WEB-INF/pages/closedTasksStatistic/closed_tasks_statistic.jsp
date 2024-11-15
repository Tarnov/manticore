<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<c:forEach var="entry" items="${projects}" varStatus="counter">
    <table id="closedTasksMainTable">
        <tr class="default-selected-color">
            <th>
                <span class="m-label">${entry.key}</span>&nbsp;&nbsp;
            </th>
        </tr>
        <tr class="upper-border">
            <td>
                <table id="${entry.key}">
                    <tr class="m-label default-selected-color">
                        <th>Repo</th>
                        <th>Total tasks</th>
                        <th>Archived closed tasks</th>
                    </tr>
                    <c:forEach var="project" items="${entry.value}" varStatus="counter">
                        <tr class="mark-out upper-border">
                            <td class="m-label default-selected-color align-right">${project.repoSlug}</td>
                            <td class="m-label default-selected-color align-right">${project.totalTasks}</td>
                            <td class="m-label default-selected-color align-right">${project.closedTasks}</td>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>
</c:forEach>