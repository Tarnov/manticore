<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ACCESS_RELEASE-BRANCHES')" var="isAccessReleaseBranches"/>


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
                                <div class="input-group" style="padding-left: 1px">
                                    <input id="selectedBranch" class="form-control text-center m-label"
                                           placeholder="BRANCH"
                                           name="selectedBranch" type="text" onfocus="this.placeholder = ''"
                                           onblur="this.placeholder = 'BRANCH'"> <label
                                        for="selectedBranch" class="display-none"></label>
                                    <div class="input-group-btn">
                                        <button id="updateBranch" type="button" class="btn btn-sm btn-primary"
                                                onclick="loadBranch(); setPageTitle(getCurrentReleaseTitle())">
                                            <i class="fa fa-refresh"></i> Update
                                        </button>
                                        <button id="selectBranch" type="button"
                                                class="btn btn-sm btn-primary dropdown-toggle" data-toggle="dropdown"
                                                aria-expanded="false">
                                            <span class="caret"></span> <span class="sr-only">Toggle Dropdown</span>
                                        </button>
                                        <ul class="dropdown-menu" role="menu">
                                            <li><a onclick="loadPresetBranch($(this))">master_rc</a></li>
                                            <li><a onclick="loadPresetBranch($(this))">master_hotfix</a></li>
                                            <li><a onclick="loadPresetBranch($(this))">master_autotest</a></li>
                                        </ul>
                                    </div>
                                    <label for="selectedSince" class="display-none"></label>
                                    <input id="selectedSince" class="form-control text-center m-label"
                                           placeholder="SINCE"
                                           style="margin-left: -2px"
                                           name="selectedSince" type="text" onfocus="this.placeholder = ''"
                                           onblur="this.placeholder = 'SINCE'" value="master">
                                </div>
                            </td>
                            <c:if test="${isAccessReleaseBranches}">
                                <td>
                                    <div id="branchesButtonDiv" class="margin-auto">
                                        <button type="branchesButton" class="btn btn-sm btn-primary" data-toggle="modal"
                                                href="#branchesModal"
                                                onclick="getAllProjects()"> Branches
                                        </button>
                                    </div>
                                </td>
                            </c:if>
                        </tr>
                    </table>
                </div>
                <div id="release-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>
<!-- /.row -->

<!-- Modal -->
<div id="branchesModal" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <table class="width-full margin-auto" style="margin-top: 10px">
        <tr>
            <td style="text-align: left; padding-left: 15px">
                <button id="archiveClosedTasksButton" type="button" class="btn btn-primary" onclick="archiveClosedTasks()">
                    Archive closed tasks
                </button>
            </td>
            <td style="text-align: right; padding-right: 15px;">
                <span class="m-label align-right"> Git branches status <i class="fa fa-refresh"></i></span>
            </td>
        </tr>
    </table>
    <div class="modal-body">
        <div class="input-group margin-auto text-center">
            <select id="projectSelect" class="selectpicker" data-style="btn-primary" data-width="auto"
                    data-live-search="true" title="Project" onchange="getProjectRepos(false);">
            </select>
            <select id="repoSelect" class="selectpicker" data-style="btn-primary" data-width="auto"
                    data-live-search="true" title="Repo" onchange="getStatistics();">
            </select>
        </div>

        <div id="branchesLoadingAnimation" style="margin-top: 20px"></div>
        <table id="tasksStatistics"></table>
        <table id="taskBranch"></table>

        <div id="branchesCmd" style="display: none">
            <div>
                <fieldset>
                    <legend class="m-label">New Repository</legend>
                    <div>
                        <pre id="newRepositoryCmd" ondblclick="selectText(this.id)"></pre>
                    </div>
                </fieldset>
            </div>

            <div>
                <fieldset>
                    <legend class="m-label">Archive Closed Branches <span id="archiveClosedBranchesCount"
                                                                          class="counter">0</span></legend>
                    <div>
                        <pre id="archiveClosedBranchesCmd" ondblclick="selectText(this.id)"></pre>
                    </div>
                </fieldset>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="closeBranchesStatus" type="button" class="btn btn-primary" data-dismiss="modal"
                onclick="clearTaskBranchesAndStatistics(); clearBranchesStrings()"><i class="fa fa-times"></i> Close
        </button>
    </div>
</div>


