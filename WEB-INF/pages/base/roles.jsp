<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="row" style="margin-top: 15px; margin-bottom: 15px;">
    <div class="col-lg-12">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="pull-right">
                    <span class="primary-color m-label panel-header"><i class="fa fa-users"></i> ROLE MANAGEMENT</span>
                </div>
            </div>
            <div id="roles-body" class="panel-body"></div>
        </div>
    </div>
</div>

<script type="text/javascript">

    /* Set page title */
    var title = 'ADMINISTRATION / ROLES';
    setPageTitle(title);

    /* Set page title after closing of modal */
    $('#role').on('hidden', function () {
        setPageTitle(title);
    });

    /* Preload list of roles */
    loadBlockContainer('roles', '/administration_controller/roles', false, '', '', '');

    if (${loadRoleKey != null}) {
        setTimeout(function () {
            $('#role').modal('show');
            generateRoleDialog('${loadRoleKey}');
        }, 500);
        setTimeout(function () {
            $('#role-name').focus()
        }, 0);
    }

    /* Add by Enter */
    $('#role-name').keypress(function (event) {
        if (event.which == 13) {
            $("#add").click();
        }
    });


</script>
