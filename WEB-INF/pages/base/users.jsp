<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="rolesLength" value="${fn:length(roles)}" />
<c:set var="rolesFirstHalfLength" value="${fn:substringBefore(rolesLength div 2, '.') + (rolesLength mod 2)}" />

<div class="row" style="margin-top: 15px; margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <span class="primary-color m-label panel-header"><i class="fa fa-user"></i> USER MANAGEMENT</span>
                </div>
            </div>
            <div id="users-body" class="panel-body"></div>
        </div>
    </div>
</div>

<!-- Modal -->
<div id="user-modal" class="modal dialog" tabindex="-1" data-backdrop="static">
    <div class="modal-header">
        <span class="modal-title m-label"><i class="fa fa-pencil"></i> <span id="username"></span></span>
    </div>
    <div class="modal-body">
        <table class="width-full margin-auto">
            <tr>
                <td>
                    <div class="display-none">
                        <input id="userId" type="text"><label for="userId"></label>
                    </div>
                    <div style="padding: 5px">
                        <input id="full-name" class="form-control text-center" type="text" placeholder="Full name"
                               onfocus="this.placeholder = ''" onblur="this.placeholder = 'Full name';
                               $(this).tooltip('destroy').removeClass('error')">
                    </div>
                    <div style="padding: 5px">
                        <input id="e-mail" class="form-control text-center" type="text" placeholder="e-mail"
                               onfocus="this.placeholder = ''" onblur="this.placeholder = 'e-mail';
                               $(this).tooltip('destroy').removeClass('error')">
                    </div>
                    <div class="checkbox" style="padding-left: 25px; padding-top: 5px;">
                        <input id="enabled" type="checkbox" name="enabled"> <label for="enabled">Enabled</label>
                    </div>
                </td>
                <td class="vertical-align-top">
                    <c:set var="counter" value="0"/>
                    <ul style="list-style-type: none">
                        <c:forEach var="role" items="${roles}">
                            <li>
                                <div class="checkbox" style="margin-top: 5px; margin-bottom: 0">
                                    <c:set var="roleId" value="${fn:toLowerCase(role.name).replace(' ', '-')}" />
                                    <input id="role-${roleId}" type="checkbox" name="role-${roleId}"><label
                                        for="role-${roleId}">${role.name}</label>
                                        <i class="fa fa-pencil fa-lg clickable" data-dismiss="modal"
                                           data-role-id="${roleId}" onclick="loadModalRole('${roleId}')"></i>
                                </div>
                            </li>
                        </c:forEach>
                    </ul>
                </td>
            </tr>
        </table>
    </div>
    <div id="user-modal-footer-container" class="modal-footer">
        <button id="close" type="button" class="btn btn-primary" data-dismiss="modal"><i class="fa fa-times"></i> Close</button>
        <button id="save" type="button" class="btn btn-primary" onclick="updateUser();"><i class="fa fa-check"></i> Save
        </button>
    </div>
</div>
<!-- /.modal -->

<script type="text/javascript">

    /* Set page title */
    var title = 'ADMINISTRATION / USERS';
    setPageTitle(title);

    /* Set page title after closing of modal */
    $('#user-modal').on('hidden', function () {
        setPageTitle(title);
    });

    /* Preload list of users */
    loadBlockContainer('users', '/administration_controller/users', false, '', '', '');

    /* Modify by Enter */
    $('#full-name,#e-mail').keypress(function (event) {
        if (event.which == 13) {
            $("#save").click();
        }
    });

    function loadModalRole(roleId) {
        // Role management
        $(document).ready(function () {
            loadRolesContainerView();
            setTimeout(function ()
            {
                $('#role').modal('show')
            }, 500);
            setTimeout(function ()
            {
                generateRoleDialog(roleId);
                $('#role-name').focus();
            }, 700);
        });
    }

</script>
