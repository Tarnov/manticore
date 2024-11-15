<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" style="margin-top: 15px;margin-bottom: 15px; color: snow">
    <table style="table-layout: fixed; width: 700px;">
        <colgroup>
            <col width="126">
        </colgroup>
        <tr>
            <td class="align-right">%FIXVER%</td>
            <td>
                <div>
                    <pre id="spain-jurisdiction-build-data" contenteditable="true">${build}</pre>
                </div>
            </td>
        </tr>
        <tr>
            <td class="align-right">%STARTDATE%</td>
            <td>
                <div>
                    <pre id="spain-jurisdiction-startDate-data" contenteditable="true">${startDate}</pre>
                </div>
            </td>
        </tr>
        <tr>
            <td class="align-right">%FINISHDATE%</td>
            <td>
                <div>
                    <pre id="spain-jurisdiction-finishDate-data" contenteditable="true">${finishDate}</pre>
                </div>
            </td>
        </tr>
        <tr>
            <td class="align-right">%COMMENT%</td>
            <td>
                <div>
                    <pre id="spain-jurisdiction-comment-data" contenteditable="true"></pre>
                </div>
            </td>
        </tr>
        <tr>
            <td class="align-right">%TASKID%</td>
            <td>
                <div>
                    <pre id="spain-jurisdiction-taskId-data" contenteditable="true">${tasks}</pre>
                </div>
            </td>
        </tr>
    </table>


    <div class="row" style="margin-top: 15px;margin-bottom: 15px;">
        <div class="col-lg-12">

            <div>
                <table class="margin-auto" style="margin-bottom: 15px;">
                    <tr>
                        <td>
                            <div id="graphLoading"></div>
                            <div id="branchesButtonDiv" class="margin-auto">
                                <button id="spain-jurisdiction-create-update-button" type="branchesButton"
                                        class="btn btn-sm btn-primary" data-toggle="modal" href="#branchesModal"
                                        onclick="createSpainJurisdictionContent(); this.style.display='none';"> Create
                                </button>
                                <div id="spain-jurisdiction-create-success" class="visibility-hidden success-text">Success</div>
                                <div id="spain-jurisdiction-create-error" class="visibility-hidden error-text">Error</div>
                            </div>

                        </td>
                    </tr>
                </table>

                <div id="spain-jurisdiction-create-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var ajaxRequestStatusContainer = $("#spain-jurisdiction-create-update-button");
    var ajaxRequestStatusContainerSavedContent = ajaxRequestStatusContainer.html();

    var preCallback = function()
    {
        ajaxRequestStatusContainer.html(getLoadingAnimation('height: 15px', 'primary', 5));
    };

    var doneCallback = function()
    {
        ajaxRequestStatusContainer.html(ajaxRequestStatusContainerSavedContent);
        $("#spain-jurisdiction-create-success").removeClass("visibility-hidden");
    };

    var failCallback = function()
    {
        ajaxRequestStatusContainer.html(ajaxRequestStatusContainerSavedContent);
        $("#spain-jurisdiction-create-error").removeClass("visibility-hidden");
    };

    function createSpainJurisdictionContent()
    {
        const build = $("#spain-jurisdiction-build-data").html();
        const startDate = $("#spain-jurisdiction-startDate-data").html();
        const finishDate = $("#spain-jurisdiction-finishDate-data").html();
        const comment = $("#spain-jurisdiction-comment-data").html();
        const taskId = $("#spain-jurisdiction-taskId-data").html();

        createContentForSpainJurisdiction(build, startDate, finishDate, comment, taskId, preCallback, doneCallback,
                failCallback);
    }

</script>