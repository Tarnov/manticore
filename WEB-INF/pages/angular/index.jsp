<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!doctype html>
<html ng-app>
<head>
    <script src="resources/js/angular/angular.min.js"></script>
</head>
<body>
<div>
    <label>Name:</label>
    <input type="text" ng-model="yourName" placeholder="Enter a name here">
    <hr>
    <h1>Hello {{yourName}}!</h1>
</div>
</body>
</html>