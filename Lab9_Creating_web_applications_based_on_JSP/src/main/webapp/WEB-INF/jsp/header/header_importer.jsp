<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<body>

<c:choose>
    <c:when test="${pageContext.request.userPrincipal.name == null}">
        <c:import url="header/not_logged_header.jsp"/>
    </c:when>
    <c:otherwise>
        <c:import url="header/logged_header.jsp"/>
    </c:otherwise>
</c:choose>
</body>
</html>
