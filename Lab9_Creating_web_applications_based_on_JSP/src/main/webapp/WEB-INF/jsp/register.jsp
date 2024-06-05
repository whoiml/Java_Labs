<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>User register</title>
    <script src='https://www.google.com/recaptcha/api.js'></script>
</head>
<body>

<style>
    .required::after {
        content: ' *';
        color: red;
    }
</style>

<h2>User Login</h2>


<div style="color:red">
    <c:forEach items="${errors}" var="error">
        <c:out value="${error}"/>
        <p>
    </c:forEach>
</div>

<form action="/register" method="post">
    <label class="required" for="email">Email:</label><br>
    <input type="email" id="email" name="email" required><br><br>

    <label class="required" for="username">Username:</label><br>
    <input type="text" id="username" name="username" required><br><br>

    <label class="required" for="name">Name:</label><br>
    <input type="text" id="name" name="name" required><br><br>

    <label class="required" for="password">Password:</label><br>
    <input type="password" id="password" name="password" required><br><br>

    <label class="required" for="rePassword">Retype Password:</label><br>
    <input type="password" id="rePassword" name="rePassword" required><br><br>

    <div class="g-recaptcha col-sm-5"
         data-sitekey="6Lenjl4oAAAAANgknjB0kmafS6MA03GrAPp804PD"></div>
    <span id="captchaError" class="alert alert-danger col-sm-4"
          style="display:none"></span>

    <input id="submitbutton" type="submit" value="Login">
</form>

<br>
<br>

<a href="/login">Already have an account</a>
</body>
</html>
