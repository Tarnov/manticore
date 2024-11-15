<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row" style="margin-top: 15px;margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading" style="border-bottom: none; min-height: 35px">
                <div class="pull-right"></div>
            </div>
            <div class="panel-body">
                    <table class="margin-auto" style="margin-bottom: 15px;">
                        <tr>
                            <td style="height: 30px">
                                <div id="release_candidate-update-button" class="input-group">
                                    <div class="input-group-btn">
                                        <select id="report-select" class="selectpicker" data-style="btn-primary"
                                                data-width="auto" style="background: inherit" title="Preset">
                                            <c:forEach items="${candidates}" var="candidate">
                                                <option id="${candidate.getKey()}">${candidate.getValue()}</option>
                                            </c:forEach>
                                        </select>
                                        <button id="showButton" onclick="loadController()"
                                                class="btn btn-sm btn-primary" data-toggle="modal"> Show
                                        </button>
                                    </div>
                                </div>
                                <div id="release_candidate-update-loading" class="btn-group"
                                style="height: 20px; display:none;"></div>
                            </td>
                        </tr>
                    </table>
                <div id="release_candidate-body" style="min-height: 20px"></div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">
    /* Set page title */
    setPageTitle('RELEASE CANDIDATE');

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    function loadController() {
        const select = $('#report-select option:selected');
        loadReleaseCandidateController(select.attr("id"));
    }
</script>