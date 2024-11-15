<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<security:authorize access="hasRole('ACCESS_TESTING-MASTER-TEST')" var="isAccessTestingMasterTest"/>
<security:authorize access="hasRole('ACCESS_TESTING-NOT-IN-MASTER-TEST')" var="isAccessTestingNotInMasterTest"/>

<div role="tabpanel" style="margin-top: 15px;">
    <ul class="nav nav-tabs nav-justified" role="tablist" style="width: 100%">
        <c:if test="${isAccessTestingMasterTest}">
            <li id="tab-branch-master-test" role="presentation">
                <a class="m-label" href="#branch-master-test" style="width: 100%" aria-controls="branch-master-test"
                   role="tab" data-toggle="tab">Branch master_test</a>
            </li>
        </c:if>

        <c:if test="${isAccessTestingNotInMasterTest}">
            <li id="tab-not-in-master-test" role="presentation">
                <a class="m-label" href="#not-in-master-test" style="width: 100%" aria-controls="not-in-master-test"
                   role="tab" data-toggle="tab">Not in master_test</a>
            </li>
        </c:if>

    </ul>

    <div class="tab-content">
        <c:if test="${isAccessTestingMasterTest}">

            <div role="tabpanel" class="tab-pane" id="branch-master-test">
                <div class="row" style="margin-bottom: 15px;">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div id="panel" class="panel-heading">
                                <div id="branch-master-test-update-button" class="btn-group" style="height: 20px">
                                    <button type="button" class="btn btn-xs btn-update btn-primary"
                                            onclick="loadBlockContainer('branch-master-test', '/testing_controller/branch_master_test', true, '', '', '')">
                                        <i class="fa fa-refresh"></i> Update
                                    </button>
                                </div>
                                <div id="branch-master-test-update-loading" class="btn-group"
                                     style="height: 20px"></div>
                                <div class="pull-right">
                                    <a class="primary-color m-label panel-header" target="_blank"
                                       href=${gitLink}>master_test</a>
                                </div>
                            </div>
                            <div id="branch-master-test-body" class="panel-body"></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

        <c:if test="${isAccessTestingNotInMasterTest}">
            <div role="tabpanel" class="tab-pane" id="not-in-master-test">
                <div class="row" style="margin-bottom: 15px;">
                    <div class="col-lg-12">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <div id="not-in-master-test-update-button" class="btn-group" style="height: 20px">
                                    <button type="button" class="btn btn-xs btn-update btn-primary"
                                            onclick="loadBlockContainer('not-in-master-test', '/testing_controller/not_in_master_test', true, '', '', '')">
                                        <i class="fa fa-refresh"></i> Update
                                    </button>
                                </div>
                                <div id="not-in-master-test-update-loading" class="btn-group"
                                     style="height: 20px"></div>
                                <div class="pull-right">
                                    <a class="primary-color m-label panel-header" target="_blank"
                                       href=${gitLink}>master_test</a>
                                </div>
                            </div>
                            <div id="not-in-master-test-body" class="panel-body"></div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>

    </div>
</div>

<script type="text/javascript">

    var currentTab;

    if (${tab != null}) {
        currentTab = '${tab}'.toLowerCase().replace(/ /g, '-').replace(/_/g, '-');
    }
    else {
        currentTab = 'branch-master-test';
    }

    /* Set page title when other tab is selected */
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        currentTab = $(e.target).text().substr(0, 18);
        loadTabProperty(currentTab);
        var tabUrl = currentTab.toLowerCase().replace(/ /g, '_').replace(/-/g, '_');
        loadBlockContainer(currentTab.toLowerCase().replace(/ /g, '-').replace(/_/g, '-'), '/testing_controller/' + tabUrl, false, '', '', '');
        var callFunction = {                                 // some params of function than called this one:
            name: "loadMasterTestContainerView",            // name of function
            arguments: {"0": "?tab=" + currentTab.replace(/ /g, '+')}       // arguments function was called with
        };
        historyPushState('master_test', '/testing/master_test?tab=' + currentTab.replace(/ /g, '+'), callFunction);
    });

    function loadTabProperty(tab) {
        tab = tab.toLowerCase().replace(/ /g, '-').replace('_', '-');
        $('#tab-' + tab).addClass('active');
        $('#' + tab).addClass('active');
        if (tab == 'branch-master-test') {
            setPageTitle('TESTING / MASTER TEST / Master Test');
        }
        else {
            setPageTitle('TESTING / MASTER TEST / Not in Master Test');
        }
    }

    /* Preload internal containers */
    $(document).ready(function () {
        loadTabProperty(currentTab);
        preLoadBlockContainerByBody('/testing_controller', currentTab, '');
    });

    function setTabBranchMasterTestNumber(number) {
        if (number != 0) {
            $('#tab-branch-master-test').children().html('Branch master_test' + '&thinsp;<span class="counter">' + number + '</span>');
        }
        else {
            $('#tab-branch-master-test').children().html('Branch master_test' + '&thinsp;<span class="counter" >' + number + '</span>');
        }
    }

    function setTabNotInMasterTestNumber(number) {
        if (number == 0) {
            $('#tab-not-in-master-test').children().html('Not in master_test');
        }
        else {
            $('#tab-not-in-master-test').children().html('Not in master_test' + '&thinsp;<span class="counter">' + number + '</span>');
        }
    }


</script>
