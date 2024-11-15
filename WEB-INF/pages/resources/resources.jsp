<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<security:authorize access="hasRole('ACCESS_RESOURCES-RESERVATION-ADMIN')" var="isAccessResourceAdmin" />
<security:authorize access="hasRole('ACCESS_RESOURCES-RESERVATION-POWER-USER')" var="isAccessPowerResourcesUser" />
<fmt:setLocale value="ru_RU" scope="session"/>


<table class="margin-auto width-full">
    <tr>
        <td style="width: 25%"></td>
        <td style="width: 50%">
            <div id="reservesErrorDiv"></div>
        </td>
        <td class="align-right" style="width: 25%">
            <div>
                <button type="button" class="btn btn-sm btn-primary"
                        onclick="printResource()">
                    <i class="fa fa-print"></i> Print table
                </button>
            </div>
        </td>
    </tr>
</table>


<table id="reservesTable" class="default-selected-color text-center border-collapse margin-auto"
        style="border-bottom: 2px solid #404141;">
    <tr class='m-label default-selected-color'>
        <th>Week day</th>
        <th>Time</th>
        <th class="deleteMarker"></th>
        <th width="500px">Description</th>
        <th class="deleteMarker"></th>
        <th class="deleteMarker"></th>
        <th>Author</th>
    </tr>
    
    <c:forEach items="${reservesByDayKeys}" var="dayKey">
        <c:set var="dailyReserves" value="${reservesByDayMap[dayKey]}"/>
        <fmt:formatDate pattern="dd.MM.yyyy" value="${dayKey}" var="date"/>
        <fmt:formatDate pattern="EEEE" value="${dayKey}" var="dayOfWeek"/>
        <tbody style="border-left: 3px solid #404141; border-right: 3px solid #404141; border-top: 3px solid #404141">
        <c:set var="rowspan" value="${fn:length(dailyReserves)}"/>
        <c:if test="${rowspan < 3}">
            <c:set var="rowspan" value="3"/>
        </c:if>
        <c:set var="reserveNumber" value="0"/>
        <c:forEach items="${dailyReserves}" var="reserve">
            <fmt:formatDate pattern="HH:mm" value="${reserve.fromDate}" var="fromTime"/>
            <fmt:formatDate pattern="HH:mm" value="${reserve.toDate}" var="toTime"/>
            <c:set var="timeInterval" value="${fromTime} &mdash; ${toTime}" />
            <tr <c:if test="${dayKey == currentDate}">style="background-color: #3c2c2c"</c:if> class="bottom-border">
                <c:if test="${reserveNumber == 0}">
                    <td class="grey-border-right
                    <c:if test="${dayKey >= currentDate}">
                        mark-out cursor-pointer"
                        onclick="addReserveModal('${date}','${dayOfWeek}')"
                        data-toggle="modal" href="#reserveModal
                    </c:if>"
                    rowspan="${rowspan}">
                        <div class="m-label">${dayOfWeek}</div>
                        <div class="font-size-smaller" style="color: #909090">${date}</div>
                    </td>
                </c:if>
                <c:set var="reserveNumber" value="${reserveNumber + 1}"/>
                <td class="m-label grey-border-right">
                    <div style="margin-top: 3px">
                        ${timeInterval}
                    </div>
                </td>
                <td class="deleteMarker">
                    <c:if test="${reserve.dayPeriod ne 2147483647}">
                        <i class="fa fa-refresh"></i>
                    </c:if>
                </td>
                <td width="500px" class="align-left" style="word-break: break-all">
                    <div style="white-space: pre-line"><c:out value="${reserve.unescapedDetails}"/></div>
                </td>
                <td class="deleteMarker">
                    <c:if test="${reserve.username eq currentUsername &&
                        (startDateMap[reserve.id].fromDate >= currentDateTime || reserve.dayPeriod != 2147483647)}">
                        <i class="fa fa-pencil fa-lg clickable"
                           data-toggle="modal" href="#reserveModal"
                           onclick="editReserveModal(${reserve.id}, ${startDateMap[reserve.id].fromDate.time},
                               ${startDateMap[reserve.id].toDate.time}, '${reserve.details}', ${reserve.dayPeriod},
                                   '${date}', '${dayOfWeek}')"></i>
                    </c:if>
                </td>
                <td class="grey-border-right deleteMarker">
                    <c:if test="${reserve.username eq currentUsername or isAccessPowerResourcesUser}">
                        <i class="fa fa-trash-o fa-lg clickable"
                           data-toggle="modal" href="#reserveWarningModal"
                           onclick="deleteReserveModal(${reserve.id});"></i>
                    </c:if>
                </td>
                <td class="align-left">
                    <div>${reserve.fullName}</div>
                </td>
            </tr>
        </c:forEach>
        <c:if test="${reserveNumber < 3}">
            <c:forEach begin="${reserveNumber}" end="2" varStatus="i">
                <tr <c:if test="${dayKey == currentDate}">style="background-color: #3c2c2c"</c:if>>
                    <c:if test="${i.index == 0}">
                        <td class=" grey-border-right
                        <c:if test="${dayKey >= currentDate}">
                            mark-out cursor-pointer"
                            onclick="addReserveModal('${date}', '${dayOfWeek}')"
                            data-toggle="modal" href="#reserveModal
                        </c:if>" rowspan="3">
                            <div class="m-label">${dayOfWeek}</div>
                            <div class="font-size-smaller" style="color: #909090">${date}</div>
                        </td>
                    </c:if>
                    <td class="grey-border-right">&nbsp;</td>
                    <td class="deleteMarker">&nbsp;</td>
                    <td>&nbsp;</td>
                    <td class="deleteMarker">&nbsp;</td>
                    <td class="grey-border-right deleteMarker">&nbsp;</td>
                    <td>&nbsp;</td>
                </tr>
            </c:forEach>
        </c:if>
        </tbody>
    </c:forEach>
