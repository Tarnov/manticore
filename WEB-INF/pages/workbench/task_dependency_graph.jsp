<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<meta name="viewport" content="user-scalable=yes, width=device-width, initial-scale=1.0, maximum-scale=1.0">

<div class="row" id="dot-with-ghaph" style="margin-bottom: 15px;">
    <div style="width: 25%" id="dot-column">
                <pre id="dot-text" contenteditable="true" hidden
                     style="background-color: #2c2c2c; color: snow; overflow: scroll; height: 100vh;">
                </pre>
    </div>
    <style type="text/css">
        TD, TH {
            /* Рамка вокруг ячеек */
        }
    </style>
    <div id="graph-column" style="padding-left: 15px; padding-right: 15px">

        <div style="position: relative; height: 100%">
            <table class="nav_search" id="nav_search" style="text-align: center;">
                <tr>
                    <td>
                        <div class="input-group" style="padding-left: 1px">
                            <input id="search-text" class="form-control text-center m-label"
                                   placeholder="SEARCH"
                                   name="selectedSearch" type="text" onkeyup="searchByEnter()"
                                   onfocus="this.placeholder = ''"
                                   onblur="this.placeholder = 'SEARCH'"> <label
                                for="search-text" class="display-none"></label>

                            <div class="input-group-btn">
                                <button id="searchButton" type="button" class="btn btn-sm btn-primary"
                                        onclick="searchInDependsGraph()">
                                    <i class="fa fa-search"></i> Search
                                </button>

                            </div>
                        </div>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table class="m-label default-selected-color"
                               style="border: 0px; background-color: rgba(44, 44, 44, 0.5); border-collapse: separate; border-spacing: 7px 11px; margin-left: 0">
                            <tr>
                                <th style="padding: 0 0 15px 0">
                                    Priority
                                </th>
                                <th style="padding: 0 0 15px 0">
                                    Master_test
                                </th>
                            </tr>
                            <tr>
                                <td>
                                    <img id="legend-blocker-img" src="">
                                </td>
                                <td class="align-right">
                                    <img id="legend-masterTest-true-img" src="">
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <img id="legend-critical-img" src="">
                                </td>
                                <td class="align-right">
                                    <img id="legend-masterTest-false-img" src="">
                                </td>
                            </tr>

                            <tr>
                                <td>
                                    <img id="legend-other-img" src="">
                                </td>
                                <td></td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <div class="black-block"></div>
            <table class="nav_graph">
                <tr>
                    <td>
                        <div id="graph" style="height: 950px"></div>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>

