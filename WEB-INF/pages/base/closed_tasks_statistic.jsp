<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" style="margin-top: 15px;margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading" style="border-bottom: none; min-height: 35px"></div>
            <div class="panel-body">
                <table class="margin-auto">
                    <tr>
                        <td>
                            <div id="closed_tasks_statistic-select-update-loading" class="btn-group"></div>
                            <select id="closed_tasks_statistic-select" class="selectpicker" title="DATES"
                                    data-style="btn btn-sm btn-primary dropdown-toggle"
                                    data-selected-text-format="count" data-live-search="true" data-width="auto">
                            </select>
                            <button id="selectDateButton" type="button" class="btn btn-sm btn-primary"
                                    data-toggle="modal" onclick="getClosedTasksStatistic()"> Show statistic
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="closed_tasks_statistic-update-loading" class="btn-group"
                                 style="height: 20px; text-align: center"></div>
                        </td>
                    </tr>
                </table>
                <div id="closed_tasks_statistic-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    /* Set page title */
    setPageTitle('TASK MANAGEMENT / CLOSED TASKS STATISTIC');

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    getDatesOfExecutionCloseTasks();

    function getDatesOfExecutionCloseTasks() {
        let select = $("#closed_tasks_statistic-select")
        select.css('visibility', 'hidden')
        let loading = $("closed_tasks_statistic-select-update-loading")
        loading.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $.ajax({
            type: "GET",
            url: "/closed_tasks_statistic_controller/getDates"
        }).done(function (response) {
                let datesArray = response;
                let composedOptionsListString = '';
                for (let i = 0; i < datesArray.length; i++) {
                    // получаем дату в формате - dd/mm/YY, HH:MM:SS
                    let dateString = new Date(datesArray[i]).toLocaleString("en-GB", {timeZone: "Europe/Moscow"}).replace(/\s+/g, '');
                    // сплит строки по запятой
                    let dateArray = dateString.split(",");
                    // получаем первую часть строки с днем, месяцем и годом и сразу делаем сплит по символу "/"
                    let dayMonthYearArray = dateArray[0].split("/")
                    // получаем вторую часть строки с часами, минутами и секундами и сразу делаем сплит по символу ":"
                    let hoursMinutesSecondsArray = dateArray[1].split(":")

                    let year = dayMonthYearArray[2];
                    let month = dayMonthYearArray[1];
                    let day = dayMonthYearArray[0];

                    let hour = hoursMinutesSecondsArray[0];
                    let min = hoursMinutesSecondsArray[1];

                    let formattedDate = year + "-" + month + "-" + day + " " + hour + ":" + min;
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', formattedDate);
                }
                $("#closed_tasks_statistic-select").html(composedOptionsListString).selectpicker('refresh');
                loading.html("");
                loading.css('display', 'none;');
                select.css('visibility', 'visible')
            }
        ).fail(function (e) {
                console.log('Error loading dates');
                loading.html("");
            }
        );
    }


    function getClosedTasksStatistic() {
        let date = $('#closed_tasks_statistic-select').val();
        let loading = $("#closed_tasks_statistic-select-update-loading")
        loading.html(getLoadingAnimation('height: 15px', 'primary', 5));
        $("#selectDateButton").attr("disabled", true);

        $.ajax({
            type: "POST",
            url: "/closed_tasks_statistic_controller/getStatisticByDate",
            data: {
                date: date
            }
        }).done(function (response) {
                $("#selectDateButton").attr("disabled", false);
                $("#closed_tasks_statistic-body").html(response);
                loading.html("");
                loading.css('display', 'none;');
            }
        ).fail(function () {
                $("#selectDateButton").attr("disabled", false);
                console.log('Could not get projects statistics');
                loading.html("");
            }
        );
    }
</script>

