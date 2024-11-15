<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="manticore" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ACCESS_RELEASE-MAIL')" var="isAccessReleaseMail"/>
<security:authorize access="hasRole('ACCESS_RELEASE-BUILD')" var="isAccessReleaseBuild"/>


<%-- Prepare changes field --%>
<c:set var="changes" value="" scope="session"/>
<c:set var="changesForMail" value="" scope="session"/>
<c:set var="changesForBranchesMail" value="" scope="session"/>
<c:set var="number" value="1"/>
<c:forEach var="change" items="${issueSummaryMap}">
    <c:set var="changes" value="${changes}
# ${change.key} ${change.value}"/>
    <c:set var="changesForMail" value="${changesForMail}<div>${change.key} ${change.value}</div>"/>
    <c:set var="changesForBranchesMail" value="${changesForBranchesMail}<div>${number}.
                    <a href='${jiraHref}browse/${change.key}'>${change.key}</a> ${change.value}</div>"/>
    <c:set var="number" value="${number + 1}"/>
</c:forEach>

<%-- Prepare config changes field --%>
<c:set var="configChanges" value="" scope="session"/>
<c:set var="configChangesForMail" value="" scope="session"/>
<c:set var="number" value="1"/>
<c:if test="${fn:length(descriptionMap) > 0}">
    <c:set var="configChanges"
           value="h6. Все инструкции по изменению конфигурационных файлов находятся в поле _Description_ нижеследующих задач:"
           scope="session"/>
    <c:set var="configChangesList" value=""/>
    <c:forEach var="configChange" items="${descriptionMap}">
        <c:set var="configChanges" value="${configChanges}
# ${configChange.key} ${configChange.value}"/>
        <c:set var="configChangesList" value="${configChangesList} ${configChange.key}"/>

        <c:set var="configChangesForMail"
               value="${configChangesForMail}<div>${number}. <a href='${jiraHref}browse/${configChange.key}'>
                    ${configChange.key}</a> ${configChange.value}</div>"/>
        <c:set var="number" value="${number + 1}"/>
    </c:forEach>
</c:if>

<%-- Prepare message for latest build --%>
<c:set var="buildWarningMessage" value=""/>
<c:if test="${not latestBuildContainer.lastBuildSuccessful}">
    <c:set var="buildWarningMessage" value="Last build was not successful &#10"/>
</c:if>
<c:if test="${latestBuildContainer.newBuildIsRunning}">
    <c:set var="buildWarningMessage" value="${buildWarningMessage}Build is currently running"/>
</c:if>


<div id="release-load-time" class="text-right color-gray font-style-italic font-size-smaller"
     style="margin-top: 5px"></div>
<table class="margin-auto" style="margin-bottom: 23px;">
    <tbody>
    <tr>
        <td>
            <c:if test="${isAccessReleaseMail}">
                <button id="mailButton" type="button" class="btn btn-sm btn-primary" data-toggle="modal" href="#mail"
                        onclick="setPageTitle(getCurrentReleaseTitle() + ' / Mail')">
                    <i class="fa fa-envelope"></i> Mail
                </button>
            </c:if>
        </td>
        <c:if test="${runBuildChecked == true}">
            <td>
                <c:if test="${isAccessReleaseBuild}">
                    <div class="input-group">
                        <div class="input-group-btn">
                            <a id="runBuildButton" type="button" class="btn btn-primary" onclick="runBuild();"
                               target="_blank" disabled onclick="runBuild();"><i class="fa fa-cube"></i> Run build</a>
                            <select id="runBuildSelect" class="selectpicker" data-style="btn-primary" data-width="auto"
                                    title="Branch" onchange="$('#runBuildButton').attr('disabled', false);">
                                <option data-hidden="true"
                                        data-content="<span class='bootstrap-select-disabled'>Branch</span>"></option>
                                <option value="master_rc">master_rc</option>
                                <option value="master_hotfix">master_hotfix</option>
                                <option value="master_autotest">master_autotest</option>
                            </select>
                        </div>
                    </div>
                </c:if>
            </td>
        </c:if>
        <td>
            <button type="button" class="btn btn-sm btn-primary" data-toggle="modal" href="#static"
                    onclick="setPageTitle(getCurrentReleaseTitle() + ' / Deployment Task')">
                <i class="fa fa-bars"></i> Deployment Task
            </button>
        </td>
    </tr>
    </tbody>
</table>
<div id="release-response" class="success-text error-text" style="text-align: center; display: none">Response</div>

