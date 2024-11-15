<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ACCESS_ATLASSIAN')" var="isAccessAtlassian"/>
<security:authorize access="hasRole('ACCESS_ATLASSIAN-JIRA')" var="isAccessAtlassianJira"/>
<security:authorize access="hasRole('ACCESS_ATLASSIAN-JIRA-FILTERS')" var="isAccessAtlassianJiraFilters"/>
<security:authorize access="hasRole('ACCESS_ATLASSIAN-JIRA-FILTERS-PANBET')" var="isAccessAtlassianJiraFiltersPanbet"/>
<security:authorize access="hasRole('ACCESS_RELEASE')" var="isAccessRelease"/>
<security:authorize access="hasRole('ACCESS_STATISTICS')" var="isAccessStatistics"/>
<security:authorize access="hasRole('ACCESS_TESTING')" var="isAccessTesting"/>
<security:authorize access="hasRole('ACCESS_TESTING-MASTER-TEST')" var="isAccessTestingMasterTest"/>
<security:authorize access="hasRole('ACCESS_TESTING-NOT-IN-MASTER-TEST')" var="isAccessTestingNotInMasterTest"/>
<security:authorize access="hasRole('ACCESS_REPORTING')" var="isAccessReporting"/>
<security:authorize access="hasRole('ACCESS_REPORTING-SPAIN-JURISDICTION')" var="isAccessReportingSpainJurisdiction"/>
<security:authorize access="hasRole('ACCESS_REPORTING-RELEASE-CANDIDATE')" var="isAccessReportingReleaseCandidate"/>
<security:authorize access="hasRole('ACCESS_REPORTING-PANBET-AUTOTESTS')" var="isAccessReportingPanbetAutotests"/>
<security:authorize access="hasRole('ACCESS_WORKBENCH')" var="isAccessWorkbench"/>
<security:authorize access="hasRole('ACCESS_WORKBENCH-MERGE-TASK-TO-BRANCH')" var="isAccessWorkbenchMergeTaskToBranch"/>
<security:authorize access="hasRole('ACCESS_WORKBENCH-TASK-DEPENDENCY-GRAPH')"
                    var="isAccessWorkbenchTaskDependencyGraph"/>
<security:authorize access="hasRole('ACCESS_TASK-MANAGEMENT')" var="isAccessTaskManagement"/>
<security:authorize access="hasRole('ACCESS_TASK-MANAGEMENT-TASK-MOVEMENT')" var="isAccessTaskManagementTaskMovement"/>
<security:authorize access="hasRole('ACCESS_RESOURCES-RESERVATION')" var="isAccessResourcesReservation"/>
<security:authorize access="hasRole('ACCESS_SUBSCRIPTIONS')" var="isAccessSubscription"/>
<security:authorize access="hasRole('ACCESS_ADMINISTRATION')" var="isAccessAdministration"/>
<security:authorize access="hasRole('ACCESS_ADMINISTRATION-USERS')" var="isAccessAdministrationUsers"/>
<security:authorize access="hasRole('ACCESS_ADMINISTRATION-ROLES')" var="isAccessAdministrationRoles"/>
<security:authorize access="hasRole('ACCESS_ADMINISTRATION-MAIL-CLIENT')" var="isAccessAdministrationMailClient"/>
<security:authorize access="hasRole('ACCESS_ADMINISTRATION-JABBER-CLIENT')" var="isAccessAdministrationJabberClient"/>
<security:authorize access="hasRole('ACCESS_ADMINISTRATION-TERMINAL')" var="isAccessAdministrationTerminal"/>
<security:authorize access="hasRole('ACCESS_TASK-MANAGEMENT-EXPORT-PROJECT-STATISTICS')"
                    var="isAccessTaskManagementExportProjectStatistics"/>

<!-- Header -->
<jsp:include page="../header.jsp"/>
<body onload="initProjectTitle()">


