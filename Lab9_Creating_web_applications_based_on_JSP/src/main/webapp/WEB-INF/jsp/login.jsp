<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User Login</title>
</head>
<body>
<h2>User Login</h2>

<c:if test="${error != null}">
    <div style="color: red">
        <c:out value="${error}"/>
        <p>
    </div>
</c:if>

<form action="/login" method="post">
    <label for="username">Username:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label for="password">Password:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Login">
</form>
</body>
</html>