<c:if test="${fn:length(issueList) != 0}">
    <table style="margin-bottom: 20px">
        <tr>
            <td>
                <fieldset class="modal-fieldset" style="border-color: red">
                    <legend class="m-label" style="border-color: red">Missing Issues</legend>
                    <table class="margin-auto">
                        <tr class="m-label default-selected-color">
                            <th></th>
                            <th>Task</th>
                            <th>Reason</th>
                        </tr>
                        <c:forEach var="issue" items="${issueList}" varStatus="counter">
                            <tr class="mark-out upper-border">
                                <c:set var="style" scope="session"
                                       value='style="text-decoration: line-through; color: gray; font-style: italic;"'/>
                                <c:set var="style" scope="session" value=''/>
                                <c:set var="plus" scope="session"
                                       value='<a href="/workbench/merge_task_to_branch/task/${issue.key}" target="_blank"><i class="fa fa-plus clickable"></i></a>'/>

                                <td> ${plus} </td>
                                <td class="white-space-nowrap no-padding m-label">
                                    <div style="padding-top: 3px;">
                                        <img src="<c:out value="${issue.status.iconUrl}" escapeXml="false" />"
                                             title="${issue.status.name}"/>
                                        <a href="${jiraHref}browse/${issue.key}"
                                           target="_blank" ${style}>${issue.key}</a>
                                    </div>
                                </td>
                                <td class="no-padding">
                                    <ul class="list-style-type-none">
                                        <c:set var="reasonStyle" scope="session" value=""/>
                                        <c:forEach var="note" items="${reasons.get(issue.key)}">
                                            <c:set var="reasonStyle" scope="session" value="reason-minus"/>
                                            <li class="${reasonStyle}">${note.message}</li>
                                        </c:forEach>
                                    </ul>
                                </td>
                            </tr>
                        </c:forEach>
                    </table>
                </fieldset>
            </td>
        </tr>
    </table>
</c:if>

<c:set var="hasTOVersionChange" value="false"/>
<c:set var="hasFullRestart" value="false"/>
<c:set var="hasDBUpdate" value="false"/>
<table class="margin-auto width-full">
    <tr>
        <td class="top">
            <table id="releaseBranchCommits" class="border-collapse width-full">
                <c:forEach var="commit" items="${commits}" varStatus="counter">
                    <c:set var="rowTextColor" value="base-text-color"/>
                    <c:if test="${commit.getStatus() != 'CLOSED'}">
                        <c:set var="rowTextColor" value="delete-color"/>
                    </c:if>
                    <tr id="${commit.id}" class="mark-out upper-border ${rowTextColor}" style="min-height: 20px">
                        <td>
                            <c:if test="${flags.get('TO_VERSION_CHANGE').contains(commit.id)}">
                                <c:set var="hasTOVersionChange" value="true"/>
                                <i class="fa fa-warning warning-color" title="TO Version Change"></i>
                            </c:if>
                        </td>
                        <td class="text-center vertical-align-middle"
                            style="line-height:75%; padding-bottom: 5px; padding-top: 5px"
                            title="${commit.getFormattedTimeStamp()}">
                            <a target="_blank" class="m-label transition revision" style="position: relative"
                               href="${commit.commitURI}">${commit.shortId}</a><br> <span
                                class="nowrap font-size-xx-small">${commit.howLongCommited()}</span>
                        </td>
                        <td class="text-center vertical-align-middle">
                                ${commit.getAuthorName()}<br>
                        </td>
                        <td>${commit.getMessage()}</td>
                        <td>
                            <c:if test="${flags.get('FULL_RESTART').contains(commit.id)}">
                                <c:set var="hasFullRestart" value="true"/>
                                <i class="fa fa-repeat error-color" title="Full Restart"></i>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${flags.get('NO_ROLLBACK').contains(commit.id)}">
                                <c:set var="hasNoRollback" value="true"/>
                                <i class="fa fa-refresh warning-color" title="No Rollback"></i>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${flags.get('DB_UPDATE').contains(commit.id)}">
                                <c:set var="hasDBUpdate" value="true"/>
                                <i class="fa fa-database base-text-color" title="DB Update"></i>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </td>
    </tr>
</table>

<c:set var="descriptionOpt1" value=""/>
<c:set var="descriptionOpt2" value=""/>
<c:set var="descriptionText" value=""/>

<c:choose>
    <c:when test="${needsEPSRestart}">
        <c:set var="descriptionOpt1"
               value="Выкладку и проверку в инкубаторе проводить по схеме *Active/StandBy*. Кластер Epservice нуждается в полном перезапуске."/>
        <c:set var="descriptionOpt2"
               value="Выкладку и проверку в инкубаторе проводить по схеме *с рестартом АПП*. Кластер Epservice нуждается в полном перезапуске."/>
        <c:set var="descriptionText" value="${descriptionOpt2}"/>
    </c:when>
    <c:otherwise>
        <c:set var="descriptionOpt1" value="Выкладку и проверку в инкубаторе проводить по схеме *Active/StandBy*."/>
        <c:set var="descriptionOpt2" value="Выкладку и проверку в инкубаторе проводить по схеме *с рестартом АПП*."/>

        <c:choose>
            <c:when test="${hasFullRestart}">
                <c:set var="descriptionText"
                       value="Выкладку и проверку в инкубаторе проводить по схеме *с рестартом АПП*."/>
            </c:when>
            <c:otherwise>
                <c:set var="descriptionText"
                       value="Выкладку и проверку в инкубаторе проводить по схеме *Active/StandBy*."/>
            </c:otherwise>
        </c:choose>
    </c:otherwise>
</c:choose>

