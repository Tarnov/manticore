<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div>
    <table>
        <tr>
            <th>
                <div class="text-center m-label default-selected-color">
                    To
                </div>
            </th>
            <th>
                <div class="text-center m-label default-selected-color">
                    Message text
                </div>
            </th>
            <th></th>
        </tr>
        <tr>
            <td>
                <input class="form-control m-label" id="jabbberAddressee" type="text">
            </td>
            <td>
                <div style="position: relative" id="jabberMessageContainer">
                    <textarea id="jabberMessageText" style="resize: none; top: 0; width: 25em; height: 2.40em;"
                            class="form-control m-label"></textarea>
                </div>
            </td>
            <td>
                <button id="jabberSendButton" type="button" class="btn btn-sm btn-primary"
                    onclick="sendMessage()">
                    <i class="fa fa-mail-forward"></i> Send
                </button>
            </td>
        </tr>
    </table>
</div>
<div id="jabberStatusDiv" style="margin-top: 15px"></div>

<script type="text/javascript">
    /* Set page title */
    setPageTitle('JABBER TEST');
    initMessageText();
    var statusDiv = $('#jabberStatusDiv');
    
    function sendMessage() {
        statusDiv.html(getLoadingAnimation('height: 15px', 'primary', 5));
        statusDiv.show();
        var addressee = $('#jabbberAddressee').val();
        var message = $('#jabberMessageText').val();
        $.ajax({
            type: "POST",
            url: "/administration_controller/jabber_client/send",
            contentType: 'application/json',
            data: JSON.stringify({
                addressee: addressee,
                message: message
            })
        }).done(function () {
            result("Message sent", true);
        }).fail(function (response) {
            result(response.responseText, false);
        });
    }


    function result(message, isPositive)
    {
        var style = isPositive ? 'style="color: green; text-shadow: 0 0 13px green"' : 'style="color: red; text-shadow: 0 0 13px red"';
        message = isPositive ? message : 'Error:' + message;
        statusDiv.html('<table class="m-label margin-auto"' +  style + '><tbody><tr><td>' +
        message +
        '</td></tr></tbody></table>');

        statusDiv.delay(4000).fadeOut(600);
    }


    function initMessageText()
    {
        var width = $('#jabberMessageText').css('width');
        $('#jabberMessageContainer').height($('#jabberMessageContainer').css('height'));
        $('#jabberMessageContainer').width($('#jabberMessageContainer').css('width'));

        $('#jabberMessageText').focus(function () {
            $(this).animate({ height: "10em" }, 0);
            $(this).css({
                width: width,
                position: 'absolute'
            });
        }).blur(function () {
            $('#jabberMessageText').animate({ height: "2.40em", position: 'static' }, 0);
        });
    }
    
</script>