<script type="text/javascript">

    var network;
    var issueNumberMap;
    var DOTString =
    ${dotJson}.dotString;
    var issueNumberMap =
    ${dotJson}.issueNumberMap;
    var jiraUri =
    ${dotJson}.jiraUri;
    $('#dot-text').html(DOTString);
    drawDependsGraph(DOTString);


    jQuery(document).ready(function ($) {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 200) {
                var yScroll = $(window).scrollTop();
                var yFixed;
                yFixed = $(window).scrollTop() - 200;
                $(".nav_search").css({"top": yFixed + "px"});
            } else {
                $(".nav_search").css({"top": "0"});

            }
        });
    });

    jQuery(document).ready(function ($) {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 200) {
                var yDScroll = $(window).scrollTop();
                var yDFixed;
                yDFixed = $(window).scrollTop() - 200;
                $("#dot-text").css({"top": yDFixed + "px"});
            } else {
                $("#dot-text").css({"top": "0"});

            }
        });
    });


    jQuery(document).ready(function ($) {
        $(window).scroll(function () {
            if ($(window).scrollTop() > 200) {
                var yGScroll = $(window).scrollTop();
                var yGFixed;
                yGFixed = $(window).scrollTop() - 200;
                $(".graph").css({"top": yGFixed + "px"});
            } else {
                $(".graph").css({"top": "0px"});

            }
        });
    });


    function drawDependsGraph(DOTString) {
        DOTString = DOTString.replace(/&gt;/g, '>');
        // provide data in the DOT language
        var parsedData = vis.network.convertDot(DOTString);

        var data = {
            nodes: parsedData.nodes,
            edges: parsedData.edges
        };

        for (var nodeNumber in data.nodes) {
            var node = data.nodes[nodeNumber];
            if (node['id'] > 0) {
                node['image'] = getSvgImage(node['label'], node['color'].border, node['shape']);
                node['shape'] = 'image';
                node['label'] = node['status'];
                node['font'] = {size: 16, color: '#ffffff', face: 'arial'};
            }
        }
        for (var edgeNumber in data.edges) {
            var edge = data.edges[edgeNumber];
            if (edge['from'] >= 0) {
                var color = edge.color['color'];
                edge['font'] = {
                    color: color,
                    strokeColor: '#2c2c2c'
                };
            }
        }
        console.log(data.edges);
        var container = document.getElementById('graph');

        document.getElementById("legend-masterTest-true-img").src = getSvgURL(8 * 10, 30, 'TRUE', 'grey', 'box');
        document.getElementById("legend-masterTest-false-img").src = getSvgURL(8 * 10, 30, 'FALSE', 'grey', '');
        document.getElementById("legend-blocker-img").src = getSvgURL(8 * 10, 30, 'BLOCKER', '#c62104', '');
        document.getElementById("legend-critical-img").src = getSvgURL(8 * 10, 30, 'CRITICAL', 'gold', '');
        document.getElementById("legend-other-img").src = getSvgURL(8 * 10, 30, 'OTHER', 'green', '');

        var options = parsedData.options;

        options['physics'] =
        {
            barnesHut: {
                gravitationalConstant: -10000
            }
        }
        // create a network
        network = new vis.Network(container, data, options);
        network.on('doubleClick', function (properties) {
            var nodeNumber = properties.nodes;
            var issueName = "";
            for (var nodeName in issueNumberMap) {
                if (issueNumberMap[nodeName] == nodeNumber) {
                    issueName = nodeName;
                }
            }
            if (issueName != "") {
                window.open(jiraUri + "browse/" + issueName);
            }
        });
    }


    function updateGraphView(checkbox) {
        var t = $('#' + checkbox + '-toggle').is(':checked');

        $('#' + checkbox + '-toggle').prop('checked', t);


        var dot = $('#dot-toggle').is(':checked');
        var graph = $('#graph-toggle').is(':checked');

        if (graph || dot) {
            $('#dot-text').prop('hidden', !dot);
            $('#graph-column').prop('hidden', !graph);
            if ((!graph) || (dot && graph)) {
                $('#dot-column').css({"float": "right", "padding-right": "15px"});
                $('#graph-column').css({"width": "75%"});
            }
            if (!dot) {
                $('#graph-column').css({"width": "100%"});
            }
        }
        else {
            $('#' + checkbox + '-toggle').prop('checked', !t);
        }

        setTimeout(function () {
            $('#graph-dot-view').focus()
        }, 0);
    }

    function searchInDependsGraph() {
        var issue = $('#search-text').val().toUpperCase();
        var numberIssue = issueNumberMap[issue];
        network.focus(numberIssue, {
            scale: 8.0,
            offset: {x: 0, y: 0},
            animation: {
                duration: 1000,
                easingFunction: "easeInOutQuad"
            }
        });
    }


    function searchByEnter(e) {
        e = e || window.event;
        if (e.keyCode === 13) {
            document.getElementById("searchButton").click()
        }
        return false;
    }

    function getSvgImage(issueName, color, shape) {
        var size = issueName.length;
        var mainX = size * 23;
        var mainY = 70;
        return getSvgURL(mainX, mainY, issueName, color, shape);
    }

    function getSvgURL(mainX, mainY, issueName, color, shape) {
        var centerX = mainX / 2;
        var centerY = mainY / 2;

        var shapeSvg;
        if (shape == 'box') {
            shapeSvg = '<rect x="0" y="0" width="100%" height="100%" fill="' + color + '"></rect>';
        }
        else {
            shapeSvg = '<ellipse cx="' + centerX + '" cy="' + centerY + '" rx="' + centerX + '" ry="' + centerY + '" fill="' + color + '"/>';
        }
        var data = '<svg xmlns="http://www.w3.org/2000/svg" width="' + mainX + '" height="' + mainY + '">' + shapeSvg +
                '<text x="' + centerX + '" y="' + centerY + '"  style="font-family: Segoe UI Light, Helvetica Neue Light, ' +
                'HelveticaNeue-Light, Helvetica Neue, Calibri, Helvetica, Arial, serif; font-size:' + (mainY * 50) / 100 + 'px;" text-anchor="middle" ' +
                'alignment-baseline="central">' + issueName + '</text>' +
                '</svg>';

        var DOMURL = window.URL || window.webkitURL || window;
        var svg = new Blob([data], {type: 'image/svg+xml;charset=utf-8'});
        var url = DOMURL.createObjectURL(svg);
        return url;
    }

</script>