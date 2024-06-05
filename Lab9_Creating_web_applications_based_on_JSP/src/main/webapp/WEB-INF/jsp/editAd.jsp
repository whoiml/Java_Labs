<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Edit ad</title>
</head>
<body>

<style>
    .required::after {
        content: ' *';
        color: red;
    }
</style>

<c:import url="header/header_importer.jsp"/>

<h2>Edit Ad</h2>


<form action="/ad/${ad.id}/edit" method="post">
    <label class="required" for="adName">Ad name:</label><br>
    <input type="text" id="adName" name="adName" value="${ad.adName}" required><br><br>

    <label class="required" for="description">Description:</label><br>
    <input type="text" id="description" name="description" value="${ad.description}" required><br><br>

    <label class="required" for="theme">Theme:</label><br>
    <input type="text" id="theme" name="theme" value="${ad.theme}" required><br><br>

    <input id="submitbutton" type="submit" value="Edit">
</form>
</body>
</html>
