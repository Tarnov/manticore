<%@ attribute name="count" required="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach var="i" begin="1" end="${count}">
    &nbsp;&nbsp;&nbsp;&nbsp;
</c:forEach>