</table>

<!-- Delete reserve modal -->
<div id="reserveWarningModal" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <div class="modal-header align-right">
        <span class="m-label"> WARNING </span>
    </div>
    <div class="modal-body">
        <table class="m-label margin-auto" style="color: red; text-shadow: 0 0 13px red">
            <tbody>
            <tr>
                <td>
                    The reservation will be deleted. Do you wish to proceed?
                </td>
            </tr>
            </tbody>
        </table>
        <table>
            <tr>
                <td>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">
                        <i class="fa fa-times"></i> Cancel
                    </button>
                </td>
                <td>
                    <button id="warningReserveDeleteButton" type="button" class="btn btn-sm btn-primary"
                            data-dismiss="modal">
                        <i class="fa fa-check"></i> Yes
                    </button>
                </td>
            </tr>
        </table>
    </div>
</div>


<!-- Add/Edit reserve modal -->
<div id="reserveModal" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div id="reserveModalHeader" class="modal-header align-right"></div>
    <div class="modal-body">
        <div class="m-label text-center default-selected-color" id="reservationDayOfWeek"></div>
        <div class="font-size-smaller text-center" style="color: #909090" id="reservationDate"></div>
        <table>
            <tr>
                <td>
                    <div class="text-center m-label default-selected-color">
                        From
                    </div>
                </td>
                <td>
                    <div class="text-center m-label default-selected-color">
                        To
                    </div>
                </td>
            </tr>
            <tr>
                <td>
                    <div class='input-group date' id='reserveFromDate'>
                        <input id="reserveFromDateInput" type='text' class="form-control text-center m-label"
                               onfocus="$(this).select()"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </td>
                <td>
                    <div class='input-group date' id='reserveToDate'>
                        <input id="reserveToDateInput" type='text' class="form-control text-center m-label"
                               onfocus="$(this).select()"/>
                        <span class="input-group-addon">
                            <span class="glyphicon glyphicon-calendar"></span>
                        </span>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div class="m-label default-selected-color" style="margin-top: 10px;">
                        Description
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <textarea id="detailsTextArea" style="height: 12em; margin-bottom: 10px" class="form-control">
                    </textarea>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <div id="reserveErrorDiv"></div>
                </td>
            </tr>
        </table>
        <table style="margin-bottom: 15px" align="right">
            <tr>
                <td>
                    <div class="checkbox m-label" style="margin-top: 5px; margin-bottom: 0">
                        <input id="periodicReserve" type="checkbox" name="periodicReserve"><label
                            for="periodicReserve">REPEAT</label>
                    </div>
                </td>
                <td>
                    <div id="reserveSaveButtonContainer">
                        <button id="saveReserveButton" type="button" class="btn btn-sm btn-primary">
                            <i class="fa fa-check"></i> Save
                        </button>
                    </div>
                </td>
                <td>
                    <button type="button" class="btn btn-primary" data-dismiss="modal">
                        <i class="fa fa-times"></i> Cancel
                    </button>
                </td>
            </tr>
        </table>
    </div>
