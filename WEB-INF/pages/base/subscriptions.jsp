<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" style="margin-top: 15px;margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading" style="border-bottom: none; min-height: 35px">
                <div class="pull-right"></div>
            </div>
            <div class="panel-body">
                <div>
                    <table class="margin-auto" style="margin-bottom: 15px;">
                        <tr>
                            <td>
                                <select id="subscriptions-select" class="selectpicker"
                                        data-live-search="true"
                                        data-style="btn btn-sm btn-primary dropdown-toggle"
                                        data-selected-text-format="count"
                                        title="<span class='bootstrap-select-disabled'>SUBSCRIPTION</span>"
                                        data-width="auto"
                                        onchange="loadContainer()">
                                    <option data-hidden="true"
                                            data-content="<span class='bootstrap-select-disabled'>SELECT SUBSCRIPTION</span>"></option>
                                    <c:forEach items="${subscriptions}" var="subscription">
                                        <option id="subscription-${subscription.id}"
                                                value="${subscription.id}">${subscription.name}</option>
                                    </c:forEach>
                                </select>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="subscriptions-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    /* Set page title */
    setPageTitle('SUBSCRIPTIONS');

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    if (${subscriptionId != null})
    {
        $('#subscriptions-select').selectpicker('refresh').selectpicker('val', '${subscriptionId}');
        loadBlockContainer('subscriptions', '/subscriptions_controller/get_subscription?subscriptionId=' + '${subscriptionId}', false, '', '', '');
    }

    function loadContainer() {
        var callFunction = {                                 // some params of function than called this one:
            calledFunctionName: window.history.state.calledFunctionName,            // name of function
            calledFunctionArgs: window.history.state.calledFunctionArgs        // arguments function was called with
        };

        const select = $('#subscriptions-select option:selected');
        window.history.replaceState(callFunction, 'subscriptions', '/subscriptions/' + select.val());
        loadBlockContainer('subscriptions', '/subscriptions_controller/get_subscription?subscriptionId=' + select.val(), false, '', '', '');
    }

</script>
