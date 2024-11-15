<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div style="margin-top: 15px;margin-bottom: 15px;table-layout: fixed; width: 100%; color: snow">
    <table style="margin-bottom: 15px;width: 100%;height: 100%;">
        <tr>
            <td style="padding: 0">
                <table>
                    <tr>
                        <td style="padding-left: 25px">
                            <i class="fa fa-pencil fa-lg clickable"
                               style="margin-bottom: 10px; display: none"></i>
                        </td>
                        <td style="width: 100%">
                            <pre id="subscription-description-${subscription.id}"
                                 style="white-space:pre-wrap; text-align: center; border: none">${subscription.description}</pre>
                        </td>
                        <td>
                            <i class="fa fa-pencil fa-lg clickable"
                               style="margin-bottom: 10px; padding-top: 8px"
                               onclick="generateChangeDescriptionDialog('${subscription.id}')"></i>
                        </td>
                        <td style="padding-right: 0">
                            <div class="checkbox">
                                <input id="subscription-enabled-${subscription.id}" type="checkbox" name="enabled"
                                <c:if test="${subscription.isEnabled()}">
                                       checked
                                </c:if>
                                       onclick="changeEnabledField('${subscription.id}')"> <label
                                    for="subscription-enabled-${subscription.id}"></label>
                            </div>
                        </td>
                    </tr>
                </table>
            </td>
        </tr>
        <tr>
            <td align="center">
                <div class="input-group" style="margin-top: 20px">
                    <input id="emails" class="form-control text-center m-label"
                           placeholder="EMAILS"
                           name="emails" type="email" multiple onfocus="this.placeholder = ''"
                           onblur="this.placeholder = 'EMAILS'" autofocus="">
                    <span style="width: 0; display: table-cell;"></span>
                    <span class="input-group-btn">
                        <button id="addSubscriberEmails" type="button" class="btn btn-sm btn-primary"
                                style="margin-right : 1px;"
                                onclick="addSubscriberEmails(${subscription.id})"> Add
                        </button>
                    </span>
                </div>
            </td>
        </tr>
    </table>
    <table class="margin-auto" style="margin-bottom: 15px;">
        <c:forEach items="${subscription.subscribers}" var="subscriber">
            <tr>
                <td>
                    <span id="subscriber-${subscriber.id}-user">
                            <c:if test="${subscriber.username.isPresent()}">
                                ${subscriber.username.get()}
                            </c:if>
                    </span>
                </td>
                <td>
                    <c:if test="${subscriber.username.isPresent()}">
                        <span>
                           |
                        </span>
                    </c:if>
                </td>
                <td>
                    <span id="subscriber-${subscriber.id}-email">
                            ${subscriber.email}
                    </span>
                </td>
                <td>
                    <span>
                            <i class="fa fa-trash-o fa-lg clickable"
                               onclick="generateDeleteSubscriberDialog('${subscription.id}','${subscriber.id}')"></i>
                    </span>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>

<!-- Modal -->
<div id="modal-delete-subscriber" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-body">
        <div id="message"></div>
    </div>
    <div class="modal-footer">
        <button id="delete-subscriber-button" type="button" class="btn btn-primary" data-dismiss="modal"><i
                class="fa fa-check"></i> Yes
        </button>
        <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i>Cancel</button>
    </div>
</div>
<!-- Modal -->
<div id="modal-change-description" class="modal dialog subscription" style="width: 1000px;" tabindex="-1"
     data-backdrop="static">
    <div class="modal-header">
        <span>EDIT DESCRIPTION</span>
        <span class="modal-title m-label"><i class="fa fa-pencil"></i> <span id="subscription-name"></span></span>
    </div>
    <div class="modal-body">
         <textarea id="description-message" class="form-control" style="overflow: auto; height: 500px;"
                   contenteditable="true"></textarea>
    </div>
    <div class="modal-footer">
        <button id="save-description-button" type="button" disabled class="btn btn-primary" data-dismiss="modal"><i
                class="fa fa-check"></i> Save
        </button>
        <button type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i>Cancel</button>
    </div>