<div id="wrapper">
    <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="margin-bottom: 0">
        <div id="navbar-header" class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".sidebar-collapse">
                <span class="sr-only">Toggle navigation</span> <span class="icon-bar"></span> <span
                    class="icon-bar"></span> <span class="icon-bar"></span>
            </button>
            <span class="navbar-brand">
                <a href="<spring:url value="/" />" style="color: inherit">MANTICORE</a>
                <a id="manticore-version" target="_blank" href="${buildUrl}" style="color: inherit">v${buildVersion}</a>
            </span>
        </div>
        <!-- /.navbar-header -->

        <ul class="nav navbar-top-links navbar-right">
            <%--<li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i class="fa fa-envelope fa-fw"></i> <i
                        class="fa fa-caret-down"></i> </a>
                <ul class="dropdown-menu dropdown-messages">
                    <li>
                        <a href="#">
                            <div>
                                <strong>John Smith</strong>
                                            <span class="pull-right text-muted">
                                                <em>Yesterday</em>
                                            </span>
                            </div>
                            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <strong>John Smith</strong>
                                            <span class="pull-right text-muted">
                                                <em>Yesterday</em>
                                            </span>
                            </div>
                            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <strong>John Smith</strong>
                                            <span class="pull-right text-muted">
                                                <em>Yesterday</em>
                                            </span>
                            </div>
                            <div>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Pellentesque eleifend...</div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a class="text-center" href="#"> <strong>Read All Messages</strong> <i class="fa fa-angle-right"></i>
                        </a>
                    </li>
                </ul>
                <!-- /.dropdown-messages -->
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i class="fa fa-tasks fa-fw"></i> <i
                        class="fa fa-caret-down"></i> </a>
                <ul class="dropdown-menu dropdown-tasks">
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 1</strong> <span class="pull-right text-muted">40% Complete</span>
                                </p>

                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-success" role="progressbar" aria-valuenow="40"
                                         aria-valuemin="0" aria-valuemax="100" style="width: 40%">
                                        <span class="sr-only">40% Complete (success)</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 2</strong> <span class="pull-right text-muted">20% Complete</span>
                                </p>

                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-info" role="progressbar" aria-valuenow="20"
                                         aria-valuemin="0" aria-valuemax="100" style="width: 20%">
                                        <span class="sr-only">20% Complete</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 3</strong> <span class="pull-right text-muted">60% Complete</span>
                                </p>

                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-warning" role="progressbar" aria-valuenow="60"
                                         aria-valuemin="0" aria-valuemax="100" style="width: 60%">
                                        <span class="sr-only">60% Complete (warning)</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <p>
                                    <strong>Task 4</strong> <span class="pull-right text-muted">80% Complete</span>
                                </p>

                                <div class="progress progress-striped active">
                                    <div class="progress-bar progress-bar-danger" role="progressbar" aria-valuenow="80"
                                         aria-valuemin="0" aria-valuemax="100" style="width: 80%">
                                        <span class="sr-only">80% Complete (danger)</span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a class="text-center" href="#"> <strong>See All Tasks</strong> <i class="fa fa-angle-right"></i> </a>
                    </li>
                </ul>
                <!-- /.dropdown-tasks -->
            </li>
            <li class="dropdown" style="margin-right: -4px;">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"> <i class="fa fa-bell fa-fw"></i> <i
                        class="fa fa-caret-down"></i> </a>
                <ul class="dropdown-menu dropdown-alerts">
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-comment fa-fw"></i> New Comment <span
                                    class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-twitter fa-fw"></i> 3 New Followers <span
                                    class="pull-right text-muted small">12 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-envelope fa-fw"></i> Message Sent <span
                                    class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-tasks fa-fw"></i> New Task <span
                                    class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="#">
                            <div>
                                <i class="fa fa-upload fa-fw"></i> Server Rebooted <span
                                    class="pull-right text-muted small">4 minutes ago</span>
                            </div>
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a class="text-center" href="#"> <strong>See All Alerts</strong> <i class="fa fa-angle-right"></i> </a>
                    </li>
                </ul>
                <!-- /.dropdown-alerts -->
            </li>--%>
            <li id="shortcutProjectPAN"
                onclick="setDefaultProjectAndRepoAndPlanByShortcut(this, 'PAN (Panbet)', 'panbet', 'PANBUILD-PANBETMASTER'); setFiltersTitle('PAN')">
                <a class="m-label">PAN</a>
            </li>
            <li id="shortcutProjectWEB"
                onclick="setDefaultProjectAndRepoAndPlanByShortcut(this, 'WEB (Panweb)', 'panweb', 'WEB-PANWEB'); setFiltersTitle('WEB')">
                <a class="m-label">WEB</a>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                    <span id="current-username">${user.login}</span>
                    <i class="fa fa-user fa-fw"></i>
                    <i class="fa fa-caret-down"></i>
                </a>
                <ul class="dropdown-menu top dropdown-user">
                    <%--<li><a href="#"><i class="fa fa-user fa-fw"></i> User Profile</a>
                    </li>--%>
                    <li>
                        <a data-toggle="modal" href="#settingsModal" onclick="getAllProjects()">
                            <i class="fa fa-gear fa-fw"></i> Settings
                        </a>
                    </li>
                    <li class="divider"></li>
                    <li>
                        <a href="<spring:url value="/j_spring_security_logout" />">
                            <i class="fa fa-sign-out fa-fw"></i> Logout
                        </a>
                    </li>
                </ul>
                <!-- /.dropdown-user -->
            </li>
            <!-- /.dropdown -->
        </ul>
        <!-- /.navbar-top-links -->

        <div class="navbar-default navbar-static-side" role="navigation"
             style="border-right: 1px solid; height: 10000px">
            <div class="sidebar-collapse">
                <ul class="nav" id="side-menu">
                    <%--<li class="sidebar-search">
                        <div class="input-group custom-search-form">
                            <input type="text" class="form-control" placeholder="Search...">
                                        <span class="input-group-btn">
                                        <button id="search" class="btn btn-default" type="button">
                                            <i class="fa fa-search"></i>
                                        </button>
                                    </span>
                        </div>
                        <!-- /input-group -->
                    </li>--%>
                    <%--<li>
                        <a id="nav-item-dashboard" onclick=""><i
                                class="fa fa-dashboard fa-fw"></i> Dashboard</a>
                    </li>--%>
                    <c:if test="${isAccessAtlassian}">
                        <li>
                            <a id="nav-item-atlassian"><i class="fa fa-university fa-fw" aria-hidden="true"></i>
                                ATLASSIAN<span
                                        class="fa arrow"></span></a>
                            <ul class="nav nav-second-level collapse">
                                <c:if test="${isAccessAtlassianJira}">
                                    <li>
                                        <a id="nav-item-atlassian_jira">
                                            <i class="fa fa-tasks fa-fw" aria-hidden="true"></i> JIRA<span
                                                class="fa arrow"></span>
                                        </a>
                                        <ul class="nav nav-third-level collapse">
                                            <c:if test="${isAccessAtlassianJiraFilters}">
                                                <li>
                                                    <a id="nav-item-atlassian_jira_filters"><i
                                                            class="fa fa-filter fa-fw" aria-hidden="true"></i>
                                                        FILTERS<span
                                                                class="fa arrow"></span></a>
                                                    <ul class="nav nav-fourth-level collapse">
                                                        <c:if test="${isAccessAtlassianJiraFiltersPanbet}">
                                                            <li>
                                                                <a id="nav-item-atlassian_jira_filters_panbet"
                                                                   onclick="loadAtlassianJiraFiltersPanbetContainerView('?tab=eligible_for_test', '${user.defaultRepo}')">PANBET</a>
                                                            </li>
                                                        </c:if>
                                                    </ul>
                                                </li>
                                            </c:if>
                                        </ul>
                                    </li>
                                </c:if>
                            </ul>
                        </li>
                    </c:if>
                    <c:if test="${isAccessRelease}">
                        <li>
                            <a id="nav-item-release" onclick="loadReleaseContainerView()">
                                <i class="fa icon-exit fa-fw"></i> Release
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${isAccessStatistics}">
                        <li>
                            <a id="nav-item-statistics" onclick="loadStatisticsContainerView()">
                                <i class="fa fa-bar-chart fa-fw"></i> Statistics
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${isAccessTesting}">
                        <li>
                            <a id="nav-item-testing">
                                <i class="fa icon-enter fa-fw"></i> Testing<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level collapse">
                                <c:if test="${isAccessTestingMasterTest || isAccessTestingNotInMasterTest}">
                                    <li>
                                        <a id="nav-item-master_test" onclick="loadMasterTestContainerView(
                                        <c:choose>
                                        <c:when test="${isAccessTestingMasterTest}">
                                                '?tab=Branch+master_test'
                                        </c:when>
                                        <c:otherwise>
                                                '?tab=not+in+master_test'
                                        </c:otherwise>
                                        </c:choose>
                                                )"><i class="fa icon-lab fa-fw"></i> Master Test
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </c:if>
                    <c:if test="${isAccessReporting}">
                        <li>
                            <a id="nav-item-reporting">
                                <i class="fa fa-book fa-fw"></i> REPORTING<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level collapse">
                                <c:if test="${isAccessReportingSpainJurisdiction}">
                                    <li>
                                        <a id="nav-item-spain_jurisdiction"
                                           onclick="loadSpainJurisdictionControlContainerView();">
                                            <i class="fa fa-user fa-fw"></i> SPAIN JURISDICTION
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${isAccessReportingReleaseCandidate}">
                                    <li>
                                        <a id="nav-item-release_candidate"
                                           onclick="loadReleaseCandidateContainerView();">
                                            <i class="fa fa-user fa-fw"></i> RELEASE CANDIDATE
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${isAccessReportingPanbetAutotests}">
                                    <li>
                                        <a id="nav-item-autotests"
                                           onclick="loadAutotestsContainerView();">
                                            <i class="fa fa-user fa-fw"></i> PANBET AUTOTESTS
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </c:if>

                    <%--<li>--%>
                    <%--<a id="nav-item-policy_control" onclick="loadPolicyControlContainerView()">--%>
                    <%--<i class="fa fa-shield fa-fw"></i> Policy Control--%>
                    <%--</a>--%>
                    <%--</li>--%>
                    <c:if test="${isAccessWorkbench}">
                        <li>
                            <a id="nav-item-workbench">
                                <i class="fa icon-hammer fa-fw"></i> Workbench<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level collapse" style="">
                                <c:if test="${isAccessWorkbenchMergeTaskToBranch}">
                                    <li>
                                        <a id="nav-item-merge_task_to_branch"
                                           onclick="loadMergeTaskToBranchContainerView()">
                                            <i class="fa icon-shuffle fa-fw"></i> Merge Task to Branch
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${isAccessWorkbenchTaskDependencyGraph}">
                                    <li>
                                        <a id="nav-item-task_dependency_graph"
                                           onclick="loadTaskDependencyGraphContainerView();">
                                            <i class="fa fa-pie-chart fa-fw"></i> Task dependency graph
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </c:if>
                    <c:if test="${isAccessTaskManagement}">
                        <li>
                            <a id="nav-item-task_management">
                                <i class="fa fa-cubes"></i> TASK MANAGEMENT<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level collapse" style="">
                                <c:if test="${isAccessTaskManagementTaskMovement}">
                                    <li>
                                        <a id="nav-item-task_movement"
                                           onclick="loadTaskMovementContainerView()">
                                            <i class="fa fa-bar-chart"></i> TASK MOVEMENT
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <ul class="nav nav-second-level collapse" style="">
                                <c:if test="${isAccessTaskManagementExportProjectStatistics}">
                                    <li>
                                        <a id="nav-item-export_project_statistics"
                                           onclick="loadExportProjectStatisticsContainerView()">
                                            <i class="fa icon-shuffle fa-fw"></i> EXPORT PROJECT STATISTICS
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <ul class="nav nav-second-level collapse" style="">
                                <c:if test="${isAccessRelease}">
                                    <li>
                                        <a id="nav-item-closed_tasks_statistic"
                                           onclick="loadClosedStatisticContainerView()">
                                            <i class="fa fa-bar-chart"></i> CLOSED TASKS STATISTIC
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </c:if>
                    <c:if test="${isAccessSubscription}">
                        <li>
                            <a id="nav-item-subscriptions" onclick="loadSubscriptionsContainerView()">
                                <i class="fa fa-shield fa-fw"></i> Subscriptions
                            </a>
                        </li>
                    </c:if>
                    <c:if test="${isAccessAdministration}">
                        <li>
                            <a id="nav-item-administration">
                                <i class="fa fa-cogs"></i> Administration<span class="fa arrow"></span>
                            </a>
                            <ul class="nav nav-second-level collapse" style="">
                                <c:if test="${isAccessAdministrationUsers}">
                                    <li>
                                        <a id="nav-item-users" onclick="loadUsersContainerView()">
                                            <i class="fa fa-user fa-fw"></i> Users
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${isAccessAdministrationRoles}">
                                    <li>
                                        <a id="nav-item-roles" onclick="loadRolesContainerView()">
                                            <i class="fa fa-users fa-fw"></i> Roles
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${isAccessAdministrationMailClient}">
                                    <li>
                                        <a id="nav-item-mail_client" onclick="loadMailClientContainerView()">
                                            <i class="fa fa-users fa-fw"></i> Mail Client
                                        </a>
                                    </li>
                                </c:if>
                                <c:if test="${isAccessAdministrationJabberClient}">
                                    <li>
                                        <a id="nav-item-jabber_client" onclick="loadJabberClientContainerView()">
                                            <i class="fa fa-users fa-fw"></i> Jabber Client
                                        </a>
                                    </li>
                                </c:if>
                                <li>
                                    <a id="nav-item-alerts" onclick="loadAlertsContainerView()">
                                        <i class="fa fa-exclamation-circle fa-fw"></i> Alerts
                                    </a>
                                </li>
                                <li>
                                    <a id="nav-item-test_rank" onclick="loadTestRankContainerView()">
                                        <i class="fa fa-balance-scale fa-fw"></i> Test Rank Automation
                                    </a>
                                </li>
                                <c:if test="${isAccessAdministrationTerminal}">
                                    <li>
                                        <a id="nav-item-terminal" onclick="loadTerminalContainerView()">
                                            <i class="fa fa-cogs"></i> Terminal
                                        </a>
                                    </li>
                                </c:if>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </c:if>
                </ul>
                <!-- /#side-menu -->
            </div>
            <!-- /.sidebar-collapse -->
        </div>
        <!-- /.navbar-static-side -->
    </nav>

    <div id="page-wrapper"></div>
    <!-- /#page-wrapper -->

