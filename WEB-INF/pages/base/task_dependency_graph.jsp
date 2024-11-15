<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta name="viewport" content="user-scalable=yes, width=device-width, initial-scale=1.0, maximum-scale=1.0">
<div class="row" style="margin-top: 15px; margin-bottom: 15px">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading" style="border-bottom: none; min-height: 1px;">
            </div>
            <div class="panel-body margin-auto">
                <div>
                    <table class="margin-auto" style="width: 100%">
                        <tr>
                            <td>
                                <div class="input-group" style="padding-left: 1px; width: 100%">
                                    <input id="jql-text" class="form-control text-center m-label" style="width: 100%"
                                           placeholder="JQL"
                                           name="selectedBranch" type="text" onkeyup="updateRedraw();redrawByEnter();"
                                           onfocus="this.placeholder = ''"
                                           onblur="this.placeholder = 'JQL'"> <label
                                        for="jql-text" class="display-none"></label>

                                    <div class="input-group-btn">
                                        <button id="redraw" type="button" class="btn btn-sm btn-primary"
                                                style="width: 100%"
                                                onclick="redrawGraph();" disabled>
                                            <i class="fa fa-refresh"></i> REDRAW
                                        </button>
                                        </div>

                                </div>
                            </td>

                            <td style="padding-top: 15px; color: snow;">
                                <table class="margin-auto">
                                    <tr>
                                        <td style="padding-left: 40px">
                                            <div class="checkbox">
                                                <input id="dot-toggle" type="checkbox"
                                                       onclick="updateGraphView('dot')">
                                                <label for="dot-toggle">DOT</label>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="checkbox">
                                                <input id="graph-toggle" type="checkbox" checked
                                                       onclick="updateGraphView('graph')">
                                                <label for="graph-toggle">GRAPH</label>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </td>
                        </tr>
                        <tr>
                            <td>
                       <div>
                                           <select id="remove-connected-component" class="selectpicker"
                                                data-style="btn btn-sm btn-primary dropdown-toggle"
                                                data-selected-text-format="count"
                                                title="<span class='bootstrap-select-disabled'>Numbers</span>"
                                                   data-width="auto" onchange="updateRedraw()">
                                            <option id="null" value="0"><a>0</a></option>
                                            <option id="one" value="1"><a>1</a></option>
                                            <option id="two" value="2"><a>2</a></option>
                                            <option id="three" value="3"><a>3</a></option>
                                            <option id="four" value="4"><a>4</a></option>
                                            <option id="five" value="5"><a>5</a></option>
                                            <option id="six" value="6"><a>6</a></option>
                                            <option id="seven" value="7"><a>7</a></option>
                                            <option id="eight" value="8"><a>8</a></option>
                                            <option id="nine" value="9"><a>9</a></option>
                                        </select>
                                        <select id="links-select" class="selectpicker"
                                                data-style="btn btn-sm btn-primary dropdown-toggle" multiple
                                                data-selected-text-format="count"
                                                title="<span class='bootstrap-select-disabled'>LINKS</span>"
                                                data-width="auto" onchange="updateRedraw()">
                                            <option id="link-blocks" value="1"><a>blocks</a></option>
                                            <option id="link-is-blocked-by" value="2"><a>is blocked by</a></option>
                                            <option id="link-is-required-for" value="3"><a>is required for</a></option>
                                            <option id="link-depends-on" value="4"><a>depends on</a></option>
                                            <option id="link-is-conflict-for" value="5"><a>is conflict for</a></option>
                                            <option id="link-conflicts-with" value="6"><a>conflicts with</a></option>
                                        </select>

                                        <select id="status-select" class="selectpicker"
                                                data-style="btn btn-sm btn-primary dropdown-toggle" multiple
                                                data-selected-text-format="count"
                                                onchange="updateRedraw()"
                                                title="<span class='bootstrap-select-disabled'>STATUSES</span>"
                                                data-width="auto">
                                            <option id="status-open" value="1"><a>OPEN</a>
                                            </option>
                                            <option id="status-paused" value="10514"><a>PAUSED</a>
                                            </option>
                                            <option id="status-reopened" value="4"><a>REOPENED</a>
                                            </option>
                                            <option id="status-in-progress" value="3"><a>IN
                                                PROGRESS</a></option>
                                            <option id="status-in-review" value="10012"><a>IN
                                                REVIEW</a>
                                            </option>
                                            <option id="status-resolved" value="5"><a>RESOLVED</a>
                                            </option>
                                            <option id="status-testing" value="10005"><a>TESTING</a>
                                            </option>
                                            <option id="status-in-test-review" value="10013"><a>IN TEST REVIEW</a>
                                            </option>
                                            <option id="status-tested" value="10008"><a>TESTED</a>
                                            </option>
                                            <option id="status-ready-to-merge" value="10006"><a>READY
                                                TO
                                                MERGE</a></option>
                                            <option id="status-merged" value="10007"><a>MERGED</a>
                                            </option>
                                            <%--Links with closed task - is not valid--%>
                                            <%--<option id="status-closed" value="6"><a>CLOSED</a>--%>
                                            <%--</option>--%>
                                        </select>
                                    </div>
                            </td>
                            <td style="padding-left: 40px">
                                <button type="button" id="dot-to-graph" class="btn btn-sm btn-primary" style="width: 100%"
                                        onclick="drawDependsGraph($('#dot-text').html())"> DOT
                                    <i class="fa fa-arrow-right"></i> GRAPH
                                </button>
                            </td>
                        </tr>
                        <tr>
                            <td>
                        <div id="graphLoading" style="height: 25px" ></div>
                           </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="task_dependency_graph-body" style="min-height: 20px"></div>



