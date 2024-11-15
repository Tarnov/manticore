<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<table class="margin-auto" style="margin-bottom: 15px;">
    <tr>
        <td>
            <div class="text-center m-label default-selected-color">
                From date
            </div>
        </td>
        <td>
            <div class="text-center m-label default-selected-color">
                To date
            </div>
        </td>
        <td>
            <div class="text-center m-label default-selected-color">
                Number of points
            </div>
        </td>
        <td>
            <div class="text-center m-label default-selected-color">
                Chart type
            </div>
        </td>
        <td></td>
    </tr>
    <tr>
        <td>
            <input id="fromDate" class="form-control text-center m-label" placeholder="dd-mm-yyyy"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'dd-mm-yyyy'">
        </td>
        <td>
            <input id="toDate" class="form-control text-center m-label" placeholder="dd-mm-yyyy"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'dd-mm-yyyy'">
        </td>
        <td>
            <input id="numOfPoints" class="form-control text-center m-label" placeholder="Number of points"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Number of grid lines'"
                   onkeypress="redrawOnEnterPress(event)">
        </td>
        <td>
            <select id="chartTypeSelect" class="selectpicker" data-style="btn-primary" data-width="auto" title="Chart type">
                <option value="bar_chart">Bar chart</option>
                <option value="line_chart">Line chart</option>
            </select>
        </td>
        <td>
            <button type="button" class="btn btn-sm btn-primary"
                    onclick="drawGraph(getNewGraphData())">
                <i class="fa fa-refresh"></i> Redraw graph
            </button>
        </td>
    </tr>
</table>

<div id="loadingAnimationDiv"></div>
<div id="currentIssuesRatio"></div>