</div>
<!-- /#wrapper -->

<!-- Modal -->
<div id="comment" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-header">
        <select id="modulesSelect" class="selectpicker"
                data-style="btn btn-sm btn-primary dropdown-toggle"
                data-selected-text-format="count"
                data-live-search="true"
                data-width="auto" onchange="selectModuleComment()">
        </select>
        <span class="modal-title m-label" style="margin-top: 7px">
            <i class="fa fa-pencil">&nbsp;</i><span id="issueKey"></span>
        </span>
    </div>
    <div class="modal-body" style="text-align: center">
        <span id="timeChange" style="text-align: center"></span>
        <textarea id="comment-message" class="form-control" style="overflow: auto; height: 300px;"
                  onkeyup="includeButtonSave()"
                  contenteditable="true"></textarea>
    </div>
    <div class="modal-footer">
        <button id="save" type="button" class="btn btn-primary" disabled onclick="changeCommentQuestion()">
            <i class="fa fa-check">&nbsp;</i>SAVE
        </button>
        <button id="delete" type="button" class="btn btn-primary" onclick="generateDeleteCommentDialog()">
            <i class="fa fa-check">&nbsp;</i>DELETE
        </button>
        <button id="cancel" type="button" class="btn btn-primary" data-dismiss="modal">
            <i class="fa fa-times">&nbsp;</i>CANCEL
        </button>
    </div>
    <div id='comment-modal-response-message'>
    </div>
