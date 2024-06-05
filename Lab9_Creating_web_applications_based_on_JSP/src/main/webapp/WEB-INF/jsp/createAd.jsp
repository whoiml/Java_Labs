<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Create ad</title>
</head>
<body>

<style>
    .required::after {
        content: ' *';
        color: red;
    }
</style>

<c:import url="header/header_importer.jsp"/>

<h2>Create Ad</h2>


<form action="/ad/create" method="post">
    <label class="required" for="adName">Ad name:</label><br>
    <input type="text" id="adName" name="adName" required><br><br>

    <label class="required" for="description">Description:</label><br>
    <input type="text" id="description" name="description" required><br><br>

    <label class="required" for="theme">Theme:</label><br>
    <input type="text" id="theme" name="theme" required><br><br>

    <input id="submitbutton" type="submit" value="Create">
</form>
</body>
</html>
