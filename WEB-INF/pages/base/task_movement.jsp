<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" style="margin-top: 15px;margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading" style="border-bottom: none; min-height: 35px"></div>
            <div class="panel-body">
                <table class="margin-auto">
                    <tr>
                        <td>
                            <i id="task_movement-icon-info" class="fa fa-info-circle fa-lg" style="color: #357ebd"></i>
                            <div id="task_movement_leads-select-update-loading" class="btn-group"></div>
                            <select id="task_movement_leads-select" class="selectpicker" title="PROJECT LEADS"
                                    data-style="btn btn-sm btn-primary dropdown-toggle" multiple
                                    data-selected-text-format="count" data-live-search="true"
                                    data-width="auto" onchange="getProjectsByLeadDisplayName()">
                            </select>
                            <div id="task_movement_projects-select-update-loading" class="btn-group"></div>
                            <select id="task_movement_projects-select" class="selectpicker" title="PROJECTS"
                                    data-style="btn btn-sm btn-primary dropdown-toggle" multiple
                                    data-selected-text-format="count" data-live-search="true"
                                    data-width="auto">
                            </select>
                            <button id="selectedProjectsButton" type="button" class="btn btn-sm btn-primary" data-toggle="modal"
                                    onclick="getStatisticsForProjects()"> Show statistics
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <div id="task_movement-update-loading" class="btn-group" style="height: 20px; text-align: center"></div>
                        </td>
                    </tr>
                </table>
                <div id="task_movement-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    /* Set page title */
    var title = 'TASK MANAGEMENT / TASK MOVEMENT';
    setPageTitle(title);

    getAllProjects();
    getAllProjectLeads()

    var taskOrBranch;

    $('#task_movement-icon-info').on({
        "click mouseenter": function () {
            $(this).tooltip({
                title: '- You can choose multiple leads and multiple projects<br>' +
                    '- Click "SHOW STATISTICS" button with empty lead and project to show all statistics for all projects<br>' +
                    '- Click "SHOW STATISTICS" button with selected lead and empty project to show all project statistics for selected lead',
                placement: 'left',
                html: true
            })
            $(this).tooltip('show')
        },
        "mouseout": function () {
            $(this).tooltip('hide')
        }
    })

    function getAllProjects() {
        let select = $("#task_movement_projects-select")
        select.css('visibility', 'hidden')
        let loading = $("#task_movement_projects-select-update-loading")
        loading.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $.ajax({
            type: "GET",
            url: "/task_movement_controller/getAllProjects"
        }).done(function (response) {
                let selectedProjects = response;
                let composedOptionsListString = '';
                for (let i = 0; i < selectedProjects.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', selectedProjects[i]);
                }
                $("#task_movement_projects-select").html(composedOptionsListString).selectpicker('refresh');
                loading.html("");
                loading.css('display', 'none;');
                select.css('visibility', 'visible')
            }
        ).fail(function () {
                console.log('Error loading projects');
                loading.html("");
            }
        );
    }

    function getAllProjectLeads() {
        let select = $("#task_movement_leads-select")
        select.css('visibility', 'hidden')
        let loading = $("#task_movement_leads-select-update-loading")
        loading.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $.ajax({
            type: "GET",
            url: "/task_movement_controller/getAllProjectLeads",
            cache: false
        }).done(function (response) {
                let leadsArray = response;
                let composedOptionsListString = '';
                for (let i = 0; i < leadsArray.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', leadsArray[i]);
                }
                $("#task_movement_leads-select").html(composedOptionsListString).selectpicker('refresh');
                loading.html("");
                loading.css('display', 'none;');
                select.css('visibility', 'visible')
            }
        ).fail(function () {
                console.log('Error loading leads');
                loading.html("");
            }
        );
    }

    function getStatisticsForProjects() {
        let loading = $("#task_movement-update-loading")
        loading.html(getLoadingAnimation('height: 15px', 'primary', 5));

        $('#selectedProjectsButton').prop('disabled', true);

        let projectsString = getProjectsToShow()

        $.ajax({
            type: "POST",
            url: "/task_movement_controller/getStatisticsForProjects",
            data: {
                selectedProjects: projectsString
            }
        }).done(function (response) {
                $("#task_movement-body").html(response);
                loading.html("");
                loading.css('display', 'none;');
                $('#selectedProjectsButton').prop('disabled', false);
            }
        ).fail(function () {
                console.log('Could not get projects statistics');
                loading.html("");
                $('#selectedProjectsButton').prop('disabled', false);
            }
        );
    }

    function getProjectsToShow() {
        let projects = $('#task_movement_projects-select').val()
        let leads = $('#task_movement_leads-select').val()

        let resultString = ''

        if (!jQuery.isEmptyObject(leads) && jQuery.isEmptyObject(projects)) {
            let arr = document.getElementById('task_movement_projects-select').options
            for (let i = 0; i < arr.length; i++) {
                resultString += arr[i].value.match(/(?<=\().+?(?=\))/gi)
                resultString += arr.length == i + 1 ? '' : ','
            }
            return resultString;
        }

        if (jQuery.isEmptyObject(projects)) {
            return resultString;
        }

        for (let i = 0; i < projects.length; i++) {
            resultString += projects[i].match(/(?<=\().+?(?=\))/gi)
            resultString += projects.length == i + 1 ? '' : ','
        }
        return resultString;
    }

    function getProjectsByLeadDisplayName() {
        let arr = $('#task_movement_leads-select').val()

        if (jQuery.isEmptyObject(arr)) {
            getAllProjects()
            return;
        }

        let leadsString = '';
        for (let i = 0; i < arr.length; i++) {
            leadsString += arr.length == i + 1 ? arr[i] : arr[i] + ','
        }

        $.ajax({
            type: "POST",
            url: "/task_movement_controller/getProjectsByLeadDisplayName",
            data: {
                leadDisplayNames: leadsString
            }
        }).done(function (response) {
                let projectSelectElement = $("#task_movement_projects-select");
                let bufferSelectedProjects = projectSelectElement.val()

                projectSelectElement.html("").selectpicker('refresh');

                let projectsArray = response;
                let composedOptionsListString = '';
                for (let i = 0; i < projectsArray.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', projectsArray[i]);
                }
                projectSelectElement.html(composedOptionsListString).selectpicker('refresh');

                let newArray = document.getElementById('task_movement_projects-select').options
                if (!jQuery.isEmptyObject(newArray) && !jQuery.isEmptyObject(bufferSelectedProjects)) {
                    for (let i = 0; i < newArray.length; i++) {
                        if (bufferSelectedProjects.includes(newArray[i].value)) {
                            $('#task_movement_projects-select option:contains(' + newArray[i].value + ')').prop('selected', true);
                        }
                    }
                    projectSelectElement.selectpicker('refresh');
                }
            }
        ).fail(function () {
                console.log('Could not get projects with this project lead');
            }
        );
    }
</script>
