<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-4">
            <div class="login-panel panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Create User</h3>
                </div>
                <div class="panel-body">

                    <c:if test="${not empty errorMessage}">
                        <div class="has-error">${errorMessage}</div>
                    </c:if>
                    <c:if test="${not empty successMessage}">
                        <div class="has-success">${successMessage}</div>
                    </c:if>

                    <form:form action="/cu" method="get">
                        <fieldset>
                            <div class="form-group">
                                <input class="form-control" placeholder="Login" name="login" type="text" autofocus>
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Password" name="password" type="password" value="">
                            </div>
                            <div class="form-group">
                                <input class="form-control" placeholder="Role" name="role" type="text" value="">
                            </div>
                            <div class="form-group">
                                <input class="btn btn-lg btn-success btn-block" type="submit" value="Create">
                            </div>
                        </fieldset>
                    </form:form>

                </div>
            </div>
        </div>
    </div>
</div>