</div>

<script type="text/javascript">
    /* Set page title */
    setPageTitle('RESOURCES RESERVATION');

    var resourceByNameBody = $('#resource_by_name-body');

    //Global variable, here we store day to which we're trying to add reservation when modal opens
    var dayInModal;

    var resModalHeader = $('#reserveModalHeader');
    var resModal = $('#reserveModal');
    var reserveDetails = $('#detailsTextArea');
    var reservePeriodCheckbox = $('#periodicReserve');
    var reserveErrorDiv = $('#reserveErrorDiv');
    var reserveButtonContainer = $('#reserveSaveButtonContainer');

    var printTableHtml = initPrintTable();

    var reserveFromDate = $('#reserveFromDate');
    var reserveToDate = $('#reserveToDate');

    var parseDate = function(inputDate)
    {
        if (typeof inputDate === 'string')
        {
            var splitTime = inputDate.split(':');
            var hours = splitTime[0];
            var minutes = splitTime[1];

            return moment(dayInModal).hours(hours).minutes(minutes);
        }
        else
        {
            return moment(inputDate);
        }
    }

    reserveDetails.bind('input propertychange', function() {
        disableSaveIfNotValid(reserveFromDate.data('DateTimePicker').date(), reserveToDate.data('DateTimePicker').date());
    });

    $('#reserveFromDateInput').inputmask("h:s",{});
    $('#reserveToDateInput').inputmask("h:s",{});
    
    initFromDatepicker(reserveFromDate);
    initToDatepicker(reserveToDate);
    initFromPickerChangeEvents(reserveFromDate, reserveToDate);
    initToPickerChangeEvents(reserveFromDate, reserveToDate);


    // FUNCTIONS
    function clearModal()
    {
        reserveFromDate.data("DateTimePicker").minDate(false);
        reserveFromDate.data("DateTimePicker").maxDate(false);
        reserveToDate.data("DateTimePicker").minDate(false);
        reserveToDate.data("DateTimePicker").maxDate(false);
        reserveDetails.val('');
        reservePeriodCheckbox.prop('checked', false);
        reserveErrorDiv.html('');
    }


    function setModalTimePickersTime(fromMoment, toMoment)
    {
        reserveFromDate.data("DateTimePicker").date(fromMoment);
        reserveToDate.data("DateTimePicker").date(toMoment);
    }


    function addReserveModal(date, dayOfWeek)
    {
        resModalHeader.html('<span class="m-label"><i class="fa fa-pencil"></i> ADD RESERVATION </span>');
        clearModal();
        $('#reservationDate').html(date);
        $('#reservationDayOfWeek').html(dayOfWeek);

        var dateForReserve = moment(date, 'DD.MM.YYYY');
        dayInModal = moment(dateForReserve);
        var currentDate = moment(new Date());
        var fromMoment;
        var toMoment;
        if (dateForReserve.isAfter(currentDate)) {
            fromMoment = moment(dateForReserve).hours(9).minutes(0).seconds(0).milliseconds(0);
            toMoment = moment(dateForReserve).hours(10).minutes(0).seconds(0).milliseconds(0);
        }
        else
        {
            fromMoment = moment(dateForReserve).hours(currentDate.hours() + 1).minutes(0).seconds(0).milliseconds(0);
            toMoment = moment(dateForReserve).hours(currentDate.hours() + 2).minutes(0).seconds(0).milliseconds(0);
        }
        setModalTimePickersTime(fromMoment, toMoment);

        $('#saveReserveButton').unbind();
        $('#saveReserveButton').on('click', function () {
            reserveResource('/resources_controller/reserve_resource');
        });
        disableSaveIfNotValid(fromMoment, toMoment);
    }


    function editReserveModal(reserveId, resFromDate, resToDate, resDetails, dayPeriod, date, dayOfWeek)
    {
        resModalHeader.html('<span class="m-label"><i class="fa fa-pencil"></i> EDIT RESERVATION </span>');
        clearModal();
        $('#reservationDate').html(date);
        $('#reservationDayOfWeek').html(dayOfWeek);

        var fromMoment = moment(resFromDate);
        dayInModal = fromMoment.clone().hours(0).minutes(0).seconds(0).milliseconds(0);
        var toMoment = moment(resToDate);
        setModalTimePickersTime(fromMoment, toMoment);

        reserveDetails.val(resDetails);
        $('#saveReserveButton').unbind();
        $('#saveReserveButton').on('click', function () {
            reserveResource('/resources_controller/edit_reserve', reserveId);
        });
        if (dayPeriod != 2147483647)
        {
            reservePeriodCheckbox.prop('checked', true);
        }
        disableSaveIfNotValid(fromMoment, toMoment);
    }


    function createData(reserveId, fromDate, toDate)
    {
        if (! reserveId)
        {
            return {
                resId: $('#resourceSelect option:selected').data("id"),
                fromDateTime: fromDate.unix(),
                toDateTime: toDate.unix(),
                details: reserveDetails.val(),
                dayPeriod: reservePeriodCheckbox.prop('checked') ? 7 : 2147483647
            }
        }
        else
        {
            return {
                reserveId: reserveId,
                resourceId: $('#resourceSelect option:selected').data("id"),
                fromDateTime: fromDate.unix(),
                toDateTime: toDate.unix(),
                details: reserveDetails.val(),
                dayPeriod: reservePeriodCheckbox.prop('checked') ? 7 : 2147483647
            }
        }
    }


    function reserveResource(url, reserveId) {
        var fromDate = reserveFromDate.data('DateTimePicker').date();
        var toDate = reserveToDate.data('DateTimePicker').date();
        if (fromDate != null && toDate != null) {
            var temp = reserveButtonContainer.html();
            var loadAnimation = getLoadingAnimation('height: 15px', 'primary', 5);
            reserveButtonContainer.html(loadAnimation);
            $.ajax({
                type: "GET",
                url: url,
                data: createData(reserveId, fromDate, toDate)
            }).done(function () {
                reserveButtonContainer.html(temp);
                loadResourcesReservationContainerController($('#resourceSelect option:selected').val(),
                        $('#reservesDateRange').data('daterangepicker').startDate.unix(),
                        $('#reservesDateRange').data('daterangepicker').endDate.unix());
                resModal.modal('hide');
                resModal.remove();
                reserveErrorDiv.html('');
            }).fail(function (response) {
                reserveButtonContainer.html(temp);
                $('#saveReserveButton').on('click', function () {
                    reserveResource(url, reserveId);
                });
                fadingError(reserveErrorDiv, response.responseText);
            });
        }
        else
        {
            fadingError(reserveErrorDiv, 'From time and to time must be not empty');
        }
    }


    function deleteReserveModal(resId)
    {
        $('#warningReserveDeleteButton').unbind('click');
        $('#warningReserveDeleteButton').on('click', function () {
            deleteReserve(resId);
        });
    }


    function deleteReserve(resId) {
        var tmpBody = resourceByNameBody.html();
        resourceByNameBody.html(getLoadingAnimation('height: 15px', 'primary', 5));
        var selectedOption = $('#resourceSelect option:selected');
        $.ajax({
            type: "GET",
            url: "/resources_controller/delete_reserve",
            data: {resId: resId, resourceName: selectedOption.html()},
            async: false
        }).done(loadResourcesReservationContainerController(selectedOption.val(),
                        $('#reservesDateRange').data('daterangepicker').startDate.unix(),
                        $('#reservesDateRange').data('daterangepicker').endDate.unix())
        ).fail(function(response){
                    resourceByNameBody.html(tmpBody);
                    fadingError($("#reservesErrorDiv"), response.responseText);
        });
    }


    function printResource()
    {
        var newWin = window.open("");
        newWin.document.write(printTableHtml);
        newWin.print();
        newWin.close();
    }
    
    
    function disableSaveIfNotValid(fromMoment, toMoment)
    {
        if (reserveDetails.val().trim() == '' || fromMoment.isSame(toMoment) || fromMoment.isAfter(toMoment))
        {
            $('#saveReserveButton').prop('disabled', true);
        }
        else
        {
            $('#saveReserveButton').prop('disabled', false);
        }
    }


    // INIT FUNCTIONS
    function initFromDatepicker(fromDatepicker)
    {
        fromDatepicker.datetimepicker({
            locale: 'ru',
            format: 'HH:mm',
            useCurrent: false
        });
        fromDatepicker.data("DateTimePicker").parseInputDate(parseDate);
    }


    function initToDatepicker(toDatepicker)
    {
        toDatepicker.datetimepicker({
            locale: 'ru',
            format: 'HH:mm',
            useCurrent: false
        });
        toDatepicker.data("DateTimePicker").parseInputDate(parseDate);
    }


    function initFromPickerChangeEvents(fromPicker, toPicker)
    {
        fromPicker.on("dp.change", function (e) {
            if (!e.date) {
                toPicker.data("DateTimePicker").minDate(false);
                toPicker.data("DateTimePicker").maxDate(false);
            }
            else {
                disableSaveIfNotValid(e.date, toPicker.data("DateTimePicker").date());
                var maxDate = e.date.clone().hours(23).minutes(59).seconds(0).milliseconds(0);
                var newTimePlusFifteenMinutes = e.date.clone().minutes(e.date.minutes() + 15);
                var minDate = newTimePlusFifteenMinutes.isBefore(maxDate) ? newTimePlusFifteenMinutes : maxDate;
                if (!toPicker.data("DateTimePicker").minDate()) {
                    toPicker.data("DateTimePicker").maxDate(maxDate);
                    toPicker.data("DateTimePicker").minDate(minDate);
                }
                else if (toPicker.data("DateTimePicker").minDate().isBefore(e.date)) {
                    toPicker.data("DateTimePicker").maxDate(maxDate);
                    toPicker.data("DateTimePicker").minDate(minDate);
                }
                else {
                    toPicker.data("DateTimePicker").minDate(minDate);
                    toPicker.data("DateTimePicker").maxDate(maxDate);
                }
                var oldDate = e.oldDate;
                if (oldDate != null)
                {
                    var newToPickerDate = toPicker.data("DateTimePicker").date().add(e.date.diff(oldDate));
                    toPicker.data("DateTimePicker").date(newToPickerDate);
                }
            }
        });
    }


    function initToPickerChangeEvents(fromPicker, toPicker)
    {
        toPicker.on("dp.change", function (e) {
            if (!e.date) {
                fromPicker.data("DateTimePicker").maxDate(false);
                fromPicker.data("DateTimePicker").minDate(false);
            }
            else {
                disableSaveIfNotValid(fromPicker.data("DateTimePicker").date(), e.date);
                var minPossibleTime = getMinMoment(e.date)
                if (!fromPicker.data("DateTimePicker").maxDate()) {
                    fromPicker.data("DateTimePicker").minDate(minPossibleTime);
                    //fromPicker.data("DateTimePicker").maxDate(e.date);
                }
                else if (fromPicker.data("DateTimePicker").maxDate().isAfter(e.date)) {
                    fromPicker.data("DateTimePicker").minDate(minPossibleTime);
                    //fromPicker.data("DateTimePicker").maxDate(e.date);
                }
                else {
                    fromPicker.data("DateTimePicker").maxDate(e.date);
                    //fromPicker.data("DateTimePicker").minDate(minPossibleTime);
                }
            }
        });
    }


    function getMinMoment(date)
    {
        var currentMoment = moment();
        if (currentMoment.year() == date.year() &&
                currentMoment.month() == date.month() &&
                currentMoment.date() == date.date())
        {
            return currentMoment;
        }
        else
        {
            return date.clone().hours(0).minutes(0).seconds(0).milliseconds(0);
        }
    }


    function initPrintTable()
    {
        var printTable = $('#reservesTable').clone();
        printTable.find('th.deleteMarker, td.deleteMarker').remove();
        printTable.find('th, td').each(function () {
            $(this).removeAttr("style");
            $(this).css({padding: "10px", border: "1px solid black"});
            $(this).css("border-left", "2px solid black");
        });
        printTable.find('th:nth-child(2), td:nth-child(2)').each (function () {
            $(this).css({width: "20%"});
        });
        printTable.find('th:nth-child(3), td:nth-child(3)').each (function () {
            $(this).css({width: "52%"});
        });
        printTable.find('th:nth-child(4), td:nth-child(4)').each (function () {
            $(this).css({width: "8%"});
        });
        printTable.find('tbody').each(function () {
            $(this).css({border: "2px solid black"});
        });

        return "<table class='text-center' style='font-size:14px; width: 100%; border-collapse: collapse;'>" +
                printTable.html() + "</table>";
    }

</script>