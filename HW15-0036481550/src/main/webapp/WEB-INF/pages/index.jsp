<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head>
<title>Blog - Welcome</title>
</head>
<body>
    <div>
        <c:choose>
            <c:when test="${login_status == '0'}">
                <p>
                    Welcome back
                    <c:out value="${cookie['current.user.fn']}" />
                    !
                </p>
                <form action="servleti/logout" method="post">
                    <input type="submit" value="Logout" />
                </form>
            </c:when>
        </c:choose>
    </div>
    <div>
        <form action="servleti/login" method="post">
            <input type="text" name="username" placeholder="Username"><br> <input type="password"
                name="password" placeholder="Password"><br> <input type="submit" value="Login" />
        </form>
    </div>
    <div>
        <c:choose>
            <c:when test="${login_status == '1'}">
                <p>
                    <c:out value="Incorrect username or password entered. Please try again." />
                </p>
            </c:when>
        </c:choose>
    </div>
    <div>
        <form action="register.jsp" method="post">
            <input type="submit" value="Register" />
        </form>
    </div>
    <div>
        <c:forEach var="user" items="${users}">
            <a href="servleti/author/${user.nick}" ><c:out value="${user.nick}" /></a>
        </c:forEach>
    </div>
</body>
</html>