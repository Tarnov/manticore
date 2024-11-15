<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="manticore" tagdir="/WEB-INF/tags" %>

<table class="margin-auto" style="margin-top: 5px">
    <tbody>
    <tr>
        <td>
            <div class="btn-group">
                <button type="button" class="btn btn-sm btn-primary clickable" data-toggle="modal" data-role-id="new"
                        onclick="setPageTitle('ADMINISTRATION / ROLES / Add role')"><i class="fa fa-plus"></i> Add role
                </button>
            </div>
        </td>
    </tr>
    </tbody>
</table>

<table class="margin-auto">
    <tbody>
    <tr class="m-label default-selected-color">
        <th>#</th>
        <th>Role name</th>
        <th>Accesses</th>
        <th>Operations</th>
    </tr>
    <c:forEach var="role" items="${roles}">
        <tr class="mark-out upper-border base-text-color" style="min-height: 20px">
            <td id="id-${role.name}" class="text-right vertical-align-middle">${role.id}</td>
            <td id="role-name-${role.name}" class="text-right vertical-align-middle">${role.name}</td>
            <td class="vertical-align-middle text-right">
                <c:choose>
                    <c:when test="${role.name ne 'SUPERADMIN'}">
                        <ul id="accesses-${role.name}" class="list-style-type-none padding-none">
                            <c:forEach var="access" items="${role.accesses}">
                                <li>${access.name}</li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        all
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="text-center vertical-align-middle">
                <c:if test="${role.name ne 'SUPERADMIN'}">
                    <i class="fa fa-pencil fa-lg clickable" data-toggle="modal" data-role-id="${role.name}">
                    </i> &nbsp;&nbsp;&nbsp;
                    <i class="fa fa-trash-o fa-lg clickable" data-rolename="${role.name}"
                       onclick="deleteRole($(this), '${role.id}')"></i>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>


<!-- Modal -->
<div id="role" class="modal dialog container" tabindex="-1" data-backdrop="static">
    <div class="modal-header">
        <span class="modal-title m-label"><i class="fa fa-pencil"></i> Role</span>
    </div>
    <div class="modal-body">
        <table class="width-full margin-auto">
            <tr>
                <td>
                    <div class="display-none">
                        <input id="roleId" type="text"><label for="roleId"></label>
                    </div>
                    <div style="padding: 5px">
                        <input id="role-name" class="form-control text-center m-label" type="text"
                               placeholder="Role name"
                               onfocus="this.placeholder = ''" onblur="this.placeholder = 'Role name';
                               $(this).tooltip('destroy').removeClass('error')">
                    </div>
                </td>
            </tr>
            <tr>
                <c:forEach var="access" items="${accessesHierarchy}">
                    <c:if test="${access.fullName == 'manticore-debug'}">
                        <td valign="top" align="center">
                            <div class="checkbox" style="margin-top: 5px; margin-bottom: 0">
                                <manticore:tabBuilder count="${access.depth}"/>
                                <input id="access-${access.fullName}" type="checkbox"
                                       onchange="changeParentAndChildren('${access.fullName}')"
                                       data-parent="${access.parent}" data-children="${access.children}"
                                       data-accessId="${access.id}"
                                <c:if test="${access.isDisabled()}">
                                       disabled
                                </c:if>
                                       name="access-${access.fullName}"><label
                                    for="access-${access.fullName}">${access.name}</label>
                            </div>
                        </td>
                    </c:if>
                </c:forEach>
            </tr>
            <tr>
                <td class="vertical-align-top">
                    <table>
                        <c:forEach var="access" items="${accessesHierarchy}">
                        <c:if test="${access.fullName != 'manticore-debug'}">
                        <c:if test="${access.depth == 0}">
                    </table>
                    <table class="col-lg-6">
                        </c:if>
                            <tr>
                                <td>
                                    <div class="checkbox" style="margin-top: 5px; margin-bottom: 0">
                                        <manticore:tabBuilder count="${access.depth}"/>
                                        <input id="access-${access.fullName}" type="checkbox"
                                               onchange="changeParentAndChildren('${access.fullName}')"
                                               data-parent="${access.parent}" data-children="${access.children}"
                                               data-accessId="${access.id}"
                                        <c:if test="${access.isDisabled()}">
                                               disabled
                                        </c:if>
                                               name="access-${access.fullName}"><label
                                            for="access-${access.fullName}">${access.name}</label>
                                    </div>
                                </td>
                            </tr>
                        </c:if>
                        </c:forEach>
                    </table>
                </td>
            </tr>
        </table>
    </div>
    <div class="modal-footer">
        <button id="close" type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close
        </button>
        <button id="change" type="button" class="btn btn-primary" disabled onclick="changeRole();"><i
                class="fa fa-check"></i> Change
        </button>
        <button id="add" type="button" class="btn btn-primary" disabled onclick="addRole();"><i class="fa fa-check"></i>
            Add
        </button>
    </div>
</div>
<!-- /.modal -->


<script type="text/javascript">


    /* Process edit request event and generate corresponding form */
    $('.clickable:not(.fa-trash-o)').click(function () {
        $('#role').modal('show');
        generateRoleDialog($(this).attr('data-role-id'));
        setTimeout(function () {
            $('#role-name').focus()
        }, 0);
    });

    function changeParentAndChildren(access) {
        if (!$('input[id=access-' + access + ']').is(":checked")) {
            $('input[id=access-' + access + ']').attr('data-children').split(',').forEach(function (item) {
                $('input[id=access-' + item + ']').prop("checked", false);
            });
        }
        var parent = $('input[id=access-' + access + ']').attr('data-parent');
        if (parent != "") {
            var parentChecked = false;
            $('input[id=access-' + parent + ']').attr('data-children').split(',').forEach(function (item) {
                parentChecked = parentChecked || $('input[id=access-' + item + ']').is(":checked");
            });

            if (parentChecked) {
                $('input[id=access-' + parent + ']').prop("checked", true);
                changeParentAndChildren(parent);
            }
            else {
                if ($('input[id=access-' + parent + ']').is(":disabled")) {
                    $('input[id=access-' + parent + ']').prop("checked", false);
                    changeParentAndChildren(parent);
                }
            }
        }
    }
</script>