</div>

<!-- Modal -->
<div id="changeComment" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-body">
        <a>Your comment will override previous one. Do you wish to proceed?</a>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="changeComment()" data-dismiss="modal">
            <i class="fa fa-check"></i> Yes
        </button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">
            <i class="fa fa-times"></i>Cancel
        </button>
    </div>
</div>

<!-- Modal -->
<div id="errorComment" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-body">
        <span class="m-label margin-auto"
              style="color: red; text-shadow: 0 0 13px red">Error. Contact administrator</span>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" data-dismiss="modal">
            <i class="fa fa-times"></i>OK
        </button>
    </div>
</div>

<!-- Modal -->
<div id="deleteComment" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-body">
        <a>Your comment will be delete. Do you wish to proceed?</a>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="deleteComment()" data-dismiss="modal">
            <i class="fa fa-check"></i> Yes
        </button>
        <button type="button" class="btn btn-primary" data-dismiss="modal">
            <i class="fa fa-times"></i>Cancel
        </button>
    </div>
</div>

<!-- Modal -->
<div id="settingsModal" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <div class="modal-header align-center">
        <span class="m-label"> Settings</span>
    </div>
    <div class="modal-body">
        <div class="input-group margin-auto text-center">
            <div class="checkbox">
                <span class="m-label"> Choose default project </span>
                <select id="defaultProjectSelect" class="selectpicker" data-style="btn-primary"
                        data-width="auto" data-live-search="true" title="Project"
                        onchange="setUserDefaultProject(this.value); setFiltersTitle(this.value)">
                </select>
            </div>
            <div class="checkbox">
                <span class="m-label"> Choose default repo </span>
                <select id="defaultProjectRepoSelect" class="selectpicker" data-style="btn-primary"
                        data-width="auto" data-live-search="true" title="Repo"
                        onchange="setUserDefaultProjectRepo(this.value)">
                </select>
            </div>
            <div class="checkbox">
                <span class="m-label"> Choose default bamboo plan </span>
                <select id="defaultBambooPlanSelect" class="selectpicker" data-style="btn-primary"
                        data-width="auto" data-live-search="true" title="Plan"
                        onchange="setUserDefaultBambooPlan(this.value)">
                </select>
            </div>
        </div>
    </div>
    <div class="modal-footer">
        <button id="closeSettings" type="button" class="btn btn-primary align-left" data-dismiss="modal"
                onclick="updateIndexAndEligibleAndBranchMTPages()">
            <i class="fa fa-times"></i> Close
        </button>
    </div>