<!-- Modal -->
<div id="static" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <div class="modal-header align-right">
        <span class="m-label"><i class="fa fa-bars"></i> Deployment task generation</span>
    </div>
    <div class="modal-body">
        <table id="table" class="margin-auto">
            <tr>
                <td>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">Summary
                        </legend>
                        <pre id="summary" class="white-space-pre-wrap" contenteditable="true"></pre>
                        <table class="margin-auto">
                            <tr>
                                <td>
                                    <select id="summarySelect" class="selectpicker" data-style="btn-primary"
                                            data-width="108px" style="background: inherit" title="Summary"
                                            onchange="$('#summary').html($('#summarySelect option:selected').val())">
                                        <option data-hidden="true"
                                                data-content="<span class='bootstrap-select-disabled'>Summary</span>"></option>
                                        <option value="Deployment">regular</option>
                                        <option value="Deployment [HOTFIX]">hotfix</option>
                                        <option value="Deployment [DEMO]">demo</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">#Build
                        </legend>
                        <pre id="build" class="white-space-pre-wrap" contenteditable="true"></pre>
                        <div id="buildSelectContainer">
                            <table class="margin-auto">
                                <tr>
                                    <td>
                                        <select id="buildSelect" class="selectpicker" data-style="btn-primary"
                                                data-width="141px" style="background: inherit" title="Build"
                                                data-size="5" onchange="changeBuildVersion('build')">
                                            <option data-hidden="true"
                                                    data-content="<span class='bootstrap-select-disabled'>Build</span>"></option>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </fieldset>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">
                            <span class="cursor-help" data-toggle="tooltip" data-placement="top"
                                  title="Time format: d/mmm/yy HH:MM [A|P]M">Release Time</span>

                            <div class="checkbox">
                                <input type="checkbox" id="time-toggle" name="time-toggle"
                                       onclick="toggleField($(this), $('#time-store').html(), 'now')"> <label
                                    for="time-toggle"></label>
                            </div>
                            <div id="time-store" class="hidden"></div>
                        </legend>
                        <pre id="time" class="white-space-pre-wrap" contenteditable="true"></pre>
                    </fieldset>
                    <div class="white-space-nowrap flex-container">
                        <fieldset class="modal-fieldset flex-inline-sub-container">
                            <legend class="m-label legend-checkbox">Application

                                <div class="checkbox">
                                    <input type="checkbox" id="app-toggle" name="app-toggle" checked
                                           onclick="toggleFieldApplication($(this), 'update', '')"> <label
                                        for="app-toggle"></label>
                                </div>
                            </legend>
                            <pre id="app" class="white-space-pre-wrap" contenteditable="true">update</pre>
                        </fieldset>
                        <fieldset class="modal-fieldset flex-inline-sub-container">
                            <legend class="m-label legend-checkbox">Web
                                <div class="checkbox">
                                    <input type="checkbox" id="web-toggle" name="web-toggle" checked
                                           onclick="toggleField($(this), 'update', '')"> <label
                                        for="web-toggle"></label>
                                </div>
                            </legend>
                            <pre id="web" class="white-space-pre-wrap" contenteditable="true">update</pre>
                        </fieldset>
                        <fieldset class="modal-fieldset flex-inline-sub-container">
                            <legend class="m-label legend-checkbox">Web Services
                                <div class="checkbox">
                                    <input type="checkbox" id="ws-toggle" name="ws-toggle" checked
                                           onclick="toggleField($(this), 'update', '')"> <label for="ws-toggle"></label>
                                </div>
                            </legend>
                            <pre id="ws" class="white-space-pre-wrap" contenteditable="true">update</pre>
                        </fieldset>
                        <fieldset class="modal-fieldset flex-inline-sub-container">
                            <legend class="m-label legend-checkbox">Clients
                                <div class="checkbox">
                                    <input type="checkbox" id="clients-toggle" name="clients-toggle" checked
                                           onclick="toggleField($(this), 'update', '')"> <label
                                        for="clients-toggle"></label>
                                </div>
                            </legend>
                            <pre id="clients" class="white-space-pre-wrap" contenteditable="true">update</pre>
                        </fieldset>
                        <fieldset class="modal-fieldset flex-inline-sub-container">
                            <legend class="m-label legend-checkbox">Database
                                <div class="checkbox">
                                    <input type="checkbox" id="db-toggle" name="db-toggle"
                                           onclick="toggleField($(this), 'update', '')"> <label for="db-toggle"></label>
                                </div>
                            </legend>
                            <pre id="db" class="white-space-pre-wrap" contenteditable="true"></pre>
                        </fieldset>
                    </div>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">Config Changes <span class="counter">${fn:length(descriptionMap)}</span>
                        </legend>
                        <c:if test="${fn:length(descriptionMap) != 0}">
                            <div>
                                <pre id="config-changes" class="white-space-pre-wrap"
                                     contenteditable="true" ondblclick="selectText(this.id)">${configChanges}</pre>
                            </div>
                            <pre id="configChangesList"
                                 class="white-space-pre-wrap display-none">${fn:trim(configChangesList)}</pre>
                            <div id="linkConfigChanges">
                                <table class="margin-auto">
                                    <tr>
                                        <td>
                                            <div class="input-group">
                                                <input id="deploymentTask" class="form-control text-center m-label"
                                                       style="background: inherit" placeholder="Deployment task"
                                                       name="selectedBranch" type="text" onfocus="this.placeholder = ''"
                                                       onblur="this.placeholder = 'Deployment task'" value="DUTYADMIN-"
                                                       onkeyup="linkConfigsValidate()"/>

                                                <div class="input-group-btn">
                                                    <button id="linkConfigChangesButton" type="button"
                                                            class="btn btn-primary" disabled
                                                            onclick="linkConfigChanges()">
                                                        <i class="fa fa-exchange"></i> Link config changes
                                                    </button>
                                                </div>
                                            </div>
                                        </td>
                                        <td>
                                            <div class="text-center">
                                                <select id="config-changesModeSelect" class="selectpicker"
                                                        data-style="btn-primary"
                                                        dstyle="background: inherit" title="Config changes field mode"
                                                        onchange="changeMode('config-')">
                                                    <option selected value="Wiki" class="text-left">Wiki</option>
                                                    <option value="Text" class="text-left">Text</option>
                                                </select>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </c:if>
                    </fieldset>
                    <fieldset id="fieldset-rollback" class="modal-fieldset">
                        <legend class="m-label">Rollback
                        </legend>
                        <pre id="rollback" class="white-space-pre-wrap"
                             contenteditable="true">[${lastBuild}|${pathBuild}${lastBuild}/]</pre>
                        <div id="rollbackSelectContainer">
                            <table class="margin-auto">
                                <tr>
                                    <td>
                                        <select id="rollbackSelect" class="selectpicker" data-style="btn-primary"
                                                data-width="141px" style="background: inherit" title="Build"
                                                data-size="5" onchange="changeBuildVersion('rollback')">
                                            <option data-hidden="true"
                                                    data-content="<span class='bootstrap-select-disabled'>Build</span>"></option>
                                        </select>
                                    </td>
                                </tr>
                            </table>
                        </div>
                    </fieldset>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">Platform</legend>
                        <pre id="platform" class="white-space-pre-wrap" contenteditable="true">COM, CO.UK, ES, IT</pre>
                        <table class="margin-auto">
                            <tr>
                                <td>
                                    <select id="platformSelect" class="selectpicker" multiple="multiple"
                                            data-style="btn-primary" data-width="173px"
                                            data-selected-text-format="count>1" style="background: inherit"
                                            title="<span class='bootstrap-select-disabled'>Platform</span>"
                                            onchange="$('#platform').html($('#platformSelect option:selected').map(function(){ return this.value }).get().join(', ')); showReleaseTaskHelper()">
                                        <option data-hidden="true" data-content="Platform"></option>
                                        <option selected value="COM">COM</option>
                                        <option selected value="CO.UK">CO.UK</option>
                                        <option selected value="ES">ES</option>
                                        <option selected value="IT">IT</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">Changes <span class="counter">${fn:length(issueSummaryMap)}</span>
                        </legend>
                        <div>
                            <pre id="changes" class="white-space-pre-wrap" contenteditable="true"
                                 ondblclick="selectText(this.id)">${changes}</pre>
                        </div>

                        <table class="margin-auto">
                            <tr>
                                <td>
                                    <select id="changesModeSelect" class="selectpicker" data-style="btn-primary"
                                            data-width="141px" style="background: inherit" title="Changes field mode"
                                            data-size="5" onchange="changeMode('');">
                                        <option selected value="Wiki">Wiki</option>
                                        <option value="Text">Text</option>
                                    </select>
                                </td>
                            </tr>
                        </table>

                    </fieldset>
                    <fieldset class="modal-fieldset">
                        <legend class="m-label">Description
                            <div class="checkbox">
                                <input type="checkbox" id="description-toggle" name="description-toggle" checked
                                       onclick="toggleField($(this), $('#descriptionStorage').html(), '')"> <label
                                    for="description-toggle"></label>
                            </div>
                        </legend>
                        <div id="descriptionStorage" class="display-none">${descriptionText}</div>
                        <pre id="description" class="white-space-pre-wrap"
                             contenteditable="true">${descriptionText}</pre>
                        <table id="descriptionOption" class="margin-auto">
                            <tr>
                                <td>
                                    <select id="descriptionSelect" class="selectpicker" data-style="btn-primary"
                                            data-width="105px" title="Description"
                                            onchange="$('#description, #descriptionStorage').html($('#descriptionSelect option:selected').val()); showReleaseTaskHelper(); ">
                                        <option data-hidden="true"
                                                data-content="<span class='bootstrap-select-disabled'>Description</span>"></option>
                                        <option value="${descriptionOpt1}" data="as">A/S</option>
                                        <option value="${descriptionOpt2}" data="restart">Restart</option>
                                    </select>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
        </table>
        <table class="margin-auto">
            <tr>
                <td>
                    <fieldset class="modal-fieldset" style="border-color: red">
                        <legend class="m-label" style="border-color: red">Script
                        </legend>
                        <div>
                            <pre id="script" class="white-space-pre-wrap" ondblclick="selectText(this.id)"></pre>
                        </div>
                        <table class="margin-auto">
                            <tr>
                                <td>
                                    <a id="createTaskButton" type="button" class="btn btn-primary"
                                       href="${jiraHref}secure/CreateIssue.jspa?pid=11310&issuetype=14"
                                       target="_blank"><i class="fa fa-plus"></i> Create deployment task </a>
                                </td>
                            </tr>
                        </table>
                    </fieldset>
                </td>
            </tr>
        </table>
    </div>
    <div class="modal-footer">
        <button id="closeDeploymentTask" type="button" class="btn btn-primary" data-dismiss="modal"><i
                class="fa fa-times"></i> Close
        </button>
    </div>
