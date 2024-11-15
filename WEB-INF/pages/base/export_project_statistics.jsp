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
                                <i id="export-project-statistics-icon-info" class="fa fa-info-circle fa-lg"
                                   style="color: #357ebd"></i>
                            </td>
                            <td>
                                <select id="export-project-statistics-select" class="selectpicker" title="PROJECTS"
                                        data-style="btn btn-sm btn-primary dropdown-toggle" data-live-search="true"
                                        data-selected-text-format="count" data-width="auto" multiple>
                                </select>
                            </td>
                            <td>
                                <input id="startDate" class="form-control text-center m-label"
                                       style="border-color: #357ebd"
                                       placeholder="start date yyyy-mm-dd" onfocus="this.placeholder = ''"
                                       onblur="this.placeholder = 'start date yyyy-mm-dd'">
                            </td>
                            <td>
                                <input id="endDate" class="form-control text-center m-label"
                                       style="border-color: #357ebd"
                                       placeholder="end date yyyy-mm-dd" onfocus="this.placeholder = ''"
                                       onblur="this.placeholder = 'end date yyyy-mm-dd'">
                            </td>
                            <td>
                                <button id="export-project-statistics-button" type="button"
                                        class="btn btn-sm btn-primary"
                                        data-toggle="modal" onclick="exportStatistics()">Export statistics
                                </button>
                            </td>
                        </tr>
                    </table>
                </div>
                <div>
                    <table class="margin-auto" style="margin-bottom: 15px;">
                        <tr>
                            <td style="text-align: center">
                                <div id="export-project-statistics-success"
                                     class="visibility-hidden success-text">Success
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="text-align: center">
                                <div id="export-project-statistics-error"
                                     class="visibility-hidden error-text">Error
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    /* Set page title */
    setPageTitle('TASK MANAGEMENT / EXPORT PROJECT STATISTICS');

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    getAllProjects();

    let datepickerOptions = {
        orientation: 'bottom',
        format: "yyyy-mm-dd"
    };
    $('#startDate').datepicker(datepickerOptions)
    $('#endDate').datepicker(datepickerOptions)

    $('#export-project-statistics-icon-info').on({
        "click mouseenter": function () {
            $(this).tooltip({
                title: 'You can choose multiple projects',
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
        $.ajax({
            type: "GET",
            url: "/task_movement_controller/getAllProjects"
        }).done(function (response) {
                let projects = response;
                let composedOptionsListString = '';
                for (let i = 0; i < projects.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', projects[i]);
                }
                $("#export-project-statistics-select").html(composedOptionsListString).selectpicker('refresh');
            }
        ).fail(function () {
                console.log('Error loading projects');
            }
        );
    }

    function exportStatistics() {
        let arr = $('#export-project-statistics-select').val()
        let startDate = $('#startDate').val();
        let endDate = $('#endDate').val();

        if (jQuery.isEmptyObject(arr)) {
            return;
        }

        let projects = '';
        for (let i = 0; i < arr.length; i++) {
            let project = arr[i].match(/(?<=\().+?(?=\))/gi)[0]
            projects += arr.length == i + 1 ? project : project + ' '
        }

        let successDiv = $('#export-project-statistics-success')
        let errorDiv = $('#export-project-statistics-error')
        successDiv.addClass('visibility-hidden')
        errorDiv.addClass('visibility-hidden')

        $.ajax({
            type: "POST",
            url: "/export_project_statistics_controller/exportProjectStatistics",
            data: {
                projects: projects,
                startDate: startDate,
                endDate: endDate
            }
        }).done(function (response) {
                successDiv.removeClass('visibility-hidden')
                successDiv.text('')
                jQuery('<a>', {
                    id: 'export-project-statistics-script-success',
                    href: response
                }).appendTo(successDiv)
                $('#export-project-statistics-script-success')
                    .text("Go to the created page")
            }
        ).fail(function (e) {
                errorDiv.removeClass('visibility-hidden')
                errorDiv.text('Error: ' + e.responseText)
            }
        );
    }
</script>

