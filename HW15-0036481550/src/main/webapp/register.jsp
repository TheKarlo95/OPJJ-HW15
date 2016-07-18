<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Blog - Register</title>
</head>
<body>
    <div>
        <form action="servleti/register" method="post">
            <input type="text" name="firstname" placeholder="First name"><br>
            <input type="text" name="lastname" placeholder="Last name"><br>
            <input type="text" name="email" placeholder="E-Mail"><br>
            <input type="text" name="username" placeholder="Username"><br>
            <input type="password" name="password" placeholder="Password"><br>
            <input type="submit" value="Login" />
        </form>
    </div>
</body>
</html>