</div>

<div id="mail" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <div class="modal-header align-right">
        <span class="m-label"><i class="fa fa-envelope"></i> Mail texts</span>
    </div>
    <div class="modal-body">
        <div role="tabpanel" style="margin-top: 15px;">
            <ul class="nav nav-tabs nav-justified" role="tablist">
                <li role="presentation" class="active"><a class="m-label" href="#panbet-release-mail"
                                                          aria-controls="panbet-release-mail" role="tab"
                                                          data-toggle="tab">PANBET RELEASE</a></li>
                <li role="presentation">
                    <a class="m-label" href="#branch-report-mail" aria-controls="branch-report-mail"
                       role="tab" data-toggle="tab">
                        BRANCH REPORT
                        <c:if test="${not latestBuildContainer.lastBuildSuccessful or latestBuildContainer.newBuildIsRunning}">
                            <i class="fa fa-warning warning-color" title="${buildWarningMessage}"/>
                        </c:if>
                    </a>
                </li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="panbet-release-mail">
                    <div class="row" style="margin-bottom: 15px;">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading"></div>
                                <div class="panel-body">
                                    <fieldset class="modal-fieldset">
                                        <legend class="m-label">ЗА 2 ЧАСА ДО НАЧАЛА ПОДГОТОВКИ РЕЛИЗ-КАНДИДАТА</legend>
                                        <div class="mail">
                                            <div id="mailBeforeRC" ondblclick="selectText(this.id)">
                                                Коллеги, добрый день!
                                                <br><br><br>
                                                Через 2 часа начнется формирование очередного релиз-кандидата для
                                                тестирования и последующего релиза.
                                                Просьба посмотреть все свои задачи (и ваших коллег по группе) и
                                                поставить их в соответствующие статусы (<b>Ready to Merge</b>),
                                                чтобы они попали в сборку релиз-кандидата.
                                                <br>
                                                После, задачи в сборку релиз-кандидата будут приниматься только с
                                                разрешения <a href="mailto:oleg_zasl@marathonbet.ru">Олега
                                                Заславского</a>,
                                                <a href="mailto:khomyakov@marathonbet.ru">Сергея Хомякова</a> и/или <a
                                                    href="mailto:geen@marathonbet.ru">Евгения Березовского</a>
                                                <span class="error-color"> в письменном виде на <a
                                                        href="mailto:deveng@marathonbet.ru">deveng@marathonbet.ru</a> (или по звонку) до 20:00 завтрашнего дня.</span>.
                                                <br><br>
                                                Список протестированных задач, готовых к релизу (не имеющих ограничений
                                                для релиза), но не в статусе <b>Ready to Merge</b> доступен по этой
                                                <a style="text-decoration: none"
                                                   href="http://manticore.mara.local/atlassian/jira/filters/panbet/eligible_for_rtm">ссылке</a>.
                                                <br>
                                                Список готовых к релизу задач (в статусе <b>Ready to Merge</b>) доступен
                                                по этой
                                                <a style="text-decoration: none"
                                                   href="http://manticore.mara.local/atlassian/jira/filters/panbet/eligible_for_merge">ссылке</a>
                                                (если это не так,
                                                незамедлительно свяжитесь с <a href="mailto:starnov@marathonbet.ru">Сергеем
                                                Тарновым</a> и продублируйте сообщение дежурному билд инженеру
                                                на <a href="mailto:deveng@marathonbet.ru">deveng@marathonbet.ru</a> для
                                                выяснения причин).
                                                <br>
                                                График соотношения тестируемых и готовых к тестированию задач доступен
                                                по этой <a style="text-decoration: none"
                                                           href="http://manticore.mara.local/statistics">ссылке</a>.
                                                <br><br>
                                                <span class="error-color">Так же УБЕДИТЕЛЬНАЯ ПРОСЬБА с момента получения первого письма о начале подготовки релиз-кандидата
                                                    и до получения последнего письма о завершении подготовки всем быть на рабочем месте (по возможности) –
                                                    в процессе подготовки могут возникать вопросы требующие немедленного реагирования компетентных лиц.</span>
                                                <br><br>
                                                Предварительный список задач-кандидатов на включение в релиз-кандидат
                                                <b>${eligibleParentForRtmAmount + eligibleParentForMergeAmount}</b>
                                                (включая subtasks: ${eligibleForRtmAmount + eligibleForMergeAmount}):
                                                <table style="margin: 5px; border: 1px solid gray">
                                                    <tbody>
                                                    <tr style="vertical-align: top">
                                                        <th>
                                                            <c:if test="${rtmEligibleGroups.size() > 0}">
                                                                <span class="status">TESTED</span>
                                                            </c:if>
                                                        </th>
                                                        <th>
                                                            <c:if test="${mergeEligibleGroups.size() > 0}">
                                                                <span class="status">READY TO MERGE</span>
                                                            </c:if>
                                                        </th>
                                                    </tr>
                                                    <tr style="vertical-align: top">
                                                        <td style="border: 1px solid gray">
                                                            <manticore:releaseMailViewGroups
                                                                    eligibleGroups="${rtmEligibleGroups}"/>
                                                        </td>
                                                        <td style="border: 1px solid gray">
                                                            <manticore:releaseMailViewGroups
                                                                    eligibleGroups="${mergeEligibleGroups}"/>
                                                        </td>
                                                    </tr>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </div>
                                        <table class="margin-auto">
                                            <tr>
                                                <td>
                                                    <a id="createMail" type="button" class="btn btn-primary"
                                                       style="margin-top: 10px"
                                                       href="mailto:panbet-release@marathonbet.ru?cc=deveng@marathonbet.ru&subject=%subject%"><i
                                                            class="fa fa-envelope"></i> Create mail</a>
                                                </td>
                                            </tr>
                                        </table>
                                    </fieldset>
                                    <fieldset class="modal-fieldset">
                                        <legend class="m-label">НАЧАЛО ПОДГОТОВКИ РЕЛИЗ-КАНДИДАТА</legend>
                                        <div class="mail">
                                            <div id="mailDuringRC" ondblclick="selectText(this.id)">
                                                Коллеги!
                                                <br><br><br>
                                                Началось формирование релиз-кандидата.
                                                <br>
                                                Напоминаю, что задачи в релиз-кандидатную сборку теперь принимаются
                                                только с разрешения <a href="mailto:oleg_zasl@marathonbet.ru">Олега
                                                Заславского</a>,
                                                <a href="mailto:khomyakov@marathonbet.ru">Сергея Хомякова</a> и/или <a
                                                    href="mailto:geen@marathonbet.ru">Евгения Березовского</a>
                                                <span class="error-color">в письменном виде на <a
                                                        href="mailto:deveng@marathonbet.ru">deveng@marathonbet.ru</a> (или по звонку) до 20:00 завтрашнего дня</span>.
                                                <br><br><br>
                                                Cписок задач на включение в релиз-кандидат
                                                <b>${eligibleParentForMergeAmount}</b> (включая
                                                subtasks: ${eligibleForMergeAmount}):
                                                <br><br>
                                                <manticore:releaseMailViewEligibleElements
                                                        taskElements="${mergeTaskElementsMainElements}" title="Задачи:"
                                                        childrenElementsMap="${mergeChildrenElementsMap}"/>
                                                <br>
                                                <manticore:releaseMailViewEligibleElements
                                                        taskElements="${mergeBugElementsMainElements}"
                                                        title="Исправления ошибок:"
                                                        childrenElementsMap="${mergeChildrenElementsMap}"/>
                                            </div>
                                        </div>
                                    </fieldset>
                                    <fieldset class="modal-fieldset">
                                        <legend class="m-label">ЗАВЕРШЕНИЕ ПОДГОТОВКИ РЕЛИЗ-КАНДИДАТА</legend>
                                        <div class="mail">
                                            <div id="mailAfterRC" ondblclick="selectText(this.id)">
                                                Коллеги!
                                                <br><br><br>
                                                Формирование релиз-кандидата завершено, сборка передана на автоматически
                                                тестирование,
                                                задачи в релизную сборку принимаются только с разрешения <a
                                                    href="mailto:oleg_zasl@marathonbet.ru">Олега Заславского</a>,
                                                <a href="mailto:khomyakov@marathonbet.ru">Сергея Хомякова</a> и/или <a
                                                    href="mailto:geen@marathonbet.ru">Евгения Березовского</a>
                                                <span class="error-color">в письменном виде на <a
                                                        href="mailto:deveng@marathonbet.ru">deveng@marathonbet.ru</a> (или по звонку) до 20:00 завтрашнего дня</span>
                                                и под вашу <span class="error-color">личную ответственность</span> (так
                                                как не будут проверены автоматическим тестированием).
                                                <br><br><br>
                                                Список задач <b>${issueParentDataSize}</b> (включая
                                                subtasks: ${issueDataSize}), попавших в релиз-кандидат:
                                                <br><br>
                                                <manticore:releaseMailView issueDataList="${issueDataListTask}"
                                                                           title="Задачи:"/>
                                                <manticore:releaseMailView issueDataList="${issueDataListBug}"
                                                                           title="Исправления ошибок:"/>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane" id="branch-report-mail">
                    <div class="row" style="margin-bottom: 15px;">
                        <div class="col-lg-12">
                            <div class="panel panel-default">
                                <div class="panel-heading"></div>
                                <div class="panel-body">
                                    <div class="panel-body">
                                        <fieldset class="modal-fieldset">
                                            <div id="branchMailDiv" class="mail">
                                                <div id="branchReport" ondblclick="selectText(this.id)">
                                                    Build #: <a
                                                        href='${bambooHref}browse/${buildKey}-${latestBuildContainer.planName}-${latestBuildContainer.buildNumber}'>
                                                    ${latestBuildContainer.latestBuild}</a>
                                                    <br>
                                                    <br>
                                                    <br>Список задач (<b>${issueDataSize}</b>):<br>
                                                    <br>
                                                    <manticore:releaseMailView issueDataList="${issueDataListTask}"
                                                                               title="Задачи:"/>
                                                    <manticore:releaseMailView issueDataList="${issueDataListBug}"
                                                                               title="Исправления ошибок:"/>
                                                    <c:if test="${fn:length(descriptionMap) > 0}">
                                                        Список задач на изменение конфигурации (<b>${fn:length(descriptionMap)}</b>):<br>
                                                        ${configChangesForMail}
                                                    </c:if>
                                                    <br>

                                                    <c:if test="${fn:length(modifiedConfigs) > 0}">
                                                        <br>
                                                        Список измененных конфигурационных файлов (<b>${fn:length(modifiedConfigs)}</b>):<br>
                                                        <c:forEach var="modifiedConfig" items="${modifiedConfigs}">
                                                            <a href='${modifiedConfig.value}'>${modifiedConfig.key}</a><br>
                                                        </c:forEach>
                                                    </c:if>
                                                </div>
                                            </div>
                                            <table class="margin-auto">
                                                <tr>
                                                    <td>
                                                        <a id="branchCreateMailButton" type="button"
                                                           class="btn btn-primary" style="margin-top: 10px"
                                                           href="mailto:qa_autotest@marathonbet.ru?cc=deveng@marathonbet.ru,amitkevich@marathonbet.ru,lsaenko@marathonbet.ru&subject=Branch %selected branch% build #%releaseNumber%"><i
                                                                class="fa fa-envelope"></i> Create mail</a>
                                                    </td>
                                                </tr>
                                            </table>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div class="modal-footer">
        <button id="closeMail" type="button" class="btn btn-primary" data-dismiss="modal"><i
                class="fa fa-times"></i> Close
        </button>
    </div>
