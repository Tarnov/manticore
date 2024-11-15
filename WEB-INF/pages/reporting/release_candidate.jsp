<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<table class="margin-auto">
    <tr>
        <td style="height: 50px">&nbsp;</td>
        <td id="response-message"></td>
    </tr>
    <tr>
        <td>
            <label class="primary-color panel-header">Emails:</label>
        </td>
        <td width="99%">
            <div style="padding-left: 1px; width: 100%">
                <input style="width: 100%" id="to" name="toAddresses"
                       type="email" multiple required
                       class="form-control text-center" value="autotest@marathonbet.ru"
                       oninput="validate();">
            </div>
        </td>
        <td>
            <div id="release-candidate-send-report">
                <button id="send-report-button" disabled onclick="sendReleaseCandidateReport()"
                        class="btn btn-sm btn-primary" data-toggle="modal"> Send Report
                </button>
            </div>
        </td>
    </tr>
</table>

<div id="release-candidate-report"
     style="margin-top: 15px; margin-bottom: 15px; background-color: #ffffff; color: #000000">

    <style>
        table {
            border-collapse: collapse;
        }

        th {
            border-bottom: 1px solid #ccc;
            text-align: left;
            color: #707070;
            font-size: 12px;
            white-space: nowrap;
            font-family: Arial, sans-serif;
        }

        th.block {
            text-align: center;
        }

        td {
            padding: 5px;
        }

        td.row {
            border-bottom: 1px solid #ccc;
        }

        h2 {
            margin: 0;
        }

        a {
            color: #3b73af;
            text-decoration: none;
            white-space: nowrap;
        }

        .status {
            background-color: #fff;
            border-color: #e4e8ed;
            color: #000000;
            max-width: 12em;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            word-wrap: normal;
            word-break: normal;
            border: 1px solid #cccccc;
            border-radius: 3px;
            display: inline-block;
            font-weight: bold;
            line-height: 99%;
            margin: 0;
            padding: 2px 5px;
            text-align: center;
            text-decoration: none;
            text-transform: uppercase;
            font-family: Arial, sans-serif;
            font-size: 12px;
            vertical-align: middle;
        }
    </style>

    <table style="margin:0; padding:0; width:100%; border:0">
        <tr>
            <td>
                <div style="max-width: 100%; width: 100%; text-align: center;">

                    <table style="margin:0; padding:0; width:100%; border:0">

                        <tr>
                            <td>
                            <span style="display:inline-block; width:100%;">
                                <a href="${report.contentURI}" target="_blank">${report.contentURI}</a>
                                <br>
                                <a href="http://bamboo.cur.local/" target="_blank">http://bamboo.cur.local/</a>
                            </span>
                            </td>
                        </tr>

                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>
                        <tr>
                            <td>&nbsp;</td>
                        </tr>

                        <tr>
                            <td>
                                <table style="margin:auto; padding:0; border:0; width:100%">
                                    <tr class="m-label default-selected-color">
                                        <th>Key</th>
                                        <th>Summary</th>
                                        <th>Assignee</th>
                                        <th>Priority</th>
                                        <th style="text-align: center;">Created</th>
                                        <th>Status</th>
                                    </tr>
                                    <c:forEach var="key" items="${report.issuesView.keySet()}">
                                        <tr>
                                            <td style="border: none"></td>
                                        </tr>
                                        <tr>
                                            <td style="border: none"></td>
                                        </tr>
                                        <tr class="m-label default-selected-color">
                                            <th colspan="6" class="block"><h2>${key}
                                                (${report.issuesView.get(key).size()})</h2></th>
                                        </tr>
                                        <c:forEach var="issue" items="${report.issuesView.get(key)}"
                                                   varStatus="counter">
                                            <tr id="issue-${issue.key}" class="upper-border">
                                                <td class="row">
                                                    <a href="${issue.link}"
                                                       target="_blank">${issue.key}</a>
                                                </td>
                                                <td class="row" id="summary-${issue.key}"><a href="${issue.link}"
                                                                                             target="_blank">${issue.summary}</a>
                                                </td>
                                                <td class="row" id="assignee-${issue.key}">${issue.assignee}</td>
                                                <td class="row">
                                                    <img src="<c:out value="${issue.priorityView.iconUrl}" escapeXml="false" />"
                                                         title="${issue.priorityView.name}"/>${issue.priorityView.name}
                                                </td>
                                                <td class="row" style="text-align: center">${issue.creationDate}</td>
                                                <td class="row">
                                                    <span class="status">${issue.statusView.name}</span>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:forEach>
                                </table>
                            </td>
                        </tr>
                    </table>
                </div>
            </td>
        </tr>
    </table>
</div>

<script type="text/javascript">

    function validate() {
        var message = document.getElementById('to').validationMessage;
        if (!(document.getElementById('to').validity.valid)) {
            $('#response-message').html(showErrorBlockWithMessage(message)).show('slow');
            $('#send-report-button').attr('disabled', true);
        } else {
            $('#response-message').html(showErrorBlockWithMessage(message)).show('slow');
            $('#send-report-button').attr('disabled', false);
        }
    }

    function sendReleaseCandidateReport() {
        $.ajax({
            type: "POST",
            url: "/reporting_controller/release_candidate/send_report",
            data: {
                report: $('#release-candidate-report').html(),
                toAddresses: $('#to').val(),
                version : '${report.getBuildVersion()}'
            }
        }).done(function (response) {
                    $('#response-message').html(SUCCESS_BLOCK).show('slow');
                    setTimeout(function () {
                        $('#response-message').fadeOut('fast');
                    }, 3000);
                }
        ).fail(function (response) {
                    if (response.status == 400) {
                        $('#response-message').html(showErrorBlockWithMessage(response.responseText)).show('slow');
                    }
                    else if (response.status == 0 || (response.status == 500 && response.responseText.indexOf("MaxUploadSizeExceededException") > 0)) {
                        $('#response-message').html(showErrorBlockWithMessage('The allowable file size')).show('slow');
                    }
                    else {
                        $('#response-message').html(showErrorBlockWithMessage('Fail. See logs.')).show('slow');
                    }
                }
        );
    }
</script>