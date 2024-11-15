<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<jsp:useBean id="now" class="java.util.Date"/>

<security:authorize access="hasRole('ACCESS_MANTICORE-DEBUG')" var="hasManticoreDebug"/>

<c:set var="taskStatusCounterAll" scope="session" value="0"/>
<c:forEach var="status" items="${taskStatusCounter.values()}">
    <c:set var="taskStatusCounterAll" scope="session" value="${taskStatusCounterAll + status}"/>
</c:forEach>

<c:if test="${hasManticoreDebug}">
    <div id="master-test-load-time" class="text-right color-gray font-style-italic font-size-smaller"
         style="margin-top: 5px"></div>
</c:if>
<div id="master-test-cache-update-time-ago" class="text-right color-gray font-style-italic font-size-smaller"></div>
<table id="selectRows" class="margin-auto" style="margin-bottom: 13px;">
    <tr>
        <td style="word-spacing: 0.01em;">
            <div class="checkbox checkbox-inline">
                <input id="all" type="checkbox" name="all"
                       onclick="toggleSelectAll('select', $(this), function() { filterMasterTestCommits() })"> <label
                    for="all">All&thinsp;<span class="counter">${taskStatusCounterAll}</span></label>
            </div>
            <div class="checkbox checkbox-inline">
                <input id="selectClosed" type="checkbox" name="selectMerged" onclick="filterMasterTestCommits()"><label
                    class="closed" for="selectClosed">Merged&thinsp;<span
                    class="counter">${taskStatusCounter.get("closed")}</span></label>
            </div>
            <div class="checkbox checkbox-inline">
                <input id="selectRtm" type="checkbox" name="selectRtmed" onclick="filterMasterTestCommits()"> <label
                    class="rtm" for="selectRtm">Ready-to-merge&thinsp;<span
                    class="counter">${taskStatusCounter.get("rtm")}</span></label>
            </div>
            <div class="checkbox checkbox-inline">
                <input id="selectTested" type="checkbox" name="selectTested" onclick="filterMasterTestCommits()"> <label
                    class="tested" for="selectTested">Tested&thinsp;<span
                    class="counter">${taskStatusCounter.get("tested")}</span></label>
            </div>
            <div class="checkbox checkbox-inline">
                <input id="selectOpen" type="checkbox" name="selectOpen" onclick="filterMasterTestCommits()"> <label
                    class="open" for="selectOpen">Open&thinsp;<span
                    class="counter">${taskStatusCounter.get("open")}</span></label>
            </div>
            <div class="checkbox checkbox-inline">
                <input id="selectResolved" type="checkbox" name="selectOther" onclick="filterMasterTestCommits()">
                <label class="resolved" for="selectResolved">Other&thinsp;<span
                        class="counter">${taskStatusCounter.get("resolved")}</span></label>
            </div>
            <div class="checkbox checkbox-inline">
                <input id="selectReverted" type="checkbox" name="selectReverted" onclick="filterMasterTestCommits()">
                <label class="reverted" for="selectReverted">Reverted&thinsp;<span class="counter"
                                                                                   style="font-style: normal;">${taskStatusCounter.get("reverted")}</span>
                </label>
            </div>
        </td>
    </tr>
</table>
<table class="margin-auto" style="margin-bottom: 7px;">
    <tr>
        <td>
            <div class="btn-group">
                <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" href="#static"
                        onclick="showMasterTestSelectedCommits()"><i class="fa fa-check-square-o"></i> Selected
                </button>
                <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" href="#static"
                        onclick="filterMasterTestCommits()"><i class="fa fa-eye"></i> Visible
                </button>
                <button type="button" class="btn btn-sm btn-primary" data-toggle="modal"
                        onclick="selectIssueByFilter()"><i class="fa fa-check-square-o"></i> select issue by filter
                </button>
            </div>
        </td>
    </tr>
