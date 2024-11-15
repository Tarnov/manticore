<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<h1>${message}</h1>
<h3>You are ${user}</h3>

<a class="btn btn-primary" href="<spring:url value="/j_spring_security_logout" />">Logout</a>
