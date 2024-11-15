<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container">
    <form id="send-email" enctype="multipart/form-data" accept-charset="UTF-8">
        <table class="text-left m-label default-selected-color">
            <tr>
                <td>
                    <div>From</div>
                    <label><input id="from" name="fromAddress" type="email"
                                  class="form-control text-center m-label"></label>
                </td>
                <td>
                    <div>Cc</div>
                    <label><input id="cc" name="ccAddresses" type="email" multiple
                                  class="form-control text-center m-label"></label>
                </td>
            </tr>
            <tr>
                <td>
                    <div>To</div>
                    <label><input id="to" name="toAddresses" type="email" multiple
                                  class="form-control text-center m-label"></label>
                </td>
                <td>
                    <div>Bcc</div>
                    <label><input id="bcc" name="bccAddresses" type="email" multiple
                                  class="form-control text-center m-label"></label>
                </td>
            </tr>
            <tr>
                <td>
                    <div>Subject</div>
                    <label><input id="subject" type="text"
                                  class="form-control text-center m-label"></label>
                </td>
                <td>
                    <div>Attachment</div>
                    <input id="attachment" name="file" type="file" multiple>
                </td>
            </tr>
        </table>
        <table>
            <tr>
                <td>
                    <label><textarea id="content"
                                     style="overflow: auto; height: 600px ;width: 800px;"></textarea></label>
                </td>
            </tr>
            <tr>
                <td>
                    <div style="text-align: center;">
                        <div id="sendButton">
                            <input type="submit" class="btn btn-sm btn-primary">
                        </div>
                        <progress id="progressbar" value="0" max="100"></progress>
                        <div id="response-message"></div>
                    </div>
                </td>
            </tr>
        </table>
    </form>
</div>
<script type="text/javascript">

    var progressBar = $('#progressbar');
    $('#send-email').submit(function (event) {
        event.preventDefault();
        var form = $('#send-email')[0];
        var formData = new FormData(form);
        formData.append("subject", encodeURIComponent($('#subject').val()));
        formData.append("content", encodeURIComponent($('#content').val()));
        var sendButton = $('#sendButton').html();
        $('#sendButton').html(getLoadingAnimation('height: 15px', 'primary', 5));
        $.ajax({
            url: "/administration_controller/mail_client/send",
            cache: false,
            type: "post",
            contentType: false,
            processData: false,
            data: formData,
            xhr: function () {
                var xhr = $.ajaxSettings.xhr(); // получаем объект XMLHttpRequest
                xhr.upload.addEventListener('progress', function (evt) { // добавляем обработчик события progress (onprogress)
                    // высчитываем процент загруженного
                    var percentComplete = Math.ceil(evt.loaded / evt.total * 100);
                    // устанавливаем значение в атрибут value тега <progress>
                    // и это же значение альтернативным текстом для браузеров, не поддерживающих <progress>
                    progressBar.val(percentComplete).text('Загружено ' + percentComplete + '%');

                }, false);
                return xhr;
            }
        }).done(function (response) {
                    $('#sendButton').html(sendButton);
                    $('#response-message').html(SUCCESS_BLOCK).show('slow');
                    setTimeout(function () {
                        $('#response-message').fadeOut('fast')
                    }, 1000);
                }
        ).fail(function (response) {
                    $('#sendButton').html(sendButton);
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
    });
</script>
