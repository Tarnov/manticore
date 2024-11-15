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
                            <td style="height: 30px">
                                <div id="autotests-update-button" class="input-group">
                                    <div class="input-group-btn">
                                        <select id="report-select" class="selectpicker" data-style="btn-primary"
                                                data-width="auto" style="background: inherit" title="Preset">
                                            <c:if test="${builds != null}">
                                                <c:forEach items="${builds}" var="build">
                                                    <option id="report">${build.number}</option>
                                                </c:forEach>
                                            </c:if>
                                            <c:if test="${builds == null}">
                                                <option id="report">error</option>
                                            </c:if>
                                        </select>
                                        <c:if test="${builds != null || builds.size() == 0}">
                                            <button id="showButton" onclick="loadController()"
                                                    class="btn btn-sm btn-primary" data-toggle="modal"> Show</button>
                                        </c:if>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <div id="autotests-update-loading" class="btn-group"
                                     style="height: 20px; display:none;"></div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div id="autotests-body" style="min-height: 20px"></div>
                <c:if test="${error != null}">
                    <div class="error" style="min-height: 20px;">${error}</div>
                </c:if>
            </div>
        </div>
    </div>
</div>



<script type="text/javascript">

    /* Set page title */
    setPageTitle('PANBET AUTOTESTS');

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    function loadController()
    {
        const select = $('#report-select option:selected');
        loadPanbetAutotests(select.html());
    }


</script>