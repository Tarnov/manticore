<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" style="margin-top: 15px;margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading" style="border-bottom: none; min-height: 35px"></div>
            <div class="panel-body">
                <div>
                    <table class="margin-auto" style="margin-bottom: 15px;">
                        <tr>
                            <td>
                                <div class="input-group" style="padding-left: 1px;">
                                    <input id="taskID" class="form-control text-center m-label" placeholder="JIRA TASK ID"
                                           name="search" type="text" onfocus="this.placeholder = ''"
                                           onblur="this.placeholder = 'JIRA TASK ID'" autofocus=""> <label for="taskID"
                                                                                                           class="display-none"></label>
                                    <span class="input-group-btn">
                                        <button id="searchTask" type="button" class="btn btn-sm btn-primary"
                                                onclick="loadMergeTaskToBranchContainerController('', '', $('#taskID').val().toUpperCase());
                                                 setPageTitle('WORKBENCH / MERGE TASK TO BRANCH'); taskOrBranch = 'Task'"><i class="fa fa-tasks"></i> Task
                                        </button>
                                        <button id="searchBranch" type="button" class="btn btn-sm btn-primary"
                                                style="margin-right : 1px;"
                                                onclick="loadMergeTaskToBranchContainerController('projectKey', 'repoKey', $('#taskID').val().toUpperCase());
                                                 setPageTitle('WORKBENCH / MERGE TASK TO BRANCH'); taskOrBranch = 'Branch'"><i class="fa fa-code-fork"></i> Branch
                                        </button>
                                    </span>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="merge_task_to_branch-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    /* Set page title */
    var title = 'WORKBENCH / MERGE TASK TO BRANCH';
    setPageTitle(title);

    var taskOrBranch;

    // Search by Enter & Ctrl + Enter
    $("#taskID").focus().keypress(function (event) {
        if ((event.keyCode == 10 || event.keyCode == 13) && event.ctrlKey) {
            $("#searchBranch").click();
        } else if (event.which == 13) {
            $("#searchTask").click();
        }
    });

</script>
