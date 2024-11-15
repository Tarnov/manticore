<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authorize access="hasRole('ACCESS_RESOURCES-ADMIN')" var="isAccessResourceAdmin"/>
<security:authorize access="hasRole('ACCESS_POWER-RESOURCES-USER')" var="isAccessPowerResourcesUser"/>

<c:forEach var="project" items="${taskMovementStatistics}" varStatus="counter">
    <table class="col-lg-3">
        <tr class="default-selected-color">
            <th>
                <a class="primary-color panel-header" href="${project.getUserProfileUri()}">
                        Project lead: ${project.lead.displayName}</a>
            </th>
        </tr>
        <tr class="default-selected-color">
            <th>
                <a class="primary-color panel-header"
                   onclick='showTaskMovementGraph(${project.graphData})'
                ><span class="m-label">${project.key}</span>&nbsp;&nbsp;
                    <img src="<c:out value="${project.avatarUri}" escapeXml="false" />"
                         style="max-width: 24px; max-height: 24px"/>&nbsp;&nbsp;
                        ${project.name}
                </a>
            </th>
        </tr>
        <tr class="upper-border">
            <td>
                <table id="${project.key}">
                    <tr class="m-label default-selected-color">
                        <th>Dates</th>
                        <c:forEach var="column" items="${project.getColumns()}" varStatus="counter">
                            <th colspan="2">${column.name}</th>
                        </c:forEach>
                    </tr>
                    <c:forEach var="date" items="${project.getDates()}" varStatus="counter">
                        <tr class="mark-out upper-border">
                            <td class="m-label default-selected-color align-right">${(date.get(2))+1}.${(date.get(1))}</td>
                            <c:forEach var="column" items="${project.getColumns()}" varStatus="counter">
                                <td class="align-right">
                                    <c:if test="${project.getDifferenceWithPreviousMonth(date,column.name) != ''}">
                                        <c:choose>
                                            <c:when test="${project.getDifferenceWithPreviousMonth(date,column.name).contains('+')}">
                                                                <span class="nowrap font-size-xx-small"
                                                                      style="color:green">${project.getDifferenceWithPreviousMonth(date,column.name)}</span>
                                            </c:when>
                                            <c:when test="${project.getDifferenceWithPreviousMonth(date,column.name).contains('-')}">
                                                                <span class="nowrap font-size-xx-small"
                                                                      style="color:red">${project.getDifferenceWithPreviousMonth(date,column.name)}</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="nowrap font-size-xx-small">${project.getDifferenceWithPreviousMonth(date,column.name)}</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </c:if>
                                </td>
                                <td class="align-right" style="padding-right: 20px">
                                    <c:choose>
                                        <c:when test="${project.getCountByDateAndColumn(date,column.name) == null}">
                                            <a href="${project.getJqlUri(date,column)}" target="_blank"
                                               style="color: red">X</a>
                                        </c:when>
                                        <c:otherwise>
                                            <a href="${project.getJqlUri(date,column)}"
                                               target="_blank">${project.getCountByDateAndColumn(date,column.name)}</a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </td>
        </tr>
    </table>
</c:forEach>

<!-- Modal -->
<div id="taskMovement" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <div class="modal-header">
        <span id="projectName" class="modal-title m-label"></span>
    </div>
    <div class="modal-body">
        <div id="taskMovementGraph"></div>
    </div>
    <div class="modal-footer">
        <button id="close" type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close
        </button>
    </div>
</div>
<!-- /.modal -->
<script type="text/javascript">

    function showTaskMovementGraph(graphData) {
        $('#taskMovement').modal('show');

        var blue = '#0000FF';
        var yellow = '#FFFF00';
        var green = '#008000';
        var red = '#FF0000';

        var graph = $('#taskMovementGraph');
        graph.html('');
        var morrisOptions = {
            element: 'taskMovementGraph',
            data: graphData,
            ykeys: ['created', 'resolved', 'closed', 'backlog'],
            xkey: 'date',
            labels: ['Created', 'Resolved', 'Closed', 'Backlog'],
            lineColors: [blue, yellow, green, red],
            trendLineColors: [blue, yellow, green, red],
            gridTextSize: 10,
            gridTextFamily: 'azoft',
            gridIntegers: true,
            graphFullyInGrid: true,
            numLines: 10,
            parseTime: false,
            ymin: 0

        };

        graph.height(750);

        var chart = new Morris.Line(morrisOptions);
    }
</script>