</div>


<script type="text/javascript">

    var isExistsRollBackFlag = $(".fa-refresh.warning-color").length;    // whether build contains at least one task with filled No rollback

    /* Set page title on load*/
    setPageTitle(getCurrentReleaseTitle());

    /* Set page title after closing of modal */
    $('#static').on('hidden', function () {
        setPageTitle(getCurrentReleaseTitle());
    });

    $('#mail').on('hidden', function () {
        setPageTitle(getCurrentReleaseTitle());
    });

    /* Enable bootstrap select */
    $('.selectpicker').selectpicker();

    /* Set loading time */
    $("#release-load-time").html('Loaded in ' + '${timer.stop()}' + '.');


    /* Calculate and set:
     - summary based on branch requested
     - next and current build versions */
    var artifactoryFullPathToBuilds = '${artifactoryFullPathToBuilds}';    // full path to PANBET builds in Artifactory
    var buildVersion = '${lastRegularBuild}';
    var rollbackVersion = '${lastBuild}';
    var deploymentString = 'Deployment PAN ' + buildVersion;
    // compose and set DEPLOYMENT field value
    if ($("#selectedBranch").val() == 'master_hotfix') {
        deploymentString += ' [HOTFIX]';
        buildVersion = '${lastBuild}';
    } else if ($("#selectedBranch").val() != 'master_rc') {
        deploymentString += ' [DEMO]';
    }

    $('#mailButton').show();

    $("#summary").html(deploymentString);
    $('#summarySelect').selectpicker('val', deploymentString);
    // increase BUILD field version's last rank by one
    buildVersion = buildVersion.split('.');
    buildVersion[buildVersion.length - 1]++;
    buildVersion = buildVersion.join('.');
    // generate BUILD and ROLLBACK fields corresponding selects and preselect already filled versions
    $.ajax({
        type: "GET",
        url: "/artifactory_controller/get_build_versions_list"
    })
        .done(function (response) {
            var buildVersionsList = response;
            var composedOptionsListString = $("#buildSelect").html();
            var boolBuildString = false;
            for (var i = buildVersionsList.length - 1; i >= 0; i--) {
                composedOptionsListString += sprintf('<option value="%1$s">%1$s</option>',
                    buildVersionsList[i]);
                if (buildVersionsList[i] == buildVersion) {
                    boolBuildString = true;
                }
            }
            $("#rollbackSelect")
                .html(composedOptionsListString).selectpicker('refresh').selectpicker('val',
                rollbackVersion);
            if (!boolBuildString) {
                composedOptionsListString = sprintf('<option value="%1$s">%1$s</option>', buildVersion)
                    + composedOptionsListString;
            }
            $("#buildSelect")
                .html(composedOptionsListString).selectpicker('refresh').selectpicker('val',
                buildVersion);
            // set BUILD and ROLLBACK field values with corresponding versions
            changeBuildVersion('build');
            if (!isExistsRollBackFlag) {
                changeBuildVersion('rollback');
            }
        })
        .fail(function () {
                console.log('Could not get builds');
            }
        );
    // set next release version for mail template
    $('#createMail').attr('href', $('#createMail').attr('href').replace(/%subject%/, 'Релиз-кандидат ' + buildVersion));

    $('#branchCreateMailButton').attr('href', $('#branchCreateMailButton').attr('href')
        .replace(/%selected branch%/, $('#selectedBranch').val())
        .replace(/%releaseNumber%/, '${latestBuildContainer.latestBuild}'));

    /* Calculate and set release time */
    var date = new Date();
    var currentDay = date.getDay();
    // set date
    if ($('#selectedBranch').val() == 'master_rc') {
        if (currentDay < 4) {
            date.setDate(date.getDate() + 3 - currentDay)
        } else {
            date.setDate(date.getDate() + 5 - currentDay)
        }
    } else {
        date.setDate(date.getDate() + 1)
    }
    // set time
    date.setHours(5, 0);
    // set field with calculated date
    $('#time-store').html(date.format('d/mmm/yy HH:MM') + ' AM');
    if ($('#selectedBranch').val() == 'master_rc') {
        $('#time-toggle').click();
    } else {
        $('#time').html('now');
    }

    /* Set field DBUpdate to 'update' if so */
    if ('${hasDBUpdate}' == 'true') {
        $('#db-toggle').click();
    }

    /* Set DESCRIPTION select by default */
    $('#descriptionSelect').selectpicker('val', $('#description').text());

    /* Catch update of any pre fields event and update resulting java-script */
    document.getElementById("table").addEventListener("input", function () {
        delayKeyUp(function () {
            showReleaseTaskHelper();
        }, 500);
    }, false);

    /* Replace since value with one from row (for quick filtering) */
    $("tr.mark-out")
        .click(function (e) {
                if (e.shiftKey) {
                    $('#selectedSince').val(e.currentTarget.id);
                    loadReleaseContainerController('PAN', 'panbet', $('#selectedSince').val(),
                        $('#selectedBranch').val(), true)
                }
            }
        );

    /* Change cursor to pointer when Shift is pressed */
    $(document)
        .on('keydown', function (e) {
            if (e.shiftKey) {
                $('tr.mark-out').css('cursor', 'pointer');
            }
        })
        .on('keyup', function (e) {
                if (e.which == 16) {
                    $('tr.mark-out').css('cursor', 'default');
                }
            }
        );

    /* Init tooltips */
    $(function () {
            $('[data-toggle="tooltip"]').tooltip()
        }
    );


    /**
     * Update build version field
     *
     * @param id    id prefix for corresponding field
     */
    function changeBuildVersion(id) {
        $("#" + id).html(sprintf('[%1$s|%2$s%1$s/]', $("#" + id + "Select").val(), artifactoryFullPathToBuilds));
        showReleaseTaskHelper();
    }

    function runBuild() {
        let responseDiv = $('#release-response')
        responseDiv.css('display', 'none')
        responseDiv.addClass('success-text').addClass('error-text')

        $.ajax({
            type: "POST",
            data: {
                branch: $('#runBuildSelect option:selected').val()
            },
            url: "/release_controller/run"
        })
            .done(function (response) {
                responseDiv.css('display', 'block')
                responseDiv.removeClass('error-text')
                responseDiv.text('')
                jQuery('<a>', {
                    id: 'release-runbuild-success',
                    href: response[1]
                }).appendTo(responseDiv)
                $('#release-runbuild-success')
                    .text("Go to the build (release number " + response[0] + ")");
            })
            .fail(function () {
                responseDiv.css('display', 'block')
                responseDiv.removeClass('success-text')
                responseDiv.text('Error: could not run Build')
            });
    }

    var changesSelectFunction;
    var configChangesSelectFunction;

    changesSelectFunction = $('#changes').attr('onclick');
    $('#changes').attr('onclick', '');
    configChangesSelectFunction = $('#config-changes').attr('onclick');
    $('#config-changes').attr('onclick', '');

    function changeMode(configPrefix) {
        var newChangesContent = '';
        var fieldQuery = '#' + configPrefix + 'changes';
        var selectorId = configPrefix + 'changesModeSelect';
        var text;

        if ($('#' + selectorId + ' option:selected').val() == 'Wiki') {
            text = $(fieldQuery).text().split('\n');
            if (configPrefix != '') {
                newChangesContent += 'h6. Все инструкции по изменению конфигурационных файлов находятся в поле _Description_ нижеследующих задач:\n';
            }
            newChangesContent += '# ' + text[0];
            for (var i = 1; i < text.length; i++) {
                newChangesContent += '\n' + '# ' + text[i];
            }
            $(fieldQuery).html(newChangesContent).prop('contenteditable', true).attr('onclick', '');
        } else {
            text = $(fieldQuery).text().split('#');
            for (var i = 1; i < text.length; i++) {
                newChangesContent += text[i].substr(1);
            }
            if (configPrefix == '') {
                $(fieldQuery).html(newChangesContent).prop('contenteditable', false).attr('onclick', changesSelectFunction);
            } else {
                $(fieldQuery).html(newChangesContent).prop('contenteditable', false).attr('onclick', configChangesSelectFunction);
            }
        }
    }

    $('#runBuildSelect').selectpicker('val', $("#selectedBranch").val()).selectpicker('refresh');
    if ($('#runBuildSelect option:selected').val() == $("#selectedBranch").val()) {
        $('#runBuildButton').attr('disabled', false);
    }

    if (isExistsRollBackFlag) {
        $('#rollback').html('');
        $('#fieldset-rollback').prop('disabled', false).hide();
    }

</script>