</div>

<!-- Footer -->
<jsp:include page="../footer.jsp"/>


<script type="text/javascript">
    /* Set page title */
    setPageTitle('MANTICORE');
    <c:choose>
    <c:when test="${control == 'release'}">

    // Release
    $(document).ready(function () {
        loadReleaseContainerView('${project}', '${repo}', '${since}', '${branch}')
    });
    </c:when>

    <c:when test="${control == 'statistics'}">

    // Statistics
    $(document).ready(function () {
        loadStatisticsContainerView()
    });
    </c:when>

    <c:when test="${control == 'master_test'}">

    // Master test
    $(document).ready(function () {
        loadMasterTestContainerView('${requestParams}')
    });
    </c:when>

    <c:when test="${control == 'atlassian_jira_filters_panbet'}">

    // Master Test Tools
    $(document).ready(function () {
        loadAtlassianJiraFiltersPanbetContainerView('${requestParams}', '${repoSlug}')
    });
    </c:when>

    <c:when test="${control == 'policy_control'}">

    // Policy Control
    $(document).ready(function () {
        loadPolicyControlContainerView()
    });
    </c:when>

    <c:when test="${control == 'spain_jurisdiction'}">

    // Spain Jurisdiction
    $(document).ready(function () {
        loadSpainJurisdictionControlContainerView()
    });
    </c:when>

    <c:when test="${control == 'autotests'}">

    // Spain Jurisdiction
    $(document).ready(function () {
        loadAutotestsControlContainerView()
    });
    </c:when>

    <c:when test="${control == 'release_candidate'}">

    // Release candidate report
    $(document).ready(function () {
        loadReleaseCandidateContainerView()
    });
    </c:when>

    <c:when test="${control == 'merge_task_to_branch'}">

    // Merge Task to Branch
    $(document).ready(function () {
        loadMergeTaskToBranchContainerView('${project}', '${repo}', '${task}')
    });
    </c:when>

    <c:when test="${control == 'task_movement'}">

    // Task Movement
    $(document).ready(function () {
        loadTaskMovementContainerView()
    });
    </c:when>

    <c:when test="${control == 'task_dependency_graph'}">

    // Graph Control
    $(document).ready(function () {
        loadTaskDependencyGraphContainerView('${requestParams}')
    });
    </c:when>

    <c:when test="${control == 'users'}">

    // User management
    $(document).ready(function () {
        loadUsersContainerView()
    });
    </c:when>

    <c:when test="${control == 'roles'}">

    // Role management
    $(document).ready(function () {
        loadRolesContainerView('${requestParams}')
    });
    </c:when>

    <c:when test="${control == 'mail_client'}">

    // Role management
    $(document).ready(function () {
        loadMailClientContainerView()
    });
    </c:when>

    <c:when test="${control == 'jabber_client'}">

    // Role management
    $(document).ready(function () {
        loadJabberClientContainerView()
    });
    </c:when>

    <c:when test="${control == 'subscriptions'}">

    $(document).ready(function () {
        loadSubscriptionsContainerView('${subscriptionId}')
    });
    </c:when>

    <c:when test="${control == 'alerts'}">

    // Role management
    $(document).ready(function () {
        loadAlertsContainerView()
    });
    </c:when>

    <c:when test="${control == 'test_rank'}">
    $(document).ready(function () {
        loadTestRankContainerView();
    });
    </c:when>

    <c:when test="${control == 'terminal'}">
    $(document).ready(function () {
        loadTerminalContainerView();
    });
    </c:when>

    </c:choose>

    <c:choose>
    <c:when test="${defaultProject == 'PAN'}">
    hoverDefaultProject('#shortcutProjectPAN');
    </c:when>

    <c:when test="${defaultProject == 'WEB'}">
    hoverDefaultProject('#shortcutProjectWEB');
    </c:when>

    <c:when test="${defaultProject == 'CONF'}">
    hoverDefaultProject('#shortcutProjectCONF');
    </c:when>

    <c:when test="${defaultProject == 'UTRADING'}">
    hoverDefaultProject('#shortcutProjectUT');
    </c:when>
    </c:choose>

    // Catch forward/backward navigation and react
    window.addEventListener("popstate", function (event) {
        historyPopState(event);
    });

    function getAllProjects() {
        $.ajax({
            type: "POST",
            url: "/base_controller/settings/getAllProjects"
        }).done(function (response) {
                const projectsList = response;
                let composedOptionsListString = '';
                for (let i = 0; i < projectsList.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', projectsList[i]);
                }
                const projectKey = getKeyFromDefaultProject(projectsList[0])
                getProjectRepos(projectKey)
                $("#defaultProjectSelect").html(composedOptionsListString).selectpicker('refresh');
            }
        ).fail(function () {
                console.log('Could not get projects');
            }
        );
    }

    function setUserDefaultProject(defaultProject) {
        $.ajax({
            type: "POST",
            url: "/base_controller/users/setUserDefaultProject",
            data: {
                defaultProject: defaultProject
            }
        }).done(function () {
            getProjectRepos(defaultProject)

            if (defaultProject.substring(0, defaultProject.indexOf(' ')) == 'PAN') {
                hoverDefaultProject('#shortcutProjectPAN')
            } else if (defaultProject.substring(0, defaultProject.indexOf(' ')) == 'WEB') {
                hoverDefaultProject('#shortcutProjectWEB')
            } else if (defaultProject.substring(0, defaultProject.indexOf(' ')) == 'CONF') {
                hoverDefaultProject('#shortcutProjectCONF')
            } else if (defaultProject.substring(0, defaultProject.indexOf(' ')) == 'UTRADING') {
                hoverDefaultProject('#shortcutProjectUT')
            } else {
                hoverDefaultProject(null);
            }

            console.log('The default project has been successfully modified');
        }).fail(function () {
            console.log('Something went wrong while changing the default project');
        });
    }

    function getProjectRepos(defaultProject) {
        $.ajax({
            type: "POST",
            url: "/base_controller/settings/getProjectRepos",
            data: {
                defaultProject: defaultProject
            }
        }).done(function (response) {
                const reposList = response;
                let composedOptionsListString = '';
                for (let i = 0; i < reposList.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', reposList[i]);
                }
                setUserDefaultProjectRepo(reposList[0]);
                getBambooPlans(reposList[0])
                $("#defaultProjectRepoSelect").html(composedOptionsListString).selectpicker('refresh');
            }
        ).fail(function () {
                console.log('Could not get projects');
            }
        );
    }

    function getBambooPlans(repo) {
        $.ajax({
            type: "POST",
            url: "/base_controller/settings/getBambooPlans",
            data: {
                repo: repo
            }
        }).done(function (response) {
                const plansList = response;
                let composedOptionsListString = '';
                for (let i = 0; i < plansList.length; i++) {
                    composedOptionsListString += sprintf('<option value="%1$s" class="text-left">%1$s</option>', plansList[i]);
                }
                setUserDefaultBambooPlan(plansList[0]);
                $("#defaultBambooPlanSelect").html(composedOptionsListString).selectpicker('refresh');
            }
        ).fail(function () {
                console.log('Could not get Bamboo plans');
            }
        );
    }

    function setUserDefaultProjectRepo(repoKey) {
        $.ajax({
            type: "POST",
            url: "/base_controller/users/setUserDefaultProjectRepo",
            data: {
                repoKey: repoKey
            }
        }).done(function () {
            if (repoKey == 'panbet') {
                hoverDefaultProject('#shortcutProjectPAN')
            } else if (repoKey == 'panweb') {
                hoverDefaultProject('#shortcutProjectWEB')
            } else if (repoKey == 'webpanbet') {
                hoverDefaultProject('#shortcutProjectCONF')
            } else if (repoKey == 'followservice') {
                hoverDefaultProject('#shortcutProjectUT')
            } else {
                hoverDefaultProject(null);
            }

            console.log('The default project repo has been successfully modified');
        }).fail(function () {
            console.log('Something went wrong while changing the default project repo');
        });
    }

    function setUserDefaultBambooPlan(planKey) {
        $.ajax({
            type: "POST",
            url: "/base_controller/users/setUserDefaultBambooPlan",
            data: {
                planKey: planKey
            }
        }).done(function () {
            if (planKey == 'PANBUILD-PANBETMASTER') {
                hoverDefaultProject('#shortcutProjectPAN')
            } else if (planKey == 'WEB-PANWEB') {
                hoverDefaultProject('#shortcutProjectWEB')
            } else if (planKey == 'UNITRADING-UNITRADINGSERVICE') {
                hoverDefaultProject('#shortcutProjectUT')
            } else {
                hoverDefaultProject(null);
            }

            console.log('The default plan has been successfully modified');
        }).fail(function () {
            console.log('Something went wrong while changing the default plan');
        });
    }

    function setFiltersTitle(defaultProject) {
        document.getElementById('nav-item-atlassian_jira_filters_panbet').innerHTML = getKeyFromDefaultProject(defaultProject);
    }

    function getKeyFromDefaultProject(defaultProject) {
        let arr = defaultProject.match(/\S+/gi);
        return arr[0];
    }

    function initProjectTitle() {
        $.ajax({
            type: "GET",
            url: "/base_controller/settings/initProjectName"
        }).done(function (response) {
            document.getElementById('nav-item-atlassian_jira_filters_panbet').innerHTML = response;
        }).fail(function () {
            console.log('Something went wrong while changing the title');
        });
    }

    function updateIndexAndEligibleAndBranchMTPages() {
        loadBlockContainer('branch-master-test', '/testing_controller/branch_master_test', true, '', '', '');
        loadBlockContainer('eligible-for-test', '/panbet_controller/eligible_for_test/${defaultRepo}', true, '', '', '');
        loadBlockContainer('eligible-for-rtm', '/panbet_controller/eligible_for_rtm/${defaultRepo}', true, '', '', '');
        loadBlockContainer('eligible-for-merge', '/panbet_controller/eligible_for_merge/${defaultRepo}', true, '', '', '')
        loadBlockContainer('nav-item-atlassian', '/', true, '', '', '')
    }

    function hoverDefaultProject(element) {
        $('li').each(function () {
            if (this.id.startsWith('shortcut')) {
                if (this != element) {
                    $(this).css('background-color', '#2c2c2c');
                    $(this).find('a').css('color', '#a9b7c5');
                }
            }
        })
        if (element != null) {
            $(element).css('background-color', '#a9b7c5');
            $(element).find('a').css('color', '#2c2c2c');
        }
    }

    function setDefaultProjectAndRepoAndPlanByShortcut(element, project, repo, plan) {
        $.ajax({
            type: "POST",
            url: "/base_controller/users/setDefaultProjectAndRepoAndPlanByShortcut",
            data: {
                project: project,
                repo: repo,
                plan: plan
            }
        }).done(function () {
            hoverDefaultProject(element);
            updateIndexAndEligibleAndBranchMTPages()
            let href = window.location.href;
            if (href.contains('/atlassian/jira/filters/')) {
                let slashPos = href.lastIndexOf('/');
                if (href.contains('?tab=eligible_for_test')) {
                    window.location.replace(href.substring(0, slashPos + 1) + repo + '?tab=eligible_for_test');
                } else if (href.contains('?tab=eligible-for-rtm')) {
                    window.location.replace(href.substring(0, slashPos + 1) + repo + '?tab=eligible-for-rtm');
                } else {
                    window.location.replace(href.substring(0, slashPos + 1) + repo + '?tab=eligible-for-merge');
                }
            }
        }).fail(function () {
            console.log('Something went wrong while changing the default project');
        });
    }
</script>

</body>
</html>