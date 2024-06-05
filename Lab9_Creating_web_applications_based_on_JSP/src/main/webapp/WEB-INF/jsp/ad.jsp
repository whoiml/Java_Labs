<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Ad by ${ad.username}</title>
</head>
<body>

<c:import url="header/header_importer.jsp"/>

<h2>
    ${ad.adName}
</h2>

<div>
        Posted by ${ad.username}, at ${ad.creationDate}
</div>

<br>
<hr>
<br>

<div>
    ${ad.description}
</div>

<br>

<form action="/ad/${ad.id}/delete" method="post">
    <input type="submit" value="Delete">
</form>

<form action="/ad/${ad.id}/edit" method="get">
    <input type="submit" value="Edit">
</form>

</body>
</html>