<script type="text/javascript">
    /* Set page title */
    setPageTitle('RELEASE');

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    /* Shortcut function to start loading branch from list after it has been selected */
    function loadPresetBranch(obj) {
        $('#selectedBranch').val(obj.html());
        loadBranch()
    }

    function loadBranch() {
        if ($('#selectedBranch').val()) {
            $.ajax({
                type: "GET",
                url: "/release_controller/getDefaultProjectAndRepo"
            }).done(function (response) {
                    let repo = response[1];
                    let project = response[0];
                    loadReleaseContainerController(project, repo, $('#selectedSince').val(), $('#selectedBranch').val(), true)
                }
            ).fail(function () {
                console.log('Error when setting values, setting value for project PAN');
            });
        }
    }

    /* Search by Enter & Ctrl + Enter */
    $("#selectedBranch").focus();
    $("#selectedBranch,#selectedSince").keypress(function (event) {
        if (event.which == 13) {
            $("#updateBranch").click();
        }
    });

    function getAllProjects() {
        $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", true);
        $('#branchesLoadingAnimation').html(getLoadingAnimation('height: 15px', 'primary', 7));

        $.ajax({
            type: "GET",
            url: "/release_controller/branches/getAllProjects"
        }).done(function (response) {
                const projectsList = response;
                let composedOptionsListString = '';
                for (let i = 0; i < projectsList.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', projectsList[i]);
                }
                $("#projectSelect").html(composedOptionsListString).selectpicker('refresh').selectpicker('val', 'PAN (Panbet)');
                $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", false);
                getProjectRepos(true);
                $('#branchesLoadingAnimation').html('');
            }
        ).fail(function () {
                console.log('Could not get projects');
            }
        );
    }

    function getProjectRepos(defaultSelectionNeeded) {
        $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", true);

        $.ajax({
            type: "GET",
            url: "/release_controller/branches/getProjectRepos",
            data: {
                projectKey: $('#projectSelect option:selected').text().split(" ")[0]
            }
        }).done(function (response) {
                var reposList = response;
                var composedOptionsListString = '<option data-hidden="true" data-content="<span class=\'bootstrap-select-disabled\'>Repo</span>"></option>';
                for (var i = 0; i < reposList.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', reposList[i]);
                }
                if (defaultSelectionNeeded) {
                    $("#repoSelect").html(composedOptionsListString).selectpicker('refresh').selectpicker('val', 'Panbet');
                    $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", false);
                    getStatistics();
                } else {
                    $("#repoSelect").html(composedOptionsListString).selectpicker('refresh').selectpicker('val', '');
                    clearTaskBranchesAndStatistics();
                    clearBranchesStrings();
                    $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", false);
                }
            }
        ).fail(function () {
                console.log('Could not get repos');
            }
        );
    }

    var branchesList;

    function getStatistics() {
        $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", true);
        clearTaskBranchesAndStatistics();
        clearBranchesStrings();
        $('#branchesLoadingAnimation').html(getLoadingAnimation('height: 15px', 'primary', 7));

        $.ajax({
            type: "GET",
            url: "/release_controller/branches/getBranchesAndTasksByProjectAndRepo",
            data: {
                projectKey: $('#projectSelect option:selected').text().split(" ")[0],
                repo: $('#repoSelect option:selected').text()
            }
        }).done(function (response) {
                branchesList = response;
                if (branchesList.length > 1) {
                    var taskStatisticsBody = getStatisticsHeader();
                    var statisticsMatrix = branchesList.last();
                    for (var i = 0; i < statisticsMatrix.length - 1; i++) {
                        taskStatisticsBody += sprintf('<tr>' +
                            '<td class="vertical-align-middle">' +
                            '<div class="checkbox">' +
                            '<input type="checkbox" id="checkboxStatus-%3$s" name="%3$s" onclick="getTasksAndBranches()">' +
                            '<label for="checkboxStatus-%3$s"></label>' +
                            '</div>' +
                            '</td>' +
                            '<td>%1$s</td>' +
                            '<td>%2$s</td>' +
                            '</tr>', statisticsMatrix[i][0], statisticsMatrix[i][1], statisticsMatrix[i][0].replace(/ /g, ''));
                    }
                    taskStatisticsBody += sprintf('<tr class="m-label default-selected-color">' +
                        '<th></th>' +
                        '<th>%1$s:   %2$s</th>' +
                        '<th></th>' +
                        '</tr>', statisticsMatrix.last()[0],
                        statisticsMatrix.last()[1]);
                    $("#tasksStatistics").html(taskStatisticsBody);
                    getTasksAndBranches();
                }
                checkAllCheckboxes();
                $("#branchesModal").find(".bootstrap-select").find("button").attr("disabled", false);
                $('#branchesLoadingAnimation').html('');
            }
        ).fail(function () {
                console.log('Could not get tasks and branches');
            }
        );
    }

    function getStatisticsHeader() {
        return '<tr class="m-label default-selected-color">' +
            '<th class="vertical-align-middle">' +
            '<div class="checkbox">' +
            '<input id="allStatistics" type="checkbox" name="allStatistics" onclick="toggleSelectAll(\'checkboxStatus-\', $(this), createBranchesStrings); getTasksAndBranches();">' +
            '<label for="allStatistics"></label>' +
            '</div>' +
            '</th>' +
            '<th>Statistics</th>' +
            '<th></th>' +
            '</tr>';
    }

    function getTasksAndBranchesHeader() {
        return '<tr class="m-label default-selected-color">' +
            '<th class="vertical-align-middle">' +
            '<div class="checkbox">' +
            '<input id="allTasks" type="checkbox" name="allTasks" checked="true" onclick="toggleSelectAll(\'checkboxTask-\', $(this), createBranchesStrings);">' +
            '<label for="allTasks"></label>' +
            '</div>' +
            '</th>' +
            '<th>JIRA</th>' +
            '<th>STASH</th>' +
            '</tr>';
    }

    function getTasksAndBranches() {
        var taskBranchHtml = getTasksAndBranchesHeader();
        if (branchesList.length > 1) {
            for (var i = 0; i < branchesList.length - 1; i++) {
                if ($("#checkboxStatus-" + branchesList[i][2].replace(/ /g, '')).is(":checked")) {
                    taskBranchHtml += sprintf('<tr class="mark-out">' +
                        '<td class="vertical-align-middle">' +
                        '<div class="checkbox">' +
                        '<input type="checkbox" id="checkboxTask-%4$s-%2$s" name="%2$s" checked="true" onclick="createBranchesStrings()">' +
                        '<label for="checkboxTask-%4$s-%2$s"></label>' +
                        '</div>' +
                        '</td>' +
                        '<td>' +
                        '<img src="%5$s" title="%3$s"/><a href="${jiraHref}browse/%1$s"</a>' +
                        '%1$s</td>' +
                        '<td class="align-center">%2$s</td>' +
                        '</tr>', branchesList[i][0], branchesList[i][1], branchesList[i][2], branchesList[i][2].replace(/ /g, ''), branchesList[i][3]);
                }
            }
        }
        $("#taskBranch").html('');
        $("#taskBranch").html(taskBranchHtml);
        clearBranchesStrings();
        createBranchesStrings();
    }


    function createBranchesStrings() {
        var projectKey = $('#projectSelect option:selected').text().split(" ")[0];
        var repository = $('#repoSelect option:selected').text().replaceAll(' ', '-');
        var archiveClosedCmd = '';
        var count = 0;
        $("input[id^='checkboxTask-']:checked").each(function () {
            var id = $(this).attr("id");
            var status = id.split('-')[1];
            var branchName = id.replace('checkboxTask-' + status + '-', '');

            count++;
            archiveClosedCmd += ' git tag archive/' + branchName + ' origin/' + branchName + ';';

            archiveClosedCmd += ' git push origin :' + branchName + ';';
        });
        if (archiveClosedCmd != '') {
            archiveClosedCmd += ' git push --tag;';
            archiveClosedCmd = archiveClosedCmd.replace(/^ /, "");
            var newRepoCmd = 'git clone ssh://git@' + '${stashUrl}' + ':7999/'
                + projectKey + '/' + repository + '.git && cd ' + repository;
            $('#archiveClosedBranchesCount').html(count);
            $('#newRepositoryCmd').html(newRepoCmd);
            $('#archiveClosedBranchesCmd').html(archiveClosedCmd);
            $('#branchesCmd').show();
        } else {
            clearBranchesStrings();
        }
    }

    function clearBranchesStrings() {
        $('#branchesCmd').hide();
        $('#archiveClosedBranchesCount').html(0);
        $('#newRepositoryCmd').html('');
        $('#archiveClosedBranchesCmd').html('');
    }

    function clearTaskBranchesAndStatistics() {
        $("#tasksStatistics").html('');
        $("#taskBranch").html('');
    }

    function checkAllCheckboxes() {
        $('#allStatistics').prop("checked", true);
        $("input[id^=checkboxStatus-]:not(:disabled)").parent().parent().parent().filter(":visible").find("input").prop("checked", true);
        getTasksAndBranches();
        createBranchesStrings();
    }

    function archiveClosedTasks() {
        $('#archiveClosedTasksButton').attr("disabled", true);
        $.ajax({
            type: "GET",
            url: "/release_controller/branches/archiveClosedTasks"
        }).done(function (response) {
                $('#archiveClosedTasksButton').attr("disabled", false);
            }
        ).fail(function () {
                $('#archiveClosedTasksButton').attr("disabled", false);
            }
        );
    }

</script>
