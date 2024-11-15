<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<table class="margin-auto">
    <tbody>
    <tr class="m-label default-selected-color">
        <th>#</th>
        <th>Username</th>
        <th>Full name</th>
        <th>Roles</th>
        <th>Enabled</th>
        <th>Operations</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <tr class="mark-out upper-border base-text-color" style="min-height: 20px">
            <td id="id-${user.id}" class="text-right vertical-align-middle">${user.id}</td>
            <td id="username-${user.id}" class="text-center vertical-align-middle">${user.login}</td>
            <td class="text-center vertical-align-middle">
                <c:choose>
                    <c:when test="${user.email != null}">
                        <a id="full-name-${user.id}" href="mailto:${user.email}">${user.fullName}</a>
                    </c:when>
                    <c:otherwise>
                        <span id="full-name-${user.id}">-</span>
                    </c:otherwise>
                </c:choose>
            </td>
            <td class="vertical-align-middle text-right">
                <ul id="roles-${user.id}" class="list-style-type-none padding-none">
                    <c:forEach var="role" items="${user.roles}">
                        <li>${role.name}</li>
                    </c:forEach>
                </ul>
            </td>
            <td class="text-center">
                <c:set var="enabledClass" value="" scope="session" />
                <c:if test="${user.enabled}">
                    <c:set var="enabledClass" value="check" scope="session" />
                </c:if>
                <i id="enabled-${user.id}" class="fa fa-${enabledClass}-square-o"></i>
            </td>
            <td class="text-center vertical-align-middle">
                <i class="fa fa-pencil fa-lg clickable" data-toggle="modal" data-user-id="${user.id}"
                   onclick="setPageTitle('ADMINISTRATION / USERS / Edit user ' + '${user.fullName}');">
                </i> &nbsp;&nbsp;&nbsp;<i class="fa fa-trash-o fa-lg clickable" data-username="${user.login}"
                                          onclick="deleteUser($(this), '${user.id}')"></i>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<script type="text/javascript">

    /* Process edit request event and generate corresponding form */
    $('.clickable:not(.fa-trash-o)')
            .click(function ()
            {
                $('#user-modal').modal('show');
                generateUserDialog($(this));
                setTimeout(function ()
                {
                    $('#full-name').focus()
                }, 0);
            }
    );

    /* Remove ability to self-delete */
    $('[data-username]')
            .each(function()
            {
                if ($(this).attr('data-username') == $('#current-username').text())
                {
                    $(this).removeClass('clickable').addClass('visibility-hidden');
                }
            }
    );

</script>
