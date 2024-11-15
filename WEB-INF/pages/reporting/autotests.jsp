<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${issues != null}">
    <div class="row" style="margin-top: 15px;margin-bottom: 15px; color: snow">
        <div id="branchesButtonDiv" class="align-center">
            <button id="autotests-button" type="branchesButton"
                    class="btn btn-sm btn-primary" data-toggle="modal" href="#branchesModal"
                    onclick="markAsAutotestChecked(); this.style.display='none';"> MARK AS AUTOTEST CHECKED
            </button>
            <div id="autotests-button-success" class="success-text" style="display: none">Success</div>
            <div id="autotests-button-error" class="error-text" style="display: none">Error</div>
        </div>
        <div class="align-center">
            <ul class="list-style-type-none" style="display: inline-block">
                <li class="align-left">
                    <div class="checkbox checkbox-inline" style="margin-bottom: 15px;">
                        <input type="checkbox" id="toggle" name=""
                               onclick="toggleSelectAll('checkbox-', $(this))" checked>
                        <label for="toggle"></label>
                    </div>
                </li>
                <c:forEach var="issue" items="${issues}">
                    <li id="li-id-${issue.key}" class="align-left">
                        <div class="checkbox checkbox-inline" style="margin-bottom: 15px;">
                            <input type="checkbox" id="checkbox-${issue.key}" name="${issue.key}" checked>
                            <label for="checkbox-${issue.key}"></label>
                        </div>
                        <img src="${issue.issueType.iconUri}" title="${issue.issueType.name}"/>
                        <a href="${url}browse/${issue.key}">${issue.key}</a>
                            ${issue.summary}
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</c:if>

<c:if test="${error != null}">
    <div class="error">${error}</div>
</c:if>

<script>
    var ajaxRequestStatusContainer = $("#autotests-button");
    var ajaxRequestStatusContainerSavedContent = ajaxRequestStatusContainer.html();

    var preCallback = function()
    {
        ajaxRequestStatusContainer.html(getLoadingAnimation('height: 15px', 'primary', 5));
    };

    var doneCallback = function()
    {
        ajaxRequestStatusContainer.html(ajaxRequestStatusContainerSavedContent);
        $("#autotests-button-success").show();
    };

    var failCallback = function()
    {
        ajaxRequestStatusContainer.html(ajaxRequestStatusContainerSavedContent);
        $("#autotests-button-error").show();
    };

    function markAsAutotestChecked()
    {
        var tasks = [];
        $("[id^=checkbox-]:checked").each(
            function () {
                var checkbox = $(this);
                tasks.push(checkbox.attr('id').replace('checkbox-', ''));
            }
        );

        preCallback();

        $.ajax({
                type: "POST",
                url : '/reporting_controller/autotests/mark',
                data: JSON.stringify({
                    tasks: tasks
                }),
                contentType: 'application/json'
            }
        ).done(function ()
            {
                doneCallback();
            }
        ).fail(function (response)
            {
                console.log(response);
                failCallback();
            }
        );
    }
</script>


