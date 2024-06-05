<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>${user.username}'s page</title>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

<c:import url="header/header_importer.jsp"/>

<h2>User page</h2>

<p>Username: ${user.username}</p>
<p>Email: ${user.email}</p>
<p>Name: ${user.name}</p>

<p>All ads:</p>

<p>Search by: </p>

<form action="/ad/user" method="post">
    <label for="theme">Theme:</label>
    <input type="text" id="theme" name="theme" value="${selectedOptions.theme}">

    <label for="date">Date:</label>
    <input type="date" id="date" name="date" value="${selectedOptions.date}">

    <input type="submit" value="Find">
</form>

<br>

<div>
    <c:forEach items="${ads}" var="ad">
        <a href='/ad/${ad.id}'>${ad.adName} made by ${ad.username} at ${ad.creationDate}</a>
        <p>
    </c:forEach>
</div>
</body>
</html>