</table>
<table class="margin-auto width-full">
    <tr>
        <td class="top">
            <table id="masterTestCommits" class="border-collapse width-full">
                <tr>
                    <td style="width: 1%">
                        <div class="checkbox" style="margin-bottom: 15px">
                            <input type="checkbox" id="toggleCheckCommits" name=""
                                   onclick="toggleSelectAll('checkbox-', $(this), function() { showMasterTestSelectedCommits() })">
                            <label for="toggleCheckCommits"></label>
                        </div>
                    </td>
                    <td style="width: 1%">
                        <label class="closed">Commits&thinsp;<span id="commits-counter" class="counter">0</span>
                        </label>
                    </td>
                    <td style="width: 1%">
                        <label class="closed">Issues&thinsp;<span id="issues-counter" class="counter">0</span>
                        </label>
                    </td>
                    <td>
                        <div class="form-group" style="margin-top: 10px">
                            <input id="kwd_search" class="form-control text-center m-label" placeholder="Search..."
                                   onfocus="this.placeholder = ''" onblur="this.placeholder = 'Search...'"
                                   onclick="this.setSelectionRange(0, this.value.length)" name="search" type="text">
                            <label for="kwd_search" class="display-none"></label>
                        </div>
                    </td>
                </tr>
                <c:forEach var="commit" items="${commits}" varStatus="counter">
                    <c:set var="dateColor" scope="session" value="#808080"/>
                    <c:if test="${now.time - 1209600000 > commit.getAuthorTimestamp().getTime()}">
                        <c:set var="dateColor" scope="session" value="#cc4839"/>
                    </c:if>
                    <tr id="row-${commit.id}" class="mark-out upper-border
                    <c:choose>
                        <c:when test="${commit.drawStrikeThrough()}">
                            reverted
                        </c:when>
                        <c:otherwise>
                            <c:out value="${commit.getStatus()}" />
                        </c:otherwise>
                    </c:choose>"
                        style="min-height: 20px">
                        <td class="vertical-align-middle">
                            <div class="checkbox">
                                <input type="checkbox" id="checkbox-${commit.id}"
                                       name="${commit.id}" onchange="changeCounters()">
                                <label for="checkbox-${commit.id}"></label>
                            </div>
                        </td>
                        <td class="text-center vertical-align-middle"
                            style="line-height:75%; padding-bottom: 5px; padding-top: 5px"
                            title="${commit.getFormattedTimeStamp()}">
                            <a target="_blank" class="m-label transition revision" style="position: relative"
                               href="${commit.commitLink}">${commit.id}</a><br>
                            <span id="${commit.id}" class="nowrap font-size-xx-small"
                                  style="color: ${dateColor}"></span>
                        </td>
                        <td class="text-center vertical-align-middle color-gray">
                                ${commit.authorDisplayName}<br>
                        </td>
                        <td id="message-${commit.id}">${commit.getMessage()}</td>

                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<!-- Modal -->
<div id="static" class="modal fade container" tabindex="-1" data-backdrop="static"
     style="display: none; margin-top: -100px; border: 1px dashed">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
    </div>
    <div class="modal-body">
        <div id="selectedRevisions" class="thin-border text-center" style="padding: 10px; margin: 5px"
             ondblclick="selectText($(this).attr('id'))"></div>
    </div>
</div>


