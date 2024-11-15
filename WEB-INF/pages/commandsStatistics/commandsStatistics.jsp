<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<table class="margin-auto">
    <tr>
        <td>
            <input id="commandsFromDate" class="form-control text-center m-label" placeholder="From date (dd-mm-yyyy)"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'From date (dd-mm-yyyy)'">
        </td>
        <td>
            <input id="commandsToDate" class="form-control text-center m-label" placeholder="To date (dd-mm-yyyy)"
                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'To date (dd-mm-yyyy)'">
        </td>
        <td>
            <select id="commandSelect" class="selectpicker" data-style="btn-primary" data-width="auto"
                    title="Command name" onchange="getCommandInfoByName($('#commandSelect option:selected').val())">
            </select>
        </td>
        <td>
            <button type="button" class="btn btn-sm btn-primary"
                    onclick="redrawGraphs()">
                <i class="fa fa-refresh"></i> Draw graph
            </button>
        </td>
    </tr>
</table>

<div id="commandsLoadingAnimationDiv" style="margin-top: 15px"></div>

<table>
    <tr>
        <td>
            <div id="commandsTimeStatistics"></div>
        </td>
        <td>
            <div id="commandsMemoryStatistics"></div>
        </td>
    </tr>
</table>


<script type="text/javascript">

    $('.selectpicker').selectpicker();

    $('ul.nav a').on('shown.bs.tab', function () {
        //redrawGraphs();
    });

    var commandSelectHtml = '<option data-hidden="true" data-content="<span class=&quotbootstrap-select-disabled&quot>Command name</span>"></option>';
    <c:forEach items="${commandsNames}" var="name">
        commandSelectHtml += sprintf('<option value="%1$s">%1$s</option>', '${name}');
    </c:forEach>
    $('#commandSelect').html(commandSelectHtml).selectpicker('refresh');

    var commandsFromDate = $('#commandsFromDate');
    var commandsToDate = $('#commandsToDate');
    var datepickerInitProceeds = false;

    var allAvailableCommandsData = [];
    function getCommandInfoByName (name) {
        var commandsLoadingAnimationDiv = $('#commandsLoadingAnimationDiv');
        commandsLoadingAnimationDiv.html(getLoadingAnimation('height: 15px', 'primary', 5));
        $.ajax({
            type: "GET",
            url: "/statistics_controller/info_by_command_name?name=" + name
        }).done(function(response) {
            allAvailableCommandsData = response;
            if (allAvailableCommandsData.length != 0) {
                initDatepickers();
            }
            commandsLoadingAnimationDiv.html('');
        }).fail(function() {
            commandsLoadingAnimationDiv.html('<table class="m-label margin-auto" style="color: red; text-shadow: 0 0 13px red"><tbody><tr><td>Error ' + '</td></tr></tbody></table>')
        })
    }

    function initDatepickers() {
        datepickerInitProceeds = true;
        var startDate = new Date(allAvailableCommandsData[0].date);
        var endDate = new Date(allAvailableCommandsData.last().date);
        endDate.setHours(0);
        startDate.setHours(0);

        var datepickerOptions = {
            orientation: 'bottom',
            format: "dd-mm-yyyy",
            weekStart: 1
        };

        commandsFromDate.datepicker(datepickerOptions).datepicker('setStartDate', startDate);
        commandsFromDate.datepicker(datepickerOptions).datepicker('setEndDate', endDate);
        commandsToDate.datepicker(datepickerOptions).datepicker('setStartDate', startDate);
        commandsToDate.datepicker(datepickerOptions).datepicker('setEndDate', endDate);

        commandsFromDate.datepicker('setDate', startDate);
        commandsToDate.datepicker('setDate', endDate);

        commandsFromDate.datepicker().on('changeDate', function () {
            if (!datepickerInitProceeds) {
                commandsToDate.data('datepicker').setStartDate(commandsFromDate.datepicker('getDate'));
            }
        });
        commandsToDate.datepicker().on('changeDate', function () {
            if (!datepickerInitProceeds) {
                commandsFromDate.data('datepicker').setEndDate(commandsToDate.datepicker('getDate'));
            }
        });
        datepickerInitProceeds = false;
    }

    function redrawGraphs() {
        $('#commandsTimeStatistics').html('');
        $('#commandsMemoryStatistics').html('');
        var graphData = getNewCommandsGraphData();
        new Morris.Line ({
            element: 'commandsTimeStatistics',
            data: graphData,
            xkey: 'date',
            ykeys: ['numberOfCalls'],
            labels: ['Number of calls'],
            lineColors: ['#478EC7'],
            gridTextSize: 10,
            gridTextFamily: 'azoft',
            dateFormat: function (x) {
                var date = new Date(x);
                var month = date.getMonth() + 1;
                month = month < 10 ? '0' + month : month;
                var day = date.getDate();
                day = day < 10 ? '0' + day : day;
                return day + '-' + month + '-' + date.getFullYear();
            }
        });

        new Morris.Line ({
            element: 'commandsMemoryStatistics',
            data: graphData,
            xkey: 'date',
            ykeys: ['memoryPerCall'],
            labels: ['Memory Per Call'],
            lineColors: ['#478EC7'],
            gridTextSize: 10,
            gridTextFamily: 'azoft',
            dateFormat: function (x) {
                var date = new Date(x);
                var month = date.getMonth() + 1;
                month = month < 10 ? '0' + month : month;
                var day = date.getDate();
                day = day < 10 ? '0' + day : day;
                return day + '-' + month + '-' + date.getFullYear();
            }
        });
    }

    function getNewCommandsGraphData ()
    {
        var date1 = commandsFromDate.val();
        if (! isValidDate(date1)) {
            error('Invalid from date format');
            return [];
        }
        var date2 = commandsToDate.val();
        if (! isValidDate(date2)) {
            error('Invalid to date format');
            return [];
        }
        date1 = commandsFromDate.datepicker('getDate').getTime();
        date2 = commandsToDate.datepicker('getUTCDate').getTime();

        var fromIndex = -1;
        var toIndex = -1;
        var i = 0;
        while (toIndex == -1 && i < allAvailableCommandsData.length) {
            if (allAvailableCommandsData[i].date >= date1 && fromIndex == -1) {
                fromIndex = i;
            } else if (allAvailableCommandsData[i].date > date2) {
                break;
            }
            i++;
        }
        toIndex = i;
        return allAvailableCommandsData.slice(fromIndex, toIndex);
    }

</script>
