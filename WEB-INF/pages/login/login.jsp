<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- logout -->

<!-- Header -->
<jsp:include page="../header.jsp"/>
<body>


<div class="jumbotron vertical-center">
    <div class="container text-center">
        <div id="login-error" class="form-group"></div>
        <div class="col-md-4 col-md-offset-4">
            <form role="form" method="post">
                <fieldset style="border: none">
                    <div class="form-group">
                        <input id="login" class="form-control text-center m-label" placeholder="Login" onkeyup="validate()"
                               onfocus="this.placeholder = ''" onblur="this.placeholder = 'Login'" name="username"
                               type="text" autofocus> <label for="login" class="display-none"></label>
                    </div>
                    <div class="form-group">
                        <input id="passwd" class="form-control text-center m-label" placeholder="Password"
                               onfocus="this.placeholder = ''" onblur="this.placeholder = 'Password'" name="password"
                               type="password" value=""> <label for="passwd" class="display-none"></label>
                    </div>
                    <div id="submit-container" class="form-group align-right">
                        <input id="submit" type="submit" class="btn btn-lg btn-success" value="Login"
                               onclick="authenticate()">
                    </div>
                </fieldset>
            </form>
        </div>
    </div>
</div>


<!-- Footer -->
<%@include file="../footer.jsp" %>
<script language="javascript">
    // User update submit action
    $('#passwd,#login').keypress(function (event) {
        if (event.which == 13) {
            $("#submit").click();
        }
    });

    function authenticate() {
        var submitContainer = $("#submit-container");
        var submitContainerContent = submitContainer.html();
        var processResponseHandling = function (errorMsg) {
            // Unblock controls
            $('#passwd, #login').removeAttr('disabled');
            // Restore container content
            submitContainer.html(submitContainerContent);
            // Show message according to response
            $("#login-error").html(errorMsg);
            // Clear out password field
            $("#passwd").val('');
        };
        $('#passwd,#login').attr('disabled', 'disabled').blur();
        $.ajax({
            type: "POST",
            url : "/login/authenticate",
            data: {
                username: $("#login").val(),
                password: $("#passwd").val()
            }
        }).done(function (response) {
            window.location.href = response.redirect;
        }).fail(function (response) {
            if (response.status == 401) {
                processResponseHandling('Invalid login or password');
            } else {
                processResponseHandling('Error during authentication. Please, contact your administrator.');
            }
        });
        submitContainer.html(getLoadingAnimation('top: 15px', 'success', 10));
    }

    function validate() {
        $.ajax({
            type: "POST",
            url : "/login/validateLoginField",
            data: {login: $("#login").val()}
        }).done(function (response) {
                    $("#login-error").html(response);
                });
    }
</script>
</body>
</html>