<script type="text/javascript">

    var gitCherryPickCommand = 'git cherry-pick --strategy recursive -Xignore-cr-at-eol -Xignore-space-at-eol ';
    var hashTimeMap = [];
    <c:forEach var="commit" items="${commits}">
    hashTimeMap.push({hash: '${commit.id}', timestamp: ${commit.authorTimestamp.time}});
    </c:forEach>

    if (${notInMasterTestCounter != null}) {
        setTabNotInMasterTestNumber(${notInMasterTestCounter});
    }

    var mtViewDataCreationTime = ${mtViewDataCreationTime};
    updateTimestamps();
    setInterval(updateTimestamps, 5000);


    function updateTimestamps() {
        var currentTime = new Date().getTime();
        $("#master-test-cache-update-time-ago").html('Cache updated ' + wrapNumbersWithBold(millisToString(currentTime - mtViewDataCreationTime)) + '.');
        hashTimeMap.forEach(function (hashTime) {
            var timeSince = currentTime - hashTime.timestamp;
            $('#' + hashTime.hash).html(millisToString(timeSince));
        });
    }


    function filterMasterTestCommits() {

        var base = $("#masterTestCommits");
        var selectedClassList = [];
        var unselectedClassList = [];
        var selectedRevisionList = [];

        base.find("tr.mark-out").css('display', 'none');

        var contains = '';
        if ($("#kwd_search").val() != "") {
            contains = ":contains-ci('" + $("#kwd_search").val() + "')";
        }

        $("#selectRows").find("input[id^=select]").each(function () {
            if ($(this).is(':checked')) {
                selectedClassList.push('tr.' + $(this).attr('id').replace(/.*([A-Z].*)/g, "$1").toLowerCase() + contains);
            } else {
                unselectedClassList.push('tr.' + $(this).attr('id').replace(/.*([A-Z].*)/g, "$1").toLowerCase());
            }
        });

        base.find(selectedClassList.join(',')).css('display', '').find("a.revision").each(function () {
            selectedRevisionList.push($(this).html());
        });
        base.find(unselectedClassList.join(',')).css('display', 'none').find("a.revision").each(function () {
            selectedRevisionList.remove($(this).html());
        });

        $("#selectedRevisions").html(gitCherryPickCommand + selectedRevisionList.reverse().join(' '));
        setTabBranchMasterTestNumber(selectedRevisionList.length);
    }


    function showMasterTestSelectedCommits() {
        var selectedRevisions = [];

        $("input[type='checkbox'][id^='checkbox-']:checked").each(function () {
            if ($(this).parent('div').is(':visible')) {
                selectedRevisions.push($(this).attr('name'))
            }
        });
        changeCounters();
        $("#selected-commit-counter").html($("[id^=checkbox-]:checked").length);
        $("#selectedRevisions").html(gitCherryPickCommand + selectedRevisions.reverse().join(' '));
    }

    function changeCounters() {
        var commitCounter = 0;
        var issues = '';
        $("[id^=checkbox-]:checked").each(
            function () {
                var checkbox = $(this);
                var id = checkbox.attr('id').replace('checkbox-', '');
                var message = $("#message-" + id).html();
                commitCounter++;
                var found = ${projectKey == 'WEB'} ? message.match(/WEB-[\d]+/g) : message.match(/PAN-[\d]+/g);
                for (var j = 0; j < found.length; j++) {
                    if (issues.indexOf(found[j]) == -1) {
                        issues = issues + found[j] + ",";
                    }
                }
            }
        );
        $("#commits-counter").html(commitCounter);

        var result = ${projectKey == 'WEB'} ? issues.match(/WEB-[\d]+/g) : issues.match(/PAN-[\d]+/g);
        if (result != null) {
            $("#issues-counter").html(result.length);
        } else {
            $("#issues-counter").html(0);
        }
    }


    function selectIssueByFilter() {
        var button = $("#branch-master-test-update-button");                   // default container for showing
        var loading = $("#branch-master-test-update-loading");
        button.attr('style', 'display:none;');
        loading.html(getLoadingAnimation('height: 15px', 'primary', 5));
        $.ajax({
            type: "GET",
            url: "/testing_controller/branch_master_test/issues_by_filter"
        })
            .done(function (response) {
                $("[id^=checkbox-]").each(
                    function () {
                        var checkbox = $(this);
                        var id = checkbox.attr('id').replace('checkbox-', '');
                        var message = $("#message-" + id).html();
                        for (var i = 0; i < response.length; i++) {
                            if ((message.indexOf('>' + response[i] + '<') > -1) && (!$("#row-" + id)[0].classList.contains("reverted"))) {
                                checkbox.prop('checked', true);
                            }
                        }
                    }
                );
                button.attr('style', 'display:block;');
                loading.html("");
                changeCounters();
            })
            .fail(function (response) {
                    button.attr('style', 'display:block;');
                    button.html(showErrorBlockWithMessage(response));
                    loading.html("");
                    changeCounters();
                }
            );
    }


    // jQuery expression for case-insensitive filter
    $.extend($.expr[":"], {
        "contains-ci": function (elem, i, match, array) {
            return (elem.textContent || elem.innerText || $(elem).text() || "").toLowerCase().indexOf((match[3] || "").toLowerCase()) >= 0;
        }
    });


    $(document).ready(function () {
        // Write on key up event of keyword input element
        var delayKeyUp = (function () {
            var timer = 0;
            return function (callback, ms) {
                clearTimeout(timer);
                timer = setTimeout(callback, ms);
            };
        })();

        $("#kwd_search").keyup(function () {
            delayKeyUp(function () {
                filterMasterTestCommits();
            }, 500);
        });

        <c:if test="${hasManticoreDebug}">
        $("#master-test-load-time").html('Loaded in ' + wrapNumbersWithBold('${timer.stop()}') + '.');
        </c:if>

        $("input[type='checkbox'][id='all']").click();
    });

</script>
