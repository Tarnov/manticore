<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="container" style="height: 100%; width: 100%">
    <div class="panel panel-default" style="margin-top: 20px; height: 88%; background-color: #000000">
        <div class="panel-body" id="terminalWindow"
             style="background-color: #000000; color: #ffffff; height: 100%; overflow-y: scroll"></div>
    </div>
    <table style="width: 100%">
        <tbody>
        <tr>
            <td>
                <input class="form-control m-label" id="commandInput" type="text"
                       placeholder="Type command" autofocus>
            </td>
            <td style="width: 5%">
                <button id="commandEnterButton" type="button" class="btn btn-sm btn-primary"
                        onclick="enterCommand()">
                    <i class="fa fa-mail-forward"></i> Enter
                </button>
            </td>
        </tr>
        </tbody>
    </table>
</div>

<%--<c:if test="${passwordModal}">--%>
<%--    <script type="text/javascript">--%>
<%--        $('#passwordModal').modal('show')--%>
<%--    </script>--%>
<%--</c:if>--%>

<!-- Modal -->
<%--<div>--%>
<%--    <div id="passwordModal" class="modal dialog container" tabindex="-1" data-backdrop="static">--%>
<%--        <div class="modal-header align-center">--%>
<%--            <span class="m-label"> Settings</span>--%>
<%--        </div>--%>
<%--        <div class="modal-body">--%>
<%--            <div class="input-group margin-auto text-center">--%>
<%--                Test--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--    <div class="modal-footer">--%>
<%--        <button id="acceptPasswordButton" type="button" class="btn btn-primary align-left" data-dismiss="modal"--%>
<%--                onclick="$('#passwordModal').modal('hide')">--%>
<%--            <i class="fa fa-times"></i> Close--%>
<%--        </button>--%>
<%--    </div>--%>
<%--</div>--%>


<script type="text/javascript">
    /* Set page title */
    setPageTitle('ADMINISTRATION / TERMINAL');

    $('#terminalWindow').html(getCurrentDir());

    $('#commandInput').keyup(function (event) {
        if (event.keyCode === 13) {
            enterCommand()
        }
    })

    function enterCommand() {
        let command = $('#commandInput').val();
        $('#commandInput').val('');
        $('#commandInput').prop('disabled', true);
        $('#commandEnterButton').prop('disabled', true);

        $('#terminalWindow').append(command).append('<br>');

        $.ajax({
            type: "POST",
            contentType: 'application/json',
            url: "/administration_controller/terminal/enterCommand",
            data: JSON.stringify({
                command: command
            })
        }).done(function (response) {
            result(response.toString());
        }).fail(function (response) {
            result(response.responseText);
        });
    }


    function getCurrentDir(command) {
        $.ajax({
            type: "POST",
            contentType: 'application/json',
            url: "/administration_controller/terminal/getCurrentDir",
            data: JSON.stringify({
                command: command
            })
        }).done(function (response) {
            $('#terminalWindow').append(response.toString())
        }).fail(function () {
            $('#terminalWindow').append(command)
        })
    }


    function result(message) {
        $('#terminalWindow').append(message).append('<br>');
        getCurrentDir();
        $('#commandInput').prop('disabled', false);
        $('#commandEnterButton').prop('disabled', false);
        $('#terminalWindow').scrollTop($('#terminalWindow')[0].scrollHeight + 50)
        $('#commandInput').focus();
    }
</script>