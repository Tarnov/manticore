<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html lang="en">
<head></head>
<body>
	<h2>Build: <a target="_blank" href="${buildUrl}">${buildVersion}</a></h2>

	<h2>Status ${status}</h2>
	<h2>Start Time ${startTime}</h2>
	<h2>Up Time ${upTime}</h2>
	<div>
		<h2>Manticore, services URL's:</h2>
		<table>
			<c:forEach var="entry" items="${services}">
				<tr>
					<td>${entry.key}:</td>
					<td><a href="${entry.value}" target="_blank">${entry.value}</a></td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>