<script type="text/javascript">

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    /* Set page title */
    setPageTitle('TASK DEPENDENCY GRAPH');

    var jiraUri;
    initParams();

    function redrawByEnter(e) {
        e = e || window.event;
        if (e.keyCode === 13) {
            document.getElementById("redraw").click()
        }
        return false;
    }


    function initParams() {
        var redraw = true;
        if (${jql != null} &&
        ${jql != ""})
        {
            selectJql('${jql}');
            redraw = redraw && true;
        }
    else
        {
            redraw = false;
        }
        if (${links != null} &&
        ${links != ""})
        {
            var links = ('${links}')
                    .replace(/_/g, '-')
                    .split(",");
            selectLinks(links);
            redraw = redraw && true;
        }
    else
        {
            redraw = false;
        }
        if (${statuses != null} &&
        ${statuses != ""})
        {
            var statuses = ('${statuses}')
                    .replace(/_/g, '-')
                    .split(",");
            selectStatuses(statuses);
            redraw = redraw && true;
        }
    else
        {
            redraw = false;
        }
        if (${remove_connected_component != null} &&
        ${remove_connected_component != ""})
        {
            var removeConnectedComponent = ('${remove_connected_component}')
                    .replace(/_/g, '-');
            selectRemoveConnectedComponent(removeConnectedComponent);
            redraw = redraw && true;
        }
    else
        {
            redraw = false;
        }
        if (redraw == true) {
            redrawGraph();
        }
    }


    function redrawGraph() {
        updateViewUrl();
        var controllerParams =
        {
            jql                       : $('#jql-text').val(),
            links                     : getSelectedLinks(),
            statuses: getSelectedStatuses(),
            remove_connected_component: $('#remove-connected-component').val()
        };
        loadTaskDependencyGraphContainerController(controllerParams);
    }


    function selectJql(jql) {
        $('#jql-text').val(jql);
        updateRedraw();
    }

    function selectStatuses(status) {
        $('option[id^="status-"]:selected')
                .each(function () {
                    $(this).prop("selected", false);
                }
        );
        for (var i = 0; i < status.length; i++) {
            var stat = status[i].replace(/ /g, '-');
            $('option[id^="status-"]')
                    .each(function () {
                        if ($(this).val() == stat) {
                            $(this).prop("selected", true);
                        }
                    });
        }
        $('#status-select').selectpicker('refresh');
        updateRedraw();
    }

    function selectLinks(links) {
        $('option[id^="link-"]:selected')
                .each(function () {
                    $(this).prop("selected", false);
                }
        );
        for (var i = 0; i < links.length; i++) {
            var link = links[i].replace(/\/ /, '').replace(/ /g, '-');
            $("#link-" + link).prop("selected", true);
            $('option[id^="link-"]')
                    .each(function () {
                        if ($(this).val() == link) {
                            $(this).prop("selected", true);
                        }
                    });
        }
        $('#links-select').selectpicker('refresh');
        updateRedraw();
    }

    function selectRemoveConnectedComponent(removeConnectedComponent) {
        $('#remove-connected-component').val(removeConnectedComponent);
        updateRedraw();
    }

    function getSelectedLinks() {
        var linksList = [];
        $('option[id^="link-"]:selected')
                .each(function () {
                    linksList.push($(this).val())
                }
        );
        return linksList.join().toLowerCase();
    }

    function getSelectedStatuses() {
        var statusList = [];
        $('option[id^="status-"]:selected')
                .each(function () {
                    statusList.push($(this).val())
                }
        );
        return statusList.join().toLowerCase();
    }

    function updateViewUrl() {

        var params = '';
        var jql = $('#jql-text').val();
        var links = getSelectedLinks().replace(/ /g, '_');
        var statuses = getSelectedStatuses().replace(/ /g, '_');
        var removeConnectedComponent = $('#remove-connected-component').val();
        if (jql != '') {
            params += '?jql=' + jql;
        }
        if (links != '') {
            if (params == '') {
                params += '?';
            }
            else {
                params += '&';
            }
            params += 'links=' + links;
        }
        if (statuses != '') {
            if (params == '') {
                params += '?';
            }
            else {
                params += '&';
            }
            params += 'statuses=' + statuses;
        }
        if (removeConnectedComponent != '') {
            if (params == '') {
                params += '?';
            }
            else {
                params += '&';
            }
            params += 'remove_connected_component=' + removeConnectedComponent;
        }
        var control = 'task_dependency_graph';
        var viewUrl = '/workbench/' + control;

        window.history.replaceState(null, control, viewUrl + params);
    }


    function updateRedraw() {
        var jql = $('#jql-text').val();
        var links = getSelectedLinks().replace(/ /g, '_');
        var statuses = getSelectedStatuses().replace(/ /g, '_');
        var removeConnectedComponent = $('#remove-connected-component').val();
        $('#redraw').prop('disabled', !((jql != '') && (links != '') && (statuses != '') && (removeConnectedComponent != '')));
    }

</script>

