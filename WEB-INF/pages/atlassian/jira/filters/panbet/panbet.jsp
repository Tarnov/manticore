<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<div role="tabpanel" style="margin-top: 15px;">
    <ul class="nav nav-tabs nav-justified" role="tablist">
        <li id="tab-eligible-for-test" role="presentation">
            <a class="m-label" href="#eligible-for-test" aria-controls="eligible-for-test" role="tab" data-toggle="tab"
               data-tab="eligible-for-test">eligible for test</a>
        </li>
        <li id="tab-eligible-for-rtm" role="presentation">
            <a class="m-label" href="#eligible-for-rtm" aria-controls="eligible-for-rtm" role="tab" data-toggle="tab"
               data-tab="eligible-for-rtm">eligible for rtm</a>
        </li>
        <li id="tab-eligible-for-merge" role="presentation">
            <a class="m-label" href="#eligible-for-merge" aria-controls="eligible-for-merge" role="tab"
               data-toggle="tab"
               data-tab="eligible-for-merge">eligible for merge</a>
        </li>
    </ul>

    <div class="tab-content">
        <div role="tabpanel" class="tab-pane" id="eligible-for-test">
            <div class="row" style="margin-bottom: 15px;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="eligible-for-test-update-button" class="btn-group" style="height: 20px">
                                <button type="button" class="btn btn-xs btn-update btn-primary"
                                        onclick="loadBlockContainer('eligible-for-test', '/panbet_controller/eligible_for_test/${repoSlug}', true, '', '', '')">
                                    <i class="fa fa-refresh"></i> Update
                                </button>
                            </div>
                            <div id="eligible-for-test-update-loading" class="btn-group" style="height: 20px"></div>
                            <div class="pull-right">
                                <span>
                                     <a class="primary-color m-label panel-header" target="_blank"
                                        href="${originalUriEligibleForTest}">eligible for test</a>
                                </span>
                            </div>
                        </div>
                        <div id="eligible-for-test-body" class="panel-body"></div>
                    </div>
                </div>
            </div>
        </div>

        <div role="tabpanel" class="tab-pane" id="eligible-for-rtm">
            <div class="row" style="margin-bottom: 15px;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="eligible-for-rtm-update-button" class="btn-group" style="height: 20px">
                                <button type="button" class="btn btn-xs btn-update btn-primary"
                                        onclick="loadBlockContainer('eligible-for-rtm', '/panbet_controller/eligible_for_rtm/${repoSlug}', true, '', '', '')">
                                    <i class="fa fa-refresh"></i> Update
                                </button>
                            </div>
                            <div id="eligible-for-rtm-update-loading" class="btn-group" style="height: 20px"></div>
                            <div class="pull-right">
                                <span>
                                     <a class="primary-color m-label panel-header" target="_blank"
                                        href="${originalUriEligibleForRtm}">eligible for rtm</a>
                                </span>
                            </div>
                        </div>
                        <div id="eligible-for-rtm-body" class="panel-body"></div>
                    </div>
                </div>
            </div>
        </div>

        <div role="tabpanel" class="tab-pane" id="eligible-for-merge">
            <div class="row" style="margin-bottom: 15px;">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            <div id="eligible-for-merge-update-button" class="btn-group" style="height: 20px">
                                <button type="button" class="btn btn-xs btn-update btn-primary"
                                        onclick="loadBlockContainer('eligible-for-merge', '/panbet_controller/eligible_for_merge/${repoSlug}', true, '', '', '')">
                                    <i class="fa fa-refresh"></i> Update
                                </button>
                            </div>
                            <div id="eligible-for-merge-update-loading" class="btn-group" style="height: 20px"></div>
                            <div class="pull-right">
                                <span>
                                     <a class="primary-color m-label panel-header" target="_blank"
                                        href="${originalUriEligibleForMerge}">eligible for merge</a>
                                </span>
                            </div>
                        </div>
                        <div id="eligible-for-merge-body" class="panel-body"></div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">

    var currentTab;
    if (${tab != null}) {
        currentTab = '${tab}'.toLowerCase().replace(/ /g, '-').replace(/_/g, '-');
    }
    else {
        currentTab = "eligible-for-rtm";
    }


    /* Set page title when other tab is selected */
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
        currentTab = $(e.target).attr("data-tab");
        loadTabProperty(currentTab);
        var tabUrl = currentTab.toLowerCase().replace(/ /g, '_').replace(/-/g, '_');
        console.log('/panbet_controller/' + tabUrl + '/${repoSlug}')
        loadBlockContainer(currentTab.toLowerCase().replace(/ /g, '-').replace(/_/g, '-'), '/panbet_controller/' + tabUrl + '/${repoSlug}', false, '', '', '');

        var callFunction = {                                 // some params of function than called this one:
            name: "loadAtlassianJiraFiltersPanbetContainerView",            // name of function
            arguments: {"0": "?tab=" + currentTab.replace(/ /g, '+')}        // arguments function was called with
        };

        historyPushState('atlassian/jira/filters/panbet', '/atlassian/jira/filters/${repoSlug}?tab=' + currentTab.replace(/ /g, '_'), callFunction);
    });

    function loadTabProperty(tab) {
        tab = tab.toLowerCase().replace(/ /g, '-').replace('_', '-');
        $('#tab-' + tab).addClass('active');
        $('#' + tab).addClass('active');
        /* Set page title */
        if (tab == 'eligible-for-rtm') {
            setPageTitle('ATLASSIAN / JIRA / FILTERS / ${repoSlug.toUpperCase()} / ELIGIBLE FOR RTM');
        }
        else if (tab == 'eligible-for-merge') {
            setPageTitle('ATLASSIAN / JIRA / FILTERS / ${repoSlug.toUpperCase()} / ELIGIBLE FOR MERGE');
        }
        else if (tab == 'eligible-for-test') {
            setPageTitle('ATLASSIAN / JIRA / FILTERS / ${repoSlug.toUpperCase()} / ELIGIBLE FOR TEST');
        }
    }

    /* Preload internal containers */
    $(document).ready(function () {
        loadTabProperty(currentTab);
        preLoadBlockContainerByBody('/panbet_controller', currentTab, '${repoSlug}');
    });

    function setTabEligibleForRtmAmounts(eligibleAmount, notEligibleAmount) {
        $('#tab-eligible-for-rtm').children().html('eligible for rtm' + '&nbsp;<span class="counter">' +
            eligibleAmount + '&thinsp;/&thinsp;' + notEligibleAmount + '</span>');
    }

    function setTabEligibleForMergeAmounts(eligibleAmount, notEligibleAmount) {
        $('#tab-eligible-for-merge').children().html('eligible for merge' + '&nbsp;<span class="counter">' +
            eligibleAmount + '&thinsp;/&thinsp;' + notEligibleAmount + '</span>');
    }

    function setTabEligibleForTestAmounts(eligibleAmount, notEligibleAmount) {
        $('#tab-eligible-for-test').children().html('eligible for test' + '&nbsp;<span class="counter">' +
            eligibleAmount + '&thinsp;/&thinsp;' + notEligibleAmount + '</span>');
    }
</script>