<script type="text/javascript">
    $('.selectpicker').selectpicker();

    var inTestColor = '#478EC7';
    var eligibleForTestColor = '#D7561F';
    var numOfPointsJquery = $('#numOfPoints');

    //Gets all available data from model and stores it in allAvailableData array
    var allAvailableData = [];
    <c:forEach items="${issuesRatio}" var="currentRatio">
        <fmt:formatDate pattern="dd-MM-yyyy" value="${currentRatio.date}" var="formattedDate" />
        allAvailableData.push({date: '${formattedDate}', inTest: ${currentRatio.issuesInTest},
            eligibleForTest: ${currentRatio.issuesEligibleForTest}, longDate: ${currentRatio.date.time}});
    </c:forEach>

    var maxNumberOfDaysDisplayed = 28;
    var startDataIndex = allAvailableData.length > maxNumberOfDaysDisplayed ?
        allAvailableData.length - (maxNumberOfDaysDisplayed + 1) : 0;

    if (allAvailableData.length != 0) {
        var datepickerOptions = {
            orientation: 'bottom',
            format: "dd-mm-yyyy",
            startDate: allAvailableData[0].date,
            endDate: allAvailableData.last().date,
            weekStart: 1
        };
        var fromDate = $('#fromDate');
        var toDate = $('#toDate');
        fromDate.datepicker(datepickerOptions).datepicker('setDate', allAvailableData[startDataIndex].date);
        toDate.datepicker(datepickerOptions).datepicker('setDate', allAvailableData.last().date);

        fromDate.datepicker().on('changeDate', function () {
            toDate.data('datepicker').setStartDate(fromDate.datepicker('getDate'));
            var newGraphData = getNewGraphData();
            if (numOfPointsJquery.val() > newGraphData.length) {
                numOfPointsJquery.val(newGraphData.length);
            }
            drawGraph(newGraphData);
        });
        toDate.datepicker().on('changeDate', function () {
            fromDate.data('datepicker').setEndDate(toDate.datepicker('getDate'));
            var newGraphData = getNewGraphData();
            if (numOfPointsJquery.val() > newGraphData.length) {
                numOfPointsJquery.val(newGraphData.length);
            }
            drawGraph(newGraphData);
        });

        $('#numOfPoints').val(allAvailableData.length - startDataIndex);
        drawGraph(allAvailableData.slice(startDataIndex, allAvailableData.length));
    }

    function drawGraph (graphData) {
        var loadingAnimationDiv = $('#loadingAnimationDiv');
        loadingAnimationDiv.html(getLoadingAnimation('height: 15px', 'primary', 5));
        if (graphData.length != 0) {
            var currentIssuesRatio = $('#currentIssuesRatio');
            currentIssuesRatio.html('');
            var shownPointsNumber =  numOfPointsJquery.val();
            if (shownPointsNumber > graphData.length) {
                shownPointsNumber = graphData.length;
                numOfPointsJquery.val(shownPointsNumber);
            } else if (shownPointsNumber < 0) {
                shownPointsNumber = 0;
                numOfPointsJquery.val(0);
            }
            var morrisOptions = {
                element: 'currentIssuesRatio',
                data: graphData,
                ykeys: ['inTest', 'eligibleForTest'],
                labels: ['In Test', 'Eligible For Test'],
                barColors: [inTestColor, eligibleForTestColor],
                lineColors: [inTestColor, eligibleForTestColor],
                trendLineColors: [inTestColor, eligibleForTestColor],
                gridTextSize: 10,
                gridTextFamily: 'azoft',
                gridIntegers: true,
                graphFullyInGrid: true,
                numLines: Math.ceil(getMaxBarValue(graphData) / 10),
                ymin: 0,
                barSize: 4,
                barSizeRatio: 0.99,
                barGap: 1,
                shownPointsNumber: shownPointsNumber
            };
            if ($('#chartTypeSelect option:selected').val() == 'bar_chart') {
                currentIssuesRatio.height(150 + graphData.length * 13);
                morrisOptions['xkey'] = 'date';
                morrisOptions['horizontal'] = true;
                var chart = new Morris.Bar(morrisOptions);
            } else {
                currentIssuesRatio.height(750);
                morrisOptions['xkey'] = 'longDate';
                morrisOptions['dateFormat'] = function (x) {
                    var date = new Date(x);
                    var month = date.getMonth() + 1;
                    month = month < 10 ? '0' + month : month;
                    var day = date.getDate();
                    day = day < 10 ? '0' + day : day;
                    return day + '-' + month + '-' + date.getFullYear();
                };
                var chart = new Morris.Line(morrisOptions);
            }
        }
        loadingAnimationDiv.html('');
    }

    function getNewGraphData ()
    {
        var date1 = fromDate.val();
        if (! isValidDate(date1)) {
            error('Invalid from date format');
            return [];
        }
        var date2 = toDate.val();
        if (! isValidDate(date2)) {
            error('Invalid to date format');
            return [];
        }
        date1 = fromDate.datepicker('getDate').getTime();
        date2 = toDate.datepicker('getUTCDate').getTime();

        var fromIndex = -1;
        var toIndex = -1;
        var i = 0;
        while (toIndex == -1 && i < allAvailableData.length) {
            if (allAvailableData[i].longDate >= date1 && fromIndex == -1) {
                fromIndex = i;
            } else if (allAvailableData[i].longDate > date2) {
                break;
            }
            i++;
        }
        toIndex = i;
        return allAvailableData.slice(fromIndex, toIndex);
    }

    function getMaxBarValue (ratioGraphData) {
        var maxBarValue = 0;
        for (var i = 0; i < ratioGraphData.length; i++)
        {
            if (ratioGraphData[i].inTest > maxBarValue) {
                maxBarValue = ratioGraphData[i].inTest;
            }
            if (ratioGraphData[i].eligibleForTest > maxBarValue) {
                maxBarValue = ratioGraphData[i].eligibleForTest;
            }
        }
        return maxBarValue;
    }

    function isValidDate(date)
    {
        var matches = /^(\d{2})[-](\d{2})[-](\d{4})$/.exec(date);
        if (matches == null) {
            return false;
        }
        var d = matches[1];
        var m = matches[2] - 1;
        var y = matches[3];
        var composedDate = new Date(y, m, d);
        return composedDate.getDate() == d && composedDate.getMonth() == m && composedDate.getFullYear() == y;
    }

    function redrawOnEnterPress(e) {
        if (e.keyCode == 13) {
            drawGraph(getNewGraphData());
        }
    }

    function error (message) {
        $('#currentIssuesRatio').html('<table class="m-label margin-auto" style="color: red; text-shadow: 0 0 13px red"><tbody><tr><td>Error\: ' + message + '</td></tr></tbody></table>');
    }

</script>