</div>

<script type="text/javascript">
    function addSubscriberEmails(subscriptionId) {
        $.ajax({
            type: "POST",
            url: "/subscriptions_controller/add_subscribers",
            data: {
                subscriptionId: subscriptionId,
                subscriberEmails: $('#emails').val(),
            }
        }).done(function () {
                    loadBlockContainer('subscriptions', '/subscriptions_controller/get_subscription?subscriptionId=' + subscriptionId, false, '', '', '');
                    removeAllTooltips();
                }
        ).fail(function (response) {
                    $('#response-message').html(showErrorBlockWithMessage(response.responseText)).show('slow');
                }
        );
    }

    function generateDeleteSubscriberDialog(subscriptionId, subscriberId) {
        var subscription = $('#subscription-' + subscriptionId).html();
        var user = $('#subscriber-' + subscriberId + "-user").html();
        if ($.trim(user).length > 0) {
            user += " | ";
        }
        var email = $('#subscriber-' + subscriberId + "-email").html();
        var message = "Are you sure you want to unsubscribe" + user + "<span style=' color: white;'>" + email + "</span>" + " from " + "<span style='font-weight: bold;'>" + subscription + "</span>" + "?";
        $('#message').html(message);
        $('#modal-delete-subscriber').modal('show');
        $('#delete-subscriber-button').click(function () {
            deleteSubscriber(subscriptionId, subscriberId);
        });
    }

    function deleteSubscriber(subscriptionId, subscriberId) {
        $.ajax({
            type: "POST",
            url: "/subscriptions_controller/delete_subscriber",
            data: {
                subscriptionId: subscriptionId,
                subscriberId: subscriberId
            }
        }).done(function (response) {
                    loadBlockContainer('subscriptions', '/subscriptions_controller/get_subscription?subscriptionId=' + subscriptionId, false, '', '', '');
                    removeAllTooltips();
                }
        ).fail(function (response) {
                    $('#response-message').html(showErrorBlockWithMessage(response.responseText)).show('slow');
                }
        );
    }

    function generateChangeDescriptionDialog(subscriptionId) {
        var subscription = $('#subscription-' + subscriptionId).html();
        var description = $('#subscription-description-' + subscriptionId).html();
        $('#subscription-name').html(subscription);
        $('#description-message').html(description).keyup(function () {
            var message = $('#description-message').val().replace(/\s{2,}/g, '');
            var message2 = description.replace(/\s{2,}/g, '');
            $('#save-description-button').attr('disabled', message == message2);
        });
        $('#modal-change-description').modal('show');
        $('#save-description-button').click(function () {
            changeDescription(subscriptionId);
        });
    }

    function changeDescription(subscriptionId) {
        $.ajax({
            type: "POST",
            url: "/subscriptions_controller/change_description",
            data: {
                subscriptionId: subscriptionId,
                description: $('#description-message').val()
            }
        }).done(function (response) {
                    loadBlockContainer('subscriptions', '/subscriptions_controller/get_subscription?subscriptionId=' + subscriptionId, false, '', '', '');
                    removeAllTooltips();
                }
        ).fail(function (response) {
                    $('#response-message').html(showErrorBlockWithMessage(response.responseText)).show('slow');
                }
        );
    }

    function changeEnabledField(subscriptionId) {
        $.ajax({
            type: "POST",
            url: "/subscriptions_controller/change_enabled_field",
            data: {
                subscriptionId: subscriptionId,
                enabled: $("#subscription-enabled-" + subscriptionId).is(":checked")
            }
        }).done(function (response) {
                    loadBlockContainer('subscriptions', '/subscriptions_controller/get_subscription?subscriptionId=' + subscriptionId, false, '', '', '');
                    removeAllTooltips();
                }
        ).fail(function (response) {
                    $('#response-message').html(showErrorBlockWithMessage(response.responseText)).show('slow');
                }
        );
    }

    // Search by Enter & Ctrl + Enter
    $("#emails").focus().keypress(function (event) {
        if (event.which == 13) {
            $("#addSubscriberEmails").click();
        }
    });
</script>